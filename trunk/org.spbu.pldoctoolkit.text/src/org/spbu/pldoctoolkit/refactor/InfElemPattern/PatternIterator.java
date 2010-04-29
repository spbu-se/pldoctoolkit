package org.spbu.pldoctoolkit.refactor.InfElemPattern;


import java.util.LinkedList;


import org.spbu.pldoctoolkit.refactor.Couple;

public class PatternIterator {
	private PatternElement pattern;
	//private PatternElement curElem;
	private LinkedList<PatternElement> stack = new LinkedList<PatternElement>();
	private int curIdx = -1;
	
	public PatternIterator(PatternElement pattern) {
		this.pattern = pattern;
		stack.push(pattern);
	}
	
	public PatternElement getPattern() {
		return pattern;
	}
	
	public EndElement getNextEndElement() {
		++curIdx;
		while (curIdx < stack.size() && !(stack.get(curIdx) instanceof EndElement)) {
			PatternElement cur = stack.get(curIdx);
			if (cur instanceof WithChildsElement) {
				stack.addAll(curIdx+1, ((WithChildsElement)cur).getChilds());
			}
			else { // Choice
				stack.add(curIdx+1, ((Choice)cur).getNextChoice());
			}
			
			++curIdx;
		}
		
		if (curIdx < stack.size())
			return (EndElement)stack.get(curIdx);
		else
			return null;
	}
	
	public Couple<EndElement, EndElement> getLastChoice() {
		EndElement lastEndElement = (EndElement)stack.get(curIdx);
		--curIdx;
		if (curIdx < 0)
			return null;
		PatternElement cur = stack.get(curIdx); 
		while (curIdx >= 0) {
			if (cur instanceof Choice) {
				if (((Choice)cur).getNextChoice() == null)
					((Choice)cur).resetChoice();
				else {
					stack.add(curIdx+1, ((Choice)cur).getCurChoice());
					return new Couple<EndElement, EndElement>(lastEndElement,getNextEndElement());					
				}
			}
			else if (cur instanceof EndElement) {
				lastEndElement = (EndElement)cur;
			}
			
			--curIdx;
		}
		
		return null;
	}
}
