package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class ReplaceWithDirRef {
	
	private LangElem entry;
	private LangElem template; 
		
	public PositionInDRL from, to;
	public PositionInText fromText, toText;
	public DRLDocument doc;
	
	private int fromIdx, toIdx;	
	
	private boolean isValid = false;
	private boolean wasValidation = false;	
	
	private Element parent = null;
	private String prefex;
	
	public ReplaceWithDirRef() {	
	}
	
	public void setPararams(LangElem template,  LangElem entry) {		
		this.entry = entry;
		this.template = template;		
	}
	
	public void setValidationPararams( DRLDocument doc,
							 		   PositionInText fromText, PositionInText toText ) {		
		this.fromText = fromText;
		this.toText = toText;
		this.doc = doc;
	}
	
	public void reset() {
		wasValidation = false;		
		isValid = false;
	}
	
	private void init() {
		// 1.
		from = doc.findByPosition(fromText);
		to = doc.findByPosition(toText);
		
		if (from.isInText)
			fromIdx = from.parent.getChilds().indexOf(from.elem);
		else
			fromIdx = from.parent.getChilds().indexOf(from.next);
		
		if (to.isInText)
			toIdx = from.parent.getChilds().indexOf(to.elem);
		else
			toIdx = from.parent.getChilds().indexOf(to.prev);
		
		// 2.
		if (from.isInTag == true)
			return;
		
		UpwardIterator searchInfElemiterator;
		if (from.isInText)
			searchInfElemiterator = new UpwardIterator(from.elem);
		else if (from.next != null)
			searchInfElemiterator = new UpwardIterator(from.next);
		else
			return;
		
		parent = searchInfElemiterator.next();
		while (parent != null) {				
			if (parent instanceof LangElem) {
				LangElem langElem = (LangElem)parent; 
				if ( langElem.tag.equals("InfElement") ||
					 langElem.tag.equals("Adapter")	)
					break;				
			}
			parent = searchInfElemiterator.next();
		}
	}
	
	public boolean validate() {
		if (wasValidation)
			return isValid;
		
		init();
		
		isValid = true;
		
		// 1. 		
		if (from.parent != to.parent) 
			isValid = false;				
			
		// 2.		
		if (parent == null) 
			isValid = false;
		
		// 3.
		if (from.isInTag == true || to.isInTag == true)
			isValid = false;
		
		// 4.
		for (int i = fromIdx; i <= toIdx; ++i ) {
			TreeIterator iter = new TreeIterator(from.parent.getChilds().get(i));
			while (iter.hasNext()) {
				Element elem = iter.next();
				if (elem instanceof LangElem) {
					for (String tag : LangElem.TAGS)
						if ( ((LangElem)elem).tag.equals(tag) ) {
							isValid = false;
							wasValidation = true;
							return isValid;
						}
				}
			}
		}
	
		wasValidation = true;
		return isValid;
	}
	
	public void perform() {
		validate();
		
		if (isValid){
			splitIfNecessary();
		
			prefex = doc.DRLnsPrefix;
			if (!prefex.equals(""))
				prefex += ":";					
						
			from.parent.removeChilds(fromIdx, toIdx);//getChilds().remove(fromIdx);
			LangElem dirRef = createNewDirRef();			
			from.parent.getChilds().add(fromIdx, dirRef);			
		}		
	}	
	
	private void splitIfNecessary() {
		if (from.isInText) {
			boolean isSame = false;
			if (to.isInText && from.elem == to.elem)
				isSame = true;
			
			((TextElement)from.elem).Split(fromText);
			Element parent = from.elem.getParent(); 
			from = new PositionInDRL(false, false, null, parent.getChilds().get(fromIdx), parent.getChilds().get(fromIdx+1), parent);
			++fromIdx;
			++toIdx;
			
			if (isSame)
				to = doc.findByPosition(toText);
		}
		if (to.isInText) {
			((TextElement)to.elem).Split(toText);
			Element parent = to.elem.getParent(); 
			to = new PositionInDRL(false, false, null, parent.getChilds().get(toIdx), parent.getChilds().get(toIdx+1), parent);			
		}
	}

	private LangElem createNewDirRef() {
		String templateId = template.attrs.getValue(LangElem.ID);
		String entryId = entry.attrs.getValue(LangElem.ID);
		LangElem dirRef = new LangElem("DictRef", prefex + "DictRef", null, from.parent, from.parent.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)dirRef.attrs).addAttribute(LangElem.ENTRYID, LangElem.ENTRYID, LangElem.ENTRYID, "", entryId);
		((AttributesImpl)dirRef.attrs).addAttribute(LangElem.TEMPLATEID, LangElem.TEMPLATEID, LangElem.TEMPLATEID, "", templateId);
		return dirRef;
	}
}
