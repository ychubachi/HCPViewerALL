/*
 * @(#)HRepeatCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.process;

import model.HChartElement;

/**
 * �J��Ԃ��^�v���Z�X�R�}���h�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HRepeatCommandAnalyzer.java,v 1.12 2004/11/25 05:00:47 gackt Exp $
 */
public class HRepeatCommandAnalyzer extends HAProcessCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HRepeatCommandAnalyzer() {
		super();
	}

	protected int getCreatedProcessType() {
		return HChartElement.REPEAT;
	}

}