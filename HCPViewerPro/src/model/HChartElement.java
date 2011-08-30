/*
 * @(#)HChartElement.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * �`���[�g�v�f��\�����܂��B�i�����ƃf�[�^�j
 * @author Manabu Sugiura
 * @version $Id: HChartElement.java,v 1.8 2009/09/10 03:48:31 macchan Exp $
 */
public abstract class HChartElement extends HElement {

	//�^�C�v�錾
	/** �G���[�p */
	public static final int UNKNOWN = -1;

	/** ��{�^ */
	public static final int BASIS = 1;
	/** �J��Ԃ��^ */
	public static final int REPEAT = 2;
	/** �U�蕪���^ */
	public static final int CONDITION = 3;
	/** ����^ */
	public static final int SELECT = 4;

	/** ���\�b�h�Ăяo���^ */
	public static final int CALL_MODULE = 5;
	/** �E�o�^ */
	public static final int RETURN = 6;
	/** ��O�o���^ */
	public static final int EXCEPTION_EXIT = 7;
	/** �G���[�`�F�b�N�^ */
	public static final int ERROR_CHECK = 8;

	/** �Q�ƌ^ */
	public static final int REFERENCE = 9;
	/** ��Q�ƌ^ */
	public static final int REFERED = 10;
	/** �X�y�[�X�^ */
	public static final int SPACE = 11;

	private int type;
	private boolean reverse = false;

	private List<HChartElement> inputs = new ArrayList<HChartElement>();//����
	private List<HChartElement> outputs = new ArrayList<HChartElement>();//�o��

	/**
	 * �R���X�g���N�^
	 * @param type ���
	 */
	protected HChartElement(int type) {
		this.type = type;
	}

	/**
	 * ��ނ��擾���܂�
	 * @return ���
	 */
	public int getType() {
		return this.type;
	}

	public String getTypeString() {
		switch (this.getType()) {
			case BASIS :
				return "��{�^";
			case REPEAT :
				return "�J��Ԃ��^";
			case CALL_MODULE :
				return "���\�b�h�Ăяo���^";
			case RETURN :
				return "�E�o�^";
			case EXCEPTION_EXIT :
				return "��O�o���^";
			case ERROR_CHECK :
				return "�G���[�`�F�b�N�^";
			case CONDITION :
				return "����^";
			case SELECT :
				return "�����^";
			case REFERENCE :
				return "�Q�ƌ^";
			case REFERED :
				return "��Q�ƌ^";
			case UNKNOWN :
				return "�G���[�ł�";
			default :
				return "�^�C�v�ݒ薳��";
		}
	}

	/**
	 * �o�̓f�[�^��ǉ����܂�
	 * @param data �o�̓f�[�^
	 */
	public void addOutput(HChartElement element) {
		this.outputs.add(element);
	}

	/**
	 * ���̓f�[�^��ǉ����܂�
	 * @param data ���̓f�[�^
	 */
	public void addInput(HChartElement element) {
		this.inputs.add(element);
	}

	/**
	 * �o�̓f�[�^���擾���܂�
	 * @return �o�̓f�[�^
	 */
	public List<HChartElement> getOutputs() {
		return Collections.unmodifiableList(this.outputs);
	}

	/**
	 * ���̓f�[�^���擾���܂�
	 * @return ���̓f�[�^
	 */
	public List<HChartElement> getInputs() {
		return Collections.unmodifiableList(this.inputs);
	}

	public int countIOSize() {
		return inputs.size() + outputs.size();
	}

	public int getIOIndex(HChartElement element) {
		int index;
		if ((index = inputs.indexOf(element)) != -1) {
			return index;
		} else if ((index = outputs.indexOf(element)) != -1) {
			return inputs.size() + index;
		} else {
//			assert false;
			throw new RuntimeException("assertion");
//			return -1;
		}
	}

	public boolean hasInput() {
		return !inputs.isEmpty();
	}

	public boolean hasOutput() {
		return !outputs.isEmpty();
	}

	/**
	 * @return Returns the reverse.
	 */
	public boolean isReverse() {
		return this.reverse;
	}
	/**
	 * @param reverse The reverse to set.
	 */
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public void reverse() {
		reverse = !reverse;
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
		buffer.append(" text=\"" + getText() + "\"");
		buffer.append(super.getAttributes() + this.getAttributes());
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
	protected abstract String getAttributes();

}