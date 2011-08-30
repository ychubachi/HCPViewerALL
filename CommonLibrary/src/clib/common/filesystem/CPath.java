/*
 * CPath.java
 * Created on 2010/02/12 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.filesystem;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import clib.common.collections.CList;

/**
 * CPath ファイルのパスを表現するクラス
 * 
 * @todo windowsのドライブに対応すること
 */
public class CPath implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String PATH_SEPARATOR = "/";
	public static final String DRIVE_SEPARATOR = ":";

	private LinkedList<String> elements;
	private String cashPathString;
	private String drive = "";

	public CPath(String pathString) {
		if (pathString == null) {
			pathString = "";
		}
		pathString = normalize(pathString);

		// 絶対パスに対応（/から始まると仮定）
		if (pathString.startsWith(PATH_SEPARATOR)) {
			drive = PATH_SEPARATOR;
			pathString = pathString.substring(1);
		}

		// 絶対パス，windowsフォーマットに対応
		int separatorIndex = pathString.indexOf(DRIVE_SEPARATOR);
		if (separatorIndex >= 0) {
			drive = pathString.substring(0, separatorIndex) + DRIVE_SEPARATOR
					+ PATH_SEPARATOR;
			pathString = pathString.substring(separatorIndex + 1);
		}

		// パスに分解して格納
		String[] paths = pathString.split(PATH_SEPARATOR);
		elements = new LinkedList<String>();
		for (int i = 0; i < paths.length; i++) {
			if (i != 0 && paths[i].equals(".")) {
				continue;
			}
			if (!(paths[i].length() == 0)/* use paths[i].isEmpty() in JDK1.6 */) {
				elements.add(paths[i]);
			}
		}

		// キャッシュを作成
		createCashPathString();
	}

	public CPath(CPath basePath, CPath relativePath) {
		this(basePath);
		elements.addAll(relativePath.elements);
		createCashPathString();
	}

	public CPath(CPath another) {
		this.elements = new LinkedList<String>(another.elements);
		this.drive = another.drive;
		createCashPathString();
	}

	private String normalize(String pathString) {
		return pathString.replace("\\", "/");
	}

	private void createCashPathString() {
		cashPathString = drive + CList.toString(elements, PATH_SEPARATOR);
	}

	public boolean isAbsolute() {
		return this.drive.length() != 0;
	}

	public boolean isRelative() {
		return !isAbsolute();
	}

	public CPath appendedPath(String path) {
		return this.appendedPath(new CPath(path));
	}

	public CPath appendedPath(CPath path) {
		return new CPath(this, path);
	}

	public CPath getParentPath() {
		if (!hasParent()) {
			return null;
		}

		CPath newPath = new CPath(this);
		newPath.chop();
		return newPath;
	}

	public boolean hasParent() {
		return this.elements.size() > 0;
	}

	private void chop() {
		this.elements.removeLast();
		createCashPathString();
	}

	public List<String> toPathStrings() {
		return new ArrayList<String>(this.elements);
	}

	public String toString() {
		return cashPathString;
	}

	public boolean equals(Object another) {
		if (another == null) {
			return false;
		}

		if (!(another instanceof CPath)) {
			return false;
		}

		return another.toString().equals(this.toString());
	}

	public int hashCode() {
		return this.toString().hashCode();
	}

	public File toJavaFile() {
		return new File(toString());
	}

	public CFilename getName() {
		return new CFilename(toJavaFile().getName());
	}

	public boolean exists() {
		return toJavaFile().exists();
	}

	public CPath getRelativePath(CPath base) {

		// prepare
		CPath newPath = new CPath(this);

		// fix drive
		if (this.isAbsolute() && base.isAbsolute()) {
			if (this.drive.equals(base.drive)) {
				newPath.drive = "";
			} else {
				return newPath;
			}
		} else if (this.isAbsolute() && base.isRelative()) {
			return newPath;
		} else if (this.isRelative() && base.isAbsolute()) {
			return new CPath("");
		} else {// this.isRelative() && base.isRelative()
		}

		// prepare
		int thisSize = this.elements.size();
		int baseSize = base.elements.size();
		int minsize = thisSize < baseSize ? thisSize : baseSize;

		// count for intersection
		int count = 0;
		for (count = 0; count < minsize; count++) {
			String thisElement = this.elements.get(count);
			String baseElement = base.elements.get(count);
			if (!thisElement.equals(baseElement)) {
				break;
			}
		}

		// fix a part of not intersection for this
		for (int i = 0; i < count; i++) {
			newPath.elements.removeFirst();
		}

		// fix a part of not intersection for base
		for (int i = 0; i < baseSize - count; i++) {
			newPath.elements.addFirst("..");
		}

		newPath.createCashPathString();
		return newPath;
	}

	// 単体テスト
	public static void main(String[] args) {

		// 基本機能
		testEql(new CPath("a"), "a");
		testEql(new CPath("a/a"), "a/a");
		testEql(new CPath("/"), "/");
		testEql(new CPath("C:/"), "C:/");
		testEql(new CPath("."), ".");
		testEql(new CPath(".."), "..");
		testEql(new CPath("C:/."), "C:/");
		testEql(new CPath("C:/./a"), "C:/a");
		testEql(new CPath("./a"), "./a");
		System.out.println(new CPath(new File("a").getAbsolutePath()));

		// append, chop
		testEql(new CPath("hoge"), "hoge");
		testEql(new CPath("hoge/hoge"), "hoge/hoge");
		testEql(new CPath("hoge").appendedPath("hoge"), "hoge/hoge");
		testEql(new CPath("hoge/").appendedPath("hoge"), "hoge/hoge");
		testEql(new CPath("hoge").appendedPath(new CPath("hoge")), "hoge/hoge");
		testEql(new CPath("hoge/").appendedPath(new CPath("hoge")), "hoge/hoge");
		testEql(new CPath("hoge/hoge").getParentPath(), "hoge");
		testEql(new CPath("hoge/hoge/").getParentPath(), "hoge");
		testEql(new CPath("hoge/hoge").getParentPath().getParentPath(), "");
		testEql(new CPath("hoge/hoge").getParentPath().getParentPath()
				.getParentPath(), null);

		// relative path
		relativeTest("", "", "");
		relativeTest("a/b/c", "", "a/b/c");
		relativeTest("a/b/c", "a", "b/c");
		relativeTest("a/b/c", "a/b", "c");
		relativeTest("a/b/c", "a/b/c", "");
		relativeTest("a/b/c", "a/b/c/d", "..");
		relativeTest("a/b/c", "a/d/c", "../../b/c");
		relativeTest("/a/b/c", "/a/b", "c");
		relativeTest("C:/a/b/c", "C:/a/b", "c");
		relativeTest("C:/a/b/c", "D:/a/b", "C:/a/b/c");
		relativeTest("C:/a/b/c", "a/b", "C:/a/b/c");
		relativeTest("a/b/c", "C:/a/b", "");// 例外
	}

	private static void testEql(Object value, Object expected) {
		boolean result = false;
		if (value == null) {
			result = value == expected;
		} else {
			result = value.toString().equals(expected);
		}
		System.out.println(result + " : " + value);
	}

	private static void test(CPath path, String expected) {
		System.out.println(path.toString().equals(expected) + " : "
				+ path.toString());
	}

	private static void relativeTest(String path1, String path2, String expected) {
		test(new CPath(path1).getRelativePath(new CPath(path2)), expected);
	}
}
