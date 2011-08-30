/*
 * @(#)HDataListScanner.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.extenddata.datascanner;

import java.util.ArrayList;
import java.util.List;

import compiler.HCompileLogger;
import compiler.HStringUtils;

/**
 * データ記述の字句解析機 
 * ・データの区切りを解析して，1～N個のデータ（の名前）に解析します
 * ・例えば，“入力文字，出力文字”なら，“[入力文字][出力文字]という2個のデータに解析します
 * ・HElementScannerで，Textとして認識され，かつデータ記述だと断定された箇所を再度スキャンするために使用されます
 * @author Manabu Sugiura
 * @version $Id: HDataListScanner.java,v 1.3 2009/09/10 03:48:33 macchan Exp $
 */
public class HDataListScanner {

	/***********************
	 * クラス変数
	 ***********************/

	//状態定義
	private static final int WAIT_FIRST_DATA = 0;
	private static final int READ_DATA = 1;
	private static final int WAIT_NEXT_DATA = 2;
	private static final int CANCEL = 3;

	//エラーメッセージ
	private static final String NO_DATA_EMES = "区切り文字に対してデータの記述が不足しています";
	private static final String NO_SUCH_STATE_EMES = "定義されていない状態です";

	public static final char EOL = '\0';

	/***********************
	 * インスタンス変数
	 ***********************/

	private int state = WAIT_FIRST_DATA;
	private List<HSData> dataList = new ArrayList<HSData>();//解析結果（HSDataのリスト） 
	private StringBuffer buffer = new StringBuffer();//読み取り中の文字バッファ

	/**
	 * コンストラクタ
	 */
	public HDataListScanner() {
		super();
	}

	/**
	 * スキャン結果を取得します。
	 * @return dataList データのリスト
	 */
	public List<HSData> getDataList() {
		return this.dataList;
	}

	/**
	 * 入力を行います。
	 * @param c 入力文字
	 * @param lineNumber 行番号
	 */
	public void receive(char c, int lineNumber) {
		switch (this.state) {
			case WAIT_FIRST_DATA :
				this.processWaitFirstData(c, lineNumber);
				break;
			case READ_DATA :
				this.processReadData(c);
				break;
			case WAIT_NEXT_DATA :
				this.processWaitNextData(c, lineNumber);
				break;
			case CANCEL :
				break;
			default :
				throw new RuntimeException(NO_SUCH_STATE_EMES);
		}
	}

	/**
	 * 最初のデータを待っている状態からの処理です。
	 * @param c 入力文字
	 * @param lineNumber 行番号
	 */
	private void processWaitFirstData(char c, int lineNumber) {
		switch (c) {
			case '　' :
			case ' ' :
			case '\t' :
				this.state = WAIT_FIRST_DATA;
				break;
			case ',' :
			case '，' :
			case '、' :
				this.showWarningMessage(lineNumber);
				this.state = CANCEL;
				break;
			case EOL :
				this.showWarningMessage(lineNumber);
				break;
			default :
				this.appendBuffer(c);
				this.state = READ_DATA;
				break;
		}
	}

	/**
	 * データを読み取り中からの処理です。
	 * @param c 入力文字
	 */
	private void processReadData(char c) {
		switch (c) {
			case ',' :
			case '，' :
			case '、' :
				this.stockDataElement();
				this.state = WAIT_NEXT_DATA;
				break;
			case EOL :
				this.stockDataElement();
				break;
			default :
				this.appendBuffer(c);
				this.state = READ_DATA;
				break;
		}
	}

	/**
	 * 2個目以降のデータを待っている状態からの処理です。
	 * @param c 入力文字
	 * @param lineNumber 行番号
	 */
	private void processWaitNextData(char c, int lineNumber) {
		switch (c) {
			case '　' :
			case ' ' :
			case '\t' :
				this.state = WAIT_NEXT_DATA;
				break;
			case ',' :
			case '，' :
			case '、' :
				this.showWarningMessage(lineNumber);
				this.state = CANCEL;
				break;
			case EOL :
				this.showWarningMessage(lineNumber);
				break;
			default :
				this.appendBuffer(c);
				this.state = READ_DATA;
				break;
		}
	}

	/**
	 * 読み取り中の警告を通知します。
	 * @param lineNumber 行番号
	 */
	private void showWarningMessage(int lineNumber) {
		HCompileLogger logger = HCompileLogger.getInstance();
		logger.showWarningMessage(NO_DATA_EMES, lineNumber);
	}

	/**
	 * データをリストに追加します。
	 */
	private void stockDataElement() {
		this.dataList.add(new HSData(this.getBuffer()));
		this.clearBuffer();
	}

	/**
	 * 文字列バッファに文字を追加します。
	 * @param c 追加する文字
	 */
	private void appendBuffer(char c) {
		this.buffer.append(c);
	}

	/**
	 * 文字列バッファから文字列を取得します。 取得の際には文字列末尾の余分な空白を削除します。
	 * @return バッファから取得した文字列
	 */
	private String getBuffer() {
		return HStringUtils.removeLastWhiteSpace(this.buffer.toString());
	}

	/**
	 * 文字列バッファを初期化します。
	 */
	private void clearBuffer() {
		this.buffer.delete(0, this.buffer.length());
	}

}