/*
 * @(#)HStringUtils.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler;

/**
 * 文字操作のためのスタティックユーティリティーメソッド
 * @author Manabu Sugiura
 * @version $Id: HStringUtils.java,v 1.3 2004/11/25 05:00:46 gackt Exp $
 */
public class HStringUtils {

	private HStringUtils() {
		//Do nothing.
	}

	public static String removeLastWhiteSpace(String s) {
		int len = s.length();
		while (len != 0
				&& (s.charAt(len - 1) == ' ' || s.charAt(len - 1) == '　' || s
						.charAt(len - 1) == '\t')) {
			s = chop(s);
			len = s.length();
		}
		return s;
	}

	private static String chop(String s) {
		int len = s.length();
		if (len > 0) {
			return s.substring(0, len - 1);
		}
		return s;
	}

}