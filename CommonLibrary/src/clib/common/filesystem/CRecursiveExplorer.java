package clib.common.filesystem;

import java.util.List;


public class CRecursiveExplorer {

	private CFileFilter dirFilter = CFileFilter.ALL_ACCEPT_FILTER();
	private CFileFilter fileFilter = CFileFilter.ALL_ACCEPT_FILTER();
	private ICRecursiveExplorerHandler handler;

	public CRecursiveExplorer(ICRecursiveExplorerHandler handler) {
		this.handler = handler;
	}

	public CFileFilter getDirFilter() {
		return dirFilter;
	}

	public void setDirFilter(CFileFilter dirFilter) {
		this.dirFilter = dirFilter;
	}

	public CFileFilter getFileFilter() {
		return fileFilter;
	}

	public void setFileFilter(CFileFilter fileFilter) {
		this.fileFilter = fileFilter;
	}

	public void explore(CDirectory base) {
		exploreDir(base);
	}

	// ディレクトリが削除されても良いようにこの順番にしている
	private void exploreDir(CDirectory dir) {
		List<CFile> files = dir.getFileChildren();
		for (CFile file : files) {
			if (fileFilter.accept(file)) {
				try {
					handler.processFile(file);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		List<CDirectory> subdirs = dir.getDirectoryChildren();
		for (CDirectory subdir : subdirs) {
			if (dirFilter.accept(subdir)) {
				exploreDir(subdir);
				try {
					handler.processDir(subdir);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	// Test
	public static void main(String args[]){
		CRecursiveExplorer explorer = new CRecursiveExplorer(new ICRecursiveExplorerHandler() {
			public void processFile(CFile file) throws Exception {
				System.out.println("file:"+file.getAbsolutePath());				
			}
			public void processDir(CDirectory dir) throws Exception {
				System.out.println("dir:"+dir.getAbsolutePath());
			}
		});
		System.out.println("--- ALL ACCEPT ---");
		explorer.explore(CFileSystem.getExecuteDirectory());
		
		System.out.println("--- IGNORE bin, .svn, ACCEPT java---");
		explorer.setDirFilter(CFileFilter.IGNORE_BY_NAME_FILTER("bin", ".svn"));
		explorer.setFileFilter(CFileFilter.ACCEPT_BY_NAME_FILTER("*.java"));
		explorer.explore(CFileSystem.getExecuteDirectory());
	}
}
