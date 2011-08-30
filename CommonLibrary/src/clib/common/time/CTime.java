/*
 * CTimeInterval.java
 * Created on Mar 24, 2011 
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.time;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author macchan
 */
public class CTime implements ICTimeOrderable {

	public static long MILLISECOND = 1;
	public static long SECOND = MILLISECOND * 1000;
	public static long MINUTE = SECOND * 60;
	public static long HOUR = MINUTE * 60;
	public static long DAY = HOUR * 24;
	public static long MONTH = DAY * 30;
	public static long YEAR = MONTH * 30;

	public static long getLength(int field) {
		if (!lengths.containsKey(field)) {
			throw new RuntimeException();
		}
		return lengths.get(field);
	}

	private static Map<Integer, Long> lengths = new HashMap<Integer, Long>();

	static {
		lengths.put(Calendar.MILLISECOND, MILLISECOND);
		lengths.put(Calendar.SECOND, SECOND);
		lengths.put(Calendar.MINUTE, MINUTE);
		lengths.put(Calendar.HOUR_OF_DAY, HOUR);
		lengths.put(Calendar.DAY_OF_MONTH, DAY);
		lengths.put(Calendar.MONTH, MONTH);
		lengths.put(Calendar.YEAR, YEAR);
	}

	public static DateFormat format = DateFormat.getDateTimeInstance();

	private long millis;

	public CTime() {
		this.millis = System.currentTimeMillis();
	}

	public CTime(long millis) {
		this.millis = millis;
	}

	public CTime(Date date) {
		this.millis = date.getTime();
	}

	public CTime(Calendar cal) {
		this.millis = cal.getTimeInMillis();
	}

	public CTime(CTime another) {
		this.millis = another.millis;
	}

	public CTime(int year, int month, int date, int hour, int min, int sec) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date, hour, min, sec);
		this.millis = cal.getTimeInMillis();
	}

	public long getAsLong() {
		return millis;
	}

	public Date getAsDate() {
		return new Date(millis);
	}

	public Calendar getAsCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		return cal;
	}

	public boolean before(CTime when) {
		// return getAsDate().before(when.getAsDate());
		return millis < when.millis;
	}

	public boolean beforeAndEqual(CTime when) {
		// return getAsDate().before(when.getAsDate());
		return millis <= when.millis;
	}

	public boolean after(CTime when) {
		// return getAsDate().after(when.getAsDate());
		return millis > when.millis;
	}

	public boolean afterAndEqual(CTime when) {
		// return getAsDate().before(when.getAsDate());
		return millis >= when.millis;
	}

	public CTimeInterval diffrence(CTime when) {
		long diff = Math.abs(when.millis - millis);
		return new CTimeInterval(diff);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof CTime)) {
			return false;
		}
		return ((CTime) obj).millis == this.millis;
	}

	public CTime getNextWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getAsDate());
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		return new CTime(cal.getTime());
	}

	public CTime getStartOfWeek() {
		return getStartOfWeek(Calendar.getInstance().getFirstDayOfWeek());
		// SUNDAY, in Japan
	}

	public CTime getStartOfWeek(int firstDayOfWeek) {
		return getStartOfWeek(firstDayOfWeek, 0);
	}

	public CTime getStartOfWeek(int firstDayOfWeek, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(firstDayOfWeek);
		cal.setTime(getAsDate());
		cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new CTime(cal.getTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clib.common.time.IPLTimeOrderable#getTime()
	 */
	@Override
	public CTime getTime() {
		return this;
	}

	public String toString(DateFormat formatter) {
		return formatter.format(getAsDate());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return format.format(getAsDate());
	}

	public static void main(String[] args) {
		CTime time;
		time = new CTime();
		System.out.println(time);
		time = time.getStartOfWeek();
		System.out.println(time);
		time = time.getNextWeek();
		System.out.println(time);

		time = new CTime();
		System.out.println(time);
		time = time.getStartOfWeek(Calendar.WEDNESDAY, 3);
		System.out.println(time);
		time = time.getNextWeek();
		System.out.println(time);
	}

}
