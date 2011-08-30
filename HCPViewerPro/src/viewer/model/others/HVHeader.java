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
	 * コンストラクタ
	 */
	public HVHeader(HVModule model) {
		initialize(model);
	}

	private void initialize(HVModule model) {
		this.model = model;
		title = model.getModelModule().getTitle();
		module = "モジュール：" + model.getModelModule().getId();
		version = "バージョン：" + model.getModelModule().getVersion();
		author = "作者：" + model.getModelModule().getAuthor();
		date = "日付：" + model.getModelModule().getDate();
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
		//タイトル
		Font defaultFont = g.getFont();
		Font titleFont = context.getTitleFont();
		g.setFont(titleFont);
		g.drawString(title, 0, titleFont.getSize());
		g.setFont(defaultFont);

		//左段（モジュールとバージョン）
		int leftY = titleFont.getSize() + 25;
		g.drawString(module, 0, leftY);
		g.drawString(version, 0, leftY += 15);
		int leftWidth = Math.max(context.calculateStringWidth(module), context
				.calculateStringWidth(version));

		//右段（作者と日付）
		int rightY = titleFont.getSize() + 25;
		String author = "作者：" + this.model.getModelModule().getAuthor();
		g.drawString(author, leftWidth + context.headerXInterval, rightY);
		String date = "日付：" + this.model.getModelModule().getDate();
		g.drawString(date, leftWidth + context.headerXInterval, rightY += 15);
	}

	public void debugPrint(CGraphics g) {
		//範囲の描画
		g.setColor(Color.green);
		g.drawRoundRect(0, 0, getSize().width, getSize().height, 2, 2);
		g.setColor(Color.black);

		//基点の描画
		g.setColor(Color.blue);
		g.drawOval(0, 0, 1, 1);
		g.setColor(Color.black);
	}

}