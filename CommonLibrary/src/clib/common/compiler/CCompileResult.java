/*
 * CCompileResult.java
 * Created on 2011/06/03
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.compiler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import clib.common.filesystem.CPath;

/**
 * @author macchan
 */
public class CCompileResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean success;

	private List<CPath> targetPaths;
	private List<CDiagnostic> diagnostics;

	public CCompileResult(boolean success, List<CPath> targetPaths,
			List<CDiagnostic> diagnostics) {
		this.success = success;
		this.targetPaths = new ArrayList<CPath>(targetPaths);
		this.diagnostics = new ArrayList<CDiagnostic>(diagnostics);
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @return the targetPaths
	 */
	public List<CPath> getTargetPaths() {
		return targetPaths;
	}

	/**
	 * @return the diagnostics
	 */
	public List<CDiagnostic> getDiagnostics() {
		return diagnostics;
	}

}
