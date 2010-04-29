package org.spbu.pldoctoolkit.refactor;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.spbu.pldoctoolkit.parser.DRLParser;
import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.parser.DRLLang.LangElem;
import org.spbu.pldoctoolkit.registry.IProjectContent;

public class ProjectContent implements IProjectContent{
	public HashMap<IFile , DRLDocument> DRLDocs = new HashMap<IFile, DRLDocument>();	
	public ArrayList<LangElem> adapters = new ArrayList<LangElem>();
	public ArrayList<LangElem> infElemRefs = new ArrayList<LangElem>();
	public ArrayList<LangElem> dictionarys = new ArrayList<LangElem>();
	public ArrayList<LangElem> directories = new ArrayList<LangElem>();
	public ArrayList<LangElem> infElems = new ArrayList<LangElem>();
	public ArrayList<LangElem> nests = new ArrayList<LangElem>();
	public ArrayList<LangElem> finalInfPrs = new ArrayList<LangElem>();
	public ArrayList<LangElem> infPrs = new ArrayList<LangElem>();
	public ArrayList<LangElem> templates = new ArrayList<LangElem>();
	//lebedkova
	//finalInfProducts with defined variables
	private HashMap<String, ArrayList<String>> finalInfProductsVariables = new HashMap<String,ArrayList<String>>();
//	public String projectName;
/*	
	public void parseAll(IProject project) {
		DRLDocs.clear();
		try {
			for (IResource resource : project.members()) {
				if (resource instanceof IFile) {
					IFile file = (IFile) resource;
					String ext = file.getFileExtension();
					if (ext != null && ext.equals("drl"))
					{
						//DRLDocument doc = DRLParser.parse( file.getContents(), file.getContents(), this );
						//DRLDocs.put(file, doc);
					}
				}
			}
		}
		catch (CoreException e){
			e.printStackTrace();
		}
	}
*/	
	public void saveAll() {
		for (IFile file : DRLDocs.keySet()) {			
			String docText = DRLDocs.get(file).getTextRepresentation();
			try {
				file.setContents(new ByteArrayInputStream(docText.getBytes(file.getCharset())), 0, null);
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void add(IFile file) {
		try {
			removeInfoAboutFile(file);
			DRLDocument doc = DRLParser.parse( file.getContents(), file.getContents(), this, file.getCharset());
			DRLDocs.put(file, doc);
			doc.file = file;
		}
		catch (CoreException e){
			e.printStackTrace();
		}
		catch (Exception e)
		//catch (SAXException e) 
		{
			System.out.print(e.getMessage());
		}
		catch (Error e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void remove(IFile file) {
		DRLDocs.remove(file);
	}

	public void change(IFile file) {		
		try {
			removeInfoAboutFile(file);
			DRLDocument doc = DRLParser.parse( file.getContents(), file.getContents(), this, file.getCharset() );
			DRLDocs.put(file, doc); // If the map previously contained a mapping for the key, the old value is replaced.
			doc.file = file;
		}
		catch (CoreException e){
			e.printStackTrace();
		}
	}
	
	private void removeInfoAboutFile(IFile file) {
		DRLDocument doc = DRLDocs.get(file);
	
		removeInfoAboutDocFromList(adapters, doc);
		removeInfoAboutDocFromList(infElemRefs, doc);
		removeInfoAboutDocFromList(dictionarys, doc);
		removeInfoAboutDocFromList(directories, doc);
		removeInfoAboutDocFromList(infElems, doc);
		removeInfoAboutDocFromList(nests, doc);
		removeInfoAboutDocFromList(finalInfPrs, doc);
		removeInfoAboutDocFromList(infPrs, doc);
		removeInfoAboutDocFromList(templates, doc);
		/*
		int i = 0;
		while (i < Adapters.size()) {
			if (Adapters.get(i).getDRLDocument() == doc)
				Adapters.remove(i);
			else
				++i;
		}
		
		i = 0;
		while (i < InfElemRefs.size()) {
			if (InfElemRefs.get(i).getDRLDocument() == doc)
				InfElemRefs.remove(i);
			else
				++i;
		}
		
		i = 0;
		while (i < dictionarys.size()) {
			if (dictionarys.get(i).getDRLDocument() == doc)
				dictionarys.remove(i);
			else
				++i;
		}
		*/
	}
	
	private static void removeInfoAboutDocFromList(ArrayList<LangElem> list, DRLDocument doc) {
		int i = 0;
		while (i < list.size()) {
			if (list.get(i).getDRLDocument() == doc)
				list.remove(i);
			else
				++i;
		}
	}
	
	public boolean containsVariable(LangElem finalInfProduct, String varName) {
		String name = finalInfProduct.attrs.getValue(LangElem.ID);
		if(finalInfProductsVariables.containsKey(name)) {
			ArrayList<String> vars = finalInfProductsVariables.get(name);
			if(vars.contains(varName)) 
				return true;
		}
		return false;
	}
	
	public void setFinalInfProdValiable(String finalInfProduct, String varName) {
		if(finalInfProduct == null || finalInfProduct.isEmpty())
			return;
		ArrayList<String> vars;
		if(finalInfProductsVariables.containsKey(finalInfProduct)) {
			vars = finalInfProductsVariables.get(finalInfProduct);
		} 
		else {
			vars = new ArrayList<String>();
		}
		vars.add(varName);
		finalInfProductsVariables.put(finalInfProduct, vars);
	}
}
