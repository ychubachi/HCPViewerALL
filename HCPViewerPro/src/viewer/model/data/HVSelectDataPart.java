/*
 * @(#)HVSelectDataPart.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model.data;

import model.HChartElement;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVDataChartPart;

/**
 * Class HVSelectDataPartPart.
 * @author Manabu Sugiura
 * @version $Id: HVSelectDataPart.java,v 1.1 2005/04/17 18:45:06 macchan Exp $
 */
public class HVSelectDataPart extends HVDataChartPart {

	/**
	 * コンストラクタ
	 * @param model
	 */
	public HVSelectDataPart(HChartElement model) {
		super(model);
	}

	/**
	 * @see application.model.HVChartPart#paintIcon(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintIcon(CGraphics g, HRenderingContext context) {
		//しかく
		g.drawRoundRect(0, 0, context.diameter, context.diameter, 0, 0);

		//さんかく
		double power = 6d / 8d;
		int len = (int) (context.diameter * power);
		int rightX = context.diameter;
		int middleY = context.radius;
		int leftX = (int) (rightX - len * Math.sqrt(3) / 2);
		int topY = middleY - len / 2;
		int bottomY = middleY + len / 2;

		g.drawLine(leftX, topY, rightX, middleY);
		g.drawLine(leftX, bottomY, rightX, middleY);
		g.drawLine(leftX, topY, leftX, bottomY);
	}

}