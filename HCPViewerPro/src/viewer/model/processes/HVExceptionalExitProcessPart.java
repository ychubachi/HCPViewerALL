/*
 * HVExceptionalExitProcessPart.java
 * Created on 2003/11/23
 * 
 * Copyright (c) 2003 CreW Project. All rights reserved.
 */
package viewer.model.processes;

import model.HChartElement;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVChartPart;
import viewer.model.others.HVTerminalElement;

/**
 * Class HVExceptionalExitProcessPart.
 * 
 * @author macchan
 * @version $Id: HVExceptionalExitProcessPart.java,v 1.1 2005/04/17 18:45:06 macchan Exp $
 */
public class HVExceptionalExitProcessPart extends HVChartPart implements HVTerminalElement {

	/**
	 * コンストラクタ
	 * @param model
	 */
	public HVExceptionalExitProcessPart(HChartElement model) {
		super(model);
	}

	/**
	 * @see application.model.HVChartPart#paintIcon(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintIcon(CGraphics g, HRenderingContext context) {
		int x = context.radius;
		int topY = 0;
		//		int middleY = context.radius;
		//		int bottomY = context.radius;
		//		g.drawLine(x, topY, x, middleY);

		double quarter = context.diameter / 4;
		int leftX1 = 0;
		int leftX2 = (int) (x - quarter);
		int arc = context.radius;

		g.drawRoundRect(leftX1, topY, context.diameter, context.radius, arc,
				arc);
		g.drawOval(leftX2, topY, context.radius, context.radius);

	}

}