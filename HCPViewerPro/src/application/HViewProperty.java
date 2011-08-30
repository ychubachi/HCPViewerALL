/*
 * HViewProperty.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application;

import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Class HViewProperty
 * 
 * @author macchan
 * @version $Id: HViewProperty.java,v 1.5 2008/10/06 02:38:25 macchan Exp $
 */
public class HViewProperty {

	public static final String LEVEL = "level";
	public static final String SCALE = "scale";
	public static final String CHANGE_FONT = "font";
	public static final String HIDE_ERROR_CHECK = "hideErrorCheck";
	public static final String HIDE_HEADER = "hideHeader";
	public static final String HIDE_LINE_NUMBER = "hideLineNumber";
	public static final String INITIALIZE = "initialize";

	public static final int RENDERRING_LEVEL_ALL = 10000;// 10000をALLレベルの番兵値とする
	public static final double SCALE_DEFAULT = 1d;

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	private int renderringLevel = RENDERRING_LEVEL_ALL;
	private double scale = SCALE_DEFAULT;
	private boolean hideErrorCheck = false;
	private boolean hideLineNumber = false;
	private boolean hideHeader = false;
	// private Font font = HRenderingContext.getGraphics().getFont();
	private Font font = new Font("MS Gothic", Font.PLAIN, 12);

	public HViewProperty() {
		// GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
	}

	public boolean isRenderringLevelALL() {
		return renderringLevel == RENDERRING_LEVEL_ALL;
	}

	public int getRenderringLevel() {
		return this.renderringLevel;
	}

	public void setRenderringLevel(int newLevel) {
		if (renderringLevel <= 0) {
			throw new IllegalArgumentException("renderringLevel = "
					+ renderringLevel);
		}

		int oldLevel = this.renderringLevel;
		this.renderringLevel = newLevel;
		propertyChangeSupport.firePropertyChange(LEVEL, new Integer(oldLevel),
				new Integer(newLevel));
	}

	public double getScale() {
		return this.scale;
	}

	public void setScale(double newScale) {
		if (scale <= 0) {
			throw new IllegalArgumentException("scale = " + scale);
		}

		double oldScale = this.scale;
		this.scale = newScale;
		propertyChangeSupport.firePropertyChange(SCALE, new Double(oldScale),
				new Double(newScale));
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		Font oldFont = this.font;
		Font newFont = font;
		this.font = newFont;
		propertyChangeSupport.firePropertyChange(CHANGE_FONT, oldFont, newFont);
	}

	public boolean isHideErrorCheck() {
		return this.hideErrorCheck;
	}

	public void setHideErrorCheck(boolean newHideErrorCheck) {
		boolean oldHideErrorCheck = this.hideErrorCheck;
		this.hideErrorCheck = newHideErrorCheck;
		propertyChangeSupport.firePropertyChange(HIDE_ERROR_CHECK, new Boolean(
				oldHideErrorCheck), new Boolean(newHideErrorCheck));
	}

	public boolean isHideHeader() {
		return this.hideHeader;
	}

	public void setHideHeader(boolean newHideHeader) {
		boolean oldHideHeader = this.hideHeader;
		this.hideHeader = newHideHeader;
		propertyChangeSupport.firePropertyChange(HIDE_HEADER, new Boolean(
				oldHideHeader), new Boolean(newHideHeader));
	}

	public boolean isHideLineNumber() {
		return this.hideLineNumber;
	}

	public void setHideLineNumber(boolean newHideLineNumber) {
		boolean oldHideLineNumber = this.hideLineNumber;
		this.hideLineNumber = newHideLineNumber;
		propertyChangeSupport.firePropertyChange(HIDE_LINE_NUMBER, new Boolean(
				oldHideLineNumber), new Boolean(newHideLineNumber));
	}

	/***************************************************************************
	 * イベント関連
	 **************************************************************************/

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void fireInitializeEvents() {
		propertyChangeSupport.firePropertyChange(INITIALIZE, null, null);
	}

}