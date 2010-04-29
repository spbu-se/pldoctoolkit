package org.spbu.pldoctoolkit.refactor.InfElemPattern;

import java.util.Stack;

import org.spbu.pldoctoolkit.refactor.InfElemPattern.TextFragments.TextFragment;

public class PatternMatcher {
	private PatternElement pattern;
	private Stack<TextFragment> fragments = new Stack<TextFragment>();
	private String text;
	private PatternIterator it;
	
	public PatternMatcher(String text, PatternElement pattern) {
		this.text = text;
		this.pattern = pattern;
		it = new PatternIterator(pattern);
	}
	
	public String getText() {
		return text;
	}
	
	public PatternElement getPattern() {
		return pattern;
	}
/*	
	public boolean nextMatch() {
		boolean res = findPos(0, 0, false, !isFirstMatch);		
		isFirstMatch = false;
		if (!res)
			isFirstMatch = true;
		return res;
	}
	
	public boolean findPos(int idx, int offset, boolean isPrevAny, boolean moveNext) {
		
		Couple<EndElement, EndElement> movePair = it.getLastChoice();
		
		
///////////////////////////////////////////////////////////////////////////////		
		if (idx > elements.size()-1) {
			if (moveNext)
				return false;
			
			PatternElement prev = elements.get(idx-1);
			if (isPrevAny) {				 
				prev.setLength(text.length() - prev.getOffset());
				prev.setText(text.substring(prev.getOffset(), prev.getOffset() + prev.getLength()));
				return true;
			}
			else {
				if (prev.getOffset() + prev.getLength() == text.length())
					return true;
				else
					return false;
			}
		}
		
		PatternElement curElem = elements.get(idx);
		if (curElem instanceof AnyThing) {
			curElem.setOffset(offset);
			if (!moveNext)
				curElem.setLength(0);
			return findPos(idx+1, offset + curElem.getLength(), true, moveNext);
		}
		else {
			if (moveNext) {
				if (isPrevAny) {
					int curOffset;
					int pos = offset;
					boolean wasMoved = false;
					while (true) {
						if (findPos(idx+1, pos + curElem.getLength(), false, !wasMoved)) {					
							PatternElement prev = elements.get(idx-1); 
							prev.setLength(pos - prev.getOffset());
							prev.setText(text.substring(prev.getOffset(), prev.getOffset() + prev.getLength()));
							return true;
						}
					
						curOffset = pos + 1;
						if (curOffset + curElem.getLength() > text.length())
							return false;
						pos = text.indexOf(curElem.getText(), curOffset);
						if (pos == -1)
							return false;
						
						curElem.setOffset(pos);
						wasMoved = true;
					}					
				}
				else {
						return findPos(idx+1, offset + curElem.getLength(), false, moveNext);
				}
			}
			else {
				if (isPrevAny) {
					int pos = 0;
					int curOffset = offset;
					while (true) {
						pos = text.indexOf(curElem.getText(), curOffset);						
						if (pos == -1)
							return false;
						
						curElem.setOffset(pos);
					
						if (findPos(idx+1, pos + curElem.getLength(), false, moveNext)){
							PatternElement prev = elements.get(idx-1); 
							prev.setLength(pos - prev.getOffset());
							prev.setText(text.substring(prev.getOffset(), prev.getOffset() + prev.getLength()));
							return true;
						}
						
						curOffset = pos + 1;
					}
				}
				else {
					if (offset + curElem.getLength() > text.length())
						return false;
					if (text.substring(offset, offset + curElem.getLength()).equals(curElem.getText())) {
						curElem.setOffset(offset);
						return findPos(idx+1, offset + curElem.getLength(), false, moveNext);
					}
					else
						return false;
				}
			}
		}
	}
	*/
}
