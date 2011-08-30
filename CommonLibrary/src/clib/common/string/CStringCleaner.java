/*
 * CStringCleaner.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.string;

public class CStringCleaner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(CStringCleaner.cleaning("abc def"));
		System.out.println(CStringCleaner.cleaning("abc def  "));
		System.out.println(CStringCleaner.cleaning("   abc def"));
		System.out.println(CStringCleaner.cleaning("abc  def"));
	}

	public static String cleaning(String line) {
		CStringCleaner cleaner = new CStringCleaner();
		int len = line.length();
		for (int i = 0; i < len; i++) {
			cleaner.put(line.charAt(i));
		}
		return cleaner.getString();
	}

	private enum State {
		INIT, IN_WORD, OUT_WORD
	};

	private State state = State.INIT;
	private StringBuffer buf = new StringBuffer();

	public String getString() {
		return buf.toString();
	}

	public void put(char c) {
		switch (state) {
		case INIT:
			switch (c) {
			case ' ':
			case '\t':
				break;
			default:
				buf.append(c);
				state = State.IN_WORD;
				break;
			}
			break;
		case IN_WORD:
			switch (c) {
			case ' ':
			case '\t':
				state = State.OUT_WORD;
				break;
			default:
				buf.append(c);
				break;
			}
			break;
		case OUT_WORD:
			switch (c) {
			case ' ':
			case '\t':
				break;
			default:
				buf.append(' ');
				buf.append(c);
				state = State.IN_WORD;
				break;
			}
			break;
		default:
			throw new RuntimeException();
		}
	}

}
