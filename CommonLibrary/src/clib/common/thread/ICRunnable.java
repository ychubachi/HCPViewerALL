/*
 * ICRunnable.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.thread;

public interface ICRunnable {

	/**
	 * スレッドが開始できるかどうかを返す
	 * 
	 * @return スレッドが開始できる:true 開始できない:false
	 */
	public boolean allowStart();

	/**
	 * スレッドが開始するときの処理を記述する
	 */
	public void handlePrepareStart();

	/**
	 * スレッドが終了するときの処理を記述する
	 */
	public void handlePrepareStop();

	/**
	 * 一周期あたりの処理を記述する
	 */
	public void handleProcessStep();

	/**
	 * Return whether the task has finished or not.
	 */
	public boolean isFinished();

}
