/*
 * @(#)HDocument.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ドキュメントを表現するためのクラス
 * 
 * @author Manabu Sugiura
 * @version $Id: HDocument.java,v 1.15 2009/09/10 03:48:31 macchan Exp $
 */
public class HDocument {

	/*********************************
	 * クラス変数
	 *********************************/

	public static final String DEFAULT_TITLE = "新規HCPチャート";
	public static final String DEFAULT_DATE = Calendar.getInstance().getTime()
			.toString();
	public static final String DEFAULT_AUTHOR = System.getProperty("user.name");
	public static final String DEFAULT_VERSION = "1.0";

	/*********************************
	 * インスタンス変数
	 *********************************/

	// ファイル名
	private String filename = null;
	
	// ヘッダー
	private String title = DEFAULT_TITLE;
	private String date = DEFAULT_DATE;
	private String author = DEFAULT_AUTHOR;
	private String version = DEFAULT_VERSION;

	private Map<String, HModule> modules = new LinkedHashMap<String, HModule>();

	/*********************************
	 * 公開メソッド
	 *********************************/
	
	/**
	 * ファイル名を取得します
	 */
	public String getFilename(){
		return this.filename;
	}
	
	/**
	 * ファイル名を設定します
	 * @param filename
	 */
	public void setFilename(String filename){
		this.filename = filename;
	}

	/**
	 * モジュールを追加します
	 * 
	 * @param module
	 *            モジュール
	 */
	public void addModule(HModule module) {
		// Dupulicate Check
		setNonDupulicateModuleId(module);

		// Put
		this.modules.put(module.getId(), module);
	}

	private void setNonDupulicateModuleId(HModule module) {
		String idBase = module.getId();
		for (int counter = 1; getModule(module.getId()) != null; counter++) {
			module.setId(idBase + "<" + counter + ">");
		}
	}

	/**
	 * モジュールを取得します
	 * 
	 * @return モジュール
	 */
	public List<HModule> getModules() {
		return new ArrayList<HModule>(modules.values());
	}

	/**
	 * ドキュメント内の全てのプロセスを取得します
	 * 
	 * @return ドキュメント内の全てのプロセス
	 */
	public List<HProcessElement> getAllProcessElements() {
		List<HProcessElement> allProcessElements = new ArrayList<HProcessElement>();
		List<HModule> modules = getModules();
		for (int i = 0; i < modules.size(); i++) {
			HModule module = modules.get(i);
			allProcessElements.addAll(module.getAllProcessElements());
		}
		return allProcessElements;
	}

	/**
	 * 名前からモジュールを取得します
	 * 
	 * @param moduleId
	 *            取得するモジュールの名前
	 * @return モジュール（見つからない場合はnull）
	 */
	public HModule getModule(String moduleId) {
		return (HModule) modules.get(moduleId);
	}

	public boolean hasDuplicateModuleId() {
		// for (int i = 0; i < this.modules.size(); i++) {
		// HModule one = (HModule) this.modules.get(i);
		// for (int j = 0; j < this.modules.size(); j++) {
		// HModule another = (HModule) this.modules.get(j);
		// if (one != another && one.getId().equals(another.getId())) {
		// return true;
		// }
		// }
		// }
		return false;
	}

	/**
	 * タイトルを取得します
	 * 
	 * @return タイトル
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * タイトルを設定します
	 * 
	 * @param title
	 *            タイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 作成日時を取得します
	 * 
	 * @return 作成日
	 */
	public String getDate() {
		return this.date;
	}

	/**
	 * 作成日時を設定します
	 * 
	 * @param date
	 *            作成日時
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * バージョンを取得します
	 * 
	 * @return バージョン
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * バージョンを設定します
	 * 
	 * @param version
	 *            バージョン
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 作成者を取得します
	 * 
	 * @return 作成者
	 */
	public String getAuthor() {
		return this.author;
	}

	/**
	 * 作成者を設定します
	 * 
	 * @param author
	 *            作成者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	// ***** FOR PART OF DEBUG *****

	/**
	 * FOR DEBUG
	 */
	public void debugPrint(StringBuffer buffer) {
		buffer.append("<?xml version='1.0' encoding='Shift_JIS' ?>\n");

		startTag(buffer);
		appendModules(buffer);
		endTag(buffer);
	}

	/**
	 * FOR DEBUG
	 */
	private void startTag(StringBuffer buffer) {
		buffer.append("<" + getClass().getName());
		buffer.append(getAttributes());
		if (!this.modules.isEmpty()) {
			buffer.append(">\n");
		} else {
			buffer.append("/>\n");
		}
	}

	/**
	 * FOR DEBUG
	 */
	private void appendModules(StringBuffer buffer) {
		List<HModule> modules = getModules();
		for (int i = 0; i < modules.size(); i++) {
			HModule module = modules.get(i);
			module.debugPrint(buffer, 1);
		}
	}

	/**
	 * FOR DEBUG
	 */
	private void endTag(StringBuffer buffer) {
		if (!this.modules.isEmpty()) {
			buffer.append("</" + getClass().getName() + ">\n");
		}
	}

	/**
	 * FOR DEBUG
	 */
	protected String getAttributes() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" title=\"" + this.title + "\"");
		buffer.append(" date=\"" + this.date + "\"");
		buffer.append(" author=\"" + this.author + "\"");
		buffer.append(" version=\"" + this.version + "\"");
		return buffer.toString();
	}

}