/*
 * CModifiedFileListupper.java
 * Created on Jun 27, 2010 
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.app;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import clib.common.filesystem.CDirectory;
import clib.common.filesystem.CFile;
import clib.common.filesystem.CFileFilter;
import clib.common.filesystem.CPath;
import clib.common.filesystem.CRecursiveExplorer;
import clib.common.filesystem.ICRecursiveExplorerHandler;
import clib.common.io.CStreamReader;

public class CModifiedFileListupper {

	public static final String DELIMITER = "\t";

	private CDirectory baseDirectory = null;
	private CFile logfile = null;

	private List<CFile> targets = null;
	private Map<String, Long> list = null;
	private CRecursiveExplorer explorer;

	public CModifiedFileListupper(CDirectory baseDirectory, CFile logfile,
			String... fileExtensions) {
		this.baseDirectory = baseDirectory;
		this.logfile = logfile;
		explorer = new CRecursiveExplorer(new ICRecursiveExplorerHandler() {
			public void processFile(CFile file) throws Exception {
				String path = file.getRelativePath(
						CModifiedFileListupper.this.baseDirectory).toString();
				long date = file.getLastModified();
				if (!list.containsKey(path) || list.get(path) != date) {
					list.put(path, date);// 更新
					targets.add(CModifiedFileListupper.this.baseDirectory
							.findFile(new CPath(path)));
				}
			}

			public void processDir(CDirectory dir) throws Exception {
			}
		}) {
		};

		// Filterの設定
		explorer.setDirFilter(CFileFilter.IGNORE_BY_NAME_FILTER("cvs", ".svn"));
		explorer.setFileFilter(CFileFilter
				.ACCEPT_BY_EXTENSION_FILTER(fileExtensions));
	}

	public List<CFile> listup() {
		// initialize
		targets = new ArrayList<CFile>();
		list = new LinkedHashMap<String, Long>();

		// do it
		load();
		explorer.explore(baseDirectory);
		return targets;
	}

	private void load() {
		for (CStreamReader reader = logfile.openReader(); reader.hasMoreLine(); reader
				.toNextLine()) {
			String line = reader.getCurrentLine();
			String[] tokens = line.split(DELIMITER);
			list.put(tokens[0], Long.parseLong(tokens[1]));
		}
	}

	public void save() {
		PrintStream out = new PrintStream(logfile.openOutputStream());
		for (String path : list.keySet()) {
			long modified = list.get(path);
			out.println(path + DELIMITER + modified + DELIMITER + "/*"
					+ new Date(modified) + "*/");
		}
	}

	public void cleanup() {
		logfile.delete();
	}

	public CRecursiveExplorer getExplorer() {
		return this.explorer;
	}
}
