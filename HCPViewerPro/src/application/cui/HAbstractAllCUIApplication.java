package application.cui;

import java.io.PrintStream;
import java.util.List;

import application.HCPViewer;
import clib.app.CModifiedFileListupper;
import clib.common.filesystem.CDirectory;
import clib.common.filesystem.CFile;
import clib.common.filesystem.CFileSystem;
import clib.common.filesystem.CPath;

public abstract class HAbstractAllCUIApplication {

	protected void run(String[] args) throws Exception {
		showTitleString(System.out);

		boolean clean = false;
		String logname = "hcp.clog";
		if (args.length == 4 && args[3].equals("clean")) {
			if (args[3].equals("clean")) {
				clean = true;
			}
			logname = args[2];
		} else if (args.length == 3) {
			if (args[2].equals("clean")) {
				clean = true;
			} else {
				logname = args[2];
			}
		} else if (args.length != 2) {
			System.err
					.println("Usage: split [srcdir] [dstdir] (filename)(clean)");
			return;
		}

		CDirectory basedir = CFileSystem.getExecuteDirectory();
		String src = args[0];
		String dst = args[1];
		CDirectory srcDir = basedir.findDirectory(new CPath(src));
		CDirectory dstDir = basedir.findOrCreateDirectory(new CPath(dst));

		convert(srcDir, dstDir, "hcp", basedir.findOrCreateFile(logname), clean);
	}

	protected void showTitleString(PrintStream out) {
		out.println(HCPViewer.APP_NAME + " " + HCPViewer.getVersionString());
		out.println(HCPViewer.DEVELOPERS + " " + HCPViewer.COPYRIGHT);
	}

	protected void convert(CDirectory srcDir, CDirectory dstDir,
			String targetExtension, CFile logfile, boolean clean)
			throws Exception {

		CModifiedFileListupper listupper = new CModifiedFileListupper(srcDir,
				logfile, targetExtension);
		listupper.getExplorer().getDirFilter().add("bin");// CVS, .svn, bin
															// //@todo
		if (clean) {
			listupper.cleanup();
		}
		List<CFile> targets = listupper.listup();

		for (CFile file : targets) {
			System.out.println(file.getAbsolutePath().toString());
			CDirectory filedstDir = dstDir.findOrCreateDirectory(file
					.getRelativePath(srcDir).getParentPath());
			HToSVGApplication.main(new String[] {
					file.getAbsolutePath().toString(),
					filedstDir.getAbsolutePath().toString() });
		}

		listupper.save();
	}

	protected abstract void doProcess(String in, String outdir);

}
