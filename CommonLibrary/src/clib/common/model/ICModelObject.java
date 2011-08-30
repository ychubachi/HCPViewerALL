/*
 * ICModelObject.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.model;

public interface ICModelObject {

	public void addModelListener(ICModelListener l);

	public void removeModelListener(ICModelListener l);
}
