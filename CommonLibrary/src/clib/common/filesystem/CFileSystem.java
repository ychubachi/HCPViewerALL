/*
 * CFileSystem.java
 * Created on 2010/02/12 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.filesystem;

import java.io.File;

/**
 * CFileSystem
 */
public class CFileSystem {

	private static final CDirectory root = new CDirectory(new CPath("/"));
	private static final CDirectory executeRoot = new CDirectory(new CPath("."));

	public static CDirectory getRootDirectory() {
		return root;
	}

	public static CDirectory getExecuteDirectory() {
		return executeRoot;
	}

	public static CFile findFile(String path) {
		return findFile(new CPath(path));
	}

	public static CFile findFile(CPath path) {
		return new CFile(path);
	}

	public static CDirectory findDirectory(String path) {
		return findDirectory(new CPath(path));
	}

	public static CDirectory findDirectory(CPath path) {
		return new CDirectory(path);
	}

	public static CFileElement convertToCFile(File javaFile) {
		if (javaFile == null) {
			return null;
		}
		if (!javaFile.exists()) {
			return null;
		}

		if (javaFile.isFile()) {
			return new CFile(javaFile);
		} else {// directory
			return new CDirectory(javaFile);
		}
	}
}
