package org.spbu.pldoctoolkit.editors;

import static org.spbu.pldoctoolkit.registry.RegisteredLocation.*;

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
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.registry.ProjectRegistry;
import org.spbu.pldoctoolkit.registry.RegisteredLocation;

public class DrlCompletionProcessor implements IContentAssistProcessor {
	private static final String INF_PRODUCT_ID = "infproductid";
	private static final String INF_ELEM_ID = "infelemid";
	private static final String DICT_ID = "dictid";
	private static final String DIRECTORY_ID = "directoryid";
	private static final String TEMPLATE_ID = "templateid";
	
	private final IEditorPart editor;

	public DrlCompletionProcessor(IEditorPart editor) {
		this.editor = editor;
	}

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		IDocument d = viewer.getDocument();
		try {
			int off = offset - 1;
			if (d.getChar(off) == '<') {
				// TODO
				return null;
			} 
			if (d.getChar(off) == '\"') {
				while (XMLChar.isSpace(d.getChar(--off)));
				if (d.getChar(off) != '=')
					return null;
				while (XMLChar.isSpace(d.getChar(--off)));
				int attributeNameEnd = off + 1;
				while (XMLChar.isName(d.getChar(--off)));
				if (!XMLChar.isSpace(d.getChar(off)))
					return null;
				int attributeNameStart = off + 1;
				String attributeName = d.get(attributeNameStart, attributeNameEnd - attributeNameStart);
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
				ICompletionProposal[] result = new ICompletionProposal[list.size()];
				for (int i = 0; i < list.size(); i++) {
					RegisteredLocation loc = list.get(i);
					result[i] = new CompletionProposal(loc.getId(), offset, 0, loc.getId().length(), null, loc.getId() + " // " + loc.getName(), null, loc.getId());
				}
				return result;
			}
		} catch (BadLocationException e) {
		}
		return null;
	}

	private ProjectRegistry getRegistry() {
		IProject project = ((IFileEditorInput)editor.getEditorInput()).getFile().getProject();
		return PLDocToolkitPlugin.getRegistry(project.getName());
	}
	
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return null;
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] {'<', '\"'};
	}

	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	public String getErrorMessage() {
		return null;
	}
}
