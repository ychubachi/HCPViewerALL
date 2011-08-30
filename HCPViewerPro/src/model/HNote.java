/*
 * @(#)HNote.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

/**
 * �m�[�g��\�����邽�߂̃N���X�ł�
 * @author Manabu Sugiura
 * @version $Id: HNote.java,v 1.8 2004/11/22 19:18:11 gackt Exp $
 */
public class HNote {

	private String contents;
	private HElement parent;

	/**
	 * �R���X�g���N�^
	 * @param contents �m�[�g�̓��e
	 * @param index �m�[�g�̃C���f�b�N�X
	 */
	public HNote(String contents) {
		this.contents = contents;
	}

	/**
	 * �m�[�g�̓��e���擾���܂�
	 * @return �m�[�g�̓��e
	 */
	public String getContents() {
		return this.contents;
	}

	public void setParent(HElement parent) {
		this.parent = parent;
	}

	public HElement getParent() {
		return this.parent;
	}

}