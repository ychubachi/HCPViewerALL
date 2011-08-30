/*
 * @(#)HVCompositPart.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model;

import java.awt.Dimension;
import java.awt.Point;

import viewer.CGraphics;
import viewer.HRenderingContext;

/**
 * @author Manabu Sugiura
 * @version $Id: HVCompositPart.java,v 1.8 2005/03/22 10:33:33 macchan Exp $
 */
public class HVCompositPart extends HVElement {

	private HVPart part;
	private HVEnvironment environment = new HVEnvironment(null);

	/**
	 * コンストラクタ
	 */
	public HVCompositPart() {
		super();
	}

	public void debugPrint(CGraphics g) {
	}

	public void setEnvironment(HVEnvironment environment) {
		environment.setParent(this);//仮
		this.environment = environment;
	}

	public HVEnvironment getEnvironment() {
		return this.environment;
	}

	public void setPart(HVPart part) {
		this.part = part;
		part.setParent(this);//仮
	}

	public HVPart getPart() {
		return this.part;
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		//下の階層の大きさを決める
		this.part.layout(context);
		this.environment.layout(context);

		//自分の大きさを決める
		int w = 0, h = 0;
		w = part.getSize().width + this.environment.getSize().width;
		h = Math.max(part.getSize().height, environment.getSize().height);
		setSize(new Dimension(w + context.indent, h));

		//パートの大きさを再調整する
		part.setSize(new Dimension(part.getSize().width, getSize().height));

		//下の階層の位置を決める
		part.setPosition(new Point(0, 0));
		environment.setPosition(new Point(part.getSize().width, 0));
	}

	/**
	 * @see application.model.HVElement#paintComponent(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintComponent(CGraphics g, HRenderingContext context) {
		part.paint(g, context);
		environment.paint(g, context);
	}

}