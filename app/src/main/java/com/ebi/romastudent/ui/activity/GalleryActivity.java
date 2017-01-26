package com.ebi.romastudent.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ebi.romastudent.R;

import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

public class GalleryActivity extends BaseActivity {
	public static final String EXTRA_FRAMES = "extra_frames";
	public static final String EXTRA_SELECTED_INDEX = "extra_selected_index";

	public static Intent newIntent(Context context, String frame) {
		Intent intent = new Intent(context, GalleryActivity.class);
		intent.putExtra(EXTRA_FRAMES, new String[]{frame});
		intent.putExtra(EXTRA_SELECTED_INDEX, 0);
		return intent;
	}

	public static Intent newIntent(Context context, String[] frames, int selectedIndex) {
		Intent intent = new Intent(context, GalleryActivity.class);
		intent.putExtra(EXTRA_FRAMES, frames);
		intent.putExtra(EXTRA_SELECTED_INDEX, selectedIndex);
		return intent;
	}

	private View decorView;
	private String[] frames;
	private ViewPager pager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		decorView = getWindow().getDecorView();

		frames = getIntent().getStringArrayExtra(EXTRA_FRAMES);
		pager = new ViewPager(this);
		pager.setId(android.R.id.list);

		setContentView(pager);
		pager.setClipToPadding(false);
		pager.setPageMargin((int) (16 * getResources().getDisplayMetrics().density));
		pager.setBackgroundColor(Color.BLACK);
		pager.setAdapter(new GalleryPagerAdapter());
		pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				updateTitle();
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		if (savedInstanceState == null) {
			pager.setCurrentItem(getIntent().getIntExtra(EXTRA_SELECTED_INDEX, 0));
		}
		getSupportActionBar().setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.gallery_toolbar));
		updateTitle();
	}

	protected void updateTitle() {
		if (frames.length == 1) {
			setTitle("");
		} else {
			setTitle((pager.getCurrentItem() + 1) + " из " + frames.length);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		}
	}

	@Override
	public void finish() {
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);

		super.finish();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class GalleryPagerAdapter extends PagerAdapter {

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = GalleryActivity.this.getLayoutInflater().inflate(R.layout.view_gallery, null);
			PhotoDraweeView imageView = (PhotoDraweeView) view.findViewById(R.id.photo_view);

			imageView.setPhotoUri(Uri.parse(frames[position]));

			container.addView(view);
			imageView.setOnPhotoTapListener(new OnPhotoTapListener() {
				@Override
				public void onPhotoTap(View view, float x, float y) {
					if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
						return;
					}
					if ((decorView.getSystemUiVisibility() & View.SYSTEM_UI_FLAG_IMMERSIVE) ==
							View.SYSTEM_UI_FLAG_IMMERSIVE) {
						decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
								| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
								| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
					} else {
						decorView.setSystemUiVisibility(
								View.SYSTEM_UI_FLAG_LAYOUT_STABLE
										| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
										| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
										| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
										| View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
										| View.SYSTEM_UI_FLAG_IMMERSIVE);
					}
				}
			});
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return frames.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}
}