package org.spbu.pldoctoolkit.refactor.InfElemPattern;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

public class WithChildsElement extends PatternElement{
	private ArrayList<PatternElement> childs = new ArrayList<PatternElement>();
	private int curChildIdx = 0;
	
	public WithChildsElement(LangElem le, PatternElement parent) {
		this.elem = le;
		this.parent = parent;
	}
	
	public ArrayList<PatternElement> getChilds() {
		return childs;
	}
	
	public void setChilds(ArrayList<PatternElement> childs) {
		this.childs = childs;
	}
	
	public PatternElement getNextChild() {
		if (curChildIdx < childs.size())			
			return childs.get(curChildIdx++);		
		else
			return null;		
	}
	public void resetChilds() {
		curChildIdx = 0;
	}
}
