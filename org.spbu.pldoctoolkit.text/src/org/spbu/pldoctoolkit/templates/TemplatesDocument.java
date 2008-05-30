package org.spbu.pldoctoolkit.templates;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.spbu.pldoctoolkit.DrlPublisherPlugin;
import org.w3c.dom.Document;

public class TemplatesDocument {
	public int numOfTemplates;
	public Template [] templates;
	
	public TemplatesDocument(){
		try{
			File TemplatesFile = new File(DrlPublisherPlugin.getDefault().getPath() + "templates/Templates.xml");
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        Document  document = builder.parse(TemplatesFile);
		    int j = 0;
	        for (int i = 0; i < document.getDocumentElement().getChildNodes().getLength(); i++){
		    	if (document.getDocumentElement().getChildNodes().item(i).getNodeName().equals("Template")){
		    		j++;	
		    	}	
	        }
	        this.numOfTemplates = j;
	        this.templates = new Template[numOfTemplates];
	        j = 0;
	        for (int i = 0; i < document.getDocumentElement().getChildNodes().getLength(); i++){
		    	if (document.getDocumentElement().getChildNodes().item(i).getNodeName().equals("Template")){
		    		templates[j] = new Template(document.getDocumentElement().getChildNodes().item(i).getAttributes().item(0).getNodeValue(), 
		    				document.getDocumentElement().getChildNodes().item(i).getChildNodes().item(1).getTextContent());	    	
		    		j++;	
		    	}
		    }		    
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
