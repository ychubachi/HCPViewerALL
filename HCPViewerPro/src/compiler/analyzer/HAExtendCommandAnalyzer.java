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
 * 拡張コマンドの抽象アナライザー
 * ・拡張コマンドは\note等のコマンドとテキスト（引数）の後に続くコマンドです
 * @author Manabu Sugiura
 * @version $Id: HAExtendCommandAnalyzer.java,v 1.1 2004/11/25 05:00:46 gackt Exp $
 */
public abstract class HAExtendCommandAnalyzer implements HCommandAnalyzer {

	/***********************
	 * クラス変数
	 ***********************/

	protected static final HCompileLogger logger = HCompileLogger.getInstance();

	/**
	 * コンストラクタ
	 */
	public HAExtendCommandAnalyzer() {
		super();
	}

	public abstract void analyze(HSText arg, HElement element, HEnvironment env);

}