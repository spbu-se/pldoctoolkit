package org.spbu.pldoctoolkit.parser.DRLLang;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public abstract class Element {
	protected int startLine, startColumn, endLine, endColumn;
	protected PositionInText startPos, endPos, tagStartPos;
	protected ArrayList<Element> childs = new ArrayList<Element>();
	protected Element parent;
	protected DRLDocument doc;

	public int offsetInTextRepresentation;
	
	public String toString(){
		return "[pos:"+startLine+"."+startColumn+"-"+endLine+"."+endColumn 
		+ " startPos:"+startPos+" endPos:"+endPos+ " tagStartPos:" + tagStartPos+"]";
	}

	/*
	 * public int getStartLine() { return startLine; }
	 * 
	 * public void setStartLine(int startLine) { this.startLine = startLine; }
	 * 
	 * ////////////////////////////////////////////////////////////
	 * 
	 * public int getStartColumn() { return startColumn; }
	 * 
	 * public void setStartColumn(int startColumn) { this.startColumn =
	 * startColumn; }
	 * 
	 * /////////////////////////////////////////////////////////////
	 * 
	 * public int getEndLine() { return startLine; }
	 * 
	 * public void setEndLine(int endLine) { this.endLine = endLine; }
	 * 
	 * /////////////////////////////////////////////////////////////
	 * 
	 * public int getEndColumn() { return startColumn; }
	 * 
	 * public void setEndColumn(int endColumn) { this.endColumn = endColumn; }
	 */
	// ///////////////////////////////////////////////////////////
	public PositionInText getStartPos() {
		return startPos;
	}

	public void setStartPos(PositionInText startPos) {
		this.startPos = startPos;
	}

	// ///////////////////////////////////////////////////////////

	public PositionInText getEndPos() {
		return endPos;
	}

	public void setEndPos(PositionInText endPos) {
		this.endPos = endPos;
	}

	// //////////////////////////////////////////////////////////

	public PositionInText getTagStartPos() {
		return tagStartPos;
	}

	public void setTagStartPos(PositionInText tagStartPos) {
		this.tagStartPos = tagStartPos;
	}

	// //////////////////////////////////////////////////////////
	public Element getParent() {
		return parent;
	}

	public DRLDocument getDRLDocument() {
		return doc;
	}

	// ///////////////////////////////////////////////////////////

	public ArrayList<Element> getChilds() {
		return childs;
	}

	public void setChilds(ArrayList<Element> childs) {
		this.childs = childs;
	}

	// ///////////////////////////////////////////////////////////

	public PositionInDRL findByPosition(PositionInText posToFind) {
		// if (startPos.compare(posToFind) == 1 || endPos.compare(posToFind) <=
		// 0)
		// return null;
		PositionInText endTagStartPos;
		if (childs == null || childs.size() == 0) {
			// endTagStartPos = startPos;
			// if (startPos.compare(posToFind) == 1 ||
			// endTagStartPos.compare(posToFind) == -1)
			return new PositionInDRL(false, true, this, null, null, parent);
		} else {
			endTagStartPos = childs.get(childs.size() - 1).endPos;
			if (startPos.compare(posToFind) == 1
					|| endTagStartPos.compare(posToFind) == -1)
				return new PositionInDRL(false, true, this, null, null, parent);
		}

		// if (childs != null &&
		// childs.size() != 0) // �� ������ ������
		{
			PositionInText prevPos = new PositionInText(startPos);
			Element prevChild = null;
			for (int i = 0; i < childs.size(); ++i) {
				Element curChild = childs.get(i);
				if (curChild.endPos.compare(posToFind) == 1) {
					if (curChild.tagStartPos.compare(posToFind) == 0)
						return new PositionInDRL(false, false, null, prevChild,
								curChild, this);
					else
						return curChild.findByPosition(posToFind);
				}
				/*
				 * PositionInDRL pos = curChild.findByPosition(posToFind); if
				 * (pos != null) return pos;//break; else { if
				 * (curChild.getStartPos().compare(posToFind) == 1) { //
				 * *<a><a/> Element prev = i > 0 ? childs.get(i - 1) : null;
				 * return new PositionInDRL(false, false, null, prev, curChild,
				 * this); } }
				 */
				prevChild = curChild;
			}
			return new PositionInDRL(false, false, null, childs.get(childs
					.size() - 1), null, this);
		}
		// else
		// return new PositionInDRL(false, false, null, null, null, this);
	}

	public ArrayList<Element> removeChilds(int from, int to) {
		ArrayList<Element> res = new ArrayList<Element>();
		for (int i = from; i <= to; ++i)
			res.add(childs.remove(from));

		return res;
	}

	public void appendChilds(ArrayList<Element> childsToInsert) {
		// for (int i = 0; i < childsToInsert.size(); ++i)
		if (childs == null)
			childs = new ArrayList<Element>();
		childs.addAll(childsToInsert);
	}

	public int getChildIndex(Element elem) {
		if (parent != null) {
			return parent.getChilds().indexOf(elem);
		}
		return -1;
	}

	public Element getNextElementByParent() {
		int index = getChildIndex(this);
		if (index + 1 < parent.getChilds().size()) {
			return parent.getChilds().get(index + 1);
		}
		return null;
	}

	public Element getPrevElementByParent() {
		int index = getChildIndex(this);
		if (index - 1 > -1) {
			return parent.getChilds().get(index - 1);
		}
		return null;
	}
	
	public boolean containsOrEquals(Element elem) {
		return equals(elem) || contains(elem);
	}

	public boolean contains(Element elem) {
		if (childs.contains(elem)) {
			return true;
		} else {
			for (Element child : childs) {
				if (child.contains(elem)) {
					return true;
				}
			}
		}
		return false;
	}

	public abstract String getTextRepresentation();

	public abstract Element clone(Element parent);

	// public abstract ArrayList<PatternElement> getPatternElements();
}
