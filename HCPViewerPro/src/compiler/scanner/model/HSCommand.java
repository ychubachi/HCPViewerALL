/*
 * @(#)HSCommand.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;

/**
 * 字句解析したコマンドを格納するための読み取り専用（不変）オブジェクトです。
 * 
 * @author Manabu Sugiura
 * @version $Id: HSCommand.java,v 1.5 2004/11/18 12:08:07 macchan Exp $
 */
public class HSCommand extends HSElement {

	public static final HSCommand DEFAULT_COMMAND = new HSCommand("Default");

	/**
	 * 新しくオブジェクトを生成します。
	 * 
	 * @param contents
	 *               内容
	 */
	public HSCommand(String contents) {
		super(contents);
	}

	/**
	 * オブジェクトの文字列表現を返します。
	 * 
	 * @return このオブジェクトの文字列表現
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[HSCommand:" + this.getContents());
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * デバッグ用の文字列表現を返します。
	 * 
	 * @return 内容に含まれる全・半角スペース、タブを可視化したデバッグ用の文字列表現
	 */
	public String debugPrint() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[HSCommand:" + this.getContents());
		buffer.append("]");
		return HSElement.visualizeWhiteSpace(buffer.toString());
	}

}