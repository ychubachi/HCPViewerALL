/*
 * @(#)HDocument.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * �h�L�������g��\�����邽�߂̃N���X
 * 
 * @author Manabu Sugiura
 * @version $Id: HDocument.java,v 1.15 2009/09/10 03:48:31 macchan Exp $
 */
public class HDocument {

	/*********************************
	 * �N���X�ϐ�
	 *********************************/

	public static final String DEFAULT_TITLE = "�V�KHCP�`���[�g";
	public static final String DEFAULT_DATE = Calendar.getInstance().getTime()
			.toString();
	public static final String DEFAULT_AUTHOR = System.getProperty("user.name");
	public static final String DEFAULT_VERSION = "1.0";

	/*********************************
	 * �C���X�^���X�ϐ�
	 *********************************/

	// �t�@�C����
	private String filename = null;
	
	// �w�b�_�[
	private String title = DEFAULT_TITLE;
	private String date = DEFAULT_DATE;
	private String author = DEFAULT_AUTHOR;
	private String version = DEFAULT_VERSION;

	private Map<String, HModule> modules = new LinkedHashMap<String, HModule>();

	/*********************************
	 * ���J���\�b�h
	 *********************************/
	
	/**
	 * �t�@�C�������擾���܂�
	 */
	public String getFilename(){
		return this.filename;
	}
	
	/**
	 * �t�@�C������ݒ肵�܂�
	 * @param filename
	 */
	public void setFilename(String filename){
		this.filename = filename;
	}

	/**
	 * ���W���[����ǉ����܂�
	 * 
	 * @param module
	 *            ���W���[��
	 */
	public void addModule(HModule module) {
		// Dupulicate Check
		setNonDupulicateModuleId(module);

		// Put
		this.modules.put(module.getId(), module);
	}

	private void setNonDupulicateModuleId(HModule module) {
		String idBase = module.getId();
		for (int counter = 1; getModule(module.getId()) != null; counter++) {
			module.setId(idBase + "<" + counter + ">");
		}
	}

	/**
	 * ���W���[�����擾���܂�
	 * 
	 * @return ���W���[��
	 */
	public List<HModule> getModules() {
		return new ArrayList<HModule>(modules.values());
	}

	/**
	 * �h�L�������g���̑S�Ẵv���Z�X���擾���܂�
	 * 
	 * @return �h�L�������g���̑S�Ẵv���Z�X
	 */
	public List<HProcessElement> getAllProcessElements() {
		List<HProcessElement> allProcessElements = new ArrayList<HProcessElement>();
		List<HModule> modules = getModules();
		for (int i = 0; i < modules.size(); i++) {
			HModule module = modules.get(i);
			allProcessElements.addAll(module.getAllProcessElements());
		}
		return allProcessElements;
	}

	/**
	 * ���O���烂�W���[�����擾���܂�
	 * 
	 * @param moduleId
	 *            �擾���郂�W���[���̖��O
	 * @return ���W���[���i������Ȃ��ꍇ��null�j
	 */
	public HModule getModule(String moduleId) {
		return (HModule) modules.get(moduleId);
	}

	public boolean hasDuplicateModuleId() {
		// for (int i = 0; i < this.modules.size(); i++) {
		// HModule one = (HModule) this.modules.get(i);
		// for (int j = 0; j < this.modules.size(); j++) {
		// HModule another = (HModule) this.modules.get(j);
		// if (one != another && one.getId().equals(another.getId())) {
		// return true;
		// }
		// }
		// }
		return false;
	}

	/**
	 * �^�C�g�����擾���܂�
	 * 
	 * @return �^�C�g��
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * �^�C�g����ݒ肵�܂�
	 * 
	 * @param title
	 *            �^�C�g��
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * �쐬�������擾���܂�
	 * 
	 * @return �쐬��
	 */
	public String getDate() {
		return this.date;
	}

	/**
	 * �쐬������ݒ肵�܂�
	 * 
	 * @param date
	 *            �쐬����
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * �o�[�W�������擾���܂�
	 * 
	 * @return �o�[�W����
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * �o�[�W������ݒ肵�܂�
	 * 
	 * @param version
	 *            �o�[�W����
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * �쐬�҂��擾���܂�
	 * 
	 * @return �쐬��
	 */
	public String getAuthor() {
		return this.author;
	}

	/**
	 * �쐬�҂�ݒ肵�܂�
	 * 
	 * @param author
	 *            �쐬��
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	// ***** FOR PART OF DEBUG *****

	/**
	 * FOR DEBUG
	 */
	public void debugPrint(StringBuffer buffer) {
		buffer.append("<?xml version='1.0' encoding='Shift_JIS' ?>\n");

		startTag(buffer);
		appendModules(buffer);
		endTag(buffer);
	}

	/**
	 * FOR DEBUG
	 */
	private void startTag(StringBuffer buffer) {
		buffer.append("<" + getClass().getName());
		buffer.append(getAttributes());
		if (!this.modules.isEmpty()) {
			buffer.append(">\n");
		} else {
			buffer.append("/>\n");
		}
	}

	/**
	 * FOR DEBUG
	 */
	private void appendModules(StringBuffer buffer) {
		List<HModule> modules = getModules();
		for (int i = 0; i < modules.size(); i++) {
			HModule module = modules.get(i);
			module.debugPrint(buffer, 1);
		}
	}

	/**
	 * FOR DEBUG
	 */
	private void endTag(StringBuffer buffer) {
		if (!this.modules.isEmpty()) {
			buffer.append("</" + getClass().getName() + ">\n");
		}
	}

	/**
	 * FOR DEBUG
	 */
	protected String getAttributes() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" title=\"" + this.title + "\"");
		buffer.append(" date=\"" + this.date + "\"");
		buffer.append(" author=\"" + this.author + "\"");
		buffer.append(" version=\"" + this.version + "\"");
		return buffer.toString();
	}

}