/*
 * CJavaCompiler.java
 * Created on 2011/06/03
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.compiler;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import clib.common.execution.CCommandExecuter;
import clib.common.filesystem.CDirectory;
import clib.common.filesystem.CFile;
import clib.common.filesystem.CFileSystem;
import clib.common.filesystem.CPath;
import clib.common.system.CEncoding;

/**
 * @author macchan
 */
public class CJavaCompiler implements ICCompiler {

	private CDirectory base;
	private CDirectory sourceDir;
	private CDirectory destDir;
	private CEncoding encoding;

	// private List<String> classpaths = new ArrayList<String>();
	private CClasspathManager classpathManager;
	private List<CPath> targetPaths = new ArrayList<CPath>();

	public CJavaCompiler(CDirectory base) {
		this.base = base;
		sourceDir = base;
		destDir = base;
		classpathManager = new CClasspathManager(base);
	}

	public void setSourcepath(CPath path) {
		if (path.isRelative()) {
			path = base.getAbsolutePath().appendedPath(path);
		}
		this.sourceDir = CFileSystem.findDirectory(path);
	}

	public void setDestpath(CPath path) {
		if (path.isRelative()) {
			path = base.getAbsolutePath().appendedPath(path);
		}
		this.destDir = CFileSystem.findDirectory(path);
	}

	public void setEncoding(CEncoding encoding) {
		this.encoding = encoding;
	}

	public void addSource(CPath relativePath) {
		if (!relativePath.isRelative()) {
			throw new RuntimeException();
		}
		targetPaths.add(relativePath);
	}

	public void clearSource() {
		targetPaths.clear();
	}

	public void addClasspath(CPath path) {
		classpathManager.addClasspath(path);
	}

	public void addClasspathDir(CPath path) {
		classpathManager.addClasspathDir(path);
	}

	private Iterable<? extends JavaFileObject> getJavaTargetFiles(
			StandardJavaFileManager fileManager) {
		List<File> targetFiles = new ArrayList<File>();
		if (targetPaths.size() > 0) {
			for (CPath path : targetPaths) {
				targetFiles.add(sourceDir.findOrCreateFile(path)
						.getAbsolutePath().toJavaFile());
			}
		} else { // wild card.
			targetFiles.addAll(getAllJavaSources(sourceDir));
		}
		return fileManager.getJavaFileObjectsFromFiles(targetFiles);
	}

	private List<File> getAllJavaSources(CDirectory dir) {
		List<File> files = new ArrayList<File>();
		for (CDirectory child : dir.getDirectoryChildren()) {
			files.addAll(getAllJavaSources(child));
		}
		for (CFile child : dir.getFileChildren()) {
			if (child.getName().getExtension().equals("java")) {
				files.add(child.toJavaFile());
			}
		}
		return files;
	}

	private List<String> getParameters() {
		List<String> params = new ArrayList<String>();
		params.add("-d");
		params.add(destDir.getAbsolutePath().toString());
		params.add("-cp");
		params.add(classpathManager.createClassPathString(destDir
				.getAbsolutePath()));
		params.add("-sourcepath");
		params.add(sourceDir.getAbsolutePath().toString());
		if (encoding != null) {
			params.add("-encoding");
			params.add(encoding.toString());
		}
		return params;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see clib.compiler.ICCompiler#doCompile()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public CCompileResult doCompile() {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		// JDK, JRE問題
		if (compiler == null) {
			return doCompileByJavac();
			// throw new
			// RuntimeException("Compiler is null. Use JDK java instead of JRE java");
		}

		DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				collector, null, null);
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
				collector, getParameters(), null,
				getJavaTargetFiles(fileManager));
		boolean success = task.call();
		List<Diagnostic> diagnostics = (List) collector.getDiagnostics();
		List<CDiagnostic> cDiagnostics = new ArrayList<CDiagnostic>();
		for (Diagnostic diagnostic : diagnostics) {
			cDiagnostics.add(new CDiagnostic(diagnostic));
		}
		CCompileResult result = new CCompileResult(success, targetPaths,
				cDiagnostics);
		return result;
	}

	private CCompileResult doCompileByJavac() {
		try {
			String enc = CEncoding.UTF8.toString();
			CCommandExecuter executer = new CCommandExecuter();

			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			PrintStream err = new PrintStream(byteArray, true, enc);
			executer.setErr(err);
			executer.setVerbose(true);
			List<String> commands = getParameters();
			commands.add(0, "javac");
			List<String> filenames = new ArrayList<String>();
			for (File file : getAllJavaSources(sourceDir)) {
				filenames.add(file.getAbsolutePath());
				// なぜか，これでは動かない
				// filenames.add(new CPath(file.getAbsolutePath())
				// .getRelativePath(sourceDir.getAbsolutePath())
				// .toString());
			}
			commands.addAll(filenames);
			executer.executeCommandAndWait(commands);

			// 解析
			String messages = byteArray.toString(enc);
			// System.out.println(messages);
			boolean success = messages.length() <= 0;

			List<CDiagnostic> diagnostics;
			if (success) {
				diagnostics = new ArrayList<CDiagnostic>();
			} else {
				diagnostics = parseJavaError(messages);
			}
			CCompileResult result = new CCompileResult(success, targetPaths,
					diagnostics);
			return result;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private List<CDiagnostic> parseJavaError(String error) {
		String[] lines = error.split("\r?\n");
		List<String> linesList = new ArrayList<String>();
		for (int i = 0; i < lines.length; i++) {
			linesList.add(lines[i]);
		}
		return parseJavaError(linesList);
	}

	private List<CDiagnostic> parseJavaError(List<String> lines) {
		lines.remove(lines.size() - 1);// the last line
		List<List<String>> messages = new ArrayList<List<String>>();
		List<String> message = null;
		for (String line : lines) {
			if (line.indexOf(".java:") != -1) {
				if (message != null) {
					messages.add(message);
				}
				message = new ArrayList<String>();
			}

			if (message == null) {
				throw new RuntimeException();
			}
			message.add(line);
		}
		if (message != null) {
			messages.add(message);
		}
		return parseJavaErrorMessages(messages);
	}

	private List<CDiagnostic> parseJavaErrorMessages(List<List<String>> messages) {
		List<CDiagnostic> diagnostics = new ArrayList<CDiagnostic>();
		for (List<String> message : messages) {
			// System.out.println(message);
			CDiagnostic diagnostic = parseJavaErrorMessage(message);
			diagnostics.add(diagnostic);
		}
		return diagnostics;
	}

	private CDiagnostic parseJavaErrorMessage(List<String> messages) {
		CDiagnostic diag = new CDiagnostic();
		String title = messages.get(0);
		String[] a = title.split(": ");
		String[] b = a[0].split(":");
		int line = Integer.parseInt(b[b.length - 1]);
		String filename = new CPath(b[b.length - 2]).getName().toString();
		String content = a[1];
		diag.setLineNumber(line);
		diag.setSourceName(filename);
		diag.setMessage(content);
		for (String message : messages) {
			if (message.matches("[\\s]*[\\^]")) {
				diag.setPosition(countSpace(message));
			}
		}
		return diag;
	}

	private int countSpace(String line) {
		char[] array = line.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if (array[i] != ' ') {
				return i;
			}
		}
		return -1;
	}
}
