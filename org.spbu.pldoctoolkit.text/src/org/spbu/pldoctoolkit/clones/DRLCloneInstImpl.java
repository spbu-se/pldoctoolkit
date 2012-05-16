package org.spbu.pldoctoolkit.clones;

import java.util.StringTokenizer;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public class DRLCloneInstImpl implements ICloneInst {

	private PositionInText absoluteStartPosOfClone;
	private PositionInText absoluteEndPosOfClone;
	private final LangElem infEl;
	private String cloneText;

	/*public DRLCloneInstImpl(LangElem infEl, PositionInText localStartPos,
			PositionInText localEndPos) {
		this.infEl = infEl;
		this.absoluteStartPosOfClone = localStartPos;
		this.absoluteStartPosOfClone = extractCloneTextAndGetTrueEndPosit(
				localStartPos, localEndPos);
	}*/
	
	public DRLCloneInstImpl(LangElem infEl, PositionInText absoluteStartPos,
			PositionInText absoluteEndPos, String cloneText) {
		this.infEl = infEl;
		this.absoluteStartPosOfClone = absoluteStartPos;
		this.absoluteEndPosOfClone = absoluteEndPos;
		this.cloneText = cloneText;
	}

	@Override
	public String getName() {
		return toString();
	}

	@Override
	public String toString() {
		return infEl.getDRLDocument().file.getFullPath() + ":"
				+ getAbsoluteStartPos() + "-" + getAbsoluteEndPos();
	}

	@Override
	public String getFirstLineOfCloneText() {
		StringTokenizer lineTokenizer = new StringTokenizer(getCloneText(),
				"\n");
		return lineTokenizer.nextToken();
	}

	@Override
	public LangElem getInfEl() {
		return infEl;
	}

	@Override
	public String getCloneText() {
		return cloneText;
	}
	
	@Override
	public String getPath() {
		return infEl.getDRLDocument().file.getFullPath().toString();
	}

	@Override
	public int getAbsoluteStartPosition() {
		return convertPositionInTextToIntPosition(absoluteStartPosOfClone,
				getFullTextOfFile());
	}

	private int convertPositionInTextToIntPosition(PositionInText pos,
			String text) {
		int rez = 0;
		StringTokenizer lineTok = new StringTokenizer(text, "\n");
		for (int i = 1; i < pos.line; i++) {
			String line = lineTok.nextToken();
			rez += line.length() + 1;
		}
		rez += pos.column;
		return rez;
	}

	@Override
	public int getAbsoluteEndPosition() {
		return convertPositionInTextToIntPosition(absoluteEndPosOfClone,
				getFullTextOfFile());
	}

	@Override
	public PositionInText getAbsoluteStartPos() {
		return absoluteStartPosOfClone;
	}

	@Override
	public PositionInText getAbsoluteEndPos() {
		return absoluteEndPosOfClone;
	}

	private String getFullTextOfFile() {
		return infEl.getDRLDocument().getTextRepresentation();
	}

}