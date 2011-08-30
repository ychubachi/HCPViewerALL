/*
 * @(#)HRepeatDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.data;

import model.HChartElement;

/**
 * 繰り返し型データアナライザー
 * @author Manabu Sugiura
 * @version $Id: HSpaceDataCommandAnalyzer.java,v 1.1 2004/12/03 05:07:36 macchan Exp $
 */
public class HSpaceDataCommandAnalyzer extends HADataCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HSpaceDataCommandAnalyzer() {
		super();
	}

	protected int getCreateDataType() {
		return HChartElement.SPACE;
	}

}