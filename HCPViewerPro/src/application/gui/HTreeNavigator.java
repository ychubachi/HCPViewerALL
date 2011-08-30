/*
 * HTreeNavigator.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import model.HModule;
import application.HCPViewer;
import application.HDocumentManager;

/**
 * Class HTreeNavigator
 * 
 * @author macchan
 * @version $Id: HTreeNavigator.java,v 1.3 2009/09/10 03:48:32 macchan Exp $
 */
public class HTreeNavigator extends JTree
		implements
			TreeSelectionListener,
			PropertyChangeListener {
	public static final long serialVersionUID = 1L;
	private HCPViewer application;

	/**
	 * Constructor for HTreeNavigator
	 */
	public HTreeNavigator(HCPViewer application) {
		this.application = application;
		initialize();
		clearModel();
	}

	private void initialize() {
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		setRootVisible(false);
		addTreeSelectionListener(this);
		application.getDocumentManager().addPropertyChangeListener(this);
	}

	/***********************************
	 * View => ModelÇÃçXêVån
	 ************************************/

	/**
	 * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
	 */
	public void valueChanged(TreeSelectionEvent evt) {
		changeModule(evt);
	}

	private void changeModule(TreeSelectionEvent evt) {
		removeTreeSelectionListener(this);

		HModule module = (HModule) ((HModuleTreeNode) evt.getPath()
				.getLastPathComponent()).getModule();
		application.getDocumentManager().setCurrentModule(module);

		addTreeSelectionListener(this);
	}

	/***********************************
	 * Model => ViewÇÃçXêVån
	 ************************************/

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals(HDocumentManager.FILE_CHANGED)) {
			clearModel();
		} else if (evt.getPropertyName().equals(
				HDocumentManager.DOCUMENT_CHANGED)) {
			refreshTree();
		} else if (evt.getPropertyName().equals(
				HDocumentManager.CURRENT_MODULE_CHANGED)) {
			refreshSelection();
		}
	}

	private void refreshTree() {
		if (application.getDocumentManager().hasDocument()) {
			List<HModule> modules = application.getDocumentManager().getDocument()
					.getModules();
			setModel(modules);
		}
	}

	private void clearModel() {
		setModel(new ArrayList<HModule>());
	}

	private void setModel(List<HModule> values) {
		setModel(createTreeModel(values));
	}

	private TreeModel createTreeModel(List<HModule> values) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		for (Iterator<HModule> i = values.iterator(); i.hasNext();) {
			HModule module = (HModule) i.next();
			root.add(new HModuleTreeNode(module));
		}
		return new DefaultTreeModel(root, false);
	}

	private void refreshSelection() {
		if (!application.getDocumentManager().hasDocument()
				|| !application.getDocumentManager().hasCurrentModule()) {
			getSelectionModel().clearSelection();
			return;
		}

		setSelectionRow(application.getDocumentManager()
				.getCurrentModuleIndex());
	}

	/***********************************
	 * TreeNode Implementer
	 ************************************/

	class HModuleTreeNode implements MutableTreeNode {
		private HModule module;
		HModuleTreeNode(HModule module) {
			this.module = module;
		}
		public HModule getModule() {
			return this.module;
		}
		public void insert(MutableTreeNode child, int index) {
		}
		public void remove(int index) {
		}
		public void remove(MutableTreeNode node) {
		}
		public void removeFromParent() {
		}
		public void setParent(MutableTreeNode newParent) {
		}
		public void setUserObject(Object object) {
		}
		public Enumeration<HModule> children() {
			return null;
		}
		public boolean getAllowsChildren() {
			return false;
		}
		public TreeNode getChildAt(int childIndex) {
			return null;
		}
		public int getChildCount() {
			return 0;
		}
		public int getIndex(TreeNode node) {
			return 0;
		}
		public TreeNode getParent() {
			return null;
		}
		public boolean isLeaf() {
			return true;
		}
		public String toString() {
			return module.getId();
		}
	}

}