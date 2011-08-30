/*
 * CJavaSystem.java
 * Created on 2010/02/16 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.system;

import java.text.DecimalFormat;
import java.util.Properties;

/**
 * CJavaSystem
 */
public class CJavaSystem {

	private static CJavaSystem instance;

	public static CJavaSystem getInstance() {
		if (instance == null) {
			instance = new CJavaSystem();
		}
		return instance;
	}

	private CJavaSystem() {
	}

	// public CEncoding getEncoding() {
	// return new CEncoding(System.getProperty("sun.jnu.encoding"));
	// }

	public String getCRLF() {
		return System.getProperty("line.separator");
	}

	public boolean isWindows() {
		return System.getProperty("os.name").startsWith("Windows");
	}

	public boolean isMac() {
		return System.getProperty("os.name").startsWith("Mac");
	}

	/**
	 * Java 仮想マシンのメモリ総容量、使用量、 使用を試みる最大メモリ容量の情報を返します。
	 * 
	 * @return Java 仮想マシンのメモリ情報
	 */
	public String getMemoryInfo() {
		DecimalFormat f1 = new DecimalFormat("#,###KB");
		DecimalFormat f2 = new DecimalFormat("##.#");
		long free = Runtime.getRuntime().freeMemory() / 1024;
		long total = Runtime.getRuntime().totalMemory() / 1024;
		long max = Runtime.getRuntime().maxMemory() / 1024;
		long used = total - free;
		double ratio = (used * 100 / (double) total);
		String info = "Java Memory : Total=" + f1.format(total) + "、"
				+ "Using=" + f1.format(used) + " (" + f2.format(ratio) + "%)、"
				+ "Max=" + f1.format(max);
		return info;
	}

	public static void main(String[] args) {
		Properties prop = System.getProperties();
		for (Object key : prop.keySet()) {
			System.out.print(key);
			System.out.print("=");
			System.out.print(prop.get(key));
			System.out.println();
		}
		// System.out.println(System.getProperties());
	}
}
