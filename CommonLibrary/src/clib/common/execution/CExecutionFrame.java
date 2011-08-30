/*
 * CExecutionWindow.java
 * Created on 2011/06/22
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.execution;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import clib.common.model.ICModelListener;

/**
 * @author macchan
 */
public class CExecutionFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public enum Mode {
		AUTOCLOSE_ON_FINISH, DIALOG_ON_FINISH, NOTHING_ON_FINISH
	}

	private CConsoleTextPane console = new CConsoleTextPane();
	private CCommandExecuter executer = new CCommandExecuter();

	private Mode mode = Mode.DIALOG_ON_FINISH;

	private boolean closed = false;

	/**
	 * Constructor
	 */
	public CExecutionFrame() {
		super("Execution Frame");// Default;
		initialize();
	}

	private void initialize() {
		setBounds(100, 100, 500, 500);// Default;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// Default;
		getContentPane().add(new JScrollPane(console));
		executer.setOut(console.out);
		executer.setErr(console.err);
		executer.setInput(new ICConsoleInputable() {
			public void setOutputStream(OutputStream stream) {
				console.consoleToStream = new PrintStream(stream);
				console.in.refresh();
			}
		});
		executer.addModelListener(new ICModelListener() {
			public void modelUpdated(Object... args) {
				if (CCommandExecuter.FINISHED.equals(args[0])) {
					if (!closed) {
						processFinished();
					}
				}
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closed = true;
				executer.killAll();
			}
		});

	}

	public CConsoleTextPane getConsole() {
		return console;
	}

	public CCommandExecuter getExecuter() {
		return executer;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	/**
	 * @return the mode
	 */
	public Mode getMode() {
		return mode;
	}

	private void processFinished() {
		switch (mode) {
		case AUTOCLOSE_ON_FINISH:
			dispose();
			break;
		case DIALOG_ON_FINISH:
			int result = JOptionPane.showConfirmDialog(this, "Close Window?",
					"Program Finished", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				dispose();
			}
			break;
		case NOTHING_ON_FINISH:
			break;
		}
	}

	public void open() {
		closed = false;
		setVisible(true);
	}

	public void openAndRun(String... commands) {
		closed = false;
		setVisible(true);
		executer.executeCommand(commands);
	}

}
