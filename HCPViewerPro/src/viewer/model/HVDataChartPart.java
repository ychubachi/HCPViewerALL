/*
 * HVDataChartPart.java
 * Copyright(c) 2004 CreW Project. All rights reserved.
 */
package viewer.model;

import java.awt.Dimension;

import model.HChartElement;
import viewer.CGraphics;
import viewer.HRenderingContext;

/**
 * Class HVDataChartPart
 * 
 * @author macchan
 * @version $Id: HVDataChartPart.java,v 1.5 2008/10/04 01:59:10 macchan Exp $
 */
public abstract class HVDataChartPart extends HVChartPart {

	private boolean moduleInput = false;
	private boolean moduleOutput = false;

	// IOÇèúÇ¢ÇΩDimension
	private Dimension dataDimension = new Dimension();

	/**
	 * Constructor for HVDataChartPart
	 */
	public HVDataChartPart(HChartElement model) {
		super(model);
		// reverseIO();
	}

	/**
	 * @see application.model.HVElement#paintComponent(application.CGraphics,
	 *      application.HRenderingContext)
	 */
	protected void paintComponent(CGraphics g, HRenderingContext context) {
		super.paintComponent(g, context);
		paintModuleIO(g, context);
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		if (!isVisible()) {
			setSize(new Dimension(0, 0));
			return;
		}

		// í èÌÇÃÉfÅ[É^óÃàÊÇÃï™Çê›íË
		super.layout(context);
		this.dataDimension = getSize();

		// moduleIOÇÃï™Çë´Ç∑
		int ioW = this.dataDimension.height + context.radius;
		Dimension size = new Dimension(this.dataDimension);
		int ioCount = 0;
		if (isModuleInput()) {
			ioCount++;
		}
		if (isModuleOutput()) {
			ioCount++;
		}
		size.width += ioW * ioCount;
		setSize(size);
	}

	private void paintModuleIO(CGraphics g, HRenderingContext context) {
		g.startTranslate();
		g.translate(this.dataDimension.width + context.radius, 0);
		if (isModuleInput()) {
			drawLeftArrow(g, getSize().height);
			g.translate(getSize().height + context.radius, 0);
		}
		if (isModuleOutput()) {
			drawRightArrow(g, getSize().height);
		}
		g.endTranslate();
	}

	private void drawLeftArrow(CGraphics g, int size) {
		int half = size / 2;
		int quarter = size / 4;
		int[] xPoints = new int[] { 0, half, half, size, size, half, half, 0 };
		int[] yPoints = new int[] { half, size, size - quarter, size - quarter,
				quarter, quarter, 0, half };
		g.drawPolyline(xPoints, yPoints, xPoints.length);
	}

	private void drawRightArrow(CGraphics g, int size) {
		int half = size / 2;
		int quarter = size / 4;
		int[] xPoints = new int[] { size, half, half, 0, 0, half, half, size };
		int[] yPoints = new int[] { half, 0, quarter, quarter, size - quarter,
				size - quarter, size, half };
		g.drawPolyline(xPoints, yPoints, xPoints.length);
	}

	/**
	 * @return Returns the moduleInput.
	 */
	public boolean isModuleInput() {
		return this.moduleInput;
	}

	/**
	 * @param moduleInput
	 *            The moduleInput to set.
	 */
	public void setModuleInput(boolean moduleInput) {
		this.moduleInput = moduleInput;
	}

	/**
	 * @return Returns the moduleOutput.
	 */
	public boolean isModuleOutput() {
		return this.moduleOutput;
	}

	/**
	 * @param moduleOutput
	 *            The moduleOutput to set.
	 */
	public void setModuleOutput(boolean moduleOutput) {
		this.moduleOutput = moduleOutput;
	}

}