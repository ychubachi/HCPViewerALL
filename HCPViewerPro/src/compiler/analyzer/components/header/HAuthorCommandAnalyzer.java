/*
 * @(#)AuthorLineAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.header;

import model.HDocument;

/**
 * �쐬�҃R�}���h�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HAuthorCommandAnalyzer.java,v 1.11 2004/11/25 05:00:47 gackt Exp $
 */
public class HAuthorCommandAnalyzer extends HAHeaderCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HAuthorCommandAnalyzer() {
		super();
	}

	protected void setHeader(HDocument document, String headerText) {
		document.setAuthor(headerText);
	}

}