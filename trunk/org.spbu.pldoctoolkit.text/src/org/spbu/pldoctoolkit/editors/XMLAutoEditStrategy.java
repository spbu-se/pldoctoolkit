package org.spbu.pldoctoolkit.editors;

import net.sf.saxon.om.XMLChar;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

public class XMLAutoEditStrategy implements IAutoEditStrategy {
	public void customizeDocumentCommand(IDocument doc, DocumentCommand cmd) {
		if (cmd.getCommandCount() == 1 && ">".equals(cmd.text)) {
			try {
				if (doc.getChar(cmd.offset - 1) == '/')
					return;
				if (doc.getChar(cmd.offset - 1) == '-' && doc.getChar(cmd.offset - 2) == '-')
					return;
				
				int off = cmd.offset - 1;
				while (off >= 0 && doc.getChar(off) != '<')
					off--;
				if (off < 0)
					return;
				
				off++;
				if (doc.getChar(off) == '/')
					return;
				
				int elementNameOffset = off;
				while (off < cmd.offset && (XMLChar.isName(doc.getChar(off))))
					off++;
				int elementNameLength = off - elementNameOffset;
				
				doc.replace(cmd.offset, 0, "</" + doc.get(elementNameOffset, elementNameLength) + ">");
			} catch (BadLocationException e) {
			}
			return;
		}
		if (cmd.getCommandCount() == 1 && "\"".equals(cmd.text)) {
			try {
				int off = cmd.offset;
				while (XMLChar.isSpace(doc.getChar(--off)));
				if (doc.getChar(off) == '=')
					doc.replace(cmd.offset, 0, "\"");
			} catch (BadLocationException e) {
			}
		}
	}
}
