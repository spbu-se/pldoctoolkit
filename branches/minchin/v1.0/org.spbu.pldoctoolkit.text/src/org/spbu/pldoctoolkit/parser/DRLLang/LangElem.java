package org.spbu.pldoctoolkit.parser.DRLLang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

public class LangElem extends Element{
	public static final String DOCUMENTATIONCORE = "DocumentationCore";
	public static final String PRODUCTDOCUMENTATION = "ProductDocumentation";
	public static final String PRODUCTLINE = "ProductLine";
	public static final String INFPRODUCT = "InfProduct";
	public static final String FINALINFPRODUCT = "FinalInfProduct";
	public static final String INFELEMENT = "InfElement";
	public static final String INFELEMREF = "InfElemRef";
	public static final String INFELEMREFGROUP = "InfElemRefGroup";
	public static final String NEST = "Nest";
	public static final String ADAPTER = "Adapter";
	public static final String REPLACENEST = "Replace-Nest";
	public static final String INSERTAFTER = "Insert-After";
	public static final String INSERTBEFORE = "Insert-Before";
	public static final String DIRECTORY = "Directory";
	public static final String DIRTEMPLATE = "DirTemplate";
	public static final String DIRREF = "DirRef";
	public static final String ATTR = "Attr";
	public static final String ATTRREF = "AttrRef";
	public static final String ENTRY = "Entry";
	public static final String DICTIONARY = "Dictionary";
	public static final String DICTREF = "DictRef";
	
	public static final String TAGS[] = {
		DOCUMENTATIONCORE,
		PRODUCTDOCUMENTATION,
		PRODUCTLINE,
		INFPRODUCT,
		FINALINFPRODUCT,
		INFELEMENT,
		INFELEMREF,
		INFELEMREFGROUP,
		NEST,
		ADAPTER,
		REPLACENEST,
		INSERTAFTER,
		INSERTBEFORE,
		DIRECTORY,
		DIRTEMPLATE,
		DIRREF,
		ATTR,
		ATTRREF,
		ENTRY,
		DICTIONARY,
		DICTREF
	};
	
///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static final String ID = "id";
	public static final String NAME= "name";
	public static final String ATTRID = "attrid";
	public static final String DIRECTORYID = "directoryid";
	public static final String INFPRODUCTID = "infproductid";
	public static final String INFELEMREFID = "infelemrefid";
	public static final String INFELEMID = "infelemid";
	public static final String ENTRYID = "entryid";
	public static final String TEMPLATEID = "templateid";
	
///////////////////////////////////////////////////////////////////////////////////////////////////////	

	public final String tag, tagNS;
	public final Attributes attrs;
	public HashMap<String, String> prefex;
	
	
	@Override
	public Element clone(Element parent) {
		LangElem newElem = new LangElem(tag, tagNS, new PositionInText(startPos), parent, parent.getDRLDocument(), new AttributesImpl(attrs));
		newElem.setEndPos(new PositionInText(endPos));
		newElem.setTagStartPos(new PositionInText(tagStartPos));
		
		if (newElem.getChilds() == null)
			newElem.childs = new ArrayList<Element>();
		
		for (Element elem: childs){
			newElem.childs.add(elem.clone(newElem));
		}
		
		return newElem;		
	}
	
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
		
		if (prefex != null)
			res += getTextXMLNS();		
		
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
	
	private String getTextXMLNS() {
		String res = "";
		Set<Entry<String, String>> entrys = prefex.entrySet();
		for (Entry<String, String> entry : entrys) {
			res += " xmlns";
			if (entry.getKey().equals(""))
				res += "=";
			else
				res += ":" + entry.getKey() + "=";
			res += '"' + entry.getValue() + '"';
		}
		
		return res;
	}
}
