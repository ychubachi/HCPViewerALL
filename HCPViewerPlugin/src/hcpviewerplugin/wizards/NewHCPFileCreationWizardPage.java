package hcpviewerplugin.wizards;

import hcpviewerplugin.HCPViewerPlugin;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import application.HCPViewer;

public class NewHCPFileCreationWizardPage extends WizardNewFileCreationPage {

	public static final String SAMPLE_FILE = "templates/sample.hcp";
	public static final String SAMPLE_ENCODING = "MS932";

	public NewHCPFileCreationWizardPage(String pageName,
			IStructuredSelection selection) {
		super(pageName, selection);
		variables.put("date", new SimpleDateFormat().format(new Date()));
	}

	protected InputStream getInitialContents() {
		try {
			URL contentURL = HCPViewerPlugin.getDefault().getURL(SAMPLE_FILE);
			InputStream in = contentURL.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, SAMPLE_ENCODING));

			String text = "";
			String line;
			while ((line = reader.readLine()) != null) {
				line = replaceVariable(line);
				text += line + System.getProperty("line.separator");
			}

			return new ByteArrayInputStream(text
					.getBytes(HCPViewer.DEFAULT_ENCODING));
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		// try {
		// String s = "";
		// s += "\\title 新規HCPチャート\n";
		// s += "\\author あなたの名前\n";
		// s += "\\version 1.0\n";
		// s += "\\date " + new SimpleDateFormat().format(new Date()) + "\n";
		// s += "\n";
		// s += "1レベル目\n";
		// s += "\t2レベル目\n";
		// s += "\t\t3レベル目\n";
		// s += "\t\\r 繰り返し処理\n";
		// s += "\t\t\\b 脱出条件\n";
		// s += "\t\t 繰り返し処理の内容１\n";
		// s += "\t\t 繰り返し処理の内容２\n";
		// s += "\t\\f 振り分け処理\n";
		// s += "\t\t\\b 条件1\n";
		// s += "\t\t\t 振り分け処理1の内容\n";
		// s += "\t\t\\b 条件2\n";
		// s += "\t\t\t 振り分け処理2の内容\n";
		// return new ByteArrayInputStream(s.getBytes("MS932"));
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// return null;
		// }
	}

	private static Pattern variablePattern = Pattern
			.compile("\\$\\{([^\\}]*)\\}");

	// 属性
	private Map variables = new HashMap();// <variableKey: String, variableValue

	/**
	 * @param line
	 * @return
	 */
	private String replaceVariable(String line) {
		// 変数表現にマッチさせる
		Matcher matcher = variablePattern.matcher(line);

		// マッチした部分を置換
		StringBuffer replaced = new StringBuffer();
		while (matcher.find()) {
			String key = matcher.group(1);
			String variable = (String) variables.get(key);
			if (variable == null) {
				variable = "unknown";
			}
			matcher.appendReplacement(replaced, variable);
		}
		matcher.appendTail(replaced);
		return replaced.toString();
	}
}
