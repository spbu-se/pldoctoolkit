package org.spbu.pldoctoolkit.diff;

import java.util.ArrayList;
import java.util.List;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;

public class DiffMaker {

	private List<IPairGroup> groups;
	private IPairGroup currentPair;
	private DiffResultPart partInFirstElement;
	private DiffResultPart partInSecondElement;

	public List<IPairGroup> makeDiff(LangElem firstInfElementToCompare,
			LangElem secondInfElementToCompare) {

		groups = new ArrayList<IPairGroup>();

		String[] cleanFirstLines = getLines(firstInfElementToCompare);
		String[] cleanSecondLines = getLines(secondInfElementToCompare);
		//String[] cleanFirstLines = new String[]{firstInfElementToCompare.getTextRepresentation()};
		//String[] cleanSecondLines = new String[]{secondInfElementToCompare.getTextRepresentation()};
		for (int i =0;i<cleanFirstLines.length;i++) {
			System.out.println("IIIII" + cleanFirstLines[i] + "IIIII");
		}
		for (int i =0;i<cleanSecondLines.length;i++) {
			System.out.println("II_II" + cleanSecondLines[i] + "II_II");
		}
		List<Difference> diffs = (new Diff<String>(cleanFirstLines,
				cleanSecondLines)).diff();

		currentPair = new PairGroupImpl(firstInfElementToCompare,
				secondInfElementToCompare);
		groups.add(currentPair);
		partInFirstElement = new DiffResultPart(0);
		partInSecondElement = new DiffResultPart(0);

		System.out.println("diffs size" + diffs.size());
		int max = 0;
		for (Difference diff : diffs) {
			if (diff.getDeletedEnd() != Difference.NONE && diff.getAddedEnd() != Difference.NONE) {
				max = Math.max(max, diff.getDeletedEnd() - diff.getDeletedStart());
				max = Math.max(max, diff.getAddedEnd() - diff.getAddedStart());
			}
		}
		System.out.println(max);
		for (Difference diff : diffs) {
			String type = diff.getDeletedEnd() != Difference.NONE
					&& diff.getAddedEnd() != Difference.NONE ? "c" : (diff
					.getDeletedEnd() == Difference.NONE ? "a" : "d");
			System.out
					.println(toString(diff.getDeletedStart(), diff
							.getDeletedEnd())
							+ type
							+ toString(diff.getAddedStart(), diff.getAddedEnd()));
			addIdenticalIfNecessary(diff.getDeletedStart(), diff.getDeletedEnd(), diff.getAddedStart(), diff.getAddedEnd(), firstInfElementToCompare, secondInfElementToCompare);

			partInFirstElement = new DiffResultPart(diff.getDeletedStart(),
					diff.getDeletedEnd(), false);
			partInSecondElement = new DiffResultPart(diff.getAddedStart(), diff
					.getAddedEnd(), false);

			System.out.println("haha" + partInFirstElement.isSafe(firstInfElementToCompare));
			if (partInFirstElement.isSafe(firstInfElementToCompare)
					&& partInSecondElement.isSafe(secondInfElementToCompare)) {
				currentPair.getFirstInstance().addPart(partInFirstElement);
				currentPair.getSecondInstance().addPart(partInSecondElement);
			} else {
				currentPair = new PairGroupImpl(firstInfElementToCompare,
						secondInfElementToCompare);
				if ((diff.getDeletedEnd() != cleanFirstLines.length - 1)
						|| (diff.getAddedEnd() != cleanSecondLines.length - 1)) {
					groups.add(currentPair);
				}
			}
			if (diff.getDeletedEnd() != Difference.NONE) {
			partInFirstElement = new DiffResultPart(diff.getDeletedEnd() + 1);
			} else {
				partInFirstElement = new DiffResultPart(diff.getDeletedStart() + 1);
			}
			if (diff.getAddedEnd() != Difference.NONE) {
			partInSecondElement = new DiffResultPart(diff.getAddedEnd() + 1);
			} else {
				partInSecondElement = new DiffResultPart(diff.getAddedStart() + 1);
			}
		}
		addIdenticalIfNecessary(cleanFirstLines.length, 0, cleanSecondLines.length, 0, firstInfElementToCompare, secondInfElementToCompare);
		if ((groups.get(groups.size() - 1).getFirstInstance().getParts().size() == 0) || (groups.get(groups.size() - 1).getSecondInstance().getParts().size() == 0)) {
			groups.remove(groups.size() - 1);
		}
		return groups;
	}

	private String[] getLines(LangElem langElem) {
		String[] lines = langElem.getTextRepresentation().split("\n");
		String[] cleanLines = new String[lines.length - 2];
		for (int i = 1; i < lines.length - 1; i++) {
			cleanLines[i - 1] = lines[i];
		}
		return cleanLines;
	}

	private void addIdenticalIfNecessary(int delStart, int delEnd, int addStart, int addEnd, LangElem firstInfElem,
			LangElem secondInfElem) {
		if (delStart > partInFirstElement.getStartLineNumber()) {
			/*int lineEnd;
			if (delEnd != Difference.NONE) {
				lineEnd = delStart - 1;
			} else {
				lineEnd = delStart;
			}
			System.out.println("identical" + delStart + lineEnd);*/
			partInFirstElement.setEndLineNumber(delStart - 1);
			partInFirstElement.setIdentical(true);
		}
		if (addStart > partInSecondElement.getStartLineNumber()) {
			/*int lineEnd;
			if (addEnd != Difference.NONE) {
				lineEnd = addStart - 1;
			} else {
				lineEnd = addStart;
			}
			System.out.println("identical" + addStart + lineEnd);*/
			partInSecondElement.setEndLineNumber(addStart - 1);
			partInSecondElement.setIdentical(true);
		}
		handleIdentical(partInFirstElement, partInSecondElement, firstInfElem, secondInfElem);
	}

	private void handleIdentical(DiffResultPart identicalPartForFirstInst,
			DiffResultPart identicalPartForSecondInst, LangElem firstInfElem,
			LangElem secondInfElem) {
		System.out.println("HEEEREEE" + (identicalPartForFirstInst.getEndLineNumber() - identicalPartForFirstInst.getStartLineNumber()));
		System.out.println("2HEEEREEE" + (identicalPartForSecondInst.getEndLineNumber() - identicalPartForSecondInst.getStartLineNumber()));
		if (identicalPartForFirstInst.getEndLineNumber()
				- identicalPartForFirstInst.getStartLineNumber() == identicalPartForSecondInst
				.getEndLineNumber()
				- identicalPartForSecondInst.getStartLineNumber()) {
			int offset = identicalPartForSecondInst.getEndLineNumber() - identicalPartForFirstInst.getEndLineNumber();
			DiffResultPart partToAdd = null;
			for (int line = identicalPartForFirstInst.getStartLineNumber(); line <= identicalPartForFirstInst
					.getEndLineNumber(); line++) {
				boolean addLineToPart = true;
				Element curElem1 = DiffResultPart
						.getElement(firstInfElem, line);
				Element curElem2 = DiffResultPart.getElement(secondInfElem,
						line);
				if ((curElem1.getEndPos().line == firstInfElem.getTagStartPos().line
						+ 1 + line
						|| curElem2.getEndPos().line == secondInfElem
								.getTagStartPos().line
								+ 1 + line) && (curElem1.getStartPos().line != firstInfElem.getTagStartPos().line
										+ 1 + line
										|| curElem2.getStartPos().line != secondInfElem
												.getTagStartPos().line
												+ 1 + line)) {
					if (currentPair.getFirstInstance().getParts().size() > 0
							&& currentPair.getSecondInstance().getParts()
									.size() > 0) {
						Element firstElemOfFirstInst = DiffResultPart
								.getElement(firstInfElem, currentPair
										.getFirstInstance().getParts().get(0)
										.getStartLineNumber());
						Element firstElemOfSecondInst = DiffResultPart
								.getElement(secondInfElem, currentPair
										.getSecondInstance().getParts().get(0)
										.getStartLineNumber());
						if (curElem1.contains(firstElemOfFirstInst)
								|| curElem2.contains(firstElemOfSecondInst)) {
							addLineToPart = false;
						}
					} else {
						addLineToPart = false;
					}
				}
				if (addLineToPart) {
					if (partToAdd == null) {
						partToAdd = new DiffResultPart(line);
						partToAdd.setIdentical(true);
					} else {
						partToAdd.setEndLineNumber(line);
					}
				} else {
					if (partToAdd != null) {
						if (partToAdd.getEndLineNumber() == 0) {
							partToAdd.setEndLineNumber(partToAdd
									.getStartLineNumber());
						}
						currentPair.getFirstInstance().addPart(partToAdd);
						DiffResultPart tmpPart = new DiffResultPart(partToAdd.getStartLineNumber() + offset, partToAdd.getEndLineNumber() + offset, true);
						currentPair.getSecondInstance().addPart(tmpPart);
						partToAdd = null;
						if (!groups.contains(currentPair)) {
							groups.add(currentPair);
						}
						currentPair = new PairGroupImpl(firstInfElem,
								secondInfElem);
					}
				}
			}
			if (partToAdd != null) {
				if (partToAdd.getEndLineNumber() == 0) {
					partToAdd.setEndLineNumber(partToAdd.getStartLineNumber());
				}
				currentPair.getFirstInstance().addPart(partToAdd);
				DiffResultPart tmpPart = new DiffResultPart(partToAdd.getStartLineNumber() + offset, partToAdd.getEndLineNumber() + offset, true);
				currentPair.getSecondInstance().addPart(tmpPart);
				if (!groups.contains(currentPair)) {
					groups.add(currentPair);
				}
				//currentPair = new PairGroupImpl(firstInfElem, secondInfElem);				
			}
			if (!groups.contains(currentPair)) {
			groups.add(currentPair);
			}
		} else {
			System.out.println("error on handle identical parts");
		}
	}

	private static Element getElement(DRLDocument doc, PositionInText pos) {
		PositionInDRL posDRL = doc.findByPosition(pos);
		Element elem = posDRL.elem != null ? posDRL.elem : posDRL.next;
		if (elem.getTextRepresentation().trim().equals("")) {
			elem = elem.getNextElementByParent();
		}
		return elem;
	}

	protected String toString(int start, int end) {
		// adjusted, because file lines are one-indexed, not zero.

		StringBuffer buf = new StringBuffer();

		// match the line numbering from diff(1):
		buf.append(end == Difference.NONE ? start : (1 + start));

		if (end != Difference.NONE && start != end) {
			buf.append(",").append(1 + end);
		}
		return buf.toString();
	}

}
