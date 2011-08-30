/*
 * HVErrorCheckProcessPart.java
 * Created on 2003/11/23
 * 
 * Copyright (c) 2003 CreW Project. All rights reserved.
 */
package viewer.model.processes;

import model.HChartElement;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVChartPart;

/**
 * Class HVErrorCheckProcessPart.
 * 
 * @author macchan
 * @version $Id: HVErrorCheckProcessPart.java,v 1.1 2005/04/17 18:45:06 macchan Exp $
 */
public class HVErrorCheckProcessPart extends HVChartPart {

	/**
	 * コンストラクタ
	 */
	public HVErrorCheckProcessPart(HChartElement model) {
		super(model);
	}

	/**
	 * @see application.model.HVChartPart#paintIcon(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintIcon(CGraphics g, HRenderingContext context) {
		//まる
		g.drawOval(0, 0, context.diameter, context.diameter);

		//さんかく
		double power = 6d / 8d;
		int len = (int) (context.radius * power);
		int rightX = context.radius + len;
		int leftX = context.radius - len;
		int topY = context.radius + len;
		int bottomY = context.radius - len;

		g.drawLine(leftX, topY, rightX, bottomY);
		g.drawLine(rightX, topY, leftX, bottomY);
	}

}