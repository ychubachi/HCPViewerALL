/*
 * HVConnection.java
 * Copyright(c) 2004 CreW Project. All rights reserved.
 */
package viewer.model.connections;

import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import viewer.CGraphics;
import viewer.model.HVChartPart;

/**
 * Class HVConnection
 * 
 * @author macchan
 * @version $Id: HVConnection.java,v 1.14 2009/09/10 03:48:32 macchan Exp $
 */
public class HVConnection {

	public static final int CORNER_RADIUS = 6; //コーナーの半径
	public static final int ARROW_WIDTH = 8; //矢印の幅
	public static final int ARROW_HEIGHT = 4; //矢印の高さ

	//属性
	private HVChartPart processPart;
	private HVChartPart dataPart;
	private boolean dataToProcess;
	private int bendPointX;

	//Cash
	private Point start;
	private Point end;

	//Cash
	private HVLine firstHorizontalPart;
	private HVLine verticalPart;
	private HVLine secondHorizontalPart;

	/**
	 * Constructor for HVConnection
	 */
	public HVConnection(HVChartPart processPart, HVChartPart dataPart,
			boolean dataToProcess) {
		this.processPart = processPart;
		this.dataPart = dataPart;

		this.dataToProcess = dataToProcess;
	}

	public void refreshPosition() {
		//Set Start & End
		start = calcStartLocation();
		end = calcEndLocation();

		//Set mergeX
		//		int kariMergeX = (start.x + end.x) / 2;
		//		int y = Math.min(start.y, end.y);
		//		int len = Math.abs(start.y - end.y);
	}

	public void setBendPointX(int x) {
		this.bendPointX = x;
	}

	public int getBendPointX() {
		return bendPointX;
	}

	public HVChartPart getProcessPart() {
		return this.processPart;
	}

	public HVChartPart getDataPart() {
		return this.dataPart;
	}

	public void refreshIntersection(List<HVConnection> others) {
		for (Iterator<HVConnection> i = others.iterator(); i.hasNext();) {
			HVConnection other = (HVConnection) i.next();
			this.verticalPart.addIntersectPoint(other.firstHorizontalPart);
			this.verticalPart.addIntersectPoint(other.secondHorizontalPart);
		}
	}

	public void paint(CGraphics g) {
		this.drawLines(g);
		this.drawRoundCorners(g);
		this.drawArrow(g);
	}

	private void drawLines(CGraphics g) {
		this.firstHorizontalPart.paintComponent(g);
		this.verticalPart.paintComponent(g);
		this.secondHorizontalPart.paintComponent(g);
	}

	private void drawRoundCorners(CGraphics g) {
		if (this.start.x < this.end.x) { //始点の方が左
			if (this.start.y > this.end.y) {
				//始点が終点より低い
				g.drawArc(this.bendPointX - CORNER_RADIUS, this.start.y
						- CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, 270, 90);
				g.drawArc(this.bendPointX, this.end.y, CORNER_RADIUS,
						CORNER_RADIUS, 90, 90);
			} else if (this.start.y < this.end.y) {
				//始点が終点より高い
				g.drawArc(this.bendPointX - CORNER_RADIUS, this.start.y,
						CORNER_RADIUS, CORNER_RADIUS, 0, 90);
				g.drawArc(this.bendPointX, this.end.y - CORNER_RADIUS,
						CORNER_RADIUS, CORNER_RADIUS, 180, 90);
			}
		} else if (this.start.x > this.end.x) { //始点の方が右
			if (this.start.y > this.end.y) {
				//始点が終点より低い
				g.drawArc(this.bendPointX, this.start.y - CORNER_RADIUS,
						CORNER_RADIUS, CORNER_RADIUS, 180, 90);
				g.drawArc(this.bendPointX - CORNER_RADIUS, this.end.y,
						CORNER_RADIUS, CORNER_RADIUS, 0, 90);
			} else if (this.start.y < this.end.y) {
				//始点が終点より高い
				g.drawArc(this.bendPointX, this.start.y, CORNER_RADIUS,
						CORNER_RADIUS, 90, 90);
				g.drawArc(this.bendPointX - CORNER_RADIUS, this.end.y
						- CORNER_RADIUS, CORNER_RADIUS, CORNER_RADIUS, 270, 90);
			}
		}
	}

	private void drawArrow(CGraphics g) {
		int haw = ARROW_WIDTH / 2;
		int hah = ARROW_HEIGHT / 2;

		if (dataToProcess) {
			int x = start.x;
			int y = start.y;
			g.drawLine(x, y, x + haw, y + hah);
			g.drawLine(x, y, x + haw, y - hah);
		} else {
			int x = end.x;
			int y = end.y;
			g.drawLine(x, y, x - haw, y + hah);
			g.drawLine(x, y, x - haw, y - hah);
		}
	}

	public Point calcStartLocation() {
		int x = processPart.getRightX();
		int y = processPart.getConnectionVerticalPosition(this);
		Point p = new Point(x, y);
		processPart.toAbsolute(p);
		return p;
	}

	public Point calcEndLocation() {
		int x = dataPart.getLeftX();
		int y = dataPart.getConnectionVerticalPosition(this);
		Point p = new Point(x, y);
		dataPart.toAbsolute(p);
		return p;
	}

	public static final int MARGIN = 3;

	public void buildParts() {
		//最初の横線
		Point firstPartEnd = null;
		if (this.start.x < this.end.x) { //始点の方が左
			firstPartEnd = new Point(this.bendPointX - CORNER_RADIUS / 2,
					this.start.y);
			this.firstHorizontalPart = new HVLine(this.start, firstPartEnd);
		} else if (this.start.x >= this.end.x) { //始点の方が右
			firstPartEnd = new Point(this.bendPointX + CORNER_RADIUS / 2,
					this.start.y);
		}
		this.firstHorizontalPart = new HVLine(this.start, firstPartEnd);

		//縦線
		Point verticalPartStart = null;
		Point verticalPartEnd = null;
		if (this.start.y > this.end.y) { //始点が終点より低い
			verticalPartStart = new Point(this.bendPointX, this.start.y
					- CORNER_RADIUS / 2);
			verticalPartEnd = new Point(this.bendPointX, this.end.y
					+ CORNER_RADIUS / 2);
		} else if (this.start.y < this.end.y) { //始点が終点より高い
			verticalPartStart = new Point(this.bendPointX, this.start.y
					+ CORNER_RADIUS / 2);
			verticalPartEnd = new Point(this.bendPointX, this.end.y
					- CORNER_RADIUS / 2);
		} else {
			verticalPartStart = new Point(this.bendPointX - CORNER_RADIUS,
					this.start.y);
			verticalPartEnd = new Point(this.bendPointX + CORNER_RADIUS,
					this.end.y);
		}
		this.verticalPart = new HVLine(verticalPartStart, verticalPartEnd);

		//2番目の横線
		Point secondPartStart = null;
		if (this.start.x < this.end.x) { //始点の方が左
			secondPartStart = new Point(this.bendPointX + CORNER_RADIUS / 2,
					this.end.y);
		} else if (this.start.x >= this.end.x) { //始点の方が右
			secondPartStart = new Point(this.bendPointX - CORNER_RADIUS / 2,
					this.end.y);
		}
		this.secondHorizontalPart = new HVLine(secondPartStart, new Point(
				this.end.x, this.end.y));
	}

	public boolean isDataToProcess() {
		return this.dataToProcess;
	}
	public Point getEnd() {
		return this.end;
	}

	public Point getStart() {
		return this.start;
	}

	public int getTopY() {
		return Math.min(start.y, end.y);
	}

	public int getBottomY() {
		return Math.max(start.y, end.y);
	}
}