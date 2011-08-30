/*
 * HScaleComboBox.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.gui.parts;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComboBox;

import application.HViewProperty;

/**
 * Class HFontComboBox
 * 
 * @author macchan
 * @version $Id: HFontComboBox.java,v 1.3 2009/09/10 03:48:32 macchan Exp $
 */
public class HFontComboBox extends JComboBox implements ItemListener,
		PropertyChangeListener {

	public static final long serialVersionUID = 1L;
	
	private static int DEFAULT_SIZE = 12;

	private HViewProperty property;

	public HFontComboBox(HViewProperty property) {
		this.property = property;

		int selectedIndex = 0;
		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getAllFonts();
		for (int i = 0; i < fonts.length; i++) {
			if (fonts[i].getName().equals(getFont().getName())) {
				selectedIndex = i;
			}
			Font font12 = new Font(fonts[i].getName(), fonts[i].getStyle(),
					DEFAULT_SIZE);
			addItem(new FontComponent(font12));
		}
		addItemListener(this);
		property.addPropertyChangeListener(this);
		setSelectedIndex(selectedIndex);
	}

	/**
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
		property.setFont(getSelectedFont());
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (!evt.getPropertyName().equals(HViewProperty.CHANGE_FONT)
				&& !evt.getPropertyName().equals(HViewProperty.INITIALIZE)) {
			return;
		}

		// •\Ž¦‚ðØ‚è‘Ö‚¦‚é
		Font font = property.getFont();
		for (int i = 0; i < getItemCount(); i++) {
			FontComponent fontComponent = (FontComponent) getItemAt(i);
			Font each = ((FontComponent) fontComponent).getFont();
			if (each.getFontName().equals(font.getFontName())){
				setSelectedItem(fontComponent);
				return;
			}
		}
		throw new RuntimeException("Unregistered Font font=" + font.getFontName());
	}

	private Font getSelectedFont() {
		try {
			Object selectedItem = getSelectedItem();
			if (selectedItem != null) {
				return ((FontComponent) selectedItem).getFont();
			} else {
				return getFont();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	class FontComponent {
		private Font f;

		public FontComponent(Font f) {
			this.f = f;
		}

		public Font getFont() {
			return f;
		}

		public String toString() {
			return f.getName();
		}
	}

}