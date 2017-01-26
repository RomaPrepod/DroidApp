package com.ebi.romastudent.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormats {

	public static final SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
	public static final SimpleDateFormat HHmmddMMMMyyyy = new SimpleDateFormat("HH:mm, dd MMMM yyyy г.", Locale.getDefault());
	public static final SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm", Locale.getDefault());

	public static String getDate(long timestamp) {
		if (System.currentTimeMillis() - days(1) < timestamp) {
			return HHmm.format(new Date(timestamp));
		} else {
			return HHmmddMMMMyyyy.format(new Date(timestamp));
		}
	}

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
}