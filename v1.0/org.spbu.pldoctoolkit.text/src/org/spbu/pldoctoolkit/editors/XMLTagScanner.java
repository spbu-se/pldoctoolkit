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
			if (ch == '<') {
				while (isName(ch = scanner.read()));
				scanner.unread();
				return new Token(new TextAttribute(manager.getColor(IXMLColorConstants.TAG)));
			}
			if (!isName(ch)) {
				scanner.unread();
				return Token.UNDEFINED;
			}
			while (isName(ch = scanner.read()));
			if (isSpace(ch))
				while (isSpace(ch = scanner.read()));
			if (ch == '=')
				return new Token(new TextAttribute(manager.getColor(IXMLColorConstants.ATTRIBUTE)));
			scanner.unread();
			scanner.unread();
			return Token.UNDEFINED;
		}
		
		private boolean isName(int ch) {
			return ch != ICharacterScanner.EOF && XMLChar.isName(ch);
		}
		
		private boolean isSpace(int ch) {
			return ch != ICharacterScanner.EOF && XMLChar.isSpace(ch);
		}
	}
}
