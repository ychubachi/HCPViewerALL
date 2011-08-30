/*
 * HView.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.gui;

import application.gui.parts.HToolBar;

/**
 * Class HView
 * 
 * @author macchan
 * @version $Id: HView.java,v 1.1 2005/04/03 14:02:35 macchan Exp $
 */
public class HView {

	private HViewer viewer;
	private HToolBar toolBar;

	public HView(HViewer viewer, HToolBar toolBar) {
		this.viewer = viewer;
		this.toolBar = toolBar;
	}

	public HToolBar getToolBar() {
		return this.toolBar;
	}
	public HViewer getViewer() {
		return this.viewer;
	}
}