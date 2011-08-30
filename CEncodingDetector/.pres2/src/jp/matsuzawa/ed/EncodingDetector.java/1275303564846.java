package jp.matsuzawa.ed;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

public class EncodingDetector {

	public static int SAMPLE_MAX = 65535;// byte

	public static String UNKNOWN = "UNKNOWN";
	public static String UTF8 = "UTF-8";
	public static String GB2312 = "GB2312";// EUC-JP
	public static String SJIS = "Shift_JIS";

	public static String detect(File file) {
		try {
			return detect(new FileInputStream(file));
		} catch (Exception ex) {
			ex.printStackTrace();
			return UNKNOWN;
		}
	}

	public static String detect(InputStream in) {
		EncodingDetector detector = new EncodingDetector();
		detector.doDetect(in);
		return detector.result;
	}

	private String result = "UNKNOWN";

	private void doDetect(InputStream in) {
		try {
			nsDetector det = new nsDetector(nsPSMDetector.JAPANESE);
			// det.

			det.Init(new nsICharsetDetectionObserver() {
				public void Notify(String charset) {
					System.out.println("AA" + charset);
					result = charset;
				}
			});

			BufferedInputStream imp = new BufferedInputStream(in);
			int size = imp.available();

			byte[] buf = new byte[1024];
			int len;
			// while ((len = imp.read(buf, 0, buf.length)) != -1) {
			// det.DoIt(buf, len, false);
			// }
			det.Done();
			imp.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
