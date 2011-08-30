/*
 * @(#)HVHeader.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model.others;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVElement;

/**
 * @author Manabu Sugiura
 * @version $Id: HVHeader.java,v 1.6 2008/10/06 02:38:26 macchan Exp $
 */
public class HVHeader extends HVElement {

	private HVModule model;

	private String title;
	private String module;
	private String version;
	private String author;
	private String date;

	/**
	 * �R���X�g���N�^
	 */
	public HVHeader(HVModule model) {
		initialize(model);
	}

	private void initialize(HVModule model) {
		this.model = model;
		title = model.getModelModule().getTitle();
		module = "���W���[���F" + model.getModelModule().getId();
		version = "�o�[�W�����F" + model.getModelModule().getVersion();
		author = "��ҁF" + model.getModelModule().getAuthor();
		date = "���t�F" + model.getModelModule().getDate();
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		setPosition(new Point(0, 0));
		int titleWidth = context.calculateTitleStringWidth(title);
		int leftWidth = Math.max(context.calculateStringWidth(module), context
				.calculateStringWidth(version)) ;
		
		int rightWidth = Math.max(context.calculateStringWidth(author), context
				.calculateStringWidth(date));
		
		int totalWidth = context.headerXInterval + leftWidth + rightWidth;		
		totalWidth = Math.max(totalWidth, titleWidth);
		setSize(new Dimension(totalWidth, context.headerHeight));
	}

	/**
	 * @see application.model.HVElement#paintComponent(application.CGraphics, application.HRenderingContext)
	 */
	protected void paintComponent(CGraphics g, HRenderingContext context) {
		//�^�C�g��
		Font defaultFont = g.getFont();
		Font titleFont = context.getTitleFont();
		g.setFont(titleFont);
		g.drawString(title, 0, titleFont.getSize());
		g.setFont(defaultFont);

		//���i�i���W���[���ƃo�[�W�����j
		int leftY = titleFont.getSize() + 25;
		g.drawString(module, 0, leftY);
		g.drawString(version, 0, leftY += 15);
		int leftWidth = Math.max(context.calculateStringWidth(module), context
				.calculateStringWidth(version));

		//�E�i�i��҂Ɠ��t�j
		int rightY = titleFont.getSize() + 25;
		String author = "��ҁF" + this.model.getModelModule().getAuthor();
		g.drawString(author, leftWidth + context.headerXInterval, rightY);
		String date = "���t�F" + this.model.getModelModule().getDate();
		g.drawString(date, leftWidth + context.headerXInterval, rightY += 15);
	}

	public void debugPrint(CGraphics g) {
		//�͈͂̕`��
		g.setColor(Color.green);
		g.drawRoundRect(0, 0, getSize().width, getSize().height, 2, 2);
		g.setColor(Color.black);

		//��_�̕`��
		g.setColor(Color.blue);
		g.drawOval(0, 0, 1, 1);
		g.setColor(Color.black);
	}

}