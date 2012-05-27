package org.spbu.pldoctoolkit.refactor;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.spbu.pldoctoolkit.clones.CloneFinder;
import org.spbu.pldoctoolkit.clones.IClonesGroup;
import org.spbu.pldoctoolkit.clones.view.ClonesGroupResultView;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

public class CloneFinderUtil {
	
	private CloneFinderUtil(){};

	private static volatile List<IClonesGroup> clonesGroups = null;
	public static void doFindOfClones(final LangElem infEl, IEditorPart editor) {
		ClonesGroupResultView view = getView();
		//progress monitor
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask("DocLine clones finding ...", 24);
				clonesGroups = getClonesGroups(infEl, monitor);
				monitor.done();
			}
		};
		ProgressMonitorDialog pmDialog = new ProgressMonitorDialog(DrlPublisherPlugin.getShell());
		try {
			pmDialog.run(true, false, op);
			int returnCode = pmDialog.getReturnCode();
			if (returnCode == ProgressMonitorDialog.OK)
				showMessage(null);
		} catch (InvocationTargetException e) {
			showMessage(e);
		} catch (InterruptedException e) {
			showMessage(e);
		}
		view.setContent(clonesGroups, editor);		
	}
	
	private static void showMessage(Exception e) {
		if (e == null){
//			MessageDialog.openInformation(DrlPublisherPlugin.getShell(), "Information", "Export successfull");
		}else
			MessageDialog.openError(DrlPublisherPlugin.getShell(), "Error", "Export failed: " + e.getCause().getMessage());
	}
	
	private static List<IClonesGroup> getClonesGroups(LangElem infElementToFindOfClones,IProgressMonitor monitor) {
		CloneFinder cloneFinder = new CloneFinder();
		return cloneFinder.findClones(infElementToFindOfClones, monitor);
	}
	
	public static ClonesGroupResultView getView(){
		try {
			return (ClonesGroupResultView) getPage().showView(
					"org.spbu.pldoctoolkit.clones.view.ClonesGroupResultView");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static IWorkbenchPage getPage() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		return window.getActivePage();
	}
	
}
