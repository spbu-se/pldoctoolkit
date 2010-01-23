package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.xml.sax.helpers.AttributesImpl;

public class CreateNewDictionary {
	public static LangElem perform(DRLDocument doc, String name, String id) {
		String prefex = doc.DRLnsPrefix;
		if (!prefex.equals(""))
			prefex += ":";
		
		Element parent = doc.getChilds().get(0); 
		
		LangElem dict	= new LangElem("Dictionary", prefex + "Dictionary", null, parent, doc, new AttributesImpl());
		((AttributesImpl)dict.attrs).addAttribute("id", "id", "id", "", id);
		((AttributesImpl)dict.attrs).addAttribute("name", "name", "name", "", name);
		
		parent.getChilds().add(dict);
		
		return dict;
	}
	
	public static ArrayList<DRLDocument> getPossipleDocs(ProjectContent proj) {
		ArrayList<DRLDocument> docs = new ArrayList<DRLDocument>();
		
		for (DRLDocument doc : proj.DRLDocs.values()) {
			LangElem le = (LangElem)doc.getChilds().get(0);
			if (le.tag == "DocumentationCore" || le.tag == "ProductDocumentation")
				docs.add(doc);
		}
		
		return docs;
	}
}
