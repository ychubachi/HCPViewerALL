/*
 * ICThreadStateListener.java
 * Created on May 6, 2011 
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.thread;

/**
 * @author macchan
 * 
 */
public interface ICThreadStateListener {

	public void threadStarted();

	public void threadStopped();

}
