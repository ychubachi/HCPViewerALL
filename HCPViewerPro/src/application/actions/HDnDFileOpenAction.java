/*
 * HDnDFileOpenAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import java.io.File;

import application.HCPViewer;

/**
 * Class HDnDFileOpenAction
 * 
 * @author macchan
 * @version $Id: HDnDFileOpenAction.java,v 1.1 2005/04/03 16:59:17 macchan Exp $
 */
public class HDnDFileOpenAction extends HAbstractAction {

	private File file;

	/**
	 * Constructor for HDnDFileOpenAction
	 */
	public HDnDFileOpenAction(HCPViewer application, File file) {
		super(application);
		this.file = file;
	}

	/**
	 * @see application.actions.HAbstractAction#doAction()
	 */
	protected void doAction() {
		getApplication().getDocumentManager().open(file);
	}

}