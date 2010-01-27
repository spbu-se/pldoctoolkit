package org.spbu.pldoctoolkit.parser.DRLLang;

import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public class Comment extends Element{
	private int length;
	private String text;
	
	public Comment(PositionInText startPos,
			           int length, String text, Element parent, DRLDocument doc) {
		//this.startLine = startLine;
		//this.startColumn = startColumn;
		this.startPos = startPos;
		this.tagStartPos = startPos;
		this.length = length;
		this.text = text;
		this.parent = parent;
		this.doc = doc;
		HeightAndWidth hw = computeHeightAndWidth(text);
		endPos = new PositionInText(startPos.line + hw.height,
				hw.height == 0 ? ( startPos.column + hw.width ) : hw.width);
		
		//this.endLine = startLine + hw.height;
		//this.endColumn = hw.height == 0 ? ( startColumn + hw.width ) : hw.width;		
	}
	
	//public static TextElement create()
	private HeightAndWidth computeHeightAndWidth(String text)
	{
		int height = 0;
		int posOfRet = 0;
		for (int i = 0; i < text.length(); ++i) {
			if (text.charAt(i) == '\n'){
				++height;
				posOfRet = i;
			}
		}
				
		return new HeightAndWidth(height, text.length() - posOfRet);
	}
	
	public String getTextRepresentation() {		
		return text;
	}
	
	public PositionInDRL findByPosition(PositionInText posToFind) {
		if (startPos.compare(posToFind) == 1 || endPos.compare(posToFind) <= 0)
			return null;
		
		return new PositionInDRL(false, true, this, null, null, parent);				
	}

	@Override
	public Element clone(Element parent) {
		TextElement newComment = new TextElement(new PositionInText(startPos), length, new String(text), parent, parent.getDRLDocument());
		return newComment;
	}
}
