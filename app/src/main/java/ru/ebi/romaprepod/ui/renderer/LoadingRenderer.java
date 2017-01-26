package ru.ebi.romaprepod.ui.renderer;

import android.view.ViewGroup;

import ru.ebi.romaprepod.R;
import ru.ebi.romaprepod.ui.adapter.BaseViewHolder;
import ru.ebi.romaprepod.ui.adapter.EntryRenderer;

public class LoadingRenderer extends EntryRenderer<LoadingRenderer.Holder, Void> {

	@Override
	public Holder onCreateViewHolder(ViewGroup parent) {
		return new Holder(parent);
	}

	@Override
	public void onBindViewHolder(Holder h, int position, Void entry) {}

	public static class Holder extends BaseViewHolder {

		public Holder(ViewGroup parent) {
			super(R.layout.item_bottom_loading, parent);
		}
	}
}