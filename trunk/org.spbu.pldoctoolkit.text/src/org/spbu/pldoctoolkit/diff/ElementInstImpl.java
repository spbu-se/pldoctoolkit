package org.spbu.pldoctoolkit.diff;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public class ElementInstImpl implements IElementInst {

	private List<DiffResultPart> parts;
	private LangElem infElem;

	public ElementInstImpl() {
		parts = new ArrayList<DiffResultPart>();
	}

	@Override
	public void addPart(DiffResultPart part) {
		parts.add(part);
	}

	@Override
	public PositionInText getAbsoluteEndPos() {
		PositionInText localPosition = getLocalEndPos();
		return getAbsolutePosition(infElem.getTagStartPos(), localPosition);
		// return new PositionInText(infElem.getTagStartPos().line + line,
		// column);
	}

	private PositionInText getLocalEndPos() {
		DiffResultPart part;
		if (parts.get(parts.size() - 1).getEndLineNumber() != Difference.NONE) {
		 part = parts.get(parts.size() - 1);
		} else {
			part = parts.get(parts.size() - 2);
		}
		int line = part.getEndLineNumber() + 1;
		int column = infElem.getTextRepresentation().split("\n")[line].length() - infElem.getTagStartPos().column + 1;
		System.out.println("getLocalEndPos");
		System.out.println(line);
		System.out.println(column);
		return new PositionInText(line, column);
	}

	private PositionInText getAbsolutePosition(PositionInText prefixPos,
			PositionInText localPos) {
		int line = localPos.line + prefixPos.line;
		int column = localPos.column;
		// if (localPos.line == 1)
		column += prefixPos.column;
		/*
		 * StringTokenizer lineTok = new StringTokenizer(getFullTextOfFile(),
		 * "\n"); for (int i = 1; i < prefixPos.line; i++) {
		 * lineTok.nextToken(); } String lastPrefixLine = lineTok.nextToken();
		 * if (prefixPos.column < lastPrefixLine.length()) line--;
		 */
		return new PositionInText(line, column);
	}

	@Override
	public PositionInText getAbsoluteStartPos() {
		PositionInText localPosition = getLocalStartPos();
		return getAbsolutePosition(infElem.getTagStartPos(), localPosition);
		// return new PositionInText(infElem.getTagStartPos().line + line,
		// column);
	}

	private PositionInText getLocalStartPos() {
		int line = parts.get(0).getStartLineNumber() + 1;
		int column = infElem.getTextRepresentation().split("\n")[line].length() - infElem.getTextRepresentation().split("\n")[line].trim().length() - infElem.getTagStartPos().column + 1;
		System.out.println("getLocalStartPos");
		System.out.println(line);
		System.out.println(column);
		return new PositionInText(line, column);
	}

	@Override
	public String getName() {
		/*int start = convertPositionInTextToIntPosition(getLocalStartPos(),
				infElem.getTextRepresentation());
		int end = convertPositionInTextToIntPosition(getLocalEndPos(), infElem
				.getTextRepresentation());*/		
		String name;
		String id;
		int i = 0;
		while (!infElem.attrs.getQName(i).equals("name")) {
			i++;
		}
		name = infElem.attrs.getValue(i);
		i = 0;
		while (!infElem.attrs.getQName(i).equals("id")) {
			i++;
		}
		id = infElem.attrs.getValue(i);
		return "Text from Inf Element " + name + " [ID = " + id +"]"; 
	}

	@Override
	public int numberOfDifferences() {
		int number = 0;
		for (DiffResultPart part : parts) {
			if (!part.getIdentical()) {
				number++;
			}
		}
		return number;
	}

	@Override
	public String getPath() {
		return infElem.getDRLDocument().file.getFullPath().toString();
	}

	@Override
	public void setInfElem(LangElem infElem) {
		this.infElem = infElem;
	}

	private int convertPositionInTextToIntPosition(PositionInText pos,
			String text) {
		int rez = 0;
		StringTokenizer lineTok = new StringTokenizer(text, "\n");
		for (int i = 1; i < pos.line; i++) {
			String line = lineTok.nextToken();
			rez += line.length() + 1;
		}
		rez += pos.column - 1;
		return rez;
	}

	@Override
	public int getAbsoluteEndPosition() {
		return convertPositionInTextToIntPosition(getAbsoluteEndPos(),
				getFullTextOfFile());
	}

	@Override
	public int getAbsoluteStartPosition() {
		return convertPositionInTextToIntPosition(getAbsoluteStartPos(),
				getFullTextOfFile());
	}

	private String getFullTextOfFile() {
		return infElem.getDRLDocument().getTextRepresentation();
	}

	@Override
	public List<DiffResultPart> getParts() {
		return parts;
	}

	@Override
	public LangElem getInfElem() {
		return infElem;
	}

}
