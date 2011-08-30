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
 * �G���[�_�C�A���O���o��������N���X
 * ��O��StackTrace�\���@�\�������܂�
 * @author macchan
 * @version $Id: HErrorDialog.java,v 1.2 2009/09/10 03:48:32 macchan Exp $
 */
public class HErrorDialog extends JPanel {

	public static final long serialVersionUID = 1L;
	
	/*******************************
	 * static
	 *******************************/

	public static final String TITLE = "�G���[";
	public static final String DESCRIPTION = "�ڍ�";
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
	 * ������
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
	 * ��O�p�l���֘A
	 *******************************/

	private void showStackTraceDialog() {
		JOptionPane.showMessageDialog(this.owner, this.createStackTracePanel(),
				DESCRIPTION, JOptionPane.PLAIN_MESSAGE);
	}

	private JPanel createStackTracePanel() {
		//�p�l������		
		JPanel panel = new JPanel();

		//�e�L�X�g�G���A����
		final JTextArea textArea = this.createStackTraceTextArea();

		//�X�N���[���y�C������				
		final JScrollPane scroller = new JScrollPane(textArea);
		scroller.setPreferredSize(new Dimension(STACKTRACEPANEL_WIDTH,
				STACKTRACEPANEL_HEIGHT));
		panel.add(scroller, null);

		//�X�N���[����Ԃ���ԍ���ɂ���(InvokeLater�łȂ���,Dialog�o�����ɍĐݒ肳��Ă��܂�)
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scroller.getViewport().setViewPosition(new Point(0, 0));
			}
		});
		return panel;
	}

	private JTextArea createStackTraceTextArea() {
		//StackTrace��ByteArray�Ɉ�U�f���o��
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		this.throwable.printStackTrace(printStream);

		//�f���o���ꂽ�����������l�Ƃ���TextArea����
		JTextArea textArea = new JTextArea();
		textArea.setText(outputStream.toString());
		textArea.setEditable(false);
		return textArea;
	}
}