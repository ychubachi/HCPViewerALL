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
 * アナライザーファクトリ
 * 
 * @author Manabu Sugiura
 * @version $Id: HCommandAnalyzerFactory.java,v 1.1 2004/11/25 05:00:46 gackt
 *          Exp $
 */
public class HCommandAnalyzerFactory {

	/***************************************************************************
	 * クラス変数
	 **************************************************************************/

	private static final HCompileLogger logger = HCompileLogger.getInstance();

	private static final HALineCommandAnalyzer DEFAULT_ANALYZER = new HBasisCommandAnalyzer();

	private static HCommandAnalyzerFactory instance;
	private static HashMap<String, HCommandAnalyzer> analyzers = new HashMap<String, HCommandAnalyzer>();// キャッシュ用

	/**
	 * コンストラクタ
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
	 * 取得関連
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
				// @TODO ここでnull例外が出ている時があり？
				// 大きい例外処理はその応急処置
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
		// キャッシュからアナライザーを取得する
		if (isCached(className)) {
			return getAnalyzerFromCashe(className);
		}

		// キャッシュにない場合，アナライザーを新規作成してキャッシュする
		HCommandAnalyzer analyzer = createAnalyzerInstance(className);
		addCache(className, analyzer);
		return analyzer;
	}

	private HALineCommandAnalyzer getDefaultLineCommandAnalyzer() {
		return DEFAULT_ANALYZER;
	}

	/***************************************************************************
	 * 生成 & キャッシュ
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
	 * エラー処理
	 **************************************************************************/

	private void showNoDeclarationCommandMessage(HSCommand command) {
		String message = "コマンド［" + command.getContents() + "］は定義されていません";
		logger.showWarningMessage(message, command.getParent().getLineNumber());
	}

	private void showIllegalCommandPositionMessage(HSCommand command) {
		String message = "コマンド［" + command.getContents() + "］の位置が不正です";
		logger.showWarningMessage(message, command.getParent().getLineNumber());
	}

}