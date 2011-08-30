/*
 * HVInitialCapDataPart.java
 * Created on 2003/11/22
 * 
 * Copyright (c) 2003 CreW Project. All rights reserved.
 */
package viewer.model.data;

import java.awt.Dimension;

import model.HEnvironment;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVCapPart;

/**
 * Class HVInitialCapDataPart.
 * @author Manabu Sugiura
 * @version $Id: HVInitialCapDataPart.java,v 1.1 2005/04/17 18:45:06 macchan Exp $
 */
public class HVInitialCapDataPart extends HVCapPart {

	/**
	 * コンストラクタ
	 * @param model
	 */
	public HVInitialCapDataPart(HEnvironment model) {
		super(model);
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		if (this.isRootCap()) {
			this.setSize(new Dimension(context.diameter, context.capHeight));
		} else {
			this.setSize(new Dimension(context.newLevelConnectorWidth,
					context.newLevelConnectorHeight));
		}
	}

	public void paintComponent(CGraphics g, HRenderingContext hint) {
		if (!this.isRootCap()) {
			this.paintNewLevelCap(g, hint);
		}
	}

	private void paintNewLevelCap(CGraphics g, HRenderingContext hint) {
		int x = -hint.indent + hint.newLevelMargin;
		int y = -hint.radius;
		int width = hint.newLevelConnectorWidth - hint.newLevelMargin;
		int height = hint.newLevelConnectorHeight;

		//縦線
		g.drawLine(x, y, x, y + height * 4 / 5);

		//横線
		g.drawLine(x, y + height * 4 / 5, x + width, y + height * 4 / 5);

		//縦線
		g.drawLine(x + width, y + height * 4 / 5, x + width,
				hint.newLevelConnectorHeight);
	}

	private boolean isRootCap() {
		return getParentEnvironment().getParent() instanceof HVDataEnvironment;
	}

}