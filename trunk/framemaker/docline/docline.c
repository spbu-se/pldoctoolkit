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
#define NEWIE 2
#define NEWIP 3
#define PRINT 4
#define BBOOK 5
#define BDOC 6
#define BCHECK 7
#define BPRINT 8
#define MAXSTRING 255
#define SAVEHT 101
#define SAVEPD 103
#define OPEN 105
#define TESTOPEN 106
#define IMPORT 107
#define FM 200
#define DRL 201
#define FMBOOK 202
#define INFELEM 203
#define EXPORT 998
#define CLOSE 999
#define in ((MetricT) 65536*72)
#define defaultPath  (F_StrCopyString("C:\\"))
#define defaultBookName (F_StrCopyString("mainDRLFMBook.book"))

F_ObjHandleT menubarId, menuId; // !MakerMainMenu and Docline menus
F_ObjHandleT newMenuId; // "New" submenu in the Docline menu
F_ObjHandleT openMenuId; // "Open" submenu in the Docline menu
F_ObjHandleT saveMenuId; // "Save As..." submenu in the Docline menu
F_ObjHandleT newProjectId, separatorId, newInfElemId, newInfProdId, cmd4Id; // Commands from the "New" submenu
F_ObjHandleT saveDoclineAsHtmlId, saveDoclineAsPdfId; // Commands from the "Save As..." submenu
//F_ObjHandleT addInfElemId, addInfProdId; // Commands from the "Add" submenu
F_ObjHandleT openFmID, importDrlID, bmenubarId, bmenuId, bnewmenuId, bcmd1Id, bcmd2Id, bcmd3Id, bcmd4Id;
F_ObjHandleT closeProjectId; // menu item for closing active project and all files in it
F_ObjHandleT exportProjectId; // menu item for exporting active project back to DRL

VoidT createNewDocLineBook();
VoidT addNewDocLineDoc();
VoidT openBook();
VoidT importDocLineDoc();
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
	/* Add seperator after the New docline project command*/
	separatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "NewSeparator");
	F_ApiAddCommandToMenu(newMenuId, separatorId);
	/* Define "New *SpecificElement*" commands and add them to the "New" menu. */
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
	/* Define and add the New-> menu to the DocLine menu. */
	bnewmenuId = F_ApiDefineAndAddMenu(bmenuId, "BNewDocLineMenu", "Add");
	/* Define some commands and add them to the New-> menu. */
	//bcmd1Id = F_ApiDefineAndAddCommand(BBOOK, bnewmenuId, "BNewDocLineBook", "Book", "\\!BNB");
	bcmd2Id = F_ApiDefineAndAddCommand(BDOC, bnewmenuId, "BNewDocLineDocument", "Element", "\\!BND");
	/* Define some commands and add them to the DocLine menu. */
	bcmd3Id = F_ApiDefineAndAddCommand(BCHECK, bmenuId, "BCheckGrammar", "Second command", "\\!BCG");
	bcmd4Id = F_ApiDefineAndAddCommand(BPRINT, bmenuId, "BPrintErrors", "Third command", "\\!BPE");
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
  case BDOC:
	addNewDocLineDoc();
	break;
  case OPEN:
      openBook();
      break;
  case IMPORT:
      importDocLineDoc();
      break;
  }
}

//Converts full path to path of directory
VoidT pathFilename(UCharT *str)
{
    F_ApiAlert(str,FF_ALERT_CONTINUE_NOTE);
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
                    return (!(F_StrIEqual((StringT)str,(StringT)".backup.fm")));
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
            return (F_StrIEqual((StringT)str,(StringT)".book"));
            break;
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

//Common part of Open and Import
VoidT openFilesInDirectory(StringT path)
{
    FilePathT *newpath, *file;
    StringT bookPath, tmpPath, tmpPath2, place, fileName;
    //path - path of openned document
    //newpath~path
    //bookPath - book path
    //defaultPath - default directory path
    F_ObjHandleT bookID, compID, elemID;
    DirHandleT handle;
    IntT statusp, statusp2;
    BoolT bookExists, compExists;
    F_ElementLocT elemLoc;

    pathFilename(path);
    bookPath = "";
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
        while ((!bookExists)&&((file = F_FilePathGetNext(handle, &statusp)) != NULL))
        {
            F_Free(bookPath);
            bookPath = F_FilePathToPathName(file, FDosPath);
            if (F_StrIEqual(fileFileName(bookPath),defaultBookName))
            {
                bookExists = True;
            }
            F_FilePathFree(file);
        }   
        F_FilePathCloseDir(handle);
        handle = F_FilePathOpenDir(newpath, &statusp2);
        if (!bookExists)
        {
            bookID = F_ApiSimpleOpen("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_book_template.book",False);
            bookPath = F_Alloc(F_StrLen(path)+F_StrLen(defaultBookName)+1,NO_DSE);
            bookPath = path;
            F_StrCat(bookPath,defaultBookName);    
        }
        else
        {
            bookID = F_ApiSimpleOpen(bookPath,False);
        }
        while((file = F_FilePathGetNext(handle, &statusp2)) != NULL)
        {
            tmpPath = F_FilePathToPathName(file, FDosPath);
            if (validateFilename(tmpPath,FM) == True) 
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
	bookId = F_ApiSimpleOpen("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_book_template.book",False);
	// proper path must be here
	path = F_StrCopyString("D:\\framemaker\\newproject\\");
	bookPath = F_Alloc(F_StrLen(path)+F_StrLen(defaultBookName)+1,NO_DSE);
    bookPath = path;
    F_StrCat(bookPath,defaultBookName);
	F_ApiSimpleSave(bookId, bookPath, False);
    F_ApiDeallocateString(&path);
    F_ApiDeallocateString(&bookPath);
}

VoidT addNewDocLineDoc()
{
	F_ObjHandleT bookId, elemId;
	F_ElementLocT elemLoc;
	
	bookId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveBook);
	elemLoc.childId = F_ApiGetId(FV_SessionId, bookId, FP_HighestLevelElement);
	elemLoc.parentId = 0;
	elemLoc.offset = 0;
	/* Insert the new element. */
	elemId = F_ApiNewBookComponentInHierarchy(bookId, "Chapter1", &elemLoc);
	/*if (FP_HighestLevelElement)
	{
		F_ApiAlert("The book is structured!", FF_ALERT_CONTINUE_NOTE);
	}*/
	if (FA_errno == FE_BookUnStructured) 
	{
		F_ApiAlert("The book is unstructured! Can't insert new Book Component.", FF_ALERT_CONTINUE_NOTE);
	}
}


VoidT importDocLineDoc()
{
    F_ObjHandleT docID, fileID;
    StringT path, tmpPath, bookPath;
    FilePathT *newpath, *file;
    DirHandleT handle;
    IntT statusp, statusp2, i;
    F_PropValsT params, *returnParams;
    //Do not use russian letters in path!!!
    docID = F_ApiSimpleOpen(defaultPath,True);
    if (docID != 0)
    {
        path = F_ApiGetString(FV_SessionId,docID,FP_Name);
        pathFilename(path);
        bookPath = "";
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
            returnParams = NULL;
            params = F_ApiGetOpenDefaultParams();
            if (params.len == 0)
            {
                F_ApiAlert("Default params error",FF_ALERT_CONTINUE_NOTE);
                return;
            }
            else
            {
                i = F_ApiGetPropIndex(&params,FS_StructuredOpenApplication);
                params.val[i].propVal.u.ival = "DocLine";
                i = F_ApiGetPropIndex(&params, FS_UseAutoSaveFile);
                params.val[i].propVal.u.ival = FV_DoYes;
            }
            while((file = F_FilePathGetNext(handle, &statusp2)) != NULL)
            {
                tmpPath = F_FilePathToPathName(file,FDosPath);
                if (validateFilename(tmpPath,DRL))
                {
                    F_ApiAlert(tmpPath,FF_ALERT_CONTINUE_NOTE);
                    fileID = F_ApiOpen(tmpPath,&params,&returnParams);
                    F_ApiDeallocatePropVals(returnParams);
                }
                F_Free(tmpPath);
                F_FilePathFree(file);
            }
            F_ApiDeallocatePropVals(&params);    
            F_FilePathCloseDir(handle);
        }
        openFilesInDirectory(path);
        F_FilePathFree(file);
        F_FilePathFree(newpath);
        F_ApiDeallocateString(&path);
        F_ApiDeallocateString(&bookPath);
        //F_ApiDeallocateString(&tmpPath);
        //F_ApiDeallocateString(&tmpPath2);
    }
}