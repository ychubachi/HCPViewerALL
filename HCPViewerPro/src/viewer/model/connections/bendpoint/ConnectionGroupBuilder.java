/*
 * ConnectionGroupBuilder.java
 * Copyright(c) 2004 CreW Project. All rights reserved.
 */
package viewer.model.connections.bendpoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import viewer.model.HVChartPart;
import viewer.model.connections.HVConnection;

/**
 * Class ConnectionGroupBuilder
 * 
 * @author macchan
 * @version $Id: ConnectionGroupBuilder.java,v 1.3 2009/09/10 03:48:31 macchan Exp $
 */
public class ConnectionGroupBuilder {

	public static List<ConnectionGroup> createGroups(List<HVConnection> connections) {
		ConnectionGroupBuilder map = new ConnectionGroupBuilder(connections);
		return map.createGroups();
	}

	private List<HVConnection> connections;

	private ConnectionGroupBuilder(List<HVConnection> connections) {
		this.connections = connections;
	}

	private List<ConnectionGroup> createGroups() {
		Map<Entry, ConnectionGroup> groups = new LinkedHashMap<Entry, ConnectionGroup>();

		//Processをまとめつつ，マップに加える
		for (Iterator<HVConnection> i = connections.iterator(); i.hasNext();) {
			HVConnection connection = (HVConnection) i.next();
			put(groups, connection, connection.getProcessPart());
		}

		//まだまとまっていないものを取り出す(Processですでにまとまっているものは，まとめないので)
		List<HVConnection> singleGroupConnections = new ArrayList<HVConnection>();
		for (Iterator<ConnectionGroup> i = groups.values().iterator(); i.hasNext();) {
			ConnectionGroup group = (ConnectionGroup) i.next();
			if (group.isSingle()) {
				i.remove();
				singleGroupConnections.add(group.getFirst());
			}
		}

		//Dataをまとめつつ，マップに加える
		for (Iterator<HVConnection> i = singleGroupConnections.iterator(); i.hasNext();) {
			HVConnection connection = (HVConnection) i.next();
			put(groups, connection, connection.getDataPart());
		}

		return new ArrayList<ConnectionGroup>(groups.values());
	}

	private void put(Map<Entry, ConnectionGroup> map, HVConnection conn, HVChartPart part) {
		Entry entry = new Entry(part, conn.isDataToProcess());

		if (!map.containsKey(entry)) {
			map.put(entry, new ConnectionGroup());
		}

		ConnectionGroup group = (ConnectionGroup) map.get(entry);
		group.add(conn);
	}

	class Entry {
		HVChartPart part;
		boolean dataToProcess;
		Entry(HVChartPart part, boolean dataToProcess) {
			this.part = part;
			this.dataToProcess = dataToProcess;
		}
		public boolean equals(Object o) {
			Entry another = (Entry) o;
			return part == another.part
					&& dataToProcess == another.dataToProcess;
		}
		public int hashCode() {
			return toString().hashCode();
		}
		public String toString() {
			return part.toString() + dataToProcess;
		}
	}
}