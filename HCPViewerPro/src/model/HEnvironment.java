/*
 * @(#)HEnvironment.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * ����\�����邽�߂̃N���X
 * 
 * @author Manabu Sugiura
 * @version $Id: HEnvironment.java,v 1.19 2009/09/10 03:48:31 macchan Exp $
 */
public class HEnvironment {

	/*********************************
	 * �C���X�^���X�ϐ�
	 *********************************/

	private List<HProcessElement> processElements = new ArrayList<HProcessElement>();
	private List<HDataElement> dataElements = new ArrayList<HDataElement>();

	private HElement parentElement = null;

	/*********************************
	 * �R���X�g���N�^
	 *********************************/

	/**
	 * �R���X�g���N�^
	 * @param parentElement �e�v�f
	 */
	public HEnvironment(HElement parentElement) {
		this.parentElement = parentElement;
	}

	/*********************************
	 * �v���Z�X�@���@�f�[�^�@����
	 *********************************/

	/**
	 * �q�v�f��ǉ����܂�
	 * @param element �q�v�f
	 */
	public void addChild(HElement element) {
		if (element.getParentEnvironment() != null) {
			throw new IllegalArgumentException("���ɐe�����܂�");
		}

		//�ǉ�
		if (element instanceof HProcessElement) {
			this.processElements.add((HProcessElement)element);
			element.setParentEnvironment(this);
		} else if (element instanceof HDataElement) {
			this.dataElements.add((HDataElement)element);
			element.setParentEnvironment(this);
		} else {
			throw new IllegalArgumentException(element.toString());
		}
	}

	/**
	 * �v���Z�X���擾���܂�
	 * @return �v���Z�X
	 */
	public List<HProcessElement> getProcessElements() {
		return Collections.unmodifiableList(this.processElements);
	}

	/**
	 * �f�[�^���擾���܂�
	 * @return �f�[�^
	 */
	public List<HDataElement> getDataElements() {
		return Collections.unmodifiableList(this.dataElements);
	}

	/**
	 * �S�Ẵf�[�^���ċA�I�Ɏ擾���܂�
	 * @param allDataElements �S�Ẵf�[�^���i�[���郊�X�g
	 */
	public List<HDataElement> getAllDataElements() {
		List<HDataElement> allDataElements = new ArrayList<HDataElement>();

		allDataElements.addAll(this.getDataElements());
		for (int i = 0; i < dataElements.size(); i++) {
			HDataElement data = (HDataElement) dataElements.get(i);
			HEnvironment child = data.getEnvironment();
			allDataElements.addAll(child.getAllDataElements());
		}
		return allDataElements;
	}

	/**
	 * �S�Ẵv���Z�X���ċA�I�Ɏ擾���܂�
	 * @param allDataElements �S�Ẵv���Z�X���i�[���郊�X�g
	 */
	public List<HProcessElement> getAllProcessElements() {
		List<HProcessElement> allProcessElements = new ArrayList<HProcessElement>();

		allProcessElements.addAll(this.getProcessElements());
		for (int i = 0; i < processElements.size(); i++) {
			HProcessElement process = (HProcessElement) processElements.get(i);
			HEnvironment child = process.getEnvironment();
			allProcessElements.addAll(child.getAllProcessElements());
		}
		return allProcessElements;
	}

	/**
	 * �w�肵�����O�̃f�[�^���������܂�
	 * @param name ���O
	 * @return �w�肵�����O�̃f�[�^
	 */
	public HDataElement searchData(String name) {
		//���̊��Ō�������
		HDataElement data = this.searchDataInternal(name);

		//������Ȃ��ꍇ�́C��ʊ�����������
		if (data != null) {//��������
			return data;
		} else if (getParent() == null) {
			return null;
		} else {
			return getParent().searchData(name);
		}
	}

	/**
	 * �f�[�^�̖��O����f�[�^���擾���܂�
	 * @param name �f�[�^�̖��O
	 * @return �f�[�^�i�錾���Ȃ��ꍇ��null�j
	 */
	private HDataElement searchDataInternal(String name) {
		List<HDataElement> allData = getAllDataElements();
		for (Iterator<HDataElement> i = allData.iterator(); i.hasNext();) {
			HDataElement data = i.next();
			if (data.getText().equals(name)) {
				return data;
			}
		}
		return null;
	}

	/*********************************
	 * ���J����
	 *********************************/

	/**
	 * �e�v�f���擾���܂�
	 * @param �e�v�f
	 */
	public HElement getParentElement() {
		return this.parentElement;
	}

	/**
	 * �������郂�W���[�����擾���܂�
	 * @return �������郂�W���[��
	 */
	public HModule getModule() {
		if (isTopLevel()) {
			return (HModule) this.parentElement;
		}

		return this.getParent().getModule();
	}

	/**
	 * ��������h�L�������g���擾���܂�
	 * @return ��������h�L�������g
	 */
	public HDocument getDocument() {
		return getModule().getDocument();
	}

	/**
	 * �K�w���x�����擾���܂��B
	 * @return �K�w���x��
	 */
	public int getLevel() {
		if (this.isTopLevel()) {
			return 0;
		}

		//�����^�̃u�����`�̏ꍇ�̓��x�����J�E���g���Ȃ�
		if (this.isBranchModule()) {
			return this.getParent().getLevel();
		}

		return this.getParent().getLevel() + 1;
	}

	/**
	 * @return
	 */
	public HEnvironment getParent() {
		return getParentElement().getParentEnvironment();
	}

	/**
	 * @return
	 */
	public boolean isTopLevel() {
		return getParent() == null;
	}

	private boolean isBranchModule() {
		if ((this.parentElement instanceof HChartElement)
				&& ((HChartElement) this.parentElement).getType() == HChartElement.CONDITION) {
			return true;
		}
		return false;
	}

	/**
	 * @return
	 */
	public boolean hasChildElement() {
		return !processElements.isEmpty() || !dataElements.isEmpty();
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
		buffer.append(getAttributes());

		if (this.processElements.isEmpty() && this.dataElements.isEmpty()) {
			buffer.append("/>\n");
		} else {
			buffer.append(">\n");
		}
	}

	/**
	 * FOR DEBUG
	 */
	protected String getAttributes() {
		StringBuffer buffer = new StringBuffer();

		buffer.append(" level=\"");
		buffer.append(getLevel() + "\"");

		buffer.append(" hash=\"");
		buffer.append(hashCode() + "\"");

		return buffer.toString();
	}

	/**
	 * FOR DEBUG
	 */
	private void appendChildren(StringBuffer buffer, int depth) {
		//Data
		for (int i = 0; i < this.dataElements.size(); i++) {
			HChartElement element = (HChartElement) this.dataElements.get(i);
			element.debugPrint(buffer, depth);
		}

		//Process
		for (Iterator<HProcessElement> i = this.processElements.iterator(); i.hasNext();) {
			HProcessElement processElement = i.next();
			processElement.debugPrint(buffer, depth);

		}
	}

	/**
	 * FOR DEBUG
	 */
	private void endTag(StringBuffer buffer, int depth) {
		if (!this.processElements.isEmpty() || !this.dataElements.isEmpty()) {
			for (int i = 0; i < depth; i++) {
				buffer.append("\t");
			}
			buffer.append("</" + getClass().getName() + ">\n");
		}
	}

}