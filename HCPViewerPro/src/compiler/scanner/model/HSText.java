/*
 * @(#)HSText.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;


/**
 * �����͂����e�L�X�g���i�[���邽�߂̓ǂݎ���p�i�s�ρj�I�u�W�F�N�g�ł��B
 * @author Manabu Sugiura
 * @version $Id: HSText.java,v 1.3 2004/11/17 09:00:29 gackt Exp $
 */
public class HSText extends HSElement {

	/**
	 * �V�����I�u�W�F�N�g�𐶐����܂��B
	 * @param contents ���e
	 */
	public HSText(String contents) {
		super(contents);
	}

	/**
	 * �I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 * @return ���̃I�u�W�F�N�g�̕�����\��
	 */
	public String toString() {
		return "[HSText:" + this.getContents() + "]";
	}

	/**
	 * �f�o�b�O�p�̕�����\����Ԃ��܂��B
	 * @return ���e�Ɋ܂܂��S�E���p�X�y�[�X�A�^�u�����������f�o�b�O�p�̕�����\��
	 */
	public String debugPrint() {
		return HSElement.visualizeWhiteSpace(this.toString());
	}

}