/*
 * @(#)HVEnvironment.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import model.HEnvironment;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.processes.HVConditionProcessPart;

/**
 * @author Manabu Sugiura
 * @version $Id: HVEnvironment.java,v 1.27 2009/09/10 03:48:31 macchan Exp $
 */
public class HVEnvironment extends HVElement {

	private HEnvironment model;
	private List<HVCompositPart> children = new ArrayList<HVCompositPart>();

	/**
	 * コンストラクタ
	 */
	public HVEnvironment(HEnvironment model) {
		this.model = model;
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		if (!isVisible()) {
			setSize(new Dimension(0, 0));
			return;
		}

		// 下の階層の大きさを決める
		for (Iterator<HVCompositPart> i = this.children.iterator(); i.hasNext();) {
			HVCompositPart compositPart = (HVCompositPart) i.next();
			compositPart.layout(context);
		}

		// 自分の大きさを決める
		int w = 0, h = 0;
		for (Iterator<HVCompositPart> i = this.children.iterator(); i.hasNext();) {
			HVCompositPart compositPart = (HVCompositPart) i.next();
			Dimension d = compositPart.getSize();
			w = Math.max(w, d.width);
			h += d.height;
		}
		if (hasBranchChild()) {
			w -= context.indent;
		}
		this.setSize(new Dimension(w, h));

		// 下の階層の位置を決める
		if (hasBranchChild()) {
			layoutNextLevel(0);
		} else {
			layoutNextLevel(context.indent);
		}
	}

	private boolean hasBranchChild() {
		for (Iterator<HVCompositPart> i = this.children.iterator(); i.hasNext();) {
			HVCompositPart compositPart = (HVCompositPart) i.next();
			if (compositPart.getPart() instanceof HVConditionProcessPart) {
				return true;
			}
		}
		return false;
	}

	private void layoutNextLevel(int indent) {
		int h = 0;
		for (Iterator<HVCompositPart> i = this.children.iterator(); i.hasNext();) {
			HVCompositPart compositPart = (HVCompositPart) i.next();
			compositPart.setPosition(new Point(indent, h));
			h += compositPart.getSize().height;
		}
	}

	public void paintComponent(CGraphics g, HRenderingContext context) {
		for (Iterator<HVCompositPart> i = this.children.iterator(); i.hasNext();) {
			HVCompositPart compositPart = (HVCompositPart) i.next();
			compositPart.paint(g, context);
		}
	}

	public void addChild(HVCompositPart compositPart) {
		this.children.add(compositPart);
		compositPart.setParent(this);// 仮
	}

	public List<HVCompositPart> getChildren() {
		return Collections.unmodifiableList(this.children);
	}

	public boolean hasNextCompositPart(HVPart part) {
		for (Iterator<HVCompositPart> i = children.iterator(); i.hasNext();) {
			HVCompositPart composit = (HVCompositPart) i.next();
			if (composit.getPart() == part) {
				return i.hasNext();
			}
		}
		return false;
	}

	public HVCompositPart getNextCompositPart(HVPart part) {
		for (Iterator<HVCompositPart> i = children.iterator(); i.hasNext();) {
			HVCompositPart composit = (HVCompositPart) i.next();
			if (composit.getPart() == part && i.hasNext()) {
				return (HVCompositPart) i.next();
			}
		}
		return null;
	}

	public HVCompositPart getParentCompositPart(HVPart part) {
		for (Iterator<HVCompositPart> i = children.iterator(); i.hasNext();) {
			HVCompositPart composit = (HVCompositPart) i.next();
			if (composit.getPart() == part) {
				return composit;
			}
		}
		return null;
	}

	public HVCompositPart getPreviousCompositPart(HVPart part) {
		for (ListIterator<HVCompositPart> i = children.listIterator(); i.hasNext();) {
			HVCompositPart composit = (HVCompositPart) i.next();
			if (composit.getPart() == part) {
				i.previous();
				if (i.hasPrevious()) {
					return (HVCompositPart) i.previous();
				}
			}
		}
		return null;
	}

	/***************************************************************************
	 * デバッグ
	 **************************************************************************/

	public void debugPrint(CGraphics g) {
		g.setColor(Color.red);
		g.drawRoundRect(0, 0, getSize().width, getSize().height, 2, 2);
		// if (getModel() != null) {
		// g.drawString(getModel().toString(), getSize().width - 100, 10);
		// }
		// g.drawString("(" + this.getPosition().x + "," + this.getPosition().y
		// + ")", 0, 0);
		g.setColor(Color.black);
	}

	/**
	 * @return Returns the model.
	 */
	public HEnvironment getModel() {
		return this.model;
	}
}