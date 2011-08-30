/*
 * HAbstractFileChooseAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import application.HCPViewer;

/**
 * Class HAbstractFileChooseAction
 * 
 * @author macchan
 * @version $Id: HAbstractFileChooseAction.java,v 1.1 2005/04/03 16:59:17 macchan Exp $
 */
public abstract class HAbstractFileChooseAction extends HAbstractAction {

	/**
	 * Constructor for HAbstractFileChooseAction
	 */
	public HAbstractFileChooseAction(HCPViewer application) {
		super(application);
	}

	public FileFilter createFileFilter(final String extension) {
		return new FileFilter() {
			public boolean accept(File file) {
				if (file.isDirectory()
						|| file.getName().toLowerCase().endsWith(extension)) {
					return true;
				}
				return false;
			}

			public String getDescription() {
				return extension;
			}
		};
	}

}