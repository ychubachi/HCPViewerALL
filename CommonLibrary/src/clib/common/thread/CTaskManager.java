/*
 * CTaskManager.java
 * Created on Apr 17, 2010 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.thread;

import java.util.LinkedList;

/**
 * CTaskManager
 */
public class CTaskManager extends CRunnableThread {

	private Object lock = new Object();
	private LinkedList<ICTask> taskQueue = new LinkedList<ICTask>();
	private ICTask currentTask;

	/**
	 * Constructor
	 */
	public CTaskManager() {
		setInterval(10);
	}

	public void addTask(ICTask task) {
		synchronized (lock) {
			taskQueue.addLast(task);
		}
	}

	public void handleProcessStep() {
		while (true) {
			synchronized (lock) {
				if (taskQueue.isEmpty()) {
					break;
				}
				currentTask = taskQueue.removeFirst();
			}
			try {
				currentTask.doTask();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			synchronized (lock) {
				currentTask = null;
			}
		}
	}

	@Override
	public void stop() {
		handleProcessStep();
		super.stop();
	}

	public void cancelAllWaitingTasks() {
		synchronized (lock) {
			this.taskQueue.clear();
		}
	}

	public boolean isTaskProcessing() {
		synchronized (lock) {
			return this.isRunning()
					&& (this.currentTask != null || !this.taskQueue.isEmpty());
		}
	}

	public boolean allowStart() {
		return true;
	}

	public void handlePrepareStart() {
	}

	public void handlePrepareStop() {
	}

	public boolean isFinished() {
		return false;
	}

}
