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
 * ���ۓ��o�̓f�[�^�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HAExtendDataCommandAnalyzer.java,v 1.2 2009/09/10 03:48:33 macchan Exp $
 */
public abstract class HAExtendDataCommandAnalyzer
		extends
			HAExtendCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HAExtendDataCommandAnalyzer() {
		super();
	}

	/***********************
	 * ��͊֘A
	 ***********************/

	public void analyze(HSText arg, HElement element, HEnvironment env) {

		if (!(element instanceof HProcessElement)) { //�v���Z�X�ȊO�ɋL�q����Ă���
			showInvalidCommandPositionMessage(arg.getParent().getLineNumber());
			return;
		}

		//�f�[�^����͂���
		String text = arg != null ? arg.getContents() : "";

		List<HSData> dataList = scanDataList(text, arg.getParent().getLineNumber());
		for (Iterator<HSData> i = dataList.iterator(); i.hasNext();) {
			HSData scanedData = (HSData) i.next();
			String scanedDataText = scanedData.getText();

			//�f�[�^���쐬����
			HDataElement data = getData(scanedDataText, env);

			//�f�[�^�������N����
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
		HDataElement data = new HDataElement(HChartElement.BASIS);//�v���Z�X�s���Ő錾�������̂͂��ׂ�Basic�^�C�v
		data.setText(name);
		env.addChild(data);
		return data;
	}

	/***********************
	 * �G���[����
	 ***********************/

	private void showInvalidCommandPositionMessage(int lineNumber) {
		String message = getCommandTypeString() + "�̓v���Z�X�ɑ΂��Ă̂݋L�q�ł��܂�";
		logger.showWarningMessage(message, lineNumber);
	}

	protected abstract String getCommandTypeString();

}