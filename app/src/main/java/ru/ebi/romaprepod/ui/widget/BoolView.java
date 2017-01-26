package ru.ebi.romaprepod.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ru.ebi.romaprepod.R;
import ru.ebi.romaprepod.util.AndroidUtils;
import ru.ebi.romaprepod.util.LayoutHelper;
import ru.ebi.romaprepod.util.Px;

public class BoolView extends RelativeLayout {

	private TextView titleView;
	private CheckBox checkView;
	private TextView subtitleView;
	private ImageView imageView;

	private OnClickListener onClickDelegate;

	public BoolView(Context context) {
		this(context, null);
	}

	public BoolView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BoolView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoolView);
		String title = a.getString(R.styleable.BoolView_boolTitle);
		boolean checked = a.getBoolean(R.styleable.BoolView_checked, false);
		String subtitle = a.getString(R.styleable.BoolView_boolSubtitle);
		Drawable drawableLeft = a.getDrawable(R.styleable.BoolView_drawableLeft);
		a.recycle();

		int vPadding = getResources().getDimensionPixelOffset(R.dimen.container_vpadding);
		int hPadding = getResources().getDimensionPixelOffset(R.dimen.container_hpadding);

		titleView = new AppCompatTextView(context);
		titleView.setId(android.R.id.title);
		titleView.setTextSize(15);
		titleView.setPadding(0, 0, hPadding, 0);
		checkView = new AppCompatCheckBox(context) {
			@Override
			public boolean dispatchTouchEvent(MotionEvent event) {
				return false;
			}
		};
		checkView.setId(android.R.id.checkbox);
		checkView.setSaveEnabled(false);

		if (!TextUtils.isEmpty(subtitle)) {
			subtitleView = new AppCompatTextView(context);
			subtitleView.setTextColor(ContextCompat.getColor(context, R.color.gray));
			subtitleView.setText(subtitle);
			subtitleView.setPadding(0, 0, hPadding, 0);
			LayoutParams subtitleLp = new LayoutParams(0, LayoutHelper.WRAP_CONTENT);
			subtitleLp.addRule(ALIGN_PARENT_LEFT);
			subtitleLp.addRule(BELOW, titleView.getId());
			subtitleLp.addRule(LEFT_OF, checkView.getId());
			subtitleView.setLayoutParams(subtitleLp);
			addView(subtitleView);
		}

		LayoutParams checkboxLp = new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT
		);
		checkboxLp.addRule(ALIGN_PARENT_RIGHT);
		checkView.setLayoutParams(checkboxLp);

		LayoutParams titleLp = new LayoutParams(0, LayoutHelper.WRAP_CONTENT);
		titleLp.addRule(ALIGN_PARENT_LEFT);
		titleLp.addRule(LEFT_OF, checkView.getId());
		titleView.setLayoutParams(titleLp);
		if (subtitleView == null) {
			titleLp.addRule(CENTER_VERTICAL);
			checkboxLp.addRule(CENTER_VERTICAL);
		}

		addView(titleView);
		addView(checkView);
		setPadding(hPadding, vPadding, hPadding, vPadding);

		titleView.setText(title);
		checkView.setChecked(checked);
		setDrawableLeft(drawableLeft);
		if (drawableLeft != null) {
			setTitlePaddingLeft(Px.dpToPx(12));
		}

		AndroidUtils.applySelector(this);

		super.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				BoolView.this.onClick(v);
				if (onClickDelegate != null) {
					onClickDelegate.onClick(v);
				}
			}
		});
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		@ColorInt int textColor = enabled ?
				Color.BLACK :
				ContextCompat.getColor(getContext(), R.color.gray_disabled);
		titleView.setTextColor(textColor);
		if (subtitleView != null) {
			subtitleView.setTextColor(textColor);
		}
		checkView.setEnabled(enabled);
	}

	public void setTypeface(Typeface tf) {
		titleView.setTypeface(tf);
	}

	public void setTitle(CharSequence title) {
		titleView.setText(title);
	}

	public CharSequence getTitle() {
		return titleView.getText().toString();
	}

	public void onClick(View v) {
		checkView.setChecked(!checkView.isChecked());
	}

	public void setChecked(boolean checked) {
		checkView.setChecked(checked);
	}

	public boolean isChecked() {
		return checkView.isChecked();
	}

	public void setDrawableLeft(@Nullable Drawable drawable) {
		if (drawable == null) {
			if (imageView != null) {
				imageView.setVisibility(GONE);
			}
			setTitlePaddingLeft(0);
			return;
		}
		if (imageView == null) {
			imageView = new AppCompatImageView(getContext());
			imageView.setId(android.R.id.icon);
			addView(imageView);

			LayoutParams titleLP = ((LayoutParams) titleView.getLayoutParams());
			titleLP.addRule(RIGHT_OF, android.R.id.icon);
			titleLP.addRule(ALIGN_PARENT_LEFT, 0);
			if (subtitleView != null) {
				LayoutParams subtitleLP = ((LayoutParams) subtitleView.getLayoutParams());
				subtitleLP.addRule(RIGHT_OF, android.R.id.icon);
				subtitleLP.addRule(ALIGN_PARENT_LEFT, 0);
				((LayoutParams) imageView.getLayoutParams()).addRule(CENTER_VERTICAL, 0);
			} else {
				((LayoutParams) imageView.getLayoutParams()).addRule(CENTER_VERTICAL);
			}
		}
		setTitlePaddingLeft(Px.dpToPx(12));
		imageView.setImageDrawable(drawable);
		requestLayout();
		invalidate();
	}

	public void setDrawableLeft(@DrawableRes int resId) {
		setDrawableLeft(ContextCompat.getDrawable(getContext(), resId));
	}

	public void setTitlePaddingLeft(int padding) {
		int hPadding = getResources().getDimensionPixelOffset(R.dimen.container_hpadding);
		titleView.setPadding(padding, 0, hPadding, 0);
		if (subtitleView != null) {
			subtitleView.setPadding(padding, 0, hPadding, 0);
		}
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		onClickDelegate = l;
	}

	public void setOnCheckedChangedListener(CompoundButton.OnCheckedChangeListener onCheckedChangedListener) {
		checkView.setOnCheckedChangeListener(onCheckedChangedListener);
	}
}