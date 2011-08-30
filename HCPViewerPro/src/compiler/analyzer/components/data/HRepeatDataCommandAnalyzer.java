/*
 * @(#)HRepeatDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.data;

import model.HChartElement;

/**
 * �J��Ԃ��^�f�[�^�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HRepeatDataCommandAnalyzer.java,v 1.11 2005/01/22 18:45:06 gackt Exp $
 */
public class HRepeatDataCommandAnalyzer extends HADataCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HRepeatDataCommandAnalyzer() {
		super();
	}

	protected int getCreateDataType() {
		return HChartElement.REPEAT;
	}

}