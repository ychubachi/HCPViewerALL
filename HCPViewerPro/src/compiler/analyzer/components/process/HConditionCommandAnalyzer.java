/*
 * @(#)HCallModuleCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.process;

import model.HChartElement;

/**
 * 条件型プロセスコマンドアナライザー
 * @author Manabu Sugiura
 * @version $Id: HConditionCommandAnalyzer.java,v 1.4 2005/04/11 12:18:55 macchan Exp $
 */
public class HConditionCommandAnalyzer extends HAProcessCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HConditionCommandAnalyzer() {
		super();
	}

	protected int getCreatedProcessType() {
		return HChartElement.CONDITION;
	}

}