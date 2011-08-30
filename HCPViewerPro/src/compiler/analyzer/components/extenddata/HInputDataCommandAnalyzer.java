/*
 * @(#)HInputDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.extenddata;

import model.HDataElement;
import model.HProcessElement;

/**
 * 入力データアナライザー
 * @author Manabu Sugiura
 * @version $Id: HInputDataCommandAnalyzer.java,v 1.4 2004/11/25 05:00:47 gackt Exp $
 */
public class HInputDataCommandAnalyzer extends HAExtendDataCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HInputDataCommandAnalyzer() {
		super();
	}

	protected void link(HProcessElement process, HDataElement data) {
		process.addInput(data);
		data.addOutput(process);
	}

	protected String getCommandTypeString() {
		return "データ入力コマンド";
	}

}