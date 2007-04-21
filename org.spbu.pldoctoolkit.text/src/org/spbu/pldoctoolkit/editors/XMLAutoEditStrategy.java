package org.spbu.pldoctoolkit.editors;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

public class XMLAutoEditStrategy implements IAutoEditStrategy {
	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		if (command.getCommandCount() == 1 && ">".equals(command.text)) {
			try {
				if (document.getChar(command.offset - 1) == '/')
					return;
				
				int off = command.offset - 1;
				while (off >= 0 && document.getChar(off) != '<')
					off--;
				if (off < 0)
					return;
				
				off++;
				if (document.getChar(off) == '/')
					return;
				
				int elementNameOffset = off;
				while (off < command.offset && (Character.isJavaIdentifierPart(document.getChar(off)) || document.getChar(off) == ':'))
					off++;
				int elementNameLength = off - elementNameOffset;
				
				document.replace(command.offset, 0, "</" + document.get(elementNameOffset, elementNameLength) + ">");
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}
}
