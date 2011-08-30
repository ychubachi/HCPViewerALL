/*
 * HPrintModuleAction.java
 * Copyright(c) 2005 CreW Project. All rights reserved.
 */
package application.actions;

import javax.print.PrintService;

import application.HCPViewer;

/**
 * Class HPrintModuleAction
 * 
 * @author macchan
 * @version $Id: HPrintModuleAction.java,v 1.4 2009/09/10 03:48:32 macchan Exp $
 */
public class HPrintModuleAction extends HAbstractPrintAction {

	/**
	 * Constructor for HPrintModuleAction
	 */
	public HPrintModuleAction(HCPViewer application) {
		super(application);
	}

	/**
	 * @see application.actions.HAbstractAction#doAction()
	 */
	protected void doAction() {
		PrintService service = getPrintService();
		if(service == null){
			//cancelled
			return;
		}
		
		printModule(getApplication().getDocumentManager().getCurrentModule(),
				service);
	}

}