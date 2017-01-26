package ru.ebi.romaprepod.util;

@SuppressWarnings("unused")
public class TimeUtils {

	public static long seconds(long seconds) {
		return seconds * 1000L;
	}

	public static long minutes(long minutes) {
		return minutes * 60000L;
	}

	public static long hours(long hours) {
		return hours * 3600000L;
	}

	public static long days(long days) {
		return days * 86400000L;
	}

	public static boolean isActual(long date, long ttl) {
		return Math.abs(System.currentTimeMillis() - date) < ttl;
	}

	public static int daysBetween(long from, long to) {
		return (int) ((to - from) / (1000 * 60 * 60 * 24));
	}
}