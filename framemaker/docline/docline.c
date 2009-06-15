/*
* Program Name:
*	    docline
*
* General Description:
*	    Works with docline technology using FrameMaker documents. This client
*      provides menu items to create new docline projects, add specific 
*      items like InfElements, InfProducts and so on to it, open existing
*      DRL documentations and work with them like with FrameMaker documents. 
*		Also it'd be possible to save projects to drl again and export projects
*		to the final documentation formats like PDF and HTML help.
*
* Invocation:
*	    Compile this client.
*
*	Install Info (UNIX):
*		If you compile this as an RPC client (the default),
*		add these lines to the apiclients file:
*
*		<APIclient
*			<Name docline>
*			<Description Calls docline client to update text insets.>
*			<Directory fdk_install_dir/samples/docline>
*			<CommandLine $FAPIDIR/fa.docline>
*		>
*
*		Replace fdk_install_dir with the path of the directory in
*		which you installed your copy of the FDK files.
*
*		If you compile this as a dynamic client, change the line
*			<CommandLine $FAPIDIR/fa.docline>
*		to:
*			<SharedLibrary fa.docline.dl>
*
*		Restart maker.
*
*	Install Info (Windows):
*		Add the following entry (all on one line) to the [APIClients]
*		section of your product.ini (for example, maker.ini) file:
*
*		docline=Standard,Call docline client to update insets,
*			c:\fdk_install_dir\samples\docline\debug\docline.dll, all 
*
*		Replace c:\fdk_install_dir with the path of the directory
*		in which you installed your copy of the FDK files.
*		Restart maker.
*
***********************************************************************/

#include "fapi.h"
#include "fchannel.h"
#include "fdetypes.h"
#include "futils.h"
#include "fstrings.h"
#include "fprogs.h"
#include "fmemory.h"

#include "cutils.h"

#include "string.h"
#include "ctype.h"

#include "publishutil.h"

#define NEW_DLG 239
#define SECTION_DLG 240
#define OKDLG 5
#define CANCELDLG 6
#define BOOK 11
#define NEWDC 12
#define NEWDI 13
#define NEWDT 14
#define NEWIE 15
#define NEWIP 16
#define BBOOK 21
#define BCORE 27
#define BNEWDC 22
#define BNEWDI 23
#define BNEWDT 24
#define BNEWIE 25
#define BNEWIP 26
#define BPROD 36
#define PNEWDC 32
#define PNEWDI 33
#define PNEWDT 34
#define PNEWIP 35
#define BLINE 47
#define BNEWPR 41
#define SAVEHT 101
#define SAVEPD 102
#define OPEN 103
#define IMPORT 104
#define BSAVEHT 111
#define BSAVEPD 112
#define BOPEN 113
#define BIMPORT 114
#define FM 200
#define DRL 201
#define FMBOOK 202
#define GENBOOK 203
#define MAXSTRING 255

#define DICTION 501
#define DIRECT 502
#define DIRTEMP 503
#define INFELEM 504
#define INFPROD 505
#define FINALINF 506
#define PRODUCT 507

#define EXPORT 901
#define CLOSE 902
#define CHECK 904
#define BEXPORT 911
#define BCLOSE 912
#define in ((MetricT) 65536*72)
#define defaultPath  (F_StrCopyString("C:\\"))
#define defaultBookName (F_StrCopyString("mainDRLFMBook.book"))

// constants for publishing action
#define HTML_FORMAT "html"
#define PDF_FORMAT "pdf"
#define JAR_FILENAME "publishutil.jar"
#define ERROR_LOG_FILENAME "error.log"
#define BUFFERSIZE (IntT)256

F_ObjHandleT menubarId, menuId; // !MakerMainMenu and Docline menus
F_ObjHandleT newMenuId; // "New" submenu in the Docline menu
F_ObjHandleT openMenuId; // "Open" submenu in the Docline menu
F_ObjHandleT saveMenuId; // "Save As..." submenu in the Docline menu
F_ObjHandleT newProjectId;// Commands from the "New" submenu
F_ObjHandleT saveDoclineAsHtmlId, saveDoclineAsPdfId; // Commands from the "Save As..." submenu
F_ObjHandleT openFmID, importDrlID; // open exising fm docline project, import project from existing drl documentation
F_ObjHandleT closeProjectId; // menu item for closing active project and all files in it
F_ObjHandleT exportProjectId; // menu item for exporting active project back to DRL

/* All the same for !BookMainMenu */
F_ObjHandleT bmenubarId, bmenuId; // !BookMainMenu and Docline menus
F_ObjHandleT bnewMenuId; // "New" submenu in the Docline menu
F_ObjHandleT docCoreId; // DocumentationCore submenu in the "New menu"
F_ObjHandleT prodDocId; // ProductDocumentation submenu in the "New menu"
F_ObjHandleT prodLineId; // ProductLine submenu in the "New menu"
F_ObjHandleT bopenMenuId; // "Open" submenu in the Docline menu
F_ObjHandleT bsaveMenuId; // "Publish" submenu in the Docline menu
F_ObjHandleT bnewProjectId, separatorId; // Some commands from the "New" submenu
F_ObjHandleT newDocCoreSectionId, coreSeparatorId, newDictId, newDirectId, newDirTempId, newInfElemId, newInfProdId; // Commands in "New DocumentationCore"
F_ObjHandleT newProdDocSectionId, prodSeparatorId, prodDictId, prodDirectId, prodDirTempId, finalInfProdId; // Commands in "New ProductDocumentation"
F_ObjHandleT newProdLineSectionId, lineSeparatorId, productId; // Commands in "New ProductLine"
F_ObjHandleT bsaveDoclineAsHtmlId, bsaveDoclineAsPdfId; // Commands from the "Save As..." submenu
F_ObjHandleT bopenFmID, bimportDrlID; // open exising fm docline project, import project from existing drl documentation
F_ObjHandleT bcloseProjectId; // menu item for closing active project and all files in it
F_ObjHandleT bexportProjectId; // menu item for exporting active project back to DRL
F_ObjHandleT checkCorrectId; // menu item for exporting active project back to DRL

VoidT createNewDocLineBook();
VoidT newDocCoreChild(IntT type);
VoidT newProdDocChild(IntT type);
VoidT newProdLineChild(IntT type);
BoolT newSecondLevelSection(BoolT isFirst, StringT type);
VoidT openBook(); //Opens existing docline project with checking its structure
VoidT importDocLineDoc(); //Imports .drl files in directory
VoidT publishDocLineDoc(StringT format); //Publishes docline project
VoidT exportDocLineDoc(); //Exports docline project
VoidT closeProject();
VoidT editHeader();
VoidT setAttributes(StringT idStr, StringT nameStr);
VoidT cleanDirectory(FilePathT* dirPath); //Deletes temporary files in directory
BoolT validateFilename(UCharT *str,IntT type); //Validates type of file
VoidT generateBooks(F_ObjHandleT mainBookID); //Generates books for second-level elements
VoidT openFilesInDirectory(StringT path); //Common part of Open and Import
VoidT pathFilename(UCharT *str); //Converts full path to path of directory
StringT fileFileName(UCharT *str); //Converts full path to file name
BoolT isDocLine(F_ObjHandleT docID); //Checks if document is docline document
StringT getHighestString(F_ObjHandleT docID); //Name of highest level element in document
IntT test;

VoidT F_ApiInitialize(IntT init)
{
	/* Making it unicode enabled. */
	F_FdeInit();
	F_ApiEnableUnicode(True);
	F_FdeInitFontEncs("UTF-8");

	switch (init)  {
  case FA_Init_First:
	  F_FdeInit();

	  /* Get the ID of the FrameMaker main menu bar. */
	  menubarId = F_ApiGetNamedObject(FV_SessionId, FO_Menu, "!MakerMainMenu");
	  /* Define and add the DocLine menu to the main menu. */
	  menuId = F_ApiDefineAndAddMenu(menubarId, "DocLineMenu", "DocLine");

	  /* Define and add the New-> menu to the DocLine menu. */
	  newMenuId = F_ApiDefineAndAddMenu(menuId, "NewDocLineMenu", "New");
	  /* Define some commands and add them to the New-> menu. */
	  newProjectId = F_ApiDefineAndAddCommand(BOOK, newMenuId, "NewDocLineProject", "Docline project", "\\!NP");	

	  /* Define and add the Open-> menu to the DocLine menu. */
	  openMenuId = F_ApiDefineAndAddMenu(menuId, "OpenDocLineMenu", "Open");
	  /* Define some commands and add them to the Open-> menu. */
	  openFmID = F_ApiDefineAndAddCommand(OPEN, openMenuId, "OpenDoclineProject", "Docline project", "\\!IW");

	  /* Define command for importing DocLine project from existing DRL documentation. */
	  importDrlID = F_ApiDefineAndAddCommand(IMPORT, menuId, "Import", "Import","\\!OO");

	  /* Define command for exporting active project back to DRL */
	  exportProjectId = F_ApiDefineAndAddCommand(EXPORT, menuId, "ExportProject", "Export","\\!EP");

	  /* Define command for closing active project and all files in it */
	  closeProjectId = F_ApiDefineAndAddCommand(CLOSE, menuId, "CloseProject", "Close Project","\\!CP");

	  /* Get the ID of the FrameMaker book menu bar. */
	  bmenubarId = F_ApiGetNamedObject(FV_SessionId, FO_Menu, "!BookMainMenu");
	  /* Define and add the DocLine menu to the main menu. */
	  bmenuId = F_ApiDefineAndAddMenu(bmenubarId, "BDocLineMenu", "DocLine");

	  /* Define and add the New-> menu to the Book DocLine menu. */
	  bnewMenuId = F_ApiDefineAndAddMenu(bmenuId, "BNewDocLineMenu", "New");
	  /* Define some commands and add them to the New-> menu. */
	  bnewProjectId = F_ApiDefineAndAddCommand(BBOOK, bnewMenuId, "BNewDocLineProject", "Docline project", "\\!BNP");
	  /* Add seperator after the New docline project command*/
	  separatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "BNewSeparator");
	  F_ApiAddCommandToMenu(bnewMenuId, separatorId);	
	  /* Define and add DocumentationCore menu to the "New" menu. */
	  docCoreId = F_ApiDefineAndAddMenu(bnewMenuId, "NewDocCoreMenu", "DocumentationCore");
	  /* Define and add comand for adding new DocumentationCore section to the project */
	  newDocCoreSectionId = F_ApiDefineAndAddCommand(BCORE, docCoreId, "BNewDocCoreSection", "DocumentationCore section", "\\!NDC");
	  /* Add seperator after new DocumentationCore Section command*/
	  coreSeparatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "CoreSeparator");
	  F_ApiAddCommandToMenu(docCoreId, coreSeparatorId);	
	  /* Define "New *SpecificElement*" commands and add them to the DocumentationCore menu. */
	  newDictId = F_ApiDefineAndAddCommand(BNEWDC, docCoreId, "BNewDictionary", "Dictionary", "\\!BND");
	  newDirectId = F_ApiDefineAndAddCommand(BNEWDI, docCoreId, "BNewDirectory", "Directory", "\\!BDI");
	  newDirTempId = F_ApiDefineAndAddCommand(BNEWDT, docCoreId, "BNewDirTemplate", "DirTemplate", "\\!BDT");
	  newInfElemId = F_ApiDefineAndAddCommand(BNEWIE, docCoreId, "BNewInfElement", "InfElement", "\\!BNE");
	  newInfProdId = F_ApiDefineAndAddCommand(BNEWIP, docCoreId, "BNewInfProduct", "InfProduct", "\\!BNP");

	  /* Define and add ProductDocumentation menu to the "New" menu. */
	  prodDocId = F_ApiDefineAndAddMenu(bnewMenuId, "NewProdDocMenu", "ProductDocumentation");
	  /* Define and add comand for adding new ProductDocumentation section to the project */
	  newProdDocSectionId = F_ApiDefineAndAddCommand(BPROD, prodDocId, "BNewProdDocSection", "ProductDocumentation section", "\\!NPD");
	  /* Add seperator after new ProductDocumentation Section command*/
	  prodSeparatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "ProdSeparator");
	  F_ApiAddCommandToMenu(prodDocId, prodSeparatorId);	
	  /* Define "New *SpecificElement*" commands and add them to the ProductDocumentation menu. */
	  prodDictId = F_ApiDefineAndAddCommand(PNEWDC, prodDocId, "PNewDictionary", "Dictionary", "\\!PND");
	  prodDirectId = F_ApiDefineAndAddCommand(PNEWDI, prodDocId, "PNewDirectory", "Directory", "\\!PDI");
	  prodDirTempId = F_ApiDefineAndAddCommand(PNEWDT, prodDocId, "PNewDirTemplate", "DirTemplate", "\\!PDT");
	  finalInfProdId = F_ApiDefineAndAddCommand(PNEWIP, prodDocId, "PNewInfProduct", "FinalInfProduct", "\\!PIP");

	  /* Define and add ProductLine menu to the "New" menu. */
	  prodLineId = F_ApiDefineAndAddMenu(bnewMenuId, "NewProdLineMenu", "ProductLine");
	  /* Define and add comand for adding new ProductLine section to the project */
	  newProdLineSectionId = F_ApiDefineAndAddCommand(BLINE, prodLineId, "BNewProdLineSection", "ProductLine section", "\\!NPL");
	  /* Add seperator after new ProductLine Section command*/
	  lineSeparatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "LineSeparator");
	  F_ApiAddCommandToMenu(prodLineId, lineSeparatorId);	
	  /* Define "New *SpecificElement*" commands and add them to the ProductLine menu. */
	  productId = F_ApiDefineAndAddCommand(BNEWPR, prodLineId, "BNewProduct", "Product", "\\!BNP");

	  /* Define and add the Open-> menu to the DocLine menu. */
	  bopenMenuId = F_ApiDefineAndAddMenu(bmenuId, "BOpenDocLineMenu", "Open");
	  /* Define some commands and add them to the Open-> menu. */
	  bopenFmID = F_ApiDefineAndAddCommand(BOPEN, bopenMenuId, "BOpenDoclineProject", "Docline project", "\\!BIW");

	  /* Define command for importing DocLine project from existing DRL documentation. */
	  bimportDrlID = F_ApiDefineAndAddCommand(BIMPORT, bmenuId, "BImport", "Import","\\!BOO");

	  /* Define and add the Save As...-> menu to the DocLine menu. */
	  bsaveMenuId = F_ApiDefineAndAddMenu(bmenuId, "BSaveDocLineMenu", "Publish");
	  /* Define some commands and add them to the Save As...-> menu. */
	  bsaveDoclineAsHtmlId = F_ApiDefineAndAddCommand(BSAVEHT, bsaveMenuId, "BSaveDoclineAsHtml", "HTML", "\\!BHT");
	  bsaveDoclineAsPdfId = F_ApiDefineAndAddCommand(BSAVEPD, bsaveMenuId, "BSaveDoclineAsPdf", "PDF", "\\!BPD");

	  /* Define command for exporting active project back to DRL */
	  bexportProjectId = F_ApiDefineAndAddCommand(BEXPORT, bmenuId, "BExportProject", "Export","\\!BEP");

	  /* Define command for checking correctness of active project */
	  checkCorrectId = F_ApiDefineAndAddCommand(CHECK, bmenuId, "BCheckCorrect", "Check correctness","\\!CC");

	  /* Define command for closing active project and all files in it */
	  closeProjectId = F_ApiDefineAndAddCommand(BCLOSE, bmenuId, "BCloseProject", "Close Project","\\!BCP");
	}
} 
VoidT F_ApiCommand(IntT command)
{
	IntT response;
	//F_ObjHandleT docId;
	/*get the ID of the active document */
	/*  docId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveDoc);
	  if (!docId) {
		F_ApiAlert((StringT) "Please open a document before invoking this command.", FF_ALERT_CONTINUE_WARN);
		return;
	  }*/

	/* Setting commands for handling menu items */
	switch(command) {
  case BOOK:
  case BBOOK:
	  closeProject();
	  createNewDocLineBook();
	  break;
  case BCORE:
	  newSecondLevelSection(False, "DocumentationCore");
	  break;
  case BNEWDC:
	  newDocCoreChild(DICTION);
	  break;
  case BNEWDI:
	  newDocCoreChild(DIRECT);
	  break;
  case BNEWDT:
	  newDocCoreChild(DIRTEMP);
	  break;
  case BNEWIE:
	  newDocCoreChild(INFELEM);
	  break;
  case BNEWIP:
	  newDocCoreChild(INFPROD);
	  break;
  case BPROD:
	  newSecondLevelSection(False, "ProductDocumentation");
	  break;
  case PNEWDC:
	  newProdDocChild(DICTION);
	  break;
  case PNEWDI:
	  newProdDocChild(DIRECT);
	  break;
  case PNEWDT:
	  newProdDocChild(DIRTEMP);
	  break;
  case PNEWIP:
	  newProdDocChild(FINALINF);
	  break;
  case BLINE:
	  newSecondLevelSection(False, "ProductLine");
	  break;
  case BNEWPR:
	  newProdLineChild(PRODUCT);
	  break;
  case OPEN:
  case BOPEN:
	  closeProject();
	  openBook();
	  break;
  case IMPORT:
  case BIMPORT:
	  closeProject();
	  importDocLineDoc();
	  break;
  case BSAVEHT:
	  publishDocLineDoc(HTML_FORMAT);
	  break;
  case BSAVEPD:
	  publishDocLineDoc(PDF_FORMAT);
	  break;
  case EXPORT:
  case BEXPORT:
	  exportDocLineDoc();
	  break;
  case CLOSE:
  case BCLOSE:
	  /* Prompt user if he really wants to save all and close. */
	  response = F_ApiAlert("This will save all files and close them. Do you still wish to continue?",FF_ALERT_YES_DEFAULT);
	  if (!response)
		closeProject();
	  break;
	}
}
VoidT pathFilename(UCharT *str)
{
	while (*str)
	{
		*str++;
	}
	str--;
	while ((*str)&&(*str != '\\'))
	{
		*str = 0;
		*str--;
	}
}
BoolT validateFilename(UCharT *str, IntT type)
{
	while (*str)
	{
		*str++;
	}
	str--;
	while ((*str)&&(*str != '.'))
	{
		*str--;
	}
	if (*str)
	{
		switch (type)
		{
		case FM:
			if (F_StrIEqual((StringT)str,(StringT)".fm"))
			{
				*str--;
				while ((*str)&&(*str != '.'))
				{
					*str--;
				}
				if (*str == 0)
				{
					return True;
				}
				else
				{
					return (!F_StrIEqual((StringT)str,(StringT)".backup.fm"))&&(!F_StrIEqual((StringT)str,(StringT)".recover.fm"));
				}
			}
			else
			{
				return False;
			}
			break;
		case DRL:
			return (F_StrIEqual((StringT)str,(StringT)".drl"));
			break;
		case FMBOOK:
			*str--;
			while ((*str)&&(*str != '\\')&&(*str != '.'))
			{
				*str--;
			}
			if ((!*str)||(*str == '\\'))
			{
				return (F_StrISuffix(str,(StringT)".book"));
			}
			return False;
			break;
		case GENBOOK:
			*str--;
			while ((*str)&&(*str != '\\')&&(*str != '.'))
			{
				*str--;
			}
			if (!*str)
			{
				F_Printf(NULL,"Filename error");
				return False;
			}
			if (*str == '.')
			{
				return False;
			}
			*str++;
			return F_StrIPrefix(str,(StringT)"book") && F_StrISuffix(str,(StringT)".book");
			//this condition is more global - need to exclude files like book_foo.book
		default:
			F_ApiAlert("Invalid validation type",FF_ALERT_CONTINUE_NOTE);
			return False;
		}
	}
	else
	{
		return False;
	}
}
BoolT isDocLine(F_ObjHandleT docID)
{
	StringT elemName;

	elemName = F_ApiGetString(docID,F_ApiGetId(docID,F_ApiGetId(docID,F_ApiGetId(FV_SessionId,docID,FP_MainFlowInDoc),FP_HighestLevelElement),FP_ElementDef),FP_Name);
	return F_StrIEqual(elemName,(StringT)"InfElement") 
		|| F_StrIEqual(elemName,(StringT)"InfProduct")
		|| F_StrIEqual(elemName,(StringT)"DirTemplate")
		|| F_StrIEqual(elemName,(StringT)"Dictionary")
		|| F_StrIEqual(elemName,(StringT)"Directory")
		|| F_StrIEqual(elemName,(StringT)"FinalInfProduct")
		|| F_StrIEqual(elemName,(StringT)"Product");
}
StringT getHighestString(F_ObjHandleT docID)
{
	if (!docID)
	{
		F_Printf(NULL,"getHighestString:\n\tError: Null document\n");
		return F_StrCopyString("");
	}
	return F_ApiGetString(docID,F_ApiGetId(docID,F_ApiGetId(docID,F_ApiGetId(FV_SessionId,docID,FP_MainFlowInDoc),FP_HighestLevelElement),FP_ElementDef),FP_Name);
}
StringT fileFileName(UCharT *str)
{
	while (*str)
	{
		*str++;
	}
	str--;
	while ((*str)&&(*str != '\\'))
	{
		*str--;
	}
	*str++;
	return str;
}
VoidT addStructuredElementToBook(F_ObjHandleT bookID, F_ObjHandleT newBookID, F_ObjHandleT currElemID, F_ObjHandleT elemID)
{
	F_ElementLocT loc;
	F_ObjHandleT childID, parentID;
	F_AttributesT attrs;

	loc.childId = 0;
	loc.offset = 0;
	loc.parentId = currElemID;
	if (F_ApiGetObjectType(bookID,elemID) == FO_BookComponent)
	{
		F_ApiNewBookComponentInHierarchy(newBookID,F_ApiGetString(bookID,elemID,FP_Name),&loc);
	}
	else if (F_ApiGetObjectType(bookID,elemID) == FO_Element)
	{
		parentID = F_ApiGetNamedObject(newBookID,FO_ElementDef,F_ApiGetString(bookID,F_ApiGetId(bookID,elemID,FP_ElementDef),FP_Name));
		parentID = F_ApiNewElementInHierarchy(newBookID,parentID,&loc);
		attrs = F_ApiGetAttributes(bookID,elemID);
		F_ApiSetAttributes(newBookID,parentID,&attrs);
		childID = F_ApiGetId(bookID,elemID,FP_FirstChildElement);
		while (childID)
		{
			if (F_ApiGetId(bookID,childID,FP_BookComponent))
			{
				addStructuredElementToBook(bookID,newBookID,parentID,F_ApiGetId(bookID,childID,FP_BookComponent));
			}
			else
			{
				addStructuredElementToBook(bookID,newBookID,parentID,childID);
			}
			childID = F_ApiGetId(bookID,childID,FP_NextSiblingElement);
		}
	}
	else
	{
		F_ApiAlert("Error in adding to hierarchy",FF_ALERT_CONTINUE_NOTE);
	}
}
VoidT openFilesInDirectory(StringT path)
{
	FilePathT *newpath, *file;
	StringT bookPath, tmpPath, tmpPath2, place, fileName, tmpBookPath;
	//path - path of openned document
	//newpath~path
	//bookPath - book path
	//defaultPath - default directory path
	F_ObjHandleT bookID, compID, elemID, docID;
	DirHandleT handle;
	IntT statusp;
	BoolT bookExists, compExists;
	F_ElementLocT elemLoc;

	pathFilename(path);
	newpath = F_PathNameToFilePath (path, NULL, FDosPath);
	handle = F_FilePathOpenDir(newpath, &statusp);
	if (!handle) 
	{
		F_FilePathFree(newpath);
		F_ApiAlert("Handle Error0", FF_ALERT_CONTINUE_NOTE);
		return;
	}
	bookExists = False;
	while ((file = F_FilePathGetNext(handle, &statusp)) != NULL)
	{
		tmpBookPath = F_FilePathToPathName(file, FDosPath);
		tmpBookPath = fileFileName(tmpBookPath);
		if (F_StrIEqual(tmpBookPath,defaultBookName))
		{
			bookExists = True;
			bookPath = F_StrCopyString(tmpBookPath);
		}
		else if (F_StrSuffix(tmpBookPath,".fm.lck"))
		{
			F_DeleteFile(file);
		}
		//F_FilePathFree(file);
		//F_ApiDeallocateString(&tmpBookPath);
	} 
	F_FilePathCloseDir(handle);
	if (!bookExists)
	{
		bookID = F_ApiSimpleOpen("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_book_template.book",False);
		bookPath = F_StrCopyString(path);
		bookPath = F_Realloc(bookPath,F_StrLen(path)+F_StrLen(defaultBookName)+1,NO_DSE);
		F_StrCat(bookPath,defaultBookName);
		handle = F_FilePathOpenDir(F_PathNameToFilePath(path,NULL,FDosPath),&statusp);
		if (!handle)
		{
			F_ApiAlert("Handle error 1",FF_ALERT_CONTINUE_NOTE);
			return;
		}
		while (file = F_FilePathGetNext(handle,&statusp))
		{
			tmpPath = F_FilePathToPathName(file,FDosPath);
			if ((validateFilename(tmpPath,FMBOOK))&&(!F_StrSuffix(tmpPath,defaultBookName)))
			{
				docID = F_ApiSimpleOpen(tmpPath,False);
				addStructuredElementToBook(docID,bookID,F_ApiGetId(FV_SessionId,bookID,FP_HighestLevelElement),
					F_ApiGetId(FV_SessionId,docID,FP_HighestLevelElement));
				F_ApiClose(docID,FF_CLOSE_MODIFIED);
			}
		}
		F_FilePathCloseDir(handle);
	}
	else
	{
		bookID = F_ApiSimpleOpen(bookPath,False);
	}
	handle = F_FilePathOpenDir(newpath, &statusp);
	if (!handle)
	{
		F_Printf(NULL,"OpenBooks:\n\tDirectory path error: %s\n",path);
		return;
	}
	while((file = F_FilePathGetNext(handle, &statusp)) != NULL)
	{
		tmpPath = F_FilePathToPathName(file, FDosPath);
		if (validateFilename(tmpPath,FM)) 
		{
			compID = F_ApiGetId(FV_SessionId,bookID,FP_FirstComponentInBook);
			compExists = False;
			while ((compID != 0)&&(!compExists))
			{
				tmpPath2 = F_ApiGetString(bookID,compID,FP_Name);
				compExists =  F_StrIEqual(fileFileName(tmpPath),fileFileName(tmpPath2));
				compID = F_ApiGetId(bookID,compID,FP_NextComponentInBook);
			}
			if (!compExists)
			{
				docID = F_ApiSimpleOpen(tmpPath,False);
				fileName = getHighestString(docID);
				F_ApiClose(docID,FF_CLOSE_MODIFIED);
				if ((F_StrIEqual(fileName,(StringT)"InfElement")) ||
					(F_StrPrefix(fileName,(StringT)"InfProduct")))
				{
					place = (StringT)"DocumentationCore";
				}
				else if (F_StrIEqual(fileName,(StringT)"FinalInfProduct"))
				{
					place = (StringT)"ProductDocumentation";
				}
				else if (F_StrIEqual(fileName,(StringT)"Product"))
				{
					place = (StringT)"ProductLine";
				}
				else
				{
					place = (StringT)"";
				}
				F_ApiDeallocateString(&fileName);
				if (F_StrIEqual(place,(StringT)""))
				{
					compID = F_ApiNewSeriesObject(bookID,FO_BookComponent,0);
					F_ApiSetString(bookID,compID,FP_Name,tmpPath);
				}
				else
				{
					compID = F_ApiGetId(FV_SessionId,bookID,FP_HighestLevelElement);
					//compID = F_ApiGetId(bookID,F_ApiGetId(FV_SessionId,bookID,FP_MainFlowInDoc),FP_HighestLevelElement);
					if (!compID)
					{
						F_Printf(NULL,"OpenFiles:\n\tHighest element error\n");
						return;
					}
					else
					{
						compID = F_ApiGetId(bookID,compID,FP_FirstChildElement);
						compExists = False;
						while (compID && (!compExists))
						{
							elemID = F_ApiGetId(bookID,compID,FP_ElementDef);
							if (F_StrIEqual(F_ApiGetString(bookID,elemID,FP_Name),place))
							{
								compExists = True;
							}
							else
							{
								compID = F_ApiGetId(bookID,compID,FP_NextSiblingElement);
							}
						}
						if (!compExists)
						{
							elemID = F_ApiGetNamedObject(bookID,FO_ElementDef,place);
							elemLoc.parentId = F_ApiGetId(FV_SessionId,bookID,FP_HighestLevelElement);
							elemLoc.childId = 0;
							elemLoc.offset = 0;
							compID = F_ApiNewElementInHierarchy(bookID,elemID,&elemLoc);
						}
						elemLoc.parentId = compID;
						elemLoc.offset = 0;
						elemLoc.childId = 0;
						compID = F_ApiNewBookComponentInHierarchy(bookID,tmpPath,&elemLoc);
					}
				}
			}
		}
		else if ((!F_StrIEqual(fileFileName(tmpPath),defaultBookName))&&
			(!F_StrSuffix(fileFileName(tmpPath),(StringT)".book"))&&
			(!F_StrSuffix(fileFileName(tmpPath),(StringT)".drl")))
			//(!F_StrSuffix(fileFileName(tmpPath),(StringT)".backup.fm")))
		{
			F_DeleteFile(file);
		}
		F_ApiDeallocateString(&tmpPath);
		F_FilePathFree(file);
	}
	//Checking for all book components, that their files exists
	compID = F_ApiGetId(FV_SessionId,bookID,FP_FirstComponentInBook);
	while (compID)
	{
		compExists = False;
		handle = F_FilePathOpenDir(newpath,&statusp);
		while (file = F_FilePathGetNext(handle,&statusp))
		{
			tmpPath = F_FilePathToPathName(file,FDosPath);
			if (compExists = F_StrIEqual(fileFileName(tmpPath),fileFileName(F_ApiGetString(bookID,compID,FP_Name))))
			{
				break;
			}
		}
		if (!compExists)
		{
			elemID = F_ApiGetId(bookID,compID,FP_NextComponentInBook);
			F_ApiDelete(bookID,compID);
			compID = elemID;
		}
		else
		{
			compID = F_ApiGetId(bookID,compID,FP_NextComponentInBook);
		}
	}
	F_ApiSimpleGenerate(bookID,False,True);
	F_ApiSimpleSave(bookID,bookPath,False); 
	F_FilePathCloseDir(handle);
	F_FilePathFree(file);
	//F_FilePathFree(newpath);
	F_ApiDeallocateString(&path);
	F_ApiDeallocateString(&bookPath);
	F_ApiDeallocateString(&tmpPath);
	F_ApiDeallocateString(&tmpPath2);
}
VoidT cleanDirectory(FilePathT *dirPath)
{
	FilePathT *file;
	StringT path;
	DirHandleT handle;
	IntT statusp;

	handle = F_FilePathOpenDir(dirPath,&statusp);
	if (!handle)
	{
		F_Printf(NULL,"Invalid directory path: %s\n",F_FilePathToPathName(dirPath,FDosPath));
		return;
	}
	while (file = F_FilePathGetNext(handle,&statusp))
	{
		path = F_FilePathToPathName(file,FDosPath);
		path = fileFileName(path);
		if ((!(validateFilename(path,FM)&&(!F_StrISuffix(path,(StringT)".backup.fm")
			&&(!F_StrISuffix(path,(StringT)".recover.fm")))))
			&&(!validateFilename(path,DRL)||(F_StrSuffix(path,(StringT)".backup.drl")))
			&&(!F_StrIEqual(path,defaultBookName)))
		{
			F_DeleteFile(file);
		}
	}
	F_FilePathCloseDir(handle);
	F_ApiDeallocateString(&path);
	F_FilePathFree(file);
}
VoidT generateBooks(F_ObjHandleT mainBookID)
{
	F_ObjHandleT elemID, bookID, childID, compID;
	StringT dirPath, bookName, place, tmpString;
	IntT i;
	F_AttributesT attrs;

	dirPath = F_ApiGetString(FV_SessionId,mainBookID,FP_Name);
	pathFilename(dirPath);
	elemID = F_ApiGetId(FV_SessionId,mainBookID,FP_HighestLevelElement);
	if (!elemID)
	{
		F_ApiAlert("Book is unstructured of there is no elements",FF_ALERT_CONTINUE_NOTE);
		return;
	}
	elemID = F_ApiGetId(mainBookID,elemID,FP_FirstChildElement);
	if (!elemID)
	{
		F_ApiAlert("There is only highest element in book",FF_ALERT_CONTINUE_NOTE);
		return;
	}
	i = 0;
	bookName = F_Alloc(F_StrLen(dirPath)+20,NO_DSE);
	while (elemID)
	{
		F_Sprintf(bookName,"%sbook%d.book",dirPath,i);
		if (!bookName)
		{
			F_Printf(NULL,"Convert error");
			return;
		}
		tmpString = F_ApiGetString(mainBookID,F_ApiGetId(mainBookID,elemID,FP_ElementDef),FP_Name);
		attrs = F_ApiGetAttributes(mainBookID,elemID);
		if (F_StrIEqual(tmpString,(StringT)"DocumentationCore"))
		{
			place = (StringT)"C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_documentationCore_book_template.book";
		}
		else if (F_StrIEqual(tmpString,(StringT)"ProductDocumentation"))
		{
			place = (StringT)"C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_productDocumentation_book_template.book";
		}
		else if (F_StrIEqual(tmpString,(StringT)"ProductLine"))
		{
			place = (StringT)"C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_productLine_book_template.book";
		}
		else
		{
			place = (StringT)"";
		}
		bookID = F_ApiSimpleOpen(place,False);
		if (!bookID)
		{
			F_Printf(NULL,"There is no template for this type of book");
			continue;
		}
		F_ApiSetAttributes(bookID,F_ApiGetId(FV_SessionId,bookID,FP_HighestLevelElement),&attrs);
		childID = F_ApiGetId(mainBookID,elemID,FP_FirstChildElement);
		while (childID)
		{
			compID = F_ApiGetId(mainBookID,childID,FP_BookComponent);
			if (compID)
			{
				addStructuredElementToBook(mainBookID,bookID,F_ApiGetId(FV_SessionId,bookID,FP_HighestLevelElement),compID);
			}
			else
			{
				addStructuredElementToBook(mainBookID,bookID,F_ApiGetId(FV_SessionId,bookID,FP_HighestLevelElement),childID);
			}
			childID = F_ApiGetId(mainBookID,childID,FP_NextSiblingElement);
		}
		F_ApiSimpleGenerate(bookID,False,True);
		F_ApiSimpleSave(bookID,bookName,False);
		F_ApiClose(bookID,FF_CLOSE_MODIFIED);
		i++;
		elemID = F_ApiGetId(mainBookID,elemID,FP_NextSiblingElement);
	}
}
VoidT openBook()
{
	StringT path;
	F_ObjHandleT docID;
	//Do not use russian letters in path!!!
	docID = F_ApiSimpleOpen(defaultPath,True);
	if (!docID)
	{
		F_Printf(NULL,"No such file: %s\n",defaultPath);
		return;
	}
	path = F_FilePathToPathName(F_PathNameToFilePath(F_ApiGetString(FV_SessionId,docID,FP_Name),NULL,FDefaultPath),FDosPath);
	if ((!validateFilename(path,FM))&&(!((validateFilename(path,FMBOOK))&&(F_StrISuffix(path,defaultBookName)))))
	{
		F_Printf(NULL,"Error. Not Framemaker file\n");
		return;
	}
	openFilesInDirectory(path);

}
VoidT createNewDocLineBook()
{
	F_ObjHandleT bookId;
	StringT path, bookPath;
	IntT err;
	/* Choose workspace for new docline project */
	err = F_ApiChooseFile(&path, "Choose directory to save new docline project", "", "", FV_ChooseOpenDir, "");
	if (err)
		return;
	/* Open template book and save it to the selected directory*/
	bookId = F_ApiSimpleOpen("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_book_template.book",False);
	bookPath = F_Alloc(F_StrLen(path)+F_StrLen(defaultBookName)+3,NO_DSE);
	bookPath = F_StrCopyString(path);
	F_StrCat(bookPath,F_StrCopyString("\\"));
	F_StrCat(bookPath,defaultBookName);
	F_ApiSimpleSave(bookId, bookPath, False);
	/* Deallocating memory */
	F_ApiDeallocateString(&path);
	//F_ApiDeallocateString(&bookPath);
}
VoidT closeProject()
{
	F_ObjHandleT openedDocId, nextDocId, openedBookId, nextBookId;
	StringT fileName, pathName;

	/* Get Id and path of the active document - any from the project */
	openedDocId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveDoc);
	/* if opened book get id of active book*/
	if (openedDocId == 0)
		openedDocId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveBook);
	/* exit if no documents are opened*/
	if (openedDocId == 0)
		return;
	fileName = F_ApiGetString(FV_SessionId, openedDocId, FP_Name);
	/* Remember path of the project directory - we will close all files from there */
	pathName = F_StrCopyString(fileName);
	pathFilename(pathName);
	/* from first opened we look through all opened documents */
	openedDocId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_FirstOpenDoc);	
    while (openedDocId != 0)
    {
		nextDocId = F_ApiGetId(FV_SessionId, openedDocId, FP_NextOpenDocInSession);
		fileName = F_ApiGetString(FV_SessionId, openedDocId, FP_Name);
		pathFilename(fileName);
		/* If current file from the project's workspace, save and close it */
		if (F_StrIEqual(pathName, fileName))
		{
			/* Save file and close it */
				fileName = F_ApiGetString(FV_SessionId, openedDocId, FP_Name);
				F_ApiSimpleSave(openedDocId, fileName, False);
				F_ApiClose(openedDocId, FF_CLOSE_MODIFIED);
		}
		openedDocId = nextDocId;
	}	
	/* All the same with books*/
	openedBookId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_FirstOpenBook);	
    while (openedBookId != 0)
    {
		nextBookId = F_ApiGetId(FV_SessionId, openedBookId, FP_NextOpenBookInSession);
		fileName = F_ApiGetString(FV_SessionId, openedBookId, FP_Name);
		pathFilename(fileName);
		if (F_StrIEqual(pathName, fileName))
		{
			/* Save file and close it */
				fileName = F_ApiGetString(FV_SessionId, openedBookId, FP_Name);
				F_ApiSimpleSave(openedBookId, fileName, False);
				F_ApiClose(openedBookId, FF_CLOSE_MODIFIED);
		}
		openedBookId = nextBookId;
	}
}

BoolT newSecondLevelSection(BoolT isFirst, StringT type)
{
	F_ObjHandleT bookId, sectionDlgId, compId, elemId;
	StringT fileName;
	F_AttributesT attributes;
	F_ElementLocT elemLoc;

	/* Get Id of the book */
	bookId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveBook);
	/* Open resource for the Create new DocumentationCore || ProductDocumentation || ProductLine section dialog */
	sectionDlgId = F_ApiOpenResource(FO_DialogResource, "doccore");	
	if (!isFirst)
		F_ApiSetInt(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 5), FP_Visibility, False);
	else
		F_ApiSetInt(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 5), FP_Visibility, True);
	if (F_StrIEqual(type, "ProductDocumentation"))
	{
		F_ApiSetString(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 0), FP_Label, "Type FileName attribute of new ProductDocumentation section:");	
		F_ApiSetString(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 5), FP_Label, "There is no ProductDocumentation section in project. New section will be created.");	
	}
	else if (F_StrIEqual(type, "ProductLine"))
	{
		F_ApiSetString(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 0), FP_Label, "Type FileName attribute of new ProductLine section:");
		F_ApiSetString(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 5), FP_Label, "There is no ProductLine section in project. New section will be created.");	
	}

	F_ApiModalDialog(SECTION_DLG, sectionDlgId);
	/* define which button was clicked */
	if((F_ApiGetInt(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 4), FP_State) == True) ||
		(F_ApiGetInt(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 3), FP_State) != True))
	{
		F_ApiClose (sectionDlgId, FF_CLOSE_MODIFIED);
		return False;
	}
	/* make sure that FileName attribute was typed in the text box */
	while (F_StrIsEmpty(F_ApiGetString(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 1), FP_Text)))
	{
		F_ApiAlert("You must type FileName attribute in the text field!", FF_ALERT_CONTINUE_NOTE);
		F_ApiModalDialog(SECTION_DLG, sectionDlgId);
		if((F_ApiGetInt(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 4), FP_State) == True) ||
			(F_ApiGetInt(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 3), FP_State) != True))
		{
			F_ApiClose (sectionDlgId, FF_CLOSE_MODIFIED);
			return False;
		}
	}
	/* get fileName attribute value from dialog box*/
	fileName = F_StrCopyString(F_ApiGetString(sectionDlgId, F_ApiDialogItemId(sectionDlgId, 1), FP_Text));
	/* Get Id of Section Element definitions*/
	elemId = F_ApiGetNamedObject(bookId, FO_ElementDef, type);
	elemLoc.parentId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
	elemLoc.childId = 0;
	elemLoc.offset = 0;
	compId = F_ApiNewElementInHierarchy(bookId, elemId, &elemLoc);
	/* Create F_AttributesT structure to set FileName value */
	attributes.len = 1;
	attributes.val = (F_AttributeT *)F_Alloc(sizeof(F_AttributeT), DSE);
	attributes.val[0].name = F_StrCopyString("FileName");
	attributes.val[0].values.len = 1;
	attributes.val[0].values.val = (StringT *)F_Alloc(sizeof(StringT), DSE);
	attributes.val[0].values.val[0] = F_StrCopyString(fileName);
	/* Set proper values */
	F_ApiSetAttributes(bookId, compId, &attributes);
	F_ApiClose (sectionDlgId, FF_CLOSE_MODIFIED);
	return True;
}

VoidT newDocCoreChild(IntT type)
{
	F_ObjHandleT bookId, docId, childEdefId, compId, elemId, dlgId, childId;
	F_ElementLocT elemLoc;
	StringT bookPath, savePath, edefName, nameStr, idStr, elemName, selectedDocCore;
	IntT len;
	BoolT compExists, compFound;
	F_AttributesT docCoreAttr;
	F_StringsT doccores;
	UIntT j;

	/* Open resource for the dialogs */
	dlgId = F_ApiOpenResource(FO_DialogResource, "docline");

	/* Get Id and path of the book */
	bookId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveBook);
	bookPath = F_ApiGetString(FV_SessionId, bookId, FP_Name);
	pathFilename(bookPath);

	/* Check whether document has Docline section*/
	compId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
	if (!compId)
	{
		F_ApiAlert("Highest element error",FF_ALERT_CONTINUE_NOTE);
		F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
		return;
	}
	else
	{
		/* Check which DocumentationCore sections already exist */
		compId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
		compExists = False;
		/* Initiallze list for dialog's pop-up*/
		doccores.val = (StringT*) F_Alloc(sizeof(StringT), NO_DSE);
		doccores.len = 1;
		doccores.val[0] = F_StrCopyString("...");
		while (compId)
		{
			elemId = F_ApiGetId(bookId, compId, FP_ElementDef);
			if (F_StrIEqual(F_ApiGetString(bookId, elemId, FP_Name), "DocumentationCore"))
			{
				compExists = True;
				break;
			}
			compId = F_ApiGetId(bookId, compId, FP_NextSiblingElement);
		}
		if (!compExists) // There is no "DocumentationCore" section
		{
			/* Create DocumentationCore section */
			if(!newSecondLevelSection(True, "DocumentationCore"))
			{
				F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
				return;
			}
		}
		compId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
		compId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
		while (compId)
		{
			elemId = F_ApiGetId(bookId, compId, FP_ElementDef);
			if (F_StrIEqual(F_ApiGetString(bookId, elemId, FP_Name), "DocumentationCore"))
			{
				docCoreAttr = F_ApiGetAttributes(bookId, compId);
				for(j=0; j<docCoreAttr.len; j++)
				{
					if (F_StrEqual("FileName", docCoreAttr.val[j].name))
					{
						/* Allocate space for new string in dialog's popup */
						doccores.len++;
						doccores.val = (StringT *) F_Realloc(doccores.val, doccores.len*sizeof(StringT), NO_DSE);
						/* Add string to the Pop-Up. */
						doccores.val[doccores.len-1] = F_StrCopyString(docCoreAttr.val[j].values.val[0]);
						break;
					}
				}
			}
			compId = F_ApiGetId(bookId, compId, FP_NextSiblingElement);
		}
		F_ApiSetStrings(dlgId, F_ApiDialogItemId(dlgId, 7), FP_Labels, &doccores);
		/* Make the first item the default. */
		F_ApiSetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State, 1);
	}

	/* show modal dialog with prompt for attributes */
	F_ApiModalDialog(NEW_DLG, dlgId);
	if ((F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, CANCELDLG), FP_State) == True) ||
		(F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, OKDLG), FP_State) != True))
	{
		F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
		return;
	}
	/* make sure that all attributes are typed in the text box*/
	while (F_StrIsEmpty(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 1), FP_Text)) ||
			F_StrIsEmpty(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 3), FP_Text)))
	{
		F_ApiAlert("You must type Id and Name in text fields!", FF_ALERT_CONTINUE_NOTE);
		F_ApiModalDialog(NEW_DLG, dlgId);
		if ((F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, CANCELDLG), FP_State) == True) ||
			(F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, OKDLG), FP_State) != True))
		{
			F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
			return;
		}
	}
	/* get Id and Name values from dialog box*/
	idStr = F_StrCopyString(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 1), FP_Text));
	nameStr = F_StrCopyString(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 3), FP_Text));

	/* get Id of selected DocumentationCore section */
	selectedDocCore = doccores.val[F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State)];
	compId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
	compId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
	compFound = False;
	while (True)
	{
		elemId = F_ApiGetId(bookId, compId, FP_ElementDef);
		if (F_StrIEqual(F_ApiGetString(bookId, elemId, FP_Name), "DocumentationCore"))
		{	
			docCoreAttr = F_ApiGetAttributes(bookId, compId);
			for(j=0; j<docCoreAttr.len; j++) {
				if (F_StrEqual("FileName", docCoreAttr.val[j].name))
				{
					if (F_StrEqual(selectedDocCore, docCoreAttr.val[j].values.val[0]))
						compFound = True;
					break;
				}
			}
			if (compFound)
				break;
		}
		compId = F_ApiGetId(bookId, compId, FP_NextSiblingElement);
	}

	/* Choose what type of element we add*/
	switch (type)
	{
	case DICTION:
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen(idStr)+F_StrLen("dictionary_.fm")+1, NO_DSE);
		len = F_Sprintf(savePath, "%sdictionary_%s.fm", (StringT)bookPath, (StringT)idStr);
		edefName = F_StrCopyString("Dictionary");
		break;
	case DIRECT:
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen(idStr)+F_StrLen("directory_.fm")+1, NO_DSE);
		len = F_Sprintf(savePath, "%sdirectory_%s.fm", (StringT)bookPath, (StringT)idStr);
		edefName = F_StrCopyString("Directory");
		break;
	case DIRTEMP:
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen(idStr)+F_StrLen("dir_template_.fm")+1, NO_DSE);
		len = F_Sprintf(savePath, "%sdir_template_%s.fm", (StringT)bookPath, (StringT)idStr);
		edefName = F_StrCopyString("DirTemplate");
		break;
	case INFELEM:
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen(idStr)+F_StrLen("inf_element_.fm")+1, NO_DSE);
		len = F_Sprintf(savePath, "%sinf_element_%s.fm", (StringT)bookPath, (StringT)idStr);
		edefName = F_StrCopyString("InfElement");
		break;
	case INFPROD:
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen(idStr)+F_StrLen("inf_product_.fm")+1, NO_DSE);
		len = F_Sprintf(savePath, "%sinf_product_%s.fm", (StringT)bookPath, (StringT)idStr);
		edefName = F_StrCopyString("InfProduct");
		break;
	}
	/* Create document from template */
	docId = F_ApiSimpleNewDoc("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_doc_template.fm", False);
	/* Get Id of the highest-level element definition for created document */
	childEdefId = F_ApiGetNamedObject(docId, FO_ElementDef, edefName);
	/* Insert new Highest-level element into the document, i.e. InfElement, InfProduct, etc. */
	F_ApiWrapElement(docId, childEdefId);
	/* Save the doc with the specific name */
	F_ApiSimpleSave(docId, savePath, False);
	/* Set correct values of attributes */
	setAttributes(idStr, nameStr);
	/* Update header of the document */
	editHeader();
	
	/* Insert Book component in DocumentationCore section*/
	childId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
	while (childId)
	{
		elemId = F_ApiGetId(bookId, childId, FP_ElementDef);
		elemName = F_ApiGetString(bookId, elemId, FP_Name);
		if (F_StrIEqual(elemName, "Dictionary"))
		{
			if (type == DICTION)
				break;
		}
		else if (F_StrIEqual(elemName, "Directory"))
		{
			if (type <= DIRECT)
				break;
		}
		else if (F_StrIEqual(elemName, "DirTemplate"))
		{
			if (type <= DIRTEMP)
				break;
		}
		else if (F_StrIEqual(elemName, "InfElement"))
		{
			if (type <= INFELEM)
				break;
		}
		else if (F_StrIEqual(elemName, "InfProduct"))
		{
			if (type <= INFPROD)
				break;
		} 
		childId = F_ApiGetId(bookId, childId, FP_NextSiblingElement);
	}
	elemLoc.childId = childId;
	elemLoc.parentId = compId;
	elemLoc.offset = 0;
	compId = F_ApiNewBookComponentInHierarchy(bookId, savePath, &elemLoc);
	/* Update book */
	F_ApiSimpleGenerate(bookId, False, True);
	bookPath = F_ApiGetString(FV_SessionId, bookId, FP_Name);
	/* Save book and docs after update */
	F_ApiSimpleSave(docId, savePath, False);
	F_ApiSimpleSave(bookId, bookPath, False);
	/* Deallocating memory */
	F_ApiDeallocateString(&bookPath);
	F_ApiDeallocateString(&savePath);
	F_ApiDeallocateString(&edefName);
	F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
}

VoidT newProdDocChild(IntT type)
{
	F_ObjHandleT bookId, docId, childEdefId, compId, elemId, dlgId, childId;
	F_ElementLocT elemLoc;
	StringT bookPath, savePath, edefName, nameStr, idStr, elemName, selectedProdDoc;
	IntT len;
	BoolT compExists, compFound;
	F_AttributesT prodDocAttr;
	F_StringsT proddocs;
	UIntT j;

	/* Open resource for the dialogs */
	dlgId = F_ApiOpenResource(FO_DialogResource, "docline");
	/* Change label from DocumentationCore to ProductDocumentation*/
	F_ApiSetString(dlgId, F_ApiDialogItemId(dlgId, 8), FP_Label, "Choose ProductDocumentation section to use");	

	/* Get Id and path of the book */
	bookId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveBook);
	bookPath = F_ApiGetString(FV_SessionId, bookId, FP_Name);
	pathFilename(bookPath);

	/* Check whether document has Docline section*/
	compId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
	if (!compId)
	{
		F_ApiAlert("Highest element error",FF_ALERT_CONTINUE_NOTE);
		F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
		return;
	}
	else
	{
		/* Check which DocumentationCore sections already exist */
		compId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
		compExists = False;
		/* Initiallze list for dialog's pop-up*/
		proddocs.val = (StringT*) F_Alloc(sizeof(StringT), NO_DSE);
		proddocs.len = 1;
		proddocs.val[0] = F_StrCopyString("...");
		while (compId)
		{
			elemId = F_ApiGetId(bookId, compId, FP_ElementDef);
			if (F_StrIEqual(F_ApiGetString(bookId, elemId, FP_Name), "ProductDocumentation"))
			{
				compExists = True;
				break;
			}
			compId = F_ApiGetId(bookId, compId, FP_NextSiblingElement);
		}
		if (!compExists) // There is no "ProsuctDocumentation" section
		{
			/* Create ProductDocumentation section */
			if(!newSecondLevelSection(True, "ProductDocumentation"))
			{
				F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
				return;
			}
		}
		compId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
		compId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
		while (compId)
		{
			elemId = F_ApiGetId(bookId, compId, FP_ElementDef);
			if (F_StrIEqual(F_ApiGetString(bookId, elemId, FP_Name), "ProductDocumentation"))
			{
				prodDocAttr = F_ApiGetAttributes(bookId, compId);
				for(j=0; j<prodDocAttr.len; j++)
				{
					if (F_StrEqual("FileName", prodDocAttr.val[j].name))
					{
						/* Allocate space for new string in dialog's popup */
						proddocs.len++;
						proddocs.val = (StringT *) F_Realloc(proddocs.val, proddocs.len*sizeof(StringT), NO_DSE);
						/* Add string to the Pop-Up. */
						proddocs.val[proddocs.len-1] = F_StrCopyString(prodDocAttr.val[j].values.val[0]);
						break;
					}
				}
			}
			compId = F_ApiGetId(bookId, compId, FP_NextSiblingElement);
		}
		F_ApiSetStrings(dlgId, F_ApiDialogItemId(dlgId, 7), FP_Labels, &proddocs);
		/* Make the first item the default. */
		F_ApiSetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State, 1);
	}

	/* show modal dialog with prompt for attributes */
	F_ApiModalDialog(NEW_DLG, dlgId);
	if ((F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, CANCELDLG), FP_State) == True) ||
		(F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, OKDLG), FP_State) != True))
	{
		F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
		return;
	}
	/* make sure that all attributes are typed in the text box*/
	while (F_StrIsEmpty(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 1), FP_Text)) ||
			F_StrIsEmpty(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 3), FP_Text)))
	{
		F_ApiAlert("You must type Id and Name in text fields!", FF_ALERT_CONTINUE_NOTE);
		F_ApiModalDialog(NEW_DLG, dlgId);
		if ((F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, CANCELDLG), FP_State) == True) ||
			(F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, OKDLG), FP_State) != True))
		{
			F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
			return;
		}
	}
	/* get Id and Name values from dialog box*/
	idStr = F_StrCopyString(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 1), FP_Text));
	nameStr = F_StrCopyString(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 3), FP_Text));

	/* get Id of selected ProductDocumentation section */
	selectedProdDoc = proddocs.val[F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State)];
	compId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
	compId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
	compFound = False;
	while (True)
	{
		elemId = F_ApiGetId(bookId, compId, FP_ElementDef);
		if (F_StrIEqual(F_ApiGetString(bookId, elemId, FP_Name), "ProductDocumentation"))
		{	
			prodDocAttr = F_ApiGetAttributes(bookId, compId);
			for(j=0; j<prodDocAttr.len; j++) {
				if (F_StrEqual("FileName", prodDocAttr.val[j].name))
				{
					if (F_StrEqual(selectedProdDoc, prodDocAttr.val[j].values.val[0]))
						compFound = True;
					break;
				}
			}
			if (compFound)
				break;
		}
		compId = F_ApiGetId(bookId, compId, FP_NextSiblingElement);
	}

	/* Choose what type of element we add*/
	switch (type)
	{
	case DICTION:
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen(idStr)+F_StrLen("dictionary_.fm")+1, NO_DSE);
		len = F_Sprintf(savePath, "%sdictionary_%s.fm", (StringT)bookPath, (StringT)idStr);
		edefName = F_StrCopyString("Dictionary");
		break;
	case DIRECT:
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen(idStr)+F_StrLen("directory_.fm")+1, NO_DSE);
		len = F_Sprintf(savePath, "%sdirectory_%s.fm", (StringT)bookPath, (StringT)idStr);
		edefName = F_StrCopyString("Directory");
		break;
	case DIRTEMP:
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen(idStr)+F_StrLen("dir_template_.fm")+1, NO_DSE);
		len = F_Sprintf(savePath, "%sdir_template_%s.fm", (StringT)bookPath, (StringT)idStr);
		edefName = F_StrCopyString("DirTemplate");
		break;
	case FINALINF:
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen(idStr)+F_StrLen("final_inf_product_.fm")+1, NO_DSE);
		len = F_Sprintf(savePath, "%sfinal_inf_product_%s.fm", (StringT)bookPath, (StringT)idStr);
		edefName = F_StrCopyString("FinalInfProduct");
		break;
	}
	/* Create document from template */
	docId = F_ApiSimpleNewDoc("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_doc_template.fm", False);
	/* Get Id of the highest-level element definition for created document */
	childEdefId = F_ApiGetNamedObject(docId, FO_ElementDef, edefName);
	/* Insert new Highest-level element into the document, i.e. Dictionary, Directory, etc. */
	F_ApiWrapElement(docId, childEdefId);
	/* Save the doc with the specific name */
	F_ApiSimpleSave(docId, savePath, False);
	/* Set correct values of attributes */
	setAttributes(idStr, nameStr);
	/* Update header of the document */
	editHeader();
	
	/* Insert Book component in ProductDocumentation section*/
	childId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
	while (childId)
	{
		elemId = F_ApiGetId(bookId, childId, FP_ElementDef);
		elemName = F_ApiGetString(bookId, elemId, FP_Name);
		if (F_StrIEqual(elemName, "Dictionary"))
		{
			if (type == DICTION)
				break;
		}
		else if (F_StrIEqual(elemName, "Directory"))
		{
			if (type <= DIRECT)
				break;
		}
		else if (F_StrIEqual(elemName, "DirTemplate"))
		{
			if (type <= DIRTEMP)
				break;
		}
		else if (F_StrIEqual(elemName, "FinalInfProduct"))
		{
			if (type <= FINALINF)
				break;
		}
		childId = F_ApiGetId(bookId, childId, FP_NextSiblingElement);
	}
	elemLoc.childId = childId;
	elemLoc.parentId = compId;
	elemLoc.offset = 0;
	compId = F_ApiNewBookComponentInHierarchy(bookId, savePath, &elemLoc);
	/* Update book */
	F_ApiSimpleGenerate(bookId, False, True);
	bookPath = F_ApiGetString(FV_SessionId, bookId, FP_Name);
	/* Save book and docs after update */
	F_ApiSimpleSave(docId, savePath, False);
	F_ApiSimpleSave(bookId, bookPath, False);
	/* Deallocating memory */
	F_ApiDeallocateString(&bookPath);
	F_ApiDeallocateString(&savePath);
	F_ApiDeallocateString(&edefName);
	F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
}

VoidT newProdLineChild(IntT type)
{
	F_ObjHandleT bookId, docId, childEdefId, compId, elemId, dlgId, childId;
	F_ElementLocT elemLoc;
	StringT bookPath, savePath, edefName, nameStr, idStr, selectedProdLine;
	IntT len;
	BoolT compExists, compFound;
	F_AttributesT prodLineAttr;
	F_StringsT prodlines;
	UIntT j;

	/* Open resource for the dialogs */
	dlgId = F_ApiOpenResource(FO_DialogResource, "docline");
	/* Change label from DocumentationCore to ProductLine*/
	F_ApiSetString(dlgId, F_ApiDialogItemId(dlgId, 8), FP_Label, "Choose ProductLine section to use");	

	/* Get Id and path of the book */
	bookId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveBook);
	bookPath = F_ApiGetString(FV_SessionId, bookId, FP_Name);
	pathFilename(bookPath);

	/* Check whether document has Docline section*/
	compId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
	if (!compId)
	{
		F_ApiAlert("Highest element error",FF_ALERT_CONTINUE_NOTE);
		F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
		return;
	}
	else
	{
		/* Check whether ProductLine sections already exist */
		compId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
		compExists = False;
		/* Initiallze list for dialog's pop-up*/
		prodlines.val = (StringT*) F_Alloc(sizeof(StringT), NO_DSE);
		prodlines.len = 1;
		prodlines.val[0] = F_StrCopyString("...");
		while (compId)
		{
			elemId = F_ApiGetId(bookId, compId, FP_ElementDef);
			if (F_StrIEqual(F_ApiGetString(bookId, elemId, FP_Name), "ProductLine"))
			{
				compExists = True;
				break;
			}
			compId = F_ApiGetId(bookId, compId, FP_NextSiblingElement);
		}
		if (!compExists) // There is no "ProductLine" section
		{
			/* Create ProductLine section */
			if(!newSecondLevelSection(True, "ProductLine"))
			{
				F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
				return;
			}
		}
		compId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
		compId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
		while (compId)
		{
			elemId = F_ApiGetId(bookId, compId, FP_ElementDef);
			if (F_StrIEqual(F_ApiGetString(bookId, elemId, FP_Name), "ProductLine"))
			{
				prodLineAttr = F_ApiGetAttributes(bookId, compId);
				for(j=0; j<prodLineAttr.len; j++)
				{
					if (F_StrEqual("FileName", prodLineAttr.val[j].name))
					{
						/* Allocate space for new string in dialog's popup */
						prodlines.len++;
						prodlines.val = (StringT *) F_Realloc(prodlines.val, prodlines.len*sizeof(StringT), NO_DSE);
						/* Add string to the Pop-Up. */
						prodlines.val[prodlines.len-1] = F_StrCopyString(prodLineAttr.val[j].values.val[0]);
						break;
					}
				}
			}
			compId = F_ApiGetId(bookId, compId, FP_NextSiblingElement);
		}
		F_ApiSetStrings(dlgId, F_ApiDialogItemId(dlgId, 7), FP_Labels, &prodlines);
		/* Make the first item the default. */
		F_ApiSetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State, 1);
	}

	/* show modal dialog with prompt for attributes */
	F_ApiModalDialog(NEW_DLG, dlgId);
	if ((F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, CANCELDLG), FP_State) == True) ||
		(F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, OKDLG), FP_State) != True))
	{
		F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
		return;
	}
	/* make sure that all attributes are typed in the text box*/
	while (F_StrIsEmpty(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 1), FP_Text)) ||
			F_StrIsEmpty(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 3), FP_Text)))
	{
		F_ApiAlert("You must type Id and Name in text fields!", FF_ALERT_CONTINUE_NOTE);
		F_ApiModalDialog(NEW_DLG, dlgId);
		if ((F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, CANCELDLG), FP_State) == True) ||
			(F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, OKDLG), FP_State) != True))
		{
			F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
			return;
		}
	}
	/* get Id and Name values from dialog box*/
	idStr = F_StrCopyString(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 1), FP_Text));
	nameStr = F_StrCopyString(F_ApiGetString(dlgId, F_ApiDialogItemId(dlgId, 3), FP_Text));

	/* get Id of selected ProductLine section */
	selectedProdLine = prodlines.val[F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State)];
	compId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
	compId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
	compFound = False;
	while (True)
	{
		elemId = F_ApiGetId(bookId, compId, FP_ElementDef);
		if (F_StrIEqual(F_ApiGetString(bookId, elemId, FP_Name), "ProductLine"))
		{	
			prodLineAttr = F_ApiGetAttributes(bookId, compId);
			for(j=0; j<prodLineAttr.len; j++) {
				if (F_StrEqual("FileName", prodLineAttr.val[j].name))
				{
					if (F_StrEqual(selectedProdLine, prodLineAttr.val[j].values.val[0]))
						compFound = True;
					break;
				}
			}
			if (compFound)
				break;
		}
		compId = F_ApiGetId(bookId, compId, FP_NextSiblingElement);
	}

	/* Choose what type of element we add*/
	savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen(idStr)+F_StrLen("product_.fm")+1, NO_DSE);
	len = F_Sprintf(savePath, "%sproduct_%s.fm", (StringT)bookPath, (StringT)idStr);
	edefName = F_StrCopyString("Product");

	/* Create document from template */
	docId = F_ApiSimpleNewDoc("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_doc_template.fm", False);
	/* Get Id of the highest-level element definition for created document */
	childEdefId = F_ApiGetNamedObject(docId, FO_ElementDef, edefName);
	/* Insert new Highest-level element into the document, i.e. Dictionary, Directory, etc. */
	F_ApiWrapElement(docId, childEdefId);
	/* Save the doc with the specific name */
	F_ApiSimpleSave(docId, savePath, False);
	/* Set correct values of attributes */
	setAttributes(idStr, nameStr);
	/* Update header of the document */
	editHeader();
	
	/* Insert Book component in ProductDocumentation section*/
	childId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
	elemLoc.childId = childId;
	elemLoc.parentId = compId;
	elemLoc.offset = 0;
	compId = F_ApiNewBookComponentInHierarchy(bookId, savePath, &elemLoc);
	/* Update book */
	F_ApiSimpleGenerate(bookId, False, True);
	bookPath = F_ApiGetString(FV_SessionId, bookId, FP_Name);
	/* Save book and docs after update */
	F_ApiSimpleSave(docId, savePath, False);
	F_ApiSimpleSave(bookId, bookPath, False);
	/* Deallocating memory */
	F_ApiDeallocateString(&bookPath);
	F_ApiDeallocateString(&savePath);
	F_ApiDeallocateString(&edefName);
	F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
}

VoidT renameFilesToActualNames(F_ObjHandleT bookID)
{
	F_ObjHandleT compID;
	StringT compPath, newCompPath, dirPath;
	F_AttributesT attrs;
	F_AttributeT attr;
	UIntT i, j;

	if (!bookID)
	{
		F_Printf(NULL,"renameFilesToActualNames error:\n\tInvalid Book\n");
		return;
	}
	dirPath = F_ApiGetString(FV_SessionId,bookID,FP_Name);
	pathFilename(dirPath);
	compID = F_ApiGetId(FV_SessionId,bookID,FP_FirstComponentInBook);
	while (compID)
	{
		compPath = F_ApiGetString(bookID,compID,FP_Name);
		attrs = F_ApiGetAttributes(bookID,F_ApiGetId(bookID,compID,FP_ComponentElement));
		for (j=0; j<attrs.len; j++)
		{
			attr = attrs.val[j];
			if (F_StrIEqual(attr.name,(StringT)"Id"))
			{
				if (!attr.values.len)
				{
					F_Printf(NULL,"Error in Id attribute in %s\n",compPath);
					continue;
				}

				newCompPath = F_Alloc(F_StrLen(attr.values.val[0])+F_StrLen(dirPath)+10,NO_DSE);
				F_Sprintf(newCompPath,"%s%s.fm",dirPath,attr.values.val[0]);
				i = 0;
				while (F_RenameFile(F_PathNameToFilePath(compPath,NULL,FDosPath),F_PathNameToFilePath(newCompPath,NULL,FDosPath)) != FdeSuccess)
				{
					F_Sprintf(newCompPath,"%s%s%d.fm",dirPath,attr.values.val[0],i);
					F_Printf(NULL,"%s\n",newCompPath);
					i++;
				}
				F_ApiSetString(bookID,compID,FP_Name,newCompPath);
				F_ApiDeallocateString(&newCompPath);
			}
		}
		compID = F_ApiGetId(bookID,compID,FP_NextComponentInBook);
	}
}

VoidT importDocLineDoc()
{
	F_ObjHandleT docID, fileID, elemID, compID;
	StringT dirPath, tmpPath, bookPath, compPath, newCompPath;
	FilePathT *newdirPath, *file;
	DirHandleT handle;
	IntT statusp, i;
	UIntT j;
	F_PropValsT params, *returnParams;
	F_AttributesT attrs;
	F_AttributeT attr;
	IntT retVal;
	UCharT jarPath[256], statusMessage[256];
	StringT tempPath;
	ChannelT chan;

	tempPath = F_ApiClientDir();
	F_Sprintf(jarPath, "%s\\%s", tempPath, JAR_FILENAME);
	F_Free(tempPath);
	returnParams = NULL;
	params = F_ApiGetOpenDefaultParams();
	if (!params.len)
	{
		F_ApiAlert("Default params error",FF_ALERT_CONTINUE_NOTE);
		return;
	}
	i = F_ApiGetPropIndex(&params, FS_ShowBrowser);
	params.val[i].propVal.u.ival = True;
	i = F_ApiGetPropIndex(&params, FS_ForceOpenAsText);
	params.val[i].propVal.u.ival = True;
	//Choise of file only determines directory
	docID = F_ApiOpen(defaultPath,&params,&returnParams);
	returnParams = NULL;
	params = F_ApiGetOpenDefaultParams();
	if (!params.len)
	{
		F_ApiAlert("Default params error",FF_ALERT_CONTINUE_NOTE);
		return;
	}
	i = F_ApiGetPropIndex(&params,FS_StructuredOpenApplication);
	params.val[i].propVal.u.sval = "DocLine";
	if (!docID)
	{
		F_Printf(NULL,"No such file: %s\n", defaultPath);
		return;
	}
	dirPath = F_ApiGetString(FV_SessionId,docID,FP_Name);
	F_ApiClose(docID,FF_CLOSE_MODIFIED);
	pathFilename(dirPath);
	retVal = callJavaImportUtil(jarPath, dirPath);
	if (retVal > 0)
	{
		F_Printf(NULL,"JVM Initiliazation error\n");
		return;
	}
	bookPath = "";
	newdirPath = F_PathNameToFilePath (dirPath, NULL, FDosPath);
	handle = F_FilePathOpenDir(newdirPath, &statusp);
	if (!handle)
	{
		F_FilePathFree(newdirPath);
		F_ApiAlert("Handle Error2", FF_ALERT_CONTINUE_NOTE);
		return;
	}
	//Deleting non-docline files in directory
	while (file = F_FilePathGetNext(handle,&statusp))
	{
		tmpPath = F_FilePathToPathName(file,FDosPath);
		if (!validateFilename(tmpPath,DRL))
		{
			F_DeleteFile(file);
		}
	}
	F_FilePathCloseDir(handle);
	handle = F_FilePathOpenDir(newdirPath,&statusp);
	if (!handle)
	{
		F_Printf(NULL,"import error:\n\tInvalid directory path: %s\n",dirPath);
		return;
	}
	//Opening of all .drl files in directory with structured application "DocLine"
	while((file = F_FilePathGetNext(handle, &statusp)) != NULL)
	{
		tmpPath = F_FilePathToPathName(file,FDosPath);
		if (validateFilename(tmpPath,DRL))
		{
			fileID = F_ApiOpen(tmpPath,&params,&returnParams);
			if (!fileID)
			{
				F_Printf(NULL,"Error in opening file %s",tmpPath);
				continue;
			}
			elemID = F_ApiGetId(FV_SessionId,fileID,FP_HighestLevelElement);
			//Inserting additional attribute, that indicates file name, in highest level element
			if ((F_StrIEqual(F_ApiGetString(fileID,F_ApiGetId(fileID,elemID,FP_ElementDef),FP_Name),(StringT)"DocumentationCore"))||
				(F_StrIEqual(F_ApiGetString(fileID,F_ApiGetId(fileID,elemID,FP_ElementDef),FP_Name),(StringT)"ProductLine"))||
				(F_StrIEqual(F_ApiGetString(fileID,F_ApiGetId(fileID,elemID,FP_ElementDef),FP_Name),(StringT)"ProductDocumentation")))
			{
				attrs = F_ApiGetAttributes(fileID,elemID);
				for (j=0; j<attrs.len; j++)
				{
					attr = attrs.val[j];
					if (F_StrIEqual(attr.name,(StringT)"FileName"))
					{
						if (!attr.values.len)
						{
							attr.values.len = 1;
							attr.values.val = (StringT*)F_Alloc(sizeof(StringT),DSE);
						}
						attr.values.val[0] = fileFileName(F_StrCopyString(tmpPath));
						attrs.val[j] = attr;
					}
				}
				F_ApiSetAttributes(fileID,elemID,&attrs);
			}
			renameFilesToActualNames(fileID);
			fileID = F_ApiSimpleSave(fileID,F_ApiGetString(FV_SessionId,fileID,FP_Name),False);
			F_ApiClose(fileID,FF_CLOSE_MODIFIED);
			F_ApiDeallocatePropVals(returnParams);
		}
		F_ApiDeallocateString(&tmpPath);
		F_FilePathFree(file);
	}
	F_ApiDeallocatePropVals(&params);    
	F_FilePathCloseDir(handle);
	openFilesInDirectory(dirPath);
	cleanDirectory(newdirPath);
	F_FilePathFree(newdirPath);
	F_ApiDeallocateString(&dirPath);
	F_ApiDeallocateString(&bookPath);
}

VoidT publishDocLineDoc(StringT format)
{
	IntT retVal, response;
	UCharT jarPath[256]; // = "C:\\Program Files (x86)\\Adobe\\FDK8\\samples\\docline\\debug\\exportutil.jar";
	UCharT sourceDirPath[256];
	UCharT sourceFileName[256];
	UCharT destinationFileName[256];

	UCharT statusMessage[256];

	StringT tempPath, tempName, tempResult;
	F_ObjHandleT bookID;
	ChannelT chan;
	UCharT ptr[BUFFERSIZE];
	IntT numread;

	// promt user if he is sure he wants to publish document
	response = F_ApiAlert("This will export all files to DRL and then publish them. Do you still wish to continue?", FF_ALERT_YES_DEFAULT);
	if (response != 0) // user clicked "no"
		return;

	//get path to jar file and check if jar exists
	tempPath = F_ApiClientDir();
	F_Sprintf(jarPath, "%s\\%s", tempPath, JAR_FILENAME);
	F_Free(tempPath);
	if((chan = F_ChannelOpen(F_PathNameToFilePath(jarPath, NULL, FDefaultPath),"r")) == NULL)
	{
		F_Sprintf(statusMessage, "Couldn't find %s. Reinstalling the application can solve this problem.", JAR_FILENAME);
		F_ApiAlert(statusMessage, FF_ALERT_CONTINUE_WARN);
		return;
	}
	F_ChannelClose(chan);

	// export document back to DRL
	//exportDocLineDoc(); // doesn't work for me

	// show dialog to choose document to publish
	//   get book's home dir
	bookID = F_ApiGetId(0, FV_SessionId, FP_ActiveBook);
	if (!bookID || !F_StrISuffix(F_ApiGetString(FV_SessionId, bookID, FP_Name), defaultBookName))
	{
		F_ApiAlert("Invalid book", FF_ALERT_CONTINUE_NOTE);
		return;
	}
	tempPath = F_ApiGetString(FV_SessionId, bookID, FP_Name);
	pathFilename(tempPath);
	//   promt for filename
	response = F_ApiChooseFile(&tempResult, "Choose a file with Final Product", tempPath, "", FV_ChooseSelect, "");
	if (response != 0)
	{
		F_ApiDeallocateString(&tempResult);
		return;
	}
	//   save file name
	F_Sprintf(sourceFileName, "%s", F_FilePathBaseName(F_PathNameToFilePath(tempResult, NULL, FDefaultPath)));
	//   save dir name
	pathFilename(tempResult);
	F_Sprintf(sourceDirPath, "%s", tempResult);
	F_ApiDeallocateString(&tempResult);

	// show dialog to choose published document name 
	//   form suggested save name
	tempName = F_Alloc(F_StrLen(sourceFileName)-F_StrLen(".drl") + F_StrLen(format) + 1 + 1,NO_DSE);
	F_StrCpyN(tempName, sourceFileName, F_StrLen(sourceFileName)-F_StrLen(".drl") + 1);
	F_StrCat(tempName, ".");
	F_StrCat(tempName, format);
	//   promt for save name
	response = F_ApiChooseFile(&tempResult, "Choose a name for a file", tempPath, tempName, FV_ChooseSave, "");
	if (response != 0)
	{
		F_ApiDeallocateString(&tempResult);
		return;
	}
	//   save chosen destination file name
	F_Sprintf(destinationFileName, "%s", tempResult);
	F_ApiDeallocateString(&tempResult);
	F_Free(tempPath);
	F_Free(tempName);

	// invoke java util
	retVal = callJavaPublishUtil(jarPath, sourceDirPath, sourceFileName, " ", format, destinationFileName);

	if (retVal > 0) // error in JVM initialization
	{
		F_ApiAlert("There was an error while initializing java machine.", FF_ALERT_CONTINUE_WARN);
		return;
	}
	else  // java util worked, so there will be an error log file
	{
		tempPath = F_ApiClientDir();
		tempResult = F_Alloc(F_StrLen(tempPath) + F_StrLen(ERROR_LOG_FILENAME) + 1, NO_DSE);
		F_Sprintf(tempResult, "%s\\%s", tempPath, ERROR_LOG_FILENAME);
		F_Free(tempPath);

		if((chan = F_ChannelOpen(F_PathNameToFilePath(tempResult, NULL, FDefaultPath),"r")) == NULL)
		{
			F_Printf(NULL, "Couldn't find error log file.");
		}
		else
		{
			if (F_ChannelSize(chan) > 0) // error log file is not empty
			{
				F_Printf(NULL, "\nPUBLISH LOG:\n");
				while(!F_ChannelEof(chan))
				{
					numread = F_ChannelRead(ptr, sizeof(UCharT), 
					BUFFERSIZE-1, chan);
					ptr[numread] = '\0';
					F_Printf(NULL, "%s\n", (StringT)ptr);
				}
				if (retVal < 0) // error while transforming
				{
					F_ApiAlert("There was an error. Check console for details.", FF_ALERT_CONTINUE_WARN);
				}
				else // everything is ok
				{
					F_Sprintf(statusMessage, "Publishing to %s format succesfully completed, but there were several warnings.\nCheck console for details", format);
					F_ApiAlert(statusMessage, FF_ALERT_CONTINUE_NOTE);	
				}
			}
			else // error log is empty
			{
				if (retVal < 0) // very unlikely to happen, error and no cause logged
				{
					F_ApiAlert("Unhandled error happened.", FF_ALERT_CONTINUE_WARN);
				}
				else  // everything is perfectly fine
				{
					F_Sprintf(statusMessage, "Publishing to %s format succesfully completed.", format);
					F_ApiAlert(statusMessage, FF_ALERT_CONTINUE_NOTE);	
				}
			}
			F_ChannelClose(chan);
		}
		F_ApiDeallocateString(&tempResult);
	}
}
VoidT exportDocLineDoc()
{
	F_ObjHandleT bookID, docID, elemID;
	F_PropValsT params, *returnParams;
	IntT i, j, statusp;
	DirHandleT handle;
	FilePathT *filePath;
	StringT path, dirPath;
	F_AttributesT attrs;
	F_AttributeT attr;
	UIntT k;
	UCharT jarPath[256];
	StringT tempPath;

	bookID = F_ApiGetId(0,FV_SessionId,FP_ActiveBook);
	if (!bookID)
	{
		bookID = F_ApiGetId(0,FV_SessionId,FP_ActiveDoc);
		if (!isDocLine(bookID))
		{
			return;
		}
		path = F_ApiGetString(FV_SessionId,bookID,FP_Name);
		pathFilename(path);
		dirPath = F_Alloc(F_StrLen(path)+F_StrLen(defaultBookName)+5,NO_DSE);
		F_Sprintf(dirPath,"%s%s",path,defaultBookName);
		bookID = F_ApiSimpleOpen(dirPath,False);
	}
	else if (!F_StrISuffix(F_ApiGetString(FV_SessionId,bookID,FP_Name),defaultBookName))
	{
		return;
	}
	else
	{
		dirPath = F_ApiGetString(FV_SessionId,bookID,FP_Name);
	}
	if (!bookID)
	{
		F_Printf(NULL,"Invalid book\n");
		return;
	}
	pathFilename(dirPath);
	returnParams = NULL;
	params = F_ApiGetSaveDefaultParams();
	if (!params.len)
	{
		F_Printf(NULL,"Invalid default save params");
		return;
	}
	i = F_ApiGetPropIndex(&params,FS_FileType);
	params.val[i].propVal.u.ival = FV_SaveFmtXml;
	i = F_ApiGetPropIndex(&params,FS_StructuredSaveApplication);
	params.val[i].propVal.u.sval = "DocLine";
	generateBooks(bookID);
	filePath = F_PathNameToFilePath(dirPath,NULL,FDefaultPath); //may be FDosPath
	handle = F_FilePathOpenDir(filePath,&statusp);
	j = 0;
	while (filePath = F_FilePathGetNext(handle,&statusp))
	{
		path = F_FilePathToPathName(filePath,FDosPath);
		if (validateFilename(path,GENBOOK))
		{
			docID = F_ApiSimpleOpen(path,False);
			elemID = F_ApiGetId(FV_SessionId,docID,FP_HighestLevelElement);
			if (!elemID)
			{
				F_Printf(NULL,"No highest level elment in %s",path);
				continue;
			}
			attrs = F_ApiGetAttributes(docID,elemID);
			for (k=0; k<attrs.len; k++)
			{
				attr = attrs.val[k];
				if (F_StrIEqual(attr.name,(StringT)"FileName"))
				{
					if (!attr.values.len)
					{
						path = F_Realloc(path,F_StrLen(path)+F_StrLen(dirPath)+10,NO_DSE);
						F_Sprintf(path,"%sfile%d.drl",dirPath,j);
						j++;
					}
					else
					{
						path = F_Realloc(path,F_StrLen(path)+F_StrLen(dirPath)+F_StrLen(attr.values.val[0])+5,NO_DSE);
						F_Sprintf(path,"%s%s",dirPath,attr.values.val[0]);
					}
				}
			}
			F_ApiSave(docID,path,&params,&returnParams);
		}
	}
	//dirPath = "D:\\Tests2\\";
	tempPath = F_ApiClientDir();
	F_Sprintf(jarPath, "%s\\%s", tempPath, JAR_FILENAME);
//	F_Free(tempPath);

	callJavaExportUtil(jarPath,dirPath);

	//cleanDirectory(F_PathNameToFilePath(dirPath,NULL,FDosPath));
	//F_FilePathCloseDir(handle);
	//F_ApiDeallocateString(&path);
	//F_Free(filePath);
	//F_Free(&docID);
	//F_Free(&bookID);
	//F_ApiDeallocateString(&dirPath);
}

VoidT editHeader()
{
	F_ObjHandleT docId, bodyPageId, masterPageId, pageFrameId, textFrameId, paraHeaderId, headerVarId, varFmtId, nextParaId, highId, firstFlowId, edefId;
	F_TextLocT headerLoc;
	StringT headerText, idText, elemName;
	F_AttributesT attributes;
	UIntT j;
	/* get Id of the active document */
	docId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveDoc);
	/* get Id of the Right MasterPage*/
	masterPageId = F_ApiGetId(FV_SessionId, docId, FP_FirstMasterPageInDoc);
	/* Get Id of the first Page masterPage */
	masterPageId = F_ApiGetId(FV_SessionId, masterPageId, FP_PageNext);
	/* Get Id of the pageFrame of masterPage*/
	pageFrameId = F_ApiGetId(FV_SessionId, masterPageId, FP_PageFrame);
	/* Get Id of TextFrame of PageFrame */
	textFrameId = F_ApiGetId(FV_SessionId, pageFrameId, FP_FirstGraphicInFrame);
	/* Get Id of First Paragraph of TextFrame */
	paraHeaderId = F_ApiGetId(FV_SessionId, textFrameId, FP_FirstPgf);
	/* Insert new first paragraph and delete old first */
	nextParaId = F_ApiNewSeriesObject(docId, FO_Pgf, paraHeaderId);
	F_ApiDelete(docId, paraHeaderId);
	/* Create insertion point in new paragraph */
	headerLoc.objId = nextParaId;
	headerLoc.offset = 0;
	//headerLoc = F_ApiAddText(docId, &headerLoc, "Test text\'s header");
	/* Create new variable format for header string */
	varFmtId = F_ApiGetNamedObject(docId, FO_VarFmt, "userVarFormat");
	if (FA_errno == FE_NameNotFound)
	{
		varFmtId = F_ApiNewNamedObject(docId, FO_VarFmt, "userVarFormat");
		FA_errno = FE_Success;
	}
	bodyPageId = F_ApiGetId(FV_SessionId, docId, FP_FirstBodyPageInDoc);
	pageFrameId = F_ApiGetId(docId, bodyPageId, FP_PageFrame);
	textFrameId = F_ApiGetId(docId, pageFrameId, FP_FirstGraphicInFrame);
	firstFlowId = F_ApiGetId(docId, textFrameId, FP_Flow);
	highId = F_ApiGetId(docId, firstFlowId, FP_HighestLevelElement);
	edefId = F_ApiGetId(docId, highId, FP_ElementDef);
	elemName = F_ApiGetString(docId, edefId, FP_Name);
	//F_Printf(NULL, "%s\n", elemName);
	attributes = F_ApiGetAttributes(docId, highId);
	for(j=0; j<attributes.len; j++) {
		if (F_StrEqual("Name", attributes.val[j].name))
		{
			idText = F_StrCopyString(attributes.val[j].values.val[0]);
			F_StrStripTrailingSpaces(idText);
			F_StrStripLeadingSpaces(idText);
			break;
		}
	}
	headerText = F_StrCopyString(elemName);
	headerText = F_Realloc(headerText, F_StrLen(headerText)+F_StrLen(" <Emphasis>")+1, NO_DSE);
    F_StrCat(headerText," <Emphasis>");
		//F_Printf(NULL, "A%sA\n", idText);
	//F_Printf(NULL, "%s\n", headerText);
	if (!F_StrIsEmpty(idText))
	{
		headerText = F_Realloc(headerText, F_StrLen(headerText)+F_StrLen(idText)+1,NO_DSE);
        F_StrCat(headerText,idText);
		//F_Printf(NULL, "%s\n", headerText);
		//F_ApiPrintFAErrno();
	}
	F_StrStripTrailingSpaces(headerText);
	//F_ApiAlert(headerText,FF_ALERT_CONTINUE_NOTE);
	F_ApiSetString(docId, varFmtId, FP_Fmt, headerText);
	//F_ApiPrintFAErrno();
	/* Insert variable with created format into header */
	headerVarId = F_ApiNewAnchoredFormattedObject(docId, FO_Var, "userVarFormat", &headerLoc);
}
VoidT setAttributes(StringT idStr, StringT nameStr)
{
	F_ObjHandleT docId, bodyPageId, pageFrameId, textFrameId, firstFlowId, highId;
	F_AttributesT attributes;

	/* get Id of the active document */
	docId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveDoc);
	/* get Id of the BodyPage*/
	bodyPageId = F_ApiGetId(FV_SessionId, docId, FP_FirstBodyPageInDoc);
	/* Get Id of the pageFrame of masterPage*/
	pageFrameId = F_ApiGetId(docId, bodyPageId, FP_PageFrame);
	/* Get Id of TextFrame of PageFrame */
	textFrameId = F_ApiGetId(docId, pageFrameId, FP_FirstGraphicInFrame);
	/* Get Id of First Flow in TextFrame */
	firstFlowId = F_ApiGetId(docId, textFrameId, FP_Flow);
	/* Get Id of the highest-level element in document */
	highId = F_ApiGetId(docId, firstFlowId, FP_HighestLevelElement);
	/* Create F_AttributesT structure to set values*/
	attributes.len = 2;
	attributes.val = (F_AttributeT *)F_Alloc(2*sizeof(F_AttributeT), DSE);
	attributes.val[0].name = F_StrCopyString("Id");
	attributes.val[0].values.len = 1;
	attributes.val[0].values.val = (StringT *)F_Alloc(sizeof(StringT), DSE);
	attributes.val[0].values.val[0] = F_StrCopyString(idStr);
	attributes.val[1].name = F_StrCopyString("Name");
	attributes.val[1].values.len = 1;
	attributes.val[1].values.val = (StringT *)F_Alloc(sizeof(StringT), DSE);
	attributes.val[1].values.val[0] = F_StrCopyString(nameStr);
	/* Set proper values */
	F_ApiSetAttributes(docId, highId, &attributes);
}