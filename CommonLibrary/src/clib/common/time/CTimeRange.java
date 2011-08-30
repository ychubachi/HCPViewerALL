/*
 * CTimeRange.java
 * Created on Mar 24, 2011 
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.time;

/**
 * @author macchan
 * 
 */
public class CTimeRange {

	private CTime start;
	private CTime end;

	public CTimeRange() {
		this(new CTime(), new CTime());
	}

	public CTimeRange(long start, long end) {
		this(new CTime(start), new CTime(end));
	}

	public CTimeRange(CTime start, CTime end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * @return the end
	 */
	public CTime getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(CTime end) {
		this.end = end;
	}

	/**
	 * @return the start
	 */
	public CTime getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(CTime start) {
		this.start = start;
	}

	public CTimeInterval getLength() {
		return new CTimeInterval(getLengthAsLong());
	}

	public long getLengthAsLong() {
		return this.end.getAsLong() - this.start.getAsLong();
	}

	public boolean isAfter(CTime when) {
		return start.after(when);
	}

	public boolean isAfterAndEqual(CTime when) {
		return start.afterAndEqual(when);
	}

	public boolean isBefore(CTime when) {
		return end.before(when);
	}

	public boolean isBeforeAndEqual(CTime when) {
		return end.beforeAndEqual(when);
	}

	public boolean isIncluding(CTime when) {
		return when.afterAndEqual(start) && when.beforeAndEqual(end);
	}

	public CTimeRange getNextWeek() {
		return new CTimeRange(start.getNextWeek(), end.getNextWeek());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		return toString().equals(obj.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "from: " + start + " to: " + end + " (" + getLength() + ") ";
	}

	public static void main(String[] args) {
		System.out.println(new CTimeRange());
		System.out.println(new CTimeRange(new CTime(700000000000l), new CTime(
				800000000000l)));
		System.out.print("1.expected:true ");
		System.out.println(new CTimeRange(new CTime(100), new CTime(200))
				.isAfter(new CTime(50)));

		System.out.print("2.expected:false ");
		System.out.println(new CTimeRange(new CTime(100), new CTime(200))
				.isAfter(new CTime(150)));

		System.out.print("3.expected:false ");
		System.out.println(new CTimeRange(new CTime(100), new CTime(200))
				.isAfter(new CTime(250)));

		System.out.print("4.expected:false ");
		System.out.println(new CTimeRange(new CTime(100), new CTime(200))
				.isBefore(new CTime(50)));

		System.out.print("5.expected:false ");
		System.out.println(new CTimeRange(new CTime(100), new CTime(200))
				.isBefore(new CTime(150)));

		System.out.print("6.expected:true ");
		System.out.println(new CTimeRange(new CTime(100), new CTime(200))
				.isBefore(new CTime(250)));

		System.out.print("7.expected:false ");
		System.out.println(new CTimeRange(new CTime(100), new CTime(200))
				.isIncluding(new CTime(50)));

		System.out.print("8.expected:true ");
		System.out.println(new CTimeRange(new CTime(100), new CTime(200))
				.isIncluding(new CTime(150)));

		System.out.print("9.expected:false ");
		System.out.println(new CTimeRange(new CTime(100), new CTime(200))
				.isIncluding(new CTime(250)));
	}
}
