package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;
import java.util.HashMap;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class InsertIntoDictionary {
	//public String elemId, elemName, refId;
	public String entryId;
	public LangElem dict;
	
	public ProjectContent project;
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

	public InsertIntoDictionary() {	
	}
	
	public void setPararams( String entryId, LangElem dict ) {		
		this.entryId = entryId;
		this.dict = dict;		
	}
	
	public void setValidationPararams( ProjectContent project, DRLDocument doc,
							 		   PositionInText fromText, PositionInText toText ) {		
		this.project = project;
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
			
			ArrayList<Element> childsToInsert = from.parent.removeChilds(fromIdx, toIdx);			
			LangElem entry = createNewEntry(entryId);
			dict.getChilds().add(entry);
			entry.appendChilds(childsToInsert);
			
			/*
			String idTofind = ((LangElem)infElem).attrs.getValue("id");
			for (LangElem infElemRef : project.InfElemRefs) {
				if (infElemRef.attrs.getValue("infelemid").equals(idTofind)) {
					String elemRefIdToFind = infElemRef.attrs.getValue("id");
					for (LangElem adapter : project.Adapters) {
						if (adapter.attrs.getValue("infelemrefid").equals(elemRefIdToFind)) {
							ArrayList<LangElem> nestsRefs = new ArrayList<LangElem>();							
							for (Element elem : adapter.getChilds()) {
								if (elem instanceof LangElem) {
									LangElem nestRef = (LangElem)elem;
									String nestid = nestRef.attrs.getValue("nestid");
									if (nestsInSelection.get(nestid) != null)
										nestsRefs.add(nestRef);		
								}
							}
							
							if (nestsRefs.size() != 0) {
								adapter.getChilds().removeAll(nestsRefs);
								LangElem newAdapter = createNewAdapter(adapter, refId);
								adapter.getParent().getChilds().add(newAdapter);
								newAdapter.setChilds(new ArrayList<Element>());								
								newAdapter.getChilds().addAll(nestsRefs);
							}
						}
					}
				}
			}
			
			
			ArrayList<Element> childsToInsert = from.parent.removeChilds(fromIdx, toIdx);
			LangElem newInfElem = createNewInfElem((LangElem)from.parent, elemId, elemName);
			from.parent.getDRLDocument().getChilds().get(0).getChilds().add(newInfElem);
			//newInfElem.appendChilds(childsToInsert);
			newInfElem.setChilds(new ArrayList<Element>());
			newInfElem.getChilds().addAll(childsToInsert);
			
			
			LangElem newInfElemRef = createNewInfElemRef((LangElem)from.parent, refId, elemId);
			from.parent.getChilds().add(fromIdx, newInfElemRef);
			*/
					
			/*
			ArrayList<Element> childsToInsert = pos1.parent.removeChilds(from, to);
			DRLdoc.getChilds().get(0).appendChilds(childsToInsert);
			
			doc.set(DRLdoc.getTextRepresentation());
			*/
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
	
	public ArrayList<LangElem> getPossibleDicts() {
		if (wasDictsPrepared)
			return dicts;
		/*		
		DRLDocument document;
		if (from.isInText)
			document = from.elem.getDRLDocument();
		else 
			document = from.next.getDRLDocument();
				
		for (Element elem : document.getChilds().get(0).getChilds()) {
			if (elem instanceof LangElem) {
				LangElem le = (LangElem)elem;
				if (le.tag == "Dictionary")
					dicts.add(le);
			}
		}
		
		LangElem docTypeElem = (LangElem)document.getChilds().get(0);
		if (docTypeElem.tag == "ProductDocumentation") {
			for (Element elem : docTypeElem.getChilds()) {
				if (elem instanceof LangElem) {
					LangElem le = (LangElem)elem;
					if (le.tag == "FinalInfProduct") {
						for (LangElem posDict : project.dictionarys) {
							if posDict
						}
					}
				}
			}
		}
		*/
		dicts = project.dictionarys;
		wasDictsPrepared = true;
		
		return dicts;
	}
	
	private LangElem createNewEntry(String id) {		
		LangElem entry = new LangElem("Entry", prefex + "Entry", null, dict, dict.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)entry.attrs).addAttribute("id", "id", "id", "", id);
		return entry;
	}
}
