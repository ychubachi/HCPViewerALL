/*
 * HVInitialCapPart.java
 * Created on 2003/11/22
 * 
 * Copyright (c) 2003 CreW Project. All rights reserved.
 */
package viewer.model.others;

import java.awt.Dimension;

import model.HEnvironment;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVCapPart;
import viewer.model.HVCompositPart;
import viewer.model.processes.HVConditionProcessPart;

/**
 * @author Manabu Sugiura
 * @version $Id: HVInitialCapPart.java,v 1.8 2005/04/17 18:45:06 macchan Exp $
 */
public class HVInitialCapPart extends HVCapPart {

	/**
	 * ÉRÉìÉXÉgÉâÉNÉ^
	 * @param model
	 */
	public HVInitialCapPart(HEnvironment model) {
		super(model);
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		if (this.isRootCap()) {
			this.setSize(new Dimension(context.diameter, context.capHeight));
		} else if (this.isPreviousBranch() || this.isBranchTop()) {
			this.setSize(new Dimension(0, context.radius));
		} else {
			this.setSize(new Dimension(context.newLevelConnectorWidth,
					context.newLevelConnectorHeight));
		}
	}

	private boolean isPreviousBranch() {
		return getParentEnvironment().getNextCompositPart(this).getPart() instanceof HVConditionProcessPart;
	}

	public void paintComponent(CGraphics g, HRenderingContext hint) {
		if (this.isRootCap()) {
			this.paintRootCap(g, hint);
		} else if (this.isPreviousBranch()) {
			this.paintBranchRootCap(g, hint);
		} else if (this.isBranchTop()) {
			this.paintBranchRootCap(g, hint);
		} else {
			this.paintNewLevelCap(g, hint);
		}
	}
	private void paintNewLevelCap(CGraphics g, HRenderingContext hint) {
		int x = -hint.indent + hint.newLevelMargin;
		int y = -hint.radius;
		int width = hint.newLevelConnectorWidth - hint.newLevelMargin;
		int height = hint.newLevelConnectorHeight;

		//ècê¸
		g.drawLine(x, y, x, y + height * 4 / 5);

		//â°ê¸
		g.drawLine(x, y + height * 4 / 5, x + width, y + height * 4 / 5);

		//ècê¸
		g.drawLine(x + width, y + height * 4 / 5, x + width,
				hint.newLevelConnectorHeight);
	}

	private void paintBranchRootCap(CGraphics g, HRenderingContext hint) {
		int x = hint.radius;
		int y = -hint.radius;
		int height = hint.diameter;
		g.drawLine(x, y, x, y + height);
	}

	private void paintRootCap(CGraphics g, HRenderingContext hint) {
		//â°ê¸Çà¯Ç≠
		int side = (hint.diameter - hint.capWidth) / 2;
		g.drawLine(side, 0, side + hint.capWidth, 0);

		//ècê¸Çà¯Ç≠
		int vertical = hint.diameter / 2;
		g.drawLine(vertical, 0, vertical, hint.capHeight);
	}

	private boolean isRootCap() {
		return getModel().isTopLevel();
	}

	private boolean isBranchTop() {
		HVCompositPart grandParent = (HVCompositPart) getParentEnvironment()
				.getParent();
		HVConnectorCapPart con = (HVConnectorCapPart) grandParent.getPart();
		if (con.getParentEnvironment().getPreviousCompositPart(con).getPart() instanceof HVConditionProcessPart) {
			return true;
		}
		return false;
	}

}