/*
 * CCSVReader.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.utils;

import java.util.ArrayList;
import java.util.List;

public class CCSVReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(CCSVReader.read("a,b"));
		System.out.println(CCSVReader.read("a,b,"));
		System.out.println(CCSVReader.read(",a,b"));
		System.out.println(CCSVReader.read("a,,b"));
		System.out.println(CCSVReader.read("a,\"b\""));
		System.out.println(CCSVReader.read("\",ho\",\"\"\"a\""));
	}

	public static List<String> read(String line) {
		CCSVReader reader = new CCSVReader();
		int len = line.length();
		for (int i = 0; i < len; i++) {
			reader.put(line.charAt(i));
		}
		reader.finish();
		return reader.getElements();
	}

	private CCSVReader() {
	}

	private enum State {
		START, IN_QUOTE, OUT_QUOTE, IN_QUOTE_PENDING, FINISHED
	};

	private List<String> elements = new ArrayList<String>();
	private State state = State.START;
	private StringBuffer buf = new StringBuffer();

	public List<String> getElements() {
		if (state != State.FINISHED) {
			throw new RuntimeException();
		}
		return elements;
	}

	public void finish() {
		push();
		state = State.FINISHED;
	}

	public void put(char c) {
		switch (state) {
		case START:
			switch (c) {
			case ',':
				push();
				break;
			case '"':
				state = State.IN_QUOTE;
				break;
			default:
				state = State.OUT_QUOTE;
				buf.append(c);
				break;
			}
			break;
		case IN_QUOTE:
			switch (c) {
			case ',':
				buf.append(c);
				break;
			case '"':
				state = State.IN_QUOTE_PENDING;
				break;
			default:
				buf.append(c);
				break;
			}
			break;
		case IN_QUOTE_PENDING:
			switch (c) {
			case ',':
				push();
				state = State.START;
				break;
			case '"':
				buf.append(c);
				state = State.IN_QUOTE;
				break;
			default:
				throw new RuntimeException();
			}
			break;
		case OUT_QUOTE:
			switch (c) {
			case ',':
				push();
				state = State.START;
				break;
			case '"':
				throw new RuntimeException();
			default:
				buf.append(c);
				break;
			}
			break;
		case FINISHED:
			break;
		default:
			throw new RuntimeException();
		}
	}

	private void push() {
		elements.add(buf.toString());
		buf = new StringBuffer();
	}

}
