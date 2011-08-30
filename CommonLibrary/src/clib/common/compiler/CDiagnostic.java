/*
 * CDiagnostic.java
 * Created on 2011/06/08
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.compiler;

import java.io.Serializable;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * @author macchan
 * 
 */
public class CDiagnostic implements Serializable {

	private static final long serialVersionUID = 1L;

	private Diagnostic.Kind kind;
	private String sourceName;
	private long position;
	private long startPosition;
	private long endPosition;
	private long lineNumber;
	private long columnNumber;
	private String code;
	private String message;

	public CDiagnostic() {
		this.kind = Diagnostic.Kind.ERROR;
		this.sourceName = "no name";
		this.position = 0;
		this.startPosition = 0;
		this.endPosition = 0;
		this.lineNumber = 0;
		this.columnNumber = 0;
		this.code = "";
		this.message = "";
	}

	public CDiagnostic(Diagnostic<JavaFileObject> original) {
		this.kind = original.getKind();
		if (original.getSource() != null) {
			this.sourceName = original.getSource().getName();
		} else {
			this.sourceName = "no name";
		}
		this.position = original.getPosition();
		this.startPosition = original.getStartPosition();
		this.endPosition = original.getEndPosition();
		this.lineNumber = original.getLineNumber();
		this.columnNumber = original.getColumnNumber();
		this.code = original.getCode();
		this.message = original.getMessage(null);
	}

	public Diagnostic.Kind getKind() {
		return kind;
	}

	public String getSourceName() {
		return sourceName;
	}

	public long getPosition() {
		return position;
	}

	public long getStartPosition() {
		return startPosition;
	}

	public long getEndPosition() {
		return endPosition;
	}

	public long getLineNumber() {
		return lineNumber;
	}

	public long getColumnNumber() {
		return columnNumber;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(Diagnostic.Kind kind) {
		this.kind = kind;
	}

	/**
	 * @param sourceName
	 *            the sourceName to set
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(long position) {
		this.position = position;
	}

	/**
	 * @param startPosition
	 *            the startPosition to set
	 */
	public void setStartPosition(long startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * @param endPosition
	 *            the endPosition to set
	 */
	public void setEndPosition(long endPosition) {
		this.endPosition = endPosition;
	}

	/**
	 * @param lineNumber
	 *            the lineNumber to set
	 */
	public void setLineNumber(long lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @param columnNumber
	 *            the columnNumber to set
	 */
	public void setColumnNumber(long columnNumber) {
		this.columnNumber = columnNumber;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
