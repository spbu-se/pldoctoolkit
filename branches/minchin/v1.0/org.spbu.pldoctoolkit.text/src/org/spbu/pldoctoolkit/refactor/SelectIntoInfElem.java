package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;
import java.util.HashMap;

import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class SelectIntoInfElem {
	public String id, name, refId;
	public ProjectContent project;
	public PositionInDRL from, to;
	
	private int fromIdx, toIdx;
	private HashMap<String, LangElem> nestsInSelection = null; 
	
	public SelectIntoInfElem(String id, String name, String refId, ProjectContent project,
						     PositionInDRL from, PositionInDRL to) 
	{	
		this.id = id;
		this.name = name;
		this.refId = refId;
		this.project = project;
		this.from = from;
		this.to = to;
	}
	
	public SelectIntoInfElem() {	
	}
	
	public void perform() {
		//if ()
		
		if (from.parent == to.parent){
			UpwardIterator searchInfElemiterator = new UpwardIterator(from.next);			
			
			Element infElem = searchInfElemiterator.next();
			while (infElem != null) {				
				infElem = searchInfElemiterator.next();
				if (infElem instanceof LangElem) {
					LangElem langElem = (LangElem)infElem; 
					if ( langElem.tag.equals("InfElement") )
						break;				
				}
			}
			
			if (infElem == null)
				return;			
			
			fromIdx = from.parent.getChilds().indexOf(from.next);
			toIdx = from.parent.getChilds().indexOf(to.prev);
			
			prepareNests();
			
			String elemId = "newElemId", elemName = "newElemName", refId = "newRefId";
			
			String idTofind = ((LangElem)infElem).attrs.getValue("id");
			for (LangElem infElemRef : project.InfElemRefs) {
				if (infElemRef.attrs.getValue("infelemid").equals(idTofind)) {
					String elemRefIdToFind = infElemRef.attrs.getValue("id");
					for (LangElem adapter : project.Adapters) {
						if (adapter.attrs.getValue("infElemRefId").equals(elemRefIdToFind)) {
							ArrayList<LangElem> nestsRefs = new ArrayList<LangElem>();							
							for (Element elem : adapter.getChilds()) {
								LangElem nestRef = (LangElem)elem;
								String nestid = nestRef.attrs.getValue("nestid");
								if (nestsInSelection.get(nestid) != null)
									nestsRefs.add(nestRef);								
							}
							
							if (nestsRefs.size() != 0) {
								adapter.getChilds().removeAll(nestsRefs);
								LangElem newAdapter = createNewAdapter(adapter, refId);
								adapter.getParent().getChilds().add(newAdapter);
							}
						}
					}
				}
			}
			
			
			ArrayList<Element> childsToInsert = from.parent.removeChilds(fromIdx, toIdx);
			LangElem newInfElemRef = createNewInfElemRef((LangElem)from.parent, "newRefId", elemId);
			from.parent.getChilds().add(fromIdx, newInfElemRef);
			
			LangElem newInfElem = createNewInfElem((LangElem)from.parent, elemId, elemName);
			from.parent.getDRLDocument().getChilds().get(0).getChilds().add(newInfElem);
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
	
	private void prepareNests() {		
		nestsInSelection = new HashMap<String, LangElem>();
		for (int i = fromIdx; i <= toIdx; ++i) {
			Element cur = from.parent.getChilds().get(i);
			if (cur instanceof LangElem) {
				//LangElem langElem = (LangElem) cur;
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
		LangElem newAdapter	= new LangElem("Adapter", "Adapter", null, prevAdapter.getParent(), prevAdapter.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)newAdapter.attrs).addAttribute("infelemrefid", "infelemrefid", "infelemrefid", "", infElemRefId);
		return newAdapter;
	}
	
	private LangElem createNewInfElem(LangElem parent, String id, String name) {
		LangElem newInfElem	= new LangElem("InfElement", "InfElement", null, parent, parent.getDRLDocument(), null);
		((AttributesImpl)newInfElem.attrs).addAttribute("id", "id", "id", "", id);
		((AttributesImpl)newInfElem.attrs).addAttribute("name", "name", "name", "", name);
		return newInfElem;
	}
	
	private LangElem createNewInfElemRef(LangElem parent, String id, String infelemid) {
		LangElem newInfElem	= new LangElem("InfElemRef", "InfElemRef", null, parent, parent.getDRLDocument(), null);
		((AttributesImpl)newInfElem.attrs).addAttribute("id", "id", "id", "", id);
		((AttributesImpl)newInfElem.attrs).addAttribute("infelemid", "infelemid", "infelemid", "", infelemid);
		return newInfElem;
	}
}
