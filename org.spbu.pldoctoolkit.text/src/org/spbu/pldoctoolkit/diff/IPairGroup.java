package org.spbu.pldoctoolkit.diff;

public interface IPairGroup {

	String getName();
	IElementInst getFirstInstance();
	IElementInst getSecondInstance();
	void setFirstInstance(IElementInst elementInst);
	void setSecondInstance(IElementInst elementInst);
	
}
