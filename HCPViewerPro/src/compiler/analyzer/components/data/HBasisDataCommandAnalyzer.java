/*
 * @(#)HBasisDataCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.data;

import model.HChartElement;

/**
 * ��{�^�f�[�^�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HBasisDataCommandAnalyzer.java,v 1.13 2004/11/25 05:00:47 gackt Exp $
 */
public class HBasisDataCommandAnalyzer extends HADataCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HBasisDataCommandAnalyzer() {
		super();
	}

	protected int getCreateDataType() {
		return HChartElement.BASIS;
	}

}