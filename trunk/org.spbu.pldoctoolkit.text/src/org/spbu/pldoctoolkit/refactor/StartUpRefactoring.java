package org.spbu.pldoctoolkit.refactor;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class StartUpRefactoring {
	public static void addInfoToCore(DRLDocument doc, String info,
			String infPrId, String infPrName, String infElemId, String infElemName, String infElemRefId) {
		String prefex = doc.DRLnsPrefix;
		if (!prefex.equals(""))
			prefex += ":";
		
		LangElem core = (LangElem)doc.getChilds().get(0);

		LangElem infPr = new LangElem(LangElem.INFPRODUCT, prefex + LangElem.INFPRODUCT, null, core, doc, new AttributesImpl());
		((AttributesImpl)infPr.attrs).addAttribute(LangElem.ID, LangElem.ID, LangElem.ID, "", infPrId);
		((AttributesImpl)infPr.attrs).addAttribute(LangElem.NAME, LangElem.NAME, LangElem.NAME, "", infPrName);
		
		LangElem infElem = new LangElem(LangElem.INFELEMENT, prefex + LangElem.INFELEMENT, null, core, doc, new AttributesImpl());
		((AttributesImpl)infElem.attrs).addAttribute(LangElem.ID, LangElem.ID, LangElem.ID, "", infElemId);
		((AttributesImpl)infElem.attrs).addAttribute(LangElem.NAME, LangElem.NAME, LangElem.NAME, "", infElemName);
		
		LangElem infElemRef = new LangElem(LangElem.INFELEMREF, prefex + LangElem.INFELEMREF, null, infPr, doc, new AttributesImpl());
		((AttributesImpl)infElemRef.attrs).addAttribute(LangElem.ID, LangElem.ID, LangElem.ID, "", infElemRefId);
		((AttributesImpl)infElemRef.attrs).addAttribute(LangElem.INFELEMID, LangElem.INFELEMID, LangElem.INFELEMID, "", infElemId);
		
		core.getChilds().add(infPr);
		core.getChilds().add(infElem);
		infPr.getChilds().add(infElemRef);
		
		TextElement textEl = new TextElement(new PositionInText(0, 0),0,info, infPr, doc);		
		infElem.getChilds().add(textEl);		
	}
	
	public static void addInfoToProduct(DRLDocument doc,
			String finalId, String infPrId, String infElemRefId) {
		String prefex = doc.DRLnsPrefix;
		if (!prefex.equals(""))
			prefex += ":";
		
		LangElem product = (LangElem)doc.getChilds().get(0);

		LangElem finalPr = new LangElem(LangElem.FINALINFPRODUCT, prefex + LangElem.FINALINFPRODUCT, null, product, doc, new AttributesImpl());
		((AttributesImpl)finalPr.attrs).addAttribute(LangElem.ID, LangElem.ID, LangElem.ID, "", finalId);
		((AttributesImpl)finalPr.attrs).addAttribute(LangElem.INFPRODUCTID, LangElem.INFPRODUCTID, LangElem.INFPRODUCTID, "", infPrId);
		
		LangElem adapter = new LangElem(LangElem.ADAPTER, prefex + LangElem.ADAPTER, null, finalPr, doc, new AttributesImpl());
		((AttributesImpl)adapter.attrs).addAttribute(LangElem.INFELEMREFID, LangElem.INFELEMREFID, LangElem.INFELEMREFID, "", infElemRefId);
		
		product.getChilds().add(finalPr);		
		finalPr.getChilds().add(adapter);		
	}

	public static void addInfoToCore(DRLDocument doc,
			String infPrId, String infPrName, String infElemId, String infElemName, String infElemRefId) {
		String prefex = doc.DRLnsPrefix;
		if (!prefex.equals(""))
			prefex += ":";
		
		LangElem core = (LangElem)doc.getChilds().get(0);

		LangElem infPr = new LangElem(LangElem.INFPRODUCT, prefex + LangElem.INFPRODUCT, null, core, doc, new AttributesImpl());
		((AttributesImpl)infPr.attrs).addAttribute(LangElem.ID, LangElem.ID, LangElem.ID, "", infPrId);
		((AttributesImpl)infPr.attrs).addAttribute(LangElem.NAME, LangElem.NAME, LangElem.NAME, "", infPrName);
		
		LangElem infElemRef = new LangElem(LangElem.INFELEMREF, prefex + LangElem.INFELEMREF, null, infPr, doc, new AttributesImpl());
		((AttributesImpl)infElemRef.attrs).addAttribute(LangElem.ID, LangElem.ID, LangElem.ID, "", infElemRefId);
		((AttributesImpl)infElemRef.attrs).addAttribute(LangElem.INFELEMID, LangElem.INFELEMID, LangElem.INFELEMID, "", infElemId);
		
		core.getChilds().add(infPr);
		infPr.getChilds().add(infElemRef);	
	}
}
