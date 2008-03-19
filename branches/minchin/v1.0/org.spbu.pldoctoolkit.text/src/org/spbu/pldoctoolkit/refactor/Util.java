package org.spbu.pldoctoolkit.refactor;

import java.io.IOException;
import java.io.Reader;
import java.util.Stack;

//import net.sf.saxon.dom.NodeOverNodeInfo;

public class Util {
	// Предполагается, что строка заканчивается концом тэга
	public static int findtagStartPos(Reader text, int from) {
		Stack<Character> stack = new Stack<Character>();
		int curPos = from;//text.length();
		stack.push('>'); // кладём закр скобку
		--curPos;		
		while (!stack.isEmpty() || curPos < 0) {			
			char curTop = stack.peek();
			char buf[] = new char[1];
			
			try {
				text.read(buf, 1, 1);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			
			char curChar = buf[0];
			
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
	
	public static int getOffset(Reader input, int line, int col) {
		int offset = 0;
		int curLine = 1;
		int curCol = 1;		
		try {
			char buf[] = new char[1000];
			input.read(buf, 0, 1000);
			while (true) {				
				if (buf[offset] == '\n') {
					++curLine;
					curCol = 0;
				}
				else
					++curCol;		
			
				++offset;
				if (curLine == line && curCol == col)
					break;			
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			offset = -1;
		}		
		
		return offset;
	}
}
