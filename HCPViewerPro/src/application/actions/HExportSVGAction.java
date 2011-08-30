/*
 * HExportSVGAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import java.io.File;

import javax.swing.JFileChooser;

import model.HModule;

import org.apache.batik.swing.svg.SVGFileFilter;

import viewer.HAWTRenderingContext;
import application.HCPViewer;
import application.exporters.HSVGExporter;

/**
 * Class HExportSVGAction
 * 
 * @author macchan
 * @version $Id: HExportSVGAction.java,v 1.4 2009/07/14 09:47:25 macchan Exp $
 */
public class HExportSVGAction extends HAbstractExportAction {

	/**
	 * Constructor for HExportSVGAction
	 */
	public HExportSVGAction(HCPViewer application) {
		super(application);
	}

	/**
	 * @see application.actions.HAbstractAction#doAction()
	 */
	protected void doAction() {
		HModule module = getApplication().getDocumentManager()
				.getCurrentModule();

		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new SVGFileFilter());
		File defaultFile = new File(createDefaultFilename("svg", module));
		chooser.setSelectedFile(defaultFile);

		int result = chooser.showSaveDialog(getApplication().getFrame());
		if (result != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = chooser.getSelectedFile();
		HSVGExporter exporter = new HSVGExporter();
		exporter.export(file, module, new HAWTRenderingContext(getApplication()
				.getFrame().getGlobalProperty()));
	}

}