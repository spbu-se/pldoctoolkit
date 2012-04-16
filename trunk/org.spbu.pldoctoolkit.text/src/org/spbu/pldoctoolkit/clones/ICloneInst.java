package org.spbu.pldoctoolkit.clones;

public interface ICloneInst {
	
	String getName();

	String getPath();

	int getAbsoluteStartPosition();
	int getAbsoluteEndPosition();

	String getCloneText();

	String getFirstLineOfCloneText();


}
