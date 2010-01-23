package org.spbu.pldoctoolkit.editors;

//Новая версия

import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.sf.saxon.om.XMLChar;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPropertyListener;
import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;

import org.spbu.pldoctoolkit.registry.RegisteredLocation;

import static org.spbu.pldoctoolkit.registry.RegisteredLocation.*;

//import org.spbu.pldoctoolkit.actions.MarkingErrorHandler;

import org.spbu.pldoctoolkit.registry.ProjectRegistry;

//import org.spbu.pldoctoolkit.templates.TemplatesDocument;

public class DrlCompletionProcessor implements IContentAssistProcessor {
	private static final String INF_PRODUCT_ID = "infproductid";
	private static final String INF_ELEM_ID = "infelemid";
	private static final String DICT_ID = "dictid";
	private static final String DIRECTORY_ID = "directoryid";
	private static final String TEMPLATE_ID = "templateid";
	// private static final SchemaCache SCHEMA_CACHE = new SchemaCache();
	private static final URL SCHEMA_URL = PLDocToolkitPlugin
			.getURL("schema/document-reuse-language.rng");

	protected static final URL DOCBOOK_SCHEMA_URL = DrlPublisherPlugin
			.getURL("schema/docbook/docbook.rng");
	protected static final URL CALSTBLX_SCHEMA_URL = DrlPublisherPlugin
			.getURL("schema/docbook/calstblx.rng");
	protected static final URL DBHIERX_SCHEMA_URL = DrlPublisherPlugin
			.getURL("schema/docbook/dbhierx.rng");
	protected static final URL DBNOTNX_SCHEMA_URL = DrlPublisherPlugin
			.getURL("schema/docbook/dbnotnx.rng");
	protected static final URL DBPOOLX_SCHEMA_URL = DrlPublisherPlugin
			.getURL("schema/docbook/dbpoolx.rng");
	protected static final URL HTMLBLX_SCHEMA_URL = DrlPublisherPlugin
			.getURL("schema/docbook/htmltblx.rng");

	private final IEditorPart editor;

	private RngParse drlParser;

	private RngParse docbookParser;

	private XmlnsGet xmlns;

	private IDocument d;
	private LinkedList<String> l = new LinkedList<String>();
	private String openedTag = "";

	public DrlCompletionProcessor(IEditorPart editor) {

		this.editor = editor;
		URL[] f = new URL[1];
		f[0] = SCHEMA_URL;

		xmlns = new XmlnsGet(editor);
		this.editor.addPropertyListener(new IPropertyListener() {

			int i = 0;

			public void propertyChanged(Object source, int propId) {
				// TODO Auto-generated method stub
				xmlns.update();

			}
		});
		drlParser = new RngParse(f);
		f = new URL[6];
		f[5] = CALSTBLX_SCHEMA_URL;
		f[1] = DOCBOOK_SCHEMA_URL;
		f[2] = DBHIERX_SCHEMA_URL;
		f[3] = DBNOTNX_SCHEMA_URL;
		f[4] = DBPOOLX_SCHEMA_URL;
		f[0] = HTMLBLX_SCHEMA_URL;
		docbookParser = new RngParse(f);
	}

	private ProjectRegistry getRegistry() {
		IProject project = ((IFileEditorInput) editor.getEditorInput())
				.getFile().getProject();
		return PLDocToolkitPlugin.getRegistry(project.getName());
	}

	/* get "Name" from the string such form as "xmln:name" */

	private String getName(String from) {
		int i = 0;
		if (from.length() == 0) {
			return from;
		}
		while ((i < from.length()) && (from.charAt(i) != ':')) {
			i++;
		}
		int begin = 0;
		if (i != from.length()) {
			begin = i;
			begin++;
		}

		return from.substring(begin, from.length());

	}

	/*
	 * Try to find first "<" symbol and return string between position of "<"
	 * and offset
	 */
	private String getPrefix(int offset) {
		String result = "";
		try {
			while ((offset >= 0) && (d.getChar(offset) != '<')) {
				result = d.getChar(offset) + result;
				offset--;
			}

		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	
	
	/*find out if we have to propose  attributes*/
	public boolean attrCheck(String s) {
		int i = s.length() - 1;
		while ((i >= 0) && (!Character.isWhitespace(s.charAt(i)))) {
			i--;
		}
		if (i < 0) {
			return false;
		} else {
			return true;
		}
	}

	
	/*compute if  we have to propose something or not */
	public int position(int offset) {
		try {
			while ((offset >= 0) && (d.getChar(offset) != '>')
					&& (d.getChar(offset) != '<')) {
				offset--;
			}
			if (d.getChar(offset) == '<') {
				return 1;
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	
	//  gets from the string  attributes
	public LinkedList<String> parseAttrib(String str) {
		LinkedList<String> result = new LinkedList<String>();

		int i = 1;
		int state = 0;
		String attribute = "";
		while (i < str.length()) {

			if ((state == 0) && (Character.isWhitespace(str.charAt(i)))) {
				i++;
				continue;
			}

			if ((state == 3) && (str.charAt(i) == '\"')) {
				state = 0;
				i++;
				continue;
			}

			if ((state == 3) && (str.charAt(i) != '\"')) {
				i++;
				continue;
			}

			if ((state != 3) && (str.charAt(i) == '\"')) {
				i++;
				state = 3;
				continue;
			}
			if ((state == 2) && (!Character.isWhitespace(str.charAt(i)))) {
				attribute = attribute + str.charAt(i);
				i++;
				continue;
			}
			if ((state == 2) && (Character.isWhitespace(str.charAt(i)))) {
				i++;
				continue;
			}
			if ((state == 0) && (!Character.isWhitespace(str.charAt(i)))) {
				state = 1;
				attribute = "";
				attribute = attribute + str.charAt(i);
				i++;
				continue;
			}
			if ((state == 1) && (str.charAt(i) != '=')) {
				attribute = attribute + str.charAt(i);
				i++;
				continue;
			}

			if ((state == 1) && (str.charAt(i) == '=')) {
				state = 2;
				if (!attribute.startsWith("xmlns")) {
					result.add(attribute);
				}
				i++;
				continue;
			}
		}

		if (state == 1) {
			result.add(attribute + "#");
		}
		return result;
	}

	
	// find out parent tag and tags between it and offset
	private int parse(int off) {

		int state = 0;
		int depth = 0;
		try {
			while ((depth != -1) && (off >= 0)) {
				switch (state) {
				case 0: {
					while (d.getChar(off) != '>') {
						off--;
					}
					off--;
					state = 1;
					break;
				}
				case 1: {
					String s = "";
					while (d.getChar(off) != '<') {
						s = d.getChar(off) + s;
						off--;
					}
					off--;
					state = 0;
					if (s.charAt(0) == '/') {
						if (depth == 0) {
							l.addFirst(getName(s.substring(1)));
						}
						depth++;
						break;
					}
					if (s.charAt(s.length() - 1) == '/') {
						if (depth == 0) {
							int i = 0;
							while ((i < s.length())
									&& (!Character.isWhitespace(s.charAt(i)))) {
								i++;
							}
							l.addFirst(getName(s.substring(0, i)));
						}
						break;
					}

					depth--;
					if (depth == -1) {
						int i = 0;
						while ((i < s.length())
								&& !Character.isWhitespace(s.charAt(i))) {
							i++;
						}

						openedTag = getName(s.substring(0, i));
					}
					break;
				}

				}
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return off;
	}

	//  propose attributes
	private ICompletionProposal[] AttrProposals(String charSeq, int off) {

		int k = 0;
		String xmlntag = "";
		while ((k < charSeq.length())
				&& (!Character.isWhitespace(charSeq.charAt(k)))) {
			xmlntag = xmlntag + charSeq.charAt(k);
			k++;
		}
		String tag = getName(xmlntag);
		String xmln = "";

		if (xmlntag.length() != tag.length()) {
			xmln = xmlntag.substring(0, xmlntag.length() - tag.length());
		}

		RngParse ns = null;
		if (xmlns.getDocbook().compareTo(xmln) == 0) {
			ns = docbookParser;
		}
		if (xmlns.getDRL().compareTo(xmln) == 0) {
			ns = drlParser;
		}
		if ((ns == null) || (ns.getElement(tag) == null)) {
			return null;
		}

		LinkedList<String> attrib = parseAttrib(charSeq.substring(xmlntag
				.length()));

		String prefix = "";

		if ((attrib.size() != 0) && (attrib.getLast().endsWith("#"))) {
			prefix = attrib.getLast().substring(0,
					attrib.getLast().length() - 1);
			attrib.removeLast();
		}

		HashSet<String> s = ns.findOptionalAttributes(ns.getElement(tag), attrib);

		Object[] li = s.toArray();
		for (int j = 0; j < li.length; j++) {
			String str = (String) li[j];
			if ((str.length() < prefix.length())
					|| (str.substring(0, prefix.length()).toLowerCase()
							.compareTo(prefix.toLowerCase()) != 0)) {
				s.remove(str);

			}
		}

		k = 0;
		ICompletionProposal[] result = new ICompletionProposal[s.size()];
		for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			String ss = (String) iterator.next();
			result[k] = new CompletionProposal(ss + "=\"\"", off
					- prefix.length() + 1, prefix.length(), ss.length() + 3);
			k++;
		}

		return result;

	}

	//remove  from the list tags from another namespace	
	private LinkedList<Vertex> absorbeAnotherNs(RngParse ns,
			LinkedList<String> tags) {
		LinkedList<Vertex> v = new LinkedList<Vertex>();
		for (Iterator it = tags.iterator(); it.hasNext();) {
			String s1 = (String) it.next();
			if (ns.getElement(s1) != null) {
				v.add(ns.getElement(s1));
			}
		}
		return v;
	}

	
	// remove from the set strings, which prefix doesn't agree with "string prefix"
	private HashSet<String> prefixCheck(HashSet<String> h, String prefix) {
		Object[] l = h.toArray();
		for (int j = 0; j < l.length; j++) {
			String str = (String) l[j];
			if ((str.length() < prefix.length())
					|| (str.substring(0, prefix.length()).toLowerCase()
							.compareTo(prefix.toLowerCase()) != 0)) {
				h.remove(str);
			}
		}
		return h;
	}

	
	// create Proposals
	private ICompletionProposal[] compileCompletion(HashSet<String> s1,
			int off, String xmlnWr, String tag) {
		ICompletionProposal[] result = new ICompletionProposal[s1.size()];
		int i = 0;
		for (Iterator iterator = s1.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			LinkedList<String> attrs;
			String xm;
			if (drlParser.getElement(str) != null) {
				attrs = drlParser.findObligatoryAttributes(drlParser
						.getElement(str));
				xm = xmlns.getDRL();
			} else {
				attrs = docbookParser.findObligatoryAttributes(docbookParser
						.getElement(str));
				xm = xmlns.getDocbook();
			}
			String attributes = "";
			for (Iterator iterator2 = attrs.iterator(); iterator2.hasNext();) {
				String s11 = (String) iterator2.next();
				attributes = attributes + " " + s11 + "=\"\"";

			}
			result[i] = new CompletionProposal(xm + str + attributes, off
					- xmlnWr.length() - tag.length(), xmlnWr.length()
					+ tag.length(), xm.length() + str.length()
					+ attributes.length());
			i++;

		}
		return result;
	}

	
	// find out proposals in case of docbook's parent tag
	private ICompletionProposal[] docbookOpened(String s, int op) {
		LinkedList<Vertex> v = absorbeAnotherNs(docbookParser, l);

		String tag = getName(s);
		String xmlnw = "";
		HashSet<String> hs = new HashSet<String>();
		int opened = op--;
		if (tag.length() != s.length()) {
			xmlnw = s.substring(0, s.length() - tag.length());
		}
		if ((xmlnw == "") || (xmlnw.compareTo(xmlns.getDocbook()) == 0)) {
			hs.addAll(docbookParser.findNextElements(docbookParser
					.getElement(openedTag), v));
		}

		while (drlParser.getElement(openedTag) == null) {
			opened = parse(opened);
		}
		/* Quick Solution */
		if ((xmlnw == "") || (xmlnw.compareTo(xmlns.getDRL()) == 0)) {
			if ((openedTag != "Replace-Nest") || (openedTag != "Insert-After")
					|| (openedTag != "Insert-Before")) {
				hs.addAll(drlParser.findNextElements(drlParser
						.getElement(openedTag), new LinkedList<Vertex>()));

			}
		}
		/* Quick Solution */
		if (hs.contains("#")) {
			hs.remove("#");
		}
		hs = prefixCheck(hs, tag);

		return compileCompletion(hs, op + 1, xmlnw, tag);
	}

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		d = viewer.getDocument();

		int off = offset - 1;
		String charSeq = getPrefix(off);
		try {

			if (position(off) == 1) {
				if (attrCheck(charSeq)) {
					return AttrProposals(charSeq, off);
				}
				l = new LinkedList<String>();
				int en = parse(off - charSeq.length() - 1);
				if (docbookParser.getElement(openedTag) != null) {
					return docbookOpened(charSeq, offset);
				}
				boolean ifFirst = false;
				if ((openedTag.compareTo("?xml") == 0) && (l.isEmpty())) {
					ifFirst = true;
				}
				if ((drlParser.getElement(openedTag) != null) || ifFirst) {
					LinkedList<Vertex> v = absorbeAnotherNs(drlParser, l);
					Vertex startFrom;
					if (ifFirst) {
						startFrom = drlParser.getStart();
					} else {
						startFrom = drlParser.getElement(openedTag);
					}
					HashSet<String> s = drlParser
							.findNextElements(startFrom, v);
					/* Quick Solution */
					if (openedTag.compareTo("Entry") == 0) {
						parse(en);
						if (openedTag.compareTo("Dictionary") == 0) {
							return null;
						}
					}
					int n = 0;
					String charSeq1 = getName(charSeq);
					String xmln = "";
					if (charSeq.length() != charSeq1.length()) {
						xmln = charSeq.substring(0, charSeq.length()
								- charSeq1.length());
					}
					charSeq = charSeq1;
					boolean fl = false;
					if (s.contains("#")) {
						fl = true;
						s.remove("#");
					}
					if ((xmln.length() == 0)
							|| (xmln.compareTo(xmlns.getDRL()) == 0)) {
						s = prefixCheck(s, charSeq);
					}
					if (fl) {
						if ((xmln.length() == 0)
								|| (xmln.compareTo(xmlns.getDocbook()) == 0)) {
							HashSet<String> doc = docbookParser.getElements();
							s.addAll(prefixCheck(doc, charSeq));
						}
					}
					return compileCompletion(s, off + 1, xmln, charSeq);

				}
			}

			if (d.getChar(off) == '\"') {

				while (XMLChar.isSpace(d.getChar(--off)))
					;
				if (d.getChar(off) != '=')
					return null;
				while (XMLChar.isSpace(d.getChar(--off)))
					;
				int attributeNameEnd = off + 1;
				while (XMLChar.isName(d.getChar(--off)))
					;
				if (!XMLChar.isSpace(d.getChar(off)))
					return null;
				int attributeNameStart = off + 1;
				String attributeName = d.get(attributeNameStart,
						attributeNameEnd - attributeNameStart);
				String type = null;
				if (INF_PRODUCT_ID.equals(attributeName))
					type = INF_PRODUCT;
				else if (INF_ELEM_ID.equals(attributeName))
					type = INF_ELEMENT;
				else if (DICT_ID.equals(attributeName))
					type = DICTIONARY;
				else if (DIRECTORY_ID.equals(attributeName))
					type = DIRECTORY;
				else if (TEMPLATE_ID.equals(attributeName))
					type = DIRTEPLATE;
				else if (type == null)
					return null;
				List<RegisteredLocation> list = getRegistry().findForType(type);
				if (list.isEmpty())
					return null;
				ICompletionProposal[] result = new ICompletionProposal[list
						.size()];
				for (int i = 0; i < list.size(); i++) {
					RegisteredLocation loc = list.get(i);
					result[i] = new CompletionProposal(loc.getId(), offset, 0,
							loc.getId().length(), null, loc.getId() + " // "
									+ loc.getName(), null, loc.getId());
				}
				return result;
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return new char[] { '<', '\"' };
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

}
