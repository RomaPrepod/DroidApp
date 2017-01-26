package com.ebi.romastudent.ui.adapter;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseEntryViewHolder<T> extends BaseViewHolder {
	public BaseEntryViewHolder(View v) {
		super(v);
	}

	public BaseEntryViewHolder(@LayoutRes int layout, ViewGroup parent) {
		super(layout, parent);
	}

	public abstract void bind(int position, T entry);
}
