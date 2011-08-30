/*
 * CStringChopper.java
 * Created on 2010/02/12 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.string;

/**
 * CStringChopper
 */
public class CStringChopper {

	/**
	 * 最後の文字を切り取る
	 * 
	 * @param str
	 * @return
	 */
	public static String chopped(String str) {
		int index = str.length() - 1;
		if (index < 0) {
			return str;
		}
		return str.substring(0, index);
	}

	// 単体テスト
	public static void main(String[] args) {
		System.out.println(CStringChopper.chopped("abc"));
		System.out.println(CStringChopper.chopped("ab"));
		System.out.println(CStringChopper.chopped("a"));
		System.out.println(CStringChopper.chopped(""));
	}
}
