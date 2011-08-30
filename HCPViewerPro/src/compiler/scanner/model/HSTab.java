/*
 * @(#)HSTab.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;

/**
 * 字句解析したタブを格納するための読み取り専用（不変）オブジェクトです。
 * @author Manabu Sugiura
 * @version $Id: HSTab.java,v 1.1 2004/10/18 06:14:53 gackt Exp $
 */
public class HSTab extends HSElement {

	/**
	 * 新しくオブジェクトを生成します。
	 */
	public HSTab() {
		super("");
	}

	/**
	 * オブジェクトの文字列表現を返します。
	 * @return このオブジェクトの文字列表現
	 */
	public String toString() {
		return "[HSTab]";
	}

	/**
	 * デバッグ用の文字列表現を返します。
	 * @return 内容に含まれる全・半角スペース、タブを可視化したデバッグ用の文字列表現
	 */
	public String debugPrint() {
		return "[HSTab]";
	}

}
