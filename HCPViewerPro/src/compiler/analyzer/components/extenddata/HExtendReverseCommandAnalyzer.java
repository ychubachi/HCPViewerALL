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
 * ���ۓ��o�̓f�[�^�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HExtendReverseCommandAnalyzer.java,v 1.1 2004/12/03 05:07:36 macchan Exp $
 */
public class HExtendReverseCommandAnalyzer extends HAExtendCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HExtendReverseCommandAnalyzer() {
		super();
	}

	/***********************
	 * ��͊֘A
	 ***********************/

	public void analyze(HSText arg, HElement element, HEnvironment env) {

		if (!(element instanceof HChartElement)) { //�v���Z�X,�f�[�^�ȊO�ɋL�q����Ă���
			showInvalidCommandPositionMessage(arg.getParent().getLineNumber());
			return;
		}

		((HChartElement) element).reverse();
	}

	/***********************
	 * �G���[����
	 ***********************/

	private void showInvalidCommandPositionMessage(int lineNumber) {
		String message = "���o�͈ʒu�t�]���߂̓v���Z�X�C�f�[�^�ɑ΂��Ă̂݋L�q�ł��܂�";
		logger.showWarningMessage(message, lineNumber);
	}

}