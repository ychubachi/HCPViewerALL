/*
 * @(#)HCallModuleCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.process;

import model.HElement;
import model.HEnvironment;
import model.HProcessElement;

import compiler.analyzer.HALineCommandAnalyzer;
import compiler.scanner.model.HSText;

/**
 * 抽象プロセスコマンドアナライザー
 * @author Manabu Sugiura
 * @version $Id: HAProcessCommandAnalyzer.java,v 1.1 2004/11/25 05:00:47 gackt Exp $
 */
public abstract class HAProcessCommandAnalyzer extends HALineCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HAProcessCommandAnalyzer() {
		super();
	}

	public HElement analyze(HSText argument, HEnvironment env) {
		String text = argument != null ? argument.getContents() : "";

		//elementを作成する
		HProcessElement element = new HProcessElement(getCreatedProcessType());
		element.setText(text);

		if (env.isTopLevel()) {
			//最上位プロセスであり，モジュールIDが設定されていない場合，最上位プロセスのテキストをモジュールのIDとする
			if (env.getModule().getId() == null) {
				env.getModule().setId(text);
			}

			env.getModule().setText(text);
		}

		return element;
	}

	protected abstract int getCreatedProcessType();

}