/*
 * @(#)HSData.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer.components.extenddata.datascanner;

/**
 * 字句解析したデータを格納するための読み取り専用（不変）
 * @author Manabu Sugiura
 * @version $Id: HSData.java,v 1.2 2004/11/25 05:00:47 gackt Exp $
 */
public class HSData {

	private String text;

	public HSData(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public String toString() {
		return "[HSData:" + this.getText() + "]";
	}

}