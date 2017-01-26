package com.ebi.romastudent.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ebi.romastudent.R;
import com.ebi.romastudent.util.LayoutHelper;
import com.ebi.romastudent.util.Px;

/**
 * Враппер над спиннером
 */
public class SingleSelectView extends FrameLayout {

	private Spinner spinner;
	private CharSequence title;
	private CharSequence[] options;

	private View dummy;

	private Adapter adapter;

	public SingleSelectView(Context context) {
		this(context, null);
	}

	public SingleSelectView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SingleSelectView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		spinner = new AppCompatSpinner(context);
		LayoutParams lp = LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT);
		lp.bottomMargin = Px.dpToPx(2);
		lp.topMargin = Px.dpToPx(2);
		addView(spinner, lp);

		// хак для отключения long click у спиннера
		dummy = new View(context);
		dummy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				spinner.performClick();
			}
		});
		addView(dummy, LayoutHelper.createFrame(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SingleSelectView);
		title = a.getString(R.styleable.SingleSelectView_singleSelectTitle);
		options = a.getTextArray(R.styleable.SingleSelectView_singleSelectOptions);
		a.recycle();

		int vPadding = getResources().getDimensionPixelOffset(R.dimen.container_vpadding);
		int hPadding = 0;
		int right = Build.VERSION.SDK_INT < Build.VERSION_CODES.M ? Px.dpToPx(20) : Px.dpToPx(8);
		setPadding(
				hPadding,
				vPadding,
				right,
				vPadding
		);

		spinner.setAdapter(adapter = new Adapter());
	}

	public void setTitle(CharSequence title) {
		this.title = title;
		adapter.notifyDataSetChanged();
	}

	public void setOptions(CharSequence[] options) {
		this.options = options;
		adapter.notifyDataSetChanged();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		dummy.setEnabled(enabled);
		spinner.setEnabled(enabled);
		adapter.notifyDataSetChanged();
	}

	public void setSelection(int optionIndex) {
		spinner.setSelection(optionIndex);
	}

	public void setItemSelectedListener(AdapterView.OnItemSelectedListener itemSelectedListener) {
		spinner.setOnItemSelectedListener(itemSelectedListener);
	}

	private class Adapter extends BaseAdapter {

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			TextView v = (TextView) LayoutInflater.from(parent.getContext())
					.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
			v.setText(options[position]);
			return v;
		}

		@Override
		public int getCount() {
			return options == null ? 0 : options.length;
		}

		@Override
		public Object getItem(int position) {
			return options[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SelectedValueView v = convertView == null ?
					new SelectedValueView(parent.getContext()) :
					(SelectedValueView) convertView;
			v.title.setText(title);
			v.subtitle.setText(options[position]);
			@ColorInt int textColor = SingleSelectView.this.isEnabled() ?
					Color.BLACK :
					ContextCompat.getColor(getContext(), R.color.gray_disabled);
			v.title.setTextColor(textColor);
			return v;
		}

		@Override
		public int getItemViewType(int position) {
			return 0;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}
	}

	private static class SelectedValueView extends LinearLayout {

		public final TextView title;
		public final TextView subtitle;

		public SelectedValueView(Context context) {
			this(context, null);
		}

		public SelectedValueView(Context context, AttributeSet attrs) {
			this(context, attrs, 0);
		}

		public SelectedValueView(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
			setOrientation(VERTICAL);

			title = new TextView(context);
			subtitle = new TextView(context);
			addView(title, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
			addView(subtitle, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

			title.setTextSize(16);
			subtitle.setTextColor(ContextCompat.getColor(context, R.color.gray));
		}
	}
}