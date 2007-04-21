package org.spbu.pldoctoolkit.editors;

import net.sf.saxon.om.XMLChar;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class XMLWhitespaceDetector implements IWhitespaceDetector {
	public boolean isWhitespace(char c) {
		return XMLChar.isSpace(c);
	}
}
