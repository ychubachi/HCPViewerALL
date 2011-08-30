/*
 * @(#)CGraphics.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer;

import java.awt.Color;
import java.awt.Font;

/**
 * Class CGraphics.
 * @author macchan
 * @version $Id: CGraphics.java,v 1.6 2005/01/22 16:34:47 gackt Exp $
 */
public interface CGraphics {

	public void drawArc(int x, int y, int width, int height, int startAngle,
			int arcAngle);
	public void drawLine(int x1, int y1, int x2, int y2);

	public void drawOval(int x, int y, int width, int height);

	public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints);

	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints);

	public void drawRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight);
	public void drawString(String str, int x, int y);

	public void fillArc(int x, int y, int width, int height, int startAngle,
			int arcAngle);

	public void fillOval(int x, int y, int width, int height);

	public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints);

	public void fillRect(int x, int y, int width, int height);

	public void fillRoundRect(int x, int y, int width, int height,
			int arcWidth, int arcHeight);

	public Color getColor();

	public void setColor(Color c);

	public Font getFont();

	public void setFont(Font font);

	public void translate(int x, int y);

	public void startTranslate();

	public void endTranslate();

	public int getLeft();

	public void dispose();

}