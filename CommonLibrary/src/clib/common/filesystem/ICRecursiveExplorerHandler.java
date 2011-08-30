package clib.common.filesystem;


public interface ICRecursiveExplorerHandler {

	public void processFile(CFile file) throws Exception;

	public void processDir(CDirectory dir) throws Exception;

}
