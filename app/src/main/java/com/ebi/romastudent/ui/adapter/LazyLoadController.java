package com.ebi.romastudent.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Контрол для реализации "ленивой подгрузки" в адаптере.
 */
public class LazyLoadController {

	private final OnLoadBottomListener onLoadBottomListener;

	private boolean isLoading;
	private boolean isEndReached;
	private boolean isFailed;

	private int threshold;

	private boolean skipScrollCallback = true;

	public LazyLoadController(RecyclerView list, OnLoadBottomListener loadBottomListener) {
		this(list, loadBottomListener, 10);
	}

	public LazyLoadController(RecyclerView list, OnLoadBottomListener loadBottomListener, int threshold) {
		this.onLoadBottomListener = loadBottomListener;
		this.threshold = threshold;
		list.addOnScrollListener(new OnRecyclerScrollListener());
	}

	public boolean isLoading() {
		return isLoading;
	}

	public boolean isEndReached() {
		return isEndReached;
	}

	public void setLoading() {
		isLoading = true;
		isFailed = false;
		isEndReached = false;
	}

	public void setLoaded() {
		isLoading = false;
		isFailed = false;
	}

	public void setIsEndReached(boolean isEndReached) {
		this.isEndReached = isEndReached;
	}

	public boolean isFailed() {
		return isFailed;
	}

	public void setFailed() {
		isFailed = true;
		isLoading = false;
	}

	private class OnRecyclerScrollListener extends RecyclerView.OnScrollListener {
		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			if (skipScrollCallback) {
				skipScrollCallback = false;
				return;
			}
			if (!isEndReached && !isLoading && !isFailed && isLastItem(recyclerView)) {
				isLoading = true;
				onLoadBottomListener.loadBottom();
			}
		}
	}

	private boolean isLastItem(RecyclerView list) {
		if (list.getChildCount() == 0) {
			return true;
		}
		final RecyclerView.LayoutManager lm = list.getLayoutManager();
		final int lastChildIndex = lm.getChildCount() - 1;
		final View lastView = lm.getChildAt(lastChildIndex);
		return list.getChildAdapterPosition(lastView) >= list.getAdapter().getItemCount() - threshold;
	}

	public interface OnLoadBottomListener {
		void loadBottom();
	}
}
