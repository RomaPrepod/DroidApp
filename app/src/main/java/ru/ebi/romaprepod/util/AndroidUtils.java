package ru.ebi.romaprepod.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.SpannableStringBuilder;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.List;

import ru.ebi.romaprepod.App;
import ru.ebi.romaprepod.R;

@SuppressWarnings("unused")
public class AndroidUtils {

	private static String appVersionName;
	private static Bundle appMetaData;
	private static String deviceId;
	private static Boolean isTablet;

	public static void ensureMainThread() {
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new IllegalStateException("method must be called only in main thread");
		}
	}

	public static void showKeyboard(View view) {
		if (view == null) {
			return;
		}
		try {
			InputMethodManager inputManager = (InputMethodManager) view.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isKeyboardShowed(View view) {
		if (view == null) {
			return false;
		}
		try {
			InputMethodManager inputManager =
					(InputMethodManager) view.getContext()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
			return inputManager.isActive(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void hideKeyboard(View view) {
		if (view == null) {
			return;
		}
		try {
			InputMethodManager imm = (InputMethodManager) view.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (!imm.isActive()) {
				return;
			}
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void hideKeyboard(Activity activity) {
		View focusedView = activity.getCurrentFocus();
		if (focusedView != null) {
			hideKeyboard(focusedView);
		}
	}

	public static void showKeyboard(Activity activity) {
		View focusedView = activity.getCurrentFocus();
		if (focusedView != null) {
			showKeyboard(focusedView);
		}
	}

	public static void showDialogFragment(FragmentManager manager, DialogFragment dialog) {
		showDialogFragment(manager, "dialog", dialog);
	}

	public static void showDialogFragment(FragmentManager manager, String tag, DialogFragment dialog) {
		Fragment fragment = manager.findFragmentByTag(tag);
		if (fragment == null) {
			dialog.show(manager, tag);
		}
	}

	/**
	 * Устанавливает {@link View} простейший селектор (рипл для L+ и полупрозрачный квадрат для pre-L)
	 *
	 * @param v {@link View}
	 */
	public static void applySelector(@NonNull View v) {
		v.setClickable(true);
		v.setBackgroundResource(R.drawable.selector_default);
	}

	public static String getDeviceId() {
		if (deviceId == null) {
			deviceId = Settings.Secure.getString(
					App.get().getContentResolver(),
					Settings.Secure.ANDROID_ID
			);
		}
		return deviceId;
	}

	public static CharSequence formatMobilePhone(String phone) {
		phone = phone.startsWith("7") ? "+" + phone : phone;
		return formatPhone(phone);
	}

	public static CharSequence formatPhone(CharSequence phone) {
		SpannableStringBuilder builder = new SpannableStringBuilder(phone);
		new PhoneNumberFormattingTextWatcher().afterTextChanged(builder);
		return builder;
	}

	public static String getInstallerPackageName(String applicationId) {
		return App.get().getPackageManager().getInstallerPackageName(applicationId);
	}

	/**
	 * Метод безопасно пытается стартануть активити по переданному интенту.
	 * Если не удалось, показывает юзеру тост
	 */
	public static void startActivitySafe(Context context, Intent activityIntent, String toastMessage) {
		try {
			context.startActivity(activityIntent);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Метод безопасно пытается стартануть активити по переданному интенту.
	 * Если не удалось, показывает юзеру тост
	 */
	public static void startActivitySafe(Context context, Intent activityIntent, @StringRes int toastMessageId) {
		startActivitySafe(context, activityIntent, context.getString(toastMessageId));
	}

	/**
	 * Метод безопасно пытается стартануть активити по переданному интенту.
	 * Если не удалось, показывает юзеру тост с надписью "Приложение не найдено"
	 */
	public static void startActivitySafe(Context context, Intent activityIntent) {
		startActivitySafe(context, activityIntent, "Приложение не найдено");
	}

	@NonNull
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static Point getWindowSize(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			display.getSize(size);
		} else {
			size.x = display.getWidth();
			size.y = display.getHeight();
		}
		return size;
	}

	public static int getActionBarHeight(Context context) {
		TypedValue tv = new TypedValue();
		context.getTheme().resolveAttribute(R.attr.actionBarSize, tv, true);
		return TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
	}

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	/**
	 * Из числа получает hex строку (применимо для html)
	 *
	 * @param color цвет
	 * @return цвет в формате #ffffffff
	 */
	public static String getColorHex(int color) {
		return String.format("#%06X", (0xFFFFFF & color));
	}

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	public static boolean isAirplaneModeOn() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
			return Settings.System.getInt(App.get().getContentResolver(),
					Settings.System.AIRPLANE_MODE_ON, 0) != 0;
		} else {
			return Settings.Global.getInt(App.get().getContentResolver(),
					Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
		}
	}

	public static boolean isArtInUse() {
		final String vmVersion = System.getProperty("java.vm.version");
		return vmVersion != null && vmVersion.startsWith("2");
	}

	public static void removeLayoutListener(ViewTreeObserver observer, ViewTreeObserver.OnGlobalLayoutListener listener) {
		if (Build.VERSION.SDK_INT < 16) {
			observer.removeGlobalOnLayoutListener(listener);
		} else {
			observer.removeOnGlobalLayoutListener(listener);
		}
	}

	public static void openUrlInBrowser(Context context, String url) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivitySafe(context, browserIntent);
	}

	public static void sendEmail(Context context, String emailTo, String theme, String body) {
		Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailTo, null));
		intent.putExtra(Intent.EXTRA_SUBJECT, theme);
		intent.putExtra(Intent.EXTRA_TEXT, body);

		startActivitySafe(context, intent);
	}

	public static Intent getCallPhoneIntent(String phone) {
		String uri = "tel:" + phone;
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse(uri));
		return intent;
	}

	public static boolean isWifiEnabled() {
		ConnectivityManager connManager = (ConnectivityManager) App.get()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connManager.getActiveNetworkInfo();
		if (activeNetworkInfo == null) {
			return false;
		}

		return activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI ||
				activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIMAX;
	}

	public static boolean isAppInstalled(String packageId) {
		PackageManager packageManager = App.get().getPackageManager();
		List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);
		for (PackageInfo packageInfo : packages) {
			if (packageInfo.packageName.equals(packageId)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPlayStoreInstalled() {
		PackageManager packageManager = App.get().getPackageManager();
		List<PackageInfo> packages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA);
		for (PackageInfo packageInfo : packages) {
			if (packageInfo.packageName.equals("com.android.vending") ||
					packageInfo.packageName.equals("com.google.market")) {
				return true;
			}
		}
		return false;
	}

	@NonNull
	public static Intent getPlayStoreIntent(String packageName) {
		if (AndroidUtils.isPlayStoreInstalled()) {
			return new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("market://details?id=" + packageName)
			);
		}
		return new Intent(
				Intent.ACTION_VIEW,
				Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)
		);
	}

	@NonNull
	public static String getAppVersion() {
		if (appVersionName != null) {
			return appVersionName;
		}
		try {
			PackageInfo pInfo = App.get().getPackageManager()
					.getPackageInfo(App.get().getPackageName(), 0);
			appVersionName = pInfo.versionName;
			return appVersionName;
		} catch (PackageManager.NameNotFoundException e) {
			throw new RuntimeException("cant get app version name");
		}
	}

	public static void setBackground(@NonNull View v, Drawable drawable) {
		if (Build.VERSION.SDK_INT >= 16) {
			v.setBackground(drawable);
		} else {
			// noinspection deprecation
			v.setBackgroundDrawable(drawable);
		}
	}

	public static void addToClipboard(CharSequence str) {
		try {
			ClipboardManager clipboard =
					(ClipboardManager) App.get().getSystemService(Context.CLIPBOARD_SERVICE);
			android.content.ClipData clip = android.content.ClipData.newPlainText("label", str);
			clipboard.setPrimaryClip(clip);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	public static String getPath(final Uri uri) {
		try {
			final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
			if (isKitKat && DocumentsContract.isDocumentUri(App.get(), uri)) {
				if (isExternalStorageDocument(uri)) {
					final String docId = DocumentsContract.getDocumentId(uri);
					final String[] split = docId.split(":");
					final String type = split[0];
					if ("primary".equalsIgnoreCase(type)) {
						return Environment.getExternalStorageDirectory() + "/" + split[1];
					}
				} else if (isDownloadsDocument(uri)) {
					final String id = DocumentsContract.getDocumentId(uri);
					final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
					return getDataColumn(App.get(), contentUri, null, null);
				} else if (isMediaDocument(uri)) {
					final String docId = DocumentsContract.getDocumentId(uri);
					final String[] split = docId.split(":");
					final String type = split[0];

					Uri contentUri = null;
					switch (type) {
						case "image":
							contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
							break;
						case "video":
							contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
							break;
						case "audio":
							contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
							break;
					}

					final String selection = "_id=?";
					final String[] selectionArgs = new String[]{
							split[1]
					};

					return getDataColumn(App.get(), contentUri, selection, selectionArgs);
				}
			} else if ("content".equalsIgnoreCase(uri.getScheme())) {
				return getDataColumn(App.get(), uri, null, null);
			} else if ("file".equalsIgnoreCase(uri.getScheme())) {
				return uri.getPath();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {
				column
		};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				String value = cursor.getString(column_index);
				if (value.startsWith("content://") || !value.startsWith("/") && !value.startsWith("file://")) {
					return null;
				}
				return value;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}

	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}


	public static String readStringManifestMeta(String name) {
		ensureMetaData();
		return appMetaData.getString(name);
	}

	public static int readIntManifestMeta(String name) {
		ensureMetaData();
		return appMetaData.getInt(name);
	}

	private static void ensureMetaData() {
		if (appMetaData != null) {
			return;
		}
		try {
			final ApplicationInfo ai = App.get().getPackageManager().getApplicationInfo(
					App.get().getPackageName(),
					PackageManager.GET_META_DATA);
			appMetaData = ai.metaData;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}