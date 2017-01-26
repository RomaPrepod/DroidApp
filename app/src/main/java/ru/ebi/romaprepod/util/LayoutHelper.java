package ru.ebi.romaprepod.util;

import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class LayoutHelper {

	public static final int MATCH_PARENT = -1;
	public static final int WRAP_CONTENT = -2;

	private static int getSize(float size) {
		return (int) (size < 0 ? size : Px.dpToPx(size));
	}

	public static FrameLayout.LayoutParams createFrame(int width, int height, int gravity) {
		return new FrameLayout.LayoutParams(getSize(width), getSize(height), gravity);
	}

	public static FrameLayout.LayoutParams createFrame(int width, int height) {
		return new FrameLayout.LayoutParams(getSize(width), getSize(height));
	}

	public static LinearLayout.LayoutParams createLinear(int width, int height, float weight, int gravity) {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getSize(width), getSize(height), weight);
		layoutParams.gravity = gravity;
		return layoutParams;
	}

	public static LinearLayout.LayoutParams createLinear(int width, int height, int gravity) {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getSize(width), getSize(height));
		layoutParams.gravity = gravity;
		return layoutParams;
	}

	public static LinearLayout.LayoutParams createLinear(int width, int height, float weight) {
		return new LinearLayout.LayoutParams(getSize(width), getSize(height), weight);
	}

	public static LinearLayout.LayoutParams createLinear(int width, int height) {
		return new LinearLayout.LayoutParams(getSize(width), getSize(height));
	}
}