/*
 * @(#)HReturnCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.process;

import model.HChartElement;

/**
 * �E�o�^�v���Z�X�R�}���h�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HReturnCommandAnalyzer.java,v 1.12 2004/11/25 05:00:47 gackt Exp $
 */
public class HReturnCommandAnalyzer extends HAProcessCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HReturnCommandAnalyzer() {
		super();
	}

	protected int getCreatedProcessType() {
		return HChartElement.RETURN;
	}

}