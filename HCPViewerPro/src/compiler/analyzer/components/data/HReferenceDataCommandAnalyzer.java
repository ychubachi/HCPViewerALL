/*
 * @(#)HReferenceDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.data;

import model.HChartElement;

/**
 * 参照型データアナライザー
 * @author Manabu Sugiura
 * @version $Id: HReferenceDataCommandAnalyzer.java,v 1.12 2004/11/25 05:00:47 gackt Exp $
 */
public class HReferenceDataCommandAnalyzer extends HADataCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HReferenceDataCommandAnalyzer() {
		super();
	}

	protected int getCreateDataType() {
		return HChartElement.REFERENCE;
	}

}