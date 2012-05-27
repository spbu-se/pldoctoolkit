package org.spbu.pldoctoolkit.clones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;

import com.sun.org.apache.bcel.internal.generic.RETURN;

final class ClonesGroupsFilter {

	private static final String DELIMITERS_OF_DOCBOOK = " \n\t";

	public List<IClonesGroup> specifyClonesGroups4DRL(List<IClonesGroup> input, LangElem infEl) {
		List<IClonesGroup> output = new ArrayList<IClonesGroup>();
//		output.addAll(input);
		for (IClonesGroup clonesGroup : input) {
			// only exact clones
			if (groupContainsOnlyExactClones(clonesGroup)){
				if (groupTextIsSimpleText(clonesGroup)){
					output.add(clonesGroup);
				}else{
//					List<String> substrings = getSubstrings(clonesGroup.getInstances().get(0), infEl);
//					List<IClonesGroup> newGroups = applyChangesToGroup(substrings, clonesGroup);
//					output.addAll(newGroups);
					List<IClonesGroup> newGroups = new LinkedList<IClonesGroup>();
					boolean firstOperation = true;
					int countOfClonesAfterSpecify = -1;
					for (ICloneInst clone : clonesGroup.getInstances()) {
						List<ICloneInst> specifiedClones = specifyClone4DRL(clone);
						if (firstOperation){
							firstOperation = false;
							countOfClonesAfterSpecify = specifiedClones.size();
							for (int i = 0; i < countOfClonesAfterSpecify; i++) {
								//TODO 
								IClonesGroup newGroup = new ClonesGroupImpl(clonesGroup.getId(), -1);
								newGroups.add(newGroup);
							}
						}else{
							if (specifiedClones.size() != countOfClonesAfterSpecify){
								throw new IllegalStateException();
							}
						}
						for (int i = 0; i < countOfClonesAfterSpecify; i++) {
							newGroups.get(i).addCloneInst(specifiedClones.get(i));
						}
					}
					newGroups = deleteGroupsWithoutText(newGroups);
					output.addAll(newGroups);
				}
			}else{
				if (!groupContainsOnlyExactClonesWithoutRegister(clonesGroup)){
					System.out.println("group don't ContainsOnlyExactClones :" + clonesGroup.getName());
				}else{
					System.out.println("register");					
				}
//				output.add(clonesGroup);
			}
		}
		for (IClonesGroup clonesGroup : output) {
			if(!groupContainsOnlyExactClones(clonesGroup)){
				System.out.println("opasnoste group:" + clonesGroup.getName());
				for (ICloneInst clone :clonesGroup.getInstances()) {
					System.out.println("clone:"+ clone);
				}
			}
		}
		//TODO
//		return doRecalculationOfClonesGroupsIds(output);
		return output;
	}
	
	private List<IClonesGroup> deleteGroupsWithoutText(
			List<IClonesGroup> newGroups) {
		for (Iterator<IClonesGroup> iterator = newGroups.iterator(); iterator.hasNext();) {
			IClonesGroup clonesGroup = iterator.next();
			String text = clonesGroup.getInstances().get(0).getCloneText()
					.replace("\t", "").replace("\n", "").replace(" ", "");
			if(text.length() == 0){
				iterator.remove();
			}else{
				int endIndexOfTag = text.indexOf('>') + 1;
				int startIndexOfNextTag = text.indexOf('<', endIndexOfTag);
				while (endIndexOfTag != -1 && endIndexOfTag == startIndexOfNextTag){
					
					endIndexOfTag = text.indexOf('>', startIndexOfNextTag) + 1;
					startIndexOfNextTag = text.indexOf('<', endIndexOfTag == -1 ? 0 : endIndexOfTag);
				}
				if (endIndexOfTag == -1 ){
					iterator.remove();
				}
			}
		}
		return newGroups;
	}

	private List<IClonesGroup> applyChangesToGroup(List<String> substrings,
			IClonesGroup clonesGroup) {
		List<IClonesGroup> rez = new ArrayList<IClonesGroup>(substrings.size());
		for (String substring: substrings) {
			int count = getCountOfTerms(substring);
			IClonesGroup group = new ClonesGroupImpl(clonesGroup.getId(), count);
		}
		for (ICloneInst clone : clonesGroup.getInstances()) {
			String cloneText = clone.getCloneText();
			StringTokenizer cloneTextTok = new StringTokenizer(cloneText, DELIMITERS_OF_DOCBOOK);
			int fromIndex = 0;
			for (String subst: substrings) {
//				cloneTextTok.
//				
//				int index = myIndexOf(cloneText, subst, fromIndex);
				
			}
		}
		return rez;
	}

	private int getCountOfTerms(String st) {
		int count = 0;
		for (StringTokenizer tok = new StringTokenizer(st, DELIMITERS_OF_DOCBOOK);
			tok.hasMoreTokens(); tok.nextToken(), count++) {
		}
		return count;
	}

	private final List<Tag> tags = new ArrayList<Tag>();
	private List<ICloneInst> specifyClone4DRL(ICloneInst clone) {
		tags.clear();
		System.out.println("\nBEGIN start:"+clone.getStartPos4EntireDocument()
				+" end:"+ clone.getEndPos4EntireDocument()+ " :\n" + clone.getCloneText() + "\n:END");
		String text = clone.getCloneText();
		int indexOfFirstGT = text .indexOf('<');
		int indexOfFirstLT = text.indexOf('>');
		if (indexOfFirstGT == -1 && indexOfFirstLT == -1)
			throw new IllegalArgumentException("text of clones have to contains '<' or '>'.");
		List<ICloneInst> rez = new ArrayList<ICloneInst>();
		if (indexOfFirstGT == -1){
			//=> indexOfFirstLT!=-1
//			rez.add(text.substring(indexOfFirstLT+1));
			PositionInText startPos = addAbsoluteIndexToPosition(
					clone.getStartPos4EntireDocument(), indexOfFirstLT+1, text);
			PositionInText endPos = clone.getEndPos4EntireDocument();
			ICloneInst inst = CloneInstImpl.createCloneInstByPositions4EntireDoc(
					clone.getInfEl(), startPos, endPos);
			rez.add(inst);
			return rez;
		}
		if (indexOfFirstLT == -1){
			//=> indexOfFirstLT!=-1
//			rez.add(text.substring(0, indexOfFirstGT));
			PositionInText startPos = clone.getStartPos4EntireDocument();
			PositionInText endPos = addAbsoluteIndexToPosition(
					startPos, indexOfFirstGT, text);
			ICloneInst inst = CloneInstImpl.createCloneInstByPositions4EntireDoc(
					clone.getInfEl(), startPos, endPos);
			rez.add(inst);
			return rez;
		}
		//search for the maximum logical group
		//3 parts: [simple text1] [<tag1>...</tag1>....<tag_n>...<tag_n>] [simple text2]
		int prefixIndex = addFirstOpeningTagToTagsAndGetPrefixIndex(clone);
		if (prefixIndex == -1){
			// need process case: (tag>text</tag)||(tag>text</tag><tag2) => text
			// need process case: (</tag1><tag2) 
			return rez;
		}else{
			// need process case: somthing<1tag>text => ?
			while(prefixIndex != -1){
				prefixIndex = addNextTagToTagsAndGetPrefixIndex(clone, prefixIndex);
			}
			System.out.println("tags" + tags);
			List<PosPara> paras = getListofPosParaByTagList();
			for (PosPara para : paras) {
				PositionInText startPos = para.startPos;
				PositionInText endPos = para.endPos;
				ICloneInst inst = CloneInstImpl.
				createCloneInstByPositions4EntireDoc(clone.getInfEl(), startPos, endPos);
				rez.add(inst);				
			}
			return rez;

		}	
			
			
//		int indexOfLastGT = text.lastIndexOf('<');
//		int indexOfLastLT = text.lastIndexOf('>');
//		
//		DRLDocument doc = infEl.getDRLDocument();
//		System.out.println("BEGIN start:"+clone.getStartPos4EntireDocument()
//				+" end:"+ clone.getEndPos4EntireDocument()+ " :\n" + clone.getCloneText() + "\n:END");
//		PositionInText startPosOfClone = clone.getStartPos4EntireDocument();
//		PositionInText endPosOfClone = clone.getEndPos4EntireDocument();
//		
//		PositionInDRL from = doc.findByPosition(startPosOfClone );
//		System.out.println("\nfrom.elem:"+from.elem + "\nfrom.isIntext:" 
//				+ from.isInText + " \nfrom.isInTag:" + from.isInTag 
//				+ " \nfrom.next:" + from.next+ "\nfrom.prev:" + from.prev +" \nfrom.parent:"+from.parent);
//		PositionInDRL to = doc.findByPosition(endPosOfClone);
//		int start = endPosOfClone.column;
//		int end = endPosOfClone.column;
//		boolean wasInLoop = false;
//		while (from.isInTag) {
//			wasInLoop = true;
//			start++;
//			from = doc.findByPosition(new PositionInText(startPosOfClone.line, start));
//		}
//		if (wasInLoop) {
//			from = doc.findByPosition(new PositionInText(startPosOfClone.line, start + 1));
//		}
//		Element first = from.elem != null ? from.elem : from.next;
//		wasInLoop = false;
//		while (to.isInTag) {
//			wasInLoop = true;
//			end--;
//			to = doc.findByPosition(new PositionInText(endPosOfClone.line, end));
//		}
//		if (wasInLoop) {
//			to = doc.findByPosition(new PositionInText(endPosOfClone.line, end - 1));
//		}
//		Element last = to.elem != null ? to.elem : to.prev;
////		ICloneInst specifiedInstance = new DRLCloneInstImpl(clone
////				.getInfEl(), first.getStartPos(), new PositionInText(last.getEndPos().line, last.getEndPos().column - 1), clone.getCloneText());
//		return CloneInstImpl.createCloneInstByPositions4EntireDoc(clone.getInfEl(), first.getStartPos(), 
//				new PositionInText(last.getEndPos().line, last.getEndPos().column));
		//		specifiedGroup.addCloneInst(specifiedInstance);
		
//		rez.add(text);
//		return rez;
	}
	
	private List<PosPara> deleteParasWhichDontContainsText(List<PosPara> paras) {
		for (Iterator<PosPara> iterator = paras.iterator(); iterator.hasNext();) {
			PosPara posPara = iterator.next();
			int tagIndex = 0;
			boolean needDelete = false;
			if (posPara.startPos.compare(posPara.endPos)==0){
				needDelete = true;
			}else{
				//contains text ?
				boolean tagFound = false;
				while (!tagFound ){
					if (tags.get(tagIndex).startPos.compare(posPara.startPos)==0){
						tagFound = true;
					}
					tagIndex++;
				}
				boolean posParaContainsText = false;
				for(;!posParaContainsText && tagIndex+1 < tags.size();tagIndex++){
					if (tags.get(tagIndex).endPos.compare(tags.get(tagIndex+1).startPos)!=0){
						posParaContainsText = true;
					}
					
				}
				needDelete = !posParaContainsText;
			}
			if (needDelete){
				iterator.remove();				
			}
			
		}
		return paras;
	}

	private List<PosPara> getListofPosParaByTagList() {
		return getListofPosParaByTagListRecurcive(0, new ArrayList<PosPara>());
	}


	private List<PosPara> getListofPosParaByTagListRecurcive(int tagIndex, List<PosPara> rez) {
		PositionInText startPos = tags.get(0).startPos;
		PositionInText endPos = tags.get(0).startPos;
		Stack<Tag> tagStack = new Stack<Tag>();
		int depth = 0;
		int depth4SpetialTag = -1;
		Tag tag = null;
		for (;tagIndex < tags.size(); tagIndex++) {
			tag = tags.get(tagIndex);
			if (tag.isOpeningTag){
				tagStack.push(tag);
				depth++;
			} else {
				try {
					Tag popTag = tagStack.pop();
					if (depth4SpetialTag == -1 || 
							depth4SpetialTag >= depth){
						if (depth4SpetialTag == -1 || 
							depth4SpetialTag > depth){
							startPos = popTag.startPos;							
							depth4SpetialTag = depth;
						}
						endPos = tag.endPos;
					}
					depth--;
					if (!popTag.name.equals(tag.name)) {
						throw new IllegalStateException("popTag.name:"+popTag.name
								+ " != tag.name:" + tag.name);
					}
					if (tagStack.isEmpty()){
						rez.add(new PosPara(startPos, endPos));
						for (;tagIndex < tags.size(); tagIndex++){
							if (tags.get(tagIndex).isOpeningTag){
								return getListofPosParaByTagListRecurcive(
										tagIndex, rez);														
							}
						}
						return rez;
					}
				} catch (EmptyStackException e) {
					throw new IllegalStateException();
				}
			}
		}
		rez.add(new PosPara(startPos, endPos));
		return rez;						
	}
	
	private static final class PosPara{
		private final PositionInText startPos;
		private final PositionInText endPos;

		PosPara(PositionInText startPos, PositionInText endPos) {
			this.startPos = startPos;
			this.endPos = endPos;
		}
	}

	private int addNextTagToTagsAndGetPrefixIndex(ICloneInst clone,
			int prefixIndex) {
		String text = clone.getCloneText();
		int index = text .indexOf('<', prefixIndex);
		int index2 = text.indexOf("</", prefixIndex);
		if(index==-1 && index2 == -1)
			return -1;
		boolean openingTag = index != index2;
		if (openingTag){
			StringTokenizer tok = new StringTokenizer(text.substring(index+1)," \n\t>");
			String name = tok.nextToken();
			int indexOfEnd = text.indexOf('>', index);
			if (indexOfEnd == -1){
				return -1;
			}
			PositionInText startPos = addAbsoluteIndexToPosition(clone.getStartPos4EntireDocument(), index, text);
			PositionInText endPos = addAbsoluteIndexToPosition(clone.getStartPos4EntireDocument(), indexOfEnd+1, text);
			tags.add(new Tag(startPos, endPos, name, true));
			return indexOfEnd+1;
		} else {
			StringTokenizer tok = new StringTokenizer(text.substring(index2+2)," \n\t>");
			String name = tok.nextToken();
			int indexOfEnd = text.indexOf('>', index2);
			if (indexOfEnd == -1){
				return -1;
			}
			PositionInText startPos = addAbsoluteIndexToPosition(clone.getStartPos4EntireDocument(), index2, text);
			PositionInText endPos = addAbsoluteIndexToPosition(clone.getStartPos4EntireDocument(), indexOfEnd+1, text);
			tags.add(new Tag(startPos, endPos, name, false));
			return indexOfEnd+1;
		}
	}

	private int addFirstOpeningTagToTagsAndGetPrefixIndex(ICloneInst clone) {
		//(tag>text</tag)||(tag>text</tag><tag2)
		String text = clone.getCloneText();
		int indexOfStart = text .indexOf('<');
		int index2 = text.indexOf("</");
		while (indexOfStart!= -1 &&
				index2!= -1 && 
				indexOfStart == index2){
			indexOfStart = text.indexOf('<', indexOfStart+1);
			index2 = text.indexOf("</", index2+1);
		}
		if (indexOfStart == -1 && index2 == -1){
			return -1;
		}
		if (indexOfStart == -1){
			throw new IllegalStateException();
		}
		if (index2 == -1){
			//(tag>text</tag><tag2) || (tag>text</tag><tag2>text)
			if (text.indexOf('>',indexOfStart) == -1)
				return -1;
		}
		StringTokenizer tok = new StringTokenizer(text.substring(indexOfStart+1)," \n\t>");
		String name = tok.nextToken();
		int indexOfEnd = text.indexOf('>', indexOfStart);
		if (indexOfEnd == -1){
			return -1;
		}
		PositionInText startPos = addAbsoluteIndexToPosition(clone.getStartPos4EntireDocument(), indexOfStart, text);
		PositionInText endPos = addAbsoluteIndexToPosition(clone.getStartPos4EntireDocument(), indexOfEnd+1, text);
		tags.add(new Tag(startPos, endPos, name, true));
		return indexOfEnd+1;
	}

	private static PositionInText addAbsoluteIndexToPosition(
			PositionInText startPos, int absIndex,
			String text) {
		try {
			BufferedReader r = new BufferedReader(new StringReader(text));
			int line = startPos.line;
			int column = startPos.column;
			String l = r.readLine();
			
			if(l.length() >= absIndex){
				column += absIndex;
			}else{
				for (; l.length() < absIndex; ){
					absIndex-= (l.length()+1);
					line++;
					l = r.readLine();
					if (l == null)
						throw new IllegalStateException();
				}
				column = absIndex + 1;
			}
			return new PositionInText(line, column);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean groupTextIsSimpleText(IClonesGroup clonesGroup) {
		// stupid realisation
		String text = clonesGroup.getInstances().get(0).getCloneText();
		return text.indexOf('<') == -1 && text.indexOf('>') == -1;
	}

	private List<IClonesGroup> doRecalculationOfClonesGroupsIds(
			List<IClonesGroup> output) {
		int id = 0;
		for (IClonesGroup clonesGroup : output) {
			clonesGroup.setGroupId(id);
			id++;
		}
		return output;
	}

	private boolean groupContainsOnlyExactClonesWithoutRegister(
			IClonesGroup clonesGroup) {
		String template = null;
		for (ICloneInst clone : clonesGroup.getInstances()) {
			if (template == null){
				template = clone.getCloneText().toLowerCase();
			}else{
				if (!textOfClonesEquals(template, clone.getCloneText().toLowerCase())){
					return false;
				}
			}
		}
		return true;
	}

	private boolean groupContainsOnlyExactClones(IClonesGroup clonesGroup) {
		String template = null;
		for (ICloneInst clone : clonesGroup.getInstances()) {
			if (template == null){
				template = clone.getCloneText();
			}else{
				if (!textOfClonesEquals(template, clone.getCloneText())){
					return false;
				}
			}
		}
		return true;
	}

	private boolean textOfClonesEquals(String template, String cloneText) {
		StringTokenizer tok1 = new StringTokenizer(template, DELIMITERS_OF_DOCBOOK);
		StringTokenizer tok2 = new StringTokenizer(cloneText, DELIMITERS_OF_DOCBOOK);
		for (; tok1.hasMoreTokens() || tok2.hasMoreTokens(); ) {
			String token1 = tok1.nextToken();
			String token2 = tok2.nextToken();
			if(!token1.equals(token2)){
				return false;
			}
		}
		return !tok1.hasMoreTokens() && !tok2.hasMoreTokens();
	}
	
	private static final class Tag {
		final PositionInText startPos;
		final PositionInText endPos;
		final String name;
		final boolean isOpeningTag;

		Tag(final PositionInText startPos, final PositionInText endPos,
				final String name, final boolean isOpeningTag) {
			this.startPos = startPos;
			this.endPos = endPos;
			this.name = name;
			this.isOpeningTag = isOpeningTag;
		}
		
		public String toString(){
			return "["+name+" start:"+startPos+" end:"+ endPos+" open:"+isOpeningTag+"]";
		}
	}

}
