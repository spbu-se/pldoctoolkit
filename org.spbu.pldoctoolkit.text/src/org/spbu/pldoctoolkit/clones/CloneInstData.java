package org.spbu.pldoctoolkit.clones;

import org.spbu.pldoctoolkit.refactor.PositionInText;

final class CloneInstData{

	final int fileNumber;
	final PositionInText startPos;
	final PositionInText endPos;
	
	CloneInstData(int fileNumber, int startLine, int startColumn, int endLine,
			int endColumn) {
		this.fileNumber = fileNumber;
		startPos = new PositionInText(startLine, startColumn);
		endPos = new PositionInText(endLine, endColumn);
	}

	public String toString() {
		return fileNumber + ":" + startPos+ "-" + endPos;
	}

}
