/*
 * @(#)HConditionCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.process;

import model.HChartElement;

/**
 * ��O�o���^�v���Z�X�R�}���h�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HExceptionExitCommandAnalyzer.java,v 1.13 2005/04/11 12:18:55 macchan Exp $
 */
public class HExceptionExitCommandAnalyzer extends HAProcessCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HExceptionExitCommandAnalyzer() {
		super();
	}

	protected int getCreatedProcessType() {
		return HChartElement.EXCEPTION_EXIT;
	}

}