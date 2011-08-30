/*
 * @(#)HModuleCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components;

import model.HElement;
import model.HEnvironment;

import compiler.analyzer.HALineCommandAnalyzer;
import compiler.scanner.model.HSText;

/**
 * モジュールコマンドアナライザー
 * @author Manabu Sugiura
 * @version $Id: HModuleCommandAnalyzer.java,v 1.10 2004/11/25 05:00:47 gackt Exp $
 */
public class HModuleCommandAnalyzer extends HALineCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HModuleCommandAnalyzer() {
		super();
	}

	public HElement analyze(HSText argument, HEnvironment env) {
		String text = argument != null ? argument.getContents() : null;
		env.getModule().setId(text);
		return null;
	}

}