package org.spbu.pldoctoolkit.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

import org.spbu.pldoctoolkit.parser.DRLLang.DRLDocument;
import org.spbu.pldoctoolkit.refactor.ProjectContent;
import org.spbu.pldoctoolkit.registry.ProjectRegistryImpl;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import com.thaiopensource.xml.sax.Jaxp11XMLReaderCreator;
//import org.apache.xerces.parsers.;
import org.xml.sax.helpers.XMLReaderFactory;
//import org.xml.sax.Parser;
//import org.apache.xerces.parsers.SAXParser;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class DRLParser {
	//private static XMLReader xmlReader;// = new Jaxp11XMLReaderCreator().createXMLReader();
	private static XMLReader xmlReader;// = new SAXParser();
	private static DRLContentHandler contetnHandler;
	
	static
	{
		try 
		{
		//	xmlReader = new Jaxp11XMLReaderCreator().createXMLReader();
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			SAXParser parser = factory.newSAXParser();			
			xmlReader = parser.getXMLReader();//XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");//factory.newSAXParser();//new SAXParser();
			
			contetnHandler = new DRLContentHandler();		
			xmlReader.setContentHandler(contetnHandler);
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
	
	public static DRLDocument parse(InputStream input1, InputStream input2, ProjectContent project)
	{		
		try {			
			contetnHandler.setProject(project);
			//contetnHandler.input = new InputSource(new InputStreamReader(input1));
			
			CharBuffer cb = CharBuffer.allocate(0);
			StringBuffer sb = new StringBuffer();
			InputStreamReader sr = new InputStreamReader(input1);			
			char cbuf[] = new char[100];
			while (true) {
				int l = sr.read(cbuf);
				if (l == -1)
					break;
				//cb.allocate(l);
				//cb.put(cbuf, 0, l);
				sb.append(cbuf, 0, l);
			}
			contetnHandler.inputText = sb;
			//contetnHandler.input.getCharacterStream().read(cbuf, 0, 100);
			xmlReader.parse(new InputSource(new InputStreamReader(input2)));//input.getCharacterStream().));			
		}
		catch (IOException e) {
			System.out.print(e.getMessage());
		}
		catch (SAXException e) {
			System.out.print(e.getMessage());
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
		
		return ((DRLContentHandler)xmlReader.getContentHandler()).doc;
		//xmlReader.setErrorHandler(errorHandler);
	}	
/*	
	public void setProject(ProjectContent project) {
		contetnHandler.setProject(project);
	}*/
}
