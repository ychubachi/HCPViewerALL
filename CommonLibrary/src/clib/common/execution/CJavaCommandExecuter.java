/*
 * CJavaCommandExecutor.java
 * Created on 2011/06/22
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.execution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import clib.common.compiler.CClasspathManager;
import clib.common.filesystem.CDirectory;
import clib.common.filesystem.CFileSystem;
import clib.common.filesystem.CPath;
import clib.common.system.CEncoding;

/**
 * @author macchan
 */
public class CJavaCommandExecuter {

	private CCommandExecuter executer;
	private CClasspathManager classpathManager;

	private String className;
	private CPath binDirPath = new CPath(".");
	private List<String> vmOptions = new ArrayList<String>();
	private CEncoding encoding = CEncoding.getVMEncoding();

	public CJavaCommandExecuter() {
		this(CFileSystem.getExecuteDirectory());
	}

	public CJavaCommandExecuter(CDirectory base) {
		this(new CCommandExecuter(), base);
	}

	public CJavaCommandExecuter(CCommandExecuter executer, CDirectory base) {
		this.executer = executer;
		executer.setDir(base);
		this.classpathManager = new CClasspathManager(base);
	}

	public CCommandExecuter getExecuter() {
		return executer;
	}

	public void setBinDirPath(CPath binDirPath) {
		this.binDirPath = binDirPath;
	}

	public void addClasspath(CPath path) {
		classpathManager.addClasspath(path);
	}

	public void addClasspathDir(CPath path) {
		classpathManager.addClasspathDir(path);
	}

	public void addVMOption(String option) {
		vmOptions.add(option);
	}

	public void setMainClass(String fqcn) {
		this.className = fqcn;
	}

	public void execute(String... javaArguments) {
		if (className == null) {
			throw new RuntimeException();
		}
		List<String> args = new ArrayList<String>();
		args.add("java");
		args.addAll(vmOptions);
		args.add("-Dfile.encoding=" + encoding.toString());
		args.add("-cp");
		args.add(classpathManager.createClassPathString(binDirPath));
		args.add(className);
		args.addAll(Arrays.asList(javaArguments));
		// System.out.println(args);
		executer.executeCommand(args);
	}

	public static void main(String[] args) {
		String fqcn = "clib.common.execution.TestProgram";
		CExecutionFrame frame = new CExecutionFrame();
		CJavaCommandExecuter executer = new CJavaCommandExecuter(
				frame.getExecuter(), CFileSystem.findDirectory("bin"));
		executer.setMainClass(fqcn);
		frame.open();
		executer.execute("request input");
	}
}
