/*
 * HCPViewer.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

import jp.matsuzawa.ed.EncodingDetector;
import application.actions.HDnDFileOpenAction;
import application.actions.HExportRasterImageAction;
import application.actions.HExportRasterImageAllAction;
import application.actions.HExportSVGAction;
import application.actions.HExportSVGAllAction;
import application.actions.HFileOpenAction;
import application.actions.HFileReloadAction;
import application.actions.HPrintModuleAction;
import application.actions.HPrintModuleAllAction;
import application.exporters.HRasterImageExporter;
import application.gui.HViewerFrame;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

/**
 * Class HCPViewer
 * 
 * @変更情報 package/README.txt
 * @パッケージング方法 build.xmlではき出すこと．
 * 
 * @DONE デフォルトエンコーディングをUTF-8にする
 * 
 * @TODO Font系がAWTに依存している
 * @TODO 字句解析時，スペースのあとスペースやタブが続く場合，その文字が文字列として認識される．
 * @TODO 印刷の倍率を指定できる機能を追加したい
 * @TODO データ入出力が多すぎる場合，データの右にベンドポイントが来てしまう
 * @TODO ヘッダーの並びを調整する機能を追加したい
 * 
 * @author macchan
 * @version $Id$
 */
public class HCPViewer {

	/***************************************************************************
	 * Static Variables.
	 **************************************************************************/

	// Debug Flag
	public static final boolean DEBUG = false;

	// Application's Information.
	public static final String APP_NAME = "HCP Viewer Professional";
	public static final String VERSION = "1.4.2";
	public static final String BUILD_DATE = "2010/05/31";
	public static final String DEVELOPERS = "Yoshiaki Matsuzawa & Manabu Sugiura";
	public static final String COPYRIGHT = "Copyright(c) 2004-2010 CreW Project. All Rights Reserved.";

	public static final String FILE_EXTENSION = "hcp";
	public static final String ICON_FOLDER_PATH = "application/gui/icons/";

	public static final String DEFAULT_ENCODING = "UTF-8";

	public static void main(String[] args) throws Exception {
		HCPViewer viewer = new HCPViewer(true);
		viewer.run();
		if (args.length == 1) {
			viewer.getDocumentManager().open(new File(args[0]));
		}
	}

	public static final String detectEncoding(File in) {
		String encoding = EncodingDetector.detect(in);
		System.out.println(encoding);
		if (EncodingDetector.UNKNOWN.equals(encoding)) {
			encoding = HCPViewer.DEFAULT_ENCODING;
		}
		return encoding;
	}

	private HDocumentManager documentManager;
	private HViewerFrame frame;
	private boolean standalone = true;

	/***************************************************************************
	 * Constructors
	 **************************************************************************/

	public HCPViewer(boolean standalone) {
		this.standalone = standalone;
	}

	/***************************************************************************
	 * メイン
	 **************************************************************************/

	public void run() {
		showMessage("初期化しています．");

		showMessage("ドキュメントマネージャを生成しています．");
		this.documentManager = new HDocumentManager();

		showMessage("Swingを初期化しています．");
		initializeLookAndFeel();

		showMessage("Windowを初期化しています．");
		this.frame = new HViewerFrame(this);
		this.frame.initialize();
		this.frame.setVisible(true);

		showMessage("ドキュメントマネージャを初期化しています．");
		this.documentManager.fireInitializeEvents();

		terminateSplash();
	}

	private void showMessage(String message) {
		if (HSystemProperty.getInstance().getMinorVersion() >= 6) {
			SplashScreen splash = SplashScreen.getSplashScreen();
			if (splash != null) {
				Graphics2D g2d = splash.createGraphics();
				g2d.setColor(Color.BLACK);
				g2d.drawString(getVersionString(), 72, 116);

				g2d.setColor(Color.WHITE);
				g2d.fillRect(10, 152, 300, 17);
				g2d.setColor(Color.BLACK);
				g2d.drawString(message, 72, 166);
				splash.update();
			}
		}
	}

	private void terminateSplash() {
		if (HSystemProperty.getInstance().getMinorVersion() >= 6) {
			SplashScreen splash = SplashScreen.getSplashScreen();
			if (splash != null) {
				splash.close();
			}
		}
	}

	/***************************************************************************
	 * ユーティリティー
	 **************************************************************************/

	private void initializeLookAndFeel() {
		if (System.getProperty("os.name").startsWith("Windows")) {
			try {
				UIManager.setLookAndFeel(WindowsLookAndFeel.class.getName());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String getVersionString() {
		return "version " + VERSION + "(build " + BUILD_DATE + ")";
	}

	public Icon createIcon(String path) {
		Image image = createImage(ICON_FOLDER_PATH + path);
		if (image != null) {
			return new ImageIcon(image);
		}
		return null;
	}

	public Image createImage(String path) {
		URL url = getClass().getClassLoader().getResource(path);
		Toolkit tk = Toolkit.getDefaultToolkit();
		if (url != null) {
			return tk.getImage(url);
		}
		return null;
	}

	/***************************************************************************
	 * getters
	 **************************************************************************/

	public HDocumentManager getDocumentManager() {
		return this.documentManager;
	}

	public HViewerFrame getFrame() {
		return this.frame;
	}

	/***************************************************************************
	 * アクションdeligator
	 **************************************************************************/

	public void doExit() {
		if (standalone) {
			System.exit(0);
		} else {
			this.frame.dispose();
		}
	}

	public void open() {
		new HFileOpenAction(this).execute();
	}

	public void open(File file) {
		new HDnDFileOpenAction(this, file).execute();
	}

	public void reload() {
		new HFileReloadAction(this).execute();
	}

	public void printModule() {
		new HPrintModuleAction(this).execute();
	}

	public void printAllModules() {
		new HPrintModuleAllAction(this).execute();
	}

	public void exportModuleToSVG() {
		new HExportSVGAction(this).execute();
	}

	public void exportAllModulesToSVG() {
		new HExportSVGAllAction(this).execute();
	}

	public void exportModuleToJPEG() {
		new HExportRasterImageAction(this, HRasterImageExporter.JPG).execute();
	}

	public void exportAllModulesToJPEG() {
		new HExportRasterImageAllAction(this, HRasterImageExporter.JPG)
				.execute();
	}

}