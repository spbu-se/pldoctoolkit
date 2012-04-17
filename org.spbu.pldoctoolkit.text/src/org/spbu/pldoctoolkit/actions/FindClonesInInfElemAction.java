package org.spbu.pldoctoolkit.actions;

import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.clones.IClonesGroup;
import org.spbu.pldoctoolkit.clones.view.ClonesGroupResultView;
import org.spbu.pldoctoolkit.editors.DrlTextEditor;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;
import org.spbu.pldoctoolkit.clones.*;

public final class FindClonesInInfElemAction extends Action implements
		IValidateDRLSelection {

	private static String refactName = "Find clones in InfElem";
	private final DrlTextEditor drlTextEditor;
	private final FileEditorInput editorInput;
	private final IProject project;
	private final ProjectContent projectContent;
	private ClonesGroupResultView view;
	private LangElem infElementToFindOfClones;

	public FindClonesInInfElemAction(DrlTextEditor drlTextEditor) {
		super(refactName);
		this.drlTextEditor = drlTextEditor;
		editorInput = (FileEditorInput) drlTextEditor.getEditorInput();
		project = editorInput.getFile().getProject();
		projectContent = (ProjectContent) ((ProjectRegistryImpl) PLDocToolkitPlugin
				.getRegistry(project.getName())).projectContent;
		drlTextEditor.getMenuListener().addListener(this);
	}

	@Override
	public void validateSelection(IEditorPart part) {
		ISelection sel = drlTextEditor.getSelectionProvider().getSelection();
		TextSelection ts = (TextSelection) sel;

		IDocument doc = drlTextEditor.getDocumentProvider().getDocument(
				editorInput);
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
		} catch (Exception e) {
			setEnabled(false);
			e.printStackTrace();
		}
		setEnabled(true);
	}

	private boolean selectedAreaIsInfElem(DRLDocument doc,
			PositionInText startPos, PositionInText endPos) {
		PositionInDRL from = doc.findByPosition(startPos);
		boolean selectedBorderInTag = from.isInTag;
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

		if (!selectedBorderInTag && !selectedBorderInTag) {
			if (selectedIsInfElem) {
				infElementToFindOfClones = elem;
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		try {
			showView();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		List<IClonesGroup> clonesGroups = getClonesGroups();
		view.setContent(clonesGroups);
	}

	private List<IClonesGroup> getClonesGroups() {
		CloneFinder cloneFinder = new CloneFinder();
		return cloneFinder.findClones(infElementToFindOfClones);
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
