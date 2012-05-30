package org.spbu.pldoctoolkit.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.clones.ICloneInst;
import org.spbu.pldoctoolkit.clones.IClonesGroup;
import org.spbu.pldoctoolkit.dialogs.SelectIntoInfElemDialog;
import org.spbu.pldoctoolkit.diff.IElementInst;
import org.spbu.pldoctoolkit.diff.IPairGroup;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.refactor.ElementPositionInDRL;
import org.spbu.pldoctoolkit.refactor.ExtractElemForDiff;
import org.spbu.pldoctoolkit.refactor.HandleDIffWithAnotherInfElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.SelectIntoInfElem;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

//this action is a part of find clones in inf elem action
public final class ExtractElemAction extends Action {

	private static String refactName = "Extract as new InfElem";
	private IEditorPart editor;
	//private final IFile file;
	private final IProject project;
	private final ProjectContent projectContent;
	private IPairGroup pairGroupToRefactor;
	SelectIntoInfElem refactSelectIntoInfElem = new SelectIntoInfElem();
	ExtractElemForDiff refact = new ExtractElemForDiff();

	public ExtractElemAction(IPairGroup group, IEditorPart editor) {
		super(refactName);
		this.pairGroupToRefactor = group;
		this.editor = editor;
		//file = ((FileEditorInput) editor.getEditorInput()).getFile();
		project = ((FileEditorInput) editor.getEditorInput()).getFile().getProject();
		projectContent = (ProjectContent) ((ProjectRegistryImpl) PLDocToolkitPlugin
				.getRegistry(project.getName())).projectContent;
		refact.setParams(projectContent);
	}

	public void run() {
		
		SelectIntoInfElemDialog selectIntoInfElemDialog = refactSelectIntoInfElem
				.createSelectIntoInfElemDialog(editor, projectContent);
		int result = selectIntoInfElemDialog.open();
		if (result != Window.OK)
			return;
		
		System.out.println("MOOOOOOOOOOOOOOOOOORE");
		for (Element elem : refact.setNestsAndReturnElements(pairGroupToRefactor)) {
			System.out.println(elem.getTextRepresentation());
		}
		
		List<ElementPositionInDRL> list = new ArrayList<ElementPositionInDRL>();
		list.add(createElementPositionInDRL(pairGroupToRefactor.getFirstInstance()));
		list.add(createElementPositionInDRL(pairGroupToRefactor.getSecondInstance()));
		
		refactSelectIntoInfElem.setPararams(selectIntoInfElemDialog
				.getInfElemId(), selectIntoInfElemDialog.getInfElemName(),
				selectIntoInfElemDialog.getInfElemRefId(), projectContent, list);
		
		/*refactSelectIntoInfElem.setPararams(selectIntoInfElemDialog
				.getInfElemId(), selectIntoInfElemDialog.getInfElemName(),
				selectIntoInfElemDialog.getInfElemRefId(), projectContent, list.get(0).getDoc(), list.get(0).getFromText(), list.get(0).getToText());*/
		refactSelectIntoInfElem.perform();
		projectContent.saveAll();
	}

	private ElementPositionInDRL createElementPositionInDRL(IElementInst inst) {
		ElementPositionInDRL eInDRL = new ElementPositionInDRL();
		eInDRL.setFromText(inst.getAbsoluteStartPos());
		eInDRL.setToText(inst.getAbsoluteEndPos());
		eInDRL.setDoc(inst.getInfElem().getDRLDocument());
		return eInDRL;
	}
	
}