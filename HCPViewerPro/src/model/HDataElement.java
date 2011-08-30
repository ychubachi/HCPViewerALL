/*
 * @(#)HDataElement.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

/**
 * データを表現するためのクラス
 * @author Manabu Sugiura
 * @version $Id: HDataElement.java,v 1.9 2004/12/03 05:07:36 macchan Exp $
 */
public class HDataElement extends HChartElement {

	/**
	 * @param type
	 */
	public HDataElement(int type) {
		super(type);
	}

	public boolean isSpace() {
		return getType() == HChartElement.SPACE;
	}

	// ***** FOR PART OF DEBUG *****

	/**
	 * FOR DEBUG
	 */
	public String getAttributes() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" type=\"" + getTypeString() + "\"");
		buffer.append(" hash=\"" + hashCode() + "\"");
		return buffer.toString();
	}

}