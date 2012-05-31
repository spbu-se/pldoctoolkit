package org.spbu.pldoctoolkit.refactor;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class CreateNest {	
	public String nestId;	
		
	//public PositionInDRL from, to;
	public PositionInDRL pos;
	//public PositionInText fromText, toText;
	public PositionInText posText;
	public DRLDocument doc;
	
	//private int fromIdx, toIdx;	
	private int idx;
	
	private boolean isValide = false;
	private boolean wasValidation = false;
	private boolean wasDictsPrepared = false;
	
	private Element parent = null;
	private String prefex;	

	public CreateNest() {	
	}
	
	public void setPararams( String id ) {		
		this.nestId = id;				
	}
	
	public void setValidationPararams( DRLDocument doc,
							 		   PositionInText posText) {		
		this.posText = posText;		
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
		pos = doc.findByPosition(posText);		
		/*
		if (pos.isInText)
			idx = pos.parent.getChilds().indexOf(pos.elem);
		
		else {
			fromIdx = from.parent.getChilds().indexOf(from.next);
		}
		*/
		
		// 2.
		if (pos.isInTag == true)
			return;
		
		UpwardIterator searchInfElemiterator;
		if (pos.isInText)
			searchInfElemiterator = new UpwardIterator(pos.elem);
		else if (pos.next != null)
			searchInfElemiterator = new UpwardIterator(pos.next);
		else
			return;
		
		parent = searchInfElemiterator.next();
		while (parent != null) {				
			if (parent instanceof LangElem) {
				LangElem langElem = (LangElem)parent; 
				if ( langElem.tag.equals(LangElem.INFELEMENT) ||
					 langElem.tag.equals(LangElem.INFPRODUCT)	)
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
		if (pos.isInTag)
			isValide = false;
		
		// 2.		
		if (parent == null) 
			isValide = false;
		
		wasValidation = true;
		return isValide;
	}
	
	public void perform() {
		validate();
		
		if (isValide){
			prefex = doc.DRLnsPrefix;
			if (!prefex.equals(""))
				prefex += ":";		
			
			//idx = -1;
			splitIfNecessary();
			{
				if (pos.next == null)
					idx = pos.parent.getChilds().size();
				else
					idx = pos.parent.getChilds().indexOf(pos.next);
			}						
						
			LangElem nest = createNewNest(prefex, nestId, parent);			
			pos.parent.getChilds().add(idx, nest);			
		}		
	}	
	
	private void splitIfNecessary() {
		if (pos.isInText) {			
			((TextElement)pos.elem).Split(posText);
			//Element parent = pos.elem.getParent();
			pos = doc.findByPosition(posText);
			//idx = pos.parent.getChilds().indexOf(pos.next);
			//pos = new PositionInDRL(false, false, null, parent.getChilds().get(idx), parent.getChilds().get(idx+1), parent);
			//idx++;
		}		
	}

	public static LangElem createNewNest(String prefex, String nestId, Element parent) {		
		LangElem entry = new LangElem(LangElem.NEST, prefex + LangElem.NEST, null, parent, parent.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)entry.attrs).addAttribute(LangElem.ID, LangElem.ID, LangElem.ID, "", nestId);		
		return entry;
	}	
	
	public static String getAppropriateNestId(String prefix, ProjectContent projectContent) {
		String nestId;
		if ("".equals(prefix)) {
			nestId = "nestId";
		} else {
			nestId = prefix + "NestId";
		}
		String resId = "";
		int i = projectContent.nests.size();
		boolean goodId = false;
		while (!goodId) {
			resId = nestId + String.valueOf(i);
			goodId = true;
			for (LangElem nest : projectContent.nests) {
				if (nest.attrs.getValue(LangElem.ID).equals(resId)) {
					goodId = false;
					break;
				}
			}
			++i;
		}
		return resId;
	}
	
}