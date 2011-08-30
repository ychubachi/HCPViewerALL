/*
 * @(#)HVBasisDataPart.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model.data;

import model.HChartElement;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVDataChartPart;

/**
 * Class HVBasisDataPart.
 * @author Manabu Sugiura
 * @version $Id: HVBasisDataPart.java,v 1.6 2005/04/17 18:45:06 macchan Exp $
 */
public class HVBasisDataPart extends HVDataChartPart {

	/**
	 * コンストラクタ
	 * @param model
	 */
	public HVBasisDataPart(HChartElement model) {
		super(model);
	}

	/**
	 * @see application.model.HVChartPart#paintIcon(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintIcon(CGraphics g, HRenderingContext context) {
		g.drawRoundRect(0, 0, context.diameter, context.diameter, 0, 0);
	}

}