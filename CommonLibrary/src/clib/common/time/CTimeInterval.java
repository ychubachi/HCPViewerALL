/*
 * CTimeInterval.java
 * Created on Mar 10, 2011 
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.time;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author macchan
 * 
 */
public class CTimeInterval {

	private static final NumberFormat formatter2 = new DecimalFormat("00");
	private static final NumberFormat formatter4 = new DecimalFormat("0000");

	private long time;

	public CTimeInterval(long time) {
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	public int getMillisecond() {
		return (int) (time % 1000);
	}

	public int getSecond() {
		return (int) ((time / 1000) % 60);
	}

	public int getMinute() {
		return (int) ((time / 1000 / 60) % 60);
	}

	public int getHour() {
		return (int) ((time / 1000 / 60 / 60) % 24);
	}

	public int getDay() {
		return (int) ((time / 1000L / 60 / 60 / 24));
	}

	public String getMillisecondString() {
		return formatter4.format(getMillisecond());
	}

	public String getSecondString() {
		return formatter2.format(getSecond());
	}

	public String getMinuteString() {
		return formatter2.format(getMinute());
	}

	public String getHourString() {
		return formatter2.format(getHour());
	}

	public String getDayString() {
		return getDay() + "d";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getMajorString() + getDetailString();
	}

	public String getMajorString() {
		String str = "";
		if (getDay() > 0) {
			str += getDayString() + " ";
		}
		str += getHourString() + ":" + getMinuteString();
		return str;
	}

	public String getDetailString() {
		return "(:" + getSecondString() + ":" + getMillisecondString() + ")";
	}

	public static void main(String[] args) {
		System.out.println(new CTimeInterval(1372));
		System.out.println(new CTimeInterval(62458));
		System.out.println(new CTimeInterval(3600000));
		System.out.println(new CTimeInterval(3600000 * 24));
		System.out.println(new CTimeInterval(3600000L * 24L * 10L));
		System.out.println(new CTimeInterval((3600000L * 24L * 30L) + 1L));
	}
}
