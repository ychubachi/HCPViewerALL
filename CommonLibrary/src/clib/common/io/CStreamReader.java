/*
 * CStreamReader.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */

package clib.common.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import clib.common.system.CEncoding;

/**
 * ファイル読み込みストリームを表現するクラス
 * 
 * @author macchan
 */
public class CStreamReader {

	private String currentLine = null;
	private BufferedReader reader = null;

	/**
	 * コンストラクタ
	 */
	public CStreamReader(InputStream inputStream, CEncoding enc) {
		try {
			this.reader = new BufferedReader(new InputStreamReader(inputStream,
					enc.toString()));
			toNextLine();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * ストリームを最後まで読み込んだかどうか調べる
	 */
	public boolean hasMoreLine() {
		return this.currentLine != null;
	}

	/**
	 * 次の行へ進む
	 */
	public void toNextLine() {
		try {
			if (reader.ready()) {
				this.currentLine = reader.readLine();
			} else {
				this.currentLine = null;
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public String getCurrentLine() {
		return this.currentLine;
	}

	/**
	 * ストリームを閉じる
	 */
	public void close() {
		try {
			this.reader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}