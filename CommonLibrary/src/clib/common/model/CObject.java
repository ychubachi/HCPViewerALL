/*
 * CObject.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CObject {

	public static final String WILDCARD = "WILDCARD";

	private PropertyChangeSupport propSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		propSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propSupport.removePropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		propSupport.removePropertyChangeListener(l);
	}

	protected void firePropertyChange() {
		firePropertyChange(WILDCARD);
	}

	protected void firePropertyChange(String propertyName) {
		firePropertyChange(propertyName, null, null);
	}

	protected void firePropertyChange(String propertyName, Object oldValue,
			Object newValue) {
		propSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

}
