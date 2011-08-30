/*
 * @(#)HEnvironment.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 環境を表現するためのクラス
 * 
 * @author Manabu Sugiura
 * @version $Id: HEnvironment.java,v 1.19 2009/09/10 03:48:31 macchan Exp $
 */
public class HEnvironment {

	/*********************************
	 * インスタンス変数
	 *********************************/

	private List<HProcessElement> processElements = new ArrayList<HProcessElement>();
	private List<HDataElement> dataElements = new ArrayList<HDataElement>();

	private HElement parentElement = null;

	/*********************************
	 * コンストラクタ
	 *********************************/

	/**
	 * コンストラクタ
	 * @param parentElement 親要素
	 */
	public HEnvironment(HElement parentElement) {
		this.parentElement = parentElement;
	}

	/*********************************
	 * プロセス　＆　データ　操作
	 *********************************/

	/**
	 * 子要素を追加します
	 * @param element 子要素
	 */
	public void addChild(HElement element) {
		if (element.getParentEnvironment() != null) {
			throw new IllegalArgumentException("既に親がいます");
		}

		//追加
		if (element instanceof HProcessElement) {
			this.processElements.add((HProcessElement)element);
			element.setParentEnvironment(this);
		} else if (element instanceof HDataElement) {
			this.dataElements.add((HDataElement)element);
			element.setParentEnvironment(this);
		} else {
			throw new IllegalArgumentException(element.toString());
		}
	}

	/**
	 * プロセスを取得します
	 * @return プロセス
	 */
	public List<HProcessElement> getProcessElements() {
		return Collections.unmodifiableList(this.processElements);
	}

	/**
	 * データを取得します
	 * @return データ
	 */
	public List<HDataElement> getDataElements() {
		return Collections.unmodifiableList(this.dataElements);
	}

	/**
	 * 全てのデータを再帰的に取得します
	 * @param allDataElements 全てのデータを格納するリスト
	 */
	public List<HDataElement> getAllDataElements() {
		List<HDataElement> allDataElements = new ArrayList<HDataElement>();

		allDataElements.addAll(this.getDataElements());
		for (int i = 0; i < dataElements.size(); i++) {
			HDataElement data = (HDataElement) dataElements.get(i);
			HEnvironment child = data.getEnvironment();
			allDataElements.addAll(child.getAllDataElements());
		}
		return allDataElements;
	}

	/**
	 * 全てのプロセスを再帰的に取得します
	 * @param allDataElements 全てのプロセスを格納するリスト
	 */
	public List<HProcessElement> getAllProcessElements() {
		List<HProcessElement> allProcessElements = new ArrayList<HProcessElement>();

		allProcessElements.addAll(this.getProcessElements());
		for (int i = 0; i < processElements.size(); i++) {
			HProcessElement process = (HProcessElement) processElements.get(i);
			HEnvironment child = process.getEnvironment();
			allProcessElements.addAll(child.getAllProcessElements());
		}
		return allProcessElements;
	}

	/**
	 * 指定した名前のデータを検索します
	 * @param name 名前
	 * @return 指定した名前のデータ
	 */
	public HDataElement searchData(String name) {
		//この環境で検索する
		HDataElement data = this.searchDataInternal(name);

		//見つからない場合は，上位環境を検索する
		if (data != null) {//見つかった
			return data;
		} else if (getParent() == null) {
			return null;
		} else {
			return getParent().searchData(name);
		}
	}

	/**
	 * データの名前からデータを取得します
	 * @param name データの名前
	 * @return データ（宣言がない場合はnull）
	 */
	private HDataElement searchDataInternal(String name) {
		List<HDataElement> allData = getAllDataElements();
		for (Iterator<HDataElement> i = allData.iterator(); i.hasNext();) {
			HDataElement data = i.next();
			if (data.getText().equals(name)) {
				return data;
			}
		}
		return null;
	}

	/*********************************
	 * 公開操作
	 *********************************/

	/**
	 * 親要素を取得します
	 * @param 親要素
	 */
	public HElement getParentElement() {
		return this.parentElement;
	}

	/**
	 * 所属するモジュールを取得します
	 * @return 所属するモジュール
	 */
	public HModule getModule() {
		if (isTopLevel()) {
			return (HModule) this.parentElement;
		}

		return this.getParent().getModule();
	}

	/**
	 * 所属するドキュメントを取得します
	 * @return 所属するドキュメント
	 */
	public HDocument getDocument() {
		return getModule().getDocument();
	}

	/**
	 * 階層レベルを取得します。
	 * @return 階層レベル
	 */
	public int getLevel() {
		if (this.isTopLevel()) {
			return 0;
		}

		//条件型のブランチの場合はレベルをカウントしない
		if (this.isBranchModule()) {
			return this.getParent().getLevel();
		}

		return this.getParent().getLevel() + 1;
	}

	/**
	 * @return
	 */
	public HEnvironment getParent() {
		return getParentElement().getParentEnvironment();
	}

	/**
	 * @return
	 */
	public boolean isTopLevel() {
		return getParent() == null;
	}

	private boolean isBranchModule() {
		if ((this.parentElement instanceof HChartElement)
				&& ((HChartElement) this.parentElement).getType() == HChartElement.CONDITION) {
			return true;
		}
		return false;
	}

	/**
	 * @return
	 */
	public boolean hasChildElement() {
		return !processElements.isEmpty() || !dataElements.isEmpty();
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
		buffer.append(getAttributes());

		if (this.processElements.isEmpty() && this.dataElements.isEmpty()) {
			buffer.append("/>\n");
		} else {
			buffer.append(">\n");
		}
	}

	/**
	 * FOR DEBUG
	 */
	protected String getAttributes() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(" level=\"");
		buffer.append(getLevel() + "\"");

		buffer.append(" hash=\"");
		buffer.append(hashCode() + "\"");

		return buffer.toString();
	}

	/**
	 * FOR DEBUG
	 */
	private void appendChildren(StringBuffer buffer, int depth) {
		//Data
		for (int i = 0; i < this.dataElements.size(); i++) {
			HChartElement element = (HChartElement) this.dataElements.get(i);
			element.debugPrint(buffer, depth);
		}

		//Process
		for (Iterator<HProcessElement> i = this.processElements.iterator(); i.hasNext();) {
			HProcessElement processElement = i.next();
			processElement.debugPrint(buffer, depth);

		}
	}

	/**
	 * FOR DEBUG
	 */
	private void endTag(StringBuffer buffer, int depth) {
		if (!this.processElements.isEmpty() || !this.dataElements.isEmpty()) {
			for (int i = 0; i < depth; i++) {
				buffer.append("\t");
			}
			buffer.append("</" + getClass().getName() + ">\n");
		}
	}

}