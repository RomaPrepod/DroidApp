package com.ebi.romastudent.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ebi.romastudent.R;
import com.ebi.romastudent.util.LayoutHelper;

public class TextInfoView extends RelativeLayout {

	private TextView titleView;
	private TextView infoView;
	private ImageView rightImage;

	private String emptyInfo;

	public TextInfoView(Context context) {
		this(context, null);
	}

	public TextInfoView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressWarnings("all")
	public TextInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextInfoView);
		String title = a.getString(R.styleable.TextInfoView_infoTitle);
		emptyInfo = a.getString(R.styleable.TextInfoView_infoEmpty);
		Drawable drawableRight = a.getDrawable(R.styleable.TextInfoView_drawableRight);
		a.recycle();

		titleView = new TextView(context);
		titleView.setTextSize(15);
		titleView.setId(android.R.id.title);
		addView(titleView);
		infoView = new TextView(context);
		infoView.setTextColor(ContextCompat.getColor(context, R.color.gray));
		addView(infoView);

		LayoutParams titleLp = new LayoutParams(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT);
		titleLp.addRule(ALIGN_PARENT_LEFT);
		titleView.setLayoutParams(titleLp);
		LayoutParams infoLp = new LayoutParams(LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT);
		infoLp.addRule(ALIGN_PARENT_LEFT);
		infoLp.addRule(BELOW, android.R.id.title);
		infoView.setLayoutParams(infoLp);

		int vPadding = getResources().getDimensionPixelOffset(R.dimen.container_vpadding);
		int hPadding = getResources().getDimensionPixelOffset(R.dimen.container_hpadding);

		setPadding(
				hPadding,
				getPaddingTop() == 0 ? vPadding : getPaddingTop(),
				hPadding,
				getPaddingBottom() == 0 ? vPadding : getPaddingBottom()
		);

		setDrawableRight(drawableRight);
		titleView.setText(title);
		infoView.setText(emptyInfo);
		setBackgroundResource(R.drawable.selector_default);
	}

	public void setTitle(CharSequence title) {
		titleView.setText(title);
	}

	public CharSequence getTitle() {
		return titleView.getText();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		@ColorInt int textColor = enabled ?
				Color.BLACK :
				ContextCompat.getColor(getContext(), R.color.gray_disabled);
		titleView.setTextColor(textColor);
		@ColorInt int infoColor = enabled
				? ContextCompat.getColor(getContext(), R.color.gray)
				: ContextCompat.getColor(getContext(), R.color.gray_disabled);
		infoView.setTextColor(infoColor);
	}

	public void setInfo(@Nullable String value) {
		if (TextUtils.isEmpty(value)) {
			infoView.setText(emptyInfo);
		} else {
			infoView.setText(value);
		}
	}

	public void setEmptyInfo(String emptyInfo) {
		if (infoView.getText().equals(this.emptyInfo)) {
			infoView.setText(emptyInfo);
		}
		this.emptyInfo = emptyInfo;
	}

	public void setDrawableRight(@DrawableRes int drawableRes) {
		setDrawableRight(ContextCompat.getDrawable(getContext(), drawableRes));
	}

	public ImageView setDrawableRight(@Nullable Drawable drawable) {
		if (drawable == null) {
			if (rightImage != null) {
				rightImage.setVisibility(GONE);
			}
			return rightImage;
		}
		if (rightImage == null) {
			rightImage = new AppCompatImageView(getContext());
			rightImage.setId(android.R.id.icon);
			addView(rightImage);
			LayoutParams lp = (LayoutParams) rightImage.getLayoutParams();
			lp.addRule(CENTER_VERTICAL);
			lp.addRule(ALIGN_PARENT_RIGHT);

			LayoutParams titleLP = ((LayoutParams) titleView.getLayoutParams());
			titleLP.addRule(LEFT_OF, android.R.id.icon);
			LayoutParams subtitleLP = ((LayoutParams) infoView.getLayoutParams());
			subtitleLP.addRule(LEFT_OF, android.R.id.icon);
		}
		rightImage.setImageDrawable(drawable);
		requestLayout();
		invalidate();
		return rightImage;
	}

	public TextView getInfoView() {
		return infoView;
	}

	public TextView getTitleView() {
		return titleView;
	}
}