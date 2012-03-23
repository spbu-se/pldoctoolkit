package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;

public class HandleDIffWithAnotherInfElem {

	public HandleDIffWithAnotherInfElem() {
	}

	public static ArrayList<DRLDocument> getPossibleDocs(ProjectContent proj) {
		ArrayList<DRLDocument> docs = new ArrayList<DRLDocument>();

		for (DRLDocument doc : proj.DRLDocs.values()) {
			LangElem le = (LangElem) doc.getChilds().get(0);
			if (le.tag == "DocumentationCore")
				docs.add(doc);
		}

		return docs;
	}

}
