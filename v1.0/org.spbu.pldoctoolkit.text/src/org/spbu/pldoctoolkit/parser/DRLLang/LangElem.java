package org.spbu.pldoctoolkit.parser.DRLLang;

import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class LangElem extends Element{
	public final String tag, tagNS;
	public final Attributes attrs;
	
	public LangElem(String tag, String tagNS, /*int startLine, int startColumn,*/
			        PositionInText startPos,
			        Element parent, DRLDocument doc, Attributes attrs) {
		//this.startLine = startLine;
		//this.startColumn = startColumn;
		this.startPos = startPos;
		this.parent = parent;
		this.doc = doc;
		this.attrs = new AttributesImpl(attrs);		
		this.tag = tag;
		this.tagNS = tagNS;
	}
	
	@Override
	public String getTextRepresentation() {
		String res = '<' + tagNS;
		
		String temp = "";
		for (int i = 0; i < attrs.getLength(); ++i) {
			temp += " " + attrs.getQName(i) + " = " + '"' + attrs.getValue(i) + '"';
		}
		
		res += temp;
		if (childs != null) {
			res += ">";
			for (Element elem: childs){
				res += elem.getTextRepresentation();
			}
			res += "</" + tagNS + ">"; 
		}
		else {
			res += "/>";
		}
		
		return res;
	}	
}
