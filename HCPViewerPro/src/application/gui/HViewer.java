/*
 * @(#)HTestViewer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package application.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import model.HModule;
import viewer.CAWTGraphicsAdapter;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.others.HVModule;
import application.HViewProperty;

/**
 * Class HViewer.
 * 
 * @author Manabu Sugiura
 * @version $Id: HViewer.java,v 1.14 2009/09/10 03:48:32 macchan Exp $
 */
public class HViewer extends JPanel implements PropertyChangeListener {

	public static final long serialVersionUID = 1L;

	/***************************************************************************
	 * Instance Variable.
	 **************************************************************************/

	private HModule model = null;
	private HViewProperty property = null;
	private HRenderingContext context = null;

	private HVModule viewRoot = null;

	/***************************************************************************
	 * Constractor.
	 **************************************************************************/

	// public HViewer(HModule model, HViewProperty property) {
	// this(model, new HAWTRenderingContext(property), property);
	// }
	// public HViewer(HModule model, HRenderingContext context) {
	// this(model, context, new HViewProperty());
	// }
	public HViewer(HModule model, HRenderingContext context,
			HViewProperty property) {
		this.context = context;
		this.property = property;
		initialize();
		setModel(model);
	}

	private void initialize() {
		setBackground(Color.white);
		property.addPropertyChangeListener(this);
	}

	/***************************************************************************
	 * Getters & Setters.
	 **************************************************************************/

	public HModule getModel() {
		return model;
	}

	public void setModel(HModule model) {
		this.model = model;
		if (this.model != null) {
			rebuildHVRoot();
		}
	}

	public HViewProperty getProperty() {
		return this.property;
	}

	/***************************************************************************
	 * Prepare Painting
	 **************************************************************************/

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(HViewProperty.HIDE_ERROR_CHECK)) {
			// this.viewRoot.hideErrorCheck(!property.isHideErrorCheck());
		} else if (evt.getPropertyName().equals(HViewProperty.LEVEL)) {
			// this.viewRoot.setRenderringLevel(property.getRenderringLevel());
			rebuildHVRoot();
		} else if (evt.getPropertyName().equals(HViewProperty.HIDE_HEADER)) {
			// this.context.setPrintHeader(!property.isHideHeader());
			rebuildHVRoot();
		} else if (evt.getPropertyName().equals(HViewProperty.HIDE_LINE_NUMBER)) {
			// this.context.setPrintLineNumber(!property.isHideLineNumber());
			rebuildHVRoot();
		} else if (evt.getPropertyName().equals(HViewProperty.SCALE)) {
		} else if (evt.getPropertyName().equals(HViewProperty.CHANGE_FONT)) {
			this.context.setFont(this.property.getFont());
		} else {
			// throw new RuntimeException("Unknown event");
		}

		refreshSize();
		repaint();
	}

	private void rebuildHVRoot() {
		this.viewRoot = new HVModule(this.model, property.getRenderringLevel());
		refreshSize();
	}

	private void refreshSize() {
		viewRoot.layout(context);
		Dimension moduleSize = new Dimension(viewRoot.getSize());

		double scale = property.getScale();
		moduleSize.width += (context.xMargin * 2);
		moduleSize.height += (context.yMargin * 2);
		moduleSize.width = (int) (moduleSize.width * scale);
		moduleSize.height = (int) (moduleSize.height * scale);
		setPreferredSize(moduleSize);
		revalidate();
	}

	/***************************************************************************
	 * Painting.
	 **************************************************************************/

	public void paint(Graphics g) {
		super.paint(g);
		paintVisual(g);
	}

	private void paintVisual(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform beforeTransform = g2.getTransform();

		// 描画の設定
		configGraphics(g2, beforeTransform, property.getScale(), true);

		// 描画する
		CAWTGraphicsAdapter cg = new CAWTGraphicsAdapter(g, context);
		paint(cg);// camei publicメソッド化

		g2.setTransform(beforeTransform);
	}

	public void paint(CGraphics cg) {
		cg.translate(context.xMargin, context.yMargin);
		viewRoot.paint(cg, context);
	}

	public static void configGraphics(Graphics2D g2,
			AffineTransform beforeTransform, double scale, boolean antialiasing) {
		// スケール設定
		AffineTransform transform = (AffineTransform) beforeTransform.clone();
		transform.concatenate(AffineTransform.getScaleInstance(scale, scale));
		g2.setTransform(transform);

		// 色設定
		g2.setBackground(Color.WHITE);
		g2.setColor(Color.BLACK);

		// アンチエイリアス
		if (antialiasing) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
		}
	}

}