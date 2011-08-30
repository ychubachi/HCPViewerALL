/*
 * ICProgressMonitor.java
 * Created on Sep 16, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.utils;

/**
 * @author macchan
 * 
 */
public interface ICProgressMonitor {

	public void setWorkTitle(String title);

	public void setMax(int n);

	public void progress(int x);

}
