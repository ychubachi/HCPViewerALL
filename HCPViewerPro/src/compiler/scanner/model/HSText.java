/*
 * @(#)HSText.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;


/**
 * 字句解析したテキストを格納するための読み取り専用（不変）オブジェクトです。
 * @author Manabu Sugiura
 * @version $Id: HSText.java,v 1.3 2004/11/17 09:00:29 gackt Exp $
 */
public class HSText extends HSElement {

	/**
	 * 新しくオブジェクトを生成します。
	 * @param contents 内容
	 */
	public HSText(String contents) {
		super(contents);
	}

	/**
	 * オブジェクトの文字列表現を返します。
	 * @return このオブジェクトの文字列表現
	 */
	public String toString() {
		return "[HSText:" + this.getContents() + "]";
	}

	/**
	 * デバッグ用の文字列表現を返します。
	 * @return 内容に含まれる全・半角スペース、タブを可視化したデバッグ用の文字列表現
	 */
	public String debugPrint() {
		return HSElement.visualizeWhiteSpace(this.toString());
	}

}