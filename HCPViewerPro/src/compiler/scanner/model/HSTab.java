/*
 * @(#)HSTab.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;

/**
 * �����͂����^�u���i�[���邽�߂̓ǂݎ���p�i�s�ρj�I�u�W�F�N�g�ł��B
 * @author Manabu Sugiura
 * @version $Id: HSTab.java,v 1.1 2004/10/18 06:14:53 gackt Exp $
 */
public class HSTab extends HSElement {

	/**
	 * �V�����I�u�W�F�N�g�𐶐����܂��B
	 */
	public HSTab() {
		super("");
	}

	/**
	 * �I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 * @return ���̃I�u�W�F�N�g�̕�����\��
	 */
	public String toString() {
		return "[HSTab]";
	}

	/**
	 * �f�o�b�O�p�̕�����\����Ԃ��܂��B
	 * @return ���e�Ɋ܂܂��S�E���p�X�y�[�X�A�^�u�����������f�o�b�O�p�̕�����\��
	 */
	public String debugPrint() {
		return "[HSTab]";
	}

}
