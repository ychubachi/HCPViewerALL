/*
 * @(#)HScanedLine.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner;

import compiler.HCompileOptions;

/**
 * @author Manabu Sugiura
 * @version $Id: HSpaceConvertedLine.java,v 1.2 2004/11/25 05:00:47 gackt Exp $
 */
public class HSpaceConvertedLine {

	private String indentPart = null;
	private String bodyPart = null;

	/**
	 * コンストラクタ
	 */
	public HSpaceConvertedLine(String s) {
		splitPart(s);
	}

	private void splitPart(String s) {
		//インデント部分と文字列部分を分割する
		for (int i = 0; i < s.length(); i++) {
			switch (s.charAt(i)) {
				case '　' :
				case ' ' :
				case '\t' :
					break;
				default : //本文が始まった
					indentPart = s.substring(0, i);
					bodyPart = s.substring(i);
					return;
			}
		}

		//インデントだけの場合
		indentPart = s;
		bodyPart = "";
	}

	public void replaceSpaceToTab() {
		HCompileOptions options = HCompileOptions.getInstance();

		//インデント部分の一定数連続するスペースをTabに置換
		//正規表現を利用します→“ {全角スペースの連続数}?| {半角スペースの連続数}?”
		String regex = "　{" + options.getFullSpaceSizeForATab() + "}?| {"
				+ options.getHalfSpaceSizeForATab() + "}?";
		indentPart = indentPart.replaceAll(regex, "\t");
	}

	public String toString() {
		return indentPart + bodyPart;
	}

}