/*
 * @(#)HDateCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.header;

import model.HDocument;

/**
 * 作成日時コマンドアナライザー
 * @author Manabu Sugiura
 * @version $Id: HDateCommandAnalyzer.java,v 1.11 2004/11/25 05:00:47 gackt Exp $
 */
public class HDateCommandAnalyzer extends HAHeaderCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HDateCommandAnalyzer() {
		super();
	}

	protected void setHeader(HDocument document, String headerText) {
		document.setDate(headerText);
	}

}