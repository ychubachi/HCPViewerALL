/*
 * HScaleComboBox.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.gui.parts;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.Format;

import javax.swing.JComboBox;

import application.HViewProperty;

/**
 * Class HScaleComboBox
 * 
 * @author macchan
 * @version $Id: HScaleComboBox.java,v 1.3 2009/09/10 03:48:32 macchan Exp $
 */
public class HScaleComboBox extends JComboBox
		implements
			ItemListener,
			PropertyChangeListener {
	public static final long serialVersionUID = 1L;
	private static final Format formatter = new DecimalFormat("#");

	private HViewProperty property;

	public HScaleComboBox(HViewProperty property, double[] scales) {
		this.property = property;
		for (int i = 0; i < scales.length; i++) {
			addItem(new Scale(scales[i]));
		}
		addItemListener(this);
		property.addPropertyChangeListener(this);
	}

	public void scaleUp() {
		int index = getSelectedIndex();
		if (index + 1 < getItemCount()) {
			setSelectedIndex(index + 1);
		}
	}

	public void scaleDown() {
		int index = getSelectedIndex();
		if (index - 1 >= 0) {
			setSelectedIndex(index - 1);
		}
	}

	/**
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
		property.setScale(getScale());
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getPropertyName().equals(HViewProperty.SCALE)
				&& !evt.getPropertyName().equals(HViewProperty.INITIALIZE)) {
			return;
		}

		//ï\é¶ÇêÿÇËë÷Ç¶ÇÈ
		double scale = property.getScale();
		for (int i = 0; i < getItemCount(); i++) {
			Scale scaleObj = (Scale) getItemAt(i);
			if (scaleObj.getValue() == scale) {
				setSelectedItem(scaleObj);
				return;
			}
		}

		throw new RuntimeException("Unregistered Scale scale=" + scale);
	}

	private double getScale() {
		try {
			Object selectedItem = getSelectedItem();
			if (selectedItem != null) {
				return ((Scale) selectedItem).getValue();
			} else {
				return 1d;
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	class Scale {

		private double value;

		Scale(double scale) {
			this.value = scale;
		}

		public double getValue() {
			return value;
		}

		public String toString() {
			return formatter.format(new Double(value * 100d)) + "%";
		}
	}

}