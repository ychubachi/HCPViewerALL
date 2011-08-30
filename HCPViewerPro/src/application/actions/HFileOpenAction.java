/*
 * HFileOpenAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import java.io.File;

import javax.swing.JFileChooser;

import application.HCPViewer;

/**
 * Class HFileOpenAction
 * 
 * @author macchan
 * @version $Id: HFileOpenAction.java,v 1.1 2005/04/03 16:59:17 macchan Exp $
 */
public class HFileOpenAction extends HAbstractFileChooseAction {
	/**
	 * Constructor for HFileOpenAction
	 */
	public HFileOpenAction(HCPViewer application) {
		super(application);
	}

	/**
	 * @see application.actions.HAbstractAction#doAction()
	 */
	protected void doAction() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(createFileFilter(HCPViewer.FILE_EXTENSION));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		int result = chooser.showOpenDialog(getApplication().getFrame());
		if (result != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = chooser.getSelectedFile();
		if (file.exists() && !file.isDirectory()) {
			getApplication().getDocumentManager().open(file);
		}
	}

}