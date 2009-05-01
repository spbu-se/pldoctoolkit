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
#include "fdetypes.h"
#include "futils.h"
#include "fstrings.h"
#include "fprogs.h"
#include "fmemory.h"

#include "cutils.h"

#include "string.h"
#include "ctype.h"


#define BOOK 1
#define NEWDC 2
#define NEWDI 3
#define NEWDT 4
#define NEWIE 5
#define NEWIP 6
#define BBOOK 11
#define BNEWDC 12
#define BNEWDI 13
#define BNEWDT 14
#define BNEWIE 15
#define BNEWIP 16
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

#define EXPORT 901
#define CLOSE 902
#define BEXPORT 911
#define BCLOSE 912
#define in ((MetricT) 65536*72)
#define defaultPath  (F_StrCopyString("C:\\"))
#define defaultBookName (F_StrCopyString("mainDRLFMBook.book"))

F_ObjHandleT menubarId, menuId; // !MakerMainMenu and Docline menus
F_ObjHandleT newMenuId; // "New" submenu in the Docline menu
F_ObjHandleT openMenuId; // "Open" submenu in the Docline menu
F_ObjHandleT saveMenuId; // "Save As..." submenu in the Docline menu
F_ObjHandleT newProjectId, separatorId, newDictId, newDirectId, newDirTempId, newInfElemId, newInfProdId; // Commands from the "New" submenu
F_ObjHandleT saveDoclineAsHtmlId, saveDoclineAsPdfId; // Commands from the "Save As..." submenu
F_ObjHandleT openFmID, importDrlID; // open exising fm docline project, import project from existing drl documentation
F_ObjHandleT closeProjectId; // menu item for closing active project and all files in it
F_ObjHandleT exportProjectId; // menu item for exporting active project back to DRL

/* All the same for !BookMainMenu */
F_ObjHandleT bmenubarId, bmenuId; // !BookMainMenu and Docline menus
F_ObjHandleT bnewMenuId; // "New" submenu in the Docline menu
F_ObjHandleT bopenMenuId; // "Open" submenu in the Docline menu
F_ObjHandleT bsaveMenuId; // "Save As..." submenu in the Docline menu
F_ObjHandleT bnewProjectId, bseparatorId, bnewDictId, bnewDirectId, bnewDirTempId, bnewInfElemId, bnewInfProdId; // Commands from the "New" submenu
F_ObjHandleT bsaveDoclineAsHtmlId, bsaveDoclineAsPdfId; // Commands from the "Save As..." submenu
F_ObjHandleT bopenFmID, bimportDrlID; // open exising fm docline project, import project from existing drl documentation
F_ObjHandleT bcloseProjectId; // menu item for closing active project and all files in it
F_ObjHandleT bexportProjectId; // menu item for exporting active project back to DRL

F_ObjHandleT firstAddedId[5];

VoidT createNewDocLineBook();
VoidT newDocCoreChild(IntT type);
VoidT openBook();
VoidT importDocLineDoc();
VoidT exportDocLineDoc();
VoidT closeProject();
IntT test;
IntT dictionCount=0, directCount=0, dirTempCount=0, infElemCount=0, infProdCount=0;

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
	  /* Add seperator after the New docline project command*/
	  separatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "NewSeparator");
	  F_ApiAddCommandToMenu(newMenuId, separatorId);
	  /* Define "New *SpecificElement*" commands and add them to the "New" menu. */
	  newDictId = F_ApiDefineAndAddCommand(NEWDC, newMenuId, "NewDictionary", "Dictionary", "\\!ND");
	  newDirectId = F_ApiDefineAndAddCommand(NEWDI, newMenuId, "NewDirectory", "Directory", "\\!DI");
	  newDirTempId = F_ApiDefineAndAddCommand(NEWDT, newMenuId, "NewDirTemplate", "DirTemplate", "\\!DT");
	  newInfElemId = F_ApiDefineAndAddCommand(NEWIE, newMenuId, "NewInfElement", "InfElement", "\\!NE");
	  newInfProdId = F_ApiDefineAndAddCommand(NEWIP, newMenuId, "NewInfProduct", "InfProduct", "\\!NP");

	  /* Define and add the Open-> menu to the DocLine menu. */
	  openMenuId = F_ApiDefineAndAddMenu(menuId, "OpenDocLineMenu", "Open");
	  /* Define some commands and add them to the Open-> menu. */
	  openFmID = F_ApiDefineAndAddCommand(OPEN, openMenuId, "OpenDoclineProject", "Docline project", "\\!IW");

	  /* Define command for importing DocLine project from existing DRL documentation. */
	  importDrlID = F_ApiDefineAndAddCommand(IMPORT, menuId, "Import", "Import","\\!OO");

	  /* Define and add the Save As...-> menu to the DocLine menu. */
	  saveMenuId = F_ApiDefineAndAddMenu(menuId, "SaveDocLineMenu", "Save Project As...");
	  /* Define some commands and add them to the Save As...-> menu. */
	  saveDoclineAsHtmlId = F_ApiDefineAndAddCommand(SAVEHT, saveMenuId, "SaveDoclineAsHtml", "HTML", "\\!HT");
	  saveDoclineAsPdfId = F_ApiDefineAndAddCommand(SAVEPD, saveMenuId, "SaveDoclineAsPdf", "PDF", "\\!PD");

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
	  bseparatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "BNewSeparator");
	  F_ApiAddCommandToMenu(bnewMenuId, bseparatorId);
	  /* Define "New *SpecificElement*" commands and add them to the "New" menu. */
	  bnewDictId = F_ApiDefineAndAddCommand(BNEWDC, bnewMenuId, "BNewDictionary", "Dictionary", "\\!BND");
	  bnewDirectId = F_ApiDefineAndAddCommand(BNEWDI, bnewMenuId, "BNewDirectory", "Directory", "\\!BDI");
	  bnewDirTempId = F_ApiDefineAndAddCommand(BNEWDT, bnewMenuId, "BNewDirTemplate", "DirTemplate", "\\!BDT");
	  bnewInfElemId = F_ApiDefineAndAddCommand(BNEWIE, bnewMenuId, "BNewInfElement", "InfElement", "\\!BNE");
	  bnewInfProdId = F_ApiDefineAndAddCommand(BNEWIP, bnewMenuId, "BNewInfProduct", "InfProduct", "\\!BNP");

	  /* Define and add the Open-> menu to the DocLine menu. */
	  bopenMenuId = F_ApiDefineAndAddMenu(bmenuId, "BOpenDocLineMenu", "Open");
	  /* Define some commands and add them to the Open-> menu. */
	  bopenFmID = F_ApiDefineAndAddCommand(BOPEN, bopenMenuId, "BOpenDoclineProject", "Docline project", "\\!BIW");

	  /* Define command for importing DocLine project from existing DRL documentation. */
	  bimportDrlID = F_ApiDefineAndAddCommand(BIMPORT, bmenuId, "BImport", "Import","\\!BOO");

	  /* Define and add the Save As...-> menu to the DocLine menu. */
	  bsaveMenuId = F_ApiDefineAndAddMenu(bmenuId, "BSaveDocLineMenu", "Save Project As...");
	  /* Define some commands and add them to the Save As...-> menu. */
	  bsaveDoclineAsHtmlId = F_ApiDefineAndAddCommand(BSAVEHT, bsaveMenuId, "BSaveDoclineAsHtml", "HTML", "\\!BHT");
	  bsaveDoclineAsPdfId = F_ApiDefineAndAddCommand(BSAVEPD, bsaveMenuId, "BSaveDoclineAsPdf", "PDF", "\\!BPD");

	  /* Define command for exporting active project back to DRL */
	  bexportProjectId = F_ApiDefineAndAddCommand(BEXPORT, bmenuId, "BExportProject", "Export","\\!BEP");

	  /* Define command for closing active project and all files in it */
	  closeProjectId = F_ApiDefineAndAddCommand(BCLOSE, bmenuId, "BCloseProject", "Close Project","\\!BCP");
	}
} 

VoidT F_ApiCommand(IntT command)
{
	/*F_ObjHandleT docId;

	get the ID of the active document */
	/*docId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveDoc);
	if (!docId) {
	F_ApiAlert((StringT) "Please open a document before invoking this command.", 
	FF_ALERT_CONTINUE_WARN);
	return;
	}*/

	/* Setting commands for handling menu items */
	switch(command) {
  case BOOK:
	  createNewDocLineBook();
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
  case OPEN:
	  openBook();
	  break;
  case IMPORT:
	  importDocLineDoc();
	  break;
  case BEXPORT:
	  exportDocLineDoc();
	  break;
  case BCLOSE:
	  closeProject();
	  break;
	}
}

//Converts full path to path of directory
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

//Validates type of file
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


//Converts full path to file name
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

//Common part of Open and Import
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
	IntT statusp, statusp2, statusp3;
	BoolT bookExists, compExists;
	F_ElementLocT elemLoc;

	pathFilename(path);
	newpath = F_PathNameToFilePath (path, NULL, FDosPath);
	handle = F_FilePathOpenDir(newpath, &statusp);
	if (handle == 0) 
	{
		F_FilePathFree(newpath);
		F_ApiAlert("Handle Error0", FF_ALERT_CONTINUE_NOTE);
		return;
	}
	else
	{
		bookExists = False;
		while ((file = F_FilePathGetNext(handle, &statusp)) != NULL)
		{
			tmpBookPath = F_FilePathToPathName(file, FDosPath);
			if (F_StrIEqual(fileFileName(tmpBookPath),defaultBookName))
			{
				bookExists = True;
				bookPath = F_StrCopyString(tmpBookPath);
			}
			else if (F_StrSuffix(bookPath,".fm.lck"))
			{
				F_DeleteFile(file);
			}
			F_FilePathFree(file);
			F_ApiDeallocateString(&tmpBookPath);
		} 
		F_FilePathCloseDir(handle);
		if (!bookExists)
		{
			bookID = F_ApiSimpleOpen("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_book_template.book",False);
			//bookPath = F_Alloc(F_StrLen(path)+F_StrLen(defaultBookName)+1,NO_DSE);
			bookPath = F_StrCopyString(path);
			bookPath = F_Realloc(bookPath,F_StrLen(path)+F_StrLen(defaultBookName)+1,NO_DSE);
			F_StrCat(bookPath,defaultBookName);
			handle = F_FilePathOpenDir(F_PathNameToFilePath(path,NULL,FDosPath),&statusp3);
			//F_ApiAlert(bookPath,FF_ALERT_CONTINUE_NOTE);
			if (!handle)
			{
				F_ApiAlert("Handle error 1",FF_ALERT_CONTINUE_NOTE);
				return;
			}
			while (file = F_FilePathGetNext(handle,&statusp3))
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
		handle = F_FilePathOpenDir(newpath, &statusp2);
		while((file = F_FilePathGetNext(handle, &statusp2)) != NULL)
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
					fileName = fileFileName(tmpPath);
					if ((F_StrPrefix(fileName,(StringT)"inf_element")) ||
						(F_StrPrefix(fileName,(StringT)"inf_product")))
					{
						place = (StringT)"DocumentationCore";
					}
					else if (F_StrPrefix(fileName,(StringT)"final_inf_product"))
					{
						place = (StringT)"ProductDocumentation";
					}
					else if (F_StrPrefix(fileName,(StringT)"product"))
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
						if (!compID)
						{
							F_ApiAlert("Highest element error",FF_ALERT_CONTINUE_NOTE);
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
				(!F_StrSuffix(fileFileName(tmpPath),(StringT)".drl"))&&
				(!F_StrSuffix(fileFileName(tmpPath),(StringT)".book"))&&//книги удал€ютс€
				(!F_StrSuffix(fileFileName(tmpPath),(StringT)".backup.fm")))
			{
				F_DeleteFile(file);
			}
			F_Free(tmpPath);
			F_FilePathFree(file);
		}
		F_ApiSimpleGenerate(bookID,False,True);
		F_ApiSimpleSave(bookID,bookPath,False); 
		//F_FilePathCloseDir(handle);
	}
	F_FilePathFree(file);
	//F_FilePathFree(newpath);
	F_ApiDeallocateString(&path);
	F_ApiDeallocateString(&bookPath);
	F_ApiDeallocateString(&tmpPath);
	F_ApiDeallocateString(&tmpPath2);
}

//Generates books for second-level elements
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
	if (docID != 0)
	{
		path = F_FilePathToPathName(F_PathNameToFilePath(F_ApiGetString(FV_SessionId,docID,FP_Name),NULL,FDefaultPath),FDosPath);
		openFilesInDirectory(path);
	}
}
VoidT createNewDocLineBook()
{
	F_ObjHandleT bookId;
	StringT path, bookPath;
	IntT err;
	/* Choose workspace for new docline project */
	err = F_ApiChooseFile(&path, "Choose directory to save new docline project", "", "", FV_ChooseOpenDir, "");
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
	/*F_ObjHandleT bookId, compId, docId;
	StringT tmpPath;
	IntT response;
	/* Get Id and path of the book */
	/*bookId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveBook);
	compId = F_ApiGetId(FV_SessionId, bookId, FP_FirstComponentInBook);
	while (compId != 0)
	{
	tmpPath = F_ApiGetString(bookId, compId, FP_Name);
	docId = F_ApiSimpleOpen(tmpPath, False);
	/* See whether document has been modified. */
	/*	if (F_ApiGetInt(FV_SessionId, docId, FP_DocIsModified))
	response = F_ApiAlert("Document was changed. Close it anyway?",FF_ALERT_OK_DEFAULT);
	if (!response)
	F_ApiClose (docId, FF_CLOSE_MODIFIED);
	compId = F_ApiGetId(bookId, compId, FP_NextComponentInBook);
	}*/
}

VoidT newDocCoreChild(IntT type)
{
	F_ObjHandleT bookId, docId, childEdefId, compId, elemId;
	F_ElementLocT elemLoc;
	StringT bookPath, savePath, edefName;
	IntT len, i, j; 
	BoolT compExists;

	/* Get Id and path of the book */
	bookId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveBook);
	bookPath = F_ApiGetString(FV_SessionId, bookId, FP_Name);
	pathFilename(bookPath);
	/* Choose what type of element we add*/
	switch (type)
	{
	case DICTION:
		dictionCount++;
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen("dictionary.fm")+5, NO_DSE);
		len = F_Sprintf(savePath, "%sdictionary%d.fm", (StringT)bookPath, (IntT)dictionCount);
		edefName = F_StrCopyString("Dictionary");
		j = 0;
		break;
	case DIRECT:
		directCount++;
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen("directory.fm")+5, NO_DSE);
		len = F_Sprintf(savePath, "%sdirectory%d.fm", (StringT)bookPath, (IntT)directCount);
		edefName = F_StrCopyString("Directory");
		j = 1;
		break;
	case DIRTEMP:
		dirTempCount++;
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen("dir_template.fm")+5, NO_DSE);
		len = F_Sprintf(savePath, "%sdir_template%d.fm", (StringT)bookPath, (IntT)dirTempCount);
		edefName = F_StrCopyString("DirTemplate");
		j = 2;
		break;
	case INFELEM:
		infElemCount++;
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen("inf_element.fm")+5, NO_DSE);
		len = F_Sprintf(savePath, "%sinf_element%d.fm", (StringT)bookPath, (IntT)infElemCount);
		edefName = F_StrCopyString("InfElement");
		j = 3;
		break;
	case INFPROD:
		infProdCount++;
		savePath = F_Alloc(F_StrLen(bookPath)+F_StrLen("inf_product.fm")+5, NO_DSE);
		len = F_Sprintf(savePath, "%sinf_product%d.fm", (StringT)bookPath, (IntT)infProdCount);
		edefName = F_StrCopyString("InfProduct");
		j = 4;
		break;
	}
	/* First create an 8.5 x 11 custom document. */
	docId = F_ApiCustomDoc(F_MetricFractMul(in,17,2), 11*in, 1, F_MetricFractMul(in,1,4), in, in, in, in, FF_Custom_SingleSided, True);
	/* Import EDD from the current book */
	F_ApiSimpleImportElementDefs(docId, bookId, FF_IED_REMOVE_OVERRIDES);
	/* Get ID of the inserting element definitions */
	childEdefId = F_ApiGetNamedObject(docId, FO_ElementDef, edefName);
	/* Insert new Highest-level element into the document, i.e. InfElement, InfProduct, etc. */
	F_ApiWrapElement(docId, childEdefId);
	/* Save the doc with the specific name */
	F_ApiSimpleSave(docId, savePath, False);
	/* Make a component */
	compId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
	if (!compId)
	{
		F_ApiAlert("Highest element error",FF_ALERT_CONTINUE_NOTE);
	}
	else
	{
		/* Check if DocumentationCore section exists */
		compId = F_ApiGetId(bookId, compId, FP_FirstChildElement);
		compExists = False;
		while (compId && (!compExists))
		{
			elemId = F_ApiGetId(bookId, compId, FP_ElementDef);
			if (F_StrIEqual(F_ApiGetString(bookId, elemId, FP_Name),"DocumentationCore"))
			{
				compExists = True;
			}
			else
			{
				compId = F_ApiGetId(bookId,compId,FP_NextSiblingElement);
			}
		}
		if (!compExists) // There is no "DocumentationCore" section
		{
			/* Create DocumentationCore section */
			elemId = F_ApiGetNamedObject(bookId, FO_ElementDef, "DocumentationCore");
			elemLoc.parentId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
			elemLoc.childId = 0;
			elemLoc.offset = 0;
			compId = F_ApiNewElementInHierarchy(bookId, elemId, &elemLoc);
		}
		/* Insert Book component in DocumentationCore section*/
		i = j + 1; // j-type of element. 0 for Dictionary, 1 for Directory, 2 for DirTemplate, 3 for InfElement, 4 for InfProduct
		/* Find element to insert our element before*/
		while ((i<5) && (!firstAddedId[i])) i++;
		if (i == 5)
			elemLoc.childId = 0;
		else
			elemLoc.childId = firstAddedId[i];
		elemLoc.parentId = compId;
		elemLoc.offset = 0;
		compId = F_ApiNewBookComponentInHierarchy(bookId, savePath, &elemLoc);
		if (!firstAddedId[j])
			firstAddedId[j] = compId;
	}
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
	params.val[i].propVal.u.ival = "DocLine";
	if (!docID)
	{
		F_Printf(NULL,"No such file: %s\n", defaultPath);
		return;
	}
	dirPath = F_ApiGetString(FV_SessionId,docID,FP_Name);
	F_ApiClose(docID,FF_CLOSE_MODIFIED);
	pathFilename(dirPath);
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
			compID = F_ApiGetId(FV_SessionId,fileID,FP_FirstComponentInBook);
			while (compID)
			{
				compPath = F_ApiGetString(fileID,compID,FP_Name);
				attrs = F_ApiGetAttributes(fileID,F_ApiGetId(fileID,compID,FP_ComponentElement));
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
						F_ApiSetString(fileID,compID,FP_Name,newCompPath);
						F_ApiDeallocateString(&newCompPath);
					}
				}
				compID = F_ApiGetId(fileID,compID,FP_NextComponentInBook);
			}
			fileID = F_ApiSimpleSave(fileID,F_ApiGetString(FV_SessionId,fileID,FP_Name),False);
			F_ApiClose(fileID,FF_CLOSE_MODIFIED);
			F_ApiDeallocatePropVals(returnParams);
		}
		F_Free(tmpPath);
		F_FilePathFree(file);
	}
	//handle = F_FilePathOpenDir(dirPath,&statusp);
	//while (file = F_FilePathGetNext)
	F_ApiDeallocatePropVals(&params);    
	F_FilePathCloseDir(handle);
	openFilesInDirectory(dirPath);
	F_FilePathFree(file);
	F_FilePathFree(newdirPath);
	F_ApiDeallocateString(&dirPath);
	F_ApiDeallocateString(&bookPath);
	//F_ApiDeallocateString(&tmpPath);
	//F_ApiDeallocateString(&tmpPath2);
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

	bookID = F_ApiGetId(0,FV_SessionId,FP_ActiveBook);
	if (!bookID)
	{
		F_ApiAlert("Not book!",FF_ALERT_CONTINUE_NOTE);
		return;
	}
	returnParams = NULL;
	params = F_ApiGetSaveDefaultParams();
	if (!params.len)
	{
		F_ApiAlert("Invalid default save params",FF_ALERT_CONTINUE_NOTE);
		return;
	}
	i = F_ApiGetPropIndex(&params,FS_FileType);
	params.val[i].propVal.u.ival = FV_SaveFmtXml;
	generateBooks(bookID);
	dirPath = F_ApiGetString(FV_SessionId,bookID,FP_Name);
	pathFilename(dirPath);
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
			F_Printf(NULL,"%s\n", path);
			F_ApiSave(docID,path,&params,&returnParams);
		}
	}
	F_FilePathCloseDir(handle);
	F_ApiDeallocateString(&path);
	F_Free(filePath);
	F_Free(&docID);
	F_Free(&bookID);
	F_ApiDeallocateString(&dirPath);
}