package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class SearchDictEntry {
	private ReplaceWithDictRef replaceWithDictRef = new ReplaceWithDictRef();
	
	public DRLDocument doc;
	public PositionInText entryPos;
	private PositionInDRL entryPosInDRL;
	
	//public String entryId;
	
	private LangElem dict;
	private LangElem entry;
	
	public PositionInText fromText, toText;
	//public DRLDocument
		
	//private int fromIdx, toIdx;	
	
	private boolean isValide = false;
	private boolean wasValidation = false;
	
	private boolean isSelectionValide = false;
	
	public SearchDictEntry() {	
	}
	
	public void setPararams( PositionInText fromText, PositionInText toText ) {		
		this.fromText = fromText;
		this.toText = toText;
		
		replaceWithDictRef.setValidationPararams(doc, fromText, toText);
		replaceWithDictRef.reset();		
	}
	
	public void setValidationPararams( DRLDocument doc, PositionInText entryPos ) {		
		this.entryPos = entryPos;		
		this.doc = doc;		
	}
		
	public void reset() {
		wasValidation = false;		
		isValide = false;
	}
	
	private void init() {
		// 1.
		entryPosInDRL = doc.findByPosition(entryPos);		
		
		// 2.
		entry = null;
		if (entryPosInDRL.isInTag && entryPosInDRL.elem instanceof LangElem && 
			((LangElem)entryPosInDRL.elem).tag == "Entry") 
			entry = (LangElem)entryPosInDRL.elem;
		else if (entryPosInDRL.parent instanceof LangElem && 
				((LangElem)entryPosInDRL.parent).tag == "Entry" )
			entry = (LangElem)entryPosInDRL.parent;
		
		if (entry == null)
			return;
		
		// 3.
		dict = (LangElem)entry.getParent();		
	}
	
	public boolean validate() {
		if (wasValidation)
			return isValide;
		
		init();
		
		isValide = true;
		
		// 1. 		
		if (entry == null) 
			isValide = false;				
	
		wasValidation = true;
		return isValide;
	}
	
	public boolean validateSelection() {
		return replaceWithDictRef.validate();
	}
	
	public void perform() {
		validate();
		
		if (isValide && validateSelection()){
			replaceWithDictRef.setPararams(entry.attrs.getValue("id"), dict);
			replaceWithDictRef.perform();					
		}		
	}
}