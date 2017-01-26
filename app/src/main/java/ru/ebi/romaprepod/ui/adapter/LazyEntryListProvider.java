package ru.ebi.romaprepod.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import ru.ebi.romaprepod.ui.renderer.LoadingFailRenderer;
import ru.ebi.romaprepod.ui.renderer.LoadingRenderer;

public class LazyEntryListProvider extends EntryListProvider {

	private final LazyLoadController lazyLoadController;
	private final EntryRenderer loadingRenderer;
	private final EntryRenderer failRenderer;

	private boolean hasStatusItem;

	public LazyEntryListProvider(
			RecyclerView list,
			LazyLoadController.OnLoadBottomListener onLoadBottomListener,
			EntryRenderer loadingRenderer,
			EntryRenderer failRenderer
	) {
		lazyLoadController = new LazyLoadController(list, onLoadBottomListener);
		this.loadingRenderer = loadingRenderer;
		this.failRenderer = failRenderer;
	}

	public LazyEntryListProvider(
			RecyclerView list,
			LazyLoadController.OnLoadBottomListener onLoadBottomListener,
			View.OnClickListener onRetryListener
	) {
		this(list, onLoadBottomListener, new LoadingRenderer(),
				new LoadingFailRenderer(onRetryListener));
	}

	public LazyEntryListProvider(
			RecyclerView list,
			final LazyLoadController.OnLoadBottomListener onLoadBottomListener
	) {
		this(list, onLoadBottomListener, new LoadingRenderer(),
				new LoadingFailRenderer(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						onLoadBottomListener.loadBottom();
					}
				}));
	}

	@Override
	public VHFactory getVHFactory(int position) {
		if (hasStatusItem && position == super.getEntryCount()) {
			if (lazyLoadController.isFailed()) {
				return failRenderer;
			}
			if (lazyLoadController.isLoading()) {
				return loadingRenderer;
			}
		}
		return super.getVHFactory(position);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void bindVH(RecyclerView.ViewHolder holder, int position) {
		if (hasStatusItem && position == super.getEntryCount()) {
			if (lazyLoadController.isFailed()) {
				failRenderer.onBindViewHolder(holder, position, null);
			}
			if (lazyLoadController.isLoading()) {
				loadingRenderer.onBindViewHolder(holder, position, null);
			}
		} else {
			super.bindVH(holder, position);
		}
	}

	@Override
	public int getEntryCount() {
		return super.getEntryCount() + (hasStatusItem ? 1 : 0);
	}

	public void setLoading() {
		lazyLoadController.setLoading();
		hasStatusItem = true;
	}

	public void setFailed() {
		lazyLoadController.setFailed();
		hasStatusItem = true;
	}

	public void setLoaded(boolean isEndReached) {
		lazyLoadController.setLoaded();
		lazyLoadController.setIsEndReached(isEndReached);
		hasStatusItem = false;
	}
}