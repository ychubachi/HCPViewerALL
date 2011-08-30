/*
 * @(#)HChartElement.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * チャート要素を表現します。（処理とデータ）
 * @author Manabu Sugiura
 * @version $Id: HChartElement.java,v 1.8 2009/09/10 03:48:31 macchan Exp $
 */
public abstract class HChartElement extends HElement {

	//タイプ宣言
	/** エラー用 */
	public static final int UNKNOWN = -1;

	/** 基本型 */
	public static final int BASIS = 1;
	/** 繰り返し型 */
	public static final int REPEAT = 2;
	/** 振り分け型 */
	public static final int CONDITION = 3;
	/** 分岐型 */
	public static final int SELECT = 4;

	/** メソッド呼び出し型 */
	public static final int CALL_MODULE = 5;
	/** 脱出型 */
	public static final int RETURN = 6;
	/** 例外出口型 */
	public static final int EXCEPTION_EXIT = 7;
	/** エラーチェック型 */
	public static final int ERROR_CHECK = 8;

	/** 参照型 */
	public static final int REFERENCE = 9;
	/** 被参照型 */
	public static final int REFERED = 10;
	/** スペース型 */
	public static final int SPACE = 11;

	private int type;
	private boolean reverse = false;

	private List<HChartElement> inputs = new ArrayList<HChartElement>();//入力
	private List<HChartElement> outputs = new ArrayList<HChartElement>();//出力

	/**
	 * コンストラクタ
	 * @param type 種類
	 */
	protected HChartElement(int type) {
		this.type = type;
	}

	/**
	 * 種類を取得します
	 * @return 種類
	 */
	public int getType() {
		return this.type;
	}

	public String getTypeString() {
		switch (this.getType()) {
			case BASIS :
				return "基本型";
			case REPEAT :
				return "繰り返し型";
			case CALL_MODULE :
				return "メソッド呼び出し型";
			case RETURN :
				return "脱出型";
			case EXCEPTION_EXIT :
				return "例外出口型";
			case ERROR_CHECK :
				return "エラーチェック型";
			case CONDITION :
				return "分岐型";
			case SELECT :
				return "条件型";
			case REFERENCE :
				return "参照型";
			case REFERED :
				return "被参照型";
			case UNKNOWN :
				return "エラーです";
			default :
				return "タイプ設定無し";
		}
	}

	/**
	 * 出力データを追加します
	 * @param data 出力データ
	 */
	public void addOutput(HChartElement element) {
		this.outputs.add(element);
	}

	/**
	 * 入力データを追加します
	 * @param data 入力データ
	 */
	public void addInput(HChartElement element) {
		this.inputs.add(element);
	}

	/**
	 * 出力データを取得します
	 * @return 出力データ
	 */
	public List<HChartElement> getOutputs() {
		return Collections.unmodifiableList(this.outputs);
	}

	/**
	 * 入力データを取得します
	 * @return 入力データ
	 */
	public List<HChartElement> getInputs() {
		return Collections.unmodifiableList(this.inputs);
	}

	public int countIOSize() {
		return inputs.size() + outputs.size();
	}

	public int getIOIndex(HChartElement element) {
		int index;
		if ((index = inputs.indexOf(element)) != -1) {
			return index;
		} else if ((index = outputs.indexOf(element)) != -1) {
			return inputs.size() + index;
		} else {
//			assert false;
			throw new RuntimeException("assertion");
//			return -1;
		}
	}

	public boolean hasInput() {
		return !inputs.isEmpty();
	}

	public boolean hasOutput() {
		return !outputs.isEmpty();
	}

	/**
	 * @return Returns the reverse.
	 */
	public boolean isReverse() {
		return this.reverse;
	}
	/**
	 * @param reverse The reverse to set.
	 */
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public void reverse() {
		reverse = !reverse;
	}

	// ***** FOR PART OF DEBUG *****

	/**
	 * FOR DEBUG
	 */
	public void debugPrint(StringBuffer buffer, int depth) {
		startTag(buffer, depth);
		appendChildren(buffer, depth + 1);
		endTag(buffer, depth);
	}

	/**
	 * FOR DEBUG
	 */
	private void startTag(StringBuffer buffer, int depth) {
		for (int i = 0; i < depth; i++) {
			buffer.append("\t");
		}
		buffer.append("<" + getClass().getName());
		buffer.append(" text=\"" + getText() + "\"");
		buffer.append(super.getAttributes() + this.getAttributes());
		if (this.hasChildElement()) {
			buffer.append(">\n");
		} else {
			buffer.append("/>\n");
		}

	}

	/**
	 * FOR DEBUG
	 */
	private void appendChildren(StringBuffer buffer, int depth) {
		if (this.hasChildElement()) {
			this.getEnvironment().debugPrint(buffer, depth);
		}
	}

	/**
	 * FOR DEBUG
	 */
	private void endTag(StringBuffer buffer, int depth) {
		if (this.hasChildElement()) {
			for (int i = 0; i < depth; i++) {
				buffer.append("\t");
			}
			buffer.append("</" + getClass().getName() + ">\n");
		}
	}

	/**
	 * FOR DEBUG
	 */
	protected abstract String getAttributes();

}