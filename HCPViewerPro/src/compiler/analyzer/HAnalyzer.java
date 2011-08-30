/*
 * @(#)HAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import model.HChartElement;
import model.HDocument;
import model.HModule;
import model.HProcessElement;

import compiler.HCompileLogger;
import compiler.scanner.model.HSCommand;
import compiler.scanner.model.HSLine;
import compiler.scanner.model.HSLineList;

/**
 * アナライザー
 * 
 * @author Manabu Sugiura
 * @version $Id: HAnalyzer.java,v 1.22 2009/09/10 03:48:32 macchan Exp $
 */
public class HAnalyzer {

	/** 参照が解決できないモジュール名（Latex風味） */
	private static final String UNRESOLVED_REFERENCE_MODULE_MARK = "*UNRESOLVED REFERENCE *??";

	/**
	 * コンストラクタ
	 */
	public HAnalyzer() {
		super();
	}

	/***************************************************************************
	 * 構文解析
	 **************************************************************************/

	public HDocument analyze(HSLineList lines) {
		HDocument document = new HDocument();
		analyzeAllModules(lines, document);
		resolveReference(document);
		return document;
	}

	private void analyzeAllModules(HSLineList lines, HDocument document) {
		// モジュールごとに分割します
		List<HSLineList> modules = divideIntoModule(lines);

		// モジュールを解析します
		HModuleAnalyzer analyzer = new HModuleAnalyzer();
		for (Iterator<HSLineList> i = modules.iterator(); i.hasNext();) {
			// 一つのモジュールを解析します
			HSLineList module = (HSLineList) i.next();
			if (!module.isEmpty()) {
				analyzer.analyze(module, document);
			}
		}
	}

	private void resolveReference(HDocument document) {
		List<HProcessElement> allProcessList = document.getAllProcessElements();

		// 参照を解決する
		for (Iterator<HProcessElement> i = allProcessList.iterator(); i.hasNext();) {
			HProcessElement process = (HProcessElement) i.next();
			if (process.getType() == HChartElement.CALL_MODULE) {
				// モジュール名を取得する
				String moduleId = process.getText();

				// モジュール名が重複していないか確かめる
				if (document.hasDuplicateModuleId()) {
					showDeplicateModuleIdMessage(moduleId);
					process.setText(UNRESOLVED_REFERENCE_MODULE_MARK);
					return;
				}

				// プロセスのテキストをモジュール名に置換する
				HModule module = document.getModule(moduleId);
				if (module != null) {// 見つかった
					// process.setText(module.getText());
					// 本当はＶｉｅｗでやるべきだが，仮
					if (!module.getText().equals(module.getId())) {
						process.setText(module.getText() + "<" + module.getId()
								+ ">");
					} else {
						process.setText(module.getText());
					}
				} else {// 見つからない
					// process.setText(UNRESOLVED_REFERENCE_MODULE_MARK);
					// showUnresolvedReferenceModuleMessage(moduleId);
				}
			}
		}
	}

	private List<HSLineList> divideIntoModule(HSLineList lines) {
		List<HSLineList> modules = new ArrayList<HSLineList>();
		HSLineList currentModule;

		currentModule = new HSLineList();
		modules.add(currentModule);

		for (ListIterator<HSLine> i = lines.listIterator(); i.hasNext();) {
			HSLine line = (HSLine) i.next();
			if (isModuleStartLine(line)) {
				currentModule = new HSLineList();
				modules.add(currentModule);
			}
			currentModule.add(line);
		}
		return modules;
	}

	private boolean isModuleStartLine(HSLine line) {
		HCommandTable config = HCommandTable.getInstance();
		HSCommand firstCommand = line.getFirstCommand();
		if (firstCommand == null) {
			return false;
		}
		return firstCommand.getContents().equals(config.getModuleCommandName());
	}

	/***************************************************************************
	 * エラー処理
	 **************************************************************************/

	// private void showUnresolvedReferenceModuleMessage(String moduleId) {
	// HCompileLogger logger = HCompileLogger.getInstance();
	// logger.showWarningMessage("IDが［" + moduleId + "］のモジュールは定義されていません");
	// }
	
	private void showDeplicateModuleIdMessage(String moduleId) {
		HCompileLogger logger = HCompileLogger.getInstance();
		logger.showWarningMessage("IDが［" + moduleId + "］のモジュールが重複して宣言されています");
	}

}