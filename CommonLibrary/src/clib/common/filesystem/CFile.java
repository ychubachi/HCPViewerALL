/*
 * CFile.java
 * Created on 2010/02/12 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.filesystem;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import clib.common.io.CFileInputStream;
import clib.common.io.CFileOutputStream;
import clib.common.io.CIOUtils;
import clib.common.io.CStreamReader;
import clib.common.io.CStreamWriter;
import clib.common.system.CEncoding;
import clib.common.system.CJavaSystem;

/**
 * CFile
 */
public class CFile extends CFileElement {

	public static final CEncoding ENCODING_IN_DEFAULT = CEncoding.JISAutoDetect;
	public static final CEncoding ENCODING_OUT_DEFAULT = CEncoding
			.getVMEncoding();

	public static final String CRLF = CJavaSystem.getInstance().getCRLF();

	private CEncoding encodingIn = ENCODING_IN_DEFAULT;
	private CEncoding encodingOut = ENCODING_OUT_DEFAULT;

	protected CFile(CPath path) {
		super(path);
		initialCheck();
	}

	protected CFile(File javaFile) {
		super(javaFile);
		initialCheck();
	}

	private void initialCheck() {
		if (this.getJavaFile().isDirectory()) {
			throw new RuntimeException(
					"This is not a file but a directory. path:"
							+ getJavaFile().getPath());
		}
	}

	public CFileInputStream openInputStream() {
		try {
			return new CFileInputStream(this.getJavaFile());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public CFileOutputStream openOutputStream() {
		return openOutputStream(false);
	}

	public CFileOutputStream openOutputStream(boolean append) {
		try {
			return new CFileOutputStream(this.getJavaFile(), append);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public CStreamReader openReader() {
		return new CStreamReader(openInputStream(), encodingIn);
	}

	public CStreamWriter openWriter() {
		return openWriter(false);
	}

	public CStreamWriter openWriter(boolean append) {
		return new CStreamWriter(openOutputStream(append), encodingOut);
	}

	public String loadText() {
		return loadText(CRLF);
	}

	public String loadText(String delim) {
		StringBuffer buf = new StringBuffer();
		CStreamReader reader = openReader();
		while (reader.hasMoreLine()) {
			buf.append(reader.getCurrentLine());
			reader.toNextLine();
			if (reader.hasMoreLine()) {
				buf.append(delim);
			}
		}
		reader.close();
		return buf.toString();
	}

	public List<String> loadTextAsList() {
		List<String> lines = new ArrayList<String>();
		CStreamReader reader;
		for (reader = openReader(); reader.hasMoreLine(); reader.toNextLine()) {
			lines.add(reader.getCurrentLine());
		}
		reader.close();
		return lines;
	}

	public void saveStream(InputStream in) {
		CIOUtils.copy(in, this.openOutputStream());
	}

	public void saveText(String text) {
		CStreamWriter writer = openWriter();
		Scanner scanner = new Scanner(text);
		while (scanner.hasNext()) {
			writer.write(scanner.nextLine());
			if (scanner.hasNext()) {
				writer.writeLineFeed();
			}
		}
		writer.close();
	}

	/**
	 * @param convertedLines
	 */
	public void saveTextFromList(List<String> lines) {
		CStreamWriter writer = openWriter();
		for (String line : lines) {
			writer.write(line);
			writer.writeLineFeed();
		}
		writer.close();
	}

	public void appendText(String text) {
		CStreamWriter writer = openWriter(true);
		writer.writeLineFeed(text);
		writer.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clib.common.filesystem.CFileElement#isDirectory()
	 */
	@Override
	public boolean isDirectory() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clib.common.filesystem.CFileElement#isFile()
	 */
	@Override
	public boolean isFile() {
		return true;
	}

	/**
	 * @param name
	 */
	public CFile copyTo(CFilename name) {
		return copyTo(name.toString());
	}

	/**
	 * @param name
	 */
	public CFile copyTo(String name) {
		return copyTo(getParentDirectory(), name);
	}

	/**
	 * @param recordFileDir
	 * @param name
	 */
	public CFile copyTo(CDirectory dir, CFilename name) {
		return copyTo(dir, name.toString());
	}

	/**
	 * @param recordFileDir
	 * @param name
	 */
	public CFile copyTo(CDirectory dir, String name) {
		CFile file = CFileSystem.getRootDirectory().findOrCreateFile(
				dir.getAbsolutePath().appendedPath(name));
		CIOUtils.copy(this.openInputStream(), file.openOutputStream());
		return file;
	}
	
	/**
	 * @param recordFileDir
	 * @param name
	 */
	public CFile copyTo(CDirectory dir, CPath path) {
		CFile file = dir.findOrCreateFile(path);
		CIOUtils.copy(this.openInputStream(), file.openOutputStream());
		return file;
	}

	/**********************************
	 * Getters and Setters
	 **********************************/

	public CEncoding getEncodingIn() {
		return encodingIn;
	}

	public void setEncodingIn(CEncoding encodingIn) {
		this.encodingIn = encodingIn;
	}

	public CEncoding getEncodingOut() {
		return encodingOut;
	}

	public void setEncodingOut(CEncoding encodingOut) {
		this.encodingOut = encodingOut;
	}

	public static void main(String[] args) throws Exception {
		CFile file = CFileSystem.getExecuteDirectory().findOrCreateFile(
				"test.txt");
		String text = "hoge";
		file.saveText(text);
		String loadText = file.loadText();
		System.out.println("result=" + text.equals(loadText) + " text=" + text
				+ " loadText=" + loadText);
	}
}
