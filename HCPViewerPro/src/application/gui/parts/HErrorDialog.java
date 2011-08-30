/*
 * HErrorDialog.java
 * Copyright (c) 2002 Boxed-Economy Project.  All rights reserved.
 */
package application.gui.parts;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * エラーダイアログを出現させるクラス
 * 例外のStackTrace表示機能を持ちます
 * @author macchan
 * @version $Id: HErrorDialog.java,v 1.2 2009/09/10 03:48:32 macchan Exp $
 */
public class HErrorDialog extends JPanel {

	public static final long serialVersionUID = 1L;
	
	/*******************************
	 * static
	 *******************************/

	public static final String TITLE = "エラー";
	public static final String DESCRIPTION = "詳細";
	public static final int STACKTRACEPANEL_WIDTH = 720;
	public static final int STACKTRACEPANEL_HEIGHT = 500;;

	public static void show(Frame owner, String message) {
		JOptionPane.showMessageDialog(owner, message, TITLE,
				JOptionPane.ERROR_MESSAGE);
	}

	public static void show(Frame owner, String message, Throwable throwable) {
		JOptionPane.showMessageDialog(owner, new HErrorDialog(owner, message,
				throwable), TITLE, JOptionPane.ERROR_MESSAGE);
	}

	/*******************************
	 * Field
	 *******************************/

	private Frame owner = null;
	private String message = null;
	private Throwable throwable = null;

	/*******************************
	 * Contructor
	 *******************************/

	private HErrorDialog(Frame owner, String message, Throwable throwable) {
		this.owner = owner;
		this.message = message;
		this.throwable = throwable;

		this.initializeComponents();
	}

	/*******************************
	 * 初期化
	 *******************************/

	private void initializeComponents() {
		//This
		this.setLayout(new BorderLayout());

		//Label
		JLabel label = new JLabel(message);
		this.add(label, BorderLayout.NORTH);

		//Button(Panel)
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.EAST);

		JButton button = this.createDescriptionButton();
		panel.add(button, BorderLayout.SOUTH);

	}

	private JButton createDescriptionButton() {
		JButton button = new JButton(DESCRIPTION);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showStackTraceDialog();
			}
		});
		return button;
	}

	/*******************************
	 * 例外パネル関連
	 *******************************/

	private void showStackTraceDialog() {
		JOptionPane.showMessageDialog(this.owner, this.createStackTracePanel(),
				DESCRIPTION, JOptionPane.PLAIN_MESSAGE);
	}

	private JPanel createStackTracePanel() {
		//パネル生成		
		JPanel panel = new JPanel();

		//テキストエリア生成
		final JTextArea textArea = this.createStackTraceTextArea();

		//スクロールペイン生成				
		final JScrollPane scroller = new JScrollPane(textArea);
		scroller.setPreferredSize(new Dimension(STACKTRACEPANEL_WIDTH,
				STACKTRACEPANEL_HEIGHT));
		panel.add(scroller, null);

		//スクロール状態を一番左上にする(InvokeLaterでないと,Dialog出現時に再設定されてしまう)
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scroller.getViewport().setViewPosition(new Point(0, 0));
			}
		});
		return panel;
	}

	private JTextArea createStackTraceTextArea() {
		//StackTraceをByteArrayに一旦吐き出す
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		this.throwable.printStackTrace(printStream);

		//吐き出された文字を初期値とするTextArea生成
		JTextArea textArea = new JTextArea();
		textArea.setText(outputStream.toString());
		textArea.setEditable(false);
		return textArea;
	}
}