package org.eclipse.xslt.conversion.actions;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Stack;

import com.a7soft.examxml.ExamXML;

// class for parsing diff xml file
// returns string with list of changes in webratio xml file
public class DiffHandler extends DefaultHandler{
	
	  // constants with status of element in xml diff file
	  private static final int NO_CHANGE = 0;
	  private static final int CHANGED_OLD = 1;
	  private static final int CHANGED_NEW = 2;
	  private static final int DELETED = 3;
	  private static final int ADDED = 4; 
	  
	  // changed Status of current element;
	  private int changedStatus = NO_CHANGE;
	  
	  // documents to compare changed part of XML file
	  private Document partOld = null;
	  private Document partNew = null;
	  
	  // arrays of added and deleted parts
	  private Document[] addedParts = new Document[1000];
	  private Document[] deletedParts = new Document[1000];
	  
	  // arrays of added and deleted parts' IDs
	  private String[] addedIds = new String[1000];
	  private String[] deletedIds = new String[1000];
	  
	  // arrays of added and deleted parts' Paths
	  private String[] addedPaths = new String[1000];
	  private String[] deletedPaths = new String[1000];
	  
	  // quantity of elements in addedParts and deletedParts arrays
	  private int addedLength = 0;
	  private int deletedLength = 0;
	  
	  // string array for IDs of parent element
	  // to know full path of current example (i.e. Siteview ... -> Page ... -> ContentUnit)
	  // u need to sequentially concatenate all of elements in this array
	  private String[] parentId = new String[100];
	  // number of parents
	  private int parentLevel = 0;
	  
	  // level of element in current changedStatus
	  private int changedLevel = 0;

	  // this variable shows if layout:Grid tag is in status NO_CHANGE and we don't need to parse it
	  private boolean layoutSectionOpened = false;
	  	
	  // parentStack - Stack for store parent elements of current handling element
	  private Stack<Element> parentStack = new Stack<Element>();
	  	  
	  private String[] changesId = new String[10000];
	  private String[] changesStr = new String[10000];
	  private int changesIndex = 0;
	  
	  // document builder factory and document builder
	  private DocumentBuilderFactory dbf;	
	  private DocumentBuilder db;
	  
	  // returns array with comments to changed elements. Contains information about
	  // changes - what've been modified, which attributes added, etc.
	  public String[] getChangesStr() {
	    return changesStr;
	  }
	  
	  // returns array with IDs of changed elements
	  public String[] getChangesId() {
		    return changesId;
	  }
	  
	  // returns quantity of changed elements
	  public int getChangesQuantity()
	  {
	    return changesIndex;
	  }
	  
	  // HandleAddedDeletedElements compares arrays of added and deleted elements and finds pairs
	  // which are the same elements with some changes
	  private void HandleAddedDeletedElements() {
		  // find blocks with equal IDs
		  for (int i = 0; i < addedLength; i++) {
			  for (int j = 0; j < deletedLength; j++) {
				  if ((addedIds[i].equals(deletedIds[j]))&&(!addedIds[i].equals(""))) {
					  // handle changes
					  HandleChanges(deletedParts[j], addedParts[i], addedPaths[i]);
					  // remove handled elements
					  addedIds[i] = "";
					  deletedIds[j] = "";
					  addedPaths[i] = "";
					  deletedPaths[j] = "";
					  addedParts[i] = db.newDocument();
					  addedParts[i] = null;
					  deletedParts[j] = db.newDocument();
					  deletedParts[j] = null;
				  }
			  }
		  }
		  // print information about the others
		  for (int i = 0; i < addedLength; i++)
			  if (addedParts[i] != null)
				  printAddedInfo(addedParts[i], i);
		  for (int j = 0; j < deletedLength; j++)
			  if (deletedParts[j] != null)
				  printDeletedInfo(deletedParts[j], j);
	  }

	  // outputs information about added element addedDoc according to its index in addedParts array
	  private void printAddedInfo(Document addedDoc, int index) {
		  Element root = (Element) addedDoc.getFirstChild();
		  if (root.hasAttribute("name")) {
			  //add information about changes to the changes list
			  changesId[changesIndex] = root.getAttribute("id");
			  changesStr[changesIndex] = root.getNodeName() + " '" + root.getAttribute("name") + "' (" +
			  	addedPaths[index] + root.getNodeName() + " '" + root.getAttribute("name") + "') added.";
			  changesIndex++;
		  } else {
			  NodeList children = root.getChildNodes();
			  for (int i=0; i<children.getLength(); i++)
				  try {
					  TransformerFactory tf = TransformerFactory.newInstance();
					  Transformer xf = tf.newTransformer();
					  DOMResult dr = new DOMResult();
					  xf.transform(new DOMSource(children.item(i)), dr);
					  Document newDoc = (Document) dr.getNode();
					  printAddedInfo(newDoc, index);
				  } catch (Exception e) {
					  //e.printStackTrace();
				  }
		  }
	  }
	  
	  // outputs information about deleted element deletedDoc according to its index in deletedParts array
	  private void printDeletedInfo(Document deletedDoc, int index) {
		  Element root = (Element) deletedDoc.getFirstChild();
		  if (root.hasAttribute("name")) {
			  //add information about changes to the changes list
			  changesId[changesIndex] = root.getAttribute("id");
			  changesStr[changesIndex] = root.getNodeName() + " '" + root.getAttribute("name") + "' (" +
			  	deletedPaths[index] + root.getNodeName() + " '" + root.getAttribute("name") + "') deleted.";
			  changesIndex++;
		  } else {
			  NodeList children = root.getChildNodes();
			  for (int i=0; i<children.getLength(); i++)
				  try {
					  TransformerFactory tf = TransformerFactory.newInstance();
					  Transformer xf = tf.newTransformer();
					  DOMResult dr = new DOMResult();
					  xf.transform(new DOMSource(children.item(i)), dr);
					  Document newDoc = (Document) dr.getNode();
					  printDeletedInfo(newDoc, index);
				  } catch (Exception e) {
					  //e.printStackTrace();
				  }
		  }
	  }

	// HandleChanges compares two changed documents - oldDoc and newDoc and prints information
	  // about difference  between them. Third parameter - startOfParentPath - path to comparing objects, 
	  // i.e. sequence of nested elements which are their parents
	  private void HandleChanges(Document oldDoc, Document newDoc, String startOfParentPath) {
		  // this code compares attributes of top-level elements,
		  // removes equal attributes from both documents
		  // and returns changed attributes except namespaces and coordinates 
		  
		  // topOldElement is the top-level element in document oldDoc 
		  Element topOldElem = (Element) oldDoc.getFirstChild();
		  // topNewElement is the top-level element in document newDoc 
		  Element topNewElem = (Element) newDoc.getFirstChild();
		  
		  // get name of current element
		  String currentName = (topNewElem.hasAttribute("name")) ? topNewElem.getAttribute("name") : "";
		  // get all attributes of topOldElem to compare them to topNewElem's attributes
		  NamedNodeMap oldAttrs = topOldElem.getAttributes();
		  int attrLength = oldAttrs.getLength();
		  for (int i=attrLength-1; i>=0; i--){
			  // get name and value of current attribute
			  String oldAttrName = oldAttrs.item(i).getNodeName();
			  String oldAttrValue = oldAttrs.item(i).getNodeValue();
			  // attempt to find corresponding attribute in topNewElement
			  String newAttrValue = topNewElem.getAttribute(oldAttrName);
			  if ((newAttrValue != null)&&(oldAttrValue.equals(newAttrValue))&&
					  (!oldAttrName.equals("id"))&&(!oldAttrName.startsWith("xmlns"))) {
				  // if we found pair of equal attributes and it's not ID or namespace, remove it
				  topOldElem.removeAttribute(oldAttrName);
				  topNewElem.removeAttribute(oldAttrName);
			  }
		  }
		  
		  // changedAttributes stores list of changed attributes in current element
		  String[] changedAttributes = new String[100];
		  // changedAttrIndex stores quantity of changed attributes in current element
		  int changedAttrIndex = 0;

		  // get all attributes of topOldElem again because some of them could be deleted
		  oldAttrs = topOldElem.getAttributes();
		  // process all attributes of topOldElem
		  attrLength = oldAttrs.getLength();
		  for (int i=attrLength-1; i>=0; i--) {
			  // process all attributes except namespaces, ids and coordinates - x and y
			  String attrName = oldAttrs.item(i).getNodeName();
			  if ((!attrName.startsWith("xmlns:"))&&(!attrName.equals("id"))&&
					  	(!attrName.equals("x"))&&(!attrName.equals("y")))
				  changedAttributes[changedAttrIndex++] = "'" + attrName + "'";
			  if ((!attrName.equals("id"))&&(!attrName.startsWith("xmlns:"))) {
				  topOldElem.removeAttribute(attrName);
				  if (topNewElem.hasAttribute(attrName))
					  topNewElem.removeAttribute(attrName);
			  }
		  }

		  // get all attributes of topNewElem
		  NamedNodeMap newAttrs = topNewElem.getAttributes();
		  // process all attributes of topNewElem
		  attrLength = newAttrs.getLength();
		  for (int i=attrLength-1; i>=0; i--) {
			  // process all attributes except namespaces, IDs and coordinates - x and y
			  String attrName = newAttrs.item(i).getNodeName();
			  if ((!attrName.startsWith("xmlns:"))&&(!attrName.equals("id"))&&
					  	(!attrName.equals("x"))&&(!attrName.equals("y"))) {
				  changedAttributes[changedAttrIndex++] = "'" + attrName + "'";
			  }
			  if ((!attrName.equals("id"))&&(!attrName.startsWith("xmlns:")))
				  topNewElem.removeAttribute(attrName);
		  }
		  
		  // save changes of attributes
		  if (changedAttrIndex == 1) {
			  // currentId stores Id of current processing element if it exists
			  String currentId = topNewElem.getAttribute("id");
			  if (currentId == null)
				  currentId = "";
			  changesId[changesIndex] = currentId;
			  changesStr[changesIndex] = "Attribute " + changedAttributes[0] + " of " + topNewElem.getNodeName() +
			  	" '" + currentName + "' (" + startOfParentPath + topNewElem.getNodeName() + " '" + currentName +  "') changed.";
			  changesIndex++;
		  } else if (changedAttrIndex > 1) {
			  // currentId stores Id of current processing element if it exists
			  String currentId = topNewElem.getAttribute("id");
			  if (currentId == null)
				  currentId = "";
			  changesId[changesIndex] = currentId;
			  changesStr[changesIndex] = "Attributes " + changedAttributes[0];
			  for (int i=1; i<changedAttrIndex; i++)
				  changesStr[changesIndex] = changesStr[changesIndex] + ", " + changedAttributes[i];
			  changesStr[changesIndex] = changesStr[changesIndex] + " of " + topNewElem.getNodeName() + " '" + currentName +
			  	"' (" + startOfParentPath + topNewElem.getNodeName() + " '" + currentName +  "') changed.";
			  changesIndex++;
		  }
		  
		  // recursively call Diff Parser to handle all changes
		  try {
			  // Assign log file to null,
			  // all error messages will be printing to the standard error stream
			  ExamXML.setLogFile(null);
			  // compare XMLs
			  String result = ExamXML.compareXMLString(docToString(oldDoc), docToString(newDoc));
			  
			  // replace comments in result with special tags for convenient parsing
			  result = result.replace("<!-- Added Element(s) -->", "<added/>");
			  result = result.replace("<!-- Deleted Element(s) -->", "<deleted/>");
			  result = result.replace("<!-- Changed Element:old -->", "<changed_old/>");
			  result = result.replace("<!-- Changed Element:new -->", "<changed_new/>");
			  if (result.equals(""))
				  result = "<empty/>";
			  
			  // convert string with XML Diff to InputStream
			  InputStream is = new ByteArrayInputStream(result.getBytes());
			  // create new SAXParser
			  SAXParserFactory diffFactory = SAXParserFactory.newInstance();
			  SAXParser diffParser = diffFactory.newSAXParser();
			  
			  // override default handler
			  DiffHandler diffHandler = new DiffHandler();
			  diffParser.parse(is, diffHandler);
			  String[] recursiveChangesStr = diffHandler.getChangesStr();
		      String[] recursiveChangesId = diffHandler.getChangesId();
		      int recursiveChangesQuantity = diffHandler.getChangesQuantity();
		      for (int i=0; i<recursiveChangesQuantity; i++){
		    	  changesId[changesIndex] = recursiveChangesId[i];
		    	  if (recursiveChangesStr[i].indexOf("  ()") == -1)
		    		  changesStr[changesIndex] = recursiveChangesStr[i].replace("(", "(" + 
		    			  startOfParentPath + topNewElem.getNodeName() + " '" + currentName + "' -> ");
		    	  else
		    		  changesStr[changesIndex] = recursiveChangesStr[i].replace("  ()", " " + 
		    				  topNewElem.getNodeName() + " '" + currentName + "' (" +
		    				  startOfParentPath + topNewElem.getNodeName() + " '" + currentName + "')");
		    	  changesIndex++;
		      }
			  
		  } catch (Exception err) {
			  //err.printStackTrace();
		  }
	  }
	  
	  // returns full path of current element, i.e. Siteview ... -> Page ... -> ContentUnit
	  // for elements parsed with JExamXML
	  private String getParentPath() {
		  String parentPath = "";
		  for (int i=0; i<parentLevel; i++)
			  if (!parentId[i].endsWith("±"))
				  parentPath = parentPath + parentId[i].substring(parentId[i].indexOf("±") + 1) + " -> ";
		  if (parentPath.length() > 4)
			  parentPath = parentPath.substring(0, parentPath.length()-4);
		  return parentPath;
	  }
	  	  
	  //returns parents name of current element
	  private String getLastParentName() {
		  String parent = "";
		  for (int i=parentLevel-1; i>=0; i--)
			  if (!parentId[i].endsWith("±")) {
				  parent = parentId[i].substring(parentId[i].indexOf("±") + 1);
				  break;
			  }
		  return parent;
	  }
	  
	  //returns parents ID of current element
	  private String getLastParentId() {
		  String parent = "";
		  for (int i=parentLevel-1; i>=0; i--){
			  parent = parentId[i].substring(0, parentId[i].indexOf("±"));
			  if (!parent.equals(""))
				  break;
		  }
		  return parent;
	  }
	  	  
	  // converts Document to String
	  private String docToString(Document dom) {
			try {
				// set up a transformer
	            TransformerFactory transfac = TransformerFactory.newInstance();
	            Transformer trans = transfac.newTransformer();
	            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	            trans.setOutputProperty(OutputKeys.INDENT, "yes");

	            // create string from xml tree
	            StringWriter sw = new StringWriter();
	            StreamResult result = new StreamResult(sw);            
	            DOMSource source = new DOMSource(dom);
	            trans.transform(source, result);
	            String xmlString = sw.toString();
	            
	            return xmlString;
			}
			catch (Exception err) {
				//err.printStackTrace();
				return "";
			}
		}
	  
	  // Event while parsing - Document started
	  @Override
	  public void startDocument() throws SAXException
	  {
		  //get an instance of factory
		  dbf = DocumentBuilderFactory.newInstance();
		  try {
			  //get an instance of builder
			  db = dbf.newDocumentBuilder();

			  //create an instances of DOMs
			  partOld = db.newDocument();
			  partNew = db.newDocument();
			  addedParts[0] = db.newDocument();
			  deletedParts[0] = db.newDocument();
		  }catch(Exception err) {
			  //err.printStackTrace();
		  }
	  }

	  // Event while parsing - new Element started
	  public void startElement(String uri, String localName, String qName, Attributes attr) throws SAXException
	  {
		  if (qName.equals("added")) {
			  changedStatus = ADDED;
			  changedLevel = 0;
			  parentStack.empty();
		  }
		  else if (qName.equals("deleted")) {
			  changedStatus = DELETED;
			  changedLevel = 0;
			  parentStack.empty();
		  }
		  else if (qName.equals("changed_old")) {
			  changedStatus = CHANGED_OLD;
			  changedLevel = 0;
			  parentStack.empty();
		  }
		  else if (qName.equals("changed_new")) {
			  changedStatus = CHANGED_NEW;
			  changedLevel = 0;
			  parentStack.empty();
		  }
		  else if (qName.equals("empty")) {
			  changedStatus = NO_CHANGE;
		  }
		  else {
			  // add id, type and name of current element to parentId array
			  if (attr.getIndex("name") != -1)
				  parentId[parentLevel++] = attr.getValue("id") + "±" + qName + " '" + attr.getValue("name") + "'";
			  else
				  parentId[parentLevel++] = (attr.getIndex("id") != -1) ? attr.getValue("id") + "±" : "±";
			  
			  // if there is "layout" element in result diff xml then it was changed
			  // we won't parse its changes, we'll just say that it has been changed
			  if (qName.startsWith("layout")) {
				  // if it is "NO_CHANGE" section then print information about changed layout
				  if ((changedStatus == NO_CHANGE) || (layoutSectionOpened)) {
					  if (qName.equals("layout:Grid")) {
						  changesId[changesIndex] = getLastParentId();
						  changesStr[changesIndex] = "Layout of " + getLastParentName() + " (" + getParentPath() +  ") changed.";
						  changesIndex++;
					  }
					  layoutSectionOpened = true;
					  changedLevel++;
					  return;
				  }
		  	  } else {
		  		  layoutSectionOpened = false;
		  	  }
			  switch (changedStatus) {
			  case NO_CHANGE:
				  changedLevel++;
				  break;
			  case ADDED:
				  Element newElem = addedParts[addedLength].createElement(qName);
				  for(int i = 0 ; i<attr.getLength() ; i++) {
					  // copy all attributes to dom element
					  // except those who starts with gr:
					  String attrName = attr.getQName(i);
					  if (attrName.startsWith("gr:"))
						  attrName = attrName.substring(3);
					  newElem.setAttribute(attrName, attr.getValue(i));
				  }
				  try {
					  // set which attribute has ID type
					  newElem.setIdAttribute("id", true);
				  } catch (DOMException err) {
					  // do nothing
				  }
				  // choose where append current element
				  if (parentStack.size() == 0) {
					  addedParts[addedLength].appendChild(newElem);
				  }
				  else {
					  parentStack.peek().appendChild(newElem);
				  }
				  parentStack.push(newElem);
				  
				  //printXMLToFile(addedParts[addedLength], "c:\\added.xml");
				  changedLevel++;
				  break;
			  case DELETED:
				  newElem = deletedParts[deletedLength].createElement(qName);
				  for(int i = 0 ; i<attr.getLength() ; i++) {
					  // copy all attributes to dom element
					  // except those who starts with gr:
					  String attrName = attr.getQName(i);
					  if (attrName.startsWith("gr:"))
						  attrName = attrName.substring(3);
					  newElem.setAttribute(attrName, attr.getValue(i));
				  }
				  try {
					  // set which attribute has ID type
					  newElem.setIdAttribute("id", true);
				  } catch (DOMException err) {
					  // do nothing
				  }
				  // choose where append current element
				  if (parentStack.size() == 0) {
					  deletedParts[deletedLength].appendChild(newElem);
				  }
				  else {
					  parentStack.peek().appendChild(newElem);
				  }
				  parentStack.push(newElem);
				  changedLevel++;
				  break;
			  case CHANGED_OLD:
				  newElem = partOld.createElement(qName);
				  for(int i = 0 ; i<attr.getLength() ; i++) {
					  // copy all attributes to dom element
					  // except those who starts with gr:
					  String attrName = attr.getQName(i);
					  if (attrName.startsWith("gr:"))
						  attrName = attrName.substring(3);
					  newElem.setAttribute(attrName, attr.getValue(i));
				  }
				  try {
					  // set which attribute has ID type
					  newElem.setIdAttribute("id", true);
				  } catch (DOMException err) {
					  // do nothing
				  }
				  // choose where append current element
				  if (parentStack.size() == 0) {
					  partOld.appendChild(newElem);
				  }
				  else {
					  parentStack.peek().appendChild(newElem);
				  }
				  parentStack.push(newElem);
				  changedLevel++;
				  break;
			  case CHANGED_NEW:
				  Element curElem = partNew.createElement(qName);
				  for(int i = 0 ; i<attr.getLength() ; i++) {
					  // copy all attributes to dom element
					  // except those who starts with gr:
					  String attrName = attr.getQName(i);
					  if (attrName.startsWith("gr:"))
						  attrName = attrName.substring(3);
					  curElem.setAttribute(attrName, attr.getValue(i));
				  }
				  try {
					  // set which attribute has ID type
					  curElem.setIdAttribute("id", true);
				  } catch (DOMException err) {
					  // do nothing
				  }

				  // choose where append current element
				  if (parentStack.size() == 0)
					  partNew.appendChild(curElem);
				  else
					  parentStack.peek().appendChild(curElem);
				  parentStack.push(curElem);
				  changedLevel++;
				  break;
			  }
		  }
	  }

	  // Event while parsing - Element ended
	  @Override
	  public void endElement(String uri, String localName, String qName) throws SAXException
	  {
		  //System.out.println(qName);
		  if (qName.equals("added")||qName.equals("deleted")||
				  qName.equals("changed_old")||qName.equals("changed_new")||qName.equals("empty")) {
			  // if this element is comment saying that next part has been changed, added
			  // or modified then set changedLevel to 0
			  // it will increase by 1 while depth-first search with every depth-child
			  // and decrease by 1 with every returning to parent
			  // when it becomes 0 it means changed part is over
			  changedLevel = 0;
			  parentStack.empty();
		  }
		  else {
			  // decrease parent level
			  parentLevel--;
			  // decrease changeLevel
			  if (changedLevel > -1)
				  changedLevel--;
			  // element ended, pop it from the top of the stack
			  if (((changedStatus == CHANGED_OLD)||(changedStatus == CHANGED_NEW)||
					  (changedStatus == ADDED)||(changedStatus == DELETED))&&
					  ((!qName.startsWith("layout"))||(!layoutSectionOpened))&&(!parentStack.isEmpty())) {
				  parentStack.pop();
			  }
			  // if change level equals to 0 it means that modified part is over
			  if (changedLevel == 0) {
				  if (changedStatus == CHANGED_OLD) {
					  // empty parentStack, because changed part is over
					  parentStack.empty();
				  } else if (changedStatus == CHANGED_NEW) {
					  // empty parentStack, because changed part is over
					  parentStack.empty();
					  //printXMLToFile(partOld, "c:\\part.old");
					  //printXMLToFile(partNew, "c:\\part.new");
					  // handle partOld and partNew Documents
					  if (!layoutSectionOpened) {
						  String startOfParentPath = (getParentPath().equals("")) ? "" : getParentPath() + " -> ";
						  HandleChanges(partOld, partNew, startOfParentPath);
					  }

					  //printXMLToFile(partOld, "c:\\part1.old");
					  //printXMLToFile(partNew, "c:\\part1.new");
					  partNew = db.newDocument();
					  partOld = db.newDocument();
					  changedStatus = NO_CHANGE;
				  } else if ((changedStatus == ADDED)&&(!layoutSectionOpened)) {
					  // write Id and path of added element
					  Element curElem = (Element) addedParts[addedLength].getFirstChild();
					  addedIds[addedLength] = (curElem.hasAttribute("id")) ? curElem.getAttribute("id") : "";
					  addedPaths[addedLength] = (getParentPath().equals("")) ? "" : getParentPath() + " -> ";
					  //printXMLToFile(addedParts[addedLength], "c:\\added"+addedLength+".xml");
					  // increase addedLength
					  addedLength++;
					  // create new empty document
					  addedParts[addedLength] = db.newDocument();
					  // empty parentStack, because added part is over
					  parentStack.empty();
				  } else if ((changedStatus == DELETED)&&(!layoutSectionOpened)) {
					  // write Id and path of deleted element
					  Element curElem = (Element) deletedParts[deletedLength].getFirstChild();
					  deletedIds[deletedLength] = (curElem.hasAttribute("id")) ? curElem.getAttribute("id") : "";
					  deletedPaths[deletedLength] = (getParentPath().equals("")) ? "" : getParentPath() + " -> ";
					  //printXMLToFile(deletedParts[deletedLength], "c:\\deleted"+deletedLength+".xml");
					  // increase deletedLength
					  deletedLength++;
					  // create new empty document
					  deletedParts[deletedLength] = db.newDocument();
					  // empty parentStack, because added part is over
					  parentStack.empty();
				  }
			  } else if (changedLevel == -1) {
				  changedStatus = NO_CHANGE;				  
			  }
		  }
	  }

	  // Event while parsing - Document ended
	  @Override
	  public void endDocument() throws SAXException
	  {
		  HandleAddedDeletedElements();
		  //System.out.println("added elements: " + addedLength);
		  //System.out.println("deleted elements: " + deletedLength);
		  // clear stack
//		  elemParent.clear();
	//	  System.out.println(changesStr+"\n\n");
	  }
	  
	  public void warning(SAXParseException spe) {
		  System.out.println("Warning at line "+spe.getLineNumber());
		  System.out.println(spe.getMessage());
	  }

	  public void fatalError(SAXParseException spe) throws SAXException {
		  System.out.println("Fatal error at line "+spe.getLineNumber());
		  System.out.println(spe.getMessage());
		  throw spe;
	  }
}
