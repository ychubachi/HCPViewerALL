/*
 * @(#)HVConditionDataPart.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model.data;

import java.awt.Dimension;

import model.HChartElement;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVCompositPart;
import viewer.model.HVDataChartPart;
import viewer.model.HVEnvironment;

/**
 * Class HVConditionDataPart.
 * @author Manabu Sugiura
 * @version $Id: HVConditionDataPart.java,v 1.1 2005/04/17 18:45:06 macchan Exp $
 */
public class HVConditionDataPart extends HVDataChartPart {

	/**
	 * コンストラクタ
	 * @param model
	 */
	public HVConditionDataPart(HChartElement model) {
		super(model);
	}

	/**
	 * @see application.model.HVChartPart#paintIcon(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintIcon(CGraphics g, HRenderingContext context) {
		//縦線
		g.drawLine(context.radius, 0, context.radius, context.radius);
		if (needExtensionLine()) {
			g.drawLine(context.radius, context.radius, context.radius,
					context.diameter);
		}

		//横線
		g.drawLine(context.radius, context.radius, context.indent
				+ context.radius * 3, context.radius);

		//矢印
		g.drawLine(context.radius * 3 + context.indent, context.radius,
				context.radius * 2 + context.indent, context.radius + 3);
		g.drawLine(context.radius * 3 + context.indent, context.radius,
				context.radius * 2 + context.indent, context.radius - 3);

	}

	private boolean needExtensionLine() {
		HVEnvironment parentEnv = getParentEnvironment();
		HVCompositPart next = parentEnv.getNextCompositPart(this);

		if (next != null) {
			return parentEnv.hasNextCompositPart(next.getPart());
		}
		return false;
	}

	public void layout(HRenderingContext context) {
		int w = context.radius * 3 + context.newLevelMargin + context.indent
				+ context.calculateStringWidth(getText());
		setSize(new Dimension(w, context.diameter));
	}

	protected void paintText(CGraphics g, HRenderingContext context) {
		g.drawString(getText(), context.diameter * 3, context.diameter * 5 / 6);
	}

	private String getText() {
		if (!getModel().getText().startsWith("(")
				&& !getModel().getText().startsWith("（")) {
			return "(" + getModel().getText() + ")";
		}
		return getModel().getText();
	}

}