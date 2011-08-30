/*
 * HVCallModuleProcessPart.java
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
 * Class HVCallModuleProcessPart.
 * 
 * @author macchan
 * @version $Id: HVCallModuleProcessPart.java,v 1.1 2005/04/17 18:45:06 macchan Exp $
 */
public class HVCallModuleProcessPart extends HVChartPart {

	/**
	 * コンストラクタ
	 */
	public HVCallModuleProcessPart(HChartElement model) {
		super(model);
	}

	/**
	 * @see application.model.HVChartPart#paintIcon(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintIcon(CGraphics g, HRenderingContext context) {
		//まる
		g.drawOval(0, 0, context.diameter, context.diameter);

		//ちさいまる
		double power = 5d / 8d;
		int smallLoc = (int) (context.radius * (1 - power));
		int smallDiameter = (int) (context.diameter * power);
		g.drawOval(smallLoc, smallLoc, smallDiameter, smallDiameter);
	}

}