package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;
import java.util.HashMap;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.xml.sax.helpers.AttributesImpl;

public class SelectIntoCondBlock {
	private String prefex;
	private PositionInText startPosition;
	private PositionInText endPosition;
	private DRLDocument doc;
	private PositionInDRL from, to;

	// number of "from", "to" in list of "parent"
	private int fromIdx, toIdx;
	public LangElem targetElem;
	public String elementType;

	private String variableName;
	private String variableValue;
	private Element parent = null;
	private ArrayList<LangElem> finalInfProducts;
	HashMap<String, Boolean> setNewValue;

	public void setValidationParams(DRLDocument ldoc,
			PositionInText startPosition, PositionInText endPosition) {
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.doc = ldoc;

	}

	public void init() {
		from = doc.findByPosition(startPosition);
		to = doc.findByPosition(endPosition);

		if (from.isInText)
			fromIdx = from.parent.getChilds().indexOf(from.elem);
		else
			fromIdx = from.parent.getChilds().indexOf(from.next);

		if (to.isInText)
			toIdx = from.parent.getChilds().indexOf(to.elem);
		else
			toIdx = from.parent.getChilds().indexOf(to.prev);

		UpwardIterator searchInfElemiterator;
		if (from.isInText)
			searchInfElemiterator = new UpwardIterator(from.elem);
		else if (from.next != null)
			searchInfElemiterator = new UpwardIterator(from.next);
		else
			return;

		parent = searchInfElemiterator.next();
		while (parent != null) {
			if (parent instanceof LangElem) {
				LangElem langElem = (LangElem) parent;
				if (langElem.tag.equals(LangElem.INFELEMENT)
						|| langElem.tag.equals(LangElem.INFPRODUCT))
					break;
			}
			parent = searchInfElemiterator.next();
		}

	}

	public boolean validate() {
		init();

		boolean selectedBorderInTag = from.isInTag || to.isInTag;
		boolean selectedOneLevelElems = to.parent == from.parent;
		boolean selectedContainsDrlElems = containsDrlElems();
		if (!selectedBorderInTag && selectedOneLevelElems
				&& !selectedContainsDrlElems) {
			targetElem = (LangElem) from.next;
			elementType = targetElem.tag;
			for (String s : LangElem.TAGS) {
				if (elementType.equals(s))
					return false;
			}
			return true;
		}
		return false;
	}

	private boolean containsDrlElems() {
		// for(int i = fromIdx; i < toIdx; i++) {
		// parent
		// }
		return false;
	}

	public void setParams(String variable, String value,
			ArrayList<LangElem> finalInfProducts,
			HashMap<String, Boolean> hashMap) {
		this.variableName = variable;
		this.variableValue = value;
		this.finalInfProducts = finalInfProducts;
		this.setNewValue = hashMap;
	}

	public void perform() {
		if (validate()) {
			// splitIfNecessary();

			prefex = doc.DRLnsPrefix;
			if (!prefex.equals(""))
				prefex += ":";
			// replace selected text with condBlock (with selected block as
			// child)
			ArrayList<Element> removedChilds = from.parent.removeChilds(
					fromIdx, toIdx);
			LangElem condBlock = createCondBlock();
			from.parent.getChilds().add(fromIdx, condBlock);
			condBlock.appendChilds(removedChilds);
			//
			for (LangElem fip : finalInfProducts) {
				String fipName = fip.attrs.getValue(LangElem.ID);
				if (!this.setNewValue.containsKey(fipName)) {
					addSetValue(fip);
					Util.addNewLine(fip);
					
				} else if (setNewValue.get(fipName)) {
					// replace old SetValue element
					for (Element child : fip.getChilds()) {
						if (child.getClass().equals(LangElem.class)) {
							LangElem childElem = (LangElem) child;
							if (childElem.tag.equals(LangElem.SETVALUE)) {
								int idx = childElem.attrs.getIndex(LangElem.ID);
								if (idx != -1
										&& childElem.attrs.getValue(idx)
												.equals(variableName)) {
									fip.getChilds().remove(child);
									
									addSetValue(fip);
									Util.addNewLine(fip);
								}
							}
						}
					}
				}
			}
		}
	}

	private void addSetValue(LangElem fip) {
		LangElem setValue = new LangElem(LangElem.SETVALUE, prefex
				+ LangElem.SETVALUE, null, fip, fip.getDRLDocument(),
				new AttributesImpl());
		((AttributesImpl) setValue.attrs).addAttribute(LangElem.ID,
				LangElem.ID, LangElem.ID, "", variableName);
		((AttributesImpl) setValue.attrs).addAttribute(LangElem.VALUE,
				LangElem.VALUE, LangElem.VALUE, "", variableValue);
		fip.getChilds().add(setValue);
	}

	private LangElem createCondBlock() {
		LangElem condBlock = new LangElem(LangElem.CONDITIONAL, prefex
				+ LangElem.CONDITIONAL, null, parent, parent.getDRLDocument(),
				new AttributesImpl());
		String conditionValue = variableName + "=" + variableValue;
		((AttributesImpl) condBlock.attrs).addAttribute(LangElem.CONDITION,
				LangElem.CONDITION, LangElem.CONDITION, "", conditionValue);
		return condBlock;
	}
}
