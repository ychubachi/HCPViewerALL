/*
 * ConnectionGroup.java
 * Copyright(c) 2004 CreW Project. All rights reserved.
 */
package viewer.model.connections.bendpoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import viewer.model.connections.HVConnection;

/**
 * Class ConnectionGroup
 * 
 * @author macchan
 * @version $Id: ConnectionGroup.java,v 1.3 2009/09/10 03:48:31 macchan Exp $
 */
public class ConnectionGroup {

	private List<HVConnection> connections = new ArrayList<HVConnection>();

	public void add(HVConnection conn) {
		connections.add(conn);
	}

	public boolean isSingle() {
		return connections.size() == 1;
	}

	public HVConnection getFirst() {
		return (HVConnection) connections.get(0);
	}

	public void setBendPointX(int x) {
		for (Iterator<HVConnection> i = connections.iterator(); i.hasNext();) {
			HVConnection conn = (HVConnection) i.next();
			conn.setBendPointX(x);
		}
	}

	public int getBendPointX() {
		return getFirst().getBendPointX();
	}

	public boolean intersects(ConnectionGroup another) {
		return getVerticalRectangleLine().intersects(
				another.getVerticalRectangleLine());
	}

	private RectangleLine getVerticalRectangleLine() {
		return new RectangleLine(getBendPointX(), getTopY(), getBottomY());
	}

	public int getTopY() {
		int y = Integer.MAX_VALUE;
		for (Iterator<HVConnection> i = connections.iterator(); i.hasNext();) {
			HVConnection conn = (HVConnection) i.next();
			y = Math.min(y, conn.getTopY());
		}
		return y;
	}

	public int getBottomY() {
		int y = 0;
		for (Iterator<HVConnection> i = connections.iterator(); i.hasNext();) {
			HVConnection conn = (HVConnection) i.next();
			y = Math.max(y, conn.getBottomY());
		}
		return y;
	}

	//	public void add(Line line) {
	//		connections.add(line);
	//	}
	//
	//	public boolean intersects(Line another) {
	//		for (Iterator i = connections.iterator(); i.hasNext();) {
	//			Line line = (Line) i.next();
	//			if (line.intersects(another)) {
	//				return true;
	//			}
	//		}
	//		return false;
	//	}
	//
	//	void reCalcX() {
	//		int x = (getLeftX() + getRightX()) / 2;
	//		int y = getTopY();
	//		int len = getBottomY() - y;
	//
	//		System.out.println(x + "," + y + "," + len);
	//
	//		Line candidate = new Line(null, x, y, len);
	//		for (int i = 0; intersects(candidate); i++) {
	//			int newCanPosition = i * 5;
	//			//			int newCanPosition = 0;
	//			//			if (i % 2 == 0) {
	//			//				newCanPosition = -(i - 1) * 5;
	//			//			} else {
	//			//				newCanPosition = (i - 1) * 5;
	//			//			}
	//			candidate = new Line(null, x + newCanPosition, y, len);
	//		}
	//
	//		System.out.println(candidate.x);
	//
	//		resetX(candidate.x);
	//	}
	//
	//	void resetX(int x) {
	//		for (Iterator i = connections.iterator(); i.hasNext();) {
	//			Line line = (Line) i.next();
	//			line.conn.setMergeX(x);
	//		}
	//	}
	//
	//	int getX() {
	//		return ((Line) connections.get(0)).x;
	//	}
	//

}