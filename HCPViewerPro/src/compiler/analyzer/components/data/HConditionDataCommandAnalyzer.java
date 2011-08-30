/*
 * @(#)HConditionDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.data;

import model.HChartElement;

/**
 * �I���^�f�[�^�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HConditionDataCommandAnalyzer.java,v 1.2 2005/04/11 12:18:55 macchan Exp $
 */
public class HConditionDataCommandAnalyzer extends HADataCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HConditionDataCommandAnalyzer() {
		super();
	}

	protected int getCreateDataType() {
		return HChartElement.CONDITION;
	}

}