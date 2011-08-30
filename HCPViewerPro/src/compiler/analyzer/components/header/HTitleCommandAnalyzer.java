/*
 * @(#)HTitleCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.header;

import model.HDocument;

/**
 * �^�C�g���R�}���h�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HTitleCommandAnalyzer.java,v 1.11 2004/11/25 05:00:47 gackt Exp $
 */
public class HTitleCommandAnalyzer extends HAHeaderCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HTitleCommandAnalyzer() {
		super();
	}

	protected void setHeader(HDocument document, String headerText) {
		document.setTitle(headerText);
	}

}