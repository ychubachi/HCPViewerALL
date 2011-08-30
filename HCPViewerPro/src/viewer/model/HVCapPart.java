/*
 * @(#)HVCapPart.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model;

import model.HEnvironment;

/**
 * @author Manabu Sugiura
 * @version $Id: HVCapPart.java,v 1.3 2004/11/25 07:33:29 macchan Exp $
 */
public abstract class HVCapPart extends HVPart {

	private HEnvironment model;

	/**
	 * コンストラクタ
	 */
	public HVCapPart(HEnvironment model) {
		super();
		this.model = model;
	}

	public HEnvironment getModel() {
		return this.model;
	}

}