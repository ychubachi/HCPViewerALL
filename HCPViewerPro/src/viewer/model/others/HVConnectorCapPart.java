package viewer.model.others;

import java.awt.Dimension;

import model.HEnvironment;
import viewer.CGraphics;
import viewer.HRenderingContext;
import viewer.model.HVCapPart;
import viewer.model.HVCompositPart;
import viewer.model.HVEnvironment;

/**
 * @author Manabu Sugiura
 * @version $Id: HVConnectorCapPart.java,v 1.3 2005/03/22 10:33:33 macchan Exp $
 */
public class HVConnectorCapPart extends HVCapPart implements HVTerminalElement {

	/**
	 * コンストラクタ
	 * @param model
	 */
	public HVConnectorCapPart(HEnvironment model) {
		super(model);
	}

	/**
	 * @see application.model.HVElement#layout(application.HRenderingContext)
	 */
	public void layout(HRenderingContext context) {
		HVEnvironment parentEnv = getParentEnvironment();

		Dimension size = new Dimension();
		size.width = context.diameter;
		size.height = context.connectorHeight;

		if (!parentEnv.hasNextCompositPart(this)) {
			size.height += context.capMargin;
		}
		setSize(size);
	}

	public void paintComponent(CGraphics g, HRenderingContext hint) {
		HVEnvironment parentEnv = getParentEnvironment();
		if (parentEnv.hasNextCompositPart(this)) {
			paintConnector(g, hint);
		} else {
			if (drawTerminalCap()) {
				paintTerminalCap(g, hint);
			}
		}
	}

	public boolean drawTerminalCap() {
		HVEnvironment parentEnv = getParentEnvironment();
		HVCompositPart previus = parentEnv.getPreviousCompositPart(this);
		if (previus.getPart() instanceof HVTerminalElement) {
			return false;
		}
		return true;
	}

	private void paintConnector(CGraphics g, HRenderingContext hint) {
		HVCompositPart next = getParentEnvironment().getNextCompositPart(this);
		int length = next.getPosition().y
				- getParentEnvironment().getParentCompositPart(this)
						.getPosition().y;
		int x = hint.radius;

		g.drawLine(x, 0, x, length);
	}

	private void paintTerminalCap(CGraphics g, HRenderingContext context) {
		//縦線を引く
		int vertical = context.diameter / 2;
		g.drawLine(vertical, 0, vertical, context.capHeight);

		//横線を引く
		int side = (context.diameter - context.capWidth) / 2;
		g.drawLine(side, context.capHeight, side + context.capWidth,
				context.capHeight);
	}

}