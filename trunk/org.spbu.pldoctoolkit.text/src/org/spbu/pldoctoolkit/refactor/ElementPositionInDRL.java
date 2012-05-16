package org.spbu.pldoctoolkit.refactor;

import java.util.HashMap;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;


public class ElementPositionInDRL {

	private PositionInText fromText, toText;
	
	private PositionInDRL from, to;

	private int fromIdx, toIdx;

	private HashMap<String, LangElem> nestsInSelection = null;
	
	public void init(DRLDocument doc) {
		from = doc.findByPosition(fromText);
		to = doc.findByPosition(toText);

		if (from.isInText)
			fromIdx = from.parent.getChilds().indexOf(from.elem);
		else
			fromIdx = from.parent.getChilds().indexOf(from.next);

		if (to.isInText)
			toIdx = from.parent.getChilds().indexOf(to.elem);
		else
			toIdx = from.parent.getChilds().indexOf(to.prev);
	}
	
	public void splitIfNecessary(DRLDocument doc) {
		if (from.isInText) {
			boolean isSame = false;
			if (to.isInText && from.elem == to.elem)
				isSame = true;

			((TextElement) from.elem).Split(fromText);
			Element parent = from.elem.getParent();
			from = new PositionInDRL(false, false, null, parent.getChilds()
					.get(fromIdx), parent.getChilds().get(fromIdx + 1), parent);
			++fromIdx;
			++toIdx;

			if (isSame)
				to = doc.findByPosition(toText);
		}
		if (to.isInText) {
			((TextElement) to.elem).Split(toText);
			Element parent = to.elem.getParent();
			to = new PositionInDRL(false, false, null, parent.getChilds().get(
					toIdx), parent.getChilds().get(toIdx + 1), parent);
		}
	}
	
	public void prepareNests() {
		nestsInSelection = new HashMap<String, LangElem>();
		for (int i = fromIdx; i <= toIdx; ++i) {
			Element cur = from.parent.getChilds().get(i);
			if (cur instanceof LangElem) {
				LangElem langElem = (LangElem) cur;
				if (langElem.tag.equals("Nest")) {
					String nestId = langElem.attrs.getValue("id");
					nestsInSelection.put(nestId, langElem);
				}
				TreeIterator treeIterator = new TreeIterator(cur);
				while (treeIterator.hasNext()) {
					Element elem = treeIterator.next();
					if (elem instanceof LangElem) {
						LangElem nest = (LangElem) elem;
						if (nest.tag.equals("Nest")) {
							String nestId = nest.attrs.getValue("id");
							nestsInSelection.put(nestId, nest);
						}
					}
				}
			}
		}
	}
	
	public PositionInText getFromText() {
		return fromText;
	}
	
	public PositionInText getToText() {
		return toText;
	}
	
	public PositionInDRL getFrom() {
		return from;
	}
	
	public PositionInDRL getTo() {
		return to;
	}
	
	public int getFromIdx() {
		return fromIdx;
	}
	
	public int getToIdx() {
		return toIdx;
	}
	
	public HashMap<String, LangElem> getNestsInSelection() {
		return nestsInSelection;
	}
	
	public void setFromText(PositionInText fromText) {
		this.fromText = fromText;
	}
	
	public void setToText(PositionInText toText) {
		this.toText = toText;
	}
	
	public void setFrom(PositionInDRL from) {
		this.from = from;
	}
	
	public void setTo(PositionInDRL to) {
		this.to = to;
	}
	
	public void setFromIdx(int idx) {
		this.fromIdx = idx;
	}
	
	public void setToIdx(int idx) {
		this.toIdx = idx;
	}
	
	public void setNestsInSelection(HashMap<String, LangElem> nestsInSelection) {
		this.nestsInSelection = nestsInSelection;
	}
	
}
