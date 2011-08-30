/*
 * @(#)HVCapPart.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model.others;

import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import model.HNote;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVElement;

/**
 * @author Manabu Sugiura
 * @version $Id: HVNote.java,v 1.5 2009/09/10 03:48:32 macchan Exp $
 */
public class HVNote extends HVElement {

	private static Map<HNote, HVElement> viewToModel = new HashMap<HNote, HVElement>();
	private HNote model;
	private int index;

	/**
	 * コンストラクタ
	 */
	public HVNote(HNote model, int index) {
		super();
		this.model = model;
		this.index = index;
		viewToModel.put(model, this);
	}

	public int getIndex() {
		return this.index;
	}

	public static HVNote getModelFromView(HNote model) {
		return (HVNote) viewToModel.get(model);
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		setSize(new Dimension(context.calculateStringWidth(getText()),
				context.diameter));
	}

	/**
	 * @see application.model.HVElement#paintComponent(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintComponent(CGraphics g, HRenderingContext context) {
		Font defaultFont = g.getFont();
		Font noteFont = context.getNoteFont();
		g.setFont(noteFont);
		g.drawString(getText(), 0, noteFont.getSize());
		g.setFont(defaultFont);
	}

	private String getText() {
		return "*" + index + "：" + model.getContents();
	}

}