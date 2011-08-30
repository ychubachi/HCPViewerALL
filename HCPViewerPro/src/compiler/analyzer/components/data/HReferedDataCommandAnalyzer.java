/*
 * @(#)HReferedDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.data;

import model.HChartElement;

/**
 * 非参照型データアナライザー
 * @author Manabu Sugiura
 * @version $Id: HReferedDataCommandAnalyzer.java,v 1.12 2004/11/25 05:00:47 gackt Exp $
 */
public class HReferedDataCommandAnalyzer extends HADataCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HReferedDataCommandAnalyzer() {
		super();
	}

	protected int getCreateDataType() {
		return HChartElement.REFERED;
	}

}