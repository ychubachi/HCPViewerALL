package application.gui.parts;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import application.gui.HViewerFrame;

/**
 * Class HDebugConsole.
 * 
 * @author Manabu Sugiura
 * @version $Id: HDebugConsole.java,v 1.3 2009/09/10 03:48:32 macchan Exp $
 *          Copyright(c) 2004, Manabu Sugiura. All rights reserved.
 */
public class HDebugConsole extends JPanel {

	public static final long serialVersionUID = 1L;

	public static Dimension SIZE = new Dimension(
			HViewerFrame.WINDOW_SIZE.width, 100);

	private JScrollPane scrollPane;
	private JTextArea area;
	private HTextAreaOutputStream newos;

	/***********************
	 * Constractor.
	 ***********************/

	public HDebugConsole() {
		setPreferredSize(SIZE);
		setLayout(new BorderLayout());

		initializeComponents();
	}

	/***********************
	 * Initialize.
	 ***********************/

	private void initializeComponents() {
		area = new JTextArea();
		area.setEditable(false);
		area.setTabSize(0);

		scrollPane = new JScrollPane(area);
		add(scrollPane, BorderLayout.CENTER);

		newos = new HTextAreaOutputStream(area);
	}

	/***********************
	 * Text Area.
	 ***********************/

	public void clearText() {
		String text = area.getText();
		area.replaceRange("", 0, text.length());
	}

	public HTextAreaOutputStream getStream() {
		return newos;
	}

}