/*
 * Helloworld.java
 * Created on 2011/06/21
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.execution;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author macchan
 * 
 */
public class TestProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Hello, world!");
		System.err.println("Hello, world!(to error)");
		System.out.println("args count=" + args.length);
		if (args.length == 1 && args[0].equals("request input")) {
			System.out.print("入力input>>");
			System.out.flush();
			// Console console = System.console();
			// String s = console.readLine();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));
			String s = reader.readLine();
			System.out.println("input:" + s);
		}
	}
}
