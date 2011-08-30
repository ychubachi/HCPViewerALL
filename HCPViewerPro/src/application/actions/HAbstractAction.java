/*
 * HAbstractAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import java.awt.Cursor;

import application.HCPViewer;
import application.gui.parts.HErrorDialog;

/**
 * Class HAbstractAction
 * 
 * @author macchan
 * @version $Id: HAbstractAction.java,v 1.2 2006/04/23 05:33:13 macchan Exp $
 */
public abstract class HAbstractAction extends Thread {

	private HCPViewer application;

	public HAbstractAction(HCPViewer application) {
		this.application = application;
	}

	public HCPViewer getApplication() {
		return this.application;
	}

	public final void execute() {
		start();
	}

	public final void run() {
		try {
			initialize();
			doAction();
		} catch (Throwable th) {
			HErrorDialog.show(application.getFrame(), th.getMessage(), th);
		} finally {
			terminate();
		}
	}

	protected void initialize() {
		application.getFrame().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	protected void terminate() {
		application.getFrame().setCursor(
				Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	protected abstract void doAction();

}