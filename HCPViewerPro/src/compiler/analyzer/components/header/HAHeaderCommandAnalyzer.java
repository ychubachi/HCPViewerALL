/*
 * @(#)HHeaderCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.header;

import model.HDocument;
import model.HElement;
import model.HEnvironment;

import compiler.analyzer.HALineCommandAnalyzer;
import compiler.scanner.model.HSText;

/**
 * 抽象ヘッダーコマンドアナライザー
 * @author Manabu Sugiura
 * @version $Id: HAHeaderCommandAnalyzer.java,v 1.1 2004/11/25 05:00:47 gackt Exp $
 */
public abstract class HAHeaderCommandAnalyzer extends HALineCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	protected HAHeaderCommandAnalyzer() {
		super();
	}

	public HElement analyze(HSText argument, HEnvironment env) {
		setHeader(env.getDocument(), argument.getContents()); //ドキュメントにコマンド内容を設定する
		return null;
	}

	protected abstract void setHeader(HDocument document, String headerText);

}