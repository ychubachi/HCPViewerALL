/*
 * @(#)HErrorCheckCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.process;

import model.HChartElement;

/**
 * �G���[�`�F�b�N�^�v���Z�X�R�}���h�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HErrorCheckCommandAnalyzer.java,v 1.11 2004/11/25 05:00:47 gackt Exp $
 */
public class HErrorCheckCommandAnalyzer extends HAProcessCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HErrorCheckCommandAnalyzer() {
		super();
	}

	protected int getCreatedProcessType() {
		return HChartElement.ERROR_CHECK;
	}

}