package com.ebi.romastudent.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.auth0.android.Auth0;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.result.Credentials;

public class LauncherActivity extends BaseActivity {

	private Lock lock;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (false) {
			// Your own Activity code
			Auth0 auth0 = new Auth0("YOUR_AUTH0_CLIENT_ID", "YOUR_AUTH0_DOMAIN");
			lock = Lock.newBuilder(auth0, callback)
					//Customize Lock
					.build(this);

			startActivity(lock.newIntent(this));

			return;
		}

		startActivity(new Intent(this, MainActivity.class));
		getWindow().setWindowAnimations(0);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (lock != null) {
			lock.onDestroy(this);
			lock = null;
		}
	}

	private LockCallback callback = new AuthenticationCallback() {
		@Override
		public void onAuthentication(Credentials credentials) {
			Log.d("LockCallback", "onAuthentication: " + credentials.toString());
			startActivity(new Intent(LauncherActivity.this, MainActivity.class));
			getWindow().setWindowAnimations(0);
			finish();
		}

		@Override
		public void onCanceled() {
			Log.d("LockCallback", "onCanceled");
		}

		@Override
		public void onError(LockException error) {
			Log.d("LockCallback", "onError: " + error.toString());
		}
	};
}