/*
 * NewCUIClassCreationAction.java
 * Created on 2007/05/01 by macchan
 * Copyright(c) 2007 CreW Project
 */
package hcpviewerplugin.wizards;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.ui.actions.AbstractOpenWizardAction;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;

/**
 * NewCUIClassCreationAction
 */
public class NewHCPFileCreationAction extends AbstractOpenWizardAction {

	public NewHCPFileCreationAction() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jdt.ui.actions.AbstractOpenWizardAction#createWizard()
	 */
	protected INewWizard createWizard() throws CoreException {
		return new NewHCPFileCreationWizard();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.jdt.ui.actions.AbstractOpenWizardAction#
	 * doCreateProjectFirstOnEmptyWorkspace(Shell)
	 */
	protected boolean doCreateProjectFirstOnEmptyWorkspace(Shell shell) {
		return true; // can work on an empty workspace
	}

}
