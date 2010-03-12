package org.spbu.pldoctoolkit.refactor;

import java.util.Collection;
import java.util.Stack;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.xml.sax.helpers.AttributesImpl;


public class Rename {
	
	PositionInDRL from, to;
	public PositionInText startPosition, endPosition;
	public DRLDocument doc;
	public Collection<DRLDocument> allDocs;
	
	public LangElem targetElem;
	
	public String oldName, newName;
	public String elementType;
	
	public static final String allowedToRename[] = {
		LangElem.ATTR,
		LangElem.DIRECTORY,
		LangElem.DICTIONARY,
		LangElem.ENTRY,
		LangElem.INFELEMENT,
		LangElem.INFELEMREF,
		LangElem.INFPRODUCT,
		LangElem.DIRTEMPLATE,
		LangElem.NEST
	};
	
	public Rename (){
	}

	public boolean validate() {
		from = doc.findByPosition(startPosition);
		to = doc.findByPosition(endPosition);
		boolean selectedBorderInTag = from.isInTag || to.isInTag;
		boolean selectedAllElem = to.parent == from.parent && from.next == to.prev;
		boolean selectedFstLine = to.parent == from.next;
		
		if (selectedBorderInTag)
			return false;
		if (selectedAllElem || selectedFstLine){
			targetElem = (LangElem) from.next;
			elementType = targetElem.tag;
			oldName = targetElem.attrs.getValue("id");
			for(String s: allowedToRename)
			{
				if(elementType.equals(s))
					return true;
			}
		}
		return false;
	}

	public void setValidationParams(DRLDocument doc,
			PositionInText startPosition, PositionInText endPosition) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.doc = doc;
	}

	public void setParams(String value, Collection<DRLDocument> docs) {
		this.newName = value;
		allDocs = docs;
	}
	
	public void perform() {
		//rename element 
		renameAttribute(targetElem,"id");
		
		//rename all uses of element
		for(DRLDocument doc : allDocs) {
			Stack<Element> elements = new Stack<Element>();
			elements.add(doc);
			while(!elements.isEmpty())
			{
				Element currentElem = elements.pop();
				if(currentElem.getTextRepresentation().contains(oldName))
				{
					elements.addAll(currentElem.getChilds());
					boolean isLangElem = currentElem.getClass() == LangElem.class;
					if(isLangElem)
					   renameAttribute((LangElem) currentElem, LangElem.getAttributeName(elementType));
				}
			}
		}
	}
	
	public void renameAttribute(LangElem elem, String attributeName) {
		AttributesImpl newAttr = (AttributesImpl) elem.attrs;
		int renameIdx = newAttr.getIndex(attributeName);
		if(renameIdx != -1 && newAttr.getValue(renameIdx).equals(oldName)) {
			newAttr.setValue(renameIdx, newName);
			elem.attrs = new AttributesImpl(newAttr);
		}
	}
}

