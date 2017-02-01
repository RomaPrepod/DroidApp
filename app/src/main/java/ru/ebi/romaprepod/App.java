package ru.ebi.romaprepod;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class App extends Application {

	private static App sApp;

	public static App get() {
		return sApp;
	}

	private Activity currentActivity;

	@Override
	public void onCreate() {
		super.onCreate();
		Fabric.with(this, new Crashlytics());
		sApp = this;

		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

			@Override
			public void onActivityStarted(Activity activity) {}

			@Override
			public void onActivityResumed(Activity activity) {
				currentActivity = activity;
			}

			@Override
			public void onActivityPaused(Activity activity) {
				currentActivity = null;
			}

			@Override
			public void onActivityStopped(Activity activity) {}

			@Override
			public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

			@Override
			public void onActivityDestroyed(Activity activity) {}
		});
	}

	@Nullable
	public Activity getCurrentActivity() {
		return currentActivity;
	}
}