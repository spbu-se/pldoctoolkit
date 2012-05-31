package org.spbu.pldoctoolkit.diff;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.spbu.pldoctoolkit.clones.CloneInstImpl;
import org.spbu.pldoctoolkit.filter4xml.AbstractPartOfIE;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public class FilteredElementInstImpl extends AbstractPartOfIE implements IElementInst{

	private LangElem infEl;
	private PositionInText startPosOfClone4EntireDoc;
	private PositionInText endPosOfClone4EntireDoc;
	private List<DiffResultPart> parts;

	protected FilteredElementInstImpl(LangElem infEl,
			PositionInText startPosOfClone4EntireDoc,
			PositionInText endPosOfClone4EntireDoc) {
		super(infEl, startPosOfClone4EntireDoc, endPosOfClone4EntireDoc);
		
		parts = new ArrayList<DiffResultPart>();
		this.infEl = infEl;
		this.startPosOfClone4EntireDoc = startPosOfClone4EntireDoc;
		this.endPosOfClone4EntireDoc = endPosOfClone4EntireDoc;
	}

	@Override
	public void addPart(DiffResultPart part) {
		if (!part.getIdentical()) {
			parts.add(part);
		}
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
	
	private String getFullTextOfFile() {
		return infEl.getDRLDocument().getTextRepresentation();
	}
	
	@Override
	public int getAbsoluteEndPosition() {
		return convertPositionInTextToIntPosition(endPosOfClone4EntireDoc, getFullTextOfFile());
	}

	@Override
	public int getAbsoluteStartPosition() {
		return convertPositionInTextToIntPosition(startPosOfClone4EntireDoc, getFullTextOfFile());
	}

	@Override
	public String getName() {
		String name;
		String id;
		int i = 0;
		while (!infEl.attrs.getQName(i).equals("name")) {
			i++;
		}
		name = infEl.attrs.getValue(i);
		i = 0;
		while (!infEl.attrs.getQName(i).equals("id")) {
			i++;
		}
		id = infEl.attrs.getValue(i);
		return "Text from Inf Element " + name + " [ID = " + id + "]";
	}

	@Override
	public List<DiffResultPart> getParts() {
		return parts;
	}

	@Override
	public String getPath() {
		return infEl.getDRLDocument().file.getFullPath().toString();
	}

	@Override
	public int numberOfDifferences() {		
		return parts.size();
	}

	@Override
	public void setInfEl(LangElem infEl) {
		this.infEl = infEl;
	}

	public static IElementInst createInstByPositions4EntireDoc(LangElem infEl,
			PositionInText startPos, PositionInText endPos) {
		return new FilteredElementInstImpl(infEl, startPos, endPos);
	}

}
