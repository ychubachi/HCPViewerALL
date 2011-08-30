/*
 * HAbstractPrintAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.print.PrinterJob;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;

import model.HModule;
import viewer.CAWTGraphicsAdapter;
import viewer.CGraphics;
import viewer.HAWTRenderingContext;
import viewer.HRenderingContext;
import viewer.model.others.HVModule;
import application.HCPViewer;
import application.HViewProperty;
import application.print.HPrintableModule;

/**
 * Class HAbstractPrintAction
 * 
 * @author macchan
 * @version $Id: HAbstractPrintAction.java,v 1.6 2008/10/06 02:38:25 macchan Exp
 *          $
 */
public abstract class HAbstractPrintAction extends HAbstractAction {

	private static PrintRequestAttributeSet aset;
	private static PrintService service;
	private static PrintService[] services;

	static {
		aset = new HashPrintRequestAttributeSet();
		aset.add(new JobName("HCPViewerProfesional", null));
		aset.add(createMediaPrintableArea(20, 20, 20, 20, MediaSize.ISO.A4));

		service = PrintServiceLookup.lookupDefaultPrintService();
		services = PrintServiceLookup.lookupPrintServices(null,
				new HashPrintRequestAttributeSet());
		List<PrintService> list = Arrays.asList(services);
		Collections.reverse(list);
		services = (PrintService[]) list.toArray();
	}

	private static MediaPrintableArea createMediaPrintableArea(int left,
			int right, int top, int bottom, MediaSize size) {
		float x = left;
		float y = top;
		float w = size.getX(MediaSize.MM);
		w = w - left - right;
		float h = size.getY(MediaSize.MM);
		h = h - top - bottom;
		return new MediaPrintableArea(x, y, w, h, MediaSize.MM);
	}

	/**
	 * Constructor for HAbstractPrintAction
	 */
	public HAbstractPrintAction(HCPViewer application) {
		super(application);
	}

	/**
	 * @deprecated API2(PrintService)を使え
	 */
	public PrintJob createPrintJob() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		return toolkit.getPrintJob(getApplication().getFrame(), null, null);
	}

	public PrintService getPrintService() {
		// -10000にすると，必ず真ん中にくる（後に仕様変更の可能性はある）
		service = ServiceUI.printDialog(null, -10000, -10000, services,
				service, null, aset);
		return service;
	}

	/**
	 * API2を使った印刷のメソッド
	 */
	public void printModule(HModule model, PrintService service) {
		try {
			HRenderingContext context = new HAWTRenderingContext(
					getApplication().getFrame().getGlobalProperty());
			HVModule module = createHVModule(model, context);
			module.layout(context);
			HPrintableModule printable = new HPrintableModule(module, context);

			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintService(service);
			job.setPrintable(printable);
			job.print(aset);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @deprecated
	 * @__ 2枚出しは，とりあえずの実装です．（松）
	 */
	public void printModule(HModule model, PrintJob job) {
		// 準備
		HRenderingContext context = new HAWTRenderingContext(getApplication()
				.getFrame().getGlobalProperty());
		((HAWTRenderingContext) context).setGraphics((Graphics2D) job
				.getGraphics());
		HVModule module = createHVModule(model, context);
		module.layout(context);

		Dimension pageSize = job.getPageDimension();
		Dimension contentsSize = module.getSize();

		// ページ数を決める
		int writablePageHeight = pageSize.height - context.yMargin;
		int pageCount = 1 + (contentsSize.height / writablePageHeight);

		// 書く
		int REDUNDANT_HEIGHT = 25;// ダブらせる領域の高さ
		for (int i = 0; i < pageCount; i++) {
			CGraphics cg = new CAWTGraphicsAdapter(job.getGraphics(), context);
			int startY = context.yMargin - i * writablePageHeight;
			if (i > 0) {
				startY += REDUNDANT_HEIGHT;
			}

			cg.translate(context.xMargin, startY);
			module.paint(cg, context);

			if (i > 0) {
				cg.setColor(Color.WHITE);
				cg.fillRect(-context.xMargin, -startY, pageSize.width,
						context.yMargin);
			}
		}
	}

	private HVModule createHVModule(HModule model, HRenderingContext context) {
		HViewProperty property = getApplication().getFrame()
				.getGlobalProperty();
		HVModule module = new HVModule(model, property.getRenderringLevel());

		return module;
	}
}