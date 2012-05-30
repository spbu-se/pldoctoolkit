package org.spbu.pldoctoolkit.clones;

import org.spbu.pldoctoolkit.filter4xml.IPartOfInfEl;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public interface ICloneInst extends IPartOfInfEl{
	
	String getName();

	String getPath();

	int getAbsoluteStartPosition();
	int getAbsoluteEndPosition();

	
}
