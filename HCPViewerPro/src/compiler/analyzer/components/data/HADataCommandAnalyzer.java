/*
 * @(#)HInputDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.data;

import model.HDataElement;
import model.HElement;
import model.HEnvironment;

import compiler.analyzer.HALineCommandAnalyzer;
import compiler.scanner.model.HSText;

/**
 * 抽象データアナライザー
 * @author Manabu Sugiura
 * @version $Id: HADataCommandAnalyzer.java,v 1.1 2004/11/25 05:00:47 gackt Exp $
 */
public abstract class HADataCommandAnalyzer extends HALineCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HADataCommandAnalyzer() {
		super();
	}

	public HElement analyze(HSText argument, HEnvironment env) {
		String text = argument != null ? argument.getContents() : "";

		HDataElement element = new HDataElement(this.getCreateDataType());
		element.setText(text);

		return element;
	}

	protected abstract int getCreateDataType();

}