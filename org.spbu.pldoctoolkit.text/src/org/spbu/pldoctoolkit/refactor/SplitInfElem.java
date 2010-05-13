package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;
import java.util.HashMap;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.xml.sax.helpers.AttributesImpl;

public class SplitInfElem {
	public String elemId, elemName, refId;
	public ProjectContent project;
	public PositionInDRL from, to;
	public PositionInText startPosition, endPosition, splitPosition;
	public DRLDocument doc;

	private int fromIdx, toIdx;

	private LangElem targetElem;
	private SelectIntoInfElem firstRefactor, secondRefactor;
	private String prefex;
	private HashMap<String, LangElem> nestsInFirstElem;
	private HashMap<String, LangElem> nestsInSecondElem;
	
	
	public SplitInfElem() {

	}

	public void init() {
		from = doc.findByPosition(startPosition);
		to = doc.findByPosition(endPosition);

		if (from.isInText)
			fromIdx = from.parent.getChilds().indexOf(from.elem);
		else
			fromIdx = from.parent.getChilds().indexOf(from.next);

		if (to.isInText)
			toIdx = from.parent.getChilds().indexOf(to.elem);
		else
			toIdx = from.parent.getChilds().indexOf(to.prev);

	}

	public boolean validate() {
		init();

		from = doc.findByPosition(startPosition);
		to = doc.findByPosition(endPosition);
		boolean selectedBorderInTag = from.isInTag || to.isInTag;
		boolean selectedAllElem = to.parent == from.parent
				&& from.next == to.prev;
		boolean selectedFstLine = to.parent == from.next;

		if (selectedBorderInTag)
			return false;
		if (selectedAllElem || selectedFstLine) {
			targetElem = (LangElem) from.next;
			String elementType = targetElem.tag;
			if (elementType.equals(LangElem.INFELEMENT)) {
				return !targetElemInXorGroup();
			}
		}
		return false;
	}

	private boolean targetElemInXorGroup() {
		String targetElemId = targetElem.attrs.getValue(LangElem.ID);
		for (LangElem ref : project.infElemRefs) {
			int groupIdIdx = ref.attrs.getIndex(LangElem.GROUPID);
			String infElem = ref.attrs.getValue(LangElem.INFELEMID);
			if (groupIdIdx != -1 && infElem.equals(targetElemId)) {
				String targetGruopId = ref.attrs.getValue(groupIdIdx);
				for (LangElem group : project.infElemRefGroups) {
					String groupId = group.attrs.getValue(LangElem.ID);
					String modifier = group.attrs.getValue(LangElem.MODIFIER);
					if (groupId.equals(targetGruopId)
							&& modifier.toUpperCase().equals(LangElem.XOR)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void setValidationParams(ProjectContent projectContent,
			DRLDocument ldoc, PositionInText pos1, PositionInText pos2) {
		this.project = projectContent;
		this.doc = ldoc;
		this.startPosition = pos1;
		this.endPosition = pos2;
	}

	public void setParams(SelectIntoInfElem firstInfElem, SelectIntoInfElem secondInfElem, ProjectContent projectContent, DRLDocument ldoc, PositionInText pos1, PositionInText pos2, PositionInText splitPosition) {
		this.firstRefactor = firstInfElem;
		this.secondRefactor = secondInfElem;
		this.project = projectContent;
		this.doc = ldoc;
		this.startPosition = pos1;
		this.endPosition = pos2;
		this.splitPosition = splitPosition;
		
	}

	public void perform() {
		if(validate()) {
			prefex = doc.DRLnsPrefix;
			if (!prefex.equals(""))
				prefex += ":";
		}
		//distribute childs
		ArrayList<Element> firstElemChilds = new ArrayList<Element>();
		ArrayList<Element> secondElemChilds = new ArrayList<Element>();
		for(Element elem : targetElem.getChilds()) {
			if(inFirstElem(elem)) {
				firstElemChilds.add(elem);
			}
			else {
				secondElemChilds.add(elem);
			}
		}
		
		distributeNests();
		
		LangElem parent = (LangElem)targetElem.getParent();
		int idx = parent.getChilds().indexOf(targetElem);
		//1  remove old infElem
		parent.getChilds().remove(idx);
		//2  create first part
		LangElem firstInfElem = createNewInfElem(parent, firstRefactor.elemId, firstRefactor.elemName);
		//from.parent.getDRLDocument().getChilds().get(0).getChilds().add(newInfElem);
		parent.getChilds().add(idx, firstInfElem);
		firstInfElem.setChilds(new ArrayList<Element>());
		firstInfElem.getChilds().addAll(firstElemChilds);
		//3  create second part
		LangElem secondInfElem = createNewInfElem(parent, secondRefactor.elemId, secondRefactor.elemName);
		Util.addNewLine(parent, idx+1);
		parent.getChilds().add(idx+2, secondInfElem);
		secondInfElem.setChilds(new ArrayList<Element>());
		Util.addNewLine(secondInfElem);
		secondInfElem.getChilds().addAll(secondElemChilds);
		
		//4 split refs and adapters
		for (LangElem infElemRef : getInfElemRefs())
		{
			boolean isOptional = false;
			int optionalIdx = infElemRef.attrs.getIndex(LangElem.OPTIONAL);
			if(optionalIdx!= -1){
				isOptional = 
					infElemRef.attrs.getValue(optionalIdx).toLowerCase().equals(LangElem.TRUE);
			}
			
			String elemRefIdToFind = infElemRef.attrs.getValue(LangElem.ID);
			LangElem refParent = (LangElem) infElemRef.getParent();
			int refIdx = refParent.getChilds().indexOf(infElemRef);
			//4.1 remove old ref
			refParent.getChilds().remove(refIdx);
			//4.2 create first ref
			LangElem firstRef = createNewInfElemRef(refParent, firstRefactor.refId,firstRefactor.elemId);
			refParent.getChilds().add(refIdx, firstRef);
			//4.3 create second ref
			LangElem secondRef = createNewInfElemRef(refParent, secondRefactor.refId,secondRefactor.elemId);
			Util.addNewLine(refParent, refIdx+1);
			refParent.getChilds().add(refIdx+2, secondRef);
			
			if(isOptional) {
				((AttributesImpl)firstRef.attrs).addAttribute(LangElem.OPTIONAL, LangElem.OPTIONAL, 
						LangElem.OPTIONAL, LangElem.OPTIONAL, LangElem.TRUE);
				((AttributesImpl)secondRef.attrs).addAttribute(LangElem.OPTIONAL, LangElem.OPTIONAL, 
						LangElem.OPTIONAL, LangElem.OPTIONAL, LangElem.TRUE);
			}
			
			//4.4 split adapters
			for (LangElem adapter : project.adapters) {
				if (adapter.attrs.getValue(LangElem.INFELEMREFID).equals(elemRefIdToFind)) {
					ArrayList<LangElem> firstElemNestsRefs = new ArrayList<LangElem>();							
					ArrayList<LangElem> secondElemNestsRefs = new ArrayList<LangElem>();	
					for (Element elem : adapter.getChilds()) {
						if (elem instanceof LangElem) {
							LangElem nestRef = (LangElem)elem;
							String nestid = nestRef.attrs.getValue(LangElem.NESTID);
							if (nestsInFirstElem.get(nestid) != null)
								firstElemNestsRefs.add(nestRef);		
							if (nestsInSecondElem.get(nestid) != null)
								secondElemNestsRefs.add(nestRef);
						}
					}
					
					LangElem adapterParent = (LangElem) adapter.getParent();
					int adapterIdx = adapterParent.getChilds().indexOf(adapter);
					//4.4.1 remove old adapter
					adapterParent.getChilds().remove(adapterIdx);
					//4.4.2 add first adapter
						LangElem newAdapter = createNewAdapter(adapterParent, firstRefactor.refId);
						adapterParent.getChilds().add(adapterIdx, newAdapter);
						newAdapter.setChilds(new ArrayList<Element>());								
						newAdapter.getChilds().addAll(firstElemNestsRefs);
						Util.addNewLine(newAdapter);
					//4.4.3 add second adapter
						newAdapter = createNewAdapter(adapterParent, secondRefactor.refId);
						Util.addNewLine(adapterParent,adapterIdx+1);
						adapterParent.getChilds().add(adapterIdx+2, newAdapter);
						newAdapter.setChilds(new ArrayList<Element>());								
						newAdapter.getChilds().addAll(secondElemNestsRefs);
						Util.addNewLine(newAdapter);
				}
			}
		}
	}

	private boolean inFirstElem(Element elem) {
		PositionInText startPos = elem.getStartPos();
		if(startPos.line < splitPosition.line)
			return true;
		if(startPos.line > splitPosition.line)
			return false;
		//if ==
		return startPos.column < splitPosition.column;
	}
	
	//return all infElemRefs on this InfElements
	public ArrayList<LangElem> getInfElemRefs() {
		ArrayList<LangElem >infElemRefs = new ArrayList<LangElem>();
		String idTofind = ((LangElem)targetElem).attrs.getValue(LangElem.ID);
		for (LangElem infElemRef : project.infElemRefs) {
			if (infElemRef.attrs.getValue("infelemid").equals(idTofind))
				infElemRefs.add(infElemRef);
		}
		
		return infElemRefs;
	}
	//find nests in selection and distribute them
	private void distributeNests() {		
		nestsInFirstElem = new HashMap<String, LangElem>();
		nestsInSecondElem = new HashMap<String, LangElem>();
		for (int i = fromIdx; i <= toIdx; ++i) {
			Element cur = from.parent.getChilds().get(i);
			if (cur instanceof LangElem) {
				LangElem langElem = (LangElem) cur;
				if (langElem.tag.equals(LangElem.NEST)) {
					String nestId = langElem.attrs.getValue(LangElem.ID);
					
					if(inFirstElem(langElem)) {
						nestsInFirstElem.put(nestId, langElem);
					}
					else {
						nestsInSecondElem.put(nestId, langElem);
					}
				}
				TreeIterator treeIterator = new TreeIterator(cur);
				while (treeIterator.hasNext()) {
					Element elem = treeIterator.next();
					if (elem instanceof LangElem) {
						LangElem nest = (LangElem) elem;
						if (nest.tag.equals(LangElem.NEST)) {
							String nestId = nest.attrs.getValue(LangElem.ID);
							if(inFirstElem(elem)) {
								nestsInFirstElem.put(nestId, langElem);
							}
							else {
								nestsInSecondElem.put(nestId, langElem);
							}
						}
					}
				}
			}
		}
	}
	
	private LangElem createNewAdapter(LangElem parent, String infElemRefId) {		
		LangElem newAdapter	= new LangElem("Adapter", prefex + "Adapter", null, parent, parent.getDRLDocument(), new AttributesImpl());
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
