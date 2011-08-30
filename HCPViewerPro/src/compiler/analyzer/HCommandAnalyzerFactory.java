/*
 * @(#)HCommandAnalyzerFactory.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer;

import java.util.HashMap;

import compiler.HCompileLogger;
import compiler.analyzer.components.process.HBasisCommandAnalyzer;
import compiler.scanner.model.HSCommand;

/**
 * �A�i���C�U�[�t�@�N�g��
 * 
 * @author Manabu Sugiura
 * @version $Id: HCommandAnalyzerFactory.java,v 1.1 2004/11/25 05:00:46 gackt
 *          Exp $
 */
public class HCommandAnalyzerFactory {

	/***************************************************************************
	 * �N���X�ϐ�
	 **************************************************************************/

	private static final HCompileLogger logger = HCompileLogger.getInstance();

	private static final HALineCommandAnalyzer DEFAULT_ANALYZER = new HBasisCommandAnalyzer();

	private static HCommandAnalyzerFactory instance;
	private static HashMap<String, HCommandAnalyzer> analyzers = new HashMap<String, HCommandAnalyzer>();// �L���b�V���p

	/**
	 * �R���X�g���N�^
	 */
	private HCommandAnalyzerFactory() {
		// Do noting.
	}

	public static HCommandAnalyzerFactory getInstance() {
		if (instance == null) {
			instance = new HCommandAnalyzerFactory();
		}
		return instance;
	}

	/***************************************************************************
	 * �擾�֘A
	 **************************************************************************/

	public HALineCommandAnalyzer getLineCommandAnalyzer(HSCommand command) {
		try {
			try {
				HCommandTable table = HCommandTable.getInstance();
				String className = table.getAnalyzerClassName(command);
				HCommandAnalyzer analyzer = getCommandAnalyzer(className);
				return (HALineCommandAnalyzer) analyzer;
			} catch (ClassCastException e) {
				showIllegalCommandPositionMessage(command);
				return getDefaultLineCommandAnalyzer();
			} catch (Exception ex) {
				// @TODO ������null��O���o�Ă��鎞������H
				// �傫����O�����͂��̉��}���u
				showNoDeclarationCommandMessage(command);
				return getDefaultLineCommandAnalyzer();
			}
		} catch (Exception ex) {
			logger.showErrorMessage(ex.getMessage());
			return getDefaultLineCommandAnalyzer();
		}
	}

	public HAExtendCommandAnalyzer getExtendCommandAnalyzer(HSCommand command) {
		try {
			HCommandTable table = HCommandTable.getInstance();
			String className = table.getAnalyzerClassName(command);
			HCommandAnalyzer analyzer = getCommandAnalyzer(className);
			return (HAExtendCommandAnalyzer) analyzer;
		} catch (ClassCastException e) {
			showIllegalCommandPositionMessage(command);
			return null;
		} catch (Exception e) {
			showNoDeclarationCommandMessage(command);
			return null;
		}
	}

	private HCommandAnalyzer getCommandAnalyzer(String className)
			throws Exception {
		// �L���b�V������A�i���C�U�[���擾����
		if (isCached(className)) {
			return getAnalyzerFromCashe(className);
		}

		// �L���b�V���ɂȂ��ꍇ�C�A�i���C�U�[��V�K�쐬���ăL���b�V������
		HCommandAnalyzer analyzer = createAnalyzerInstance(className);
		addCache(className, analyzer);
		return analyzer;
	}

	private HALineCommandAnalyzer getDefaultLineCommandAnalyzer() {
		return DEFAULT_ANALYZER;
	}

	/***************************************************************************
	 * ���� & �L���b�V��
	 **************************************************************************/

	private HCommandAnalyzer createAnalyzerInstance(String className)
			throws Exception {
		Class<?> clazz = Class.forName(className);
		return (HCommandAnalyzer) clazz.newInstance();
	}

	private void addCache(String className, HCommandAnalyzer analyzer) {
		analyzers.put(className, analyzer);
	}

	private HCommandAnalyzer getAnalyzerFromCashe(String key) {
		return (HCommandAnalyzer) analyzers.get(key);
	}

	private boolean isCached(String key) {
		return analyzers.containsKey(key);
	}

	/***************************************************************************
	 * �G���[����
	 **************************************************************************/

	private void showNoDeclarationCommandMessage(HSCommand command) {
		String message = "�R�}���h�m" + command.getContents() + "�n�͒�`����Ă��܂���";
		logger.showWarningMessage(message, command.getParent().getLineNumber());
	}

	private void showIllegalCommandPositionMessage(HSCommand command) {
		String message = "�R�}���h�m" + command.getContents() + "�n�̈ʒu���s���ł�";
		logger.showWarningMessage(message, command.getParent().getLineNumber());
	}

}