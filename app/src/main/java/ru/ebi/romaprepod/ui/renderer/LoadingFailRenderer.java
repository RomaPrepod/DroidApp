package ru.ebi.romaprepod.ui.renderer;

import android.view.View;
import android.view.ViewGroup;

import ru.ebi.romaprepod.R;
import ru.ebi.romaprepod.ui.adapter.BaseViewHolder;
import ru.ebi.romaprepod.ui.adapter.EntryRenderer;

public class LoadingFailRenderer extends EntryRenderer<LoadingFailRenderer.Holder, Void> {

	private final View.OnClickListener onRetryListener;

	public LoadingFailRenderer(View.OnClickListener onRetryListener) {
		this.onRetryListener = onRetryListener;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent) {
		return new Holder(parent, onRetryListener);
	}

	@Override
	public void onBindViewHolder(Holder h, int position, Void entry) {}

	static class Holder extends BaseViewHolder {

		View retryBtn;

		Holder(ViewGroup parent, View.OnClickListener onRetryListener) {
			super(R.layout.item_bottom_fail, parent);
			retryBtn = findView(R.id.loading_retry_button);
			retryBtn.setOnClickListener(onRetryListener);
		}
	}
}