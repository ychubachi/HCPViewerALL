/*
 * HLevelButton.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.gui.parts;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JToggleButton;

import application.HViewProperty;

/**
 * Class HLevelButton
 * 
 * @author macchan
 * @version $Id: HLevelButton.java,v 1.4 2009/09/10 03:48:32 macchan Exp $
 */
public class HLevelButton extends JToggleButton
		implements
			PropertyChangeListener,
			ActionListener {
	public static final long serialVersionUID = 1L;
	private HViewProperty property;
	private int renderringLevel;

	public HLevelButton(HViewProperty property, int renderringLevel) {
		this.property = property;
		this.renderringLevel = renderringLevel;
		addActionListener(this);
		property.addPropertyChangeListener(this);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		property.setRenderringLevel(renderringLevel);
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getPropertyName().equals(HViewProperty.LEVEL)
				&& !evt.getPropertyName().equals(HViewProperty.INITIALIZE)) {
			return;
		}

		int renderringLevel = property.getRenderringLevel();
		if (this.renderringLevel == renderringLevel) {
			this.setSelected(true);
		} else {
			this.setSelected(false);
		}
	}

}