/*
 * @(#)HNote.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

/**
 * ノートを表現するためのクラスです
 * @author Manabu Sugiura
 * @version $Id: HNote.java,v 1.8 2004/11/22 19:18:11 gackt Exp $
 */
public class HNote {

	private String contents;
	private HElement parent;

	/**
	 * コンストラクタ
	 * @param contents ノートの内容
	 * @param index ノートのインデックス
	 */
	public HNote(String contents) {
		this.contents = contents;
	}

	/**
	 * ノートの内容を取得します
	 * @return ノートの内容
	 */
	public String getContents() {
		return this.contents;
	}

	public void setParent(HElement parent) {
		this.parent = parent;
	}

	public HElement getParent() {
		return this.parent;
	}

}