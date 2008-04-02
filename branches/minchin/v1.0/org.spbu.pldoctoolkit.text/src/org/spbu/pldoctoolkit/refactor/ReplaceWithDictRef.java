package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class ReplaceWithDictRef {	
	public String entryId;
	public LangElem dict;
		
	public PositionInDRL from, to;
	public PositionInText fromText, toText;
	public DRLDocument doc;
	
	private int fromIdx, toIdx;	
	
	private boolean isValide = false;
	private boolean wasValidation = false;
	private boolean wasDictsPrepared = false;
	
	private Element parent = null;
	private String prefex;
	
	private ArrayList<LangElem> dicts = new ArrayList<LangElem>();

	public ReplaceWithDictRef() {	
	}
	
	public void setPararams( String entryId, LangElem dict ) {		
		this.entryId = entryId;
		this.dict = dict;		
	}
	
	public void setValidationPararams( DRLDocument doc,
							 		   PositionInText fromText, PositionInText toText ) {		
		this.fromText = fromText;
		this.toText = toText;
		this.doc = doc;
	}
	
	public void reset() {
		wasValidation = false;
		wasDictsPrepared = false;
		//dicts.clear();
		isValide = false;
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
			return isValide;
		
		init();
		
		isValide = true;
		
		// 1. 		
		if (from.parent != to.parent) 
			isValide = false;				
			
		// 2.		
		if (parent == null) 
			isValide = false;
		
		// 3.
		if (from.isInTag == true || to.isInTag == true)
			isValide = false;
		
		// 4.
		if ( !( 
				(from.isInText && to.isInText && from.elem == to.elem) || 
				(!from.isInText && (from.next instanceof TextElement) && to.isInText && from.next == to.elem) ||
				(from.isInText && !to.isInText && (to.prev instanceof TextElement) && from.elem == to.prev) ||
				(!from.isInText && !to.isInText && (from.next instanceof TextElement) && (to.prev instanceof TextElement) && from.next == to.prev)
		   ) )
			isValide = false;
		
		wasValidation = true;
		return isValide;
	}
	
	public void perform() {
		validate();
		
		if (isValide){
			splitIfNecessary();
		
			prefex = doc.DRLnsPrefix;
			if (!prefex.equals(""))
				prefex += ":";					
						
			LangElem dictRef = createNewDictRef();			
			from.parent.getChilds().add(fromIdx, dictRef);			
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

	private LangElem createNewDictRef() {
		String dictId = dict.attrs.getValue("id");
		LangElem entry = new LangElem("DictRef", prefex + "DictRef", null, dict, dict.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)entry.attrs).addAttribute("entryid", "entryid", "entryid", "", entryId);
		((AttributesImpl)entry.attrs).addAttribute("dictid", "dictid", "dictid", "", dictId);
		return entry;
	}
}
