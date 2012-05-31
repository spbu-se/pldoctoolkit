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

	private ReplaceWithNest refactReplaceNest = new ReplaceWithNest();

	private IPairGroup pair;

	private List<LangElem> infElemRefs;

	public ExtractElemForDiff() {
		nests = new ArrayList<NestToReplace>();
	}

	public void setParams(ProjectContent projectContent, IPairGroup pair) {
		this.projectContent = projectContent;
		this.pair = pair;
		this.infElemRefs = new ArrayList<LangElem>();
	}

	public void replaceNests(ProjectContent project, String elemRefIdToFind) {

		String prefix = pair.getSecondInstance().getInfEl().getDRLDocument().DRLnsPrefix;
		if (!prefix.equals(""))
			prefix += ":";

		List<LangElem> adapters = new ArrayList<LangElem>();
		for (LangElem oldAdapter : project.adapters) {
			if (oldAdapter.attrs.getValue("infelemrefid").equals(
					elemRefIdToFind)) {
				adapters.add(oldAdapter);
			}
		}

		// create new adapter
		if (adapters.size() == 0) {

			List<Element> parents = new ArrayList<Element>();
			// String idTofind = ((LangElem)
			// pair.getSecondInstance().getInfEl()).attrs.getValue("id");
			// getInfElemRefs(pair.getSecondInstance().getInfEl().getDRLDocument(),
			// idTofind);
			getInfElemRefs();

			for (LangElem infElemRef : infElemRefs) {
				String secondInfElemRefIdToFind = infElemRef.attrs
						.getValue("id");
				for (LangElem oldAdapter : project.adapters) {
					if (oldAdapter.attrs.getValue("infelemrefid").equals(
							secondInfElemRefIdToFind)) {
						parents.add(oldAdapter.getParent());
					}
				}
			}

			if (parents.size() == 0) {
				List<LangElem> infProducts = new ArrayList<LangElem>();
				for (LangElem infElemRef : infElemRefs) {
					infProducts.add(getInfProduct(infElemRef));
				}
				for (LangElem prod : infProducts) {
					for (LangElem fip : projectContent.finalInfPrs) {
						if (fip.attrs.getValue("infproductid").equals(
								prod.attrs.getValue("id")))
							parents.add(fip);
					}
				}
			}

			for (Element parent : parents) {
				LangElem adapter = new LangElem(LangElem.ADAPTER, prefix + "Adapter",
						null, parent, parent.getDRLDocument(),
						new AttributesImpl());
				((AttributesImpl) adapter.attrs).addAttribute("infelemrefid",
						"infelemrefid", "infelemrefid", "", elemRefIdToFind);
				adapter.setChilds(new ArrayList<Element>());
				parent.getChilds().add(adapter);
				adapters.add(adapter);
			}
		}

		//int i = 0;
		for (NestToReplace nestToReplace : nests) {
			for (LangElem adapter : adapters) {
			LangElem replaceNest = new LangElem(LangElem.REPLACENEST, prefix
					+ LangElem.REPLACENEST, null, adapter, adapter
					.getDRLDocument(), new AttributesImpl());
			((AttributesImpl) replaceNest.attrs).addAttribute(LangElem.NESTID,
					LangElem.NESTID, LangElem.NESTID, "", nestToReplace.nestID);

			replaceNest.setChilds(new ArrayList<Element>());
			replaceNest.getChilds().addAll(nestToReplace.secondElements);
			adapter.getChilds().add(replaceNest);
			}
			//i++;
		}
	}

	private void getInfElemRefs() {

		// List<LangElem> infElemRefs = new ArrayList<LangElem>();

		String idTofind = ((LangElem) pair.getSecondInstance().getInfEl()).attrs
				.getValue("id");
		for (LangElem infElemRef : projectContent.infElemRefs) {
			if (infElemRef.attrs.getValue("infelemid").equals(idTofind))
				infElemRefs.add(infElemRef);
		}
	}

	private LangElem getInfProduct(Element elem) {
		if (elem instanceof LangElem) {
			if (((LangElem) elem).tag.equals(LangElem.INFPRODUCT)) {
				return (LangElem) elem;
			}
		}
		return getInfProduct(elem.getParent());
	}

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

	public ArrayList<Element> setNestsAndReturnElements() {
		ArrayList<Element> firstChidren = getOriginalChildren(pair
				.getFirstInstance());
		ArrayList<Element> secondChidren = getOriginalChildren(pair
				.getSecondInstance());
		if ((pair.getFirstInstance().getParts().size() == pair
				.getSecondInstance().getParts().size())
				&& (pair.getFirstInstance().numberOfDifferences() == pair
						.getSecondInstance().numberOfDifferences())) {
			for (int i = pair.getFirstInstance().getParts().size() - 1; i >= 0; i--) {
				if (!pair.getFirstInstance().getParts().get(i).getIdentical()) {
					/*
					 * Element fiChildBefore = getChildBefore(pair
					 * .getFirstInstance(), i);
					 * System.out.println(fiChildBefore.
					 * getTextRepresentation()); Element fiChildAfter =
					 * getChildAfter(pair .getFirstInstance(), i);
					 * System.out.println(fiChildAfter.getTextRepresentation());
					 * 
					 * NestToReplace nestToReplace =
					 * deleteChildrenAndInsertNewNest( firstChidren,
					 * pair.getFirstInstance().getInfEl() .getDRLDocument(),
					 * fiChildBefore, fiChildAfter);
					 * 
					 * Element siChildBefore = getChildBefore(pair
					 * .getSecondInstance(), i); Element siChildAfter =
					 * getChildAfter(pair .getSecondInstance(), i);
					 * 
					 * removeChildren(secondChidren, siChildBefore,
					 * siChildAfter, nestToReplace, false);
					 * 
					 * nests.add(nestToReplace);
					 */

					String nestID = CreateNest.getAppropriateNestId("diff",
							projectContent);
					NestToReplace nestToReplace = new NestToReplace(nestID);
					refactReplaceNest.setPararams(nestID);
					refactReplaceNest.setValidationPararams(pair
							.getFirstInstance().getInfEl().getDRLDocument(),
							getAbsoluteStartPos(pair.getFirstInstance()
									.getParts().get(i), pair.getFirstInstance()
									.getInfEl()), getAbsoluteEndPos(pair
									.getFirstInstance().getParts().get(i), pair
									.getFirstInstance().getInfEl()));
					refactReplaceNest.perform();

					refactReplaceNest.reset();
					refactReplaceNest.setValidationPararams(pair
							.getSecondInstance().getInfEl().getDRLDocument(),
							getAbsoluteStartPos(pair.getSecondInstance()
									.getParts().get(i), pair
									.getSecondInstance().getInfEl()),
							getAbsoluteEndPos(pair.getSecondInstance()
									.getParts().get(i), pair
									.getSecondInstance().getInfEl()));
					if (refactReplaceNest.validate()) {
						nestToReplace.setSecondElements(refactReplaceNest
								.firstPartOfPerform());
					}
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

	/*
	 * private NestToReplace deleteChildrenAndInsertNewNest(List<Element> list,
	 * DRLDocument doc, Element before, Element after) { String nestID =
	 * CreateNest.getAppropriateNestId("diff", projectContent); NestToReplace
	 * nestToReplace = new NestToReplace(nestID);
	 * 
	 * removeChildren(list, before, after, nestToReplace, true);
	 * 
	 * createNest(nestID, doc, new PositionInText(before.getEndPos().line,
	 * before.getEndPos().column + 1));
	 * 
	 * return nestToReplace; }
	 * 
	 * private void removeChildren(List<Element> list, Element before, Element
	 * after, NestToReplace nestToReplace, boolean first) { int i = list.size()
	 * - 1; while (!list.get(i).equals(after)) { i--; } while (!list.get(i -
	 * 1).equals(before)) { if (first) {
	 * nestToReplace.addFirstElement(list.remove(i - 1)); } else {
	 * nestToReplace.addSecondElement(list.remove(i - 1)); } } }
	 * 
	 * private void createNest(String nestID, DRLDocument doc, PositionInText
	 * startPosition) { refactCreateNest.setValidationPararams(doc,
	 * startPosition); refactCreateNest.setPararams(nestID);
	 * refactCreateNest.perform(); }
	 * 
	 * private Element getChildBefore(IElementInst inst, int i) { DiffResultPart
	 * startPart; if (i > 0) { startPart = inst.getParts().get(i - 1); } else {
	 * startPart = inst.getParts().get(i); } List<Element> childrenBeforeNest =
	 * getElements(getAbsoluteStartPos( startPart, inst.getInfEl()),
	 * getAbsoluteEndPos(startPart, inst.getInfEl()), inst.getInfEl()); return
	 * childrenBeforeNest.get(childrenBeforeNest.size() - 1); }
	 * 
	 * private Element getChildAfter(IElementInst inst, int i) { DiffResultPart
	 * endPart; if (i < inst.getParts().size() - 1) { endPart =
	 * inst.getParts().get(i + 1); } else { endPart = inst.getParts().get(i); }
	 * List<Element> childrenAfterNest = getElements(getAbsoluteStartPos(
	 * endPart, inst.getInfEl()), getAbsoluteEndPos(endPart, inst .getInfEl()),
	 * inst.getInfEl()); return childrenAfterNest.get(0); }
	 */

	private ArrayList<Element> getOriginalChildren(IElementInst inst) {
		return getElements(inst.getStartPos4EntireDocument(), inst
				.getEndPos4EntireDocument(), inst.getInfEl());
	}

	private ArrayList<Element> getElements(PositionInText fromPos,
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
		if (part.getEndLineNumber() == Difference.NONE) {
			return getLocalStartPos(part, infElem);
		}
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

		void setSecondElements(List<Element> lst) {
			secondElements = lst;
		}
	}

}
