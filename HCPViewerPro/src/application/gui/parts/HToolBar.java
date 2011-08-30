/*
 * HToolBar.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.gui.parts;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import application.HCPViewer;
import application.HViewProperty;
import application.gui.HViewer;
import application.gui.icons.HIconResources;

/**
 * Class HToolBar
 * 
 * @author macchan
 * @version $Id: HToolBar.java,v 1.6 2009/09/10 03:48:32 macchan Exp $
 */
public class HToolBar extends JToolBar {
	public static final long serialVersionUID = 1L;
	public static final Dimension BUTTON_SIZE = new Dimension(23, 23);
	public static final Dimension TOOL_BAR_SIZE = new Dimension(300, 25);
	public static final double[] SCALES = new double[] { 0.5d, 0.6d, 0.7d,
			0.8d, 0.9d, 1d, 1.25d, 1.5d, 1.75d, 2d, 2.5d, 3d, 3.5d, 4d };

	public HCPViewer application;

	public HToolBar(HCPViewer application) {
		this(application, null);
	}

	public HToolBar(HCPViewer application, HViewer viewer) {
		this.application = application;

		initializeToolBar();

		if (viewer != null) {
			addFileControlButtons();
			addViewControlButtons(viewer.getProperty());
			viewer.getProperty().fireInitializeEvents();
		} else {
			add(createOpenButton());
		}
	}

	private void initializeToolBar() {
		setFloatable(false);
		setPreferredSize(TOOL_BAR_SIZE);
	}

	/***************************************************************************
	 * ファイルコントロール類
	 **************************************************************************/

	private void addFileControlButtons() {
		add(createOpenButton());
		add(createReloadButton());
		add(createPrintButton());
	}

	private JButton createOpenButton() {
		JButton button = new JButton();
		button.setIcon(application.createIcon(HIconResources.OPEN_ICON));
		button.setToolTipText("開く");
		button.setMaximumSize(BUTTON_SIZE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.open();
			}
		});
		return button;
	}

	private JButton createReloadButton() {
		JButton button = new JButton();
		button.setIcon(application.createIcon(HIconResources.RELOAD_ICON));
		button.setToolTipText("更新");
		button.setMaximumSize(BUTTON_SIZE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.reload();
			}
		});
		return button;
	}

	private JButton createPrintButton() {
		JButton button = new JButton();
		button.setIcon(application.createIcon(HIconResources.PRINT_ICON));
		button.setToolTipText("表示中のモジュールを印刷");
		button.setMaximumSize(BUTTON_SIZE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				application.printModule();
			}
		});
		return button;
	}

	/***************************************************************************
	 * ビューコントロール（レベル，スケール等）類
	 **************************************************************************/

	private void addViewControlButtons(HViewProperty property) {
		// Level
		addSeparator();
		addLevelContorls(property);

		// HideProperty
		addSeparator();
		addHidePropertyControls(property);

		// Scale
		addSeparator();
		addScaleControls(property);

		// Font
		addSeparator();
		addFontControls(property);
	}

	/***************************************************************************
	 * **********************************************: レベル関係
	 **************************************************************************/

	private void addLevelContorls(HViewProperty property) {
		for (int i = 1; i <= 7; i++) {
			add(createLevelButton(property, i));
		}
		add(createAllLevelButton(property));
	}

	private HLevelButton createLevelButton(HViewProperty property, int i) {
		HLevelButton button = new HLevelButton(property, i);
		button.setIcon(application.createIcon(i + "_16.gif"));
		button.setToolTipText("レベル" + i + "まで表示");
		button.setMaximumSize(BUTTON_SIZE);
		return button;
	}

	private HLevelButton createAllLevelButton(HViewProperty property) {
		HLevelButton button = new HLevelButton(property,
				HViewProperty.RENDERRING_LEVEL_ALL);
		button.setIcon(application.createIcon(HIconResources.LEVEL_ALL_ICON));
		button.setToolTipText("すべてのレベルを表示");
		button.setMaximumSize(BUTTON_SIZE);
		return button;
	}

	/***************************************************************************
	 * **********************************************: 非表示関係
	 **************************************************************************/

	private void addHidePropertyControls(HViewProperty property) {
		add(createHideErrorCheckButton(property));
		add(createHideHeaderButton(property));
		add(createHideLineNumberButton(property));
	}

	private JToggleButton createHideErrorCheckButton(
			final HViewProperty property) {
		final JToggleButton button = new JToggleButton();
		button.setIcon(application
				.createIcon(HIconResources.HIDE_ERRORCHECK_ICON));
		button.setToolTipText("エラーチェックを非表示");
		button.setMaximumSize(BUTTON_SIZE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				property.setHideErrorCheck(button.isSelected());
			}
		});
		property.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName()
						.equals(HViewProperty.HIDE_ERROR_CHECK)
						&& !evt.getPropertyName().equals(
								HViewProperty.INITIALIZE)) {
					button.setSelected(property.isHideErrorCheck());
				}
			}
		});
		return button;
	}

	private JToggleButton createHideHeaderButton(final HViewProperty property) {
		final JToggleButton button = new JToggleButton();
		button.setIcon(application.createIcon(HIconResources.HIDE_HEADER_ICON));
		button.setToolTipText("ヘッダーを非表示");
		button.setMaximumSize(BUTTON_SIZE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				property.setHideHeader(button.isSelected());
			}
		});
		property.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(HViewProperty.HIDE_HEADER)
						&& !evt.getPropertyName().equals(
								HViewProperty.INITIALIZE)) {
					button.setSelected(property.isHideHeader());
				}
			}
		});
		return button;
	}

	private JToggleButton createHideLineNumberButton(
			final HViewProperty property) {
		final JToggleButton button = new JToggleButton();
		button.setIcon(application
				.createIcon(HIconResources.HIDE_LINENUMBER_ICON));
		button.setToolTipText("行番号を非表示");
		button.setMaximumSize(BUTTON_SIZE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				property.setHideLineNumber(button.isSelected());
			}
		});
		property.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName()
						.equals(HViewProperty.HIDE_LINE_NUMBER)
						&& !evt.getPropertyName().equals(
								HViewProperty.INITIALIZE)) {
					button.setSelected(property.isHideLineNumber());
				}
			}
		});
		return button;
	}

	/***************************************************************************
	 * **********************************************: スケール関係
	 **************************************************************************/

	private void addScaleControls(HViewProperty property) {
		HScaleComboBox scaleBox = createScaleChooser(property);
		add(createScaleDownButton(scaleBox));
		add(createScaleUpButton(scaleBox));
		add(scaleBox);
	}

	private HScaleComboBox createScaleChooser(HViewProperty property) {
		HScaleComboBox box = new HScaleComboBox(property, SCALES);
		box.setMaximumSize(new Dimension(55, 20));
		box.setToolTipText("表示倍率の選択");
		return box;
	}

	private JButton createScaleDownButton(final HScaleComboBox scaleBox) {
		JButton button = new JButton();
		button.setIcon(application.createIcon(HIconResources.SCALEDOWN_ICON));
		button.setToolTipText("縮小");
		button.setMaximumSize(BUTTON_SIZE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleBox.scaleDown();
			}
		});
		return button;
	}

	private JButton createScaleUpButton(final HScaleComboBox scaleBox) {
		JButton button = new JButton();
		button.setIcon(application.createIcon(HIconResources.SCALEUP_ICON));
		button.setToolTipText("拡大");
		button.setMaximumSize(BUTTON_SIZE);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleBox.scaleUp();
			}
		});
		return button;
	}

	private void addFontControls(HViewProperty property) {
		HFontComboBox box = new HFontComboBox(property);
		box.setMaximumSize(new Dimension(120, 20));
		box.setToolTipText("フォントの選択");
		add(box);
	}
}