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
 * �ʏ�R�}���h���ۃA�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HALineCommandAnalyzer.java,v 1.1 2004/11/25 05:00:46 gackt Exp $
 */
public abstract class HALineCommandAnalyzer implements HCommandAnalyzer {

	/***********************
	 * �N���X�ϐ�
	 ***********************/

	protected static final HCompileLogger logger = HCompileLogger.getInstance();

	/**
	 * �R���X�g���N�^
	 */
	public HALineCommandAnalyzer() {
		super();
	}

	public abstract HElement analyze(HSText arg, HEnvironment env);

}