package ru.ebi.romaprepod.repository;

import com.auth0.android.jwt.JWT;

public class UserManager {

	private static JWT user;

	public static void setUser(String token) {
		user = new JWT(token);
	}

	public static JWT getUser() {
		return user;
	}
}