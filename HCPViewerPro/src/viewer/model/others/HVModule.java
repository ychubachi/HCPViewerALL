/*
 * @(#)HVModule.java
 *
 * Copyright 2004 Manabu Sugiura & CreW Project. All rights reserved.
 */

package viewer.model.others;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.HElement;
import model.HModule;
import model.HNote;
import model.HProcessElement;
import viewer.CGraphics;
import viewer.HElementBuilder;
import viewer.HRenderingContext;
import viewer.model.HVChartPart;
import viewer.model.HVElement;
import viewer.model.HVEnvironment;
import viewer.model.connections.HVConnection;
import viewer.model.connections.bendpoint.BendPointBuilder;
import viewer.model.data.HVDataEnvironment;

/**
 * @author Manabu Sugiura
 * @version $Id: HVModule.java,v 1.18 2009/09/10 03:48:32 macchan Exp $
 */
public class HVModule extends HVElement {

	/***************************************************************************
	 * �N���X�ϐ�
	 **************************************************************************/

	public static final int DEFAULT_BUILD_LEVEL = 10;

	/***************************************************************************
	 * �C���X�^���X�ϐ�
	 **************************************************************************/

	private HModule module;

	private HElementBuilder builder;

	private HVHeader header;
	private HVEnvironment processEnvironment;
	private HVDataEnvironment dataEnvironment;
	private List<HVConnection> connections;
	private HVNoteArea noteArea;

	/***************************************************************************
	 * �R���X�g���N�^
	 **************************************************************************/

	public HVModule(HModule module) {
		this(module, DEFAULT_BUILD_LEVEL);
	}

	public HVModule(HModule module, int buildLevel) {
		this.module = module;
		this.build(buildLevel);
	}

	/***************************************************************************
	 * �Ă��Ƃ��イ
	 **************************************************************************/

	// private void sethideErrorCheck(boolean enable) {
	// hideErrorCheck(module, enable);
	// }

	private void hideErrorCheck(HElement parent, boolean enable) {
		List<HProcessElement> processes = parent.getAllProcessElements();
		for (Iterator<HProcessElement> i = processes.iterator(); i.hasNext();) {
			HProcessElement element = (HProcessElement) i.next();
			hideErrorCheck(element, enable);
			if (element.getType() == HProcessElement.ERROR_CHECK) {
				setVisible(element, enable);
			}
		}
	}

	private void setVisible(HElement element, boolean visible) {
		HVChartPart part = this.builder.getView(element);
		HVElement elem = this.builder.getProcessEnvironment(element
				.getEnvironment());
		if (part != null) {
			part.setVisible(visible);
		}
		if (elem != null) {
			elem.setVisible(visible);
		}
	}

	/***************************************************************************
	 * �ݒ�&�擾
	 **************************************************************************/

	public HVEnvironment getProcessEnvironment() {
		return this.processEnvironment;
	}

	public HModule getModelModule() {
		return this.module;
	}

	public HVHeader getHeader() {
		return this.header;
	}
	
	public HElementBuilder getBuilder(){
		return this.builder;
	}

	/***************************************************************************
	 * �\�z
	 **************************************************************************/

	private synchronized void build(int buildLevel) {
		this.builder = new HElementBuilder(buildLevel);
		this.dataEnvironment = new HVDataEnvironment(this);
		this.connections = new ArrayList<HVConnection>();

		// if (!module.hasOnlyOneRootProcess()) {
		// throw new IllegalStateException("���[�g���������݂��܂�");
		// }

		buildHeader();
		processEnvironment = builder.buildEnvironment(module.getEnvironment());
		processEnvironment.setParent(this);
		dataEnvironment.addAll(builder.buildDataEnvironments(module
				.getEnvironment()));
		this.connections = builder.buildConnections(module);
		buildNotes();
	}

	private void buildHeader() {
		this.header = new HVHeader(this);
	}

	/***************************************************************************
	 * ���C�A�E�g
	 **************************************************************************/

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		
		this.hideErrorCheck(module, context.isPrintErrorCheck());
		
		// ���̊K�w�̑傫�������߂�
		this.header.layout(context);
		this.processEnvironment.layout(context);
		this.dataEnvironment.layout(context);
		this.noteArea.layout(context);

		// �����̑傫�������߂�
		Dimension headerSize = this.header.getSize();
		Dimension pSize = this.processEnvironment.getSize();
		Dimension dSize = this.dataEnvironment.getSize();
		Dimension noteSize = this.noteArea.getSize();

		// w
		int width = 0;
		int topWidth = 0;
		if (context.isPrintHeader()) {
			topWidth = headerSize.width;
		}
		int middleWidth = pSize.width + dSize.width
				+ context.processToDataMargin;
		if (context.isPrintLineNumber()) {
			middleWidth += context.lineNumberWidth;
		}
		int bottomWidth = noteSize.width;
		width = Math.max(topWidth, middleWidth);
		width = Math.max(width, bottomWidth);

		// h
		int height = 0;
		int topHeight = 0;
		if (context.isPrintHeader()) {
			topHeight += headerSize.height;
		}
		int middleHeight = Math.max(pSize.height, dSize.height);
		int bottomHeight = noteSize.height;
		height = topHeight + middleHeight + bottomHeight;

		Dimension d = new Dimension(width, height);
		this.setSize(d);

		// ���̊K�w�̈ʒu�����߂�
		int y = 0;
		int x = 0;
		if (context.isPrintHeader()) {
			y = header.getSize().height;
		}
		if (context.isPrintLineNumber()) {
			x = context.lineNumberWidth;
		}
		this.processEnvironment.setPosition(new Point(x, y));
		x += processEnvironment.getSize().width + context.processToDataMargin;
		this.dataEnvironment.setPosition(new Point(x, y));

		// �R�l�N�V�����|�ʒu�����߂�
		for (Iterator<HVConnection> i = connections.iterator(); i.hasNext();) {
			HVConnection conn = (HVConnection) i.next();
			conn.refreshPosition();
		}

		// �R�l�N�V�����|�܂��ʒu�v�Z
		int leftX = this.processEnvironment.getAbsoluteLocation().x
				+ processEnvironment.getSize().width;
		int rightX = this.dataEnvironment.getAbsoluteLocation().x;
		BendPointBuilder.setBendPoint(connections, leftX, rightX);

		// �R�l�N�V�����|BuildParts
		for (Iterator<HVConnection> i = connections.iterator(); i.hasNext();) {
			HVConnection conn = (HVConnection) i.next();
			conn.buildParts();
		}

		// �R�l�N�V�����|��_�v�Z
		for (Iterator<HVConnection> i = connections.iterator(); i.hasNext();) {
			HVConnection conn = (HVConnection) i.next();
			conn.refreshIntersection(new ArrayList<HVConnection>(connections));
		}
	}

	/***************************************************************************
	 * �`��
	 **************************************************************************/

	/**
	 * @see application.model.HVElement#paintComponent(application.CGraphics,
	 *      application.HRenderingContext)
	 */
	protected void paintComponent(CGraphics g, HRenderingContext context) {
		if (context.isPrintHeader()) {
			this.header.paint(g, context);
		}

		context.resetLineNumber();
		this.processEnvironment.paint(g, context);
		this.dataEnvironment.paint(g, context);
		this.noteArea.paint(g, context);
		for (Iterator<HVConnection> i = connections.iterator(); i.hasNext();) {
			HVConnection conn = (HVConnection) i.next();
			conn.paint(g);
		}
	}

	/***************************************************************************
	 * �m�[�g�֘A
	 **************************************************************************/

	private void buildNotes() {
		this.noteArea = new HVNoteArea(this);
		for (int i = 0; i < module.getNotes().size(); i++) {
			HNote noteModel = (HNote) module.getNotes().get(i);
			if (noteModel.getParent().getEnvironment().getLevel() <= builder
					.getRenderringLevel()) {
				HVNote note = new HVNote(noteModel, i + 1);
				noteArea.addNote(note);
			}
		}
	}

	public void debugPrint(CGraphics g) {
		// �͈͂̕`��
		g.setColor(Color.darkGray);
		g.drawRoundRect(0, 0, getSize().width + 2, getSize().height + 2, 2, 2);
		g.setColor(Color.black);

		// ��_�̕`��
		g.setColor(Color.blue);
		g.drawOval(0, 0, 1, 1);
		g.setColor(Color.black);
	}
}