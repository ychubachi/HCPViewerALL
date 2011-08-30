/*
 * CFileOutputStream.java
 * Created on 2010/02/15 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * CFileOutputStream 例外を拾わなくていいようにラップするクラス
 */
public class CFileOutputStream extends FileOutputStream {

	/**
	 * @param file
	 * @param append
	 * @throws FileNotFoundException
	 */
	public CFileOutputStream(File file, boolean append)
			throws FileNotFoundException {
		super(file, append);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileOutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int off, int len) {
		try {
			super.write(b, off, len);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileOutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] b) {
		try {
			super.write(b);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileOutputStream#write(int)
	 */
	@Override
	public void write(int b) {
		try {
			super.write(b);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileOutputStream#close()
	 */
	@Override
	public void close() {
		try {
			super.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
