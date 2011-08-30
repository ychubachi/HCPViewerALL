/*
 * CIOUtils.java
 * Created on 2007/09/21 by macchan
 * Copyright(c) 2007 CreW Project
 */
package clib.common.io;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * CIOUtils
 */
public class CIOUtils {

	/**
	 * コピーする
	 * 
	 * @param in
	 * @param out
	 */
	public static void copy(InputStream in, OutputStream out) {
		try {
			// コピーする
			byte[] buf = new byte[1024];
			int nByte = 0;
			while ((nByte = in.read(buf)) > 0) {
				out.write(buf, 0, nByte);
			}

			// 後処理
			in.close();
			out.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
