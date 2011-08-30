/*
 * @(#)HSLine.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * �����͂���1�s���̗v�f���i�[���邽�߂̓ǂݎ���p�i�s�ρj�I�u�W�F�N�g�ł��B
 * 
 * @author Manabu Sugiura
 * @version $Id: HSLine.java,v 1.7 2009/09/10 03:48:32 macchan Exp $
 */
public class HSLine {

	/** �v�f */
	private List<HSElement> elements = new ArrayList<HSElement>();

	/** �s�ԍ� */
	private int lineNumber;

	/**
	 * �V�����I�u�W�F�N�g�𐶐����܂��B
	 * 
	 * @param elements
	 *               �v�f
	 * @param lineNumber
	 *               �s�ԍ�
	 */
	public HSLine(List<HSElement> elements, int lineNumber) {
		this.elements.addAll(elements);
		for (Iterator<HSElement> i = elements.iterator(); i.hasNext();) {
			HSElement element = (HSElement) i.next();
			element.setParent(this);
		}
		this.lineNumber = lineNumber;
	}

	public ListIterator<HSElement> listIterator() {
		return elements.listIterator();
	}

	//	/**
	//	 * �R�}���h�ƃe�L�X�g�̃y�A�̔����q��Ԃ��܂��B
	//	 *
	//	 * @return �R�}���h�ƃe�L�X�g�̃y�A�̔����q
	//	 */
	//	public HSPairIterator pairIterator() {
	//		return new HSPairIterator(this);
	//	}

	/**
	 * �s���̃R�}���h���擾���܂�
	 * @return �s���̃R�}���h�i�R�}���h���Ȃ��ꍇ��null�j
	 */
	public HSCommand getFirstCommand() {
		for (Iterator<HSElement> i = this.elements.iterator(); i.hasNext();) {
			HSElement element = (HSElement) i.next();
			if (element instanceof HSCommand) {
				return (HSCommand) element;
			}
		}
		return null;
	}

	/**
	 * �w�肵��index�̗v�f���擾���܂�
	 * 
	 * @param index
	 *               �擾����v�f��index
	 * @return �w�肵��index�̗v�f�i�v�f���Ȃ��ꍇ��null�j
	 */
	public HSElement get(int index) {
		return (HSElement) this.elements.get(index);
	}

	/**
	 * �s�ԍ����擾���܂��B
	 * 
	 * @return �s�ԍ�
	 */
	public int getLineNumber() {
		return this.lineNumber;
	}

	/**
	 * �s���ɘA������^�u�̌����擾���܂�
	 * 
	 * @return �s���ɘA������^�u�̌�
	 */
	public int getTabCount() {
		int tabCount = 0;
		for (int i = 0; i < this.elements.size(); i++) {
			HSElement element = (HSElement) this.elements.get(i);
			if (element instanceof HSTab) {
				tabCount++;
			} else {
				break;
			}
		}
		return tabCount;
	}

	/**
	 * �L���ȍs�����肵�܂��B ��s�i�v�f���^�u+�R�����g�A�^�u�̂݁A�R�����g�̂݁A�s�̓��e��1���Ȃ��j�̏ꍇ�ɖ����Ɣ��肵�܂�
	 * 
	 * @return �L���ȏꍇ��true
	 */
	public boolean isValid() {
		return this.hasContents() && !this.hasTabAndCommentOnly();
	}

	/**
	 * �i�[����Ă���v�f���^�u�ƃR�����g�݂̂��ǂ����𔻒肵�܂��B
	 * 
	 * @return �i�[����Ă���v�f���^�u�ƃR�����g�݂̂̏ꍇ��true
	 */
	private boolean hasTabAndCommentOnly() {
		for (int i = 0; i < this.elements.size(); i++) {
			HSElement element = (HSElement) this.elements.get(i);
			if (!(element instanceof HSTab || element instanceof HSComment)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * �i�[����Ă���v�f�����邩�ǂ����𔻒肵�܂��B
	 * 
	 * @return �i�[����Ă���v�f������ꍇ��true
	 */
	private boolean hasContents() {
		return !this.elements.isEmpty();
	}

	/**
	 * �I�u�W�F�N�g�̕�����\����Ԃ��܂��B
	 * 
	 * @return ���̃I�u�W�F�N�g�̕�����\��
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator<HSElement> i = this.elements.iterator(); i.hasNext();) {
			HSElement element = (HSElement) i.next();
			buffer.append(element.toString());
		}
		return buffer.toString();
	}

	/**
	 * �f�o�b�O�p�̕�����\����Ԃ��܂��B
	 * 
	 * @return �v�f�̓��e�Ɋ܂܂��S�E���p�X�y�[�X�A�^�u�����������f�o�b�O�p�̕�����\��
	 */
	public String debugPrint() {
		StringBuffer buffer = new StringBuffer();
		for (Iterator<HSElement> i = this.elements.iterator(); i.hasNext();) {
			HSElement element = (HSElement) i.next();
			buffer.append(element.debugPrint());
		}
		return buffer.toString();
	}

	//	/**
	//	 * �R�}���h�ƃe�L�X�g�̃y�A�̔����q�ł��B
	//	 *
	//	 * @author Manabu Sugiura
	//	 * @version $Id: HSLine.java,v 1.7 2009/09/10 03:48:32 macchan Exp $
	//	 */
	//	public class HSPairIterator {
	//
	//		/** �ǂݎ��p�J�n�ʒu�J�[�\�� */
	//		private int cursor = getTabCount();
	//
	//		/** �ŏ��̃R�}���h���ȗ�����Ă��邩�ǂ��� */
	//		private boolean omit = true;
	//
	//		/** �ŏ��̃y�A�̓ǂݎ�肩�ǂ��� */
	//		private boolean firstRead = true;
	//
	//		/** ���쒆�̍s */
	//		private HSLine line = null;
	//
	//		/**
	//		 * �V�����I�u�W�F�N�g�𐶐����܂��B
	//		 */
	//		private HSPairIterator(HSLine line) {
	//			if (HSLine.this.elements.get(this.cursor) instanceof HSCommand) {
	//				this.omit = false;
	//			}
	//			this.line = line;
	//		}
	//
	//		/**
	//		 * �J��Ԃ������ł���Ƀy�A�����邩�𔻒肵�܂��B
	//		 *
	//		 * @return �����q������Ƀy�A�����ꍇ��true
	//		 */
	//		public boolean hasNext() {
	//			return this.cursor != HSLine.this.elements.size();
	//		}
	//
	//		/**
	//		 * �J��Ԃ������Ŏ��̃y�A��Ԃ��܂��B
	//		 *
	//		 * @return �J��Ԃ������Ŏ��̃y�A
	//		 */
	//		public HSCommandUnit next() {
	//			HSCommand command = null;
	//			HSText text = null;
	//			if (this.omit && this.firstRead) { //�ŏ��̓ǂݎ��ŃR�}���h���ȗ�����Ă��鎞
	//				text = (HSText) HSLine.this.elements.get(this.cursor); //�ŏ��̗v�f���e�L�X�g��
	//				this.firstRead = false;
	//				this.cursor++; //�R�}���h���Ȃ��̂�1�����i�߂�
	//			} else {
	//				command = (HSCommand) HSLine.this.elements.get(this.cursor);
	//				if (!(this.cursor + 1 >= HSLine.this.elements.size())) { //�T�C�Y�I�[�o�[��h��
	//					HSElement next = (HSElement) HSLine.this.elements
	//							.get(this.cursor + 1);
	//					if (next instanceof HSText) {
	//						text = (HSText) next;
	//					}
	//					this.cursor += 2;
	//				} else {
	//					this.cursor = HSLine.this.elements.size();
	//				}
	//			}
	//			return new HSCommandUnit(command, text, this.line);
	//		}
	//
	//	}
	//
	//	/**
	//	 * �R�}���h�P�ʁi�R�}���h+�����j��\�����܂��D
	//	 * <ul>
	//	 * <li>�ʏ폈���̃R�}���h���s���ŏȗ�����Ă���ꍇ�A�R�}���h���擾�����null���Ԃ�܂��B</li>
	//	 * <li>�����Ȃ��́C</li>
	//	 * </ul>
	//	 *
	//	 * @author Manabu Sugiura
	//	 * @version $Id: HSLine.java,v 1.7 2009/09/10 03:48:32 macchan Exp $
	//	 */
	//	public class HSCommandUnit {
	//
	//
	//	}

}