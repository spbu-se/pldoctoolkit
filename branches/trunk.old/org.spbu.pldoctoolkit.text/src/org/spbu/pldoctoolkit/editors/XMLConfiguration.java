package org.spbu.pldoctoolkit.editors;

import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.IEditorPart;

public class XMLConfiguration extends SourceViewerConfiguration {
	private final ColorManager colorManager;
	private final IEditorPart editor;
	
	private XMLDoubleClickStrategy doubleClickStrategy;
	private XMLTagScanner tagScanner;
	private XMLScanner scanner;

	public XMLConfiguration(ColorManager colorManager, IEditorPart editor) {
		this.colorManager = colorManager;
		this.editor = editor;
	}
	
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			XMLPartitionScanner.XML_COMMENT,
			XMLPartitionScanner.XML_TAG };
	}
	
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		assistant.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
		/*changed*/
	//	assistant.setContentAssistProcessor(new DrlCompletionProcessor(editor), IDocument.DEFAULT_CONTENT_TYPE);
		/*changed*/
		assistant.setContentAssistProcessor(new DrlCompletionProcessor(editor), XMLPartitionScanner.XML_TAG);
		
		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(100);
		
		return assistant;
	}

	@Override
	public String getConfiguredDocumentPartitioning(ISourceViewer sourceViewer) {
		return DrlTextEditor.XML_PARTITIONING;
	}

	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new XMLDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected XMLScanner getXMLScanner() {
		if (scanner == null) {
			scanner = new XMLScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(new TextAttribute(colorManager.getColor(IXMLColorConstants.DEFAULT))));
		}
		return scanner;
	}
	
	protected XMLTagScanner getXMLTagScanner() {
		if (tagScanner == null) {
			tagScanner = new XMLTagScanner(colorManager);
			tagScanner.setDefaultReturnToken(
				new Token(new TextAttribute(colorManager.getColor(IXMLColorConstants.TAG))));
		}
		return tagScanner;
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType) {
		if (!XMLPartitionScanner.XML_COMMENT.equals(contentType))
			return new IAutoEditStrategy[] {new XMLAutoEditStrategy(), new DefaultIndentLineAutoEditStrategy()};
		return super.getAutoEditStrategies(sourceViewer, contentType);
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		reconciler.setDocumentPartitioning(DrlTextEditor.XML_PARTITIONING);
		
		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getXMLTagScanner());
		reconciler.setDamager(dr, XMLPartitionScanner.XML_TAG);
		reconciler.setRepairer(dr, XMLPartitionScanner.XML_TAG);

		dr = new DefaultDamagerRepairer(getXMLScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr = 
			new NonRuleBasedDamagerRepairer(new TextAttribute(colorManager.getColor(IXMLColorConstants.XML_COMMENT)));
		reconciler.setDamager(ndr, XMLPartitionScanner.XML_COMMENT);
		reconciler.setRepairer(ndr, XMLPartitionScanner.XML_COMMENT);

		return reconciler;
	}
}