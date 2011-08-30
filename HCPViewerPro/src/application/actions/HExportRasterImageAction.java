/*
 * HExportRasterImageAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import java.io.File;

import javax.swing.JFileChooser;

import model.HModule;
import viewer.HAWTRenderingContext;
import application.HCPViewer;
import application.exporters.HRasterImageExporter;

/**
 * Class HExportRasterImageAction
 * 
 * @author macchan
 * @version $Id: HExportRasterImageAction.java,v 1.1 2005/04/03 16:59:17 macchan
 *          Exp $
 */
public class HExportRasterImageAction extends HAbstractExportAction {

	private String imgType;

	/**
	 * Constructor for HExportRasterImageAction
	 */
	public HExportRasterImageAction(HCPViewer application, String imgType) {
		super(application);
		this.imgType = imgType;
	}

	/**
	 * @see application.actions.HAbstractAction#doAction()
	 */
	protected void doAction() {
		HModule module = getApplication().getDocumentManager()
				.getCurrentModule();

		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(createFileFilter(imgType));
		File defaultFile = new File(createDefaultFilename(imgType, module));
		chooser.setSelectedFile(defaultFile);

		int result = chooser.showSaveDialog(getApplication().getFrame());
		if (result != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = chooser.getSelectedFile();
		HRasterImageExporter exporter = new HRasterImageExporter(imgType);
		exporter.export(file, module, new HAWTRenderingContext(getApplication()
				.getFrame().getGlobalProperty()));
	}

}