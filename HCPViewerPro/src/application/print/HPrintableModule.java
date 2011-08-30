package application.print;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import viewer.CAWTGraphicsAdapter;
import viewer.HRenderingContext;
import viewer.model.others.HVModule;
import application.gui.HViewer;

public class HPrintableModule implements Printable {

	private boolean initialized = false;
	private double scale = 1d;
	private int pages = 1;

	private HVModule module;
	private HRenderingContext context;

	public HPrintableModule(HVModule module, HRenderingContext context) {
		this.module = module;
		this.context = context;
	}

	private void initialize(PageFormat pageFormat) {
		double pw = pageFormat.getImageableWidth();
		double w = module.getSize().width;

		this.scale = 1d;
		if (w >= pw) {
			this.scale = pw / w;
		}

		double ph = pageFormat.getImageableHeight();
		double h = module.getSize().height;
		h = h * scale;

		pages = 1;
		pages = (int) (h / ph) + 1;

	}

	public int print(Graphics g, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		if (!initialized) {
			initialize(pageFormat);
			initialized = true;
		}

		if (pageIndex >= pages) {
			return Printable.NO_SUCH_PAGE;
		}

		// Print
		// 毎回描くので，多少効率は悪い
		g.translate((int) pageFormat.getImageableX() + 1, (int) pageFormat
				.getImageableY() + 1);

		// ページごとの設定
		Graphics2D g2 = (Graphics2D) g;
		int h = (int) pageFormat.getImageableHeight();
		g.translate(0, (-1 * h * pageIndex) + 1);

		// 描画
		AffineTransform beforeTransform = g2.getTransform();
		HViewer.configGraphics(g2, beforeTransform, scale, false);
		CAWTGraphicsAdapter cg = new CAWTGraphicsAdapter(g, context);
		module.paint(cg, context);
		g2.setTransform(beforeTransform);

		return Printable.PAGE_EXISTS;
	}
}
