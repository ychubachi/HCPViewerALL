/*
 * CEncoding.java
 * Created on 2010/02/15 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.system;

/**
 * CEncoding
 */
public class CEncoding {

	public static final CEncoding Shift_JIS = new CEncoding("SJIS");
	public static final CEncoding UTF8 = new CEncoding("UTF-8");
	public static final CEncoding JISAutoDetect = new CEncoding("JISAutoDetect");

	public static CEncoding getVMEncoding() {
		return new CEncoding(System.getProperty("file.encoding"));
	}

	public static CEncoding getSystemEncoding() {
		return new CEncoding(System.getProperty("sun.jnu.encoding"));
	}

	private String text;

	protected CEncoding(String text) {
		this.text = text;
	}

	public String toString() {
		return text;
	}

}
