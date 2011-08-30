package viewer.model.data;

import java.awt.Dimension;

import model.HEnvironment;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVCapPart;
import viewer.model.HVCompositPart;
import viewer.model.HVEnvironment;
import viewer.model.others.HVTerminalElement;

/**
 * @author Manabu Sugiura
 * @version $Id: HVConnectorCapDataPart.java,v 1.1 2005/04/17 18:45:06 macchan Exp $
 */
public class HVConnectorCapDataPart extends HVCapPart
		implements
			HVTerminalElement {

	/**
	 * コンストラクタ
	 * @param model
	 */
	public HVConnectorCapDataPart(HEnvironment model) {
		super(model);
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		HVEnvironment parentEnv = getParentEnvironment();

		if (!isRootCap()) {
			Dimension size = new Dimension();
			size.width = context.diameter;
			size.height = context.connectorHeight;

			if (!parentEnv.hasNextCompositPart(this)) {
				size.height += context.capMargin;
			}
			setSize(size);
		} else {//root cap
			setSize(new Dimension(context.diameter, context.capHeight));
		}
	}

	public void paintComponent(CGraphics g, HRenderingContext hint) {
		if (!isRootCap()) {
			HVEnvironment parentEnv = getParentEnvironment();
			if (parentEnv.hasNextCompositPart(this)) {
				paintConnector(g, hint);
			} else {
				//paintTerminalCap(g, hint);
			}
		}
	}

	private void paintConnector(CGraphics g, HRenderingContext hint) {
		HVCompositPart next = getParentEnvironment().getNextCompositPart(this);
		int length = next.getPosition().y
				- getParentEnvironment().getParentCompositPart(this)
						.getPosition().y;
		int x = hint.radius;

		g.drawLine(x, 0, x, length);
	}

	//	private void paintTerminalCap(CGraphics g, HRenderingContext context) {
	//		//縦線を引く
	//		int vertical = context.diameter / 2;
	//		g.drawLine(vertical, 0, vertical, context.capHeight);
	//
	//		//横線を引く
	//		int side = (context.diameter - context.capWidth) / 2;
	//		g.drawLine(side, context.capHeight, side + context.capWidth,
	//				context.capHeight);
	//	}

	private boolean isRootCap() {
		return getParentEnvironment().getParent() instanceof HVDataEnvironment;
	}

}