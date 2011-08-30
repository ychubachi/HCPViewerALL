/*
 * @(#)HVConditionProcessPart.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model.processes;

import java.awt.Dimension;

import model.HChartElement;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVChartPart;
import viewer.model.HVCompositPart;
import viewer.model.HVEnvironment;
import viewer.model.others.HVTerminalElement;

/**
 * @author Manabu Sugiura
 * @version $Id: HVConditionProcessPart.java,v 1.1 2005/04/17 18:45:06 macchan Exp $
 */
public class HVConditionProcessPart extends HVChartPart implements HVTerminalElement {

	/**
	 * �R���X�g���N�^
	 * @param model
	 */
	public HVConditionProcessPart(HChartElement model) {
		super(model);
	}

	/**
	 * @see application.model.HVChartPart#paintIcon(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintIcon(CGraphics g, HRenderingContext context) {
		//�c��
		g.drawLine(context.radius, 0, context.radius, context.radius);
		if (needExtensionLine()) {
			g.drawLine(context.radius, context.radius, context.radius,
					context.diameter);
		}

		//����
		g.drawLine(context.radius, context.radius, context.indent
				+ context.radius * 3, context.radius);

		//���
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
				&& !getModel().getText().startsWith("�i")) {
			return "(" + getModel().getText() + ")";
		}
		return getModel().getText();
	}

}