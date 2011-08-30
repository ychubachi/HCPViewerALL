package hcpviewerplugin.editors;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorLauncher;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.RefreshAction;

import application.HCPViewer;
import application.HDocumentManager;

public class HCPViewerLauncher implements IEditorLauncher {

	public void open(IPath file) {
		try {
			HCPViewer viewer = new HCPViewer(false);
			viewer.run();
			viewer.getFrame().addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					refresh();
				}
			});
			viewer.getDocumentManager().addPropertyChangeListener(
					new PropertyChangeListener() {
						public void propertyChange(PropertyChangeEvent evt) {
							if (evt.getPropertyName().equals(
									HDocumentManager.DOCUMENT_CHANGED)) {
								refresh();
							}
						}
					});
			viewer.getDocumentManager().open(file.toFile());
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
	}

	public void refresh() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench.getWorkbenchWindowCount() >= 1) {
			IWorkbenchWindow window = workbench.getWorkbenchWindows()[0];
			RefreshAction action = new RefreshAction(window);
			action.run();
		}
	}
}
