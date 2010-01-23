package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;
import java.util.HashMap;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class SelectIntoInfElem {
	public String elemId, elemName, refId;
	public ProjectContent project;
	public PositionInDRL from, to;
	public PositionInText fromText, toText;
	public DRLDocument doc;
	
	private int fromIdx, toIdx;
	
	private HashMap<String, LangElem> nestsInSelection = null;
	private ArrayList<LangElem> infElemRefs = null;
	
	private boolean isValide = false;
	private boolean wasValidation = false;
	private boolean wasInfElemRefsPrepared = false;
	
	private Element infElem = null;
	private String prefex;
/*	
	public SelectIntoInfElem( String id, String name, String refId, 
			                  ProjectContent project, DRLDocument doc,
						      PositionInText fromText, PositionInText toText ) 
	{	
		this.elemId = id;
		this.elemName = name;
		this.refId = refId;
		this.project = project;
		this.fromText = fromText;
		this.toText = toText;
		this.doc = doc;	
	}
*/	
	public SelectIntoInfElem() {	
	}
	
	public void setPararams( String id, String name, String refId, 
            				 ProjectContent project, DRLDocument doc,
            				 PositionInText fromText, PositionInText toText ) {
		this.elemId = id;
		this.elemName = name;
		this.refId = refId;
		this.project = project;
		this.fromText = fromText;
		this.toText = toText;
		this.doc = doc;
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
		isValide = false;
		wasInfElemRefsPrepared = false;
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
		
		infElem = searchInfElemiterator.next();
		while (infElem != null) {				
			if (infElem instanceof LangElem) {
				LangElem langElem = (LangElem)infElem; 
				if ( langElem.tag.equals("InfElement") )
					break;				
			}
			infElem = searchInfElemiterator.next();
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
		if (infElem == null) 
			isValide = false;
		
		// 3.
		if (from.isInTag == true || to.isInTag == true)
			isValide = false;
		
		wasValidation = true;
		return isValide;
	}
	
	public void perform() {
		validate();
		
		if (isValide){
			splitIfNecessary();
			
			prepareNests();
			
			prefex = doc.DRLnsPrefix;
			if (!prefex.equals(""))
				prefex += ":";
			
//			String idTofind = ((LangElem)infElem).attrs.getValue("id");
//			for (LangElem infElemRef : project.InfElemRefs) {
//				if (infElemRef.attrs.getValue("infelemid").equals(idTofind))
			getInfElemRefs();
			for (LangElem infElemRef : infElemRefs)
				{
					String elemRefIdToFind = infElemRef.attrs.getValue("id");
					for (LangElem adapter : project.adapters) {
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
			
			
			
			ArrayList<Element> childsToInsert = from.parent.removeChilds(fromIdx, toIdx);
			LangElem newInfElem = createNewInfElem((LangElem)from.parent, elemId, elemName);
			from.parent.getDRLDocument().getChilds().get(0).getChilds().add(newInfElem);
			//newInfElem.appendChilds(childsToInsert);
			newInfElem.setChilds(new ArrayList<Element>());
			newInfElem.getChilds().addAll(childsToInsert);
			
			
			LangElem newInfElemRef = createNewInfElemRef((LangElem)from.parent, refId, elemId);
			from.parent.getChilds().add(fromIdx, newInfElemRef);
			
					
			/*
			ArrayList<Element> childsToInsert = pos1.parent.removeChilds(from, to);
			DRLdoc.getChilds().get(0).appendChilds(childsToInsert);
			
			doc.set(DRLdoc.getTextRepresentation());
			*/
		}		
	}
	
	public HashMap<String, LangElem> getNestsInSelection() {
		if (nestsInSelection == null)
			prepareNests();
		
		return nestsInSelection;
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
	
	public ArrayList<LangElem> getInfElemRefs() {
		if (!isValide)
			return null;
		
		if (wasInfElemRefsPrepared)
			return infElemRefs;
		
		infElemRefs = new ArrayList<LangElem>();
		String idTofind = ((LangElem)infElem).attrs.getValue("id");
		for (LangElem infElemRef : project.infElemRefs) {
			if (infElemRef.attrs.getValue("infelemid").equals(idTofind))
				infElemRefs.add(infElemRef);
		}
		
		return infElemRefs;
	}
	
	private void prepareNests() {		
		nestsInSelection = new HashMap<String, LangElem>();
		for (int i = fromIdx; i <= toIdx; ++i) {
			Element cur = from.parent.getChilds().get(i);
			if (cur instanceof LangElem) {
				LangElem langElem = (LangElem) cur;
				if (langElem.tag.equals("Nest")) {
					String nestId = langElem.attrs.getValue("id");
					nestsInSelection.put(nestId, langElem);
				}
				TreeIterator treeIterator = new TreeIterator(cur);
				while (treeIterator.hasNext()) {
					Element elem = treeIterator.next();
					if (elem instanceof LangElem) {
						LangElem nest = (LangElem) elem;
						if (nest.tag.equals("Nest")) {
							String nestId = nest.attrs.getValue("id");
							nestsInSelection.put(nestId, nest);
						}
					}
				}
			}
		}
	}
		
	private LangElem createNewAdapter(LangElem prevAdapter, String infElemRefId) {		
		LangElem newAdapter	= new LangElem("Adapter", prefex + "Adapter", null, prevAdapter.getParent(), prevAdapter.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)newAdapter.attrs).addAttribute("infelemrefid", "infelemrefid", "infelemrefid", "", infElemRefId);
		return newAdapter;
	}
	
	private LangElem createNewInfElem(LangElem parent, String id, String name) {
		LangElem newInfElem	= new LangElem("InfElement", prefex + "InfElement", null, parent, parent.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)newInfElem.attrs).addAttribute("id", "id", "id", "", id);
		((AttributesImpl)newInfElem.attrs).addAttribute("name", "name", "name", "", name);
		return newInfElem;
	}
	
	private LangElem createNewInfElemRef(LangElem parent, String id, String infelemid) {
		LangElem newInfElem	= new LangElem("InfElemRef", prefex + "InfElemRef", null, parent, parent.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)newInfElem.attrs).addAttribute("id", "id", "id", "", id);
		((AttributesImpl)newInfElem.attrs).addAttribute("infelemid", "infelemid", "infelemid", "", infelemid);
		return newInfElem;
	}
}
