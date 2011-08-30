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
		// s += "\\title �V�KHCP�`���[�g\n";
		// s += "\\author ���Ȃ��̖��O\n";
		// s += "\\version 1.0\n";
		// s += "\\date " + new SimpleDateFormat().format(new Date()) + "\n";
		// s += "\n";
		// s += "1���x����\n";
		// s += "\t2���x����\n";
		// s += "\t\t3���x����\n";
		// s += "\t\\r �J��Ԃ�����\n";
		// s += "\t\t\\b �E�o����\n";
		// s += "\t\t �J��Ԃ������̓��e�P\n";
		// s += "\t\t �J��Ԃ������̓��e�Q\n";
		// s += "\t\\f �U�蕪������\n";
		// s += "\t\t\\b ����1\n";
		// s += "\t\t\t �U�蕪������1�̓��e\n";
		// s += "\t\t\\b ����2\n";
		// s += "\t\t\t �U�蕪������2�̓��e\n";
		// return new ByteArrayInputStream(s.getBytes("MS932"));
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// return null;
		// }
	}

	private static Pattern variablePattern = Pattern
			.compile("\\$\\{([^\\}]*)\\}");

	// ����
	private Map variables = new HashMap();// <variableKey: String, variableValue

	/**
	 * @param line
	 * @return
	 */
	private String replaceVariable(String line) {
		// �ϐ��\���Ƀ}�b�`������
		Matcher matcher = variablePattern.matcher(line);

		// �}�b�`����������u��
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
