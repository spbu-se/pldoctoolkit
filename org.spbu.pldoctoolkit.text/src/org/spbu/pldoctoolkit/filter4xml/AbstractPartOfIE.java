package org.spbu.pldoctoolkit.filter4xml;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public class AbstractPartOfIE implements IPartOfInfEl {
	
	//This is cache
	protected static final StringInfo stringInfo = new StringInfo();

	private final PositionInText startPosOfClone4EntireDoc;
	private final PositionInText endPosOfClone4EntireDoc;
	private final LangElem infEl;
	private String text;
	
	protected AbstractPartOfIE(final LangElem infEl, final PositionInText startPosOfClone4EntireDoc,
			final PositionInText endPosOfClone4EntireDoc) {
		if (infEl == null || startPosOfClone4EntireDoc == null || endPosOfClone4EntireDoc == null)
			throw new NullPointerException();
		stringInfo.setTextIfNeed(infEl.getDRLDocument().getTextRepresentation());
		
		this.infEl = infEl;
		this.startPosOfClone4EntireDoc = startPosOfClone4EntireDoc;
		this.endPosOfClone4EntireDoc = endPosOfClone4EntireDoc;
	}


	@Override
	public String getText() {
		if (text == null){			
			StringBuilder rez = null;
			String firstCloneLine = stringInfo.getLineByNumber(startPosOfClone4EntireDoc.line);
			rez = new StringBuilder();
			String lastCloneLine = null;
			if (startPosOfClone4EntireDoc.line == endPosOfClone4EntireDoc.line) {
				rez.append(mySubstring(firstCloneLine, startPosOfClone4EntireDoc.column,
						endPosOfClone4EntireDoc.column));
				lastCloneLine = firstCloneLine;
			} else {
				rez.append(mySubstring(firstCloneLine, startPosOfClone4EntireDoc.column));
				rez.append('\n');
				for (int i = startPosOfClone4EntireDoc.line + 1; i < endPosOfClone4EntireDoc.line; i++) {
					rez.append(stringInfo.getLineByNumber(i) + "\n");
				}
				lastCloneLine = stringInfo.getLineByNumber(endPosOfClone4EntireDoc.line);
				rez.append(mySubstring(lastCloneLine, 1, endPosOfClone4EntireDoc.column));
			}
			text = rez.toString();
		}
		return text;
	}
	
	protected static String mySubstring(String string, int beginIndex) {
		String stWithoutTabs = string.replaceAll("\t", "    ");
		return stWithoutTabs.substring(beginIndex-1);
	}
	
	/**
	 * 
	 * @param string
	 * @param beginIndex - first index equals 1
	 * @param endIndex
	 * @return
	 */
	protected static String mySubstring(String string, int beginIndex, int endIndex) {
		String stWithoutTabs = string.replaceAll("\t", "    ");
		return stWithoutTabs.substring(beginIndex-1, endIndex-1);
	}

	@Override
	public LangElem getInfEl() {
		return infEl;
	}

	@Override
	public PositionInText getStartPos4EntireDocument() {
		return startPosOfClone4EntireDoc;
	}
	
	@Override
	public String toString() {
		return infEl.getDRLDocument().file.getFullPath() + ":"
				+ getStartPos4EntireDocument() + "-" + getEndPos4EntireDocument();
	}


	@Override
	public PositionInText getEndPos4EntireDocument() {
		return endPosOfClone4EntireDoc;
	}

}
