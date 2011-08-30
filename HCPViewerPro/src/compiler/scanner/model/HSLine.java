/*
 * @(#)HSLine.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 字句解析した1行分の要素を格納するための読み取り専用（不変）オブジェクトです。
 * 
 * @author Manabu Sugiura
 * @version $Id: HSLine.java,v 1.7 2009/09/10 03:48:32 macchan Exp $
 */
public class HSLine {

	/** 要素 */
	private List<HSElement> elements = new ArrayList<HSElement>();

	/** 行番号 */
	private int lineNumber;

	/**
	 * 新しくオブジェクトを生成します。
	 * 
	 * @param elements
	 *               要素
	 * @param lineNumber
	 *               行番号
	 */
	public HSLine(List<HSElement> elements, int lineNumber) {
		this.elements.addAll(elements);
		for (Iterator<HSElement> i = elements.iterator(); i.hasNext();) {
			HSElement element = (HSElement) i.next();
			element.setParent(this);
		}
		this.lineNumber = lineNumber;
	}

	public ListIterator<HSElement> listIterator() {
		return elements.listIterator();
	}

	//	/**
	//	 * コマンドとテキストのペアの反復子を返します。
	//	 *
	//	 * @return コマンドとテキストのペアの反復子
	//	 */
	//	public HSPairIterator pairIterator() {
	//		return new HSPairIterator(this);
	//	}

	/**
	 * 行頭のコマンドを取得します
	 * @return 行頭のコマンド（コマンドがない場合はnull）
	 */
	public HSCommand getFirstCommand() {
		for (Iterator<HSElement> i = this.elements.iterator(); i.hasNext();) {
			HSElement element = (HSElement) i.next();
			if (element instanceof HSCommand) {
				return (HSCommand) element;
			}
		}
		return null;
	}

	/**
	 * 指定したindexの要素を取得します
	 * 
	 * @param index
	 *               取得する要素のindex
	 * @return 指定したindexの要素（要素がない場合はnull）
	 */
	public HSElement get(int index) {
		return (HSElement) this.elements.get(index);
	}

	/**
	 * 行番号を取得します。
	 * 
	 * @return 行番号
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}

	/**
	 * 行頭に連続するタブの個数を取得します
	 * 
	 * @return 行頭に連続するタブの個数
	 */
	public int getTabCount() {
		int tabCount = 0;
		for (int i = 0; i < this.elements.size(); i++) {
			HSElement element = (HSElement) this.elements.get(i);
			if (element instanceof HSTab) {
				tabCount++;
			} else {
				break;
			}
		}
		return tabCount;
	}

	/**
	 * 有効な行か判定します。 空行（要素がタブ+コメント、タブのみ、コメントのみ、行の内容が1つもない）の場合に無効と判定します
	 * 
	 * @return 有効な場合はtrue
	 */
	public boolean isValid() {
		return this.hasContents() && !this.hasTabAndCommentOnly();
	}

	/**
	 * 格納されている要素がタブとコメントのみかどうかを判定します。
	 * 
	 * @return 格納されている要素がタブとコメントのみの場合はtrue
	 */
	private boolean hasTabAndCommentOnly() {
		for (int i = 0; i < this.elements.size(); i++) {
			HSElement element = (HSElement) this.elements.get(i);
			if (!(element instanceof HSTab || element instanceof HSComment)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 格納されている要素があるかどうかを判定します。
	 * 
	 * @return 格納されている要素がある場合はtrue
	 */
	private boolean hasContents() {
		return !this.elements.isEmpty();
	}

	/**
	 * オブジェクトの文字列表現を返します。
	 * 
	 * @return このオブジェクトの文字列表現
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator<HSElement> i = this.elements.iterator(); i.hasNext();) {
			HSElement element = (HSElement) i.next();
			buffer.append(element.toString());
		}
		return buffer.toString();
	}

	/**
	 * デバッグ用の文字列表現を返します。
	 * 
	 * @return 要素の内容に含まれる全・半角スペース、タブを可視化したデバッグ用の文字列表現
	 */
	public String debugPrint() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator<HSElement> i = this.elements.iterator(); i.hasNext();) {
			HSElement element = (HSElement) i.next();
			buffer.append(element.debugPrint());
		}
		return buffer.toString();
	}

	//	/**
	//	 * コマンドとテキストのペアの反復子です。
	//	 *
	//	 * @author Manabu Sugiura
	//	 * @version $Id: HSLine.java,v 1.7 2009/09/10 03:48:32 macchan Exp $
	//	 */
	//	public class HSPairIterator {
	//
	//		/** 読み取り用開始位置カーソル */
	//		private int cursor = getTabCount();
	//
	//		/** 最初のコマンドが省略されているかどうか */
	//		private boolean omit = true;
	//
	//		/** 最初のペアの読み取りかどうか */
	//		private boolean firstRead = true;
	//
	//		/** 操作中の行 */
	//		private HSLine line = null;
	//
	//		/**
	//		 * 新しくオブジェクトを生成します。
	//		 */
	//		private HSPairIterator(HSLine line) {
	//			if (HSLine.this.elements.get(this.cursor) instanceof HSCommand) {
	//				this.omit = false;
	//			}
	//			this.line = line;
	//		}
	//
	//		/**
	//		 * 繰り返し処理でさらにペアがあるかを判定します。
	//		 *
	//		 * @return 反復子がさらにペアを持つ場合はtrue
	//		 */
	//		public boolean hasNext() {
	//			return this.cursor != HSLine.this.elements.size();
	//		}
	//
	//		/**
	//		 * 繰り返し処理で次のペアを返します。
	//		 *
	//		 * @return 繰り返し処理で次のペア
	//		 */
	//		public HSCommandUnit next() {
	//			HSCommand command = null;
	//			HSText text = null;
	//			if (this.omit && this.firstRead) { //最初の読み取りでコマンドが省略されている時
	//				text = (HSText) HSLine.this.elements.get(this.cursor); //最初の要素をテキストへ
	//				this.firstRead = false;
	//				this.cursor++; //コマンドがないので1だけ進める
	//			} else {
	//				command = (HSCommand) HSLine.this.elements.get(this.cursor);
	//				if (!(this.cursor + 1 >= HSLine.this.elements.size())) { //サイズオーバーを防ぐ
	//					HSElement next = (HSElement) HSLine.this.elements
	//							.get(this.cursor + 1);
	//					if (next instanceof HSText) {
	//						text = (HSText) next;
	//					}
	//					this.cursor += 2;
	//				} else {
	//					this.cursor = HSLine.this.elements.size();
	//				}
	//			}
	//			return new HSCommandUnit(command, text, this.line);
	//		}
	//
	//	}
	//
	//	/**
	//	 * コマンド単位（コマンド+引数）を表現します．
	//	 * <ul>
	//	 * <li>通常処理のコマンドが行頭で省略されている場合、コマンドを取得するとnullが返ります。</li>
	//	 * <li>引数なしは，</li>
	//	 * </ul>
	//	 *
	//	 * @author Manabu Sugiura
	//	 * @version $Id: HSLine.java,v 1.7 2009/09/10 03:48:32 macchan Exp $
	//	 */
	//	public class HSCommandUnit {
	//
	//
	//	}

}