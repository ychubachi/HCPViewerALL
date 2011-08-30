/*
 * @(#)HSElement.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;

/**
 * 字句解析の結果を格納するための読み取り専用（不変）オブジェクトです。
 * 
 * @author Manabu Sugiura
 * @version $Id: HSElement.java,v 1.3 2004/11/18 12:08:07 macchan Exp $
 */
public abstract class HSElement {

	private HSLine parent = null;

	/** 内容 */
	private String contents = null;

	/**
	 * 新しくオブジェクトを生成します。
	 * 
	 * @param contents
	 *               内容
	 */
	protected HSElement(String contents) {
		this.contents = contents;
	}

	/**
	 * 内容を取得します。
	 * 
	 * @return 内容
	 */
	public String getContents() {
		return this.contents;
	}

	/**
	 * オブジェクトの文字列表現を返します。
	 * 
	 * @return このオブジェクトの文字列表現
	 */
	public abstract String toString();

	/**
	 * デバッグ用の文字列表現を返します。
	 * 
	 * @return 内容に含まれる全・半角スペース、タブを可視化したデバッグ用の文字列表現
	 */
	public abstract String debugPrint();

	/**
	 * 文字列に含まれる全・半角スペース、タブを“□”に変換して可視化します。
	 * 
	 * @param s
	 *               変換する文字列
	 * @return 空白を可視化した文字列
	 */
	public static String visualizeWhiteSpace(String s) {
		char spaceMark = '□';
		String result = s.replace('　', spaceMark);
		result = result.replace(' ', spaceMark);
		result = result.replace('\t', spaceMark);
		return result;
	}

	/**
	 * @return Returns the parent.
	 */
	public HSLine getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *               The parent to set.
	 */
	protected void setParent(HSLine parent) {
		this.parent = parent;
	}
}