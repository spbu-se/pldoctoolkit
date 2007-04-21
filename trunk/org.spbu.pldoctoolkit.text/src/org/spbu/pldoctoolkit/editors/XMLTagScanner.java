package org.spbu.pldoctoolkit.editors;

import net.sf.saxon.om.XMLChar;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;

public class XMLTagScanner extends RuleBasedScanner {
	private final ColorManager manager;
	
	public XMLTagScanner(ColorManager manager) {
		this.manager = manager;
		
		setRules(new IRule[] {
				new NameAndAttributeRule(),
				new SingleLineRule("\"", "\"", new Token(new TextAttribute(manager.getColor(IXMLColorConstants.STRING)))),
				new WhitespaceRule(new XMLWhitespaceDetector()),
		});
	}
	
	private class NameAndAttributeRule implements IRule {
		public IToken evaluate(ICharacterScanner scanner) {
			int ch = scanner.read();
			if (ch == ICharacterScanner.EOF)
				return Token.UNDEFINED;
			if (ch == '<') {
				while (XMLChar.isName(scanner.read()));
				scanner.unread();
				return new Token(new TextAttribute(manager.getColor(IXMLColorConstants.TAG)));
			}
			if (!XMLChar.isName(ch)) {
				scanner.unread();
				return Token.UNDEFINED;
			}
			while (XMLChar.isName(ch = scanner.read()));
			if (XMLChar.isSpace(ch))
				while (XMLChar.isSpace(ch = scanner.read()));
			if (ch == '=')
				return new Token(new TextAttribute(manager.getColor(IXMLColorConstants.ATTRIBUTE)));
			scanner.unread();
			scanner.unread();
			return Token.UNDEFINED;
		}
	}
}
