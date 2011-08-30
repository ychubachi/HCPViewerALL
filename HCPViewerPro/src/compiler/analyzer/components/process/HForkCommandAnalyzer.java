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
 * @version $Id: HForkCommandAnalyzer.java,v 1.11 2005/04/11 12:35:01 macchan Exp $
 */
public class HForkCommandAnalyzer extends HAProcessCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HForkCommandAnalyzer() {
		super();
	}

	protected int getCreatedProcessType() {
		return HChartElement.CONDITION;
	}

}