/*
 * CClasspathManager.java
 * Created on 2011/06/22
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import clib.common.filesystem.CDirectory;
import clib.common.filesystem.CFile;
import clib.common.filesystem.CFileFilter;
import clib.common.filesystem.CFileSystem;
import clib.common.filesystem.CPath;

/**
 * @author macchan
 */
public class CClasspathManager {

	private List<String> classpaths = new ArrayList<String>();

	private CDirectory base;

	public CClasspathManager(CDirectory base) {
		this.base = base;
	}

	public void addClasspath(CPath path) {
		if (path.isRelative()) {
			path = base.getAbsolutePath().appendedPath(path);
		}
		classpaths.add(path.toString());
	}

	public void addClasspathDir(CPath path) {
		if (path.isRelative()) {
			path = base.getAbsolutePath().appendedPath(path);
		}
		CDirectory dir = CFileSystem.findDirectory(path);

		List<CFile> files = dir.getFileChildren(CFileFilter
				.ACCEPT_BY_EXTENSION_FILTER("jar"));

		for (CFile file : files) {
			addClasspath(file.getAbsolutePath());
		}
	}

	public String createClassPathString(CPath path) {
		CDirectory binDir;
		if (path.isRelative()) {
			binDir = base.findOrCreateDirectory(path);
		} else {// absolute
			binDir = CFileSystem.findDirectory(path);
		}
		String libString = binDir.getAbsolutePath().toString();

		for (String classPath : classpaths) {
			libString += File.pathSeparator + classPath;
		}

		return libString;
	}
}
