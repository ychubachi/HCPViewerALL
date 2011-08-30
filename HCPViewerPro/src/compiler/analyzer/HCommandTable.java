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
 * コマンドに関連付けられたアナライザーの設定値を保持します
 * ・アナライザー（のクラス名）は複数のコマンドに関連付けられています
 * ・Keyはコマンド名、Value（設定値）はアナライザー（のクラス名）です
 * ・デフォルトの設定ファイルは同パッケージ内のcommand.propertiesを使用します
 * ・このクラスから取得した設定値を利用して、アナライザーをリフレクションで生成し、構文解析を行います
 * @author Manabu Sugiura
 * @version $Id: HCommandTable.java,v 1.2 2007/05/30 21:26:10 camei Exp $
 */
public class HCommandTable {

	/***********************
	 * クラス変数
	 ***********************/

	//設定ファイル関連
	public static final String TABLE_FILE_NAME = "command.properties";
	public static final String DEFAULT_PATH = "compiler/analyzer/"
			+ TABLE_FILE_NAME;

	//間接参照関連
	public static final String MODULE_COMMAND_KEY = "moduleCommandName";
	public static final String DEFAULT_COMMAND_KEY = "defaultCommandName";

	private static HCommandTable instance = null;
	private static Properties table = new Properties();

	/**
	 * コンストラクタ
	 */
	private HCommandTable() {
		try {
			loadSetting();
		} catch (Exception e) {
			throw new HCompileException("コマンド設定ファイルの読み取り中にエラーが発生しました", e);
		}
	}

	/***********************
	 * 公開メソッド
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
	 * ファイル読み込み関連
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
			logger.showWarningMessage("コマンド設定ファイル（" + path + "）が見つかりません");
			throw new Exception(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new HCompileException("ファイル読み込みストリームが開放できません", e);
				}
			}
		}
	}

	private void loadDefaultSetting(String path) throws Exception {
		InputStream is = null;

		try {
//			ClassLoader loader = ClassLoader.getSystemClassLoader();
			ClassLoader loader = HCommandTable.class.getClassLoader();// (camei) プラグイン化対策
			is = loader.getResourceAsStream(path);
			table.load(is);
		} catch (IOException e) {
			throw new HCompileException(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new HCompileException("ファイル読み込みストリームが開放できません", e);
				}
			}
		}
	}

}