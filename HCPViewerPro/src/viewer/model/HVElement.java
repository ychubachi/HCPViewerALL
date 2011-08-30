/*
 * @(#)HVElement.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.others.HVModule;

/**
 * @author Manabu Sugiura
 * @version $Id: HVElement.java,v 1.16 2009/09/10 03:48:31 macchan Exp $
 */
public abstract class HVElement {

	/***************************************************************************
	 * インスタンス変数
	 **************************************************************************/

	private Point position = new Point();
	private Dimension size = new Dimension();
	private HVEnvironment parentEnvironment;
	private HVElement parent;// 仮
	private boolean visible = true;

	/**
	 * コンストラクタ
	 */
	public HVElement() {
		super();
	}

	/***************************************************************************
	 * プロパティ設定・取得
	 **************************************************************************/

	public boolean isVisible() {
		return this.visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return new Point(this.position);
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public Dimension getSize() {
		return new Dimension(this.size);
	}

	public void setParentEnvironment(HVEnvironment parentEnvironment) {
		this.parentEnvironment = parentEnvironment;
	}

	public HVEnvironment getParentEnvironment() {
		return this.parentEnvironment;
	}

	public HVElement getParent() {
		return this.parent;
	}

	public void setParent(HVElement parent) {
		this.parent = parent;
	}

	public Point getAbsoluteLocation() {
		Point p = new Point(this.position);
		toAbsolute(p);
		return p;
	}

	// FirstLevelとは，HVModule直下の, Header, Note, Process, Dataの4領域を指す
	// 厳密に言えば，HVModule直下とのRelativeを求めているので，名前は変
	// toAbsoluteを強引に抽象化したので，少しよみにくくなっている．
	public Point getFirstLevelAbsoluteLocation() {
		if (parent instanceof HVModule) {
			return new Point();
		}
		Point p = new Point(this.position);
		toAbsolute(p, true);
		return p;
	}

	public void toAbsolute(Point p) {
		toAbsolute(p, false);
	}

	private void toAbsolute(Point p, boolean firstLevel) {
		HVElement element = this.parent;
		while (true) {
			if (element == null
					|| (firstLevel && element.parent instanceof HVModule)) {
				break;
			}
			p.translate(element.position.x, element.position.y);
			element = element.parent;
		}
	}

	public int getLeftX() {
		return position.x;
	}

	public int getRightX() {
		return position.x + size.width;
	}

	public int getTopY() {
		return position.y;
	}

	public int getBottomY() {
		return position.y + size.height;
	}

	public int getCenterX() {
		return position.x + size.width / 2;
	}

	public int getCenterY() {
		return position.x + size.height / 2;
	}

	public HVModule getRootModule() {
		HVElement element = this;
		while (element != null && !(element instanceof HVModule)) {
			element = element.parent;
		}
		return (HVModule) element;
	}

	/***************************************************************************
	 * レイアウト
	 **************************************************************************/

	public abstract void layout(HRenderingContext context);

	/***************************************************************************
	 * 描画
	 **************************************************************************/

	public void paint(CGraphics g, HRenderingContext context) {
		if (!isVisible()) {
			return;
		}
		g.startTranslate();
		g.translate(getPosition().x, getPosition().y);
		if (context.isDebugMode()){
			debugPrint(g);
		}
		paintComponent(g, context);
		g.endTranslate();
	}

	protected abstract void paintComponent(CGraphics g,
			HRenderingContext context);

	/***************************************************************************
	 * デバッグ
	 **************************************************************************/

	public void debugPrint(CGraphics g) {
		// 範囲の描画
		g.setColor(Color.yellow);
		g.drawRoundRect(0, 0, getSize().width, getSize().height, 2, 2);
		g.setColor(Color.black);

		// 基点の描画
		g.setColor(Color.blue);
		g.drawOval(0, 0, 1, 1);
		g.setColor(Color.black);
	}

}