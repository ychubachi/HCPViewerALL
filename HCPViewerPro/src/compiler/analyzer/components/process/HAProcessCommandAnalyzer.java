/*
 * @(#)HCallModuleCommandAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.process;

import model.HElement;
import model.HEnvironment;
import model.HProcessElement;

import compiler.analyzer.HALineCommandAnalyzer;
import compiler.scanner.model.HSText;

/**
 * ���ۃv���Z�X�R�}���h�A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HAProcessCommandAnalyzer.java,v 1.1 2004/11/25 05:00:47 gackt Exp $
 */
public abstract class HAProcessCommandAnalyzer extends HALineCommandAnalyzer {

	/**
	 * �R���X�g���N�^
	 */
	public HAProcessCommandAnalyzer() {
		super();
	}

	public HElement analyze(HSText argument, HEnvironment env) {
		String text = argument != null ? argument.getContents() : "";

		//element���쐬����
		HProcessElement element = new HProcessElement(getCreatedProcessType());
		element.setText(text);

		if (env.isTopLevel()) {
			//�ŏ�ʃv���Z�X�ł���C���W���[��ID���ݒ肳��Ă��Ȃ��ꍇ�C�ŏ�ʃv���Z�X�̃e�L�X�g�����W���[����ID�Ƃ���
			if (env.getModule().getId() == null) {
				env.getModule().setId(text);
			}

			env.getModule().setText(text);
		}

		return element;
	}

	protected abstract int getCreatedProcessType();

}