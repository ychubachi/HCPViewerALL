/*
 * @(#)HCompileException.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler;

/**
 * �R���p�C�����ɔ��������O
 * @author Manabu Sugiura
 * @version $Id: HCompileException.java,v 1.4 2009/09/10 03:48:31 macchan Exp $
 */
public class HCompileException extends RuntimeException {
	public static final long serialVersionUID = 1L;
	/**
	 * �R���X�g���N�^
	 */
	public HCompileException() {
		super();
	}

	/**
	 * �R���X�g���N�^
	 * @param message ���b�Z�[�W
	 */
	public HCompileException(String message) {
		super(message);
	}

	/**
	 * �R���X�g���N�^
	 * @param cause ����
	 */
	public HCompileException(Throwable cause) {
		super(cause);
	}

	/**
	 * �R���X�g���N�^
	 * @param message ���b�Z�[�W
	 * @param cause ����
	 */
	public HCompileException(String message, Throwable cause) {
		super(message, cause);
	}

}