/*
 * @(#)HSElement.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;

/**
 * �����͂̌��ʂ��i�[���邽�߂̓ǂݎ���p�i�s�ρj�I�u�W�F�N�g�ł��B
 * 
 * @author Manabu Sugiura
 * @version $Id: HSElement.java,v 1.3 2004/11/18 12:08:07 macchan Exp $
 */
public abstract class HSElement {

	private HSLine parent = null;

	/** ���e */
	private String contents = null;

	/**
	 * �V�����I�u�W�F�N�g�𐶐����܂��B
	 * 
	 * @param contents
	 *               ���e
	 */
	protected HSElement(String contents) {
		this.contents = contents;
	}

	/**
	 * ���e���擾���܂��B
	 * 
	 * @return ���e
	 */
	public String getContents() {
		return this.contents;
	}

	/**
	 * �I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 * 
	 * @return ���̃I�u�W�F�N�g�̕�����\��
	 */
	public abstract String toString();

	/**
	 * �f�o�b�O�p�̕�����\����Ԃ��܂��B
	 * 
	 * @return ���e�Ɋ܂܂��S�E���p�X�y�[�X�A�^�u�����������f�o�b�O�p�̕�����\��
	 */
	public abstract String debugPrint();

	/**
	 * ������Ɋ܂܂��S�E���p�X�y�[�X�A�^�u���g���h�ɕϊ����ĉ������܂��B
	 * 
	 * @param s
	 *               �ϊ����镶����
	 * @return �󔒂���������������
	 */
	public static String visualizeWhiteSpace(String s) {
		char spaceMark = '��';
		String result = s.replace('�@', spaceMark);
		result = result.replace(' ', spaceMark);
		result = result.replace('\t', spaceMark);
		return result;
	}

	/**
	 * @return Returns the parent.
	 */
	public HSLine getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *               The parent to set.
	 */
	protected void setParent(HSLine parent) {
		this.parent = parent;
	}
}