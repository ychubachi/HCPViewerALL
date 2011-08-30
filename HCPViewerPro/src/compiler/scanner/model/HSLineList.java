/*
 * @(#)HSLineList.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * �����͂���1�s���̗v�f�𕡐��i�[���邽�߂̓ǂݎ���p�i�s�ρj�I�u�W�F�N�g�ł��B
 * @author Manabu Sugiura
 * @version $Id: HSLineList.java,v 1.7 2009/09/10 03:48:32 macchan Exp $
 */
public class HSLineList {

	/** �s */
	private List<HSLine> lines = new ArrayList<HSLine>();

	/**
	 * �V�����I�u�W�F�N�g�𐶐����܂��B
	 */
	public HSLineList() {
		super();
	}

	/**
	 * �s��ǉ����܂��B
	 * @param line �ǉ�����s
	 */
	public void add(HSLine line) {
		this.lines.add(line);
	}

	/**
	 * ���X�g�ɍs���Ȃ��ꍇ��true��Ԃ��܂��B
	 * @return ���X�g���s��1���ێ����Ă��Ȃ��ꍇ��true
	 */
	public boolean isEmpty() {
		return this.lines.isEmpty();
	}

	/**
	 * ���X�g���̍s��K�؂ȏ����ŌJ��Ԃ��������锽���q��Ԃ��܂��B
	 * @return ���X�g���̍s��K�؂ȏ����ŌJ��Ԃ��������锽���q
	 */
	public ListIterator<HSLine> listIterator() {
		return this.lines.listIterator();
	}

	public HSLineList subList(int fromIndex, int toIndex) {
		HSLineList lineList = new HSLineList();
		for (int i = fromIndex; i < toIndex; i++) {
			lineList.add((HSLine) this.lines.get(i));
		}
		return lineList;
	}

	public int size() {
		return this.lines.size();
	}

	/**
	 * �I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 * @return ���̃I�u�W�F�N�g�̕�����\��
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator<HSLine> i = this.lines.iterator(); i.hasNext();) {
			HSLine line = (HSLine) i.next();
			buffer.append(line.toString());
			if (i.hasNext()) {
				buffer.append(System.getProperty("line.separator"));
			}
		}
		return buffer.toString();
	}

	/**
	 * �f�o�b�O�p�̕�����\����Ԃ��܂��B
	 * @return �v�f�̓��e�Ɋ܂܂��S�E���p�X�y�[�X�A�^�u�����������f�o�b�O�p�̕�����\��
	 */
	public String debugPrint() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator<HSLine> i = this.lines.iterator(); i.hasNext();) {
			HSLine line = (HSLine) i.next();
			buffer.append(line.debugPrint());
			if (i.hasNext()) {
				buffer.append(System.getProperty("line.separator"));
			}
		}
		return buffer.toString();
	}

}