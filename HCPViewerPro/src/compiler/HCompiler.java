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
 * �R���p�C��
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
			// Encoding�����߂�
			String encoding = HCPViewer.detectEncoding(source);

			return compile(new FileInputStream(source), source.getName(),
					encoding);
		} catch (IOException ex) {
			throw new HCompileException("�\�[�X�t�@�C����������܂���", ex);
		}
	}

	public static HDocument compile(InputStream inputStream, String sourceName,
			String encoding) {
		// ���b�Z�[�W�̏o�͐��������
		initializeCompileLogger(sourceName);

		// ������
		HScanner scanner = new HScanner();
		HSLineList scanedLines = scanner.scan(inputStream, encoding);

		// �\�����
		HAnalyzer analyzer = new HAnalyzer();
		HDocument document = analyzer.analyze(scanedLines);

		// �t�@�C������ݒ肵�܂��D
		document.setFilename(sourceName);

		// ���ʂ�ʒm
		HCompileLogger.getInstance().printTotal();

		return document;
	}

	private static void initializeCompileLogger(String sourceName) {
		HCompileLogger logger = HCompileLogger.getInstance();
		logger.setStream(HCompileOptions.getInstance().getStream());
		logger.setSourceName(sourceName);
	}

}