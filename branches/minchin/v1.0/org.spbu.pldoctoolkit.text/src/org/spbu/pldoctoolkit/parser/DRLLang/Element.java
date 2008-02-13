package org.spbu.pldoctoolkit.parser.DRLLang;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public abstract class Element {
	protected int startLine, startColumn, endLine, endColumn;
	protected PositionInText startPos, endPos, tagStartPos;
	protected ArrayList<Element> childs = null;
	protected Element parent;
	protected DRLDocument doc;	
/*	
	public int getStartLine()	{
		return startLine;
	}
	
	public void setStartLine(int startLine)	{
		this.startLine = startLine;
	}	
	
	////////////////////////////////////////////////////////////
	
	public int getStartColumn()	{
		return startColumn;
	}
	
	public void setStartColumn(int startColumn)	{
		this.startColumn = startColumn;		
	}	
	
	/////////////////////////////////////////////////////////////
	
	public int getEndLine()	{
		return startLine;
	}
	
	public void setEndLine(int endLine)	{
		this.endLine = endLine;
	}
	
	/////////////////////////////////////////////////////////////
	
	public int getEndColumn()	{
		return startColumn;
	}
	
	public void setEndColumn(int endColumn)	{
		this.endColumn = endColumn;
	}
*/
	/////////////////////////////////////////////////////////////
	
	public PositionInText getStartPos()	{
		return startPos;		
	}
	
	public void setStartPos(PositionInText startPos)	{
		this.startPos = startPos;		
	}	
	
	/////////////////////////////////////////////////////////////
	
	public PositionInText getEndPos()	{
		return endPos;
	}
	
	public void setEndPos(PositionInText endPos)	{
		this.endPos = endPos;
	}
	
	////////////////////////////////////////////////////////////
	
	public PositionInText getTagStartPos()	{
		return tagStartPos;
	}
	
	public void setTagStartPos(PositionInText tagStartPos)	{
		this.tagStartPos = tagStartPos;
	}
	
	////////////////////////////////////////////////////////////
	public Element getParent()	{
		return parent;
	}
	
	public DRLDocument getDRLDocument() {
		return doc;
	}
	
	/////////////////////////////////////////////////////////////
	
	public ArrayList<Element> getChilds()	{
		return childs;
	}
	
	public void setChilds(ArrayList<Element> childs)	{
		this.childs = childs;
	}	
	
	/////////////////////////////////////////////////////////////
	
	public PositionInDRL findByPosition(PositionInText posToFind) {
		if (startPos.compare(posToFind) == 1 || endPos.compare(posToFind) <= 0)
			return null;
		
		if (childs != null && 
			childs.size() != 0) // на всякий случай
		{
			PositionInText prevPos = new PositionInText(startPos);
			for (int i = 0; i < childs.size(); ++i) {
				Element curChild = childs.get(i);
				PositionInDRL pos = curChild.findByPosition(posToFind);
				if (pos != null)
					return pos;//break;
				else {
					if (/* prevPos.compare(posToFind) == && */curChild.getStartPos().compare(posToFind) == 1) { // *<a><a/>
						Element prev = i > 0 ? childs.get(i - 1) : null;
						return new PositionInDRL(false, false, null, prev, curChild, this);
					}
				}
			}
			return new PositionInDRL(false, false, null, childs.get(childs.size()), null, this);
		}
		else{
			return new PositionInDRL(false, false, null, null, null, this);
		}			
	}
	
	public abstract String getTextRepresentation();	
}
