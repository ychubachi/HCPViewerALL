/*
 * CFrameTester.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.utils;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;

public class CFrameTester {

	public static JFrame open(Component comp) {
		return open(comp, JFrame.EXIT_ON_CLOSE);
	}

	public static JFrame open(Component comp, int defaultCloseOperation) {
		return open(comp, null, defaultCloseOperation);
	}

	public static JFrame open(Component comp, String title,
			int defaultCloseOperation) {
		JFrame frame = new JFrame(title);
		frame.setLocation(100, 100);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(defaultCloseOperation);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(comp, BorderLayout.CENTER);
		frame.setVisible(true);
		return frame;
	}
}
