/*
 * PLNullChecker.java
 * Created on 2011/06/08 by macchan
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.utils;

/**
 * PLNullChecker
 */
public class CNullChecker<T> implements ICChecker<T> {

	public boolean check(T t) {
		return true;
	}

}
