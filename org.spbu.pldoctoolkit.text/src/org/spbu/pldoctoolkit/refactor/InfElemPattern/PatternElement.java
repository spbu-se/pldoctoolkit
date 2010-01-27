package org.spbu.pldoctoolkit.refactor.InfElemPattern;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

public abstract class PatternElement {
	protected LangElem elem;
	
	protected PatternElement parent;
	
	public PatternElement getParent() {
		return parent;
	}
		
	public LangElem getLangElem() {
		return elem;
	}
	
	public void setLangElem(LangElem elem) {
		this.elem = elem;
	}	
}
