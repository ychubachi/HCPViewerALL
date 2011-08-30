/*
 * @(#)HOutputDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.extenddata;

import model.HDataElement;
import model.HProcessElement;

/**
 * 出力データアナライザー
 * @author Manabu Sugiura
 * @version $Id: HOutputDataCommandAnalyzer.java,v 1.4 2004/11/25 05:00:47 gackt Exp $
 */
public class HOutputDataCommandAnalyzer extends HAExtendDataCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HOutputDataCommandAnalyzer() {
		super();
	}

	protected void link(HProcessElement process, HDataElement data) {
		process.addOutput(data);
		data.addInput(process);
	}

	protected String getCommandTypeString() {
		return "データ出力コマンド";
	}

}