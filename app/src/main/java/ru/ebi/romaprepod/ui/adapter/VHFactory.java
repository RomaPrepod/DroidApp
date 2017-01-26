package ru.ebi.romaprepod.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface VHFactory<VH extends RecyclerView.ViewHolder> {
	VH onCreateViewHolder(ViewGroup parent);
}
