/*
 * @(#)HModuleAnalyzer.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package compiler.analyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import model.HDocument;
import model.HElement;
import model.HEnvironment;
import model.HModule;

import compiler.scanner.model.HSCommand;
import compiler.scanner.model.HSElement;
import compiler.scanner.model.HSLine;
import compiler.scanner.model.HSLineList;
import compiler.scanner.model.HSTab;
import compiler.scanner.model.HSText;

/**
 * ���W���[���A�i���C�U�[
 * @author Manabu Sugiura
 * @version $Id: HModuleAnalyzer.java,v 1.25 2009/09/10 03:48:32 macchan Exp $
 */
public class HModuleAnalyzer {

	private PreLineAnalyzer preLineAnalyzer = new PreLineAnalyzer();

	/**
	 * �R���X�g���N�^
	 */
	public HModuleAnalyzer() {
		super();
	}

	/***********************
	 * �\�����
	 ***********************/

	public void analyze(HSLineList source, HDocument document) {
		if (source.isEmpty()) {
			return;
		}

		//���W���[���𐶐�����
		HModule module = new HModule(document);

		//���W���[����������͂���
		analyze(source, module.getEnvironment());

		//���W���[����ǉ�����
		if (module.hasChildElement()) {
			document.addModule(module);
		}
	}

	private void analyze(HSLineList lines, HEnvironment env) {
		//���ׂĂ̍s����͂���
		for (ListIterator<HSLine> i = lines.listIterator(); i.hasNext();) {
			//��s��͂��āi�q�v���Z�X�Ƃ��āj�ǉ�����
			HSLine line = (HSLine) i.next();
			HElement child = analyzeLine(line, env);
			if (child != null) {
				env.addChild(child);
			} else {
				analyze(lines.subList(1, lines.size()), env);
				return;
			}

			//���̓������x���̍s���o������܂ł̃T�u�v���Z�X���X�g�����
			int tabCount = line.getTabCount();
			HSLineList subLines = new HSLineList();
			while (i.hasNext()) {
				HSLine subLine = (HSLine) i.next();
				if (subLine.getTabCount() > tabCount) {
					subLines.add(subLine);
				} else {
					i.previous();
					break;
				}
			}
			//�T�u�v���Z�X���X�g����͂���
			if (!subLines.isEmpty()) {
				analyze(subLines, child.getEnvironment()); //�ċA
			}
		}
	}

	private HElement analyzeLine(HSLine line, HEnvironment env) {
		//�O����(�s�̓��e��Command, Argument�ɕ���)
		List<Command> commands = preLineAnalyzer.analyze(line);
		Iterator<Command> i = commands.iterator();

		//LineCommand�̉�͂�����
		HElement result = analyzeLineCommand((Command) i.next(), env);

		//ExtendCommand�̉�͂�����
		while (i.hasNext()) {
			analyzeExtendCommand((Command) i.next(), result, env);
		}
		return result;
	}

	public HElement analyzeLineCommand(Command command, HEnvironment env) {
		HALineCommandAnalyzer analyzer = getLineCommandAnalyzer(command);
		return analyzer.analyze(command.argument, env);
	}

	private HALineCommandAnalyzer getLineCommandAnalyzer(Command command) {
		HCommandAnalyzerFactory factory = HCommandAnalyzerFactory.getInstance();
		return factory.getLineCommandAnalyzer(command.command);
	}

	private void analyzeExtendCommand(Command command, HElement element,
			HEnvironment env) {
		HAExtendCommandAnalyzer analyzer = getExtendCommandAnalyzer(command);

		if (analyzer != null) {
			analyzer.analyze(command.argument, element, env);
			//������Ȃ��ꍇ�͉�͂��Ȃ�
		}
	}

	private HAExtendCommandAnalyzer getExtendCommandAnalyzer(Command command) {
		HCommandAnalyzerFactory factory = HCommandAnalyzerFactory.getInstance();
		return factory.getExtendCommandAnalyzer(command.command);
	}

	/***********************
	 * �O�����p
	 ***********************/

	class PreLineAnalyzer {

		private static final int TAB_READ = 1;
		private static final int TEXT_READ = 2;
		private static final int COMMAND_READ = 3;

		private int state = TAB_READ;

		Command command;
		private List<Command> commands;

		PreLineAnalyzer() {
		}

		public List<Command> analyze(HSLine line) {
			initialize();

			for (Iterator<HSElement> i = line.listIterator(); i.hasNext();) {
				HSElement element = (HSElement) i.next();
				switch (state) {
					case TAB_READ :
						if (element instanceof HSText) {
							command.command = HSCommand.DEFAULT_COMMAND;
							command.argument = (HSText) element;
							pushCommand();
						} else if (element instanceof HSCommand) {
							command.command = (HSCommand) element;
						}
						transit(element);
						break;
					case TEXT_READ :
						if (element instanceof HSCommand) {
							command.command = (HSCommand) element;
						}
						transit(element);
						break;
					case COMMAND_READ :
						if (element instanceof HSText) {
							command.argument = (HSText) element;
							pushCommand();
						}
						transit(element);
						break;
					default :
//						assert false;
						throw new RuntimeException("assertion");
				}
			}

			terminate();

			return commands;
		}

		private void transit(HSElement element) {
			if (element instanceof HSTab) {
				state = TAB_READ;
			} else if (element instanceof HSText) {
				state = TEXT_READ;
			} else if (element instanceof HSCommand) {
				state = COMMAND_READ;
			}
		}

		private void initialize() {
			state = TAB_READ;
			command = new Command();
			commands = new ArrayList<Command>();
		}

		private void pushCommand() {
			if (command == null || command == null) {
				System.out.println();
			}
			commands.add(command);
			command = new Command();
		}

		private void terminate() {
			if (command.command != null) {
				commands.add(command);
			}
		}
	}

	class Command {
		HSCommand command;
		HSText argument;

		public String toString() {
			return command.toString() + ":" + argument.toString();
		}
	}

}