/*
 * HPrintModuleAllAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import java.util.Iterator;
import java.util.List;

import javax.print.PrintService;

import model.HModule;
import application.HCPViewer;

/**
 * Class HPrintModuleAllAction
 * 
 * @author macchan
 * @version $Id: HPrintModuleAllAction.java,v 1.1 2005/04/03 16:59:17 macchan
 *          Exp $
 */
public class HPrintModuleAllAction extends HAbstractPrintAction {

	/**
	 * Constructor for HPrintModuleAllAction
	 */
	public HPrintModuleAllAction(HCPViewer application) {
		super(application);
	}

	/**
	 * @see application.actions.HAbstractAction#doAction()
	 */
	protected void doAction() {
		PrintService service = getPrintService();
		if (service == null) {
			// cancelled
			return;
		}

		// printModule(getApplication().getDocumentManager().getCurrentModule(),
		// service);
		List<HModule> modules = getApplication().getDocumentManager()
				.getDocument().getModules();
		for (Iterator<HModule> i = modules.iterator(); i.hasNext();) {
			HModule module = i.next();
			printModule(module, service);
		}
	}
}