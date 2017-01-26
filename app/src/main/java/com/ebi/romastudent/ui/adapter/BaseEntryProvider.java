package com.ebi.romastudent.ui.adapter;

import android.support.v7.widget.RecyclerView;

public abstract class BaseEntryProvider implements EntryProvider {

	private RecyclerView.Adapter adapter;

	@Override
	public void setAdapter(RecyclerView.Adapter adapter) {
		this.adapter = adapter;
	}

	public RecyclerView.Adapter getAdapter() {
		return adapter;
	}

	public void notifyDataSetChanged() {
		adapter.notifyDataSetChanged();
	}

	public void notifyItemChanged(int position) {
		adapter.notifyItemChanged(position);
	}

	public void notifyItemRemoved(int position) {
		adapter.notifyItemRemoved(position);
	}

	public void notifyItemInserted(int position) {
		adapter.notifyItemInserted(position);
	}

	public void notifyItemRangeChanged(int positionStart, int itemCount) {
		adapter.notifyItemRangeChanged(positionStart, itemCount);
	}

	public void notifyItemRangeInserted(int positionStart, int itemCount) {
		adapter.notifyItemRangeInserted(positionStart, itemCount);
	}

	public void notifyItemRangeRemoved(int positionStart, int itemCount) {
		adapter.notifyItemRangeRemoved(positionStart, itemCount);
	}
}