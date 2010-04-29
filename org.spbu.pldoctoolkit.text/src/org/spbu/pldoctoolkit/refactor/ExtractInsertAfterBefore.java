package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class ExtractInsertAfterBefore {
	public static final int AFTER = 1;
	public static final int BEFORE = 2;
	
	public ProjectContent project;
	public PositionInDRL from, to;
	public PositionInText fromText, toText;
	public DRLDocument doc;
	
	private int fromIdx, toIdx;	
	
	private ArrayList<LangElem> infElemRefs = null;
	
	private boolean isValide = false;
	private boolean wasValidation = false;
	private boolean wasInfElemRefsPrepared = false;
	
	private Element infElem = null;
	private LangElem nest = null;
	private String prefex;
	
	private int type;

	public ExtractInsertAfterBefore() {	
	}
	
	public void setPararams( ProjectContent project ) {
		this.project = project;		
	}
	
	public void setValidationPararams( DRLDocument doc,
							 		   PositionInText fromText, PositionInText toText, int type ) {		
		this.fromText = fromText;
		this.toText = toText;
		this.doc = doc;
		this.type = type;
	}
	
	public void reset() {
		wasValidation = false;
		isValide = false;
		wasInfElemRefsPrepared = false;
	}
	
	private boolean init() {
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
		if (from.isInTag || to.isInTag)
			return false;
		
		UpwardIterator searchInfElemiterator;
		
		if (from.next != null)
			searchInfElemiterator = new UpwardIterator(from.next);
		else
			searchInfElemiterator = new UpwardIterator(from.elem);
		
		infElem = searchInfElemiterator.next();
		while (infElem != null) {				
			if (infElem instanceof LangElem) {
				LangElem langElem = (LangElem)infElem; 
				if ( langElem.tag.equals(LangElem.INFELEMENT) )
					break;				
			}
			infElem = searchInfElemiterator.next();
		}
		
		return true;
	}
	
	private boolean isNest(Element elem) {
		return (elem instanceof LangElem) && ((LangElem)elem).tag.equals(LangElem.NEST); 
	}
	
	public boolean validate() {
		if (wasValidation)
			return isValide;
		wasValidation = true;
		
		if (!init()){ 
			isValide = false;			
			return false;
		}
		
		if  ((type == AFTER && from.isInText) || (type == BEFORE && to.isInText)){ 
			isValide = false;			
			return false;
		}
		
		Element temp;
		if (type == AFTER)		
			temp = (LangElem)from.prev;
		else
			temp = (LangElem)to.next;
		
		if (isNest(temp)) {
			nest = (LangElem)temp;
		}
		else { 
			isValide = false;
			return false;
		}		
		
		// 1. 		
		if (from.parent != to.parent) { 
			isValide = false;
			return false;
		}
			
		// 2.		
		if (infElem == null) { 
			isValide = false;			
			return false;
		}
		
		// 3.
		if (from.isInTag == true || to.isInTag == true) {
			isValide = false;
			return false;
		}
		
		isValide = Util.isDocBookFragment(doc, from, to);
		return isValide;
	}
	
	public void perform() {
		validate();
		
		if (isValide){
			splitIfNecessary();
			
			prefex = doc.DRLnsPrefix;
			if (!prefex.equals(""))
				prefex += ":";

			getInfElemRefs();
			ArrayList<Element> childsToInsert = from.parent.removeChilds(fromIdx, toIdx);
			String nestId = nest.attrs.getValue(LangElem.ID);
			for (LangElem infElemRef : infElemRefs)
			{
				String elemRefIdToFind = infElemRef.attrs.getValue(LangElem.ID);
				for (LangElem adapter : project.adapters) {
					if (adapter.attrs.getValue(LangElem.INFELEMREFID).equals(elemRefIdToFind)) {														
						LangElem insertSmth = createInsertAfterBefore(nestId, adapter);
						insertSmth.appendChilds(childsToInsert);
						adapter.getChilds().add(insertSmth);
					}
				}
			}				
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
		
	private LangElem createInsertAfterBefore(String nestId, LangElem parent) {		
		if (type == AFTER)
			return createSmthWithNestID(LangElem.INSERTAFTER, nestId, parent);
		else
			return createSmthWithNestID(LangElem.INSERTBEFORE, nestId, parent);
	}
	
	private LangElem createSmthWithNestID(String tag, String nestId, LangElem parent) {
		LangElem newSmth = new LangElem(tag, prefex + tag, null, parent, parent.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)newSmth.attrs).addAttribute(LangElem.NESTID, LangElem.NESTID, LangElem.NESTID, "", nestId);
		return newSmth;
	}
}
