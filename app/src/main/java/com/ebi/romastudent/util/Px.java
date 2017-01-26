package com.ebi.romastudent.util;

import android.util.TypedValue;

import com.ebi.romastudent.App;

/**
 * Util class for converting between dp, px and other magical pixel units
 */
public class Px {

	public static float density;

	static {
		density = App.get().getResources().getDisplayMetrics().density;
	}

	private Px() {
	}

	public static float spToRaw(float sp) {
		return TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, sp, App.get().getResources().getDisplayMetrics()
		);
	}

	@android.support.annotation.Px
	public static int dpToPx(float dp) {
		if (dp == 0) {
			return 0;
		}
		return (int) Math.ceil(density * dp);
	}

	public static float dpToFPx(float dp) {
		if (dp == 0) {
			return 0;
		}
		return density * dp;
	}

	public static int pxToDp(int px) {
		return Math.round((float) px / density);
	}
}