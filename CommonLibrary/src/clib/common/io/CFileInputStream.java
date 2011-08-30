/*
 * CFileInputStream.java
 * Created on 2010/02/15 by macchan
 * Copyright(c) 2010 Yoshiaki Matsuzawa, Shizuoka University
 */
package clib.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * CFileInputStream 例外を拾わなくていいようにラップするクラス
 */
public class CFileInputStream extends FileInputStream {

	/**
	 * @param file
	 * @throws FileNotFoundException
	 */
	public CFileInputStream(File file) throws FileNotFoundException {
		super(file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileInputStream#close()
	 */
	@Override
	public void close() {
		try {
			super.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileInputStream#read()
	 */
	@Override
	public int read() {
		try {
			return super.read();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileInputStream#read(byte[], int, int)
	 */
	@Override
	public int read(byte[] b, int off, int len) {
		try {
			return super.read(b, off, len);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FileInputStream#read(byte[])
	 */
	@Override
	public int read(byte[] b) {
		try {
			return super.read(b);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
