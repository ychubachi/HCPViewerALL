/*
 * HSVGExporter.java
 * Created on 2003/12/12
 * 
 * Copyright (c) 2003 CreW Project. All rights reserved.
 */
package application.exporters;

import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import model.HModule;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;

import viewer.CAWTGraphicsAdapter;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.others.HVModule;

/**
 * Class HSVGExporter.
 * 
 * @author macchan
 * @version $Id: HSVGExporter.java,v 1.6 2008/10/06 02:38:26 macchan Exp $
 */
public class HSVGExporter extends HExporter {

	private boolean svgInitialized = false;

	/**
	 * Constructor for HSVGExporter.
	 */
	public HSVGExporter() {
		super();
	}

	public String getExtension() {
		return "svg";
	}

	public void export(File file, HModule module, HRenderingContext context) {
		try {
			if (!svgInitialized) {
				initializeSVGGraphics();
				svgInitialized = true;
			}

			// Get a SVGGraphics2D
			DOMImplementation impl = SVGDOMImplementation
					.getDOMImplementation();
			String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
			SVGDocument doc = (SVGDocument) impl.createDocument(svgNS, "svg",
					null);
			SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(doc);
			ctx.setEmbeddedFontsOn(true);
			SVGGraphics2D svg = new SVGGraphics2D(ctx, true);

			CGraphics cg = new CAWTGraphicsAdapter(svg, context);

			// つくる
			HVModule vmodule = new HVModule(module, context.getRenderingLevel());

			// レイアウト
			cg.translate(context.xMargin, context.yMargin);
			vmodule.layout(context);
			svg.setSVGCanvasSize(new Dimension(vmodule.getSize().width
					+ context.xMargin * 2, vmodule.getSize().height
					+ context.yMargin * 2));

			// 描く
			vmodule.paint(cg, context);

			// Finally, stream out SVG to the standard output using UTF-8
			// character to byte encoding
			OutputStream os = new FileOutputStream(file);
			boolean useCSS = true; // we want to use CSS style attribute
			Writer out = new OutputStreamWriter(os, "UTF-8");
			svg.stream(out, useCSS);
			os.close();

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void initializeSVGGraphics() {
		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		SVGDocument doc = (SVGDocument) impl.createDocument(svgNS, "svg", null);
		SVGGeneratorContext.createDefault(doc);
	}
}