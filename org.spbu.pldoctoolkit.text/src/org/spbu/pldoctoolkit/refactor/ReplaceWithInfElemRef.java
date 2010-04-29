package org.spbu.pldoctoolkit.refactor;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

public class ReplaceWithInfElemRef {
	private LangElem infElem;
	
	public ReplaceWithInfElemRef(LangElem infElem) {
		
	}
	/*
	public Pattern createPattern() {		
		HashMap<TextElement, LangElem> anyThingInTheFuture = new HashMap<TextElement, LangElem>();
		ArrayList<TextElement> anyThingInTheFutureText = new ArrayList<TextElement>();
		
		LangElem clonedInfElem = (LangElem)infElem.clone(infElem.getParent());
		TreeIterator iterator = new TreeIterator(clonedInfElem);		
		
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
			
		String text = getChildsText(clonedInfElem);
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
	}*/
}
