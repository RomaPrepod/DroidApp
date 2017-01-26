package com.ebi.romastudent.ui.adapter;

import android.support.v7.widget.RecyclerView;

public interface EntryProvider {

	void setAdapter(RecyclerView.Adapter adapter);

	VHFactory getVHFactory(int position);

	void bindVH(RecyclerView.ViewHolder holder, int position);

	Object getEntry(int position);

	int getEntryCount();
}