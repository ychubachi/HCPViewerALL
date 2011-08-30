/*
 * @(#)HVNoteArea.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model.others;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVElement;
import viewer.model.HVEnvironment;

/**
 * @author Manabu Sugiura
 * @version $Id: HVNoteArea.java,v 1.6 2009/09/10 03:48:32 macchan Exp $
 */
public class HVNoteArea extends HVElement {

	private HVModule module;
	private List<HVNote> notes = new ArrayList<HVNote>();

	/**
	 * コンストラクタ
	 */
	public HVNoteArea(HVModule module) {
		this.module = module;
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		HVEnvironment processEnv = module.getProcessEnvironment();

		if (context.isPrintHeader()) {
			setPosition(new Point(0, processEnv.getSize().height
					+ module.getHeader().getSize().height));
		} else {
			setPosition(new Point(0, processEnv.getSize().height));
		}

		Dimension size = new Dimension();

		for (Iterator<HVNote> i = this.notes.iterator(); i.hasNext();) {
			HVNote note = (HVNote) i.next();
			note.layout(context);
			size.height += note.getSize().height;
			size.width = Math.max(size.width, note.getSize().width);
		}

		if (size.width < processEnv.getSize().width) {
			size.width = processEnv.getSize().width;
		}

		setSize(size);
	}

	/**
	 * @see application.model.HVElement#paintComponent(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintComponent(CGraphics g, HRenderingContext context) {
		for (Iterator<HVNote> i = this.notes.iterator(); i.hasNext();) {
			HVNote note = (HVNote) i.next();
			note.paint(g, context);
			g.translate(0, note.getSize().height);
		}
	}

	public void addNote(HVNote note) {
		this.notes.add(note);
	}

	public void debugPrint(CGraphics g) {
		//範囲の描画
		g.setColor(Color.green);
		g.drawRoundRect(0, 0, getSize().width, getSize().height, 2, 2);
		g.setColor(Color.black);

		//基点の描画
		g.setColor(Color.blue);
		g.drawOval(0, 0, 1, 1);
		g.setColor(Color.black);
	}
}