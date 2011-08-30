/*
 * CArrays.java
 * Created on 2011/07/09
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.collections;

/**
 * @author macchan
 * 
 */
public class CArrays {

	public static String toString(Object[] array) {
		StringBuffer buf = new StringBuffer();
		buf.append("[");
		for (int i = 0; i < array.length; i++) {
			if (i != 0) {
				buf.append(", ");
			}
			buf.append(array[i]);
		}
		buf.append("]");
		return buf.toString();
	}
}
