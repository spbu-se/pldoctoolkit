package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;
import java.util.List;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.xml.sax.helpers.AttributesImpl;

public class HandleDIffWithAnotherInfElem {

	public PositionInText startPosition, endPosition;
	public DRLDocument doc;
	private LangElem firstInfElementToCompare;
	private LangElem secondInfElementToCompare;

	private List<Element> diffElemsToReplace;
	private List<String> nestIds;

	public HandleDIffWithAnotherInfElem() {
		nestIds = new ArrayList<String>();
	}

	public static ArrayList<DRLDocument> getPossibleDocs(ProjectContent proj) {
		ArrayList<DRLDocument> docs = new ArrayList<DRLDocument>();

		for (DRLDocument doc : proj.DRLDocs.values()) {
			LangElem le = (LangElem) doc.getChilds().get(0);
			if (le.tag == "DocumentationCore")
				docs.add(doc);
		}

		return docs;
	}

	public boolean validate() {
		PositionInDRL from = doc.findByPosition(startPosition);
		PositionInDRL to = doc.findByPosition(endPosition);
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
					firstInfElementToCompare = elem;
					return true;
				}
			}
		}
		return false;
	}

	public void replaceNests(ProjectContent project, String elemRefIdToFind) {
		
		String prefix = doc.DRLnsPrefix;
		if (!prefix.equals(""))
			prefix += ":";
		
		LangElem adapter = null;
		for (LangElem oldAdapter : project.adapters) {
			if (oldAdapter.attrs.getValue("infelemrefid").equals(
					elemRefIdToFind)) {
				adapter = oldAdapter;
			}
		}
		
		//create new adapter
		if (adapter == null) {
			// TODO: get parent instead of firstInfElementToCompare.getParent()
			adapter = new LangElem(LangElem.ADAPTER, prefix + "Adapter", null,
					firstInfElementToCompare.getParent(), doc,
					new AttributesImpl());
			((AttributesImpl) adapter.attrs).addAttribute("infelemrefid",
					"infelemrefid", "infelemrefid", "", elemRefIdToFind);
			adapter.setChilds(new ArrayList<Element>());
			firstInfElementToCompare.getParent().getChilds().add(adapter);
		}
		
		int i = 0;
		for (String id : nestIds) {
			LangElem replaceNest = new LangElem(LangElem.REPLACENEST, prefix + LangElem.REPLACENEST, null, adapter, doc, new AttributesImpl());
			((AttributesImpl)replaceNest.attrs).addAttribute(LangElem.NESTID, LangElem.NESTID, LangElem.NESTID, "", id);
			
			replaceNest.setChilds(new ArrayList<Element>());
			replaceNest.getChilds().add(diffElemsToReplace.get(i));
			adapter.getChilds().add(replaceNest);
			i++;
		}
	}

	private void foundDiffElemsToReplace() {
		diffElemsToReplace = new ArrayList<Element>();

		PositionInText p1 = null;
		PositionInText p2 = null;
		PositionInDRL from = doc.findByPosition(p1);
		PositionInDRL to = doc.findByPosition(p2);

	}

	private Element getElemContainingDiff(PositionInDRL from, PositionInDRL to) {
		Element res = from.elem != null ? from.elem : from.next;
		Element end = to.elem != null ? to.elem : to.prev;
		Element tmp = res;
		while (!tmp.containsOrEquals(end)) {
			res = tmp.getParent();
			Element next = tmp.getNextElementByParent();
			if (next == null) {
				tmp = res;
			} else {
				tmp = next;
			}
		}
		return res;
	}

	public void setValidationParams(DRLDocument doc,
			PositionInText startPosition, PositionInText endPosition) {
		this.doc = doc;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}

	public PositionInText getStartPosition() {
		return startPosition;
	}

	public PositionInText getEndPosition() {
		return endPosition;
	}

	public LangElem getFirstInfElementToCompare() {
		return firstInfElementToCompare;
	}

	public LangElem getSecondInfElementToCompare() {
		return secondInfElementToCompare;
	}

	public List<Element> getDiffElemsToReplace() {
		return diffElemsToReplace;
	}

	public void setSecondInfElementToCompare(LangElem secondInfElementToCompare) {
		this.secondInfElementToCompare = secondInfElementToCompare;
	}
	
	public void addNestId(String id) {
		nestIds.add(id);
	}

}
