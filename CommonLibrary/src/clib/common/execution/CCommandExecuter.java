package clib.common.execution;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import clib.common.collections.CArrays;
import clib.common.filesystem.CDirectory;
import clib.common.filesystem.CFileSystem;
import clib.common.model.CAbstractModelObject;
import clib.common.system.CEncoding;

/**
 * CommandExecuter.java
 */
public class CCommandExecuter extends CAbstractModelObject {

	public static final String STARTED = "STARTED";
	public static final String FINISHED = "FINISHED";

	public static void execute(String... commands) {
		execute(null, null, null, null, Arrays.asList(commands));
	}

	public static void execute(List<String> commands) {
		execute(null, null, null, null, commands);
	}

	public static void execute(CDirectory dir, PrintStream out,
			PrintStream err, ICConsoleInputable input, String... args) {
		execute(dir, out, err, input, Arrays.asList(args));
	}

	public static void execute(CDirectory dir, PrintStream out,
			PrintStream err, ICConsoleInputable input, List<String> commands) {
		CCommandExecuter executer = new CCommandExecuter(dir, out, err, input);
		executer.executeCommandAndWait(commands);
	}

	private CDirectory dir = CFileSystem.getExecuteDirectory();
	private PrintStream out = System.out;
	private PrintStream err = System.err;
	private ICConsoleInputable input = new CNullConsoleInput();
	private boolean verbose = false;
	private CEncoding enc = CEncoding.getSystemEncoding();

	private List<Process> processes = new ArrayList<Process>();

	public CCommandExecuter() {
		this(null, null, null, null);
	}

	public CCommandExecuter(CDirectory dir, PrintStream out, PrintStream err,
			ICConsoleInputable input) {
		if (dir != null) {
			this.dir = dir;
		}
		if (out != null) {
			this.out = out;
		}
		if (err != null) {
			this.err = err;
		}
		if (input != null) {
			this.input = input;
		}
	}

	/**
	 * @param dir
	 *            the dir to set
	 */
	public void setDir(CDirectory dir) {
		this.dir = dir;
	}

	/**
	 * @return the dir
	 */
	public CDirectory getDir() {
		return dir;
	}

	/**
	 * @param out
	 *            the out to set
	 */
	public void setOut(PrintStream out) {
		this.out = out;
	}

	/**
	 * @param err
	 *            the err to set
	 */
	public void setErr(PrintStream err) {
		this.err = err;
	}

	/**
	 * @param input
	 *            the input to set
	 */
	public void setInput(ICConsoleInputable input) {
		this.input = input;
	}

	/**
	 * @param enc
	 *            the enc to set
	 */
	public void setEncoding(CEncoding enc) {
		this.enc = enc;
	}

	/**
	 * @param verbose
	 *            the verbose to set
	 */
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public synchronized void killAll() {
		List<Process> copy = new ArrayList<Process>(processes);
		for (Process p : copy) {
			p.destroy();
		}
	}

	public synchronized void executeCommand(String... commands) {
		executeCommand(Arrays.asList(commands));
	}

	public synchronized void executeCommand(final List<String> commands) {
		Thread thread = new Thread() {
			public void run() {
				try {
					executeInternal(commands);
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		};
		thread.start();
	}

	public synchronized void executeCommandAndWait(String... commands) {
		executeCommandAndWait(Arrays.asList(commands));
	}

	public synchronized void executeCommandAndWait(final List<String> commands) {
		try {
			executeInternal(commands);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void executeInternal(List<String> commands) throws Exception {
		String[] commandArray = listToStringArray(commands);
		if (verbose) {
			System.out.println(CArrays.toString(commandArray));
		}
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(commandArray, null, dir.toJavaFile());
		processes.add(p);
		fireModelUpdated(STARTED, p);
		Thread outThread = createPrintStreamThread(p.getInputStream(), out);
		outThread.start();
		Thread errThread = createPrintStreamThread(p.getErrorStream(), err);
		errThread.start();
		input.setOutputStream(p.getOutputStream());
		p.waitFor();
		processes.remove(p);
		fireModelUpdated(FINISHED, p);
	}

	public static String createCommandString(List<String> list) {
		String commands = "";
		Iterator<String> i = list.iterator();
		while (i.hasNext()) {
			commands += i.next();
			if (i.hasNext()) {
				commands += " ";
			}
		}
		return commands;
	}

	private static String[] listToStringArray(List<String> list) {
		String[] array = new String[list.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

	private Thread createPrintStreamThread(final InputStream in,
			final PrintStream out) {
		return new Thread() {
			public void run() {
				try {
					InputStreamReader reader = new InputStreamReader(in,
							enc.toString());
					char[] buf = new char[1024];
					int n;
					while ((n = reader.read(buf)) > 0) {
						char[] text = new char[n];
						System.arraycopy(buf, 0, text, 0, text.length);
						out.print(text);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		};
	}

	/**
	 * @return the processes
	 */
	public List<Process> getProcesses() {
		return processes;
	}

	// test
	public static void main(String[] args) {
		System.out.println("CCommandExecuter Testing");

		CDirectory bindir = CFileSystem.findDirectory("bin");
		String java = "java";
		String name = "clib.common.execution.TestProgram";

		System.out.println("Prepare Environment");
		JFrame frame = new JFrame("Test Case 2");
		frame.setBounds(100, 100, 200, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		final CConsoleTextPane console = new CConsoleTextPane();
		frame.getContentPane().add(new JScrollPane(console));
		frame.setVisible(true);

		CDefaultConsoleInput input = new CDefaultConsoleInput();
		input.start();

		System.out.println("Test Case 1");
		CCommandExecuter.execute(bindir, null, null, null, java, name);

		System.out.println("Test Case 2");
		CCommandExecuter.execute(bindir, console.out, console.err, null, java,
				name);

		System.out.println("Test Case 3");
		CCommandExecuter.execute(bindir, System.out, System.err, input, java,
				name, "request input");

		System.out.println("Test Case 4");
		CCommandExecuter.execute(CFileSystem.findDirectory("bin"), console.out,
				console.err, new ICConsoleInputable() {
					public void setOutputStream(OutputStream stream) {
						console.consoleToStream = new PrintStream(stream);
						console.in.refresh();
					}
				}, java, name, "request input");

		input.stop();
		System.out.println("CCommandExecuter Testing End");
	}
}
