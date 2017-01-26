package com.ebi.romastudent.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

public class EntryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private final Map<VHFactory, Integer> vhFactoryToViewType = new HashMap<>();
	private final Map<Integer, VHFactory> viewTypeToVHFactory = new HashMap<>();
	private EntryProvider entryProvider;

	public EntryAdapter() {
	}

	public EntryAdapter(EntryProvider entryProvider) {
		setEntryProvider(entryProvider);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		VHFactory factory = viewTypeToVHFactory.get(viewType);
		return factory.onCreateViewHolder(parent);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		getEntryProvider().bindVH(holder, position);
	}

	@Override
	public int getItemCount() {
		return getEntryProvider().getEntryCount();
	}

	@Override
	public int getItemViewType(int position) {
		VHFactory renderer = getEntryProvider().getVHFactory(position);
		return getVHFactoryViewType(renderer);
	}

	@NonNull
	public EntryProvider getEntryProvider() {
		if (entryProvider == null) {
			throw new IllegalArgumentException("set EntryProvider to Adapter");
		}
		return entryProvider;
	}

	/**
	 * Ленивая инициализация соответствия viewType к {@link VHFactory}
	 *
	 * @param factory {@link VHFactory} для которого нужно получить тип
	 */
	private int getVHFactoryViewType(VHFactory factory) {
		Integer type = vhFactoryToViewType.get(factory);
		if (type == null) {
			type = vhFactoryToViewType.size();
			vhFactoryToViewType.put(factory, type);
			viewTypeToVHFactory.put(type, factory);
		}
		return type;
	}

	public void setEntryProvider(EntryProvider provider) {
		this.entryProvider = provider;
		entryProvider.setAdapter(this);
	}
}
