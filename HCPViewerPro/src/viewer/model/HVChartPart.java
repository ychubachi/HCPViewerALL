/*
 * @(#)HVChartPart.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.HChartElement;
import model.HNote;
import model.HProcessElement;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.connections.HVConnection;
import viewer.model.others.HVNote;

/**
 * @author Manabu Sugiura
 * @version $Id: HVChartPart.java,v 1.19 2009/09/10 03:48:31 macchan Exp $
 */
public abstract class HVChartPart extends HVPart {

	public static final int MARGIN = 3;

	private HChartElement model;

	private List<HVConnection> inputConnections = new ArrayList<HVConnection>();
	private List<HVConnection> outputConnections = new ArrayList<HVConnection>();

	private boolean ioReverse = false;

	private int lineNumber = -1;

	/**
	 * コンストラクタ
	 */
	public HVChartPart(HChartElement model) {
		super();
		this.model = model;
	}

	public HChartElement getModel() {
		return this.model;
	}

	public void addInputConnection(HVConnection conn) {
		inputConnections.add(conn);
	}

	public void addOutputConnection(HVConnection conn) {
		outputConnections.add(conn);
	}

	public int countConnectionKind() {
		int size = 0;
		if (!inputConnections.isEmpty()) {
			size++;
		}
		if (!outputConnections.isEmpty()) {
			size++;
		}
		return size;
	}

	public int getConnectionVerticalPosition(HVConnection conn) {
		int position = getCenterY();
		int ioReverse = this.ioReverse ? -1 : 1;
		if (countConnectionKind() == 2) {
			if (inputConnections.indexOf(conn) != -1) {
				position -= MARGIN * ioReverse;
			} else if (outputConnections.indexOf(conn) != -1) {
				position += MARGIN * ioReverse;
			} else {
				// assert false;
				throw new RuntimeException("assertion");
			}
		}
		return position;
	}

	/**
	 * @return Returns the ioReverse.
	 */
	public boolean isIoReverse() {
		return this.ioReverse;
	}

	/**
	 * @param ioReverse
	 *            The ioReverse to set.
	 */
	public void setIoReverse(boolean ioReverse) {
		this.ioReverse = ioReverse;
	}

	public void reverseIO() {
		this.ioReverse = !ioReverse;
	}

	public int getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		if (!isVisible()) {
			setSize(new Dimension(0, 0));
			return;
		}
		int w = context.diameter + context.textMargin
				+ context.calculateStringWidth(model.getText());
		setSize(new Dimension(w, context.diameter));
	}

	/**
	 * @see application.model.HVElement#paintComponent(application.CGraphics,
	 *      application.HRenderingContext)
	 */
	protected void paintComponent(CGraphics g, HRenderingContext context) {
		if (context.isPrintLineNumber() && model instanceof HProcessElement) {
			paintLineNumber(g, context);
		}

		paintIcon(g, context);
		paintText(g, context);
		paintNoteRef(g, context);
	}

	protected abstract void paintIcon(CGraphics g, HRenderingContext context);

	protected void paintText(CGraphics g, HRenderingContext context) {
		g.drawString(getModel().getText(), context.diameter
				+ context.textMargin, context.diameter * 5 / 6);
	}

	private void paintLineNumber(CGraphics g, HRenderingContext context) {
		// module領域に書くように修正 1.2.6
		// int x = g.getLeft() - context.lineNumberWidth + context.indent;
		int x = g.getLeft();
		int y = context.diameter;
		g.drawString(getLineNumber() + ":", x, y);
		context.addLineNumber();
	}

	private void paintNoteRef(CGraphics g, HRenderingContext context) {
		// 位置を決める
		int offset = getSize().width;

		// フォントを決める
		Font defaultFont = g.getFont();
		Font noteFont = context.getNoteFont();
		g.setFont(noteFont);

		// 書く
		for (Iterator<HNote> i = model.getNotes().iterator(); i.hasNext();) {
			HNote model = (HNote) i.next();
			HVNote note = HVNote.getModelFromView(model);
			String noteRef = "*" + note.getIndex();
			g.drawString(noteRef, offset, 5);
			offset += context.calculateStringWidth(noteRef);
		}

		// フォントを元に戻す
		g.setFont(defaultFont);
	}

	public Point getAbsLocation() {
		HVEnvironment parentEnv = getParentEnvironment();
		if (parentEnv == null) {
			return this.getPosition();
		}

		HVCompositPart composit = parentEnv.getParentCompositPart(this);
		System.out.println(composit.getPosition());
		return composit.getPosition();
	}

}