package org.spbu.pldoctoolkit.diff;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

public class PairGroupImpl implements IPairGroup{

	private IElementInst firstInstance;
	private IElementInst secondInstance;
	
	public PairGroupImpl(LangElem firstInfElem, LangElem secondInfElem) {
		firstInstance = new ElementInstImpl();
		firstInstance.setInfElem(firstInfElem);
		secondInstance = new ElementInstImpl();
		secondInstance.setInfElem(secondInfElem);
	}
	
	@Override
	public IElementInst getFirstInstance() {
		return firstInstance;
	}

	@Override
	public String getName() {
		if (firstInstance.numberOfDifferences() == 0) {
			return "Group of two elements with the same text";
		}
		return "Group of two elements with similar text. Number of differences: " + firstInstance.numberOfDifferences();
	}

	@Override
	public IElementInst getSecondInstance() {
		return secondInstance;
	}

	@Override
	public void setFirstInstance(IElementInst elementInst) {
		this.firstInstance = elementInst;
	}

	@Override
	public void setSecondInstance(IElementInst elementInst) {
		this.secondInstance = elementInst;
	}

}
