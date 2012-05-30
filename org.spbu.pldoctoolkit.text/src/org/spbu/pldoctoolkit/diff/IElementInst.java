package org.spbu.pldoctoolkit.diff;

import java.util.List;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public interface IElementInst {

	PositionInText getAbsoluteStartPos();

	PositionInText getAbsoluteEndPos();

	void addPart(DiffResultPart part);

	int numberOfDifferences();
	
	String getName();

	String getPath();
	
	LangElem getInfElem();
	
	void setInfElem(LangElem infElem);

	int getAbsoluteStartPosition();
	
	int getAbsoluteEndPosition();

	List<DiffResultPart> getParts();
}
