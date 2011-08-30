/*
 * @(#)HCommandTable.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */
package compiler.analyzer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import compiler.HCompileException;
import compiler.HCompileLogger;
import compiler.scanner.model.HSCommand;

/**
 * �R�}���h�Ɋ֘A�t����ꂽ�A�i���C�U�[�̐ݒ�l��ێ����܂�
 * �E�A�i���C�U�[�i�̃N���X���j�͕����̃R�}���h�Ɋ֘A�t�����Ă��܂�
 * �EKey�̓R�}���h���AValue�i�ݒ�l�j�̓A�i���C�U�[�i�̃N���X���j�ł�
 * �E�f�t�H���g�̐ݒ�t�@�C���͓��p�b�P�[�W����command.properties���g�p���܂�
 * �E���̃N���X����擾�����ݒ�l�𗘗p���āA�A�i���C�U�[�����t���N�V�����Ő������A�\����͂��s���܂�
 * @author Manabu Sugiura
 * @version $Id: HCommandTable.java,v 1.2 2007/05/30 21:26:10 camei Exp $
 */
public class HCommandTable {

	/***********************
	 * �N���X�ϐ�
	 ***********************/

	//�ݒ�t�@�C���֘A
	public static final String TABLE_FILE_NAME = "command.properties";
	public static final String DEFAULT_PATH = "compiler/analyzer/"
			+ TABLE_FILE_NAME;

	//�ԐڎQ�Ɗ֘A
	public static final String MODULE_COMMAND_KEY = "moduleCommandName";
	public static final String DEFAULT_COMMAND_KEY = "defaultCommandName";

	private static HCommandTable instance = null;
	private static Properties table = new Properties();

	/**
	 * �R���X�g���N�^
	 */
	private HCommandTable() {
		try {
			loadSetting();
		} catch (Exception e) {
			throw new HCompileException("�R�}���h�ݒ�t�@�C���̓ǂݎ�蒆�ɃG���[���������܂���", e);
		}
	}

	/***********************
	 * ���J���\�b�h
	 ***********************/

	public static HCommandTable getInstance() {
		if (instance == null) {
			instance = new HCommandTable();
		}
		return instance;
	}

	public String getAnalyzerClassName(HSCommand command) {
		if (command == HSCommand.DEFAULT_COMMAND) {
			return table.getProperty(table.getProperty(DEFAULT_COMMAND_KEY));
		}
		return table.getProperty(command.getContents());
	}

	public String getModuleCommandName() {
		return table.getProperty(MODULE_COMMAND_KEY);
	}

	/***********************
	 * �t�@�C���ǂݍ��݊֘A
	 ***********************/

	private void loadSetting() throws Exception {
		try {
			loadUserSetting(TABLE_FILE_NAME);
		} catch (Exception e) {
			loadDefaultSetting(DEFAULT_PATH);
		}
	}

	private void loadUserSetting(String path) throws Exception {
		InputStream is = null;

		try {
			is = new FileInputStream(path);
			table.load(is);
		} catch (IOException e) {
			HCompileLogger logger = HCompileLogger.getInstance();
			logger.showWarningMessage("�R�}���h�ݒ�t�@�C���i" + path + "�j��������܂���");
			throw new Exception(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new HCompileException("�t�@�C���ǂݍ��݃X�g���[�����J���ł��܂���", e);
				}
			}
		}
	}

	private void loadDefaultSetting(String path) throws Exception {
		InputStream is = null;

		try {
//			ClassLoader loader = ClassLoader.getSystemClassLoader();
			ClassLoader loader = HCommandTable.class.getClassLoader();// (camei) �v���O�C�����΍�
			is = loader.getResourceAsStream(path);
			table.load(is);
		} catch (IOException e) {
			throw new HCompileException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new HCompileException("�t�@�C���ǂݍ��݃X�g���[�����J���ł��܂���", e);
				}
			}
		}
	}

}