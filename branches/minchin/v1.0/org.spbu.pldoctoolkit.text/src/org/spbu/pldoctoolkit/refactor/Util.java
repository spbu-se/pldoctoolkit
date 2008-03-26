package org.spbu.pldoctoolkit.refactor;

import java.io.IOException;
import java.io.Reader;
import java.util.Stack;

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
}
