package org.spbu.pldoctoolkit.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IFile;
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
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
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

	@Override
	public void run() {
		try {
			showView();
		} catch (PartInitException e) {
			e.printStackTrace();
		}
//		int lineOfBadString = textContainsStringWithLengthMore299(infElementToFindOfClones.getTextRepresentation());
//		if (lineOfBadString !=-1){
//			System.out.println("Bad string line+"+lineOfBadString);
//		}
//		System.out.println("infElText:\n"+infElementToFindOfClones.getTextRepresentation());
		List<IClonesGroup> clonesGroups = getClonesGroups();
		view.setContent(clonesGroups, editor);
//		view.setContent(specifyClonesGroups(clonesGroups), editor);
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

	private List<IClonesGroup> getClonesGroups() {
		CloneFinder cloneFinder = new CloneFinder();
		return cloneFinder.findClones(infElementToFindOfClones);
	}

//	private List<IClonesGroup> specifyClonesGroups(List<IClonesGroup> groups) {
//		List<IClonesGroup> specifiedGroups = new ArrayList<IClonesGroup>(groups
//				.size());
//		DRLDocument doc = projectContent.DRLDocs.get(editorInput.getFile());
//		for (IClonesGroup group : groups) {
//			IClonesGroup specifiedGroup = new ClonesGroupImpl();
//			for (ICloneInst inst : group.getInstances()) {
//				System.out.println("BEGIN " + inst.getCloneText() + " END");
//				PositionInDRL from = doc.findByPosition(inst.getStartPos4EntireDocument());
//				PositionInDRL to = doc.findByPosition(inst.getEndPos4EntireDocument());
//				int start = inst.getStartPos4EntireDocument().column;
//				int end = inst.getEndPos4EntireDocument().column;
//				boolean wasInLoop = false;
//				while (from.isInTag) {
//					wasInLoop = true;
//					start++;
//					from = doc.findByPosition(new PositionInText(inst.getStartPos4EntireDocument().line, start));
//				}
//				if (wasInLoop) {
//					from = doc.findByPosition(new PositionInText(inst.getStartPos4EntireDocument().line, start + 1));
//				}
//				Element first = from.elem != null ? from.elem : from.next;
//				wasInLoop = false;
//				while (to.isInTag) {
//					wasInLoop = true;
//					end--;
//					to = doc.findByPosition(new PositionInText(inst.getEndPos4EntireDocument().line, end));
//				}
//				if (wasInLoop) {
//					to = doc.findByPosition(new PositionInText(inst.getEndPos4EntireDocument().line, end - 1));
//				}
//				Element last = to.elem != null ? to.elem : to.prev;
//
//				//System.out.println(first.getTextRepresentation());
//				//System.out.println(last.getTextRepresentation());
//				ICloneInst specifiedInstance = new DRLCloneInstImpl(inst
//						.getInfEl(), first.getStartPos(), new PositionInText(last.getEndPos().line, last.getEndPos().column - 1), inst.getCloneText());
//				specifiedGroup.addCloneInst(specifiedInstance);
//			}
//			if (!specifiedGroup.getInstances().isEmpty()) {
//				specifiedGroups.add(specifiedGroup);
//			}
//		}
//		return specifiedGroups;
//	}
	
	/*private List<IClonesGroup> specifyClonesGroups(List<IClonesGroup> groups) {
		List<IClonesGroup> specifiedGroups = new ArrayList<IClonesGroup>(groups
				.size());
		DRLDocument doc = projectContent.DRLDocs.get(editorInput.getFile());
		for (IClonesGroup group : groups) {
			IClonesGroup specifiedGroup = new ClonesGroupImpl();
			for (ICloneInst inst : group.getInstances()) {
				PositionInDRL from = doc.findByPosition(inst
						.getAbsoluteStartPos());
				PositionInDRL to = doc.findByPosition(inst.getAbsoluteEndPos());
				Element first = from.elem != null ? from.elem : from.next;
				Element last = to.elem != null ? to.elem : to.prev;
				System.out.println("first " + first.getTextRepresentation()
						+ ".");
				System.out
						.println("last " + last.getTextRepresentation() + ".");
				if ((first.getEndPos().compare(inst.getAbsoluteEndPos()) > 0)
						|| (first.getStartPos().compare(
								inst.getAbsoluteStartPos()) < 0)) {
					System.out.println("truuueee1");
					System.out.println(first.getEndPos());
					System.out.println(inst.getAbsoluteEndPos());
					System.out.println(first.getStartPos());
					System.out.println(inst.getAbsoluteStartPos());
					if (first.getChilds() != null && !first.getChilds().isEmpty()) {
						System.out.println("truuueee1'");
						first = first.getChilds().get(0);
					} else {
						System.out.println("truuueee1''");
						first = first.getNextElementByParent();
					}
				}
				if ((last.getEndPos().compare(inst.getAbsoluteEndPos()) > 0)
						|| (last.getStartPos().compare(
								inst.getAbsoluteStartPos()) < 0)) {
					System.out.println("truuueee2");
					if (last.getChilds() != null && !last.getChilds().isEmpty()) {
						System.out.println("truuueee2'");
						for (Element elem : last.getChilds()) {
							System.out.println("child " + elem.getTextRepresentation());
						}
						last = last.getChilds().get(last.getChilds().size() - 1);
					} else {
						System.out.println("truuueee2''");
						last = last.getPrevElementByParent();
					}
				}

				System.out.println("first2 " + first.getTextRepresentation()
						+ ".");
				System.out
						.println("last2 " + last.getTextRepresentation() + ".");
				
				int lineFirst = first.getTagStartPos().line
						- inst.getInfEl().getTagStartPos().line;
				System.out.println("offset " + inst.getInfEl().offsetInTextRepresentation);
				System.out.println("lineFirst " + lineFirst);
				System.out.println("first.getStartPos().column " + first.getStartPos().column);
				System.out.println("inst.getInfEl().getStartPos().column " + inst.getInfEl().getStartPos().column);
				int columnFirst = first.getTagStartPos().column
						- inst.getInfEl().getTagStartPos().column;
				System.out.println("columnFirst " + columnFirst);
				PositionInText firstPos = new PositionInText(lineFirst,
						columnFirst);

				int lineLast = last.getTagStartPos().line
						- inst.getInfEl().getTagStartPos().line;
				System.out.println("lineLast " + lineLast);
				int columnLast = last.getTagStartPos().column
						- inst.getInfEl().getTagStartPos().column;
				System.out.println("columnLast " + columnLast);
				PositionInText lastPos = new PositionInText(lineLast,
						columnLast);
				// TODO: сделать разбиение на несколько групп

				System.out.println(firstPos);
				System.out.println(lastPos);
				String cloneText = "";
				Element tmp = first;
				while (tmp != last && tmp != null) {
					cloneText += tmp.getTextRepresentation();
					System.out.println(cloneText);
					tmp = tmp.getNextElementByParent();
				}
				System.out.println(cloneText);

				ICloneInst specifiedInstance = new CloneInstImpl(inst
						.getInfEl(), firstPos, lastPos, cloneText);
				specifiedGroup.addCloneInst(specifiedInstance);
			}
			if (!specifiedGroup.getInstances().isEmpty()) {
				specifiedGroups.add(specifiedGroup);
			}
		}
		return specifiedGroups;
	}*/

	// private Element recursivelyGetNextElement(Element e)
	/*
	 * private ICloneInst getSpecifiedCloneInst(PositionInText start,
	 * PositionInText end, DRLDocument doc) { ICloneInst instance; PositionInDRL
	 * from = doc.findByPosition(start); PositionInDRL to =
	 * doc.findByPosition(end); Element first = from.elem != null ? from.elem :
	 * from.next; Element last = to.elem != null ? to.elem : to.prev; if
	 * ((first.getEndPos().compare(end) < 0) &&
	 * (last.getStartPos().compare(start) >= 0)) { if (first.getParent() ==
	 * last.getParent()) { instance = new CloneInstImpl() } else { //TODO:
	 * сделать разбиение на несколько групп } } return instance; }
	 */

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
