/*
 * @(#)CAWTGraphicsAdapter.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.util.Stack;

/**
 * Class CAWTGraphicsAdapter.
 * @author macchan
 * @version $Id: CAWTGraphicsAdapter.java,v 1.10 2009/09/10 03:48:31 macchan Exp $
 */
public class CAWTGraphicsAdapter implements CGraphics {

	private Graphics g;
	private Stack<Transform> transformStack = new Stack<Transform>();

	public CAWTGraphicsAdapter(Graphics g, HRenderingContext context) {
		this.g = g;
		g.setFont(context.getBaseFont());
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#drawArc(int, int, int, int, int, int)
	 */
	public void drawArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		this.g.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	public void draw(Shape shape) {
		((Graphics2D) this.g).draw(shape);
	}

	public void fill(Shape shape) {
		((Graphics2D) this.g).fill(shape);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#drawLine(int, int, int, int)
	 */
	public void drawLine(int x1, int y1, int x2, int y2) {
		this.g.drawLine(x1, y1, x2, y2);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#drawOval(int, int, int, int)
	 */
	public void drawOval(int x, int y, int width, int height) {
		this.g.drawOval(x, y, width, height);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#drawPolygon(int[], int[], int)
	 */
	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		this.g.drawPolygon(xPoints, yPoints, nPoints);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#drawPolyline(int[], int[], int)
	 */
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		this.g.drawPolyline(xPoints, yPoints, nPoints);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#drawRoundRect(int, int, int, int, int, int)
	 */
	public void drawRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight) {
		this.g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#drawString(java.lang.String, int, int)
	 */
	public void drawString(String str, int x, int y) {
		this.g.drawString(str, x, y);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#fillArc(int, int, int, int, int, int)
	 */
	public void fillArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		this.g.fillArc(x, y, width, height, startAngle, arcAngle);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#fillOval(int, int, int, int)
	 */
	public void fillOval(int x, int y, int width, int height) {
		this.g.fillOval(x, y, width, height);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#fillPolygon(int[], int[], int)
	 */
	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
		this.g.fillPolygon(xPoints, yPoints, nPoints);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#fillRect(int, int, int, int)
	 */
	public void fillRect(int x, int y, int width, int height) {
		this.g.fillRect(x, y, width, height);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#fillRoundRect(int, int, int, int, int, int)
	 */
	public void fillRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight) {
		this.g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#getColor()
	 */
	public Color getColor() {
		return this.g.getColor();
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#setColor(java.awt.Color)
	 */
	public void setColor(Color c) {
		this.g.setColor(c);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#getFont()
	 */
	public Font getFont() {
		return this.g.getFont();
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#setFont(java.awt.Font)
	 */
	public void setFont(Font font) {
		this.g.setFont(font);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#translate(int, int)
	 */
	public void translate(int x, int y) {
		this.g.translate(x, y);
		if (!this.transformStack.isEmpty()) {
			Transform transform = (Transform) this.transformStack.peek();
			transform.tx += x;
			transform.ty += y;
		}
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#endTranslate()
	 */
	public void endTranslate() {
		if (this.transformStack.isEmpty()) {
			throw new RuntimeException("TransformStack is Empty");
		}

		Transform transform = (Transform) this.transformStack.pop();
		this.g.translate(-transform.tx, -transform.ty);
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#startTranslate()
	 */
	public void startTranslate() {
		this.transformStack.push(new Transform());
	}

	class Transform {
		int tx;
		int ty;
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#getLeft()
	 */
	public int getLeft() {
		int x = 0;
		for (int i = 0; i < this.transformStack.size(); i++) {
			Transform t = (Transform) this.transformStack.get(i);
			x += t.tx;
		}
		return -x;
	}

	/**
	 * @see jp.ac.keio.sfc.crew.graphics.CGraphics#getFontMetrics()
	 */
	public FontMetrics getFontMetrics() {
		return this.g.getFontMetrics();
	}

	/**
	 * @see viewer_old.CGraphics#calculateStringWidth(java.lang.String)
	 */
	public int getStrWidth(String s) {
		FontMetrics metrics = this.g.getFontMetrics();
		return metrics.stringWidth(s);
	}

	/**
	 * @see viewer_old.CGraphics#getAbsoluteContextLocation()
	 */
	public Point getAbsoluteContextLocation() {
		int x = 0;
		int y = 0;
		for (int i = 0; i < this.transformStack.size(); i++) {
			Transform transform = (Transform) this.transformStack.get(i);
			x += transform.tx;
			y += transform.ty;
		}
		return new Point(x, y);
	}

	/**
	 * @see application.CGraphics#dispose()
	 */
	public void dispose() {
		g.dispose();
	}

}