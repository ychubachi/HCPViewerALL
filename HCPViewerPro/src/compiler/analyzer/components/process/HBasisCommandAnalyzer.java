/*
 * @(#)HBasisCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.process;

import model.HChartElement;

/**
 * ��{�^�v���Z�X�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HBasisCommandAnalyzer.java,v 1.14 2004/11/25 05:00:47 gackt Exp $
 */
public class HBasisCommandAnalyzer extends HAProcessCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HBasisCommandAnalyzer() {
		super();
	}

	protected int getCreatedProcessType() {
		return HChartElement.BASIS;
	}

}