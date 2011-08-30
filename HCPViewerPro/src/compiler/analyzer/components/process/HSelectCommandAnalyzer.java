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
 * @version $Id: HSelectCommandAnalyzer.java,v 1.1 2005/04/11 12:18:55 macchan Exp $
 */
public class HSelectCommandAnalyzer extends HAProcessCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HSelectCommandAnalyzer() {
		super();
	}

	protected int getCreatedProcessType() {
		return HChartElement.SELECT;
	}

}