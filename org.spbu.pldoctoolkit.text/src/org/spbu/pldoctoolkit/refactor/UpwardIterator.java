package org.spbu.pldoctoolkit.refactor;

import org.spbu.pldoctoolkit.parser.DRLLang.Element;

public class UpwardIterator implements IForwardIterator{
	Element cur;
	
	public UpwardIterator(Element start) {
		cur = start;
	}
	
	public Element next() {
		if (cur != null) {			
			cur = cur.getParent(); 
		}
		
		return cur;
	}
	
	public boolean hasNext() {
		return ( cur != null );
	}
}
