package com.fnx.webapp.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	// load milliseconds
	public static Date loadDateAfterMins(Date inputDate, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDate);
		cal.add(Calendar.MINUTE, minute);
		return cal.getTime();
	}
}
