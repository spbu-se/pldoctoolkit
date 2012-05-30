package org.spbu.pldoctoolkit.refactor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.BufferedContent;
import org.eclipse.compare.ITypedElement;
import org.spbu.pldoctoolkit.diff.DiffResultPart;
import org.spbu.pldoctoolkit.diff.Difference;
import org.spbu.pldoctoolkit.diff.IElementInst;
import org.spbu.pldoctoolkit.diff.IPairGroup;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;
import org.xml.sax.helpers.AttributesImpl;

public class ExtractElemForDiff {

	private ProjectContent projectContent;

	private ArrayList<NestToReplace> nests;

	private CreateNest refactCreateNest = new CreateNest();

	public ExtractElemForDiff() {
		nests = new ArrayList<NestToReplace>();
	}

	public void setParams(ProjectContent projectContent) {
		this.projectContent = projectContent;
	}

	/*public void replaceNests(ProjectContent project, String elemRefIdToFind) {

		String prefix = doc.DRLnsPrefix;
		if (!prefix.equals(""))
			prefix += ":";

		LangElem adapter = null;
		for (LangElem oldAdapter : project.adapters) {
			if (oldAdapter.attrs.getValue("infelemrefid").equals(
					elemRefIdToFind)) {
				adapter = oldAdapter;
			}
		}

		// create new adapter
		if (adapter == null) {
			// TODO: get parent instead of firstInfElementToCompare.getParent()
			adapter = new LangElem(LangElem.ADAPTER, prefix + "Adapter", null,
					firstInfElementToCompare.getParent(), doc,
					new AttributesImpl());
			((AttributesImpl) adapter.attrs).addAttribute("infelemrefid",
					"infelemrefid", "infelemrefid", "", elemRefIdToFind);
			adapter.setChilds(new ArrayList<Element>());
			firstInfElementToCompare.getParent().getChilds().add(adapter);
		}

		int i = 0;
		for (String id : nestIds) {
			LangElem replaceNest = new LangElem(LangElem.REPLACENEST, prefix
					+ LangElem.REPLACENEST, null, adapter, doc,
					new AttributesImpl());
			((AttributesImpl) replaceNest.attrs).addAttribute(LangElem.NESTID,
					LangElem.NESTID, LangElem.NESTID, "", id);

			replaceNest.setChilds(new ArrayList<Element>());
			replaceNest.getChilds().add(diffElemsToReplace.get(i));
			adapter.getChilds().add(replaceNest);
			i++;
		}
	}*/

	private Element getElemContainingDiff(PositionInDRL from, PositionInDRL to) {
		Element res = from.elem != null ? from.elem : from.next;
		Element end = to.elem != null ? to.elem : to.prev;
		Element tmp = res;
		while (!tmp.containsOrEquals(end)) {
			res = tmp.getParent();
			Element next = tmp.getNextElementByParent();
			if (next == null) {
				tmp = res;
			} else {
				tmp = next;
			}
		}
		return res;
	}

	public List<Element> setNestsAndReturnElements(IPairGroup pair) {
		List<Element> firstChidren = getOriginalChildren(pair
				.getFirstInstance());
		List<Element> secondChidren = getOriginalChildren(pair
				.getSecondInstance());
		if ((pair.getFirstInstance().getParts().size() == pair
				.getSecondInstance().getParts().size())
				&& (pair.getFirstInstance().numberOfDifferences() == pair
						.getSecondInstance().numberOfDifferences())) {
			for (int i = pair.getFirstInstance().getParts().size() - 1; i >= 0; i--) {
				if (!pair.getFirstInstance().getParts().get(i).getIdentical()) {
					Element fiChildBefore = getChildBefore(pair
							.getFirstInstance(), i);
					System.out.println(fiChildBefore.getTextRepresentation());
					Element fiChildAfter = getChildAfter(pair
							.getFirstInstance(), i);
					System.out.println(fiChildAfter.getTextRepresentation());

					NestToReplace nestToReplace = deleteChildrenAndInsertNewNest(
							firstChidren,
							pair.getFirstInstance().getInfElem()
									.getDRLDocument(), fiChildBefore,
							fiChildAfter);

					Element siChildBefore = getChildBefore(pair
							.getSecondInstance(), i);
					Element siChildAfter = getChildAfter(pair
							.getSecondInstance(), i);
					
					removeChildren(secondChidren, siChildBefore, siChildAfter, nestToReplace, false);
					
					nests.add(nestToReplace);
				}
			}
		} else {
			System.out.println("wrong number of differences");
			System.out.println(pair.getFirstInstance().getParts().size());
			System.out.println(pair.getSecondInstance().getParts().size());
			System.out.println(pair.getFirstInstance().numberOfDifferences());
			System.out.println(pair.getSecondInstance().numberOfDifferences());
		}
		return firstChidren;
	}

	private NestToReplace deleteChildrenAndInsertNewNest(List<Element> list,
			DRLDocument doc, Element before, Element after) {
		String nestID = CreateNest.getAppropriateNestId("diff", projectContent);
		NestToReplace nestToReplace = new NestToReplace(nestID);
		
		removeChildren(list, before, after, nestToReplace, true);
		
		createNest(nestID, doc, new PositionInText(before.getEndPos().line, before.getEndPos().column + 1));
		
		return nestToReplace;
	}

	private void removeChildren(List<Element> list, Element before, Element after,
			NestToReplace nestToReplace, boolean first) {
		int i = list.size() - 1;
		while (!list.get(i).equals(after)) {
			i--;
		}
		while (!list.get(i - 1).equals(before)) {
			if (first) {
				nestToReplace.addFirstElement(list.remove(i - 1));
			} else {
				nestToReplace.addSecondElement(list.remove(i - 1));
			}
		}
	}

	private void createNest(String nestID, DRLDocument doc,
			PositionInText startPosition) {
		refactCreateNest.setValidationPararams(doc, startPosition);
		refactCreateNest.setPararams(nestID);
		refactCreateNest.perform();
	}

	private Element getChildBefore(IElementInst inst, int i) {
		DiffResultPart startPart;
		if (i > 0) {
			startPart = inst.getParts().get(i - 1);
		} else {
			startPart = inst.getParts().get(i);
		}
		List<Element> childrenBeforeNest = getElements(getAbsoluteStartPos(
				startPart, inst.getInfElem()), getAbsoluteEndPos(startPart,
				inst.getInfElem()), inst.getInfElem());
		return childrenBeforeNest.get(childrenBeforeNest.size() - 1);
	}

	private Element getChildAfter(IElementInst inst, int i) {
		DiffResultPart endPart;
		if (i < inst.getParts().size() - 1) {
			endPart = inst.getParts().get(i + 1);
		} else {
			endPart = inst.getParts().get(i);
		}
		List<Element> childrenAfterNest = getElements(getAbsoluteStartPos(
				endPart, inst.getInfElem()), getAbsoluteEndPos(endPart, inst
				.getInfElem()), inst.getInfElem());
		return childrenAfterNest.get(0);
	}

	private List<Element> getOriginalChildren(IElementInst inst) {
		return getElements(inst.getAbsoluteStartPos(),
				inst.getAbsoluteEndPos(), inst.getInfElem());
	}

	private List<Element> getElements(PositionInText fromPos,
			PositionInText toPos, LangElem infElem) {
		PositionInDRL from = infElem.getDRLDocument().findByPosition(fromPos);
		PositionInDRL to = infElem.getDRLDocument().findByPosition(toPos);

		int fromIdx;
		int toIdx;

		if (from.isInText)
			fromIdx = from.parent.getChilds().indexOf(from.elem);
		else
			fromIdx = from.parent.getChilds().indexOf(from.next);

		if (to.isInText)
			toIdx = to.parent.getChilds().indexOf(to.elem);
		else
			toIdx = to.parent.getChilds().indexOf(to.prev);

		if (from.isInText) {
			boolean isSame = false;
			if (to.isInText && from.elem == to.elem)
				isSame = true;

			((TextElement) from.elem).Split(fromPos);
			Element parent = from.elem.getParent();
			from = new PositionInDRL(false, false, null, parent.getChilds()
					.get(fromIdx), parent.getChilds().get(fromIdx + 1), parent);
			++fromIdx;
			++toIdx;

			if (isSame)
				to = infElem.getDRLDocument().findByPosition(toPos);
		}
		if (to.isInText) {
			((TextElement) to.elem).Split(toPos);
			Element parent = to.elem.getParent();
			to = new PositionInDRL(false, false, null, parent.getChilds().get(
					toIdx), parent.getChilds().get(toIdx + 1), parent);
		}

		return from.parent.getChilds(fromIdx, toIdx);
	}

	private static PositionInText getAbsoluteEndPos(DiffResultPart part,
			LangElem infElem) {
		PositionInText localPosition = getLocalEndPos(part, infElem);
		return getAbsolutePosition(infElem.getTagStartPos(), localPosition);
	}

	private static PositionInText getLocalEndPos(DiffResultPart part,
			LangElem infElem) {
		int line = part.getEndLineNumber() + 1;
		int column = infElem.getTextRepresentation().split("\n")[line].length()
				- infElem.getTagStartPos().column + 1;
		return new PositionInText(line, column);
	}

	private static PositionInText getAbsolutePosition(PositionInText prefixPos,
			PositionInText localPos) {
		int line = localPos.line + prefixPos.line;
		int column = localPos.column;
		column += prefixPos.column;
		return new PositionInText(line, column);
	}

	private static PositionInText getAbsoluteStartPos(DiffResultPart part,
			LangElem infElem) {
		PositionInText localPosition = getLocalStartPos(part, infElem);
		return getAbsolutePosition(infElem.getTagStartPos(), localPosition);
	}

	private static PositionInText getLocalStartPos(DiffResultPart part,
			LangElem infElem) {
		int line = part.getStartLineNumber() + 1;
		int column = infElem.getTextRepresentation().split("\n")[line].length()
				- infElem.getTextRepresentation().split("\n")[line].trim()
						.length() - infElem.getTagStartPos().column + 1;
		return new PositionInText(line, column);
	}

	/*
	 * public void saveNests(IElementInst instance) { String[] fullText =
	 * instance.getInfElem().getDRLDocument()
	 * .getTextRepresentation().split("\n"); for (int i = 0; i <
	 * instance.getParts().size(); i++) { if
	 * (!instance.getParts().get(i).getIdentical()) { int lineStart = 0; boolean
	 * b = true; if (instance.getParts().get(i).getEndLineNumber() !=
	 * Difference.NONE) { lineStart =
	 * instance.getInfElem().getTagStartPos().line +
	 * instance.getParts().get(i).getStartLineNumber(); b = false; } else { if
	 * (i > 0) { lineStart = instance.getInfElem().getTagStartPos().line +
	 * instance.getParts().get(i - 1) .getEndLineNumber(); } }
	 * nestTexts.add(fullText[line]);
	 * 
	 * } }
	 * 
	 * }
	 */

	final class NestToReplace {

		String nestID;
		List<Element> firstElements;
		List<Element> secondElements;

		NestToReplace(String id) {
			this.nestID = id;
			firstElements = new ArrayList<Element>();
			secondElements = new ArrayList<Element>();
		}

		void addFirstElement(Element elem) {
			firstElements.add(0, elem);
		}

		void addSecondElement(Element elem) {
			secondElements.add(elem);
		}

	}

}
