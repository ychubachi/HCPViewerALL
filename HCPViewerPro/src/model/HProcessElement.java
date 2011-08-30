/*
 * @(#)HProcessElement.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;


/**
 * èàóùÇï\åªÇ∑ÇÈÇΩÇﬂÇÃÉNÉâÉX
 * @author Manabu Sugiura
 * @version $Id: HProcessElement.java,v 1.11 2004/12/01 07:29:47 macchan Exp $
 */
public class HProcessElement extends HChartElement {

	/**
	 * @param type
	 */
	public HProcessElement(int type) {
		super(type);
	}

	// ***** FOR PART OF DEBUG *****

	/**
	 * FOR DEBUG
	 */
	public String getAttributes() {
		StringBuffer buffer = new StringBuffer();

		//Type
		buffer.append(" type=\"" + getTypeString() + "\"");

		//Input data
		if (getInputs().size() != 0) {
			buffer.append(" in=\"");
			for (int i = 0; i < getInputs().size(); i++) {
				HDataElement data = (HDataElement) getInputs().get(i);
				buffer.append(data.getText() + "(" + data.hashCode() + ")");
				if (i != getInputs().size() - 1)
					buffer.append(" ,");
			}
		}

		//Output data
		if (getOutputs().size() != 0) {
			buffer.append(" out=\"");
			for (int i = 0; i < getOutputs().size(); i++) {
				HDataElement data = (HDataElement) getOutputs().get(i);
				buffer.append(data.getText() + "(" + data.hashCode() + ")");
				if (i != getOutputs().size() - 1)
					buffer.append(" ,");
			}
		}

		return buffer.toString();
	}

}