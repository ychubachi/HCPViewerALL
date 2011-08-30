/*
 * CCSVWriter.java
 * Created on Nov 15, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author macchan
 * 
 */
public class CCSVWriter {

	private List<String> elements = new ArrayList<String>();

	public CCSVWriter() {
	}

	public void addElement(Object element) {
		String str;
		if (element == null) {
			str = "null";
		} else if (element instanceof String) {
			str = (String) element;
		} else {
			str = element.toString();
		}
		elements.add(str);
	}

	public String create() {
		StringBuffer buf = new StringBuffer();
		for (String element : elements) {
			if (buf.length() > 0) {
				buf.append(',');
			}
			element = element.replaceAll("\"", "\"\"");
			buf.append("\"" + element + "\"");
		}
		return buf.toString();
	}
}
