package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class CreateDirEntry {
	public static void createEntry(LangElem dir, String id, ArrayList<Couple<String, String>> attrIdsAndContents) {
		if (!isValidId(dir, id))
			return;
		
		LangElem entry = new LangElem(LangElem.ENTRY, Util.getPrefix(dir.getDRLDocument()) + LangElem.ENTRY, null, dir, dir.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)entry.attrs).addAttribute(LangElem.ID, LangElem.ID, LangElem.ID, "", id);		
		dir.getChilds().add(entry);
		
		addAttrs(entry, attrIdsAndContents);
	}
	
	public static void addAttrs(LangElem entry, ArrayList<Couple<String, String>> attrIdsAndContents) {
		for (Couple<String, String> attrIdAndContent : attrIdsAndContents) {
			LangElem attr = createAttr(entry, attrIdAndContent.fst);
			attr.getChilds().add(
					new TextElement(new PositionInText(0,0), 0, attrIdAndContent.snd, attr, attr.getDRLDocument()));
			entry.getChilds().add(attr);
		}
	}
	
	private static LangElem createAttr(LangElem entry, String id) {
		LangElem attr = new LangElem(LangElem.ATTR, Util.getPrefix(entry.getDRLDocument()) + LangElem.ATTR, null, entry, entry.getDRLDocument(), new AttributesImpl());
		((AttributesImpl)attr.attrs).addAttribute(LangElem.ID, LangElem.ID, LangElem.ID, "", id);		
		return attr;
	}
	
	public static String getEntryId(LangElem dir) {
		return Util.getId(dir.getChilds(), LangElem.ENTRY);
	}
	
	public static boolean isValidId(LangElem dir, String id) {
		return Util.isValidId(dir.getChilds(), LangElem.ENTRY, id);
	}
}
