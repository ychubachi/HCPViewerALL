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
 * @version $Id: HSelectCommandAnalyzer.java,v 1.1 2005/04/11 12:18:55 macchan Exp $
 */
public class HSelectCommandAnalyzer extends HAProcessCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HSelectCommandAnalyzer() {
		super();
	}

	protected int getCreatedProcessType() {
		return HChartElement.SELECT;
	}

}