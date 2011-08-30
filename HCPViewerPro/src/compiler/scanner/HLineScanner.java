/*
 * @(#)HDataListAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner;

import java.util.ArrayList;
import java.util.List;

import compiler.HStringUtils;
import compiler.scanner.model.HSCommand;
import compiler.scanner.model.HSComment;
import compiler.scanner.model.HSElement;
import compiler.scanner.model.HSTab;
import compiler.scanner.model.HSText;

/**
 * HCPチャートのソース一行分を字句解析機 ・タブ、コマンド、テキスト、コメントを解析し、それらを格納したリストを生成します。
 * ・例えば、“HCPのソース一行分を字句解析する \in ソース \out 解析結果”等を解析できます。
 * ・上記の解析結果は、“[HSText:HCPのソース一行分を字句解析する], [HSCommand:in], [HSText:ソース],
 * [HSCommand:out],[HSText:解析結果]”になります。
 * 
 * @author Manabu Sugiura
 * @version $Id: HLineScanner.java,v 1.3 2009/09/10 03:48:32 macchan Exp $
 */
public class HLineScanner {

	/***********************
	 * クラス変数
	 ***********************/

	// 状態定義
	private static final int READ_TAB = 1;
	private static final int READ_TEXT = 2;
	private static final int READ_COMMENT = 3;
	private static final int READ_COMMAND = 4;
	private static final int WAIT_FIRST_COMMAND = 5;
	private static final int WAIT_NEXT_COMMAND = 6;

	public static final char EOL = '\0';// 行の終了文字

	/***********************
	 * インスタンス変数
	 ***********************/

	private int state = READ_TAB;

	private List<HSElement> elements = new ArrayList<HSElement>();// 解析結果（SElementのリスト）
	private StringBuffer buffer = new StringBuffer();// 読み取り中の文字バッファ

	/**
	 * 入力を行います。
	 * 
	 * @param c
	 *            入力文字
	 */
	public void receive(char c) {
		switch (this.state) {
		case READ_TAB:
			this.processReadTab(c);
			break;
		case READ_TEXT:
			this.processReadText(c);
			break;
		case READ_COMMENT:
			this.processReadComment(c);
			break;
		case READ_COMMAND:
			this.processReadCommand(c);
			break;
		case WAIT_FIRST_COMMAND:
			this.processFirstCommand(c);
			break;
		case WAIT_NEXT_COMMAND:
			this.processNextCommand(c);
			break;
		default:
			throw new RuntimeException("予期しない状態です");
		}
	}

	/**
	 * タブを読み取り中の状態からの処理です。
	 * 
	 * @param c
	 *            入力文字
	 */
	private void processReadTab(char c) {
		switch (c) {
		case '　':
		case ' ':
			this.state = READ_TAB;
			break;
		case '\t':
			this.stockTabElement();
			this.state = READ_TAB;
			break;
		case '#':
			this.state = READ_COMMENT;
			break;
		case '\\':
			this.state = WAIT_FIRST_COMMAND;
			break;
		case EOL:
			break;
		default:
			this.appendChar(c);
			this.state = READ_TEXT;
			break;
		}
	}

	/**
	 * テキストを読み取り中の状態からの処理です。
	 * 
	 * @param c
	 *            入力文字
	 */
	private void processReadText(char c) {
		switch (c) {
		case '\\':
			this.state = WAIT_NEXT_COMMAND;
			break;
		case EOL:
			this.stockTextElement();
			break;
		default:
			this.appendChar(c);
			this.state = READ_TEXT;
			break;
		}
	}

	/**
	 * コメントを読み取り中の状態からの処理です。
	 * 
	 * @param c
	 *            入力文字
	 */
	private void processReadComment(char c) {
		switch (c) {
		case EOL:
			this.stockCommentElement();
			break;
		default:
			this.appendChar(c);
			this.state = READ_COMMENT;
			break;
		}
	}

	/**
	 * コマンドを読み取り中の状態からの処理です。
	 * 
	 * @param c
	 *            入力文字
	 */
	private void processReadCommand(char c) {
		switch (c) {
		case '　':
		case ' ':
		case '\t':
			this.stockCommandElement();
			this.state = READ_TEXT;
			break;
		case EOL:
			this.stockCommandElement();
			break;
		default:
			this.appendChar(c);
			this.state = READ_COMMAND;
			break;
		}
	}

	/**
	 * 最初のコマンドを待っている状態からの処理です。
	 * 
	 * @param c
	 *            入力文字
	 */
	private void processFirstCommand(char c) {
		switch (c) {
		case '\\':
			this.appendChar(c);
			this.state = READ_TEXT;
			break;
		case EOL:
			this.stockCommandElement();
			break;
		default:
			this.appendChar(c);
			this.state = READ_COMMAND;
			break;
		}
	}

	/**
	 * 2つ目以降のコマンドを待っている状態からの処理です。
	 * 
	 * @param c
	 *            入力文字
	 */
	private void processNextCommand(char c) {
		switch (c) {
		case '\\':
			this.appendChar(c);
			this.state = READ_TEXT;
			break;
		case EOL:
			this.stockTextElement();
			break;
		default:
			this.stockTextElement();
			this.appendChar(c);
			this.state = READ_COMMAND;
			break;
		}
	}

	/**
	 * タブをリストに追加します。
	 */
	private void stockTabElement() {
		this.elements.add(new HSTab());
	}

	/**
	 * テキストをリストに追加します。
	 */
	private void stockTextElement() {
		this.elements.add(new HSText(this.getBufferContents()));
		this.clearBuffer();
	}

	/**
	 * コメントをリストに追加します。
	 */
	private void stockCommentElement() {
		this.elements.add(new HSComment(this.getBufferContents()));
		this.clearBuffer();
	}

	/**
	 * コマンドをリストに追加します。
	 */
	private void stockCommandElement() {
		this.elements.add(new HSCommand(this.getBufferContents()));
		this.clearBuffer();
	}

	/**
	 * 文字列バッファに文字を追加します。
	 * 
	 * @param c
	 *            追加する文字
	 */
	private void appendChar(char c) {
		this.buffer.append(c);
	}

	/**
	 * 文字列バッファを初期化します。
	 */
	private void clearBuffer() {
		this.buffer.delete(0, this.buffer.length());
	}

	/**
	 * 文字列バッファから文字列を取得します。 取得の際には文字列末尾の余分な空白を削除します。
	 * 
	 * @return バッファから取得した文字列
	 */
	private String getBufferContents() {
		return HStringUtils.removeLastWhiteSpace(this.buffer.toString());
	}

	/**
	 * スキャンした要素を取得します。
	 * 
	 * @return elements スキャンした要素
	 */
	public List<HSElement> getElements() {
		return this.elements;
	}

}