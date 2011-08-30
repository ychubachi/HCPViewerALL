/*
 * @(#)HCallModuleCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.process;

import model.HChartElement;

/**
 * メソッド呼び出し型プロセスコマンドアナライザー
 * @author Manabu Sugiura
 * @version $Id: HCallModuleCommandAnalyzer.java,v 1.11 2004/11/25 05:00:47 gackt Exp $
 */
public class HCallModuleCommandAnalyzer extends HAProcessCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HCallModuleCommandAnalyzer() {
		super();
	}

	protected int getCreatedProcessType() {
		return HChartElement.CALL_MODULE;
	}

}