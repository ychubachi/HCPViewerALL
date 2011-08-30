package viewer;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import application.HViewProperty;

public class HAWTRenderingContext extends HRenderingContext{

	// フォントサイズの計算用ダミー
	private static Graphics2D dummyGraphics;

	static {
		BufferedImage img = new BufferedImage(1, 1,
				BufferedImage.TYPE_4BYTE_ABGR);
		dummyGraphics = (Graphics2D) img.getGraphics();
	}

	private Graphics2D g;

	public HAWTRenderingContext(HViewProperty property) {
		super(property);
		g = dummyGraphics;
	}

	public void setGraphics(Graphics2D g) {
		this.g = g;
	}

	public int calculateTitleStringWidth(String s) {
		FontMetrics metrics = g.getFontMetrics(getTitleFont());
		return SwingUtilities.computeStringWidth(metrics, s);
	}

	public int calculateStringWidth(String s) {
		FontMetrics metrics = g.getFontMetrics(getBaseFont());
		return SwingUtilities.computeStringWidth(metrics, s);
	}


}