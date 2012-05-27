package org.spbu.pldoctoolkit.actions;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.clones.IClonesGroup;
import org.spbu.pldoctoolkit.clones.view.ClonesGroupResultView;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;
import org.spbu.pldoctoolkit.clones.*;

public final class FindClonesInInfElemAction extends Action implements
		IValidateDRLSelection {

	private static String refactName = "Find clones in InfElem";
	private final FileEditorInput editorInput;
	private IEditorPart editor;
	private TextEditor te;
	private final IProject project;
	private final ProjectContent projectContent;
	private ClonesGroupResultView view;
	private LangElem infElementToFindOfClones;

	public FindClonesInInfElemAction(IEditorPart editor) throws Exception {
		super(refactName);
		this.editor = editor;
		te = (TextEditor) editor;
		editorInput = (FileEditorInput) editor.getEditorInput();
		project = editorInput.getFile().getProject();
		projectContent = (ProjectContent) ((ProjectRegistryImpl) PLDocToolkitPlugin
				.getRegistry(project.getName())).projectContent;
		((DrlTextEditor) te).getMenuListener().addListener(this);
	}

	@Override
	public void validateSelection(IEditorPart part) {
		ISelection sel = te.getSelectionProvider().getSelection();
		TextSelection ts = (TextSelection) sel;

		IDocument doc = te.getDocumentProvider().getDocument(editorInput);
		DRLDocument DRLdoc = projectContent.DRLDocs.get(editorInput.getFile());
		try {
			int line1 = ts.getStartLine();
			int column1 = ts.getOffset() - doc.getLineOffset(line1);
			int line2 = ts.getEndLine();
			int column2 = ts.getOffset() + ts.getLength()
					- doc.getLineOffset(line2);
			PositionInText pos1 = new PositionInText(line1 + 1, column1 + 1);
			PositionInText pos2 = new PositionInText(line2 + 1, column2 + 1);

			if (pos1.compare(pos2) == 0) {
				setEnabled(false);
				return;
			}
			setEnabled(selectedAreaIsInfElem(DRLdoc, pos1, pos2));
//			setEnabled(true);
		} catch (Exception e) {
			setEnabled(false);
			e.printStackTrace();
		}
	}

	private boolean selectedAreaIsInfElem(DRLDocument doc,
			PositionInText startPos, PositionInText endPos) {
		PositionInDRL from = doc.findByPosition(startPos);
		PositionInDRL to = doc.findByPosition(endPos);
		boolean selectedBorderInTag = from.isInTag || to.isInTag;
		boolean selectedBorderInText = from.isInText || to.isInText;
		boolean selectedAllElem = to.parent == from.parent
				&& from.next == to.prev;
		LangElem elem;
		try {
			elem = (LangElem) from.next;
			if (elem == null) {
				return false;
			}
		} catch (ClassCastException e) {
			return false;
		}
		boolean selectedIsInfElem = elem.tag.equals(LangElem.INFELEMENT);

		if (!selectedBorderInTag && !selectedBorderInText) {
			if (selectedAllElem) {
				if (selectedIsInfElem) {
					infElementToFindOfClones = elem;
					return true;
				}
			}
		}
		return false;
	}

	private volatile List<IClonesGroup> clonesGroups = null;
	@Override
	public void run() {
		try {
			showView();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		//progress monitor
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask("DocLine clones finding ...", 24);
				clonesGroups = getClonesGroups(monitor);
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
	
	private void showMessage(Exception e) {
		if (e == null){
//			MessageDialog.openInformation(DrlPublisherPlugin.getShell(), "Information", "Export successfull");
		}else
			MessageDialog.openError(DrlPublisherPlugin.getShell(), "Error", "Export failed: " + e.getCause().getMessage());
	}

	private void formatTextInDrlFile(IEditorPart editor2) {
		IFile file = ((FileEditorInput) editor.getEditorInput()).getFile();
		IProject project = file.getProject();
		ProjectContent projectContent = (ProjectContent) ((ProjectRegistryImpl) PLDocToolkitPlugin
				.getRegistry(project.getName())).projectContent;
		projectContent.saveAll();		
	}

	private int textContainsStringWithLengthMore299(String text) {
		StringTokenizer sTok = new StringTokenizer(text, "\n");
		for (int i = 1;sTok.hasMoreTokens();i++){
			if (sTok.nextToken().length()>299){
				return i;
			}
		}
		return -1;
	}

	private List<IClonesGroup> getClonesGroups(IProgressMonitor monitor) {
		CloneFinder cloneFinder = new CloneFinder();
		return cloneFinder.findClones(infElementToFindOfClones, monitor);
	}

	private void showView() throws PartInitException {
		view = (ClonesGroupResultView) getPage().showView(
				"org.spbu.pldoctoolkit.clones.view.ClonesGroupResultView");
	}

	private IWorkbenchPage getPage() {
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		return window.getActivePage();
	}

}
