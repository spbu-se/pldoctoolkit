package org.spbu.pldoctoolkit.refactor;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Stack;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.parser.DRLLang.TextElement;

//import net.sf.saxon.dom.NodeOverNodeInfo;

public class Util {
	// Предполагается, что строка заканчивается концом тэга
	public static int findtagStartPos(CharSequence text, int from) {
		Stack<Character> stack = new Stack<Character>();
		int curPos = from;//text.length();
		stack.push('>'); // кладём закр скобку
		--curPos;		
		while (!stack.isEmpty() || curPos < 0) {			
			char curTop = stack.peek();
			char buf[] = new char[1];
			/*
			try {
				text.read(buf, 1, 1);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			*/
			char curChar = text.charAt(curPos);
			
			if (curChar == '"') {
				if (curTop == '"')
					stack.pop();
				else
					stack.push(curChar);
			}
			else if (curChar == '<') {
				if (curTop != '"')
					stack.pop();		
			}
			
			--curPos;
		}
		//TODO Добавить кидание исключения в случае не нах. откр. скобки
		return ++curPos;
	}
	
	public static int getOffset(CharSequence input, int line, int col) {		
		int offset = getOffset(input, line, col, 1, 1, 0);
		return offset;
	}
	
	public static int getOffset(CharSequence input, int line, int col, int prevLine, int prevCol, int prevOffset) {
		int offset = prevOffset;
		int curLine = prevLine;
		int curCol = prevCol;		
		//try 
		{
			//char buf[] = new char[1000];
			//input.read(buf, 0, 1000);
			while (true) {
				if (curLine == line && curCol == col)
					break;		
				try 
				{
					if (input.charAt(offset) == '\n') {
						++curLine;
						curCol = 1;
					}
					else
						++curCol;		
				}
				catch (IndexOutOfBoundsException e) {
					e.printStackTrace();
					offset = -1;
					return offset; 
				}
				++offset;					
			}
		}
		/*catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			offset = -1;
		}	*/	
		
		return offset;
	}
	
	public static int getColumn(CharSequence input, int offset) {
		int col = 0;
		if (input.charAt(offset) == '\n') {
			--offset;
			++col;
		}
		
		while (offset >= 0 && input.charAt(offset) == '\n') {
			--offset;
			++col;
		}
			
		return col;
	}
	
	public static String getTextRepresentationOfTemplateAndEntry(LangElem template, LangElem entry) {		
		LangElem clonedTemplate = (LangElem)template.clone(template.getParent());
		TreeIterator iterator = new TreeIterator(clonedTemplate);		
		
		while (iterator.hasNext()) {
			Element elem = iterator.next();
			if (elem instanceof LangElem && ((LangElem)elem).tag.equals(LangElem.ATTRREF)) {
				LangElem attrRef = (LangElem)elem;
				String attrId = attrRef.attrs.getValue(LangElem.ATTRID); 
				LangElem attr = getAttr(entry, attrId);
				
				if (attr == null)
					return null;
									
				LangElem parent = (LangElem)attrRef.getParent();
				int idx = parent.getChilds().indexOf(attrRef);
				parent.getChilds().remove(idx);
				
				String text = getChildsText(attr);
				
				TextElement textRepresentation = new TextElement(new PositionInText(0,0), text.length(), text,parent, parent.getDRLDocument());
				
				parent.getChilds().add(idx, textRepresentation);
			}			
		}
			
		return getChildsText(clonedTemplate);
	}

	public static LangElem getAttr(LangElem entry, String id) {
		for (Element entryElem : entry.getChilds()) {
			LangElem attr = (LangElem)entryElem;
			if (attr != null && attr.tag.equals(LangElem.ATTR)) {
				if (attr.attrs.getValue(LangElem.ID).equals(id)) {
					return attr; 
				}
			}
		}
		
		return null;
	}
	
	private static String getChildsText(LangElem le) {
		String text = "";
		for (Element elemText : le.getChilds()) {
			text += elemText.getTextRepresentation();
		}		
		
		return text;
	}
	
	public static boolean isDocBookFragment(DRLDocument doc, PositionInDRL from, PositionInDRL to) {	
		if (from.parent != to.parent) {			
			return false;
		}
		
		if (from.isInTag || to.isInTag) {			
			return false;
		}
		
		LangElem parent = (LangElem)from.parent;
		
		int fromIdx, toIdx;				
		if (from.isInText)
			fromIdx = parent.getChilds().indexOf(from.elem);
		else
			fromIdx = parent.getChilds().indexOf(from.next);
		
		if (to.isInText)
			toIdx = parent.getChilds().indexOf(to.elem);
		else
			toIdx = parent.getChilds().indexOf(to.prev);
		
		for (int i = fromIdx; i <= toIdx; ++i ) {
			if (isDRLLangElem(parent.getChilds().get(i)))
				return false;
			TreeIterator iter = new TreeIterator(parent.getChilds().get(i));
			while (iter.hasNext()) {
				Element elem = iter.next();
				if (isDRLLangElem(elem))
					return false;
			}
		}
				
		return true;
	}
	
	public static boolean isDRLLangElem(Element elem) {		
		if (elem instanceof LangElem) {
			for (String tag : LangElem.TAGS)
				if ( ((LangElem)elem).tag.equals(tag) ) {							
					return true;
				}
		}		
		
		return false; 
	}
	
	public static boolean isDocBookFragment(DRLDocument doc, PositionInText fromText, PositionInText toText) {
		return isDocBookFragment(doc, doc.findByPosition(fromText), doc.findByPosition(toText));		
	}
	
	public static ArrayList<LangElem> getTemplates(ProjectContent projectContent, LangElem directory) {
		String directoryId = directory.attrs.getValue(LangElem.ID);
		
		ArrayList<LangElem> res = new ArrayList<LangElem>(); 
		for(LangElem template : projectContent.templates) {
			if (template.attrs.getValue(LangElem.DIRECTORYID).equals(directoryId))
				res.add(template);
		}
		
		return res;
	}
	

	public static ArrayList<LangElem> getEntrys(LangElem directory) {		
		ArrayList<LangElem> res = new ArrayList<LangElem>(); 
		for(Element elem : directory.getChilds()) {			
			if (elem instanceof LangElem && ((LangElem)elem).tag.equals(LangElem.ENTRY))
				res.add((LangElem)elem);
		}
		
		return res;
	}
	
	public static String getPrefix(DRLDocument doc) {
		String prefix = doc.DRLnsPrefix;
		if (!prefix.equals(""))
			prefix += ":";
		
		return prefix;
	}
	
	public static String getId(ArrayList<Element> elems, String tag) {
		String idBase = tag;
		String resId = "";
		int i = 0;
		boolean goodId = false;
		while (!goodId) {
			resId = idBase + String.valueOf(i);
			goodId = true;
			for (Element elem : elems) {
				if (elem instanceof LangElem) {
					LangElem le = (LangElem)elem;
					if (le.tag.equals(tag) && le.attrs.getValue(LangElem.ID).equals(resId)) {						
						goodId = false;
						break;
					}
				}
			}
			++i;
		}
		
		return resId;
	}
	
	public static boolean isValidId(ArrayList<Element> elems, String tag, String id) {		
		for (Element elem : elems) {
			if (elem instanceof LangElem) {
				LangElem le = (LangElem)elem;
				if (le.tag.equals(tag) && le.attrs.getValue(LangElem.ID).equals(id))						
					return false;
			}
		}

		return true;
	}
}
