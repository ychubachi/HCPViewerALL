/*
 * HExportRasterImageAllAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import java.io.File;

import javax.swing.JFileChooser;

import viewer.HAWTRenderingContext;
import application.HCPViewer;
import application.exporters.HExporter;
import application.exporters.HRasterImageExporter;

/**
 * Class HExportRasterImageAllAction
 * 
 * @author macchan
 * @version $Id: HExportRasterImageAllAction.java,v 1.1 2005/04/03 16:59:17
 *          macchan Exp $
 */
public class HExportRasterImageAllAction extends HAbstractExportAction {

	private String imgType;

	/**
	 * Constructor for HExportRasterImageAllAction
	 */
	public HExportRasterImageAllAction(HCPViewer application, String imgType) {
		super(application);
		this.imgType = imgType;
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
		if (!dir.exists()) {
			dir.mkdirs();
		}
		HRasterImageExporter exporter = new HRasterImageExporter(imgType);
		HExporter.exportAll(dir, getApplication().getDocumentManager()
				.getDocument(), exporter, new HAWTRenderingContext(
				getApplication().getFrame().getGlobalProperty()));
	}

}