/*
 * @(#)HVSpaceDataPart.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model.data;

import java.awt.Dimension;

import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVPart;

/**
 * Class HVSpaceDataPart.
 * @author Manabu Sugiura
 * @version $Id: HVSpaceDataPart.java,v 1.3 2005/04/17 18:45:06 macchan Exp $
 */
public class HVSpaceDataPart extends HVPart {

	/**
	 * コンストラクタ
	 * @param model
	 */
	public HVSpaceDataPart() {
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		setSize(new Dimension(0, 10));
	}
	/**
	 * @see application.model.HVElement#paintComponent(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintComponent(CGraphics g, HRenderingContext context) {
	}
}