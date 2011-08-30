/*
 * RectangleLine.java
 * Copyright(c) 2004 CreW Project. All rights reserved.
 */
package viewer.model.connections.bendpoint;

import java.awt.Rectangle;

/**
 * Class RectangleLine
 * 
 * @author macchan
 * @version $Id: RectangleLine.java,v 1.2 2004/12/02 16:45:58 macchan Exp $
 */
public class RectangleLine {

	public static final int X_MARGIN = 5;
	public static final int Y_MARGIN = 1;

	private int x, y1, y2;

	//ƒLƒƒƒbƒVƒ…
	private Rectangle rect;

	public RectangleLine(int x, int y1, int y2) {
		this.x = x;
		this.y1 = y1;
		this.y2 = y2;
		createCash();
	}

	private void createCash() {
		int x = getX() - X_MARGIN;
		int width = X_MARGIN * 2;
		int y = getTopY() - Y_MARGIN;
		int height = getBottomY() - getTopY() + Y_MARGIN * 2;
		rect = new Rectangle(x, y, width, height);
	}

	public boolean intersects(RectangleLine another) {
		return rect.intersects(another.rect);
	}

	private int getX() {
		return x;
	}

	private int getTopY() {
		return Math.min(y1, y2);
	}

	private int getBottomY() {
		return Math.max(y1, y2);
	}

	//	public void setX(int x) {
	//		this.x = x;
	//		createCash();
	//	}
	//
	//	public void setY1(int y) {
	//		this.y1 = y;
	//		createCash();
	//	}
	//
	//	public void setY2(int y) {
	//		this.y2 = y;
	//		createCash();
	//	}

}