/*
 * @(#)HTestViewerFrame.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package application.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;

import model.HModule;
import viewer.HAWTRenderingContext;
import application.HCPViewer;
import application.HDocumentManager;
import application.HSystemProperty;
import application.HViewProperty;
import application.gui.icons.HIconResources;
import application.gui.parts.HDebugConsole;
import application.gui.parts.HMenuBar;
import application.gui.parts.HToolBar;

import compiler.HCompileOptions;

/**
 * Class HTestViewerFrame.
 * 
 * @author Manabu Sugiura
 * @version $Id: HViewerFrame.java,v 1.19 2009/09/10 03:48:33 macchan Exp $
 */
public class HViewerFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	/***********************
	 * Static Variable.
	 ***********************/

	// CONSTANTS.
	public static final Dimension WINDOW_SIZE = new Dimension(1000, 750);
	public static final int VIEWER_TREE_SPLIT_LOCATION = 75;
	public static final boolean INITIAL_EDITOR_VISIBLE = false;
	public static final boolean INITIAL_DEBUG_VISIBLE = false;

	private HViewProperty globalProperty = new HViewProperty();

	/***********************
	 * Instance Variable.
	 ***********************/

	private HMenuBar hMenuBar;
	private HToolBar toolBar;
	private JSplitPane editorViewerSplitPane;
	private JSplitPane inViewerSplitPane;
	private JTree treeNavigator;
	private JScrollPane viewerPane;
	private HEditor editor;
	private JPanel sourceEditorPanel;

	private HDebugConsole debugConsole;

	private Map<HModule, HView> views = new HashMap<HModule, HView>();

	private HCPViewer application;

	/***********************
	 * Constractor.
	 ***********************/

	public HViewerFrame(HCPViewer application) {
		this.application = application;
	}

	public void initialize() {
		initializeWindow();
		initializeMenuBar();
		initializeToolBar();
		initializeViews();
		application.getDocumentManager().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						documentPropertyChange(evt);
					}
				});
		this.editor.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				editorPropertyChange(evt);
			}
		});
		refreshEditorState();
		this.showEditor(INITIAL_EDITOR_VISIBLE);
		this.showDebugConsole(INITIAL_DEBUG_VISIBLE);
		this.validateTree();
	}

	/***********************
	 * Window.
	 ***********************/

	private void initializeWindow() {
		// window close operation
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				application.doExit();
			}
		});

		// Size and Location
		setPreferredSize(WINDOW_SIZE);
		setSize(WINDOW_SIZE);
		setLocationRelativeTo(null);// locate center of display.

		// TitleBar
		setIconImage(application.createImage("application/gui/icons/"
				+ HIconResources.LOGO_ICON));
		refreshWindowTitle();

		// ContentsPane
		getContentPane().setLayout(new BorderLayout());
		((JComponent) getContentPane())
				.setTransferHandler(new HFileDropInDataTransfer());
	}

	/**********************
	 * Property
	 ***********************/

	public HViewProperty getGlobalProperty() {
		return globalProperty;
	}

	/***********************
	 * Parts
	 ***********************/

	private void initializeMenuBar() {
		hMenuBar = new HMenuBar(application, this);
		setJMenuBar(hMenuBar);
	}

	private void initializeToolBar() {
		refreshCurrentToolBar();
	}

	private void initializeViews() {
		this.editorViewerSplitPane = new JSplitPane();
		getContentPane().add(this.editorViewerSplitPane, BorderLayout.CENTER);

		{// Create HCP Viewer
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setBorder(BorderFactory.createTitledBorder("HCP Viewer"));
			this.inViewerSplitPane = new JSplitPane();
			this.inViewerSplitPane
					.setDividerLocation(VIEWER_TREE_SPLIT_LOCATION);
			this.treeNavigator = new HTreeNavigator(application);
			inViewerSplitPane.add(this.treeNavigator, JSplitPane.LEFT);
			this.viewerPane = new JScrollPane();
			inViewerSplitPane.add(this.viewerPane, JSplitPane.RIGHT);
			panel.add(this.inViewerSplitPane);
			editorViewerSplitPane.add(panel, JSplitPane.RIGHT);
		}

		{// Create Source Viewer
			sourceEditorPanel = new JPanel(new BorderLayout());
			sourceEditorPanel.setBorder(BorderFactory
					.createTitledBorder("Source Editor"));
			this.editor = new HEditor(this.application);
			sourceEditorPanel.add(this.editor);
			editorViewerSplitPane.add(sourceEditorPanel, JSplitPane.LEFT);
		}

		{// Create Debug Console
			this.debugConsole = new application.gui.parts.HDebugConsole();
			this.debugConsole.setVisible(false);
			getContentPane().add(this.debugConsole, BorderLayout.SOUTH);
			HCompileOptions.getInstance().setStream(
					this.debugConsole.getStream());// 1.3.1追加
		}
	}

	/***********************
	 * 更新系
	 ***********************/

	private void documentPropertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(HDocumentManager.FILE_CHANGED)) {
			refreshWindowTitle();
			refreshEditorState();
		} else if (evt.getPropertyName().equals(
				HDocumentManager.DOCUMENT_CHANGED)) {
			refreshViews();
		} else if (evt.getPropertyName().equals(
				HDocumentManager.CURRENT_MODULE_CHANGED)) {
			refreshCurrentView();
		}
	}

	private void editorPropertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(HEditor.EDITOR_CHANGED)) {
			editorStateChanged();
		}
	}

	private void refreshEditorState() {
		if (application.getDocumentManager().hasFile()) {
			editor.setVisible(true);
			this.hMenuBar.actionSave.setEnabled(true);
		} else {
			editor.setVisible(false);
			this.hMenuBar.actionSave.setEnabled(false);
		}
	}

	public void editorStateChanged() {
		refreshWindowTitle();
		if (hMenuBar != null) {
			hMenuBar.refreshActionState(this.editor);
		}
	}

	private void refreshWindowTitle() {
		String title = HCPViewer.APP_NAME + " " + HCPViewer.VERSION;
		if (application.getDocumentManager().hasFile()) {
			title += " - "
					+ application.getDocumentManager().getFile().getName();
			if (editor.isDirty()) {
				title += "*";
			}
		}
		setTitle(title);
	}

	private void refreshViews() {
		Map<String, HView> oldViews = createIdViewMap();
		this.views = new HashMap<HModule, HView>();

		if (application.getDocumentManager().hasDocument()) {
			List<HModule> modules = application.getDocumentManager()
					.getDocument().getModules();
			for (Iterator<HModule> i = modules.iterator(); i.hasNext();) {
				HModule module = (HModule) i.next();
				views.put(module, prepareView(module, oldViews));
			}
		}
	}

	private Map<String, HView> createIdViewMap() {
		Map<String, HView> map = new HashMap<String, HView>();
		Set<HModule> keys = this.views.keySet();
		for (Iterator<HModule> i = keys.iterator(); i.hasNext();) {
			HModule module = (HModule) i.next();
			HView view = (HView) this.views.get(module);
			map.put(module.getId(), view);
		}
		return map;
	}

	private HView prepareView(HModule module, Map<String, HView> oldViews) {
		HView view = (HView) oldViews.get(module.getId());
		if (view != null) {
			view.getViewer().setModel(module);
			return view;
		} else {
			return createView(module);
		}
	}

	private HView createView(HModule module) {
		HAWTRenderingContext context = new HAWTRenderingContext(
				getGlobalProperty());
		HViewer viewer = new HViewer(module, context, getGlobalProperty());// 全部同じ表示設定
		// HViewer viewer = new HViewer(module, context, new
		// HViewProperty());//個別表示設定
		HToolBar toolBar = new HToolBar(application, viewer);
		return new HView(viewer, toolBar);
	}

	private void refreshCurrentView() {
		refreshCurrentViewer();
		refreshCurrentToolBar();
		getContentPane().validate();
		getContentPane().repaint();
	}

	private void refreshCurrentViewer() {
		// Remove Old Viewer
		viewerPane.setViewportView(null);

		if (!application.getDocumentManager().hasDocument()) {
			return;
		}

		// Get The Viewer
		HModule module = application.getDocumentManager().getCurrentModule();
		HViewer viewer = ((HView) views.get(module)).getViewer();

		// Set New Viewer
		viewerPane.setViewportView(viewer);
	}

	private void refreshCurrentToolBar() {
		// Remove ToolBar
		if (this.toolBar != null) {
			getContentPane().remove(this.toolBar);
		}

		// Get The ToolBar
		if (application.getDocumentManager().hasDocument()) {
			HModule module = application.getDocumentManager()
					.getCurrentModule();
			this.toolBar = (HToolBar) ((HView) views.get(module)).getToolBar();
		} else {
			this.toolBar = new HToolBar(application);
		}

		// Set ToolBar
		getContentPane().add(this.toolBar, BorderLayout.NORTH);
	}

	/*********************************
	 * Debug Console Management
	 *********************************/

	public void showDebugConsole(boolean show) {
		debugConsole.setVisible(show);
	}

	/*********************************
	 * Editor Management
	 *********************************/

	public void showEditor(boolean show) {
		sourceEditorPanel.setVisible(show);
		this.hMenuBar.menuEdit.setEnabled(show);
		if (show) {
			this.editorViewerSplitPane.setDividerLocation(0.5);
		} else {
			this.editorViewerSplitPane.setDividerLocation(0.0);
		}
	}

	public HEditor getEditor() {
		return editor;
	}

	/*********************************
	 * Show Dialogs.
	 *********************************/

	public void showApplicationInformationDialog() {
		String version = "Java Version: "
				+ HSystemProperty.getInstance().getVersion();
		String runtimeVersion = "Java Runtime Version: "
				+ HSystemProperty.getInstance().getRuntimeVersion();
		String[] message = { HCPViewer.APP_NAME + " " + HCPViewer.VERSION,
				HCPViewer.DEVELOPERS, HCPViewer.COPYRIGHT, version,
				runtimeVersion };
		Icon icon = new ImageIcon(getIconImage());
		showDialog(HCPViewer.APP_NAME, message,
				JOptionPane.INFORMATION_MESSAGE, icon);
	}

	public void showBugInformationDialog() {
		String[] message = { "バグ報告やアプリケーションに関する要望は",
				"macchan@crew.sfc.keio.ac.jp までお寄せください．" };
		showDialog("バグ報告", message, JOptionPane.INFORMATION_MESSAGE, null);
	}

	public void showConvertCompleteDialog(File file) {
		String title = "エクスポートの完了";
		String[] message = { "ファイルのエクスポートが完了しました",
				"（" + file.getAbsolutePath() + ")", "" };
		showDialog(title, message, JOptionPane.INFORMATION_MESSAGE, null);
	}

	public void showPrintCompleteDialog() {
		String title = "印刷ジョブ送信の完了";
		String[] message = { "印刷ジョブの送信が完了しました" };
		showDialog(title, message, JOptionPane.INFORMATION_MESSAGE, null);
	}

	private void showDialog(String title, Object message, int type, Icon icon) {
		JOptionPane op = new JOptionPane();
		op.setMessage(message);
		op.setMessageType(type);
		op.setIcon(icon);
		JDialog dialog = op.createDialog(this, title);
		dialog.setVisible(true);
	}

	/***********************
	 * For Drug & Drop.
	 ***********************/

	class HFileDropInDataTransfer extends TransferHandler {

		private static final long serialVersionUID = 1L;

		public boolean canImport(JComponent comp, DataFlavor[] flavors) {
			for (int i = 0; i < flavors.length; i++) {
				if (flavors[i].isFlavorJavaFileListType()) {
					return true;
				}
			}
			return false;
		}

		public boolean importData(JComponent comp, Transferable t) {
			try {
				DataFlavor[] flavors = t.getTransferDataFlavors();
				for (int i = 0; i < flavors.length; i++) {
					if (flavors[i].isFlavorJavaFileListType()) {
						Object o = t.getTransferData(flavors[i]);
						List<?> files = (List<?>) o;
						if (files.size() > 0) {
							File file = (File) files.get(0);
							application.open(file);
							return true;
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return false;
		}
	}

}