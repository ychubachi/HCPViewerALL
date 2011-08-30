/*
 * @(#)HDataListScanner.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.extenddata.datascanner;

import java.util.ArrayList;
import java.util.List;

import compiler.HCompileLogger;
import compiler.HStringUtils;

/**
 * �f�[�^�L�q�̎����͋@ 
 * �E�f�[�^�̋�؂����͂��āC1�`N�̃f�[�^�i�̖��O�j�ɉ�͂��܂�
 * �E�Ⴆ�΁C�g���͕����C�o�͕����h�Ȃ�C�g[���͕���][�o�͕���]�Ƃ���2�̃f�[�^�ɉ�͂��܂�
 * �EHElementScanner�ŁCText�Ƃ��ĔF������C���f�[�^�L�q���ƒf�肳�ꂽ�ӏ����ēx�X�L�������邽�߂Ɏg�p����܂�
 * @author Manabu Sugiura
 * @version $Id: HDataListScanner.java,v 1.3 2009/09/10 03:48:33 macchan Exp $
 */
public class HDataListScanner {

	/***********************
	 * �N���X�ϐ�
	 ***********************/

	//��Ԓ�`
	private static final int WAIT_FIRST_DATA = 0;
	private static final int READ_DATA = 1;
	private static final int WAIT_NEXT_DATA = 2;
	private static final int CANCEL = 3;

	//�G���[���b�Z�[�W
	private static final String NO_DATA_EMES = "��؂蕶���ɑ΂��ăf�[�^�̋L�q���s�����Ă��܂�";
	private static final String NO_SUCH_STATE_EMES = "��`����Ă��Ȃ���Ԃł�";

	public static final char EOL = '\0';

	/***********************
	 * �C���X�^���X�ϐ�
	 ***********************/

	private int state = WAIT_FIRST_DATA;
	private List<HSData> dataList = new ArrayList<HSData>();//��͌��ʁiHSData�̃��X�g�j 
	private StringBuffer buffer = new StringBuffer();//�ǂݎ�蒆�̕����o�b�t�@

	/**
	 * �R���X�g���N�^
	 */
	public HDataListScanner() {
		super();
	}

	/**
	 * �X�L�������ʂ��擾���܂��B
	 * @return dataList �f�[�^�̃��X�g
	 */
	public List<HSData> getDataList() {
		return this.dataList;
	}

	/**
	 * ���͂��s���܂��B
	 * @param c ���͕���
	 * @param lineNumber �s�ԍ�
	 */
	public void receive(char c, int lineNumber) {
		switch (this.state) {
			case WAIT_FIRST_DATA :
				this.processWaitFirstData(c, lineNumber);
				break;
			case READ_DATA :
				this.processReadData(c);
				break;
			case WAIT_NEXT_DATA :
				this.processWaitNextData(c, lineNumber);
				break;
			case CANCEL :
				break;
			default :
				throw new RuntimeException(NO_SUCH_STATE_EMES);
		}
	}

	/**
	 * �ŏ��̃f�[�^��҂��Ă����Ԃ���̏����ł��B
	 * @param c ���͕���
	 * @param lineNumber �s�ԍ�
	 */
	private void processWaitFirstData(char c, int lineNumber) {
		switch (c) {
			case '�@' :
			case ' ' :
			case '\t' :
				this.state = WAIT_FIRST_DATA;
				break;
			case ',' :
			case '�C' :
			case '�A' :
				this.showWarningMessage(lineNumber);
				this.state = CANCEL;
				break;
			case EOL :
				this.showWarningMessage(lineNumber);
				break;
			default :
				this.appendBuffer(c);
				this.state = READ_DATA;
				break;
		}
	}

	/**
	 * �f�[�^��ǂݎ�蒆����̏����ł��B
	 * @param c ���͕���
	 */
	private void processReadData(char c) {
		switch (c) {
			case ',' :
			case '�C' :
			case '�A' :
				this.stockDataElement();
				this.state = WAIT_NEXT_DATA;
				break;
			case EOL :
				this.stockDataElement();
				break;
			default :
				this.appendBuffer(c);
				this.state = READ_DATA;
				break;
		}
	}

	/**
	 * 2�ڈȍ~�̃f�[�^��҂��Ă����Ԃ���̏����ł��B
	 * @param c ���͕���
	 * @param lineNumber �s�ԍ�
	 */
	private void processWaitNextData(char c, int lineNumber) {
		switch (c) {
			case '�@' :
			case ' ' :
			case '\t' :
				this.state = WAIT_NEXT_DATA;
				break;
			case ',' :
			case '�C' :
			case '�A' :
				this.showWarningMessage(lineNumber);
				this.state = CANCEL;
				break;
			case EOL :
				this.showWarningMessage(lineNumber);
				break;
			default :
				this.appendBuffer(c);
				this.state = READ_DATA;
				break;
		}
	}

	/**
	 * �ǂݎ�蒆�̌x����ʒm���܂��B
	 * @param lineNumber �s�ԍ�
	 */
	private void showWarningMessage(int lineNumber) {
		HCompileLogger logger = HCompileLogger.getInstance();
		logger.showWarningMessage(NO_DATA_EMES, lineNumber);
	}

	/**
	 * �f�[�^�����X�g�ɒǉ����܂��B
	 */
	private void stockDataElement() {
		this.dataList.add(new HSData(this.getBuffer()));
		this.clearBuffer();
	}

	/**
	 * ������o�b�t�@�ɕ�����ǉ����܂��B
	 * @param c �ǉ����镶��
	 */
	private void appendBuffer(char c) {
		this.buffer.append(c);
	}

	/**
	 * ������o�b�t�@���當������擾���܂��B �擾�̍ۂɂ͕����񖖔��̗]���ȋ󔒂��폜���܂��B
	 * @return �o�b�t�@����擾����������
	 */
	private String getBuffer() {
		return HStringUtils.removeLastWhiteSpace(this.buffer.toString());
	}

	/**
	 * ������o�b�t�@�����������܂��B
	 */
	private void clearBuffer() {
		this.buffer.delete(0, this.buffer.length());
	}

}