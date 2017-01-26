package ru.ebi.romaprepod.ui.adapter;

import android.support.v7.widget.RecyclerView;

public interface VHBinder<VH extends RecyclerView.ViewHolder, T> {
	void onBindViewHolder(VH h, int position, T entry);
}
