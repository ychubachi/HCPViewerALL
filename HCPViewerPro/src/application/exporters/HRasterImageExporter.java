/*
 * @(#)HRasterImageExporter.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package application.exporters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.HModule;
import viewer.CAWTGraphicsAdapter;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.others.HVModule;

/**
 * Class HRasterImageExporter.
 * 
 * @author Manabu Sugiura
 * @version $Id: HRasterImageExporter.java,v 1.5 2008/10/06 02:38:26 macchan Exp
 *          $
 */
public class HRasterImageExporter extends HExporter {

	public static final String BMP = "bmp";
	public static final String JPG = "jpg";
	public static final String PNG = "png";

	private String imgType;

	public HRasterImageExporter(String imgType) {
		this.imgType = imgType;
	}

	public String getExtension() {
		return this.imgType;
	}

	public void export(File destination, HModule module,
			HRenderingContext context) {
		CGraphics g = null;
		try {
			// レイアウトする
			HVModule vmodule = new HVModule(module, context.getRenderingLevel());
			vmodule.layout(context);

			// チャートを描く
			BufferedImage image = createImage(vmodule, context);
			g = createGraphics(image, context);
			g.translate(context.xMargin, context.yMargin);
			vmodule.paint(g, context);

			// ファイルに出力する
			ImageIO.write(image, imgType, destination);

		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			// 後始末
			g.dispose();
		}
	}

	private BufferedImage createImage(HVModule vmodule,
			HRenderingContext context) {
		Dimension size = vmodule.getSize();
		return new BufferedImage(size.width + context.xMargin * 2, size.height
				+ context.yMargin * 2, BufferedImage.TYPE_INT_RGB);
	}

	private CGraphics createGraphics(BufferedImage img,
			HRenderingContext context) {
		Graphics2D g2 = (Graphics2D) img.getGraphics();

		// 設定する
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, img.getWidth(), img.getHeight());
		g2.setColor(Color.BLACK);

		return new CAWTGraphicsAdapter(g2, context);
	}

}