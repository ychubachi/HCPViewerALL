/*
 * @(#)HSelectDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.data;

import model.HChartElement;

/**
 * 選択型データアナライザー
 * @author Manabu Sugiura
 * @version $Id: HSelectDataCommandAnalyzer.java,v 1.1 2005/04/11 12:18:55 macchan Exp $
 */
public class HSelectDataCommandAnalyzer extends HADataCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HSelectDataCommandAnalyzer() {
		super();
	}

	protected int getCreateDataType() {
		return HChartElement.SELECT;
	}

}