/*
 * @(#)HSLineList.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 字句解析した1行分の要素を複数格納するための読み取り専用（不変）オブジェクトです。
 * @author Manabu Sugiura
 * @version $Id: HSLineList.java,v 1.7 2009/09/10 03:48:32 macchan Exp $
 */
public class HSLineList {

	/** 行 */
	private List<HSLine> lines = new ArrayList<HSLine>();

	/**
	 * 新しくオブジェクトを生成します。
	 */
	public HSLineList() {
		super();
	}

	/**
	 * 行を追加します。
	 * @param line 追加する行
	 */
	public void add(HSLine line) {
		this.lines.add(line);
	}

	/**
	 * リストに行がない場合にtrueを返します。
	 * @return リストが行を1つも保持していない場合はtrue
	 */
	public boolean isEmpty() {
		return this.lines.isEmpty();
	}

	/**
	 * リスト内の行を適切な順序で繰り返し処理する反復子を返します。
	 * @return リスト内の行を適切な順序で繰り返し処理する反復子
	 */
	public ListIterator<HSLine> listIterator() {
		return this.lines.listIterator();
	}

	public HSLineList subList(int fromIndex, int toIndex) {
		HSLineList lineList = new HSLineList();
		for (int i = fromIndex; i < toIndex; i++) {
			lineList.add((HSLine) this.lines.get(i));
		}
		return lineList;
	}

	public int size() {
		return this.lines.size();
	}

	/**
	 * オブジェクトの文字列表現を返します。
	 * @return このオブジェクトの文字列表現
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator<HSLine> i = this.lines.iterator(); i.hasNext();) {
			HSLine line = (HSLine) i.next();
			buffer.append(line.toString());
			if (i.hasNext()) {
				buffer.append(System.getProperty("line.separator"));
			}
		}
		return buffer.toString();
	}

	/**
	 * デバッグ用の文字列表現を返します。
	 * @return 要素の内容に含まれる全・半角スペース、タブを可視化したデバッグ用の文字列表現
	 */
	public String debugPrint() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator<HSLine> i = this.lines.iterator(); i.hasNext();) {
			HSLine line = (HSLine) i.next();
			buffer.append(line.debugPrint());
			if (i.hasNext()) {
				buffer.append(System.getProperty("line.separator"));
			}
		}
		return buffer.toString();
	}

}