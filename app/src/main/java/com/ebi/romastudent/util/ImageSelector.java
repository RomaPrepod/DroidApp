package com.ebi.romastudent.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.io.ByteArrayOutputStream;

@SuppressWarnings("unused")
public class ImageSelector {

	private static final int PHOTO_DEFAULT_MAX_WIDTH = 1080;
	private static final int PHOTO_DEFAULT_MAX_HEIGHT = 1080;
	private static final int PHOTO_DEFAULT_TARGET_QUALITY = 87;

	private static final int WRITE_EXTERNAL_STORAGE_PERMISSION = 27;

	private static final String EXTRA_ATTACHMENT_URI = "extra_attachment_uri";
	private static final String EXTRA_IMAGE_SELECT_TYPE = "extra_image_select_type";

	/**
	 * Обрабатывает пикчу до 1080х1080 с качеством 85%
	 *
	 * @param uri пикча
	 * @return пикча в байтах
	 */
	@WorkerThread
	public static byte[] processImage(Uri uri) {
		return processImage(
				uri, PHOTO_DEFAULT_MAX_WIDTH, PHOTO_DEFAULT_MAX_HEIGHT, PHOTO_DEFAULT_TARGET_QUALITY
		);
	}

	/**
	 * Обрабатывает пикчу
	 *
	 * @param uri           пикча
	 * @param maxW          максимальная ширина пикчи
	 * @param maxH          максимальная выста пикчи
	 * @param targetQuality качество сжатия пикчи
	 * @return пикча в байтах
	 */
	@WorkerThread
	public static byte[] processImage(Uri uri, int maxW, int maxH, int targetQuality) {
		Bitmap photo = Bitmaps.loadBitmap(null, uri, maxW, maxH, true);
		if (photo == null) {
			throw new NullPointerException("Error in bitmap reading!");
		}

		// компрессим битмапку в stream
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.JPEG, targetQuality, stream);

		return stream.toByteArray();
	}

	private final Activity activity;
	private final Fragment fragment;
	private final Context context;
	private final Callback callback;

	private Uri attachmentUri;

	private int imageSelectType = Callback.SelectType.CAMERA;

	/**
	 * Инструктор-конструктор!
	 *
	 * @param activity собсна, активити
	 * @param callback собсна, коллбэк
	 */
	public ImageSelector(Activity activity, Callback callback) {
		this.activity = activity;
		this.fragment = null;
		this.context = activity;
		this.callback = callback;
	}

	/**
	 * Инструктор-конструктор!
	 *
	 * @param fragment собсна, фрагмент
	 * @param callback собсна, коллбэк
	 */
	public ImageSelector(Fragment fragment, Callback callback) {
		this.activity = null;
		this.fragment = fragment;
		this.context = fragment.getContext();
		this.callback = callback;
	}

	/**
	 * Обязательно положить в onCreate
	 */
	public void onCreate(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			attachmentUri = savedInstanceState.getParcelable(EXTRA_ATTACHMENT_URI);
			imageSelectType = savedInstanceState.getInt(EXTRA_IMAGE_SELECT_TYPE);
		}
	}

	/**
	 * Обязательно положить в onSaveInstance
	 */
	public void onSaveInstance(Bundle outState) {
		outState.putParcelable(EXTRA_ATTACHMENT_URI, attachmentUri);
		outState.putInt(EXTRA_IMAGE_SELECT_TYPE, imageSelectType);
	}

	/**
	 * Обязательно положить в onRequestPermissionsResult. Разруливает пермишены
	 */
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == WRITE_EXTERNAL_STORAGE_PERMISSION) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				if (imageSelectType == Callback.SelectType.GALLERY) {
					startGalleryPickActivity();
				} else if (imageSelectType == Callback.SelectType.CAMERA) {
					startPhotoTakeActivity();
				}
			} else {
				callback.onImageError(imageSelectType, Callback.ErrorType.REQUEST_PERMISSIONS_FAIL);
			}
		}
	}

	/**
	 * Обязательно положить в onActivityResult. Доставляет результат в коллбэк
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		int selectType = requestCode - 500;
		if (selectType != Callback.SelectType.CAMERA && selectType != Callback.SelectType.GALLERY) {
			return;
		}
		if (resultCode != Activity.RESULT_OK) {
			callback.onImageCanceled(selectType);
			return;
		}
		switch (selectType) {
			case Callback.SelectType.GALLERY:
				attachmentUri = data.getData();
				callback.onImageSelected(selectType, attachmentUri);
				break;
			case Callback.SelectType.CAMERA:
				callback.onImageSelected(selectType, attachmentUri);
				break;
		}
	}

	/**
	 * Вызвать для открытия экрана выбора изображения из галлереи
	 */
	public void startGalleryPickActivity() {
		imageSelectType = Callback.SelectType.GALLERY;
		if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				== PackageManager.PERMISSION_GRANTED) {
			attachmentUri = null;
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			if (activity != null) {
				activity.startActivityForResult(intent, 500 + imageSelectType);
			} else {
				fragment.startActivityForResult(intent, 500 + imageSelectType);
			}
		} else {
			requestPermissions();
		}
	}

	/**
	 * Вызывать для открытия экрана фотографирования
	 */
	public void startPhotoTakeActivity() {
		imageSelectType = Callback.SelectType.CAMERA;
		if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				== PackageManager.PERMISSION_GRANTED) {

			attachmentUri = Bitmaps.createImageUri();

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, attachmentUri);
			if (activity != null) {
				activity.startActivityForResult(intent, 500 + imageSelectType);
			} else {
				fragment.startActivityForResult(intent, 500 + imageSelectType);
			}
		} else {
			requestPermissions();
		}
	}

	private void requestPermissions() {
		if (activity != null) {
			ActivityCompat.requestPermissions(
					activity,
					new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
					WRITE_EXTERNAL_STORAGE_PERMISSION
			);
		} else {
			fragment.requestPermissions(
					new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
					WRITE_EXTERNAL_STORAGE_PERMISSION
			);
		}
	}

	public interface Callback {

		void onImageCanceled(int selectType);

		void onImageError(int selectType, int errorType);

		void onImageSelected(int selectType, Uri uri);

		class ErrorType {
			public final static int REQUEST_PERMISSIONS_FAIL = 0;
		}

		class SelectType {
			public final static int CAMERA = 0;
			public final static int GALLERY = 1;
		}
	}
}