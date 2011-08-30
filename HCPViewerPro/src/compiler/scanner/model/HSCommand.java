/*
 * @(#)HSCommand.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;

/**
 * �����͂����R�}���h���i�[���邽�߂̓ǂݎ���p�i�s�ρj�I�u�W�F�N�g�ł��B
 * 
 * @author Manabu Sugiura
 * @version $Id: HSCommand.java,v 1.5 2004/11/18 12:08:07 macchan Exp $
 */
public class HSCommand extends HSElement {

	public static final HSCommand DEFAULT_COMMAND = new HSCommand("Default");

	/**
	 * �V�����I�u�W�F�N�g�𐶐����܂��B
	 * 
	 * @param contents
	 *               ���e
	 */
	public HSCommand(String contents) {
		super(contents);
	}

	/**
	 * �I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 * 
	 * @return ���̃I�u�W�F�N�g�̕�����\��
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[HSCommand:" + this.getContents());
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * �f�o�b�O�p�̕�����\����Ԃ��܂��B
	 * 
	 * @return ���e�Ɋ܂܂��S�E���p�X�y�[�X�A�^�u�����������f�o�b�O�p�̕�����\��
	 */
	public String debugPrint() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[HSCommand:" + this.getContents());
		buffer.append("]");
		return HSElement.visualizeWhiteSpace(buffer.toString());
	}

}