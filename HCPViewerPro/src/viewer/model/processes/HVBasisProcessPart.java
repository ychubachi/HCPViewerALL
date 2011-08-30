/*
 * @(#)HVBasisProcessPart.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model.processes;

import model.HChartElement;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVChartPart;

/**
 * Class HVBasisProcessPart.
 * @author Manabu Sugiura
 * @version $Id: HVBasisProcessPart.java,v 1.3 2005/03/22 10:33:33 macchan Exp $
 */
public class HVBasisProcessPart extends HVChartPart {

	/**
	 * コンストラクタ
	 * @param model
	 */
	public HVBasisProcessPart(HChartElement model) {
		super(model);
	}

	/**
	 * @see application.model.HVChartPart#paintIcon(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintIcon(CGraphics g, HRenderingContext context) {
		g.drawOval(0, 0, context.diameter, context.diameter);
	}

}