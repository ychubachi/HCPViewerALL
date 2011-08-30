/*
 * @(#)HNoteCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components;

import model.HElement;
import model.HEnvironment;
import model.HNote;

import compiler.analyzer.HAExtendCommandAnalyzer;
import compiler.scanner.model.HSText;

/**
 * �m�[�g�R�����g�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HNoteCommandAnalyzer.java,v 1.13 2004/11/25 05:00:47 gackt Exp $
 */
public class HNoteCommandAnalyzer extends HAExtendCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HNoteCommandAnalyzer() {
		super();
	}

	public void analyze(HSText argument, HElement element, HEnvironment env) {
		String text = argument != null ? argument.getContents() : "";

		HNote note = new HNote(text);

		//\module main \note hoge �ɑ΂��鉞�}���u�ł�
		if (element == null) {
			//env.getModule().addNote(note);
			return;
		}

		element.addNote(note);
		note.setParent(element);
	}
}