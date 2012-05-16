package org.spbu.pldoctoolkit.clones;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public interface ICloneInst {
	
	String getName();

	String getPath();

	int getAbsoluteStartPosition();
	int getAbsoluteEndPosition();
	LangElem getInfEl();

	String getCloneText();

	String getFirstLineOfCloneText();

	PositionInText getAbsoluteStartPos();

	PositionInText getAbsoluteEndPos();
	
}
