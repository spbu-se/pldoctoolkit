package org.spbu.pldoctoolkit.refactor;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class StartUpRefactoring {
	public static void addInfoToCore(DRLDocument doc, String info) {
		String prefex = doc.DRLnsPrefix;
		if (!prefex.equals(""))
			prefex += ":";
		
		LangElem core = (LangElem)doc.getChilds().get(0);

		LangElem infPr = new LangElem("InfProduct", prefex + "InfProduct", null, core, doc, new AttributesImpl());
		LangElem infEl = new LangElem("InfElement", prefex + "InfElement", null, core, doc, new AttributesImpl());
		LangElem infElemRef = new LangElem("InfElemRef", prefex + "InfElemRef", null, infPr, doc, new AttributesImpl());
		
		core.getChilds().add(infPr);
		core.getChilds().add(infEl);
		infPr.getChilds().add(infElemRef);
		
		TextElement textEl = new TextElement(new PositionInText(0, 0),0,info, infPr, doc);		
		infEl.getChilds().add(textEl);		
	}
	
	public static void addInfoToProduct(DRLDocument doc) {
		String prefex = doc.DRLnsPrefix;
		if (!prefex.equals(""))
			prefex += ":";
		
		LangElem product = (LangElem)doc.getChilds().get(0);

		LangElem finalPr = new LangElem("FinalInfProduct", prefex + "FinalInfProduct", null, product, doc, new AttributesImpl());
		LangElem adapter = new LangElem("Adapter", prefex + "Adapter", null, finalPr, doc, new AttributesImpl());		
		
		product.getChilds().add(finalPr);		
		finalPr.getChilds().add(adapter);		
	}
}
