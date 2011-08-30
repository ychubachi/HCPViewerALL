/*
 * @(#)HCallModuleCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.process;

import model.HChartElement;

/**
 * �����^�v���Z�X�R�}���h�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HForkCommandAnalyzer.java,v 1.11 2005/04/11 12:35:01 macchan Exp $
 */
public class HForkCommandAnalyzer extends HAProcessCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HForkCommandAnalyzer() {
		super();
	}

	protected int getCreatedProcessType() {
		return HChartElement.CONDITION;
	}

}