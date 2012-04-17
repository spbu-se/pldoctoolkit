package org.spbu.pldoctoolkit.clones;

import java.util.StringTokenizer;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public class CloneInstImpl implements ICloneInst {

	private final PositionInText localStartPosOfClone;
	private final PositionInText localEndPosOfClone;
	private final LangElem infEl;
	private String cloneText;

	public CloneInstImpl(LangElem infEl, PositionInText localStartPos,
			PositionInText localEndPos) {
		this.infEl = infEl;
		this.localStartPosOfClone = localStartPos;
		this.localEndPosOfClone = extractCloneTextAndGetTrueEndPosit(
				localStartPos, localEndPos);
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
	public String getCloneText() {
		return cloneText;
	}

	private PositionInText extractCloneTextAndGetTrueEndPosit(
			PositionInText localStartPosOfClone,
			PositionInText localEndPosOfClone) {
		StringTokenizer lineTok = new StringTokenizer(infEl
				.getTextRepresentation(), "\n");
		for (int i = 1; i < localStartPosOfClone.line; i++) {
			lineTok.nextToken();
		}
		StringBuilder rez = new StringBuilder();
		String firstCloneLine = lineTok.nextToken();
		String lastCloneLine = null;
		if (localStartPosOfClone.line == localEndPosOfClone.line) {
			rez.append(firstCloneLine.substring(
					localStartPosOfClone.column - 1,
					localEndPosOfClone.column - 1));
			lastCloneLine = firstCloneLine;
		} else {
			rez.append(firstCloneLine
					.substring(localStartPosOfClone.column - 1));
			for (int i = localStartPosOfClone.line; i < localEndPosOfClone.line; i++) {
				rez.append(lineTok.nextToken() + "\n");
			}
			lastCloneLine = lineTok.nextToken();
			rez.append(lastCloneLine
					.substring(0, localEndPosOfClone.column - 1));
		}
		String lastToken = extractFirstToken(lastCloneLine
				.substring(localEndPosOfClone.column - 1));
		rez.append(lastToken);
		cloneText = rez.toString();
		return new PositionInText(localEndPosOfClone.line,
				localEndPosOfClone.column + lastToken.length());
	}

	private String extractFirstToken(String string) {
		StringTokenizer tokenTok = new StringTokenizer(string, " _,.");
		if (!tokenTok.hasMoreTokens())
			return "";
		return tokenTok.nextToken();
	}

	@Override
	public String getPath() {
		return infEl.getDRLDocument().file.getFullPath().toString();
	}

	@Override
	public int getAbsoluteStartPosition() {
		return convertPositionInTextToIntPosition(getAbsoluteStartPos(),
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
		return convertPositionInTextToIntPosition(getAbsoluteEndPos(),
				getFullTextOfFile());
	}

	private PositionInText getAbsoluteStartPos() {
		return getAbsolutePosition(infEl.getTagStartPos(), localStartPosOfClone);
	}

	private PositionInText getAbsoluteEndPos() {
		return getAbsolutePosition(infEl.getTagStartPos(), localEndPosOfClone);
	}

	private PositionInText getAbsolutePosition(PositionInText prefixPos,
			PositionInText localPos) {
		int line = localPos.line + prefixPos.line;
		int column = localPos.column;
		if (localPos.line == 1)
			column += prefixPos.column;
		StringTokenizer lineTok = new StringTokenizer(getFullTextOfFile(), "\n");
		for (int i = 1; i < prefixPos.line; i++) {
			lineTok.nextToken();
		}
		String lastPrefixLine = lineTok.nextToken();
		if (prefixPos.column < lastPrefixLine.length())
			line--;
		return new PositionInText(line, column);
	}

	private String getFullTextOfFile() {
		return infEl.getDRLDocument().getTextRepresentation();
	}

}
