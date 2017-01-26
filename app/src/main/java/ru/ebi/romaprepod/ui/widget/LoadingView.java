package ru.ebi.romaprepod.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import ru.ebi.romaprepod.R;

public class LoadingView extends FrameLayout {

	private View mLoadingLayout;
	private TextView mLoadingText;
	private View mErrorLayout;
	private View mErrorImage;
	private TextView mErrorText;
	private TextView mRetryButton;

	private boolean mSmallMode = false;

	public LoadingView(Context context) {
		this(context, null);
	}

	public LoadingView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoadingView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();

		final TypedArray a = getContext().obtainStyledAttributes(
				attrs, R.styleable.LoadingView, defStyle, 0);

		mSmallMode = a.getBoolean(R.styleable.LoadingView_smallMode, false);

		a.recycle();
	}

	private void init() {
		View v = LayoutInflater.from(getContext()).inflate(R.layout.view_loading, this, true);

		mLoadingLayout = v.findViewById(R.id.loading_layout);
		mLoadingText = (TextView) v.findViewById(R.id.loading_text);
		mLoadingLayout.setVisibility(View.GONE);

		mErrorLayout = v.findViewById(R.id.error_layout);
		mRetryButton = (TextView) mErrorLayout.findViewById(R.id.loading_retry_button);
		mErrorImage = mErrorLayout.findViewById(R.id.loading_error_image);
		mErrorText = (TextView) mErrorLayout.findViewById(R.id.loading_error_text);
		mErrorLayout.setVisibility(View.GONE);

		if (getBackground() == null) {
			setBackgroundColor(Color.WHITE);
		}
		showLoaded();
	}

	public void setShowErrorImage(boolean showErrorImage) {
		mErrorImage.setVisibility(showErrorImage ? VISIBLE : GONE);
	}

	public void setLoadingText(String text) {
		mLoadingText.setText(text);
		mLoadingText.setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
	}

	public void setLoadingText(int stringId) {
		setLoadingText(getResources().getString(stringId));
	}

	public void showLoading() {
		setVisibility(View.VISIBLE);
		mErrorLayout.setVisibility(View.GONE);
		mLoadingLayout.setVisibility(View.VISIBLE);
	}

	public void showDefaultFail() {
		showFailed("Не удалось загрузить данные");
	}

	public void showFailed(String errMessage) {
		setVisibility(View.VISIBLE);
		mErrorText.setText(errMessage);
		mErrorLayout.setVisibility(View.VISIBLE);
		mLoadingLayout.setVisibility(View.GONE);

		if (mSmallMode) {
			mErrorImage.setVisibility(GONE);
		}
	}

	public void showFailed(int messageId) {
		showFailed(getResources().getString(messageId));
	}

	public void showLoaded() {
		setVisibility(View.GONE);
	}

	public void setOnRetryListener(@Nullable OnClickListener onClickListener) {
		if (onClickListener == null) {
			mRetryButton.setOnClickListener(null);
			mRetryButton.setVisibility(GONE);
		} else {
			mRetryButton.setOnClickListener(onClickListener);
			mRetryButton.setVisibility(VISIBLE);
		}
	}

	public boolean isLoaded() {
		return getVisibility() == View.GONE;
	}
}
