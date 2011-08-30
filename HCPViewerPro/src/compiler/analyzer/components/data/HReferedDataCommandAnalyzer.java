/*
 * @(#)HReferedDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.data;

import model.HChartElement;

/**
 * ��Q�ƌ^�f�[�^�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HReferedDataCommandAnalyzer.java,v 1.12 2004/11/25 05:00:47 gackt Exp $
 */
public class HReferedDataCommandAnalyzer extends HADataCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HReferedDataCommandAnalyzer() {
		super();
	}

	protected int getCreateDataType() {
		return HChartElement.REFERED;
	}

}