package org.spbu.pldoctoolkit.refactor;

import java.util.Stack;

import org.spbu.pldoctoolkit.parser.DRLLang.Element;

public class TreeIterator implements IForwardIterator{
	Element start, cur;
	Stack<Element> stack;
	
	public TreeIterator(Element elem) {
		start = elem;
		stack = new Stack<Element>();
		if (start.getChilds() != null)
			stack.addAll(start.getChilds());
	}
	
	public Element next() {
		if (stack.size() > 0) {
			Element elem = stack.pop();
			
			if (elem.getChilds() != null)
				stack.addAll(elem.getChilds());
			
			return elem;
		}
		else
			return null;
		//for (Element element : cur.getChilds())
	}
	
	public boolean hasNext() {
		return ( stack.size() > 0 );
	}
}
