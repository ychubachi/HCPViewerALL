/*
 * DefaultConsoleInputable.java
 * Created on 2011/06/20
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.execution;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import clib.common.thread.CRunnableThread;
import clib.common.thread.CThread;

/**
 * @author macchan
 */
public class CDefaultConsoleInput implements ICConsoleInputable {

	private PrintStream ps;
	private CThread th = new CRunnableThread() {
		public void handleProcessStep() {
			try {
				if (System.in.available() > 0) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(System.in));
					String line = reader.readLine();
					if (ps != null) {
						ps.println(line);
						ps.flush();
					}
				}
			} catch (Exception ex) {
			}
		};
	};

	public CDefaultConsoleInput() {
		th.setInterval(10);
	}

	public void setOutputStream(OutputStream stream) {
		this.ps = new PrintStream(stream);
	}

	public void start() {
		th.start();
	}

	public void stop() {
		th.stop();
	}

}
