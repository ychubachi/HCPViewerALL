/*
 * @(#)HCompiler.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import model.HDocument;
import application.HCPViewer;

import compiler.analyzer.HAnalyzer;
import compiler.scanner.HScanner;
import compiler.scanner.model.HSLineList;

/**
 * コンパイラ
 * 
 * @author Manabu Sugiura
 * @version $Id: HCompiler.java,v 1.14 2009/05/11 05:08:21 macchan Exp $
 */
public class HCompiler {

	private HCompiler() {
		// Do nothing.
	}

	public static HDocument compile(File source) {
		try {
			// Encodingを決める
			String encoding = HCPViewer.detectEncoding(source);

			return compile(new FileInputStream(source), source.getName(),
					encoding);
		} catch (IOException ex) {
			throw new HCompileException("ソースファイルが見つかりません", ex);
		}
	}

	public static HDocument compile(InputStream inputStream, String sourceName,
			String encoding) {
		// メッセージの出力先を初期化
		initializeCompileLogger(sourceName);

		// 字句解析
		HScanner scanner = new HScanner();
		HSLineList scanedLines = scanner.scan(inputStream, encoding);

		// 構文解析
		HAnalyzer analyzer = new HAnalyzer();
		HDocument document = analyzer.analyze(scanedLines);

		// ファイル名を設定します．
		document.setFilename(sourceName);

		// 結果を通知
		HCompileLogger.getInstance().printTotal();

		return document;
	}

	private static void initializeCompileLogger(String sourceName) {
		HCompileLogger logger = HCompileLogger.getInstance();
		logger.setStream(HCompileOptions.getInstance().getStream());
		logger.setSourceName(sourceName);
	}

}