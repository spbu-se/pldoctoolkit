package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;

import org.eclipse.jface.text.IDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class CreateDirTemplate {
	public static class FragmentToReplace {
		//private PositionInText fromText, toText;
		private int offset, lengthBefore, lengthAfter;
		private int offsetWithOurReturn;
		private String attrId;
		
		private String replacedText;
		private String attrRefText = null;
		
		private DRLDocument doc;
		
		public FragmentToReplace(int offset, String replacedText, String attrId, DRLDocument doc, String text) {
			this.offset = offset;
			this.replacedText = replacedText;
			this.attrId = attrId;
			this.doc = doc;
			
			lengthBefore = replacedText.length();
			
			offsetWithOurReturn = offset;
			for (int i = 0; i<offset; ++i) {
				if (text.charAt(i) == '\r')
					offsetWithOurReturn -= 1;
			}
		}
		
		public String getTexRepresentation() {
			if (attrRefText != null)
				return attrRefText;
			
			String prefex = doc.DRLnsPrefix;
			if (!prefex.equals(""))
				prefex += ":";
			
			LangElem attrRef = new LangElem(LangElem.ATTRREF, prefex + LangElem.ATTRREF, null, null, null, new AttributesImpl());
			((AttributesImpl)attrRef.attrs).addAttribute(LangElem.ATTRID, LangElem.ATTRID, LangElem.ATTRID, "", attrId);
			
			attrRefText = attrRef.getTextRepresentation();
			return attrRefText;
		}
		
		public String getReplacedText() {
			return replacedText;
		}
		
		public int getOffset() {
			return offset;
		}
		
		public int getOffsetWithoutRet() {
			return offsetWithOurReturn;
		}
		
		public int getLengthBefore() {
			return lengthBefore;
		}
		
		public int getLengthAfter() {
			return getTexRepresentation().length();
		}
		
		public DRLDocument getDoc() {
			return doc;
		}
		
		public String getAttrId() {
			return attrId;
		}
		
		public void setAttrId(String attrId) {
			this.attrId = attrId;
			attrRefText = null;
		}
	}
	
	private PositionInText fromText;
	private PositionInText toText;
	private String id;
	private String directoryid;
	private DRLDocument doc;
	private IDocument textDoc;
	
	private boolean isValid = false;
	private boolean wasValidation = false;
	
	private int fromIdx, toIdx;
	private PositionInDRL from, to;
	
	public CreateDirTemplate(PositionInText fromText, PositionInText toText, DRLDocument doc, IDocument textDoc) {
		this.fromText = fromText;
		this.toText = toText;
		this.doc =doc;
		this.textDoc = textDoc;
	}
	
	public void perform(String content, /*ArrayList<FragmentToReplace> fragmentsToReplace,*/ String id_, String directoryid_) {
		if (!isDocBookFragment())
			return;
		
		id = id_;
		directoryid = directoryid_;
		
		LangElem template = createTemlate();
		TextElement contentElem = new TextElement(new PositionInText(0,0), content.length(), content, template, doc);
		template.getChilds().add(contentElem);
		doc.getChilds().get(0).getChilds().add(template);
		
		
		/*ArrayList<Element> newElems = new ArrayList<Element>();
		for (int i = fromIdx)*/
		/*
		LangElem parent = (LangElem)from.parent.clone(from.parent.getParent());
		
		from = parent.findByPosition(fromText);
		to = parent.findByPosition(toText);		
		
		LangElem template = createTemlate();
		
		PositionInDRL curFrom = from;
		curFrom = splitIfNecessary(curFrom, fromText);		
		try {
			int fromOffset = textDoc.getLineOffset(fromText.line - 1);
			fromOffset += fromText.column - 1;
			for (int i = 0; i<fragmentsToReplace.size(); ++i) {
				FragmentToReplace curFragment = fragmentsToReplace.get(i);
				
				int startLine = textDoc.getLineOfOffset(fromOffset + curFragment.getOffsetWithoutRet()) + 1;
				int startCol = fromOffset + curFragment.getOffsetWithoutRet() - textDoc.getLineOffset(startLine - 1) + 1;
								
				int endLine = textDoc.getLineOfOffset(fromOffset + curFragment.getOffsetWithoutRet() + curFragment.getLengthBefore()) + 1;
				int endCol = fromOffset + curFragment.getOffsetWithoutRet() + curFragment.getLengthBefore() - textDoc.getLineOffset(endLine - 1) + 1;
				
				PositionInText start = new PositionInText(startLine, startCol);
				PositionInText end = new PositionInText(endLine, endCol);
				
				PositionInDRL curTo = parent.findByPosition(start);
				curTo = splitIfNecessary(curTo, start);
				
				int curFromIdx = parent.getChilds().indexOf(curFrom.next);
				int curToIdx = parent.getChilds().indexOf(curTo.prev);
								
				for (int j = curFromIdx; j < curToIdx; ++j) {
					template.getChilds().add(parent.getChilds().get(j));
				}
				
				LangElem attrRef = createAttrRef(curFragment.getAttrId(), template);
				template.getChilds().add(attrRef);
				
				curFrom = splitIfNecessary(parent.findByPosition(end), end);
			}
			
			PositionInDRL curTo = splitIfNecessary(to, toText);
			
			int curFromIdx = parent.getChilds().indexOf(curFrom.next);
			int curToIdx = parent.getChilds().indexOf(curTo.prev);
							
			for (int j = curFromIdx; j <= curToIdx; ++j) {
				template.getChilds().add(parent.getChilds().get(j));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	private PositionInDRL splitIfNecessary(PositionInDRL pos, PositionInText textPos) {
		if (pos.isInText) {		
			((TextElement)pos.elem).Split(textPos);
			Element parent = pos.elem.getParent(); 
			int posIdx = pos.parent.getChilds().indexOf(pos.elem);
			return new PositionInDRL(false, false, null, parent.getChilds().get(posIdx), parent.getChilds().get(posIdx+1), parent);			
		}		
		else
			return pos; 
	}
	
	private LangElem createTemlate() {
		String prefex = doc.DRLnsPrefix;
		if (!prefex.equals(""))
			prefex += ":";
		
		LangElem parent = (LangElem)doc.getChilds().get(0);
		
		LangElem template = new LangElem(LangElem.DIRTEMPLATE, prefex + LangElem.DIRTEMPLATE, null, parent, doc, new AttributesImpl());
		((AttributesImpl)template.attrs).addAttribute(LangElem.ID, LangElem.ID, LangElem.ID, "", id);
		((AttributesImpl)template.attrs).addAttribute(LangElem.DIRECTORYID, LangElem.DIRECTORYID, LangElem.DIRECTORYID, "", directoryid);
		
		return template; 
	}
	
	public LangElem createAttrRef(String id, LangElem parent) {		
		String prefex = doc.DRLnsPrefix;
		if (!prefex.equals(""))
			prefex += ":";
		
		LangElem attrRef = new LangElem(LangElem.ATTRREF, prefex + LangElem.ATTRREF, null, parent, doc, new AttributesImpl());
		((AttributesImpl)attrRef.attrs).addAttribute(LangElem.ATTRID, LangElem.ATTRID, LangElem.ATTRID, "", id);
				
		return attrRef;
	}
	
	public boolean isDocBookFragment(/*PositionInText fromText, PositionInText toText, DRLDocument doc*/) {
		if (wasValidation)
			return isValid;
		
		from = doc.findByPosition(fromText);
		to = doc.findByPosition(toText);
		
		if (from.parent != to.parent) {
			isValid = false;
			return isValid;
		}
		
		if (from.isInTag || to.isInTag) {
			isValid = false;
			return isValid;
		}
		
		LangElem parent = (LangElem)from.parent;
				
		if (from.isInText)
			fromIdx = from.parent.getChilds().indexOf(from.elem);
		else
			fromIdx = from.parent.getChilds().indexOf(from.next);
		
		if (to.isInText)
			toIdx = from.parent.getChilds().indexOf(to.elem);
		else
			toIdx = from.parent.getChilds().indexOf(to.prev);
		
		for (int i = fromIdx; i <= toIdx; ++i ) {
			TreeIterator iter = new TreeIterator(parent.getChilds().get(i));
			while (iter.hasNext()) {
				Element elem = iter.next();
				if (elem instanceof LangElem) {
					for (String tag : LangElem.TAGS)
						if ( ((LangElem)elem).tag.equals(tag) ) {
							isValid = false;
							return isValid;
						}
				}
			}
		}
		
		isValid = true;
		return isValid;
	}
	
	public boolean isText(FragmentToReplace fragment) {
		try {
		int fromOffset = textDoc.getLineOffset(fromText.line - 1);
		fromOffset += fromText.column - 1;
		
		int startLine = textDoc.getLineOfOffset(fromOffset + fragment.getOffsetWithoutRet()) + 1;
		int startCol = fromOffset + fragment.getOffsetWithoutRet() - textDoc.getLineOffset(startLine - 1) + 1;
						
		int endLine = textDoc.getLineOfOffset(fromOffset + fragment.getOffsetWithoutRet() + fragment.getLengthBefore()) + 1;
		int endCol = fromOffset + fragment.getOffsetWithoutRet() + fragment.getLengthBefore() - textDoc.getLineOffset(endLine - 1) + 1;
		
		PositionInText start = new PositionInText(startLine, startCol);
		PositionInText end = new PositionInText(endLine, endCol);
		
		PositionInDRL from = doc.findByPosition(start);
		PositionInDRL to = doc.findByPosition(end);
		
		if (from.parent != to.parent) 
			return false;				
			
		// 2.
		LangElem parent = (LangElem)from.parent;
		if (parent == null) 
			return false;
		
		// 3.
		if (from.isInTag || to.isInTag)
			return false;
		
		// 4.
		if ( !( 
				(from.isInText && to.isInText && from.elem == to.elem) || 
				(!from.isInText && (from.next instanceof TextElement) && to.isInText && from.next == to.elem) ||
				(from.isInText && !to.isInText && (to.prev instanceof TextElement) && from.elem == to.prev) ||
				(!from.isInText && !to.isInText && (from.next instanceof TextElement) && (to.prev instanceof TextElement) && from.next == to.prev)
		   ) )
			return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public String getText() {
		try {
			int fromOffset = textDoc.getLineOffset(fromText.line-1) + fromText.column - 1;
			int toOffset = textDoc.getLineOffset(toText.line-1) + toText.column - 1;
			
			return textDoc.get(fromOffset, toOffset - fromOffset);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
				
		return null;
	}
	
	public static ArrayList<DRLDocument> getPossibleDocs(ProjectContent proj) {
		return CreateNewDictionary.getPossipleDocs(proj);
	}
	
	public static ArrayList<String> getPosibleAttrIds(LangElem dir) {
		ArrayList<String> res = new ArrayList<String>();
		TreeIterator iter = new TreeIterator(dir);
		while (iter.hasNext()) {
			Element elem = iter.next();
			if ((elem instanceof LangElem) && (((LangElem)elem).tag.equals(LangElem.ATTR))) {
				res.add(((LangElem)elem).attrs.getValue(LangElem.ID));
			}
		}
		
		return res;
	}
}
