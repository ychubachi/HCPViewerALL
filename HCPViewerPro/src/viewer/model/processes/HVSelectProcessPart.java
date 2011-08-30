/*
 * HVSelectProcessPart.java
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
 * Class HVSelectProcessPart.
 * 
 * @author macchan
 * @version $Id: HVSelectProcessPart.java,v 1.1 2005/04/17 18:45:06 macchan Exp $
 */
public class HVSelectProcessPart extends HVChartPart {

	/**
	 * コンストラクタ
	 * @param model
	 */
	public HVSelectProcessPart(HChartElement model) {
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