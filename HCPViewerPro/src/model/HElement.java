/*
 * @(#)HElement.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * コメントを付加できるチャート要素（処理、データ、条件）を表現するためのクラス
 * 
 * @author Manabu Sugiura
 * @version $Id: HElement.java,v 1.13 2009/09/10 03:48:31 macchan Exp $
 */
public abstract class HElement {

	private String text = "";

	private List<HNote> notes = new ArrayList<HNote>();
	private HEnvironment environment = new HEnvironment(this);

	private HEnvironment parentEnvironment = null;

	/***************************************************************************
	 * ノート
	 **************************************************************************/

	/**
	 * ノートを取得します
	 * 
	 * @return ノート
	 */
	public List<HNote> getNotes() {
		return Collections.unmodifiableList(this.notes);
	}

	/**
	 * ノートを追加します
	 * 
	 * @param note
	 *            ノート
	 */
	public void addNote(HNote note) {
		this.notes.add(note);
	}

	/***************************************************************************
	 * テキスト
	 **************************************************************************/

	/**
	 * テキストを取得します
	 * 
	 * @return テキスト
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * テキストを設定します
	 * 
	 * @param text
	 *            テキスト
	 */
	public void setText(String text) {
		this.text = text;
	}

	/***************************************************************************
	 * 子環境
	 **************************************************************************/

	/**
	 * 子の環境を取得します
	 * 
	 * @return parentEnvironment 親の環境
	 */
	public HEnvironment getEnvironment() {
		return this.environment;
	}

	/**
	 * 子を持っているか判定します
	 * 
	 * @return 子を持っている場合はtrue
	 */
	public boolean hasChildElement() {
		return this.environment.hasChildElement();
	}

	/***************************************************************************
	 * 親環境
	 **************************************************************************/

	/**
	 * 親の環境を取得します
	 * 
	 * @return parentEnvironment 親の環境
	 */
	public HEnvironment getParentEnvironment() {
		return this.parentEnvironment;
	}

	/**
	 * 親の環境を設定します
	 * 
	 * @param parentEnvironment
	 *            親の環境
	 */
	protected void setParentEnvironment(HEnvironment parentEnvironment) {
		this.parentEnvironment = parentEnvironment;
	}

	/***************************************************************************
	 * 取得
	 **************************************************************************/

	/**
	 * ドキュメントを取得します
	 * 
	 * @return ドキュメント
	 */
	public HDocument getDocument() {
		if (parentEnvironment != null) {
			this.parentEnvironment.getDocument();
		}
		return null;
	}

	/**
	 * モジュールを取得します
	 * 
	 * @return モジュール
	 */
	public HModule getModule() {
		// if (parentEnvironment != null) {
		return this.parentEnvironment.getModule();
		// }
		// return null;
	}

	/***************************************************************************
	 * すべて取得系
	 **************************************************************************/

	/**
	 * @return
	 */
	public List<HProcessElement> getAllProcessElements() {
		return getEnvironment().getAllProcessElements();
	}

	/**
	 * @return
	 */
	public List<HDataElement> getAllDataElements() {
		return getEnvironment().getAllDataElements();
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
		buffer.append(this.getAttributes());
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
	protected String getAttributes() {
		StringBuffer buffer = new StringBuffer();
		if (!this.notes.isEmpty()) {
			buffer.append(" note=\"");
			for (int i = 0; i < this.notes.size(); i++) {
				buffer.append(((HNote) this.notes.get(i)).getContents());
				if (i != this.notes.size() - 1) {
					buffer.append(" ,");
				}
			}
			buffer.append("\"");
		}
		return buffer.toString();
	}

}