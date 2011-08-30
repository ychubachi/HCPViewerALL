/*
 * CObjectLocker.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.thread;

//This class has not tested yet.
public class CObjectLocker {

	private Object lockObject = new Object();
	private boolean locking = false;

	public void getLock() {
		try {
			synchronized (lockObject) {
				if (locking == true) {
					lockObject.wait();
					locking = true;
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	public void releaseLock() {
		try {
			synchronized (lockObject) {
				lockObject.notifyAll();
				locking = false;
			}
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}
}
