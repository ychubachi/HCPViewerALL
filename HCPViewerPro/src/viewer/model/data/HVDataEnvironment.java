/*
 * HVDataEnvironment.java
 * Copyright(c) 2004 CreW Project. All rights reserved.
 */
package viewer.model.data;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVElement;
import viewer.model.HVEnvironment;
import viewer.model.others.HVModule;

/**
 * Class HVDataEnvironment
 * 
 * @author macchan
 * @version $Id: HVDataEnvironment.java,v 1.8 2009/09/10 03:48:33 macchan Exp $
 */
public class HVDataEnvironment extends HVElement {

	private List<HVElement> children = new ArrayList<HVElement>();

	public HVDataEnvironment(HVModule module) {
		setParent(module);
	}

	public void addAll(List<HVEnvironment> environments) {
		for (Iterator<HVEnvironment> i = environments.iterator(); i.hasNext();) {
			HVEnvironment element = (HVEnvironment) i.next();
			add(element);
		}
	}

	public void add(HVEnvironment dataEnvironment) {
		children.add(dataEnvironment);
		dataEnvironment.setParent(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		// 下の階層の大きさを決定
		for (Iterator<HVElement> i = children.iterator(); i.hasNext();) {
			HVEnvironment environment = (HVEnvironment) i.next();
			environment.layout(context);
		}

		// 下の階層の位置を決める
		int h = 0;
		for (Iterator<HVElement> i = this.children.iterator(); i.hasNext();) {
			// HVElement element = (HVElement) i.next();
			HVEnvironment env = (HVEnvironment) i.next();

			// はじめに参照されているプロセスと同じ高さにしたい．
			// が，@bugfix processの位置はヘッダに影響されるから，absoluteではない．
			HVEnvironment processEnv = getRootModule().getBuilder()
					.getProcessEnvironment(env.getModel());
			// System.out.println("---");
			// System.out.println(env);
			// System.out.println(processEnv);
			// System.out.println(processEnv.getAbsoluteLocation());
			// System.out.println(processEnv.getFirstLevelAbsoluteLocation());
			// System.out.println(processEnv.getPosition());
			// System.out.println(processEnv.getParent());

			int categoryAbsoluteY = processEnv.getFirstLevelAbsoluteLocation().y;
			h = Math.max(categoryAbsoluteY, h);

			env.setPosition(new Point(0, h));
			h += env.getSize().height;
		}

		// 自分の大きさを決める
		int w = 0;
		for (Iterator<HVElement> i = this.children.iterator(); i.hasNext();) {
			HVElement child = (HVElement) i.next();
			Dimension d = child.getSize();
			w = Math.max(w, d.width);
		}
		this.setSize(new Dimension(w, h));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.model.HVElement#paintComponent(application.CGraphics,
	 * application.HRenderingContext)
	 */
	protected void paintComponent(CGraphics g, HRenderingContext context) {
		for (Iterator<HVElement> i = children.iterator(); i.hasNext();) {
			HVEnvironment environment = (HVEnvironment) i.next();
			environment.paint(g, context);
		}
	}

}