/*
 * @(#)HElement.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * �R�����g��t���ł���`���[�g�v�f�i�����A�f�[�^�A�����j��\�����邽�߂̃N���X
 * 
 * @author Manabu Sugiura
 * @version $Id: HElement.java,v 1.13 2009/09/10 03:48:31 macchan Exp $
 */
public abstract class HElement {

	private String text = "";

	private List<HNote> notes = new ArrayList<HNote>();
	private HEnvironment environment = new HEnvironment(this);

	private HEnvironment parentEnvironment = null;

	/***************************************************************************
	 * �m�[�g
	 **************************************************************************/

	/**
	 * �m�[�g���擾���܂�
	 * 
	 * @return �m�[�g
	 */
	public List<HNote> getNotes() {
		return Collections.unmodifiableList(this.notes);
	}

	/**
	 * �m�[�g��ǉ����܂�
	 * 
	 * @param note
	 *            �m�[�g
	 */
	public void addNote(HNote note) {
		this.notes.add(note);
	}

	/***************************************************************************
	 * �e�L�X�g
	 **************************************************************************/

	/**
	 * �e�L�X�g���擾���܂�
	 * 
	 * @return �e�L�X�g
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * �e�L�X�g��ݒ肵�܂�
	 * 
	 * @param text
	 *            �e�L�X�g
	 */
	public void setText(String text) {
		this.text = text;
	}

	/***************************************************************************
	 * �q��
	 **************************************************************************/

	/**
	 * �q�̊����擾���܂�
	 * 
	 * @return parentEnvironment �e�̊�
	 */
	public HEnvironment getEnvironment() {
		return this.environment;
	}

	/**
	 * �q�������Ă��邩���肵�܂�
	 * 
	 * @return �q�������Ă���ꍇ��true
	 */
	public boolean hasChildElement() {
		return this.environment.hasChildElement();
	}

	/***************************************************************************
	 * �e��
	 **************************************************************************/

	/**
	 * �e�̊����擾���܂�
	 * 
	 * @return parentEnvironment �e�̊�
	 */
	public HEnvironment getParentEnvironment() {
		return this.parentEnvironment;
	}

	/**
	 * �e�̊���ݒ肵�܂�
	 * 
	 * @param parentEnvironment
	 *            �e�̊�
	 */
	protected void setParentEnvironment(HEnvironment parentEnvironment) {
		this.parentEnvironment = parentEnvironment;
	}

	/***************************************************************************
	 * �擾
	 **************************************************************************/

	/**
	 * �h�L�������g���擾���܂�
	 * 
	 * @return �h�L�������g
	 */
	public HDocument getDocument() {
		if (parentEnvironment != null) {
			this.parentEnvironment.getDocument();
		}
		return null;
	}

	/**
	 * ���W���[�����擾���܂�
	 * 
	 * @return ���W���[��
	 */
	public HModule getModule() {
		// if (parentEnvironment != null) {
		return this.parentEnvironment.getModule();
		// }
		// return null;
	}

	/***************************************************************************
	 * ���ׂĎ擾�n
	 **************************************************************************/

	/**
	 * @return
	 */
	public List<HProcessElement> getAllProcessElements() {
		return getEnvironment().getAllProcessElements();
	}

	/**
	 * @return
	 */
	public List<HDataElement> getAllDataElements() {
		return getEnvironment().getAllDataElements();
	}

	// ***** FOR PART OF DEBUG *****

	/**
	 * FOR DEBUG
	 */
	public void debugPrint(StringBuffer buffer, int depth) {
		startTag(buffer, depth);
		appendChildren(buffer, depth + 1);
		endTag(buffer, depth);
	}

	/**
	 * FOR DEBUG
	 */
	private void startTag(StringBuffer buffer, int depth) {
		for (int i = 0; i < depth; i++) {
			buffer.append("\t");
		}
		buffer.append("<" + getClass().getName());
		buffer.append(this.getAttributes());
		if (this.hasChildElement()) {
			buffer.append(">\n");
		} else {
			buffer.append("/>\n");
		}

	}

	/**
	 * FOR DEBUG
	 */
	private void appendChildren(StringBuffer buffer, int depth) {
		if (this.hasChildElement()) {
			this.getEnvironment().debugPrint(buffer, depth);
		}
	}

	/**
	 * FOR DEBUG
	 */
	private void endTag(StringBuffer buffer, int depth) {
		if (this.hasChildElement()) {
			for (int i = 0; i < depth; i++) {
				buffer.append("\t");
			}
			buffer.append("</" + getClass().getName() + ">\n");
		}
	}

	/**
	 * FOR DEBUG
	 */
	protected String getAttributes() {
		StringBuffer buffer = new StringBuffer();
		if (!this.notes.isEmpty()) {
			buffer.append(" note=\"");
			for (int i = 0; i < this.notes.size(); i++) {
				buffer.append(((HNote) this.notes.get(i)).getContents());
				if (i != this.notes.size() - 1) {
					buffer.append(" ,");
				}
			}
			buffer.append("\"");
		}
		return buffer.toString();
	}

}