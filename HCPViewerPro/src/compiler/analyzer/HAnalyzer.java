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
 * �A�i���C�U�[
 * 
 * @author Manabu Sugiura
 * @version $Id: HAnalyzer.java,v 1.22 2009/09/10 03:48:32 macchan Exp $
 */
public class HAnalyzer {

	/** �Q�Ƃ������ł��Ȃ����W���[�����iLatex�����j */
	private static final String UNRESOLVED_REFERENCE_MODULE_MARK = "*UNRESOLVED REFERENCE *??";

	/**
	 * �R���X�g���N�^
	 */
	public HAnalyzer() {
		super();
	}

	/***************************************************************************
	 * �\�����
	 **************************************************************************/

	public HDocument analyze(HSLineList lines) {
		HDocument document = new HDocument();
		analyzeAllModules(lines, document);
		resolveReference(document);
		return document;
	}

	private void analyzeAllModules(HSLineList lines, HDocument document) {
		// ���W���[�����Ƃɕ������܂�
		List<HSLineList> modules = divideIntoModule(lines);

		// ���W���[������͂��܂�
		HModuleAnalyzer analyzer = new HModuleAnalyzer();
		for (Iterator<HSLineList> i = modules.iterator(); i.hasNext();) {
			// ��̃��W���[������͂��܂�
			HSLineList module = (HSLineList) i.next();
			if (!module.isEmpty()) {
				analyzer.analyze(module, document);
			}
		}
	}

	private void resolveReference(HDocument document) {
		List<HProcessElement> allProcessList = document.getAllProcessElements();

		// �Q�Ƃ���������
		for (Iterator<HProcessElement> i = allProcessList.iterator(); i.hasNext();) {
			HProcessElement process = (HProcessElement) i.next();
			if (process.getType() == HChartElement.CALL_MODULE) {
				// ���W���[�������擾����
				String moduleId = process.getText();

				// ���W���[�������d�����Ă��Ȃ����m���߂�
				if (document.hasDuplicateModuleId()) {
					showDeplicateModuleIdMessage(moduleId);
					process.setText(UNRESOLVED_REFERENCE_MODULE_MARK);
					return;
				}

				// �v���Z�X�̃e�L�X�g�����W���[�����ɒu������
				HModule module = document.getModule(moduleId);
				if (module != null) {// ��������
					// process.setText(module.getText());
					// �{���͂u�������ł��ׂ������C��
					if (!module.getText().equals(module.getId())) {
						process.setText(module.getText() + "<" + module.getId()
								+ ">");
					} else {
						process.setText(module.getText());
					}
				} else {// ������Ȃ�
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
	 * �G���[����
	 **************************************************************************/

	// private void showUnresolvedReferenceModuleMessage(String moduleId) {
	// HCompileLogger logger = HCompileLogger.getInstance();
	// logger.showWarningMessage("ID���m" + moduleId + "�n�̃��W���[���͒�`����Ă��܂���");
	// }
	
	private void showDeplicateModuleIdMessage(String moduleId) {
		HCompileLogger logger = HCompileLogger.getInstance();
		logger.showWarningMessage("ID���m" + moduleId + "�n�̃��W���[�����d�����Đ錾����Ă��܂�");
	}

}