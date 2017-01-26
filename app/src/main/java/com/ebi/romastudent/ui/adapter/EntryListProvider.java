package com.ebi.romastudent.ui.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntryListProvider extends BaseEntryProvider {

	private final List<Object> entries = new ArrayList<>();
	private final ArrayList<VHFactory> vhFactories = new ArrayList<>();
	private final ArrayList<VHBinder> vhBinders = new ArrayList<>();

	@Override
	public VHFactory getVHFactory(int position) {
		return vhFactories.get(position);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void bindVH(RecyclerView.ViewHolder holder, int position) {
		Object entry = entries.get(position);
		VHBinder binder = vhBinders.get(position);
		if (binder != null) {
			binder.onBindViewHolder(holder, position, entry);
		}
	}

	@Override
	public int getEntryCount() {
		return entries.size();
	}

	@Override
	public Object getEntry(int position) {
		return entries.get(position);
	}

	public <VH extends RecyclerView.ViewHolder, T> void setItems(
			List<T> items, VHFactory<? extends VH> vhFactory, VHBinder<VH, T> vhBinder
	) {
		entries.clear();
		vhFactories.clear();
		vhBinders.clear();
		entries.addAll(items);
		vhFactories.ensureCapacity(items.size());
		vhBinders.ensureCapacity(items.size());
		for (int i = 0, size = items.size(); i < size; i++) {
			vhFactories.add(vhFactory);
			vhBinders.add(vhBinder);
		}
	}

	public <VH extends RecyclerView.ViewHolder, T> void setItems(
			List<T> items, EntryRenderer<VH, T> renderer
	) {
		setItems(items, renderer, renderer);
	}

	public <VH extends RecyclerView.ViewHolder, T> void setItems(
			T[] items, EntryRenderer<VH, T> renderer
	) {
		setItems(Arrays.asList(items), renderer, renderer);
	}

	public <VH extends RecyclerView.ViewHolder, T> void setItems(
			T[] items, VHFactory<? extends VH> vhFactory, VHBinder<VH, T> vhBinder
	) {
		setItems(Arrays.asList(items), vhFactory, vhBinder);
	}

	public <VH extends RecyclerView.ViewHolder, T> void addItems(
			List<T> items, VHFactory<? extends VH> vhFactory, VHBinder<VH, T> vhBinder
	) {
		for (int i = 0, size = items.size(); i < size; i++) {
			Object item = items.get(i);
			entries.add(item);
			vhFactories.add(vhFactory);
			vhBinders.add(vhBinder);
		}
	}

	public <VH extends RecyclerView.ViewHolder, T> void addItems(
			List<T> items, EntryRenderer<VH, T> renderer
	) {
		addItems(items, renderer, renderer);
	}

	public <VH extends RecyclerView.ViewHolder, T> void addItems(
			T[] items, VHFactory<? extends VH> vhFactory, VHBinder<VH, T> vhBinder
	) {
		addItems(Arrays.asList(items), vhFactory, vhBinder);
	}

	public <VH extends RecyclerView.ViewHolder, T> void addItems(
			T[] items, EntryRenderer<VH, T> renderer
	) {
		addItems(Arrays.asList(items), renderer, renderer);
	}

	public <VH extends RecyclerView.ViewHolder, T> void addItem(
			T item, VHFactory<? extends VH> vhFactory, VHBinder<VH, T> vhBinder
	) {
		entries.add(item);
		vhFactories.add(vhFactory);
		vhBinders.add(vhBinder);
	}

	public <VH extends RecyclerView.ViewHolder, T> void addItem(
			T item, EntryRenderer<VH, T> renderer
	) {
		addItem(item, renderer, renderer);
	}

	public <VH extends RecyclerView.ViewHolder, T> void addItem(
			int index, T item, VHFactory<? extends VH> vhFactory, VHBinder<VH, T> vhBinder
	) {
		entries.add(index, item);
		vhFactories.add(index, vhFactory);
		vhBinders.add(index, vhBinder);
	}

	public <VH extends RecyclerView.ViewHolder, T> void addItem(
			int index, T item, EntryRenderer<VH, T> renderer
	) {
		addItem(index, item, renderer, renderer);
	}

	public void clearItems() {
		entries.clear();
		vhFactories.clear();
		vhBinders.clear();
	}

	public void removeItemAt(int index) {
		entries.remove(index);
		vhFactories.remove(index);
		vhBinders.remove(index);
	}

	public void removeItemsRange(int positionStart, int itemCount) {
		entries.subList(positionStart, positionStart + itemCount).clear();
		vhFactories.subList(positionStart, positionStart + itemCount).clear();
		vhBinders.subList(positionStart, positionStart + itemCount).clear();
	}

	public int indexOnfEntry(Object entry) {
		for (int i = 0, size = entries.size(); i < size; i++) {
			Object item = entries.get(i);
			if (item == null) {
				if (entry == null) {
					return i;
				}
				continue;
			}
			if (item.equals(entry)) {
				return i;
			}
		}
		return -1;
	}

	public int indexOfVHFactory(VHFactory factory) {
		for (int i = 0, size = vhFactories.size(); i < size; i++) {
			if (vhFactories.get(i).equals(factory)) {
				return i;
			}
		}
		return -1;
	}

	public int indexOfVHBinder(VHBinder binder) {
		for (int i = 0, size = vhBinders.size(); i < size; i++) {
			VHBinder item = vhBinders.get(i);
			if (item == null) {
				if (binder == null) {
					return i;
				}
				continue;
			}
			if (item.equals(binder)) {
				return i;
			}
		}
		return -1;
	}

	public boolean hasVHFactory(VHFactory factory) {
		return vhFactories.contains(factory);
	}
}