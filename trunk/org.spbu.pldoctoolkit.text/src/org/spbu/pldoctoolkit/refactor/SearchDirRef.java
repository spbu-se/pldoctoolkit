package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

public class SearchDirRef {
	private ReplaceWithDirRef replaceWithDirRef = new ReplaceWithDirRef();

	private ProjectContent projectContent;
		
	public SearchDirRef(ProjectContent projectContent) {
		this.projectContent = projectContent;
	}
	
	public void setPararams( PositionInText fromText, PositionInText toText, DRLDocument docToInsertInto ) {		
		replaceWithDirRef.setValidationPararams(docToInsertInto, fromText, toText);
		replaceWithDirRef.reset();		
	}

	public boolean validateSelection() {
		return replaceWithDirRef.validate();
	}
	
	public void perform(LangElem template,  LangElem entry) {		
		if (validateSelection()){
			replaceWithDirRef.setPararams(template, entry);
			replaceWithDirRef.perform();					
		}		
	}
	
	public String getSearchText(LangElem template, LangElem entry) {		
		return Util.getTextRepresentationOfTemplateAndEntry(template, entry);
	}
	
	public LangElem getDirectory(DRLDocument doc, PositionInText posText) {
		PositionInDRL pos = doc.findByPosition(posText);
		
		Element start;
		if (pos.isInTag) {
			if (((LangElem)pos.elem).tag.equals(LangElem.DIRECTORY))
				return (LangElem)pos.elem;
			else
				start = pos.elem;
		}
		else if (pos.isInText)
			start = pos.elem;
		else
			start = pos.prev != null ? pos.prev : pos.next;
		
		UpwardIterator iterator = new UpwardIterator(start);
		while (iterator.hasNext()) {
			LangElem elem = (LangElem)iterator.next();
			if (elem.tag.equals(LangElem.DIRECTORY))
				return elem;
		}
		
		return null;
	}
	
	public ArrayList<LangElem> getTemplates(LangElem directory) {
		String directoryId = directory.attrs.getValue(LangElem.ID);
		
		ArrayList<LangElem> res = new ArrayList<LangElem>(); 
		for(LangElem template : projectContent.templates) {
			if (template.attrs.getValue(LangElem.DIRECTORYID).equals(directoryId))
				res.add(template);
		}
		
		return res;
	}
	
	public ArrayList<LangElem> getEntrys(LangElem directory) {		
		ArrayList<LangElem> res = new ArrayList<LangElem>(); 
		for(Element elem : directory.getChilds()) {			
			if (elem instanceof LangElem && ((LangElem)elem).tag.equals(LangElem.ENTRY))
				res.add((LangElem)elem);
		}
		
		return res;
	}
}