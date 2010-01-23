package org.spbu.pldoctoolkit.refactor;

import org.spbu.pldoctoolkit.parser.DRLLang.Element;

public class PositionInDRL {
	public final boolean isInText, isInTag;
	public final Element elem, prev, next, parent;
	
	public PositionInDRL(boolean isInText, boolean isInTag, Element elem, Element prev, Element next, Element parent){
		this.isInText = isInText;
		this.isInTag = isInTag;
		this.elem = elem;
		this.prev = prev;
		this.next = next;
		this.parent = parent;		
	}
}
