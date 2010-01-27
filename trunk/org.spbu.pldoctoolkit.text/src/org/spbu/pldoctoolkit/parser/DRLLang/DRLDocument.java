package org.spbu.pldoctoolkit.parser.DRLLang;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public class DRLDocument extends Element{
	public String preface = null;
	public String DRLnsPrefix = "";	
	
	public IFile file;
	
	public DRLDocument(int startLine, int startColumn) {
		this.startLine = startLine;
		this.startColumn = startColumn;		
		this.parent = null;
		this.doc = this;		
	}	
	
	@Override
	public String getTextRepresentation() {
		String res;
		if (preface != null)
			res = preface;
		else
			res = "";
		
		for (Element elem: childs){
			res += elem.getTextRepresentation();
		}
		
		return res;
	}
		
	public PositionInDRL findByPosition(PositionInText posToFind) {
		if (childs != null &&
			childs.size() != 0) 
		{
			PositionInDRL pos = childs.get(0).findByPosition(posToFind);
			if (pos == null)
				if (posToFind.compare(childs.get(0).startPos) == -1)
					return new PositionInDRL(false, false, null, null, childs.get(0), this);
				else 
					return new PositionInDRL(false, false, null, childs.get(0), null, this);
			else
				return pos;
		}
		else
			return null;
	}

	@Override
	public Element clone(Element parent) {
		DRLDocument newDoc = new DRLDocument(startLine, startColumn);
		if (newDoc.getChilds() == null)
			newDoc.childs = new ArrayList<Element>();
		
		for (Element elem: childs){
			newDoc.childs.add(elem.clone(newDoc));
		}
		
		return newDoc;
	}
}
