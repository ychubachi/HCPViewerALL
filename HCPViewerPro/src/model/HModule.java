/*
 * @(#)HModule.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ���W���[����\�����邽�߂̃N���X
 * 
 * @author Manabu Sugiura
 * @version $Id: HModule.java,v 1.19 2009/09/10 03:48:31 macchan Exp $
 */
public class HModule extends HElement {

	public static final String DEFAULT_NAME = "default";
	/**********************************
	 * �C���X�^���X�ϐ�
	 ***********************************/

	private HDocument document = null;
	private String id = DEFAULT_NAME;// �f�[�^�����̂��̂ł�ID���t�����ꂸ�Cnullpointer�ɂȂ�̂ŁC�f�t�H���g���K�肷��D1.3.2

	/**********************************
	 * �R���X�g���N�^
	 ***********************************/

	/**
	 * �R���X�g���N�^
	 * 
	 * @param document
	 *            ��������h�L�������g
	 */
	public HModule(HDocument document) {
		this.document = document;
	}

	/**********************************
	 * Document�n
	 ***********************************/

	/**
	 * @return Returns the document.
	 */
	public HDocument getDocument() {
		return document;
	}

	/**********************************
	 * �v���p�e�B�擾�n
	 ***********************************/

	/**
	 * �^�C�g�����擾���܂�
	 * 
	 * @return �^�C�g��
	 */
	public String getTitle() {
		return getDocument().getTitle();
	}

	/**
	 * �쐬�������擾���܂�
	 * 
	 * @return �쐬��
	 */
	public String getDate() {
		return getDocument().getDate();
	}

	/**
	 * �o�[�W�������擾���܂�
	 * 
	 * @return �o�[�W����
	 */
	public String getVersion() {
		return getDocument().getVersion();
	}

	/**
	 * �쐬�҂��擾���܂�
	 * 
	 * @return �쐬��
	 */
	public String getAuthor() {
		return getDocument().getAuthor();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public boolean isDefaultID() {
		return DEFAULT_NAME.equals(getId());
	}

	/**********************************
	 * ���J���\�b�h
	 ***********************************/

	/**
	 * ���[�g�v���Z�X������ǂ������肵�܂�
	 * 
	 * @return ���[�g�v���Z�X��������̏ꍇ��true
	 */
	public boolean hasOnlyOneRootProcess() {
		return this.getEnvironment().getProcessElements().size() == 1;
	}

	/**
	 * ���[�g�v���Z�X���擾���܂�
	 * 
	 * @return ���[�g�v���Z�X
	 */
	public HProcessElement getRootProcess() {
		if (hasOnlyOneRootProcess()) {
			return (HProcessElement) getEnvironment().getProcessElements().get(
					0);
		}
		return null;
	}

	public List<HNote> getNotes() {
		List<HNote> notes = new ArrayList<HNote>();

		// �����̃m�[�g��ǉ�
		notes.addAll(super.getNotes());

		// �q�̃m�[�g��ǉ�
		notes.addAll(getNotes(getAllProcessElements()));// �v���Z�X
		notes.addAll(getNotes(getAllDataElements()));// �f�[�^

		return notes;
	}

	private List<HNote> getNotes(List<? extends HElement> elements) {
		List<HNote> allNotes = new ArrayList<HNote>();
		for (Iterator<? extends HElement> i = elements.iterator(); i.hasNext();) {
			HElement element = (HElement) i.next();
			allNotes.addAll(element.getNotes());
		}
		return allNotes;
	}

	// ***** FOR PART OF DEBUG *****

	/**
	 * FOR DEBUG
	 */
	protected String getAttributes() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" name=\"");
		buffer.append(this.getText() + "\"");
		return buffer.toString();
	}

}