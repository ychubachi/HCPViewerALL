/*
 * HExportSVGAllAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import java.io.File;

import javax.swing.JFileChooser;

import viewer.HAWTRenderingContext;
import application.HCPViewer;
import application.exporters.HExporter;
import application.exporters.HSVGExporter;

/**
 * Class HExportSVGAllAction
 * 
 * @author macchan
 * @version $Id: HExportSVGAllAction.java,v 1.5 2009/09/10 03:48:32 macchan Exp
 *          $
 */
public class HExportSVGAllAction extends HAbstractExportAction {

	/**
	 * Constructor for HExportSVGAllAction
	 */
	public HExportSVGAllAction(HCPViewer application) {
		super(application);
	}

	/**
	 * @see application.actions.HAbstractAction#doAction()
	 */
	protected void doAction() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showSaveDialog(getApplication().getFrame());
		if (result != JFileChooser.APPROVE_OPTION) {
			return;
		}
		File dir = chooser.getSelectedFile();
		if(!dir.exists()){
			dir.mkdirs();
		}
		HSVGExporter exporter = new HSVGExporter();
		HExporter.exportAll(dir, getApplication().getDocumentManager()
				.getDocument(), exporter, new HAWTRenderingContext(
				getApplication().getFrame().getGlobalProperty()));
	}

}