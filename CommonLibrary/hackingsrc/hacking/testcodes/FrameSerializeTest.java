/*
 * FrameSerializeTest.java
 * Created on Jul 31, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package hacking.testcodes;

import javax.swing.JFrame;

import clib.common.utils.CCopyUtil;

/**
 * @author macchan
 * 
 */
public class FrameSerializeTest {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Hoge");
		frame.setBounds(100, 100, 200, 200);
		frame.setVisible(true);

		JFrame frame2 = CCopyUtil.copyDeep(frame);
		frame2.setVisible(true);
	}
}
