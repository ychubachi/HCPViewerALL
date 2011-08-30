package application.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.undo.UndoableEdit;

import application.HCPViewer;
import application.HDocumentManager;

public class HEditor extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int WHITESPACE_COUNT_FOR_TAB = 4;

	public static final String EDITOR_CHANGED = "EDITOR_CHANGED";

	private HCPViewer application;

	private LinkedList<UndoableEdit> undoStack = new LinkedList<UndoableEdit>();
	private LinkedList<UndoableEdit> redoStack = new LinkedList<UndoableEdit>();

	private NonWrappingTextPane textArea = new NonWrappingTextPane();
	private LineNumberView lineNumberView;
	private boolean dirty = false;

	DocumentListener listener = new DocumentListener() {
		public void changedUpdate(DocumentEvent e) {
			setDirty(true);
		}

		public void insertUpdate(DocumentEvent e) {
			setDirty(true);
		}

		public void removeUpdate(DocumentEvent e) {
			setDirty(true);
		}
	};

	public HEditor(HCPViewer application) {
		super();
		this.application = application;
		initializeViews();
		initializeListeners();
	}

	public void initializeViews() {
		this.setLayout(new BorderLayout());
		// textArea.setEditorKit(new JavaCodeKit());
		this.lineNumberView = new LineNumberView(textArea);

		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setRowHeaderView(lineNumberView);
		this.add(BorderLayout.CENTER, scroll);
	}

	private void initializeListeners() {
		textArea.getDocument().addDocumentListener(listener);
		textArea.getDocument().addUndoableEditListener(
				new UndoableEditListener() {
					public void undoableEditHappened(UndoableEditEvent evt) {
						undoStack.addFirst(evt.getEdit());
						firePropertyChange(EDITOR_CHANGED, null, null);
					}
				});
		application.getDocumentManager().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						if (evt.getPropertyName().equals(
								HDocumentManager.FILE_CHANGED)) {
							load();
						}
					}
				});
	}

	public List<Action> getActions() {
		return Arrays.asList(textArea.getActions());
	}

	public UndoableEdit undoableEdit() {
		return undoStack.peek();
	}

	public UndoableEdit redoableEdit() {
		return redoStack.peek();
	}

	public void doUndo() {
		UndoableEdit edit = undoStack.removeFirst();
		edit.undo();
		redoStack.addFirst(edit);
		firePropertyChange(EDITOR_CHANGED, null, null);
	}

	public void doRedo() {
		UndoableEdit edit = redoStack.removeFirst();
		edit.redo();
		undoStack.addFirst(edit);
		firePropertyChange(EDITOR_CHANGED, null, null);
	}

	public String getText() {
		return textArea.getText();
	}

	public void setText(String text) {
		textArea.setText(text);
		textArea.setCaretPosition(0);
		setTabs(textArea, WHITESPACE_COUNT_FOR_TAB);
	}

	public boolean isDirty() {
		return dirty;
	}

	private void setDirty(boolean dirty) {
		if (this.dirty != dirty) {
			this.dirty = dirty;
			firePropertyChange(EDITOR_CHANGED, null, null);
		}
	}

	private void load() {
		setText(loadText());
		setDirty(false);
	}

	private String loadText() {
		File file = application.getDocumentManager().getFile();
		if (file != null) {
			this.setEnabled(true);
			return FileSystemUtil.load(file);
		} else {
			this.setEnabled(false);
			return "";
		}
	}

	public void doSave() {
		File file = application.getDocumentManager().getFile();
		FileSystemUtil.save(file, textArea.getText());
		setDirty(false);
		application.getDocumentManager().load();
	}

	public static void setTabs(JTextPane textPane, int charactersPerTab) {
		FontMetrics fm = textPane.getFontMetrics(textPane.getFont());
		int charWidth = fm.charWidth('w');
		int tabWidth = charWidth * charactersPerTab;

		TabStop[] tabs = new TabStop[10];

		for (int j = 0; j < tabs.length; j++) {
			int tab = j + 1;
			tabs[j] = new TabStop(tab * tabWidth);
		}

		TabSet tabSet = new TabSet(tabs);
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		StyleConstants.setTabSet(attributes, tabSet);
		int length = textPane.getDocument().getLength();
		textPane.getStyledDocument().setParagraphAttributes(0, length,
				attributes, false);
	}

	/**
	 * フォントを変更する場合、必ずこのメソッドを通して変更する。 （でないと行番号がずれてしまう）
	 * 
	 * @param font
	 */
	public void changeFont(Font font) {
		this.textArea.setFont(font);
		this.lineNumberView.setFontInformation(font);
	}

	private class LineNumberView extends JComponent {

		private static final long serialVersionUID = 1L;
		private static final int MARGIN = 5;
		private static final int DEBUG_BUTTON_MARGIN = 20;

		// private final JTextArea text;
		private final JTextPane text;

		private FontMetrics fontMetrics;

		private int topInset;

		private int fontAscent;

		private int fontHeight;

		// public LineNumberView(JTextArea textArea) {
		public LineNumberView(JTextPane textArea) {
			text = textArea;
			this.setFontInformation(text);
			text.getDocument().addDocumentListener(new DocumentListener() {
				public void insertUpdate(DocumentEvent e) {
					repaint();
				}

				public void removeUpdate(DocumentEvent e) {
					repaint();
				}

				public void changedUpdate(DocumentEvent e) {
				}
			});
			text.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					revalidate();
					repaint();
				}
			});
			setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
		}

		public void setFontInformation(JTextPane text) {
			setFontInformation(text.getFont());
		}

		public void setFontInformation(Font font) {
			fontMetrics = getFontMetrics(font);
			fontHeight = fontMetrics.getHeight();
			fontAscent = fontMetrics.getAscent();
			topInset = text.getInsets().top;
		}

		private int getComponentWidth() {
			return DEBUG_BUTTON_MARGIN + getLineTextWidth();
		}

		private int getLineTextWidth() {
			Document doc = text.getDocument();
			Element root = doc.getDefaultRootElement();
			int lineCount = root.getElementIndex(doc.getLength());
			int maxDigits = Math.max(3, String.valueOf(lineCount).length());
			return maxDigits * fontMetrics.stringWidth("0") + MARGIN * 2;
		}

		private int getLineAtPoint(int y) {
			Element root = text.getDocument().getDefaultRootElement();
			int pos = text.viewToModel(new Point(0, y));
			return root.getElementIndex(pos);
		}

		public Dimension getPreferredSize() {
			return new Dimension(getComponentWidth(), text.getHeight());
		}

		public void paintComponent(Graphics g) {
			Rectangle clip = g.getClipBounds();
			g.setColor(getBackground());
			g.fillRect(clip.x, clip.y, clip.width, clip.height);
			g.setColor(getForeground());
			int base = clip.y - topInset;
			int start = getLineAtPoint(base);
			int end = getLineAtPoint(base + clip.height);
			int y = topInset - fontHeight + fontAscent + start * fontHeight;
			for (int i = start; i <= end; i++) {
				String text = String.valueOf(i + 1);
				int x = DEBUG_BUTTON_MARGIN + getLineTextWidth() - MARGIN
						- fontMetrics.stringWidth(text);
				y = y + fontHeight;
				g.drawString(text, x, y);
			}
		}
	}

	private class NonWrappingTextPane extends JTextPane {
		private static final long serialVersionUID = 1L;

		public boolean getScrollableTracksViewportWidth() {
			Component parent = getParent();
			ComponentUI ui = getUI();

			return parent != null ? (ui.getPreferredSize(this).width <= parent
					.getSize().width) : true;
		}

	}

}

class FileSystemUtil {

	public static final String PATH_SEPARATOR = File.pathSeparator;
	public static final String SEPARATOR = File.separator;
	public static final String CR = System.getProperty("line.separator");

	public static String load(File f) {
		try {
			String encoding = HCPViewer.detectEncoding(f);
			StringBuffer buf = new StringBuffer();
			InputStreamReader isr = new InputStreamReader(
					new FileInputStream(f), encoding);
			BufferedReader reader = new BufferedReader(isr);
			while (reader.ready()) {
				buf.append(reader.readLine());
				if (reader.ready()) {
					buf.append(CR);
				}
			}
			reader.close();
			return buf.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void save(File f, String text) {
		try {
			FileOutputStream fos = new FileOutputStream(f);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fos, HCPViewer.DEFAULT_ENCODING));
			Scanner scanner = new Scanner(text);
			while (scanner.hasNext()) {
				writer.write(scanner.nextLine());
				if (scanner.hasNext()) {
					writer.newLine();
				}
			}
			writer.flush();
			writer.close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
