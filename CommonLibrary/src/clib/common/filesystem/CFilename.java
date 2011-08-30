/*
 * CFilename.java
 * Created on 2010/02/15 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.filesystem;

/**
 * CFilename
 */
public class CFilename {

	public static final char SEPARATOR = '.';

	private String name;
	private String extension;
	private String cash_fullname;

	public CFilename(String name, String extension) {
		this(name + SEPARATOR + extension);
	}

	public CFilename(String fullname) {
		setFullName(fullname);
	}

	public void setFullName(String name) {
		int index = name.lastIndexOf(SEPARATOR);
		if (index <= 0 || index + 1 >= name.length()) {
			this.name = name;
		} else {
			this.name = name.substring(0, index);
			this.extension = name.substring(index + 1);
		}
		createCash();
	}

	public void setName(String name) {
		this.name = name;
		createCash();
	}

	public void setExtension(String extension) {
		this.extension = extension;
		createCash();
	}

	private void createCash() {
		if (extension == null || extension.length() == 0) {
			this.cash_fullname = name;
			return;
		}
		this.cash_fullname = name + SEPARATOR + extension;
	}

	public String getName() {
		return name;
	}

	public String getExtension() {
		if (extension == null) {
			return "";
		}
		return extension;
	}

	public String toString() {
		return cash_fullname;
	}

	public static void main(String[] args) {
		test("Hoge.java");
		test("Hoge");
		test(".java");
		test("Hoge.");
		test("Hoge.java.java");
	}

	private static void test(String text) {
		CFilename name = new CFilename(text);
		System.out.println(name.getName());
		System.out.println(name.getExtension());
		System.out.println(name.toString());
	}
}
