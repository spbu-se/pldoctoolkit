package org.spbu.pldoctoolkit.actions;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.BadLocationException;

import net.sf.saxon.om.NodeInfo;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
//import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.actions.SelectionProviderAction;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.PLDocToolkitPlugin;
import org.spbu.pldoctoolkit.cache.SchemaCache;
import org.spbu.pldoctoolkit.dialogs.SelectIntoInfElemDialog;
//import org.spbu.pldoctoolkit.refactor.Util;
import org.spbu.pldoctoolkit.parser.DRLParser;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.Element;
import org.spbu.pldoctoolkit.refactor.PositionInDRL;
import org.spbu.pldoctoolkit.refactor.PositionInText;
import org.spbu.pldoctoolkit.registry.ProjectRegistry;
import org.spbu.pldoctoolkit.registry.RegisteredLocation;
import org.w3c.dom.Document;
import org.xml.sax.DTDHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.thaiopensource.validate.Validator;
import com.thaiopensource.xml.sax.Jaxp11XMLReaderCreator;

public class MyAction extends Action{//SelectionProviderAction{
	IEditorPart editor;
	TextEditor te;
	
	public MyAction(IEditorPart editor) throws Exception {
		super("My action");
		this.editor = editor;
		te = (TextEditor)editor;
		validator = SCHEMA_CACHE.getValidator(SCHEMA_URL, errorHandler);
		xmlReader = new Jaxp11XMLReaderCreator().createXMLReader();		
		xmlReader.setErrorHandler(errorHandler);
	}
	
	public void run() {
		
//		ProjectRegistry registry = PLDocToolkitPlugin.getRegistry("qwe");
		
		FileEditorInput editorInput = (FileEditorInput) editor.getEditorInput();
		
//		List<RegisteredLocation> list = registry.findForId("hahaha");//findForFilePath(editorInput.getFile().getFullPath());
//		Document doc1 = list.get(0).getNode().getOwnerDocument();
		//NodeInfo
		
		//PLDocToolkitPlugin.call(doc1);
		//Util.findNodeByPosition(doc1, 1, 1);
		
		IDocument doc = te.getDocumentProvider().getDocument(editorInput);
		String text = doc.get();
		ISelection sel = te.getSelectionProvider().getSelection();
		TextSelection ts = (TextSelection) sel;
		try {
			String selectedText = doc.get(ts.getOffset(), ts.getLength());
//			validate("<temp>" + selectedText + "</temp>");
			DRLDocument DRLdoc = DRLParser.parse(new InputSource(new StringReader(doc.get(0, doc.getLength()))));
			int line1 = ts.getStartLine();
			int column1 = ts.getOffset() - doc.getLineOffset(line1);
			int line2 = ts.getEndLine();
			int column2 = ts.getOffset() + ts.getLength() - doc.getLineOffset(line2);
			PositionInDRL pos1 = DRLdoc.findByPosition(new PositionInText(line1 + 1, column1 + 1));
			PositionInDRL pos2 = DRLdoc.findByPosition(new PositionInText(line2 + 1, column2 + 1));
			
			if (pos1.parent == pos2.parent){
				int from = pos1.parent.getChilds().indexOf(pos1.next);
				int to = pos1.parent.getChilds().indexOf(pos2.prev);
				
				ArrayList<Element> childsToInsert = pos1.parent.removeChilds(from, to);
				DRLdoc.getChilds().get(0).appendChilds(childsToInsert);
				
				doc.set(DRLdoc.getTextRepresentation());
			}
			
			int i = 10;
			
			
/*		
		
			if (!errorHandler.succeded())
				return;
		
			SelectIntoInfElemDialog dialog = new SelectIntoInfElemDialog(editor.getSite().getShell());
		
			int res = dialog.open();
			if ( res == Window.OK) {			
				if (sel instanceof TextSelection) {				
					try {				
						String textToInsert = "<InfElem id = " + '"' + dialog.getInfElemId()+ '"' +
								" name = "  + '"' + dialog.getInfElemName() + '"' +
								">\n" +
								selectedText +
								"\n</InfElem>\n";
					
						doc.replace(ts.getOffset(), ts.getLength(),
								"<InfElemRef elemid = " + '"' + dialog.getInfElemId() + '"' + "/>");					
						FindReplaceDocumentAdapter findReplaceAdapter = new FindReplaceDocumentAdapter(doc);					
						int insOffset = doc.search(doc.getLength() - 1, "<", false, true, false);
						doc.replace(insOffset, 0, textToInsert);
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.print(text);
				}
			}
			*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	private static final SchemaCache SCHEMA_CACHE = new SchemaCache();
	private static final URL SCHEMA_URL = PLDocToolkitPlugin.getURL("schema/SelectInfElemCorrect.rng");

	private final MyErrorHandler errorHandler = new MyErrorHandler();
	private XMLReader xmlReader;
	private final Validator validator;	
	
	private boolean validate(String what)
	{		
		try {
			validator.reset();
			IFileEditorInput editorInput = (IFileEditorInput) editor.getEditorInput();
			final IFile editorFile = editorInput.getFile();
			editorFile.deleteMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
			xmlReader.setContentHandler(validator.getContentHandler());
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			//errorHandler.setResource(editorFile);
			
			xmlReader.parse(new InputSource(new StringReader(what)));//editorFile.getLocationURI().toString()));
			//DRLParser.parse(new InputSource(new StringReader(what)));
		//} catch (SAXException e) {
			// ignored
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return true;
	}
/*	
	public ValidateDrlOnSaveAction(IEditorPart editor) throws Exception {
		super("Validate DRL...");
		if (editor == null)
			throw new NullPointerException("editor cannot be null");
		this.editor = editor;
		validator = SCHEMA_CACHE.getValidator(SCHEMA_URL, errorHandler);
		xmlReader = new Jaxp11XMLReaderCreator().createXMLReader();
		xmlReader.setErrorHandler(errorHandler);
	}
*//*
	@Override
	public void run() {
		try {
			validator.reset();
			IFileEditorInput editorInput = (IFileEditorInput) editor.getEditorInput();
			final IFile editorFile = editorInput.getFile();
			editorFile.deleteMarkers(IMarker.MARKER, true, IResource.DEPTH_ZERO);
			xmlReader.setContentHandler(validator.getContentHandler());
			DTDHandler dtdHandler = validator.getDTDHandler();
			if (dtdHandler != null)
				xmlReader.setDTDHandler(dtdHandler);
			errorHandler.setResource(editorFile);
			xmlReader.parse(new InputSource(editorFile.getLocationURI().toString()));
		} catch (SAXException e) {
			// ignored
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}*/

}
