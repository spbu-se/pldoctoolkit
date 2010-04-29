package org.spbu.pldoctoolkit.refactor;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class StartUpRefactoring {
	private static String XML_DECLARATION_TAG_STARTS_WITH = "<?xml";
	
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
		Util.addNewLine(core);
		core.getChilds().add(infElem);
		Util.addNewLine(core);
		
		Util.addNewLine(infPr);
		infPr.getChilds().add(infElemRef);
		Util.addNewLine(infPr);
		
		TextElement textEl = new TextElement(new PositionInText(0, 0),0,deleteXMLDeclaration(info), infPr, doc);		
		infElem.getChilds().add(textEl);
		Util.addNewLine(infElem);
	}
	//lebedkova
	//delete XML declararion tag from info string
	private static String deleteXMLDeclaration(String info) {
		String result = info.trim();
		if(result.startsWith(XML_DECLARATION_TAG_STARTS_WITH)) {
			result = result.substring(result.indexOf('>')+1, result.length());
		}
		return result;
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
		Util.addNewLine(product);
		
		Util.addNewLine(finalPr);
		finalPr.getChilds().add(adapter);	
		Util.addNewLine(finalPr);
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
		Util.addNewLine(core);
		
		Util.addNewLine(infPr);
		infPr.getChilds().add(infElemRef);	
		Util.addNewLine(infPr);
	}
}
