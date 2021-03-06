package ru.ebi.romaprepod.ui.renderer;

import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import ru.ebi.romaprepod.R;
import ru.ebi.romaprepod.model.Lecture;
import ru.ebi.romaprepod.ui.adapter.BaseViewHolder;
import ru.ebi.romaprepod.ui.adapter.EntryRenderer;
import ru.ebi.romaprepod.util.DateFormats;

public class LectureRenderer extends EntryRenderer<LectureRenderer.Holder, Lecture> {

	@Override
	public Holder onCreateViewHolder(ViewGroup parent) {
		return new Holder(parent);
	}

	@Override
	public void onBindViewHolder(final Holder h, int position, final Lecture lecture) {
		h.title.setText(lecture.title);
		h.description.setText(lecture.description);
		h.date.setText(DateFormats.ddMMyyyy.format(new Date(lecture.date)));
	}

	static class Holder extends BaseViewHolder {

		final TextView title;
		final TextView date;
		final TextView description;

		Holder(ViewGroup parent) {
			super(R.layout.item_lecture, parent);
			title = findView(R.id.title);
			date = findView(R.id.date);
			description = findView(R.id.description);
		}
	}
}