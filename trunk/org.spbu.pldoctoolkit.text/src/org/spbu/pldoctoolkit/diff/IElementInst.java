package org.spbu.pldoctoolkit.diff;

import java.util.List;

import org.spbu.pldoctoolkit.filter4xml.IPartOfInfEl;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public interface IElementInst extends IPartOfInfEl{

	PositionInText getStartPos4EntireDocument();

	PositionInText getEndPos4EntireDocument();

	void addPart(DiffResultPart part);

	int numberOfDifferences();
	
	String getName();

	String getPath();
	
	LangElem getInfEl();
	
	void setInfEl(LangElem infElem);

	int getAbsoluteStartPosition();
	
	int getAbsoluteEndPosition();

	List<DiffResultPart> getParts();
}
