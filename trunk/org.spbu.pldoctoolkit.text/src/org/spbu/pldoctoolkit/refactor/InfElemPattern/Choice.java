package org.spbu.pldoctoolkit.refactor.InfElemPattern;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

public class Choice extends PatternElement{
	private ArrayList<PatternElement> choices = new ArrayList<PatternElement>();
	private int curChoiceIdx = 0;
	
	public Choice(LangElem le, PatternElement parent) {
		this.elem = le;
		this.parent = parent;
	}
	
	public ArrayList<PatternElement> getChoices() {
		return choices;
	}
	
	public void setChoices(ArrayList<PatternElement> choices) {
		this.choices = choices;
	}
	
	public PatternElement getNextChoice() {
		if (curChoiceIdx < choices.size())			
			return choices.get(curChoiceIdx++);		
		else
			return null;
	}
	
	public PatternElement getCurChoice() {
		if (curChoiceIdx < choices.size())			
			return choices.get(curChoiceIdx);		
		else
			return null;
	}
	
	public void resetChoice() {
		curChoiceIdx = 0;
	}
}
