/*
 * HDocumentManager.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import model.HDocument;
import model.HModule;

import compiler.HCompiler;

/**
 * Class HDocumentManager
 * 
 * @author macchan
 * @version $Id: HDocumentManager.java,v 1.4 2009/09/10 03:48:32 macchan Exp $
 */
public class HDocumentManager {

	public static final String FILE_CHANGED = "fileChanged";
	public static final String PREPARE_FILE_CHANGE = "prepareFileChange";
	public static final String DOCUMENT_CHANGED = "documentChanged";
	public static final String PREPARE_DOCUMENT_CHANGE = "prepareDocumentChange";
	public static final String CURRENT_MODULE_CHANGED = "currentModuleChanged";
	public static final String INITIALIZE = "initialize";

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	private File file = null;
	private HDocument document = null;
	private HModule currentModule = null;

	/***************************
	 * 高水準操作
	 ***************************/

	public void open(File file) {
		close();
		setFile(file);
		load();
	}

	public void close() {
		clearDocument();
		clearCurrentModule();
		clearFile();
	}

	public void load() {
		HDocument document = compile(this.file);
		setDocument(document);
		validateCurrentModule();
	}

	private HDocument compile(File file) {
		return HCompiler.compile(file);
	}

	/***************************
	 * ファイル操作
	 ***************************/

	public File getFile() {
		return this.file;
	}

	private void setFile(File newFile) {
		propertyChangeSupport.firePropertyChange(PREPARE_FILE_CHANGE, null,
				null);
		File oldFile = this.file;
		this.file = newFile;
		propertyChangeSupport
				.firePropertyChange(FILE_CHANGED, oldFile, newFile);
	}

	public boolean hasFile() {
		return this.file != null;
	}

	public void clearFile() {
		setFile(null);
	}

	/***************************
	 * ドキュメント操作
	 ***************************/

	public HDocument getDocument() {
		return this.document;
	}

	private void setDocument(HDocument newDocument) {
		propertyChangeSupport.firePropertyChange(PREPARE_DOCUMENT_CHANGE, null,
				null);
		HDocument oldDocument = this.document;
		this.document = newDocument;
		propertyChangeSupport.firePropertyChange(DOCUMENT_CHANGED, oldDocument,
				newDocument);
	}

	public boolean hasDocument() {
		return this.document != null;
	}

	private void clearDocument() {
		setDocument(null);
	}

	/***************************
	 * currentModule操作
	 ***************************/

	public HModule getCurrentModule() {
		return this.currentModule;
	}

	public void setCurrentModule(HModule newModule) {
		HModule oldModule = this.currentModule;
		this.currentModule = newModule;
		propertyChangeSupport.firePropertyChange(CURRENT_MODULE_CHANGED,
				oldModule, newModule);
	}

	private void clearCurrentModule() {
		setCurrentModule(null);
	}

	public int getCurrentModuleIndex() {
		if (!hasDocument() || !hasCurrentModule()) {
			return -1;
		}
		return this.document.getModules().indexOf(this.currentModule);
	}

	public boolean hasCurrentModule() {
		return this.currentModule != null;
	}

	private void validateCurrentModule() {
		if (!hasDocument() || getDocument().getModules().isEmpty()) {
			return;
		}

		if (hasCurrentModule()) {
			List<HModule> modules = getDocument().getModules();
			for (Iterator<HModule> i = modules.iterator(); i.hasNext();) {
				HModule module = (HModule) i.next();
				if (module.getId().equals(currentModule.getId())) {
					setCurrentModule(module);
					return;
				}
			}
		}

		setCurrentModule((HModule) getDocument().getModules().get(0));
	}

	/*************************************
	 * イベント関連
	 *************************************/

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	void fireInitializeEvents() {
		propertyChangeSupport.firePropertyChange(INITIALIZE, null, null);
	}

}