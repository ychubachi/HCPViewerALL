/*
 * @(#)HScanner.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import compiler.HCompileException;
import compiler.scanner.model.HSElement;
import compiler.scanner.model.HSLine;
import compiler.scanner.model.HSLineList;

/**
 * 字句解析機
 * 
 * @author Manabu Sugiura
 * @version $Id: HScanner.java,v 1.14 2009/09/10 03:48:32 macchan Exp $
 */
public class HScanner {

	public HSLineList scan(InputStream inputStream, String encoding) {

		BufferedReader reader = null;

		// ソースの全行をスキャンする
		try {

			// 読み込む
			reader = new BufferedReader(new InputStreamReader(inputStream,
					encoding));
			HSLineList resultLineList = new HSLineList();
			int lineNumber = 0;
			String lineStr = null;

			while ((lineStr = reader.readLine()) != null) {
				HSLine resultLine = createLine(lineStr, ++lineNumber);
				if (resultLine.isValid()) { // 有効な行なら
					resultLineList.add(resultLine);
				}
			}

			// スキャンの結果を戻す
			return resultLineList;

		} catch (IOException e) {
			throw new HCompileException("ソースファイルの読み取り中にエラーが発生しました", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioe) {
					throw new HCompileException(
							"ソースファイルの読み込みストリームの開放時にエラーが発生しました", ioe);
				}
			}
		}
	}

	private HSLine createLine(String line, int lineNumber) {
		String cleanedLine = replaceSpaceToTab(line); // 1パス目
		List<HSElement> elements = scanLine(cleanedLine); // 2パス目
		return new HSLine(elements, lineNumber);
	}

	private String replaceSpaceToTab(String target) {
		HSpaceConvertedLine scanedLine = new HSpaceConvertedLine(target);
		scanedLine.replaceSpaceToTab();
		return scanedLine.toString();
	}

	private List<HSElement> scanLine(String line) {
		HLineScanner lineScanner = new HLineScanner();
		for (int i = 0; i < line.length(); i++) {
			lineScanner.receive(line.charAt(i));
		}
		lineScanner.receive(HLineScanner.EOL);
		return lineScanner.getElements();
	}

}