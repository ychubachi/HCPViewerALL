/*
 * @(#)HHeaderCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.header;

import model.HDocument;
import model.HElement;
import model.HEnvironment;

import compiler.analyzer.HALineCommandAnalyzer;
import compiler.scanner.model.HSText;

/**
 * ���ۃw�b�_�[�R�}���h�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HAHeaderCommandAnalyzer.java,v 1.1 2004/11/25 05:00:47 gackt Exp $
 */
public abstract class HAHeaderCommandAnalyzer extends HALineCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	protected HAHeaderCommandAnalyzer() {
		super();
	}

	public HElement analyze(HSText argument, HEnvironment env) {
		setHeader(env.getDocument(), argument.getContents()); //�h�L�������g�ɃR�}���h���e��ݒ肷��
		return null;
	}

	protected abstract void setHeader(HDocument document, String headerText);

}