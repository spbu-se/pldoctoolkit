package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.saxon.expr.PositionRange;

import org.eclipse.ui.IEditorPart;
import org.spbu.pldoctoolkit.dialogs.SelectIntoInfElemDialog;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class SelectIntoInfElem {
	public String elemId, elemName, refId;
	public ProjectContent project;
	public ElementPositionInDRL positionToReplace = new ElementPositionInDRL();
	// public List<PositionInText> cloneFromTexts, cloneToTexts;
	public List<ElementPositionInDRL> cloneOrSimilarElementPositions;
	// public DRLDocument doc;

	private ArrayList<LangElem> infElemRefs = null;

	private boolean isValide = false;
	private boolean wasValidation = false;
	private boolean wasInfElemRefsPrepared = false;
	
	private ExtractElemForDiff refact;

	private String prefex;

	/*
	 * public SelectIntoInfElem( String id, String name, String refId,
	 * ProjectContent project, DRLDocument doc, PositionInText fromText,
	 * PositionInText toText ) { this.elemId = id; this.elemName = name;
	 * this.refId = refId; this.project = project; this.fromText = fromText;
	 * this.toText = toText; this.doc = doc; }
	 */
	public SelectIntoInfElem() {
	}

	public void setPararams(String id, String name, String refId,
			ProjectContent project, DRLDocument doc, PositionInText fromText,
			PositionInText toText) {
		this.elemId = id;
		this.elemName = name;
		this.refId = refId;
		this.project = project;
		System.out.println("Const1");
		System.out.println(project);
		System.out.println(fromText.line);
		System.out.println(fromText.column);
		System.out.println(toText.line);
		System.out.println(toText.column);
		System.out.println(doc);
		this.positionToReplace.setFromText(fromText);
		this.positionToReplace.setToText(toText);
		this.positionToReplace.setDoc(doc);
	}

	/*
	 * public void setPararams(String id, String name, String refId,
	 * ProjectContent project, DRLDocument doc, List<PositionInText> fromTexts,
	 * List<PositionInText> toTexts) { setPararams(id, name, refId, project,
	 * doc, fromTexts.remove(0), toTexts .remove(0)); if (fromTexts.size() ==
	 * toTexts.size()) { cloneOrSimilarElementPositions = new
	 * ArrayList<ElementPositionInDRL>(); for (int i = 0; i < fromTexts.size();
	 * i++) { ElementPositionInDRL eInDRL = new ElementPositionInDRL();
	 * eInDRL.setFromText(fromTexts.get(i)); eInDRL.setToText(toTexts.get(i));
	 * eInDRL.setDoc(doc); cloneOrSimilarElementPositions.add(eInDRL); } } }
	 */

	public void setPararams(String id, String name, String refId,
			ProjectContent project, List<ElementPositionInDRL> elementPositions) {
		this.elemId = id;
		this.elemName = name;
		this.refId = refId;
		System.out.println(id + name +refId);
		this.project = project;
		this.positionToReplace = elementPositions.remove(0);
		System.out.println("Const2");
		System.out.println(project);
		System.out.println(positionToReplace.getFromText().line);
		System.out.println(positionToReplace.getFromText().column);
		System.out.println(positionToReplace.getToText().line);
		System.out.println(positionToReplace.getToText().column);
		System.out.println(positionToReplace.getDoc());
		if (elementPositions.size() > 0) {
			this.cloneOrSimilarElementPositions = elementPositions;
			System.out.println("Const3");
			System.out.println(project);
			System.out.println(cloneOrSimilarElementPositions.get(0)
					.getFromText().line);
			System.out.println(cloneOrSimilarElementPositions.get(0)
					.getFromText().column);
			System.out.println(cloneOrSimilarElementPositions.get(0)
					.getToText().line);
			System.out.println(cloneOrSimilarElementPositions.get(0)
					.getToText().column);
			System.out.println(cloneOrSimilarElementPositions.get(0).getDoc());
		}
	}

	public void setValidationPararams(ProjectContent project, DRLDocument doc,
			PositionInText fromText, PositionInText toText) {
		this.project = project;
		this.positionToReplace.setFromText(fromText);
		this.positionToReplace.setToText(toText);
		this.positionToReplace.setDoc(doc);
	}

	public void setExtractElemForDiffRefact(ExtractElemForDiff ref) {
		this.refact = ref;
	}
	
	/*
	 * public void setClonePositions(List<ClonePositionInDRL>
	 * clonePositionsInDRL) { this.clonePositions = clonePositionsInDRL; }
	 */

	public void reset() {
		wasValidation = false;
		isValide = false;
		wasInfElemRefsPrepared = false;
	}

	private void init() {

		positionToReplace.init();

		if (cloneOrSimilarElementPositions != null) {
			for (int i = 0; i < cloneOrSimilarElementPositions.size(); i++) {
				cloneOrSimilarElementPositions.get(i).init();
			}
		}

	}

	public boolean validate() {
		if (wasValidation)
			return isValide;

		init();

		isValide = positionToReplace.validate();

		if (isValide && cloneOrSimilarElementPositions != null) {
			int i = 0;
			while (isValide && i < cloneOrSimilarElementPositions.size()) {
				isValide = cloneOrSimilarElementPositions.get(i).validate();
				i++;
			}
		}

		wasValidation = true;
		return isValide;
	}

	public void perform() {
		validate();

		/*System.out.println(positionToReplace.getFromIdx());
		System.out.println(positionToReplace.getToIdx());
		System.out.println(positionToReplace.getFrom());
		System.out.println(positionToReplace.getTo());
		System.out.println(positionToReplace.getDoc());
		System.out.println(positionToReplace.getInfElem());*/
		if (isValide) {
			firstPartOfPerform();

			ArrayList<Element> childsToInsert;
			if (refact != null) {
				childsToInsert = refact.setNestsAndReturnElements();
			} else {
			childsToInsert = positionToReplace.getFrom().parent
					.getChilds(positionToReplace.getFromIdx(),
							positionToReplace.getToIdx());
			}

			/*
			 * ArrayList<Element> childsToInsert =
			 * positionToReplace.getFrom().parent.removeChilds(
			 * positionToReplace.getFromIdx(), positionToReplace.getToIdx());
			 * 
			 * if (clonePositions != null) { for (int i = 0; i <
			 * clonePositions.size(); i++) {
			 * clonePositions.get(i).getFrom().parent
			 * .removeChilds(clonePositions.get(i).getFromIdx(),
			 * clonePositions.get(i).getToIdx()); } }
			 */

			LangElem newInfElem = createNewInfElem((LangElem) positionToReplace
					.getFrom().parent, elemId, elemName);
			positionToReplace.getFrom().parent.getDRLDocument().getChilds()
					.get(0).getChilds().add(newInfElem);
			// newInfElem.appendChilds(childsToInsert);
			newInfElem.setChilds(new ArrayList<Element>());
			newInfElem.getChilds().addAll(childsToInsert);

			lastPartOfPerform();

			System.out.println(positionToReplace.getDoc().getTextRepresentation());
			/*
			 * ArrayList<Element> childsToInsert =
			 * pos1.parent.removeChilds(from, to);
			 * DRLdoc.getChilds().get(0).appendChilds(childsToInsert);
			 * 
			 * doc.set(DRLdoc.getTextRepresentation());
			 */
		}
	}

	/*
	 * public void performWithoutCreatingNewElement() { validate();
	 * 
	 * if (isValide) { firstPartOfPerform(); from.parent.removeChilds(fromIdx,
	 * toIdx); lastPartOfPerform(); } }
	 */

	private void firstPartOfPerform() {
		splitIfNecessary();

		prepareNests();

		prefex = positionToReplace.getDoc().DRLnsPrefix;
		if (!prefex.equals(""))
			prefex += ":";

		// String idTofind = ((LangElem)infElem).attrs.getValue("id");
		// for (LangElem infElemRef : project.InfElemRefs) {
		// if (infElemRef.attrs.getValue("infelemid").equals(idTofind))
		getInfElemRefs();
		for (LangElem infElemRef : infElemRefs) {
			String elemRefIdToFind = infElemRef.attrs.getValue("id");
			for (LangElem adapter : project.adapters) {
				if (adapter.attrs.getValue("infelemrefid").equals(
						elemRefIdToFind)) {
					ArrayList<LangElem> nestsRefs = new ArrayList<LangElem>();
					for (Element elem : adapter.getChilds()) {
						if (elem instanceof LangElem) {
							LangElem nestRef = (LangElem) elem;
							String nestid = nestRef.attrs.getValue("nestid");
							if (positionToReplace.getNestsInSelection().get(
									nestid) != null)
								nestsRefs.add(nestRef);
						}
					}

					if (nestsRefs.size() != 0) {
						adapter.getChilds().removeAll(nestsRefs);
						LangElem newAdapter = createNewAdapter(adapter, refId);
						adapter.getParent().getChilds().add(newAdapter);
						newAdapter.setChilds(new ArrayList<Element>());
						newAdapter.getChilds().addAll(nestsRefs);
					}
				}
			}
		}
	}

	private void lastPartOfPerform() {
		System.out.println(positionToReplace.getFrom().parent.getTextRepresentation());
		LangElem newInfElemRef = createNewInfElemRef(
				(LangElem) positionToReplace.getFrom().parent, refId, elemId);

		positionToReplace.getFrom().parent.removeChilds(positionToReplace
				.getFromIdx(), positionToReplace.getToIdx());
		positionToReplace.getFrom().parent.getChilds().add(
				positionToReplace.getFromIdx(), newInfElemRef);

		if (cloneOrSimilarElementPositions != null) {
			for (int i = 0; i < cloneOrSimilarElementPositions.size(); i++) {
				LangElem newInfElemRefClone = createNewInfElemRef(
						(LangElem) cloneOrSimilarElementPositions.get(i)
								.getFrom().parent, refId, elemId);

				cloneOrSimilarElementPositions.get(i).getFrom().parent
						.removeChilds(cloneOrSimilarElementPositions.get(i)
								.getFromIdx(), cloneOrSimilarElementPositions
								.get(i).getToIdx());
				cloneOrSimilarElementPositions.get(i).getFrom().parent
						.getChilds().add(
								cloneOrSimilarElementPositions.get(i)
										.getFromIdx(), newInfElemRefClone);
			}
		}
	}

	public HashMap<String, LangElem> getNestsInSelection() {
		if (positionToReplace.getNestsInSelection() == null)
			prepareNests();

		return positionToReplace.getNestsInSelection();
	}

	private void splitIfNecessary() {
		positionToReplace.splitIfNecessary();
		if (cloneOrSimilarElementPositions != null) {
			for (int i = 0; i < cloneOrSimilarElementPositions.size(); i++) {
				cloneOrSimilarElementPositions.get(i).splitIfNecessary();
			}
		}
	}

	public ArrayList<LangElem> getInfElemRefs() {
		if (!isValide)
			return null;

		if (wasInfElemRefsPrepared)
			return infElemRefs;

		infElemRefs = new ArrayList<LangElem>();

		infElemRefs.addAll(positionToReplace.getInfElemRefs(project));

		if (cloneOrSimilarElementPositions != null) {
			for (int i = 0; i < cloneOrSimilarElementPositions.size(); i++) {
				infElemRefs.addAll(cloneOrSimilarElementPositions.get(i)
						.getInfElemRefs(project));
			}
		}

		wasInfElemRefsPrepared = true;

		return infElemRefs;
	}

	public SelectIntoInfElemDialog createSelectIntoInfElemDialog(
			IEditorPart editor, ProjectContent projectContent) {
		SelectIntoInfElemDialog dialog = new SelectIntoInfElemDialog(editor
				.getSite().getShell());

		for (LangElem refs : projectContent.infElemRefs) {
			dialog.addRefId(refs.attrs.getValue("id"));
		}
		for (LangElem elems : projectContent.infElems) {
			dialog.addId(elems.attrs.getValue("id"));
		}

		return dialog;
	}

	private void prepareNests() {
		positionToReplace.prepareNests();
		if (cloneOrSimilarElementPositions != null) {
			for (int i = 0; i < cloneOrSimilarElementPositions.size(); i++) {
				cloneOrSimilarElementPositions.get(i).prepareNests();
			}
		}
	}

	private LangElem createNewAdapter(LangElem prevAdapter, String infElemRefId) {
		LangElem newAdapter = new LangElem("Adapter", prefex + "Adapter", null,
				prevAdapter.getParent(), prevAdapter.getDRLDocument(),
				new AttributesImpl());
		((AttributesImpl) newAdapter.attrs).addAttribute("infelemrefid",
				"infelemrefid", "infelemrefid", "", infElemRefId);
		return newAdapter;
	}

	private LangElem createNewInfElem(LangElem parent, String id, String name) {
		LangElem newInfElem = new LangElem("InfElement", prefex + "InfElement",
				null, parent, parent.getDRLDocument(), new AttributesImpl());
		((AttributesImpl) newInfElem.attrs).addAttribute("id", "id", "id", "",
				id);
		((AttributesImpl) newInfElem.attrs).addAttribute("name", "name",
				"name", "", name);
		return newInfElem;
	}

	private LangElem createNewInfElemRef(LangElem parent, String id,
			String infelemid) {
		LangElem newInfElem = new LangElem("InfElemRef", prefex + "InfElemRef",
				null, parent, parent.getDRLDocument(), new AttributesImpl());
		((AttributesImpl) newInfElem.attrs).addAttribute("id", "id", "id", "",
				id);
		((AttributesImpl) newInfElem.attrs).addAttribute("infelemid",
				"infelemid", "infelemid", "", infelemid);
		return newInfElem;
	}

	public ElementPositionInDRL getPositionToReplace() {
		return positionToReplace;
	}

}
