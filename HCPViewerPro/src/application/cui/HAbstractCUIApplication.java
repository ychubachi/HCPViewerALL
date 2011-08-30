package application.cui;

import java.io.File;
import java.io.PrintStream;

import application.HCPViewer;

public abstract class HAbstractCUIApplication {
	protected void run(String[] args) {
		showTitleString(System.out);

		if (args.length < 1 || 2 < args.length) {
			System.err.println("�����̌`��������������܂���");
			System.err.println("�g�����F �R�}���h [hcp�t�@�C����] ([out�f�B���N�g��])");
			return;
		}

		File hcpFile = new File(args[0]);
		if (!hcpFile.exists()) {
			System.err.println("�t�@�C�������݂��܂���F" + hcpFile.getAbsolutePath());
			return;
		}

		File dir = null;
		if (args.length == 2) {
			dir = new File(args[1]);
			if (!dir.exists()) {
				boolean result = dir.mkdirs();
				if (!result) {
					System.err.println("�f�B���N�g�����쐬�ł��܂���F"
							+ dir.getAbsolutePath());
					return;
				}
			}
		} else {
			dir = hcpFile.getParentFile();
		}
		doProcess(hcpFile, dir);
	}

	protected void showTitleString(PrintStream out) {
		out.println(HCPViewer.APP_NAME + " " + HCPViewer.getVersionString());
		out.println(HCPViewer.DEVELOPERS + " " + HCPViewer.COPYRIGHT);
	}

	protected abstract void doProcess(File file, File dir);
}
