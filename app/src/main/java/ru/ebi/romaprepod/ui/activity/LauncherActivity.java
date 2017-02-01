package ru.ebi.romaprepod.ui.activity;

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

import java.util.Collections;

import ru.ebi.romaprepod.BuildConfig;
import ru.ebi.romaprepod.repository.UserManager;

public class LauncherActivity extends BaseActivity {

	private Lock lock;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (false) {
			Auth0 auth0 = new Auth0(BuildConfig.AUTH0_CLIENT_ID, BuildConfig.AUTH0_DOMAIN);
			lock = Lock.newBuilder(auth0, callback)
					.closable(false)
					.loginAfterSignUp(true)
					.useBrowser(false)
					.withAuthenticationParameters(Collections.singletonMap("scope", (Object) "openid email name picture groups roles"))
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
			Log.d("LockCallback", "getAccessToken: " + credentials.getAccessToken());
			Log.d("LockCallback", "getIdToken: " + credentials.getIdToken());
			Log.d("LockCallback", "getRefreshToken: " + credentials.getRefreshToken());
			Log.d("LockCallback", "getType: " + credentials.getType());

			UserManager.setUser(credentials.getIdToken());

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