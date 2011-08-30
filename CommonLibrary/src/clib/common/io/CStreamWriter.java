/*
 * CStreamWriter.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */

package clib.common.io;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import clib.common.system.CEncoding;

/**
 * ファイル書き込みストリームを表現するクラス
 * 
 * @author macchan
 */
public class CStreamWriter {

	private BufferedWriter writer;

	/**
	 * コンストラクタ
	 */
	public CStreamWriter(OutputStream outputStream, CEncoding enc) {
		try {
			initialize(new OutputStreamWriter(outputStream, enc.toString()));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * コンストラクタ
	 */
	public CStreamWriter(Writer writer) {
		initialize(writer);
	}

	private void initialize(Writer writer) {
		try {
			this.writer = new BufferedWriter(writer);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 文字列を書き込む
	 */
	public void write(String s) {
		try {
			if (s == null) {
				writer.write("null");
			}
			writer.write(s);
			writer.flush();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * オブジェクトを書き込む
	 */
	public void write(Object o) {
		try {
			if (o == null) {
				writer.write("null");
			}
			writer.write(o.toString());
			writer.flush();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 改行する
	 */
	public void writeLineFeed() {
		try {
			writer.newLine();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 文字列を書き込んで、改行する
	 */
	public void writeLineFeed(String s) {
		write(s);
		writeLineFeed();
	}

	/**
	 * ストリームを閉じる
	 */
	public void close() {
		try {
			this.writer.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}