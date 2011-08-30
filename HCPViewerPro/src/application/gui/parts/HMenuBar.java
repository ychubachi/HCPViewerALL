/*
 * HMenuBar.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.gui.parts;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit;

import application.HCPViewer;
import application.HDocumentManager;
import application.gui.HEditor;
import application.gui.HViewerFrame;
import application.gui.icons.HIconResources;

/**
 * Class HMenuBar
 * 
 * @author macchan
 * @version $Id: HMenuBar.java,v 1.6 2009/09/10 03:48:32 macchan Exp $
 */
public class HMenuBar extends JMenuBar {

	public static final long serialVersionUID = 1L;

	private HCPViewer application;
	private HViewerFrame frame;

	public JMenu menuEdit;/* �蔲�� */
	private JMenu menuExportSVG;
	private JMenu menuExportJPEG;
	private JMenu menuPrint;
	private JMenuItem menuReload;

	private static int CTRL_MASK = KeyEvent.CTRL_MASK;

	public static boolean isMac() {
		return System.getProperty("os.name").startsWith("Mac");
	}

	static {
		if (isMac()) {
			CTRL_MASK = KeyEvent.META_MASK;
		}
	}

	public HMenuBar(HCPViewer application, HViewerFrame frame) {
		this.application = application;
		this.frame = frame;
		initializeActions();
		initializeMenuItems();
		hookStateListener();
	}

	private void initializeMenuItems() {
		add(createFileMenu());
		add(createEditMenu());
		add(createExportMenu());
		add(createViewMenu());
		add(createHelpMenu());
	}

	private void hookStateListener() {
		application.getDocumentManager().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						documentPropertyChange(evt);
					}
				});
	}

	public Action actionSave;/* �蔲�� */
	private Action actionUndo;
	private Action actionRedo;
	private Action actionCut = new DefaultEditorKit.CutAction();
	private Action actionCopy = new DefaultEditorKit.CopyAction();
	private Action actionPaste = new DefaultEditorKit.PasteAction();

	private void initializeActions() {
		Action action;

		// -- Save
		action = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				frame.getEditor().doSave();
			}
		};
		action.putValue(Action.NAME, "�ۑ�");
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_S, CTRL_MASK));
		action.setEnabled(true);
		this.actionSave = action;

		// -- Cut
		actionCut.putValue(Action.NAME, "Cut");
		actionCut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_X, CTRL_MASK));

		// -- Copy
		actionCopy.putValue(Action.NAME, "Copy");
		actionCopy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_C, CTRL_MASK));

		// -- Paste
		actionPaste.putValue(Action.NAME, "Paste");
		actionPaste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_V, CTRL_MASK));

		// Undo
		action = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				frame.getEditor().doUndo();
			}
		};
		action.putValue(Action.NAME, "Undo");
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_Z, CTRL_MASK));
		action.setEnabled(true);
		this.actionUndo = action;

		// Redo
		action = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				frame.getEditor().doRedo();
			}
		};
		action.putValue(Action.NAME, "Redo");
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_Z, CTRL_MASK | KeyEvent.SHIFT_MASK));
		action.setEnabled(true);
		this.actionRedo = action;
	}

	/***************************************************************************
	 * File Menu.
	 **************************************************************************/

	private JMenu createFileMenu() {
		JMenu menu = new JMenu("�t�@�C��(F)");
		menu.setMnemonic('F');
		{
			menu.add(createOpenMenu());
			menu.add(actionSave);
			menu.add(createReloadMenu());
			menu.addSeparator();
			menu.add(createPrintMenu());
			menu.addSeparator();
			menu.add(createExitMenu());
		}
		return menu;
	}

	private JMenuItem createOpenMenu() {
		JMenuItem menu = new JMenuItem("�J��");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.open();
			}
		});
		menu.setIcon(application.createIcon(HIconResources.OPEN_ICON));
		menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				InputEvent.CTRL_DOWN_MASK));
		return menu;
	}

	private JMenuItem createReloadMenu() {
		JMenuItem menu = new JMenuItem("�X�V");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.reload();
			}
		});
		menu.setIcon(application.createIcon(HIconResources.RELOAD_ICON));
		menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		menuReload = menu;
		return menu;
	}

	private JMenu createPrintMenu() {
		JMenu menu = new JMenu("���");
		{
			menu.add(createPrintModuleMenu());
			menu.add(createPrintAllModulesMenu());
		}
		menu.setIcon(application.createIcon(HIconResources.PRINT_ICON));
		menuPrint = menu;
		return menu;
	}

	private Component createPrintModuleMenu() {
		JMenuItem menu = new JMenuItem("�\�����̃��W���[��");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.printModule();
			}
		});
		menu.setIcon(application.createIcon(HIconResources.MODULE_ICON));
		menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				InputEvent.CTRL_DOWN_MASK));
		return menu;
	}

	private Component createPrintAllModulesMenu() {
		JMenuItem menu = new JMenuItem("�S�Ẵ��W���[��");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.printAllModules();
			}

		});
		menu.setIcon(application.createIcon(HIconResources.MODULES_ICON));
		menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				InputEvent.ALT_DOWN_MASK));
		return menu;
	}

	private JMenuItem createExitMenu() {
		JMenuItem menu = new JMenuItem("�I��");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.doExit();
			}
		});
		menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				InputEvent.CTRL_DOWN_MASK));
		return menu;
	}

	/***************************************************************************
	 * Edit Menu.
	 **************************************************************************/

	private JMenu createEditMenu() {
		JMenu menu = new JMenu("�ҏW(E)");
		this.menuEdit = menu;
		menu.setMnemonic('E');
		{
			menu.add(actionUndo);
			menu.add(actionRedo);
			menu.addSeparator();
			menu.add(actionCut);
			menu.add(actionCopy);
			menu.add(actionPaste);
		}
		return menu;
	}

	/***************************************************************************
	 * Export Menu.
	 **************************************************************************/

	private JMenu createExportMenu() {
		JMenu menu = new JMenu("�G�N�X�|�[�g(E)");
		menu.setMnemonic('E');
		{
			menu.add(createExportSVGMenu());
			menu.add(createExportJPEGMenu());
		}
		return menu;
	}

	private JMenuItem createExportSVGMenu() {
		JMenu menu = new JMenu("SVG�`��");
		{
			menu.add(createSVGExportModuleMenu());
			menu.add(createSVGExportAllModulesMenu());
		}
		menuExportSVG = menu;
		return menu;
	}

	private JMenuItem createExportJPEGMenu() {
		JMenu menu = new JMenu("JPEG �`��");
		{
			menu.add(createJPEGExportModuleMenu());
			menu.add(createJPEGExportAllModulesMenu());
		}
		menuExportJPEG = menu;
		return menu;
	}

	private JMenuItem createSVGExportModuleMenu() {
		JMenuItem menu = new JMenuItem("�\�����̃��W���[��");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.exportModuleToSVG();
			}
		});
		menu.setIcon(application.createIcon(HIconResources.MODULE_ICON));
		menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
				InputEvent.CTRL_DOWN_MASK));
		return menu;
	}

	private JMenuItem createSVGExportAllModulesMenu() {
		JMenuItem menu = new JMenuItem("�S�Ẵ��W���[��");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.exportAllModulesToSVG();
			}
		});
		menu.setIcon(application.createIcon(HIconResources.MODULES_ICON));
		menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
				InputEvent.ALT_DOWN_MASK));
		return menu;
	}

	private JMenuItem createJPEGExportModuleMenu() {
		JMenuItem menu = new JMenuItem("�\�����̃��W���[��");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.exportModuleToJPEG();
			}
		});
		menu.setIcon(application.createIcon(HIconResources.MODULE_ICON));
		menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,
				InputEvent.CTRL_DOWN_MASK));
		return menu;
	}

	private JMenuItem createJPEGExportAllModulesMenu() {
		JMenuItem menu = new JMenuItem("�S�Ẵ��W���[��");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.exportAllModulesToJPEG();
			}
		});
		menu.setIcon(application.createIcon(HIconResources.MODULES_ICON));
		menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J,
				InputEvent.ALT_DOWN_MASK));
		return menu;
	}

	/***************************************************************************
	 * View Menu.
	 **************************************************************************/

	private JMenu createViewMenu() {
		JMenu menu = new JMenu("�\��(V)");
		menu.setMnemonic('V');

		{
			final JCheckBoxMenuItem editor = new JCheckBoxMenuItem();
			editor.setText("�e�L�X�g�G�f�B�^");
			menu.add(editor);
			editor.setSelected(HViewerFrame.INITIAL_EDITOR_VISIBLE);
			editor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.showEditor(editor.isSelected());
				}
			});
			// editor.setIcon(application.createIcon(HIconResources.CONSOLE_ICON));
			editor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
					InputEvent.ALT_DOWN_MASK));
		}

		{
			final JCheckBoxMenuItem debug = new JCheckBoxMenuItem();
			debug.setText("�f�o�b�O �R���\�[��");
			menu.add(debug);
			debug.setSelected(HViewerFrame.INITIAL_DEBUG_VISIBLE);
			debug.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.showDebugConsole(debug.isSelected());
				}
			});
			debug.setIcon(application.createIcon(HIconResources.CONSOLE_ICON));
			debug.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
					InputEvent.ALT_DOWN_MASK));
		}

		return menu;
	}

	/***************************************************************************
	 * Help Menu.
	 **************************************************************************/

	private JMenu createHelpMenu() {
		JMenu helpMenu = new JMenu("�w���v(H)");
		helpMenu.setMnemonic('H');
		{
			helpMenu.add(createVersionMenu());
			helpMenu.add(createBugInformationMenu());
		}
		return helpMenu;
	}

	private JMenuItem createVersionMenu() {
		JMenuItem menu = new JMenuItem("�o�[�W�������");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.showApplicationInformationDialog();
			}
		});
		// menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
		// InputEvent.CTRL_DOWN_MASK));
		return menu;
	}

	private JMenuItem createBugInformationMenu() {
		JMenuItem menu = new JMenuItem("�o�O��");
		menu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.showBugInformationDialog();
			}
		});
		// menu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
		// InputEvent.CTRL_DOWN_MASK));
		return menu;
	}

	/***************************************************************************
	 * ��Ԕ��f����
	 **************************************************************************/

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	private void documentPropertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(
				HDocumentManager.PREPARE_DOCUMENT_CHANGE)
				|| evt.getPropertyName().equals(
						HDocumentManager.DOCUMENT_CHANGED)
				|| evt.getPropertyName().equals(HDocumentManager.INITIALIZE)) {
			refreshMenus();
		}
	}

	private void refreshMenus() {
		boolean enable = application.getDocumentManager().hasDocument();
		menuExportSVG.setEnabled(enable);
		menuExportJPEG.setEnabled(enable);
		menuPrint.setEnabled(enable);
		menuReload.setEnabled(enable);
	}

	public void refreshActionState(final HEditor editor) {
		// ���̂������Swing�łȂ��X���b�h�Ŏ��s����ƁC�f�b�h���b�N���Ă��܂��͗l�D
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				actionSave.setEnabled(false);
				if (editor != null && editor.isDirty()) {
					actionSave.setEnabled(true);
				}

				actionCut.setEnabled(editor != null);
				actionCopy.setEnabled(editor != null);
				actionPaste.setEnabled(editor != null);
				if (editor != null && editor.undoableEdit() != null) {
					actionUndo.putValue(Action.NAME, "Undo - "
							+ editor.undoableEdit().getUndoPresentationName());
					actionUndo.setEnabled(true);
				} else {
					actionUndo.putValue(Action.NAME, "Undo");
					actionUndo.setEnabled(false);
				}

				if (editor != null && editor.redoableEdit() != null) {
					actionRedo.putValue(Action.NAME, "Redo - "
							+ editor.redoableEdit().getRedoPresentationName());
					actionRedo.setEnabled(true);
				} else {
					actionRedo.putValue(Action.NAME, "Redo");
					actionRedo.setEnabled(false);
				}

				validate();
			}
		});
	}
}