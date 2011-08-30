/*
 * HAbstractExportAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import model.HModule;
import application.HCPViewer;
import application.exporters.HExporter;

/**
 * Class HAbstractExportAction
 * 
 * @author macchan
 * @version $Id: HAbstractExportAction.java,v 1.1 2005/04/03 16:59:17 macchan
 *          Exp $
 */
public abstract class HAbstractExportAction extends HAbstractFileChooseAction {

	/**
	 * Constructor for HAbstractExportAction
	 */
	public HAbstractExportAction(HCPViewer application) {
		super(application);
	}

	protected String createDefaultFilename(String extension, HModule module) {
		return HExporter.createExportFilename(getApplication()
				.getDocumentManager().getFile().getName(), extension, module);
	}
}