package com.ebi.romastudent.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.PluralsRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

@SuppressWarnings("unused")
public class BaseViewHolder extends RecyclerView.ViewHolder {

	protected final Context context;

	public BaseViewHolder(View v) {
		super(v);
		context = v.getContext();
	}

	public BaseViewHolder(@LayoutRes int layout, ViewGroup parent) {
		this(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T findView(@IdRes int viewId) {
		return (T) itemView.findViewById(viewId);
	}

	public String getString(@StringRes int stringResId) {
		return context.getString(stringResId);
	}

	public String[] getStringArray(@ArrayRes int resId) {
		return context.getResources().getStringArray(resId);
	}

	public String getQuantityString(@PluralsRes int resId, int quantity) {
		return context.getResources().getQuantityString(resId, quantity);
	}

	public String getQuantityString(@PluralsRes int resId, int quantity, Object... formatArgs) {
		return context.getResources().getQuantityString(resId, quantity, formatArgs);
	}

	public Drawable getDrawable(@DrawableRes int drawableResId) {
		return ContextCompat.getDrawable(context, drawableResId);
	}

	public Context getContext() {
		return context;
	}

	public void startActivity(Intent intent) {
		context.startActivity(intent);
	}
}
