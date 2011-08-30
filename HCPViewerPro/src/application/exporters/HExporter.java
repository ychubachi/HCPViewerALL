package application.exporters;

import java.io.File;
import java.util.Iterator;

import model.HDocument;
import model.HModule;
import viewer.HRenderingContext;

public abstract class HExporter {
	public static String createExportFilename(String filename,
			String extension, HModule module) {
		String noExtensionFileName = filename.substring(0, filename
				.indexOf("."));
		String moduleId = module.getId();

		StringBuffer defaultFileName = new StringBuffer();
		defaultFileName.append(noExtensionFileName);
		if (!module.isDefaultID()) {
			defaultFileName.append("_");
			defaultFileName.append(moduleId);
		}
		defaultFileName.append(".");
		defaultFileName.append(extension);

		// <>Ç∆Ç¢Ç§ÉÇÉWÉÖÅ[ÉãñºÇÕVistaÇ≈ÇÕégÇ¶Ç»Ç¢ÇÃÇ≈èCê≥
		String newfilename = defaultFileName.toString();
		newfilename = newfilename.replaceAll("<", "(");
		newfilename = newfilename.replaceAll(">", ")");

		return newfilename;
	}

	public static void exportAll(File dir, HDocument doc, HExporter exporter,
			HRenderingContext context) {
		for (Iterator<HModule> i = doc.getModules().iterator(); i.hasNext();) {
			HModule module = (HModule) i.next();
			File file = new File(dir, HExporter.createExportFilename(doc
					.getFilename(), exporter.getExtension(), module));
			exporter.export(file, module, context);
		}
	}

	public abstract void export(File destination, HModule module,
			HRenderingContext context);

	public abstract String getExtension();
}
