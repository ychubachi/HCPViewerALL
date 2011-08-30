/*
 * @(#)HCompileException.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler;

/**
 * コンパイル時に発生する例外
 * @author Manabu Sugiura
 * @version $Id: HCompileException.java,v 1.4 2009/09/10 03:48:31 macchan Exp $
 */
public class HCompileException extends RuntimeException {
	public static final long serialVersionUID = 1L;
	/**
	 * コンストラクタ
	 */
	public HCompileException() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param message メッセージ
	 */
	public HCompileException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ
	 * @param cause 原因
	 */
	public HCompileException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ
	 * @param message メッセージ
	 * @param cause 原因
	 */
	public HCompileException(String message, Throwable cause) {
		super(message, cause);
	}

}