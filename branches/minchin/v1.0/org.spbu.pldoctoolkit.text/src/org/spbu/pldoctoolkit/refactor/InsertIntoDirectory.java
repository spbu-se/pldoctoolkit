package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.eclipse.jface.text.IDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.spbu.pldoctoolkit.refactor.pattern.AnyThing;
import org.spbu.pldoctoolkit.refactor.pattern.NessesaryElement;
import org.spbu.pldoctoolkit.refactor.pattern.Pattern;
import org.spbu.pldoctoolkit.refactor.pattern.PatternElement;
import org.xml.sax.helpers.AttributesImpl;

public class InsertIntoDirectory {
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
	
	public InsertIntoDirectory(PositionInText fromText, PositionInText toText, DRLDocument doc, IDocument textDoc) {
		this.fromText = fromText;
		this.toText = toText;
		this.doc =doc;
		this.textDoc = textDoc;
	}
	
	public void setParams(PositionInText fromText, PositionInText toText, DRLDocument doc, IDocument textDoc) {
		this.fromText = fromText;
		this.toText = toText;
		this.doc =doc;
		this.textDoc = textDoc;
	}
	
	public PositionInText getFromText() {
		return fromText;
	}
	
	public PositionInText getToText() {
		return toText;
	}
	
	public IDocument getTextDoc() {
		return textDoc;
	}
	
	public DRLDocument getDoc() {
		return doc;
	}
	
	public void perform(LangElem entry, LangElem template) {
		from = doc.findByPosition(fromText);
		to = doc.findByPosition(toText);		
		if (!Util.isDocBookFragment(doc, from, to))
			return;

		if (from.isInText)
			fromIdx = from.parent.getChilds().indexOf(from.elem);
		else
			fromIdx = from.parent.getChilds().indexOf(from.next);
		
		if (to.isInText)
			toIdx = from.parent.getChilds().indexOf(to.elem);
		else
			toIdx = from.parent.getChilds().indexOf(to.prev);
		
		splitIfNecessary();
		
		from.parent.removeChilds(fromIdx, toIdx);
		LangElem dirRef = createDirRef(entry.attrs.getValue(LangElem.ID), template.attrs.getValue(LangElem.ID), (LangElem)from.parent);			
		from.parent.getChilds().add(fromIdx, dirRef);				
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
	
	public Pattern createPatternFromTemplate(LangElem template) {
		/*ArrayList<PatternElement> elements = new ArrayList<PatternElement>();
		for (Element elem : template.getChilds()) {			
			if (elem instanceof LangElem && ((LangElem)elem).tag.equals(LangElem.ATTRREF)) {
				AnyThing anyThing = new AnyThing();
				anyThing.setLangElem((LangElem)elem);
				elements.add(anyThing);				
			}
			else {
				elements.add(new NessesaryElement(elem.getTextRepresentation()));
			}			
		}
		
		return new Pattern(elements, getText());*/
		LangElem emptyEntry = new LangElem(LangElem.ENTRY, LangElem.ENTRY, new PositionInText(0,0), null, null, new AttributesImpl());
		return createPatternFromTemplateAndEntry(template, emptyEntry);
	}
	
	public Pattern createPatternFromTemplateAndEntry(LangElem template, LangElem entry) {		
		HashMap<TextElement, LangElem> anyThingInTheFuture = new HashMap<TextElement, LangElem>();
		ArrayList<TextElement> anyThingInTheFutureText = new ArrayList<TextElement>();
		
		LangElem clonedTemplate = (LangElem)template.clone(template.getParent());
		TreeIterator iterator = new TreeIterator(clonedTemplate);		
		
		while (iterator.hasNext()) {
			Element elem = iterator.next();
			if (elem instanceof LangElem && ((LangElem)elem).tag.equals(LangElem.ATTRREF)) {
				LangElem attrRef = (LangElem)elem;
				String attrId = attrRef.attrs.getValue(LangElem.ATTRID); 
				LangElem attr = getAttr(entry, attrId);
				
				LangElem parent = (LangElem)attrRef.getParent();
				int idx = parent.getChilds().indexOf(attrRef);
				parent.getChilds().remove(idx);
							
				if (attr == null) {
					TextElement temp = new TextElement(new PositionInText(0,0), 0, "", parent, parent.getDRLDocument());
					anyThingInTheFutureText.add(temp);
					anyThingInTheFuture.put(temp, attrRef);
					parent.getChilds().add(idx, temp);
				}
				else {					
					String text = getChildsText(attr);				
					TextElement textRepresentation = new TextElement(new PositionInText(0,0), text.length(), text,parent, parent.getDRLDocument());				
					parent.getChilds().add(idx, textRepresentation);
				}
			}			
		}
			
		String text = getChildsText(clonedTemplate);
		ArrayList<PatternElement> elements = new ArrayList<PatternElement>();
		Collections.sort(anyThingInTheFutureText, new Comparator<TextElement>() {
			public int compare(TextElement o1, TextElement o2) {				
				return o1.offsetInTextRepresentation - o2.offsetInTextRepresentation;
			}		
		});	
		int prevOffset = 0;
		for (int i = 0; i < anyThingInTheFutureText.size(); ++i) {
			TextElement te = anyThingInTheFutureText.get(i);
			LangElem attrRef = anyThingInTheFuture.get(te);

			String nessesaryText = text.substring(prevOffset, te.offsetInTextRepresentation);			
		
			AnyThing anyThing = new AnyThing();
			anyThing.setLangElem(attrRef);
						
			elements.add(new NessesaryElement(nessesaryText));
			elements.add(anyThing);						
			
			prevOffset = te.offsetInTextRepresentation;
		}		
		
		if (anyThingInTheFuture.size() > 0) {
			TextElement lastTE = anyThingInTheFutureText.get(anyThingInTheFutureText.size() - 1); 
			if (lastTE.offsetInTextRepresentation != text.length()) {
				String nessesaryText = text.substring(lastTE.offsetInTextRepresentation, text.length());
				elements.add(new NessesaryElement(nessesaryText));
			}
		}
		
		return new Pattern(elements, getText());
	}

	public static LangElem getAttr(LangElem entry, String id) {
		for (Element entryElem : entry.getChilds()) {
			LangElem attr = (LangElem)entryElem;
			if (attr != null && attr.tag.equals(LangElem.ATTR)) {
				if (attr.attrs.getValue(LangElem.ID).equals(id)) {
					return attr; 
				}
			}
		}
		
		return null;
	}
	
	private static String getChildsText(LangElem le) {
		String text = "";		
		for (Element elemText : le.getChilds()) {
			elemText.offsetInTextRepresentation = text.length();			 
			text += elemText.getTextRepresentation();
		}		
		
		return text;
	}
	
	private LangElem createDirRef(String entryId, String templateId, LangElem parent) {
		LangElem dirRef = new LangElem(LangElem.DIRREF, Util.getPrefix(parent.getDRLDocument()) + LangElem.DIRREF, null, parent, parent.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)dirRef.attrs).addAttribute(LangElem.ENTRYID, LangElem.ENTRYID, LangElem.ENTRYID, "", entryId);		
		((AttributesImpl)dirRef.attrs).addAttribute(LangElem.TEMPLATEID, LangElem.TEMPLATEID, LangElem.TEMPLATEID, "", templateId);
		return dirRef;
	}
}
