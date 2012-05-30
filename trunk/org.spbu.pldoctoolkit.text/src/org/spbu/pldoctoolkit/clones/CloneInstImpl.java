package org.spbu.pldoctoolkit.clones;

import java.util.StringTokenizer;

import org.spbu.pldoctoolkit.filter4xml.AbstractPartOfIE;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public class CloneInstImpl extends AbstractPartOfIE implements ICloneInst {
	private static final String DELIMITERS_OF_CLONE_TOOL = " ,.\t\n*\"\'()";
	// don't delimiters: "?_|<>="

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
		super(infEl, startPosOfClone4EntireDoc, endPosOfClone4EntireDoc);
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
	
	private static String extractFirstToken(String string) {
		StringTokenizer tokenTok = new StringTokenizer(string, DELIMITERS_OF_CLONE_TOOL);
		if (!tokenTok.hasMoreTokens())
			return "";
		return tokenTok.nextToken();
	}

	@Override
	public String getPath() {
		return getInfEl().getDRLDocument().file.getFullPath().toString();
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

}
