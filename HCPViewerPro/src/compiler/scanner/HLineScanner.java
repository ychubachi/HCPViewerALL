/*
 * @(#)HDataListAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner;

import java.util.ArrayList;
import java.util.List;

import compiler.HStringUtils;
import compiler.scanner.model.HSCommand;
import compiler.scanner.model.HSComment;
import compiler.scanner.model.HSElement;
import compiler.scanner.model.HSTab;
import compiler.scanner.model.HSText;

/**
 * HCP�`���[�g�̃\�[�X��s���������͋@ �E�^�u�A�R�}���h�A�e�L�X�g�A�R�����g����͂��A�������i�[�������X�g�𐶐����܂��B
 * �E�Ⴆ�΁A�gHCP�̃\�[�X��s���������͂��� \in �\�[�X \out ��͌��ʁh������͂ł��܂��B
 * �E��L�̉�͌��ʂ́A�g[HSText:HCP�̃\�[�X��s���������͂���], [HSCommand:in], [HSText:�\�[�X],
 * [HSCommand:out],[HSText:��͌���]�h�ɂȂ�܂��B
 * 
 * @author Manabu Sugiura
 * @version $Id: HLineScanner.java,v 1.3 2009/09/10 03:48:32 macchan Exp $
 */
public class HLineScanner {

	/***********************
	 * �N���X�ϐ�
	 ***********************/

	// ��Ԓ�`
	private static final int READ_TAB = 1;
	private static final int READ_TEXT = 2;
	private static final int READ_COMMENT = 3;
	private static final int READ_COMMAND = 4;
	private static final int WAIT_FIRST_COMMAND = 5;
	private static final int WAIT_NEXT_COMMAND = 6;

	public static final char EOL = '\0';// �s�̏I������

	/***********************
	 * �C���X�^���X�ϐ�
	 ***********************/

	private int state = READ_TAB;

	private List<HSElement> elements = new ArrayList<HSElement>();// ��͌��ʁiSElement�̃��X�g�j
	private StringBuffer buffer = new StringBuffer();// �ǂݎ�蒆�̕����o�b�t�@

	/**
	 * ���͂��s���܂��B
	 * 
	 * @param c
	 *            ���͕���
	 */
	public void receive(char c) {
		switch (this.state) {
		case READ_TAB:
			this.processReadTab(c);
			break;
		case READ_TEXT:
			this.processReadText(c);
			break;
		case READ_COMMENT:
			this.processReadComment(c);
			break;
		case READ_COMMAND:
			this.processReadCommand(c);
			break;
		case WAIT_FIRST_COMMAND:
			this.processFirstCommand(c);
			break;
		case WAIT_NEXT_COMMAND:
			this.processNextCommand(c);
			break;
		default:
			throw new RuntimeException("�\�����Ȃ���Ԃł�");
		}
	}

	/**
	 * �^�u��ǂݎ�蒆�̏�Ԃ���̏����ł��B
	 * 
	 * @param c
	 *            ���͕���
	 */
	private void processReadTab(char c) {
		switch (c) {
		case '�@':
		case ' ':
			this.state = READ_TAB;
			break;
		case '\t':
			this.stockTabElement();
			this.state = READ_TAB;
			break;
		case '#':
			this.state = READ_COMMENT;
			break;
		case '\\':
			this.state = WAIT_FIRST_COMMAND;
			break;
		case EOL:
			break;
		default:
			this.appendChar(c);
			this.state = READ_TEXT;
			break;
		}
	}

	/**
	 * �e�L�X�g��ǂݎ�蒆�̏�Ԃ���̏����ł��B
	 * 
	 * @param c
	 *            ���͕���
	 */
	private void processReadText(char c) {
		switch (c) {
		case '\\':
			this.state = WAIT_NEXT_COMMAND;
			break;
		case EOL:
			this.stockTextElement();
			break;
		default:
			this.appendChar(c);
			this.state = READ_TEXT;
			break;
		}
	}

	/**
	 * �R�����g��ǂݎ�蒆�̏�Ԃ���̏����ł��B
	 * 
	 * @param c
	 *            ���͕���
	 */
	private void processReadComment(char c) {
		switch (c) {
		case EOL:
			this.stockCommentElement();
			break;
		default:
			this.appendChar(c);
			this.state = READ_COMMENT;
			break;
		}
	}

	/**
	 * �R�}���h��ǂݎ�蒆�̏�Ԃ���̏����ł��B
	 * 
	 * @param c
	 *            ���͕���
	 */
	private void processReadCommand(char c) {
		switch (c) {
		case '�@':
		case ' ':
		case '\t':
			this.stockCommandElement();
			this.state = READ_TEXT;
			break;
		case EOL:
			this.stockCommandElement();
			break;
		default:
			this.appendChar(c);
			this.state = READ_COMMAND;
			break;
		}
	}

	/**
	 * �ŏ��̃R�}���h��҂��Ă����Ԃ���̏����ł��B
	 * 
	 * @param c
	 *            ���͕���
	 */
	private void processFirstCommand(char c) {
		switch (c) {
		case '\\':
			this.appendChar(c);
			this.state = READ_TEXT;
			break;
		case EOL:
			this.stockCommandElement();
			break;
		default:
			this.appendChar(c);
			this.state = READ_COMMAND;
			break;
		}
	}

	/**
	 * 2�ڈȍ~�̃R�}���h��҂��Ă����Ԃ���̏����ł��B
	 * 
	 * @param c
	 *            ���͕���
	 */
	private void processNextCommand(char c) {
		switch (c) {
		case '\\':
			this.appendChar(c);
			this.state = READ_TEXT;
			break;
		case EOL:
			this.stockTextElement();
			break;
		default:
			this.stockTextElement();
			this.appendChar(c);
			this.state = READ_COMMAND;
			break;
		}
	}

	/**
	 * �^�u�����X�g�ɒǉ����܂��B
	 */
	private void stockTabElement() {
		this.elements.add(new HSTab());
	}

	/**
	 * �e�L�X�g�����X�g�ɒǉ����܂��B
	 */
	private void stockTextElement() {
		this.elements.add(new HSText(this.getBufferContents()));
		this.clearBuffer();
	}

	/**
	 * �R�����g�����X�g�ɒǉ����܂��B
	 */
	private void stockCommentElement() {
		this.elements.add(new HSComment(this.getBufferContents()));
		this.clearBuffer();
	}

	/**
	 * �R�}���h�����X�g�ɒǉ����܂��B
	 */
	private void stockCommandElement() {
		this.elements.add(new HSCommand(this.getBufferContents()));
		this.clearBuffer();
	}

	/**
	 * ������o�b�t�@�ɕ�����ǉ����܂��B
	 * 
	 * @param c
	 *            �ǉ����镶��
	 */
	private void appendChar(char c) {
		this.buffer.append(c);
	}

	/**
	 * ������o�b�t�@�����������܂��B
	 */
	private void clearBuffer() {
		this.buffer.delete(0, this.buffer.length());
	}

	/**
	 * ������o�b�t�@���當������擾���܂��B �擾�̍ۂɂ͕����񖖔��̗]���ȋ󔒂��폜���܂��B
	 * 
	 * @return �o�b�t�@����擾����������
	 */
	private String getBufferContents() {
		return HStringUtils.removeLastWhiteSpace(this.buffer.toString());
	}

	/**
	 * �X�L���������v�f���擾���܂��B
	 * 
	 * @return elements �X�L���������v�f
	 */
	public List<HSElement> getElements() {
		return this.elements;
	}

}