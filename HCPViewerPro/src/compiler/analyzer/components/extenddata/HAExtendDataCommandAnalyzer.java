/*
 * @(#)HInputDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.extenddata;

import java.util.Iterator;
import java.util.List;

import model.HChartElement;
import model.HDataElement;
import model.HElement;
import model.HEnvironment;
import model.HProcessElement;

import compiler.analyzer.HAExtendCommandAnalyzer;
import compiler.analyzer.components.extenddata.datascanner.HDataListScanner;
import compiler.analyzer.components.extenddata.datascanner.HSData;
import compiler.scanner.model.HSText;

/**
 * 抽象入出力データアナライザー
 * @author Manabu Sugiura
 * @version $Id: HAExtendDataCommandAnalyzer.java,v 1.2 2009/09/10 03:48:33 macchan Exp $
 */
public abstract class HAExtendDataCommandAnalyzer
		extends
			HAExtendCommandAnalyzer {

	/**
	 * コンストラクタ
	 */
	public HAExtendDataCommandAnalyzer() {
		super();
	}

	/***********************
	 * 解析関連
	 ***********************/

	public void analyze(HSText arg, HElement element, HEnvironment env) {

		if (!(element instanceof HProcessElement)) { //プロセス以外に記述されている
			showInvalidCommandPositionMessage(arg.getParent().getLineNumber());
			return;
		}

		//データを解析する
		String text = arg != null ? arg.getContents() : "";

		List<HSData> dataList = scanDataList(text, arg.getParent().getLineNumber());
		for (Iterator<HSData> i = dataList.iterator(); i.hasNext();) {
			HSData scanedData = (HSData) i.next();
			String scanedDataText = scanedData.getText();

			//データを作成する
			HDataElement data = getData(scanedDataText, env);

			//データをリンクする
			link((HProcessElement) element, data);
		}
	}

	protected abstract void link(HProcessElement process, HDataElement data);

	private List<HSData> scanDataList(String s, int lineNumber) {
		HDataListScanner scanner = new HDataListScanner();

		for (int i = 0; i < s.length(); i++) {
			scanner.receive(s.charAt(i), lineNumber);
		}
		scanner.receive(HDataListScanner.EOL, lineNumber);

		return scanner.getDataList();
	}

	private HDataElement getData(String text, HEnvironment env) {
		HDataElement data;
		data = env.searchData(text);
		if (data == null) {
			data = createNewData(text, env);
		}
		return data;
	}

	private HDataElement createNewData(String name, HEnvironment env) {
		HDataElement data = new HDataElement(HChartElement.BASIS);//プロセス行内で宣言されるものはすべてBasicタイプ
		data.setText(name);
		env.addChild(data);
		return data;
	}

	/***********************
	 * エラー処理
	 ***********************/

	private void showInvalidCommandPositionMessage(int lineNumber) {
		String message = getCommandTypeString() + "はプロセスに対してのみ記述できます";
		logger.showWarningMessage(message, lineNumber);
	}

	protected abstract String getCommandTypeString();

}