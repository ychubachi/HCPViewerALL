/*
 * CTimeUtils.java
 * Created on Apr 7, 2011 
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.time;

import java.util.Calendar;

/**
 * @author macchan
 */
public class CTimeUtils {

	public static CTime getNextWeek(CTime when) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(when.getAsDate());
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		return new CTime(cal.getTime());
	}

	public static CTime getStartOfWeek(CTime when) {
		return getStartOfWeek(when, Calendar.getInstance().getFirstDayOfWeek());
		// SUNDAY, in Japan
	}

	public static CTime getStartOfWeek(CTime when, int firstDayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(firstDayOfWeek);
		cal.setTime(when.getAsDate());
		cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return new CTime(cal.getTime());
	}

	public static void main(String[] args) {
		CTime time;
		time = getStartOfWeek(new CTime());
		System.out.println(time);
		System.out.println(getNextWeek(time));

		time = getStartOfWeek(new CTime(), Calendar.WEDNESDAY);
		System.out.println(time);
		System.out.println(getNextWeek(time));
	}
}
