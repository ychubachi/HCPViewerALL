/*
 * HFileReloadAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import application.HCPViewer;

/**
 * Class HFileReloadAction
 * 
 * @author macchan
 * @version $Id: HFileReloadAction.java,v 1.1 2005/04/03 16:59:17 macchan Exp $
 */
public class HFileReloadAction extends HAbstractAction {

	/**
	 * Constructor for HFileReloadAction
	 */
	public HFileReloadAction(HCPViewer application) {
		super(application);
	}

	/**
	 * @see application.actions.HAbstractAction#doAction()
	 */
	protected void doAction() {
		getApplication().getDocumentManager().load();
	}

}