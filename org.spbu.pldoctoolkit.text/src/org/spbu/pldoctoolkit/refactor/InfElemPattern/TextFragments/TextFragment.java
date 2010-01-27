package org.spbu.pldoctoolkit.refactor.InfElemPattern.TextFragments;

import org.spbu.pldoctoolkit.refactor.InfElemPattern.EndElement;

public abstract class TextFragment {
	private String text;
	private int offset, lenght;	
	private EndElement endElement;
	
	public EndElement getEndElement() {
		return endElement;
	}
	
	public void setEndElement(EndElement endElement) {
		this.endElement = endElement;
	}
	
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
}
