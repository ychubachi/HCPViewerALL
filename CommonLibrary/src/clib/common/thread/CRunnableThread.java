/*
 * CRunnableThread.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.thread;

public abstract class CRunnableThread extends CThread implements ICRunnable {

	public CRunnableThread() {
		super();
		initialize(this);
	}

	public void handleProcessStep() {
	}

	public void handlePrepareStop() {
	}

	public void handlePrepareStart() {
	}

	public boolean allowStart() {
		return true;
	}

	public boolean isFinished() {
		return false;
	}

}
