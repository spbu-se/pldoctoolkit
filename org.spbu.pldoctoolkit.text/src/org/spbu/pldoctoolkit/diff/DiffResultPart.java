package org.spbu.pldoctoolkit.diff;

import org.eclipse.compare.structuremergeviewer.Differencer;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public class DiffResultPart {

	private int startLineNumber;
	private int endLineNumber;
	//private LangElem infElem;
	private boolean identical;
	
	public DiffResultPart(int startLineNumber) {
		this.startLineNumber = startLineNumber;
		this.identical = true;
	}
	
	public DiffResultPart(int startLineNumber, int endLineNumber,
			/*LangElem infElem,*/ boolean identical) {
		this.startLineNumber = startLineNumber;
		this.endLineNumber = endLineNumber;
		//this.infElem = infElem;
		this.identical = identical;
	}

	public boolean getIdentical() {
		return identical;
	}
	
	public void setIdentical(boolean identical) {
		this.identical = identical;
	}

	public int getStartLineNumber() {
		return startLineNumber;
	}

	public void setStartLineNumber(int startLineNumber) {
		this.startLineNumber = startLineNumber;
	}

	public int getEndLineNumber() {
		return endLineNumber;
	}

	public void setEndLineNumber(int endLineNumber) {
		this.endLineNumber = endLineNumber;
	}

	public boolean isSafe(LangElem infElement) {
		if (endLineNumber == Difference.NONE) {
			return true;
		}
		Element startElem = getElement(infElement, startLineNumber);
		Element endElem = getElement(infElement, endLineNumber);
		System.out.println("ololololo" + startElem.getTextRepresentation() + "olo");
		System.out.println("ololololo2" + endElem.getTextRepresentation() + "2olo");
		boolean isInLangElemTag = false;
		PositionInText posStart = new PositionInText(startLineNumber, 1);
		PositionInDRL start = infElement.getDRLDocument().findByPosition(posStart);
		if (start.isInTag && startElem instanceof LangElem) {
			isInLangElemTag = true;
		}
		boolean notManyChildren = true;
		if ((startElem.getChilds().size() != 0) && ((startElem.getChilds().size() != 1) || !(startElem.getChilds().get(0) instanceof TextElement))) {
				notManyChildren = false;
		}
		return (!isInLangElemTag && notManyChildren && startElem.equals(endElem));
	}
	
	public static Element getElement(LangElem infElement, int localLineNumber) {
		int absoluteLineNumber = localLineNumber + infElement.getTagStartPos().line + 1;
		int absoluteColumnNumber = getTrueLocalColumn(infElement, localLineNumber) + infElement.getTagStartPos().column;
		PositionInText pos = new PositionInText(absoluteLineNumber, absoluteColumnNumber);
		PositionInDRL posDRL = infElement.getDRLDocument().findByPosition(pos);
		Element elem = posDRL.elem != null ? posDRL.elem : posDRL.next;
		//elem = getNotBlanc(elem);
		return elem;
	}
	
	private static int getTrueLocalColumn(LangElem infElement, int localLineNumber) {
		int column = 1;
		String[] lines = infElement.getTextRepresentation().split("\n");
		String str = lines[localLineNumber + 1];
		while (str.charAt(column) == ' ') {
			column++;
		}
		return column;
	}
	
	private static Element getNotBlanc(Element elem) {
		if (!elem.getTextRepresentation().trim().equals("")) {
			return elem;
		}
		return getNotBlanc(getNextByParent(elem));
	}
	
	private static Element getNextByParent(Element elem) {
		if (elem.getNextElementByParent() == null) {
			return getNextByParent(elem.getParent());
		}
		return elem.getNextElementByParent();
	}
	
}
