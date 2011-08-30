package viewer;

import java.awt.Font;

import application.HCPViewer;
import application.HViewProperty;

public abstract class HRenderingContext {

	private static boolean debug = HCPViewer.DEBUG;
	private HViewProperty property = null;

	public HRenderingContext(HViewProperty property) {		
		setFont(property.getFont());
		this.property = property;
	}

	/***************************************************************************
	 * ��{�T�C�Y
	 **************************************************************************/

	public final int radius = 6;
	public final int diameter = radius * 2;

	public final int processToDataMargin = 100;

	/***************************************************************************
	 * �����_�����O�K�w
	 **************************************************************************/

	// private int renderingLevel = HVModule.DEFAULT_BUILD_LEVEL;
	// public void setRenderingLevel(int renderingLevel) {
	// this.renderingLevel = renderingLevel;
	// }
	public int getRenderingLevel() {
		// return this.renderingLevel;
		return property.getRenderringLevel();
	}

	/***************************************************************************
	 * �L���b�v�C�R�l�N�^�[
	 **************************************************************************/

	public final int capWidth = radius;
	public final int capHeight = radius;
	public final int capMargin = radius;
	public final int connectorHeight = radius;

	/***************************************************************************
	 * �i����
	 **************************************************************************/

	public final int newLevelMargin = 4;
	public final int newLevelConnectorWidth = diameter + newLevelMargin + 2;
	public final int newLevelConnectorHeight = diameter;
	public final int indent = newLevelMargin + radius + 2;

	/***************************************************************************
	 * �e�L�X�g
	 **************************************************************************/

	public final int textMargin = diameter * 3 / 4;

	/***************************************************************************
	 * �`��}�[�W��
	 **************************************************************************/

	public final int xMargin = 30;
	public final int yMargin = 30;

	/***************************************************************************
	 * �w�b�_�[
	 **************************************************************************/

//	private boolean printHeader = true;
	public final int headerXInterval = 20;
	public final int headerHeight = 80;

//	public void setPrintHeader(boolean printHeader) {
//		this.printHeader = printHeader;
//	}

	public boolean isPrintHeader() {
		return !property.isHideHeader();
	}

	/***************************************************************************
	 * �s�ԍ�
	 **************************************************************************/

	private int lineNumber = 1;
	public int lineNumberWidth = 30;

//	private boolean printLineNumber = true;

//	public void setPrintLineNumber(boolean printLineNumber) {
//		this.printLineNumber = printLineNumber;
//	}

	public boolean isPrintLineNumber() {
		return !property.isHideLineNumber();
	}

	public void resetLineNumber() {
		lineNumber = 1;
	}

	public String getLineNumber() {
		if (this.lineNumber < 10) {
			return "  " + this.lineNumber;
		}
		return Integer.toString(this.lineNumber);
	}

	public void addLineNumber() {
		lineNumber++;
	}
	
	/***************************************************************************
	 * �G���[�`�F�b�N
	 **************************************************************************/
	
	public boolean isPrintErrorCheck() {
		return !property.isHideErrorCheck();
	}

	/***************************************************************************
	 * �u�����`
	 **************************************************************************/

	public final int branchCapWidth = diameter + newLevelMargin + 2;
	public final int branchCapHeight = diameter * 2 + diameter;

	/***************************************************************************
	 * �t�H���g
	 **************************************************************************/

	private Font baseFont;
	private Font titleFont;
	private Font noteFont;

	// public final int noteFontSize = baseFont.getSize() - 1;
	// public final int noteRefFontSize = baseFont.getSize() - 3;
	// public final int titleFontSize = 18;
	// public final int titleStyle = Font.PLAIN;

	public void setFont(Font font) {
		baseFont = font;
		titleFont = new Font(baseFont.getName(), baseFont.getStyle(), baseFont
				.getSize() + 6);
		noteFont = new Font(baseFont.getName(), baseFont.getStyle(), baseFont
				.getSize() - 1);
		// g.setFont(baseFont);
	}

	public Font getBaseFont() {
		return baseFont;
	}

	public Font getTitleFont() {
		return titleFont;
	}

	public Font getNoteFont() {
		return noteFont;
	}

	public abstract int calculateTitleStringWidth(String s);

	public abstract int calculateStringWidth(String s);

	public void setDebugMode(boolean debug) {
		HRenderingContext.debug = debug;
	}

	public boolean isDebugMode() {
		return debug;
	}

}