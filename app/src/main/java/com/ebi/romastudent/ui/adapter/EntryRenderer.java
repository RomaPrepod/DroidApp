package com.ebi.romastudent.ui.adapter;

import android.support.v7.widget.RecyclerView;

public abstract class EntryRenderer<VH extends RecyclerView.ViewHolder, T> implements VHFactory<VH>, VHBinder<VH, T> {

}
