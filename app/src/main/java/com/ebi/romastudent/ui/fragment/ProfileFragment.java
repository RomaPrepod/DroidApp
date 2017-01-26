package com.ebi.romastudent.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebi.romastudent.R;
import com.ebi.romastudent.databinding.FragmentLecturesBinding;
import com.ebi.romastudent.ui.adapter.EntryAdapter;
import com.ebi.romastudent.ui.adapter.EntryListProvider;

public class ProfileFragment extends BaseFragment {

	private FragmentLecturesBinding binding;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lectures, container, false);
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		binding.loadingView.setOnRetryListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				load();
			}
		});

		EntryAdapter adapter = new EntryAdapter();
		EntryListProvider entryProvider = new EntryListProvider();
		adapter.setEntryProvider(entryProvider);

		binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
		binding.list.setAdapter(adapter);
	}

	private void load() {
		// TODO
	}
}