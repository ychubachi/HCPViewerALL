/*
 * BendPointBuilder.java
 * Copyright(c) 2004 CreW Project. All rights reserved.
 */
package viewer.model.connections.bendpoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import viewer.model.connections.HVConnection;

/**
 * Class BendPointBuilder
 * 
 * @author macchan
 * @version $Id: BendPointBuilder.java,v 1.6 2009/09/10 03:48:31 macchan Exp $
 */
public class BendPointBuilder {

	public static void setBendPoint(List<HVConnection> connections, int leftX, int rightX) {
		BendPointBuilder builder = new BendPointBuilder(connections, leftX,
				rightX);
		builder.build();
	}

	private List<HVConnection> connections;
	private int leftX;
	private int rightX;

	private BendPointBuilder(List<HVConnection> connections, int leftX, int rightX) {
		this.connections = new ArrayList<HVConnection>(connections);
		this.leftX = leftX;
		this.rightX = rightX;
	}

	private void build() {
		// グループを作る
		List<ConnectionGroup> groups = ConnectionGroupBuilder.createGroups(connections);

		// 折れる点を計算する
		for (Iterator<ConnectionGroup> i = groups.iterator(); i.hasNext();) {
			ConnectionGroup group = (ConnectionGroup) i.next();
			setBendPoint(groups, group);
		}
	}

	private void setBendPoint(List<ConnectionGroup> groups, ConnectionGroup group) {
		groups = new ArrayList<ConnectionGroup>(groups);
		groups.remove(group);// 自分自身ははずす
		// centerよりチョイ左側(1/3の位置)に変更 1.2.6
		int center = leftX + ((rightX - leftX) / 3);

		int i = 0;
		while (true) {
			int x = center + i * RectangleLine.X_MARGIN;
			group.setBendPointX(x);
			if (!intersects(groups, group)) {
				break;
			}
			i++;
		}
	}

	private boolean intersects(List<ConnectionGroup> groups, ConnectionGroup group) {
		for (Iterator<ConnectionGroup> i = groups.iterator(); i.hasNext();) {
			ConnectionGroup other = (ConnectionGroup) i.next();
			if (other.intersects(group)) {
				return true;
			}
		}
		return false;
	}

}