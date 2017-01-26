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
import com.ebi.romastudent.model.Lecture;
import com.ebi.romastudent.ui.adapter.EntryAdapter;
import com.ebi.romastudent.ui.adapter.EntryListProvider;
import com.ebi.romastudent.ui.renderer.LectureRenderer;
import com.ebi.romastudent.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class LecturesFragment extends BaseFragment {

	private FragmentLecturesBinding binding;

	private LectureRenderer lectureRenderer;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lectureRenderer = new LectureRenderer();
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

		List<Lecture> lectures = new ArrayList<>();
		lectures.add(new Lecture("Лекция 1. Введение", "В этой лекции студентов знакомят с тематикой предмета, предметной областью целями и задачами интернет приложений. Основной уроп идет на общее понимание среды существовония интренет проектов. Закрладывается фундамеент для дальнейшего освоения метриала курса. Так же на первом занятие предусмотрено входное тетирование, нацеленное на выяснение остаточных знаний студентов и общего уровня подготовки.", System.currentTimeMillis() - TimeUtils.days(3)));
		lectures.add(new Lecture("Лекция 2. Обзор сиситем управления контентом и знакомство с 1С-Битрикс", "Лекция включает в себя обзор систем управления контентом существующих на рынке. Обзор функций и возможностей 1С-Битрикс:Управление сайтом и инфраскруктуры постороенной вокруг системы.", System.currentTimeMillis() - TimeUtils.days(2)));
		lectures.add(new Lecture("Лекция 3. Внедрение шаблона дизайна.", "Рассматривается примение дизайна, правила интеграции гипертекстовой разметки в шаблон сайта построенного на 1С-Битрикс.", System.currentTimeMillis() - TimeUtils.days(1)));

		entryProvider.setItems(lectures, lectureRenderer);
		entryProvider.notifyDataSetChanged();
	}

	private void load() {
		// TODO
	}
}