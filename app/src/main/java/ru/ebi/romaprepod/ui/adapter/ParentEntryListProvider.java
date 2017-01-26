package ru.ebi.romaprepod.ui.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ParentEntryListProvider extends BaseEntryProvider {

	private final List<EntryProvider> entryProviders = new ArrayList<>();

	@Override
	public VHFactory getVHFactory(int position) {
		int count = 0;
		EntryProvider provider = null;
		for (int i = 0; i < entryProviders.size(); i++) {
			provider = entryProviders.get(i);
			if (count + provider.getEntryCount() <= position) {
				count += provider.getEntryCount();
			} else {
				break;
			}
		}
		if (provider == null) {
			return null;
		}
		return provider.getVHFactory(position - count);
	}

	@Override
	public void bindVH(RecyclerView.ViewHolder holder, int position) {
		int count = 0;
		EntryProvider provider = null;
		for (int i = 0; i < entryProviders.size(); i++) {
			provider = entryProviders.get(i);
			if (count + provider.getEntryCount() <= position) {
				count += provider.getEntryCount();
			} else {
				break;
			}
		}
		if (provider != null) {
			provider.bindVH(holder, position - count);
		}
	}

	@Override
	public int getEntryCount() {
		int count = 0;
		for (EntryProvider provider : entryProviders) {
			count += provider.getEntryCount();
		}
		return count;
	}

	@Override
	public void setAdapter(RecyclerView.Adapter adapter) {
		super.setAdapter(adapter);
		for (EntryProvider provider : entryProviders) {
			provider.setAdapter(adapter);
		}
	}

	@Override
	public Object getEntry(int position) {
		int count = 0;
		for (EntryProvider provider : entryProviders) {
			if (count + provider.getEntryCount() <= position) {
				count += provider.getEntryCount();
			} else {
				return provider.getEntry(position);
			}
		}
		return null;
	}

	public EntryProvider getEntryProvider(int position) {
		int count = 0;
		for (EntryProvider provider : entryProviders) {
			if (count + provider.getEntryCount() <= position) {
				count += provider.getEntryCount();
			} else {
				return provider;
			}
		}
		return null;
	}

	public int getProviderEntriesOffset(int position) {
		int count = 0;
		for (EntryProvider provider : entryProviders) {
			if (count + provider.getEntryCount() <= position) {
				count += provider.getEntryCount();
			} else {
				return count;
			}
		}
		return 0;
	}

	public void setItems(List<EntryProvider> items) {
		entryProviders.clear();
		entryProviders.addAll(items);
	}

	public void addItems(List<EntryProvider> items) {
		entryProviders.addAll(items);
	}

	public void addItem(EntryProvider item) {
		entryProviders.add(item);
	}

	public void clearItems() {
		entryProviders.clear();
	}

	public void clearChildrenItems() {
		for (EntryProvider provider : entryProviders) {
			if (provider instanceof EntryListProvider) {
				((EntryListProvider) provider).clearItems();
			}
		}
	}

	public void removeItemAt(int index) {
		entryProviders.remove(index);
	}

	public void removeItemsRange(int positionStart, int itemCount) {
		entryProviders.subList(positionStart, positionStart + itemCount).clear();
	}
}