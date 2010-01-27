package org.spbu.pldoctoolkit.refactor.pattern;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

public abstract class PatternElement {
	protected int offset, lenght;
	protected String text;
	protected LangElem elem;
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public int getLength() {
		return lenght;
	}
	
	public void setLength(int length) {
		this.lenght = length;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public LangElem getLangElem() {
		return elem;
	}
	
	public void setLangElem(LangElem elem) {
		this.elem = elem;
	}
}
