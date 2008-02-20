package org.spbu.pldoctoolkit.refactor;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.part.FileEditorInput;
import org.spbu.pldoctoolkit.parser.DRLParser;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.xml.sax.InputSource;

public class ProjectContent {
	public HashMap<IFile , DRLDocument> DRLDocs = new HashMap<IFile, DRLDocument>();	
	public ArrayList<LangElem> Adapters = new ArrayList<LangElem>();
	public ArrayList<LangElem> InfElemRefs = new ArrayList<LangElem>();
//	public String projectName;
	
	public void parseAll(IProject project) {
		DRLDocs.clear();
		try {
			for (IResource resource : project.members()) {
				if (resource instanceof IFile) {
					IFile file = (IFile) resource;
					//FileEditorInput input = new FileEditorInput(file);
					DRLDocument doc = DRLParser.parse( new InputSource(file.getContents()), this );
					DRLDocs.put(file, doc);
				}
			}
		}
		catch (CoreException e){
			e.printStackTrace();
		}
	}
	
	public void saveAll() {
		for (IFile file : DRLDocs.keySet()) {			
			String docText = DRLDocs.get(file).getTextRepresentation();
			try {
				file.setContents(new ByteArrayInputStream(docText.getBytes()), 0, null);
			}
			catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
}
