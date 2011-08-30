/*
 * HVReturnProcessPart.java
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
 * Class HVReturnProcessPart.
 * 
 * @author macchan
 * @version $Id: HVReturnProcessPart.java,v 1.1 2005/04/17 18:45:06 macchan Exp $
 */
public class HVReturnProcessPart extends HVChartPart implements HVTerminalElement {

	/**
	 * コンストラクタ
	 * @param model
	 */
	public HVReturnProcessPart(HChartElement model) {
		super(model);
	}

	/**
	 * @see application.model.HVChartPart#paintIcon(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintIcon(CGraphics g, HRenderingContext context) {
		int x = context.radius;
		int topY = 0;
		//int middleY = context.radius;
		int bottomY = (int) (context.radius * Math.sqrt(3));
		double quarter = context.diameter / 4;
		int leftX = (int) (x - quarter);
		int rightX = (int) (x + quarter);
		int leftX1 = 0;
		int rightX1 = context.diameter;

		//g.drawLine(x, topY, x, middleY);
		//した線
		g.drawLine(leftX, bottomY, rightX, bottomY);

		//三角		
		g.drawLine(leftX1, topY, rightX1, topY);
		g.drawLine(leftX1, topY, x, bottomY);
		g.drawLine(rightX1, topY, x, bottomY);

		//数字
		//		HPReturn model = (HPReturn) getProcessElement();
		//		int level = model.getReturnLevel();
		//		if (level > 1) {
		//			int strX = x - context.getDiameter() * 3 / 16;
		//			int strY = middleY + context.getRadius() * 1 / 4;
		//			g.drawString(Integer.toString(level), strX, strY);
		//		}
	}

}