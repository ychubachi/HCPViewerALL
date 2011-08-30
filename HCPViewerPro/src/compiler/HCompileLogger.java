/*
 * @(#)HCompileLogger.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

/**
 * �R���p�C������̃��b�Z�[�W��ʒm���郍�K�[
 * @author Manabu Sugiura
 * @version $Id: HCompileLogger.java,v 1.9 2009/09/10 03:48:30 macchan Exp $
 */
public class HCompileLogger {

	/***********************
	 *  �N���X�ϐ�
	 ***********************/

	//���x��
	private static final String ERROR_PREFIX = "[ERROR]";
	private static final String WARNING_PREFIX = "[WARN]";
	private static final String INFO_PREFIX = "[INFO]";
	private static final String TOTAL_MESSAGE = "�G���[�F{0} �x���F{1} ���F{2}";

	private static final String NEW_LINE = System.getProperty("line.separator");

	private static final int UNKNOWN_LINE_NUMBER = -1;

	private static HCompileLogger instance = null;

	private static OutputStream stream = null;
//	private static File source = null;
	private String sourceName = "unkownSource";

	//�ʒm�񐔃J�E���^
	private static int errorCount = 0;
	private static int warnCount = 0;
	private static int infoCount = 0;

	/**
	 * �R���X�g���N�^
	 */
	private HCompileLogger() {
		//Do nothing.
	}

	public static HCompileLogger getInstance() {
		if (instance == null) {
			instance = new HCompileLogger();
		}
		return instance;
	}

	public OutputStream getStream() {
		return stream;
	}

	public void setStream(OutputStream newStream) {
		stream = newStream;
	}

//	public void setSource(File newSource) {
//		source = newSource;
//	}
	
	public void setSourceName(String newSourceName) {
		this.sourceName = newSourceName;
	}

	/***********************
	 * �ʒm�֘A
	 ***********************/

	public void showErrorMessage(String message, int lineNumber) {
		try {
			stream.write(format(ERROR_PREFIX, message, lineNumber));
			errorCount++;
		} catch (IOException e) {
			throw new HCompileException(e);
		}
	}

	public void showErrorMessage(String message) {
		showErrorMessage(message, UNKNOWN_LINE_NUMBER);
	}

	public void showWarningMessage(String message, int lineNumber) {
		try {
			stream.write(format(WARNING_PREFIX, message, lineNumber));
			warnCount++;
		} catch (IOException e) {
			throw new HCompileException(e);
		}
	}

	public void showWarningMessage(String message) {
		try {
			stream.write(format(WARNING_PREFIX, message, UNKNOWN_LINE_NUMBER));
			warnCount++;
		} catch (IOException e) {
			throw new HCompileException(e);
		}
	}

	public void showInformationMessage(String message, int lineNumber) {
		try {
			stream.write(format(INFO_PREFIX, message, lineNumber));
			infoCount++;
		} catch (IOException e) {
			throw new HCompileException(e);
		}
	}

	public void showInformationMessage(String message) {
		try {
			stream.write(format(INFO_PREFIX, message, UNKNOWN_LINE_NUMBER));
			infoCount++;
		} catch (IOException e) {
			throw new HCompileException(e);
		}
	}

	public void printTotal() {
		showInformationMessage("�R���p�C�������m" + makeTotalCountString() + "�n");
		resetCounter();
	}

	/***********************
	 * �ʒm�p���[�e�B���e�B
	 ***********************/

	private byte[] format(String prefix, String message, int lineNumber) {
		StringBuffer buffer = new StringBuffer();

		//�ړ����x���ƃ\�[�X��
		buffer.append(prefix + " " + sourceName);

		//�s�ԍ�
		if (lineNumber != UNKNOWN_LINE_NUMBER) { //�s���łȂ����
			buffer.append(" : " + lineNumber + "�s��");
		}

		//���b�Z�[�W
		buffer.append(" - " + message + NEW_LINE);

		return buffer.toString().getBytes();
	}

	private String makeTotalCountString() {
		int[] counters = {errorCount, warnCount, infoCount};
		String[] args = convertToStringArray(counters);
		return MessageFormat.format(TOTAL_MESSAGE, (Object[])args);
	}

	private void resetCounter() {
		errorCount = 0;
		warnCount = 0;
		infoCount = 0;
	}

	private String[] convertToStringArray(int[] array) {
		String[] res = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			res[i] = String.valueOf(array[i]);
		}
		return res;
	}

}