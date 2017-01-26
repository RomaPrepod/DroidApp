package ru.ebi.romaprepod.util;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Locale;

public class Blogger {

	private final static int stackTraceOffset = 4;

	public static void d(@NonNull String format, Object... args) {
		log(format, args);
	}

	/**
	 * Логирует в виде prm0: 23; prm1: 25; prm2: ghjh
	 */
	public static void d(Object... args) {
		String format = "";
		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				format += "; ";
			}
			format += "prm" + i + ": %10s";
		}
		log(format, args);
	}

	/**
	 * Логирует в виде test: 23; offset: 25; text: ghjh.
	 * Каждый нечётный аргумент должен быть "ключём", а чётный "значением".
	 */
	public static void auto(Object... args) {
		if (args.length % 2 != 0) {
			Log.e("Blogger:auto", "Not enough arguments!");
			return;
		}
		try {
			String format = "";
			Object[] arguments = new Object[args.length / 2];
			for (int i = 0; i < args.length; i += 2) {
				if (i != 0) {
					format += "; ";
				}
				format += args[i] + ": %10s";
				arguments[i / 2] = args[i + 1] instanceof Iterable ? listToString((Iterable) args[i + 1]) : args[i + 1];
			}
			log(format, arguments);
		} catch (Exception e) {
			Log.e("Blogger:auto", "Unexpected error!");
		}
	}

	private static void log(@NonNull String format, Object... args) {
		StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		StackTraceElement stackTraceElement = stackTraceElements[stackTraceOffset];
		String log = "(" + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + ")";
		Log.d(log, String.format(Locale.US, format, args));
	}

	private static String listToString(Iterable iterable) {
		StringBuilder sb = new StringBuilder();

		for (Object obj : iterable) {
			if (sb.length() == 0) {
				sb.append("List{");
			} else {
				sb.append(", ");
			}
			sb.append(obj.toString());
		}
		if (sb.length() == 0) {
			sb.append("List{}");
		} else {
			sb.append("}");
		}

		return sb.toString();
	}
}