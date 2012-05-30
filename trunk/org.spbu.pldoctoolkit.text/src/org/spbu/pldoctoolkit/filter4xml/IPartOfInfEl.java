package org.spbu.pldoctoolkit.filter4xml;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public interface IPartOfInfEl {

	PositionInText getStartPos4EntireDocument();

	PositionInText getEndPos4EntireDocument();

	String getText();

	LangElem getInfEl();

}
