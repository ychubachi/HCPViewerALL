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
 * �g���R�}���h�̒��ۃA�i���C�U�[
 * �E�g���R�}���h��\note���̃R�}���h�ƃe�L�X�g�i�����j�̌�ɑ����R�}���h�ł�
 * @author Manabu Sugiura
 * @version $Id: HAExtendCommandAnalyzer.java,v 1.1 2004/11/25 05:00:46 gackt Exp $
 */
public abstract class HAExtendCommandAnalyzer implements HCommandAnalyzer {

	/***********************
	 * �N���X�ϐ�
	 ***********************/

	protected static final HCompileLogger logger = HCompileLogger.getInstance();

	/**
	 * �R���X�g���N�^
	 */
	public HAExtendCommandAnalyzer() {
		super();
	}

	public abstract void analyze(HSText arg, HElement element, HEnvironment env);

}