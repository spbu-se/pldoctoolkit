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
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.refactor.SelectIntoInfElem;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;

//this action is a part of find clones in inf elem action
public final class ExtractAsNewInfElem extends Action {

	private static String refactName = "Extract as new InfElem";
	private IEditorPart editor;
	private final IFile file;
	private final IProject project;
	private final ProjectContent projectContent;
	private IClonesGroup cloneGroupToRefactor;
	SelectIntoInfElem refactSelectIntoInfElem = new SelectIntoInfElem();

	public ExtractAsNewInfElem(IClonesGroup group, IEditorPart editor) {
		super(refactName);
		this.cloneGroupToRefactor = group;
		this.editor = editor;
		file = ((FileEditorInput) editor.getEditorInput()).getFile();
		project = file.getProject();
		projectContent = (ProjectContent) ((ProjectRegistryImpl) PLDocToolkitPlugin
				.getRegistry(project.getName())).projectContent;
	}

	public void run() {
		/*
		 * for (ICloneInst inst : cloneGroupToRefactor.getInstances()) {
		 * System.out.println("uhahaha" + inst.getCloneText()); }
		 */
		SelectIntoInfElemDialog selectIntoInfElemDialog = refactSelectIntoInfElem
				.createSelectIntoInfElemDialog(editor, projectContent);
		int result = selectIntoInfElemDialog.open();
		if (result != Window.OK)
			return;

		/*// выносим первый клон в инф элемент
		ICloneInst firstInstance = cloneGroupToRefactor.getInstances()
				.remove(0);
		DRLDocument DRLdoc = projectContent.DRLDocs.get(file);
		refactSelectIntoInfElem.setPararams(selectIntoInfElemDialog
				.getInfElemId(), selectIntoInfElemDialog.getInfElemName(),
				selectIntoInfElemDialog.getInfElemRefId(), projectContent,
				DRLdoc, firstInstance.getAbsoluteStartPos(), firstInstance
						.getAbsoluteEndPos());
		refactSelectIntoInfElem.perform();
		projectContent.saveAll();*/

		
		DRLDocument DRLdoc = projectContent.DRLDocs.get(file);
		List<PositionInText> l1 = new ArrayList<PositionInText>();
		List<PositionInText> l2 = new ArrayList<PositionInText>();
		for (ICloneInst inst : cloneGroupToRefactor.getInstances()) {
			l1.add(inst.getStartPos4EntireDocument());
			l2.add(inst.getEndPos4EntireDocument());
		}
		refactSelectIntoInfElem.setPararams(selectIntoInfElemDialog
				.getInfElemId(), selectIntoInfElemDialog.getInfElemName(),
				selectIntoInfElemDialog.getInfElemRefId(), projectContent,
				DRLdoc, l1, l2);
		refactSelectIntoInfElem.perform();
		projectContent.saveAll();
		
		/*// заменяем остальные клоны ссылкой на новый инф элемент
		for (ICloneInst inst : cloneGroupToRefactor.getInstances()) {
			refactSelectIntoInfElem.reset();
			refactSelectIntoInfElem.setPararams(selectIntoInfElemDialog
					.getInfElemId(), selectIntoInfElemDialog.getInfElemName(),
					selectIntoInfElemDialog.getInfElemRefId(), projectContent,
					DRLdoc, inst.getAbsoluteStartPos(), inst
							.getAbsoluteEndPos());
			refactSelectIntoInfElem.performWithoutCreatingNewElement();
		}*/

		/*// работаем с адаптерами в конечном информационном продукте
		for (ICloneInst inst : cloneGroupToRefactor.getInstances()) {
			refactSelectIntoInfElem.reset();
			refactSelectIntoInfElem.setPararams(selectIntoInfElemDialog
					.getInfElemId(), selectIntoInfElemDialog.getInfElemName(),
					selectIntoInfElemDialog.getInfElemRefId(), projectContent,
					DRLdoc, inst.getAbsoluteStartPos(), inst
							.getAbsoluteEndPos());
			refactSelectIntoInfElem.firstPartOfPerform();
		}

		// создаём новый инф элемент
		refactSelectIntoInfElem.reset();
		refactSelectIntoInfElem.setPararams(selectIntoInfElemDialog
				.getInfElemId(), selectIntoInfElemDialog.getInfElemName(),
				selectIntoInfElemDialog.getInfElemRefId(), projectContent,
				DRLdoc, cloneGroupToRefactor.getInstances().get(0)
						.getAbsoluteStartPos(), cloneGroupToRefactor
						.getInstances().get(0).getAbsoluteEndPos());
		refactSelectIntoInfElem.fdfddffirstPartOfPerform();

		// проставляем ссылки на новый инф элемент
		for (ICloneInst inst : cloneGroupToRefactor.getInstances()) {
			refactSelectIntoInfElem.reset();
			refactSelectIntoInfElem.setPararams(selectIntoInfElemDialog
					.getInfElemId(), selectIntoInfElemDialog.getInfElemName(),
					selectIntoInfElemDialog.getInfElemRefId(), projectContent,
					DRLdoc, inst.getAbsoluteStartPos(), inst
							.getAbsoluteEndPos());
			refactSelectIntoInfElem.lastPartOfPerform();
		}*/
		
		//projectContent.saveAll();
	}

}