package org.spbu.pldoctoolkit.clones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.StringTokenizer;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public class CloneInstImpl implements ICloneInst {
	//This is cache
	private static final StringInfo stringInfo = new StringInfo();
	private static final String DELIMITERS_OF_CLONE_TOOL = " ,.\t\n*\"\'()";
	// don't delimiters: "?_|<>="

	private final PositionInText startPosOfClone4EntireDoc;
	private final PositionInText endPosOfClone4EntireDoc;
	private final LangElem infEl;
	private String cloneText;

//	public CloneInstImpl(final LangElem infEl, final PositionInText localStartPos,
//			final PositionInText localEndPos) {
//		langInfo.setTextIfNeed(infEl.getDRLDocument().getTextRepresentation());
//		
//		this.infEl = infEl;
//		this.startPosOfClone4EntireDoc = getPosition4EntireDocument(infEl.getTagStartPos(), localStartPos);
//		this.endPosOfClone4EntireDoc = getPosition4EntireDocument(infEl.getTagStartPos(), localEndPos);
//		this.infEl = infEl;
//		startPosOfClone4EntireDoc = getPosition4EntireDocument(infEl.getTagStartPos(), localStartPos);
//		PositionInText tmpEndPosOfClone4EntireDoc = getPosition4EntireDocument(infEl.getTagStartPos(), localEndPos);
//		// precalculation
//		StringBuilder rez = null;
//		String lastToken = null;
//		// BufferedReader r = new BufferedReader(new StringReader(infEl
//		// .getTextRepresentation()));
//		// for (int i = 1; i < localStartPos.line; i++) {
//		// if (r.readLine() == null)
//		// throw new IllegalStateException();
//		// }
//		// String firstCloneLine = r.readLine();
//		String firstCloneLine = langInfo
//				.getLineByNumber(startPosOfClone4EntireDoc.line);
//		rez = new StringBuilder();
//		String lastCloneLine = null;
//		if (localStartPos.line == localEndPos.line) {
//			// rez.append(firstCloneLine.substring(
//			// localStartPosOfClone.column - 1,
//			// localEndPosOfClone.column - 1));
//			rez.append(mySubstring(firstCloneLine, localStartPos.column,
//					localEndPos.column));
//			lastCloneLine = firstCloneLine;
//		} else {
//			// rez.append(firstCloneLine
//			// .substring(localStartPosOfClone.column - 1));
//			rez.append(mySubstring(firstCloneLine, localStartPos.column));
//			rez.append('\n');
//			// for (int i = localStartPos.line + 1; i < localEndPos.line; i++) {
//			// rez.append(r.readLine() + "\n");
//			// }
//			for (int i = startPosOfClone4EntireDoc.line + 1; i < tmpEndPosOfClone4EntireDoc.line; i++) {
//				rez.append(langInfo.getLineByNumber(i) + "\n");
//			}
//			// lastCloneLine = r.readLine();
//			lastCloneLine = langInfo
//					.getLineByNumber(tmpEndPosOfClone4EntireDoc.line);
//			// rez.append(lastCloneLine
//			// .substring(0, localEndPosOfClone.column - 1));
//			rez.append(mySubstring(lastCloneLine, 1, localEndPos.column));
//		}
//		lastToken = extractFirstToken(mySubstring(lastCloneLine,
//				localEndPos.column));
//		rez.append(lastToken);
//		// end precalcelation
//		this.cloneText = rez.toString();
//		this.endPosOfClone4EntireDoc = new PositionInText(tmpEndPosOfClone4EntireDoc.line,
//				tmpEndPosOfClone4EntireDoc.column + lastToken .length());
		
//	}
	public static ICloneInst createCloneInstByLocalPositions(final LangElem infEl, final PositionInText localStartPos,
			final PositionInText localEndPos){
		if (infEl == null || localStartPos == null || localEndPos == null)
			throw new NullPointerException();
		return new CloneInstImpl(infEl,getPosition4EntireDocument(infEl.getTagStartPos(), localStartPos), 
				getPosition4EntireDocument(infEl.getTagStartPos(), localEndPos));
		
	}
	
	public static ICloneInst createCloneInstByPositions4EntireDoc(final LangElem infEl, final PositionInText startPosOfClone4EntireDoc,
			final PositionInText endPosOfClone4EntireDoc){
		return new CloneInstImpl(infEl, startPosOfClone4EntireDoc, endPosOfClone4EntireDoc);
		
	}
	
	private CloneInstImpl(final LangElem infEl, final PositionInText startPosOfClone4EntireDoc,
			final PositionInText endPosOfClone4EntireDoc) {
		if (infEl == null || startPosOfClone4EntireDoc == null || endPosOfClone4EntireDoc == null)
			throw new NullPointerException();
		stringInfo.setTextIfNeed(infEl.getDRLDocument().getTextRepresentation());
		
		this.infEl = infEl;
		this.startPosOfClone4EntireDoc = startPosOfClone4EntireDoc;
		this.endPosOfClone4EntireDoc = endPosOfClone4EntireDoc;
	}
	
	public static PositionInText findTrueLocalEndPosition(final LangElem infEl, final PositionInText localEndPosFromCloneMiner){
		stringInfo.setTextIfNeed(infEl.getDRLDocument().getTextRepresentation());
		
		PositionInText endPosOfClone4EntireDoc = getPosition4EntireDocument(infEl.getTagStartPos(), localEndPosFromCloneMiner);
		String lastCloneLine = stringInfo.getLineByNumber(endPosOfClone4EntireDoc.line);
		String lastToken = extractFirstToken(mySubstring(lastCloneLine,
				endPosOfClone4EntireDoc.column));
		return new PositionInText(localEndPosFromCloneMiner.line,
				localEndPosFromCloneMiner.column + lastToken.length());
	}
	
	@Override
	public String getName() {
		return toString();
	}

	@Override
	public String toString() {
		return infEl.getDRLDocument().file.getFullPath() + ":"
				+ getStartPos4EntireDocument() + "-" + getEndPos4EntireDocument();
	}

	@Override
	public LangElem getInfEl() {
		return infEl;
	}

	@Override
	public String getCloneText() {
		if (cloneText == null){			
			StringBuilder rez = null;
			String lastToken = null;
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
			cloneText = rez.toString();
		}
		return cloneText;
	}
	
	private static String mySubstring(String string, int beginIndex) {
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
	private static String mySubstring(String string, int beginIndex, int endIndex) {
		String stWithoutTabs = string.replaceAll("\t", "    ");
		return stWithoutTabs.substring(beginIndex-1, endIndex-1);
	}

	private static String extractFirstToken(String string) {
		StringTokenizer tokenTok = new StringTokenizer(string, DELIMITERS_OF_CLONE_TOOL);
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
		return convertPositionInEntireDocToIntPosition(getStartPos4EntireDocument());
	}

	private int convertPositionInEntireDocToIntPosition(PositionInText pos) {
		int rez = 0;
		// BufferedReader r = new BufferedReader(new StringReader(text));
		// for (int i = 1; i < pos.line; i++) {
		// String line;
		// line = r.readLine();
		// rez += line.length() + 1;
		// // rez += line.length();
		// }
		rez = stringInfo.getAbsoluteIndexOfFirstSymbolOfLineNumber(pos.line);
		rez += pos.column - 1;
		// if line contains tabulations
		String line = stringInfo.getLineByNumber(pos.line);
		int indexOfTabulation = line.indexOf('\t');
		if (indexOfTabulation != -1) {
			int countOfTabs = 0;
			int columnPosition = pos.column, i = 0;
			while (columnPosition > 1) {
				switch (line.charAt(i)) {
				case '\t':
					countOfTabs++;
					columnPosition -= 4;
					if (columnPosition < 1)
						throw new IllegalStateException();
					break;
				default:
					columnPosition--;
					break;
				}
				i++;
			}
			rez -= countOfTabs * 3;
		}
		return rez;
	}

	@Override
	public int getAbsoluteEndPosition() {
		return convertPositionInEntireDocToIntPosition(getEndPos4EntireDocument());
	}

	@Override
	public PositionInText getStartPos4EntireDocument() {
		return startPosOfClone4EntireDoc;
	}

	@Override
	public PositionInText getEndPos4EntireDocument() {
		return endPosOfClone4EntireDoc;
	}

	private static PositionInText getPosition4EntireDocument(PositionInText prefixPos,
			PositionInText localPos) {
		int line = localPos.line + prefixPos.line;
		int column = localPos.column;
		if (localPos.line == 1)
			column += prefixPos.column;
		// BufferedReader r = new BufferedReader(new StringReader(
		// getFullTextOfFile()));
		// for (int i = 1; i < prefixPos.line; i++) {
		// r.readLine();
		// }
		// String lastPrefixLine = r.readLine();
		String lastPrefixLine = stringInfo.getLineByNumber(prefixPos.line);
		if (prefixPos.column < lastPrefixLine.length())
			line--;
		return new PositionInText(line, column);
	}

	private String getFullTextOfFile() {
		return infEl.getDRLDocument().getTextRepresentation();
	}

}
