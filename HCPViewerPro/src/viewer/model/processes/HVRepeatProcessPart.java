/*
 * HVRepeatProcessPart.java
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
 * Class HVRepeatProcessPart.
 * 
 * @author macchan
 * @version $Id: HVRepeatProcessPart.java,v 1.1 2005/04/17 18:45:06 macchan Exp $
 */
public class HVRepeatProcessPart extends HVChartPart {

	/**
	 * �R���X�g���N�^
	 * @param model
	 */
	public HVRepeatProcessPart(HChartElement model) {
		super(model);
	}

	/**
	 * @see application.model.HVChartPart#paintIcon(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintIcon(CGraphics g, HRenderingContext context) {
		//�܂�
		g.drawOval(0, 0, context.diameter, context.diameter);

		//�܂���
		double power = 5d / 8d;
		int smallLoc = (int) (context.radius * (1 - power));
		int smallDiameter = (int) (context.diameter * power);
		g.drawArc(smallLoc, smallLoc, smallDiameter, smallDiameter, 270, 270);
		int y = context.diameter - smallLoc;
		int x = context.radius;
		int len = (int) (context.radius * 3d / 8d);
		g.drawLine(x, y, x, y - len);
		g.drawLine(x, y, x + len, y);
	}

}