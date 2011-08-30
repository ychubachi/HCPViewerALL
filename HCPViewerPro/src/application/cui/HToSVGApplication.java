/*
 * CommandToSVG.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.cui;

import java.io.File;

import viewer.HAWTRenderingContext;
import application.HDocumentManager;
import application.HViewProperty;
import application.exporters.HExporter;
import application.exporters.HSVGExporter;

/**
 * Class CommandToSVG
 * 
 * @author macchan
 * @version $Id: CommandToSVG.java,v 1.5 2009/09/10 03:48:32 macchan Exp $
 */
public class HToSVGApplication extends HAbstractCUIApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HToSVGApplication command = new HToSVGApplication();
		command.run(args);
	}

	public void doProcess(File hcpFile, File dir) {
		HDocumentManager manager = new HDocumentManager();
		manager.open(hcpFile);

		HSVGExporter exporter = new HSVGExporter();
		HExporter.exportAll(dir, manager.getDocument(), exporter,
				new HAWTRenderingContext(new HViewProperty()));
	}	
}
