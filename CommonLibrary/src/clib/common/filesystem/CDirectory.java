/*
 * CDirectory.java
 * Created on 2010/02/12 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.filesystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * CDirectory
 */
public class CDirectory extends CFileElement {

	protected CDirectory(CPath path) {
		super(path);
		initialCheck();
	}

	protected CDirectory(File javaFile) {
		super(javaFile);
		initialCheck();
	}

	private void initialCheck() {
		if (this.getJavaFile().isFile()) {
			throw new RuntimeException(
					"This is not a direcotry but a file. path:"
							+ getJavaFile().getPath());
		}
	}

	/**
	 * 子要素を取り出す
	 * 
	 * @return
	 */
	public List<CFileElement> getChildren() {
		return getChildren(CFileFilter.ALL_ACCEPT_FILTER());
	}

	/**
	 * 子要素を取り出す
	 * 
	 * @return
	 */
	public List<CFileElement> getChildren(CFileFilter filter) {
		ArrayList<CFileElement> children = new ArrayList<CFileElement>();
		File[] files = getJavaFile().listFiles();
		for (int i = 0; i < files.length; i++) {
			CFileElement element = findFileByJavaFile(files[i]);
			if (filter.accept(element)) {
				children.add(element);
			}
		}
		return children;
	}

	/**
	 * Directoryの子要素を取り出す
	 * 
	 * @return
	 */
	public List<CDirectory> getDirectoryChildren() {
		return getDirectoryChildren(CFileFilter.ALL_ACCEPT_FILTER());
	}

	/**
	 * Directoryの子要素を取り出す
	 * 
	 * @return
	 */
	public List<CDirectory> getDirectoryChildren(CFileFilter filter) {
		ArrayList<CDirectory> dirchildren = new ArrayList<CDirectory>();
		List<CFileElement> children = getChildren(filter);

		for (CFileElement cFileElement : children) {
			if (cFileElement.isDirectory()) {
				dirchildren.add((CDirectory) cFileElement);
			}
		}
		return dirchildren;
	}

	/**
	 * Fileの子要素を取り出す
	 * 
	 * @return
	 */
	public List<CFile> getFileChildren() {
		return getFileChildren(CFileFilter.ALL_ACCEPT_FILTER());
	}

	/**
	 * Fileの子要素を取り出す
	 * 
	 * @return
	 */
	public List<CFile> getFileChildren(CFileFilter filter) {
		ArrayList<CFile> filechildren = new ArrayList<CFile>();
		List<CFileElement> children = getChildren(filter);

		for (CFileElement cFileElement : children) {
			if (cFileElement.isFile()) {
				filechildren.add((CFile) cFileElement);
			}
		}
		return filechildren;
	}

	/**
	 * @param path
	 * @return
	 */
	public CFileElement findChild(CPath path) {
		CFile file = findFile(path);
		if (file != null) {
			return file;
		}
		CDirectory dir = findDirectory(path);
		if (dir != null) {
			return dir;
		}
		return null;
	}

	public CFile findOFile(CFilename name) {
		return findFile(name.toString());
	}

	public CFile findFile(String fullname) {
		return findFile(new CPath(fullname));
	}

	/**
	 * @param path
	 * @return
	 */
	public CFile findFile(CPath path) {
		try {
			CPath filepath = getAbsolutePath().appendedPath(path);
			return new CFile(filepath);
		} catch (Exception ex) {
			// ex.printStackTrace();
			return null;
		}
	}

	public CFile findOrCreateFile(CFilename name) {
		return findOrCreateFile(name.toString());
	}

	public CFile findOrCreateFile(String fullname) {
		return findOrCreateFile(new CPath(fullname));
	}

	public CFile findOrCreateFile(CPath path) {
		try {
			CPath filepath = getAbsolutePath().appendedPath(path);

			File file = new File(filepath.toString());
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			return new CFile(filepath);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public CDirectory findDirectory(String path) {
		return findDirectory(new CPath(path));
	}

	public CDirectory findDirectory(CPath path) {
		try {
			CPath filepath = getAbsolutePath().appendedPath(path);
			return new CDirectory(filepath);
		} catch (Exception ex) {
			// ex.printStackTrace();
			return null;
		}
	}

	public CDirectory findOrCreateDirectory(String path) {
		return findOrCreateDirectory(new CPath(path));
	}

	public CDirectory findOrCreateDirectory(CPath path) {
		try {
			CPath filepath = getAbsolutePath().appendedPath(path);
			File file = new File(filepath.toString());
			if (!file.exists()) {
				file.mkdirs();
			}
			return new CDirectory(filepath);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * @param file
	 * @return
	 */
	private CFileElement findFileByJavaFile(File file) {
		if (file.isDirectory()) {
			return new CDirectory(new CPath(file.getAbsolutePath()));
		} else {
			return new CFile(new CPath(file.getAbsolutePath()));
		}
	}

	protected boolean delete(boolean message) {
		List<CFileElement> children = getChildren();
		for (CFileElement each : children) {
			each.delete(message);
		}
		return super.delete(message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clib.common.filesystem.CFileElement#isDirectory()
	 */
	@Override
	public boolean isDirectory() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clib.common.filesystem.CFileElement#isFile()
	 */
	@Override
	public boolean isFile() {
		return false;
	}

}
