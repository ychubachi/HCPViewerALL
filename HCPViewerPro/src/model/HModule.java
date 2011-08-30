/*
 * @(#)HModule.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * モジュールを表現するためのクラス
 * 
 * @author Manabu Sugiura
 * @version $Id: HModule.java,v 1.19 2009/09/10 03:48:31 macchan Exp $
 */
public class HModule extends HElement {

	public static final String DEFAULT_NAME = "default";
	/**********************************
	 * インスタンス変数
	 ***********************************/

	private HDocument document = null;
	private String id = DEFAULT_NAME;// データだけのものではIDが付加されず，nullpointerになるので，デフォルトを規定する．1.3.2

	/**********************************
	 * コンストラクタ
	 ***********************************/

	/**
	 * コンストラクタ
	 * 
	 * @param document
	 *            所属するドキュメント
	 */
	public HModule(HDocument document) {
		this.document = document;
	}

	/**********************************
	 * Document系
	 ***********************************/

	/**
	 * @return Returns the document.
	 */
	public HDocument getDocument() {
		return document;
	}

	/**********************************
	 * プロパティ取得系
	 ***********************************/

	/**
	 * タイトルを取得します
	 * 
	 * @return タイトル
	 */
	public String getTitle() {
		return getDocument().getTitle();
	}

	/**
	 * 作成日時を取得します
	 * 
	 * @return 作成日
	 */
	public String getDate() {
		return getDocument().getDate();
	}

	/**
	 * バージョンを取得します
	 * 
	 * @return バージョン
	 */
	public String getVersion() {
		return getDocument().getVersion();
	}

	/**
	 * 作成者を取得します
	 * 
	 * @return 作成者
	 */
	public String getAuthor() {
		return getDocument().getAuthor();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public boolean isDefaultID() {
		return DEFAULT_NAME.equals(getId());
	}

	/**********************************
	 * 公開メソッド
	 ***********************************/

	/**
	 * ルートプロセスが一つかどうか判定します
	 * 
	 * @return ルートプロセスが一つだけの場合はtrue
	 */
	public boolean hasOnlyOneRootProcess() {
		return this.getEnvironment().getProcessElements().size() == 1;
	}

	/**
	 * ルートプロセスを取得します
	 * 
	 * @return ルートプロセス
	 */
	public HProcessElement getRootProcess() {
		if (hasOnlyOneRootProcess()) {
			return (HProcessElement) getEnvironment().getProcessElements().get(
					0);
		}
		return null;
	}

	public List<HNote> getNotes() {
		List<HNote> notes = new ArrayList<HNote>();

		// 自分のノートを追加
		notes.addAll(super.getNotes());

		// 子のノートを追加
		notes.addAll(getNotes(getAllProcessElements()));// プロセス
		notes.addAll(getNotes(getAllDataElements()));// データ

		return notes;
	}

	private List<HNote> getNotes(List<? extends HElement> elements) {
		List<HNote> allNotes = new ArrayList<HNote>();
		for (Iterator<? extends HElement> i = elements.iterator(); i.hasNext();) {
			HElement element = (HElement) i.next();
			allNotes.addAll(element.getNotes());
		}
		return allNotes;
	}

	// ***** FOR PART OF DEBUG *****

	/**
	 * FOR DEBUG
	 */
	protected String getAttributes() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" name=\"");
		buffer.append(this.getText() + "\"");
		return buffer.toString();
	}

}