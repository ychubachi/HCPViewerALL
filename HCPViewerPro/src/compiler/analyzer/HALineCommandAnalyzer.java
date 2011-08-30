/*
 * @(#)HCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer;

import model.HElement;
import model.HEnvironment;

import compiler.HCompileLogger;
import compiler.scanner.model.HSText;

/**
 * 通常コマンド抽象アナライザー
 * @author Manabu Sugiura
 * @version $Id: HALineCommandAnalyzer.java,v 1.1 2004/11/25 05:00:46 gackt Exp $
 */
public abstract class HALineCommandAnalyzer implements HCommandAnalyzer {

	/***********************
	 * クラス変数
	 ***********************/

	protected static final HCompileLogger logger = HCompileLogger.getInstance();

	/**
	 * コンストラクタ
	 */
	public HALineCommandAnalyzer() {
		super();
	}

	public abstract HElement analyze(HSText arg, HEnvironment env);

}