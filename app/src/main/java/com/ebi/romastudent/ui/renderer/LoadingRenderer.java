package com.ebi.romastudent.ui.renderer;

import android.view.ViewGroup;

import com.ebi.romastudent.R;
import com.ebi.romastudent.ui.adapter.BaseViewHolder;
import com.ebi.romastudent.ui.adapter.EntryRenderer;

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