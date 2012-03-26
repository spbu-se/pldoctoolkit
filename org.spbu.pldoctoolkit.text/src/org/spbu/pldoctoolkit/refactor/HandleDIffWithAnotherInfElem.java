package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

public class HandleDIffWithAnotherInfElem {

	public PositionInText startPosition, endPosition;
	public DRLDocument doc;
	private LangElem firstInfElementToCompare;
	
	public HandleDIffWithAnotherInfElem() {
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
		/*System.out.println("from.isInTag " + from.isInTag);
		System.out.println("from.isInText " + from.isInText);
		System.out.println("from.elem " + from.elem);
		//System.out.println("from.elem " + from.elem.getTextRepresentation());
		System.out.println("from.prev " + from.prev.getTextRepresentation());
		System.out.println("from.next " + from.next.getTextRepresentation());
		System.out.println("from.parent " + from.parent.getTextRepresentation());
		System.out.println("to.isInTag " + to.isInTag);
		System.out.println("to.isInText " + to.isInText);
		System.out.println("to.elem " + to.elem);
		//System.out.println("to.elem " + to.elem.getTextRepresentation());
		System.out.println("to.prev " + to.prev.getTextRepresentation());
		System.out.println("to.next " + to.next.getTextRepresentation());
		System.out.println("to.parent " + to.parent.getTextRepresentation());*/
		boolean selectedBorderInTag = from.isInTag || to.isInTag;
		boolean selectedBorderInText = from.isInText || to.isInText;
		boolean selectedAllElem = to.parent == from.parent && from.next == to.prev;
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
			if (selectedAllElem) {
				if (selectedIsInfElem) {
					firstInfElementToCompare = elem;
					return true;
				}
			}
		}
		return false;
	}
	
	public void setValidationParams(DRLDocument doc, PositionInText startPosition, PositionInText endPosition) {
		this.doc = doc;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}

	public LangElem getFirstInfElementToCompare() {
		return firstInfElementToCompare;
	}
	
}
