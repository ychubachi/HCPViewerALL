/*
 * @(#)HVLine.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model.connections;

import java.awt.Point;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import viewer.CGraphics;

/**
 * 線を表現するクラス
 * 
 * 複数の交点を所持することができ，交点付近は線を書かない．
 * このアルゴリズムを一般化するため，交点の描画は必ず次の手順でように行われる
 * １．x座標が小さいほうから，大きいほうへ書く
 * ２．2点のx座標が同様の場合は，y座標が小さいほうから，大きいほうへ書く
 * 
 * 所持される
 * 
 * @author Manabu Sugiura
 * @version $Id: HVLine.java,v 1.4 2009/09/10 03:48:32 macchan Exp $
 */
public class HVLine {

	public static int MARGIN = 5;
	public static Comparator<Point> COMPARATOR = new Comparator<Point>() {
		public int compare(Point p1, Point p2) {
			if (p1.x != p2.x) {
				return p1.x - p2.y;
			}

			return p1.y - p2.y;
		}
	};

	private Point start, end;
	private SortedSet<Point> intersections = new TreeSet<Point>(COMPARATOR);

	private int xMargin, yMargin;

	public HVLine(Point p1, Point p2) {
		//クラスコメントの仕様になるよう，start, end座標を設定する
		if (p1.x != p2.x) {
			if (p1.x <= p2.x) {
				this.start = p1;
				this.end = p2;
			} else {
				this.start = p2;
				this.end = p1;
			}
		} else {
			if (p1.y <= p2.y) {
				this.start = p1;
				this.end = p2;
			} else {
				this.start = p2;
				this.end = p1;
			}
		}

		//xMargin, yMarginを算出する
		if (isHorizontal()) {
			xMargin = MARGIN;
		} else if (isVertical()) {
			yMargin = MARGIN;
		}
	}

	public Point getStart() {
		return new Point(this.start);
	}

	public Point getEnd() {
		return new Point(this.end);
	}

	public void paintComponent(CGraphics g) {
		Point p1, p2;
		p1 = this.start;

		for (Iterator<Point> i = intersections.iterator(); i.hasNext();) {
			p2 = (Point) i.next();
			if (!(p2.x - p1.x < xMargin || p2.y - p1.y < yMargin)) {
				g.drawLine(p1.x, p1.y, p2.x - xMargin, p2.y - yMargin);
			}

			p1 = new Point(p2);
			p1.translate(xMargin, yMargin);
		}

		p2 = this.end;
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
	}

	public int getMaxX() {
		return Math.max(start.x, end.x);
	}
	public int getMinX() {
		return Math.min(start.x, end.x);
	}
	public int getMaxY() {
		return Math.max(start.y, end.y);
	}
	public int getMinY() {
		return Math.min(start.y, end.y);
	}

	public void addIntersectPoint(HVLine another) {
		Point p = getIntersectPoint(another);
		if (p != null) {
			intersections.add(p);
		}
	}

	private Point getIntersectPoint(HVLine another) {
		if (isVertical() && another.isHorizontal()) {
			return getIntersectPoint(another, this);
		} else if (isHorizontal() && another.isVertical()) {
			return getIntersectPoint(this, another);
		}
		return null;
	}

	private Point getIntersectPoint(HVLine horizontal, HVLine vertical) {
		//仮交点		
		int x = vertical.start.x;
		int y = horizontal.start.y;

		//ほんとに交差するか調べる（ねじれの位置にあるかもしれない）
		if (horizontal.getMaxX() <= x || horizontal.getMinX() >= x) {
			return null;
		}
		if (vertical.getMaxY() <= y || vertical.getMinY() >= y) {
			return null;
		}

		return new Point(x, y);
	}

	private boolean isVertical() {
		return this.start.x == this.end.x;
	}

	private boolean isHorizontal() {
		return this.start.y == this.end.y;
	}

}