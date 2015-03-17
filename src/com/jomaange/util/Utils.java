package com.jomaange.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}
	
	

	/*
	 * currentDate in DD-MM-YYYY
	 */
	public static String currentDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd/M/yyyy");
		String date = format.format(new Date());
		return date;
	}

	public static boolean validLongitudeAndLatitude(String location) {

		String REGEX = "([+-]?\\d+\\.?\\d+)\\s*,\\s*([+-]?\\d+\\.?\\d+)";
		String INPUT = location;
		Pattern pattern;
		Matcher matcher = null;

		pattern = Pattern.compile(REGEX);
		matcher = pattern.matcher(INPUT);
		return matcher.matches();

	}
}