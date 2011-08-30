/*
 * @(#)HInputDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.extenddata;

import model.HChartElement;
import model.HElement;
import model.HEnvironment;

import compiler.analyzer.HAExtendCommandAnalyzer;
import compiler.scanner.model.HSText;

/**
 * 抽象入出力データアナライザー
 * @author Manabu Sugiura
 * @version $Id: HExtendReverseCommandAnalyzer.java,v 1.1 2004/12/03 05:07:36 macchan Exp $
 */
public class HExtendReverseCommandAnalyzer extends HAExtendCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HExtendReverseCommandAnalyzer() {
		super();
	}

	/***********************
	 * 解析関連
	 ***********************/

	public void analyze(HSText arg, HElement element, HEnvironment env) {

		if (!(element instanceof HChartElement)) { //プロセス,データ以外に記述されている
			showInvalidCommandPositionMessage(arg.getParent().getLineNumber());
			return;
		}

		((HChartElement) element).reverse();
	}

	/***********************
	 * エラー処理
	 ***********************/

	private void showInvalidCommandPositionMessage(int lineNumber) {
		String message = "入出力位置逆転命令はプロセス，データに対してのみ記述できます";
		logger.showWarningMessage(message, lineNumber);
	}

}