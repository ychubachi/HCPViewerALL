package application.gui.parts;

import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.text.Position;

/**
 * Class HTextAreaOutputStream.
 * @author Manabu Sugiura
 * @version $Id: HTextAreaOutputStream.java,v 1.1 2007/05/30 21:26:10 camei Exp $
 * Copyright(c) 2004, Manabu Sugiura. All rights reserved.
 */
public class HTextAreaOutputStream extends OutputStream {

	private JTextArea target;

	public HTextAreaOutputStream(JTextArea target) {
		this.target = target;
	}

	private void writeTextArea(String contents) {
		target.append(" " + contents);
		autoScroll();
	}

	private void autoScroll() {
		Document document = this.target.getDocument();
		Position endPositon = document.getEndPosition();
		int offset = endPositon.getOffset();
		target.getCaret().setDot(offset);
		target.requestFocus();
	}

	public void write(int b) {
		// Do nothing
	}

	public void write(byte[] data) {
		writeTextArea(new String(data));
	}

	public void write(byte[] data, int offset, int len) {
		writeTextArea(new String(data, offset, len));
	}

}