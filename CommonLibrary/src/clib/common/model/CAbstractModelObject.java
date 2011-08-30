/*
 * CAbstractModelObject.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.model;

import java.util.Map;
import java.util.WeakHashMap;

public class CAbstractModelObject implements ICModelObject {

	private Map<ICModelListener, ICModelListener> listeners = new WeakHashMap<ICModelListener, ICModelListener>();
	private boolean notifyMode = true;

	public CAbstractModelObject() {
	}

	public void addModelListener(ICModelListener l) {
		if (!this.listeners.containsKey(l)) {
			this.listeners.put(l, l);
		}
	}

	public void removeModelListener(ICModelListener l) {
		if (this.listeners.containsKey(l)) {
			this.listeners.remove(l);
		}
	}

	protected void fireModelUpdated(Object... args) {
		if (notifyMode) {
			for (ICModelListener l : listeners.values()) {
				l.modelUpdated(args);
			}
		}
	}

	public void setNotifyMode(boolean notifyMode) {
		this.notifyMode = notifyMode;
	}

}
