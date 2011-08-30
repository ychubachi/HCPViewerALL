/*
 * CThread.java
 * Created on 2010/02/12 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * CThread
 */
public class CThread {

	private static final int DEFAULT_INTERVAL = 1000;// 1s
	private static final int DEFAULT_PRIORITY = Thread.NORM_PRIORITY;

	private Object lock = new Object();
	private volatile Thread thread;
	private boolean running = false;
	private int interval = DEFAULT_INTERVAL;
	private int priority = DEFAULT_PRIORITY;
	private ICRunnable runnable;

	private String name = "CThread";

	private List<ICThreadStateListener> listeners = new ArrayList<ICThreadStateListener>();

	protected CThread() {
	}

	public CThread(ICRunnable runnable) {
		initialize(runnable);
	}

	protected void initialize(ICRunnable runnable) {
		this.runnable = runnable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * スレッドを開始する． （既に起動している場合には，何もしない）
	 */
	public void start() {
		synchronized (lock) {
			if (!runnable.allowStart()) {
				return;
			}

			// 二重起動を防止する
			if (running) {
				return;
			}

			running = true;
			lock.notifyAll();

			// スレッドを開始する
			thread = new Thread(name) {
				public void run() {
					synchronized (lock) {
						fireThreadStarted();
						try {
							runnable.handlePrepareStart();
							while (thread == Thread.currentThread()
									&& !runnable.isFinished()) {
								runnable.handleProcessStep();
								lock.wait(interval);
							}
						} catch (InterruptedException ex) {
							// do nothing
						} catch (CIntentionalInterruptedException ex) {
							// do nothing
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						runnable.handlePrepareStop();
						running = false;
						lock.notifyAll();
						thread = null;
						fireThreadStopped();
					}
				}
			};
			thread.setPriority(getPriority());
			thread.start();
		}
	}

	/**
	 * スレッドをとめる
	 */
	public void stop() {
		// synchronized (lock) {
		if (isRunning()) {
			thread.interrupt();
		}
		// }
	}

	public void waitForStop() {
		synchronized (lock) {
			if (thread == Thread.currentThread()) {
				throw new RuntimeException();
			}
			try {
				if (isRunning()) {
					lock.wait();
				}

			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	public boolean isRunning() {
		return running;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		if (interval < 1) {
			throw new RuntimeException();
		}
		this.interval = interval;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void addStateListener(ICThreadStateListener listener) {
		this.listeners.add(listener);
	}

	public void removeStateListener(ICThreadStateListener listener) {
		this.listeners.remove(listener);
	}

	protected void fireThreadStarted() {
		for (ICThreadStateListener listener : listeners) {
			listener.threadStarted();
		}
	}

	protected void fireThreadStopped() {
		for (ICThreadStateListener listener : listeners) {
			listener.threadStopped();
		}
	}

	public static void sleep(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void sleepForInterrupt(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException ex) {
			throw new CIntentionalInterruptedException(ex);
		}
	}
}

class CIntentionalInterruptedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CIntentionalInterruptedException(InterruptedException ex) {
		super(ex);
	}

}
