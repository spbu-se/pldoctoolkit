#include "common.h"

BoolT getMainBookTemplate(StringT *path)
{
	StringT tmplPath, fmDir, tmplName;

	fmDir = F_StrCopyString(F_ApiGetString(0,FV_SessionId,FP_FM_HomeDir));
	tmplName = F_StrCopyString((StringT)"\\structure\\xml\\docline\\docline_book_template.book");
	tmplPath = (StringT)F_Alloc((F_StrLen(fmDir)+F_StrLen(tmplName)+1)*sizeof(UCharT),NO_DSE);
	F_Sprintf(tmplPath,"%s%s",F_StrCopyString(fmDir),F_StrCopyString(tmplName));
	*path = F_StrCopyString(tmplPath);

	F_ApiDeallocateString(&tmplPath);
	F_ApiDeallocateString(&tmplName);
	F_ApiDeallocateString(&fmDir);

	return TRUE;
}

BoolT getMainBookEDD(StringT *path)
{
	StringT tmplPath, fmDir, tmplName;

	fmDir = F_StrCopyString(F_ApiGetString(0,FV_SessionId,FP_FM_HomeDir));
	tmplName = F_StrCopyString((StringT)"\\structure\\xml\\docline\\docline_edd.fm");
	tmplPath = (StringT)F_Alloc((F_StrLen(fmDir)+F_StrLen(tmplName)+1)*sizeof(UCharT),NO_DSE);
	F_Sprintf(tmplPath,"%s%s",F_StrCopyString(fmDir),F_StrCopyString(tmplName));
	*path = F_StrCopyString(tmplPath);

	F_ApiDeallocateString(&tmplPath);
	F_ApiDeallocateString(&tmplName);
	F_ApiDeallocateString(&fmDir);

	return TRUE;
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

BoolT cleanDirectory(FilePathT *dirPath)
{
	FilePathT *file;
	StringT path;
	DirHandleT handle;
	IntT statusp;

	handle = F_FilePathOpenDir(dirPath,&statusp);
	if (!handle)
	{
		F_Printf(NULL,"Invalid directory path: %s\n",F_FilePathToPathName(dirPath,FDosPath));
		writeToChannel("Error. Invalid directory path");
		return FALSE;
	}
	while (file = F_FilePathGetNext(handle,&statusp))
	{
		path = F_FilePathToPathName(file,FDosPath);
		path = fileFileName(path);
		if ((!validateFilename(path,FM) || F_StrISuffix(path,(StringT)".backup.fm")
				|| F_StrISuffix(path,(StringT)".recover.fm"))//documents, which are not backup or recover
			&&(!validateFilename(path,DRL) || F_StrSuffix(path,(StringT)".backup.drl"))//drl documents, which are not recover
			&&(!F_StrIEqual(path,defaultBookName)))//main book
		{
			F_DeleteFile(file);
		}
		F_ApiDeallocateString(&path);
	}
	F_FilePathCloseDir(handle);
	F_FilePathFree(file);
	//F_Free(&handle);
	//F_Free(&statusp);

	return TRUE;
}
BoolT validateFilename(StringT str, IntT type)
{
	StringT name;
	BoolT isBook;
	switch (type)
	{
	case FM:
		return F_StrISuffix(str,(StringT)".fm")
			&& !F_StrISuffix(str,(StringT)".backup.fm")
			&& !F_StrISuffix(str,(StringT)".recover.fm");
	case FMBOOK:
		return F_StrISuffix(str,(StringT)".book")
			&& !F_StrISuffix(str,(StringT)".backup.book")
			&& !F_StrISuffix(str,(StringT)".recover.book");
	case GENBOOK:
		name = str;
		name = fileFileName(str);
		//checking highest level element in book is better choise, but it decreases perfomance
		isBook = validateFilename(name,FMBOOK) && F_StrIPrefix(name,(StringT)"book");

		F_ApiDeallocateString(&name);

		return isBook;
	case DRL:
		return F_StrISuffix(str,(StringT)".drl")
			&& !F_StrISuffix(str,(StringT)".backup.drl");
	default:
		return False;
	}
}

F_ObjHandleT openMainBook(StringT path)
{
	StringT bookPath, tmplPath;
	F_ObjHandleT bookID;

	bookPath = F_StrCopyString(path);
	bookPath = (StringT)F_Realloc(bookPath,(F_StrLen(path)+F_StrLen(defaultBookName)+1)*sizeof(UCharT),NO_DSE);
	F_StrCat(bookPath,defaultBookName);
	tmplPath = F_StrCopyString("");
	getMainBookTemplate(&tmplPath);
	bookID = F_ApiSimpleOpen(tmplPath,FALSE);
	F_ApiSimpleSave(bookID,bookPath,FALSE);

	F_ApiDeallocateString(&bookPath);
	F_ApiDeallocateString(&tmplPath);

	return bookID;
}
StringT getPlace(StringT fileName)
{
	if ((F_StrIEqual(fileName,(StringT)"InfElement")) ||
		(F_StrPrefix(fileName,(StringT)"InfProduct")))
	{
		return (StringT)"DocumentationCore";
	}
	else if (F_StrIEqual(fileName,(StringT)"FinalInfProduct"))
	{
		return (StringT)"ProductDocumentation";
	}
	else if (F_StrIEqual(fileName,(StringT)"Product"))
	{
		return (StringT)"ProductLine";
	}
	else if ((F_StrIEqual(fileName,(StringT)"DocumentationCore"))
		||((F_StrIEqual(fileName,(StringT)"ProductDocumentation")))
		||(F_StrIEqual(fileName,(StringT)"ProductLine")))
	{
		return (StringT)"DocLine";
	}
	else
	{
		return (StringT)"";
	}
}
F_ObjHandleT componentExists(F_ObjHandleT bookID, F_ObjHandleT cID, StringT name)
{
	F_ObjHandleT compID, elemID;

	compID = F_ApiGetId(bookID,cID,FP_FirstChildElement);
	while (compID)
	{
		elemID = F_ApiGetId(bookID,compID,FP_ElementDef);
		if (F_StrIEqual(F_ApiGetString(bookID,elemID,FP_Name),name))
		{
			//F_Free(&elemID);
			return compID;
		}
		else
		{
			compID = F_ApiGetId(bookID,compID,FP_NextSiblingElement);
		}
	}

	//F_Free(&elemID);
	//F_Free(&compID);

	return 0;
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
StringT fileFileName(UCharT *str)
{
	StringT tmpStr;

	tmpStr = F_StrCopyString(str);
	while (*tmpStr)
	{
		*tmpStr++;
	}
	tmpStr--;
	while ((*tmpStr)&&(*tmpStr != '\\'))
	{
		*tmpStr--;
	}
	*tmpStr++;
	return F_StrCopyString(tmpStr);
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
		writeToChannel("Error. Document not opened.\n");
		return F_StrCopyString("");
	}
	return F_ApiGetString(docID,F_ApiGetId(docID,F_ApiGetId(docID,F_ApiGetId(FV_SessionId,docID,FP_MainFlowInDoc),FP_HighestLevelElement),FP_ElementDef),FP_Name);
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
IntT getActiveBookID()
{
	IntT bookID;
	StringT dirPath, path;

	bookID = F_ApiGetId(0,FV_SessionId,FP_ActiveBook);
	//Active document is not framemaker book
	if (!bookID)
	{
		bookID = F_ApiGetId(0,FV_SessionId,FP_ActiveDoc);
		//Active document is not docline document
		if (!isDocLine(bookID))
		{
			return 0;
		}
		path = F_ApiGetString(FV_SessionId,bookID,FP_Name);
		pathFilename(path);
		dirPath = F_Alloc(F_StrLen(path)+F_StrLen(defaultBookName)+5,NO_DSE);
		F_Sprintf(dirPath,"%s%s",path,defaultBookName);
		bookID = F_ApiSimpleOpen(dirPath,False);
	}
	//Active document is not docline book
	else if (!F_StrISuffix(F_ApiGetString(FV_SessionId,bookID,FP_Name),defaultBookName))
	{
		return 0;
	}
	//Active document is docline book
	F_ApiDeallocateString(&path);
	F_ApiDeallocateString(&dirPath);
	return bookID;
}
VoidT closeAllDocs()
{
	F_ObjHandleT docID;
	docID = F_ApiGetId(0,FV_SessionId,FP_FirstOpenDoc);
	while(docID)
	{
		F_ApiClose(docID,FF_CLOSE_MODIFIED);
		docID = F_ApiGetId(0,FV_SessionId,FP_NextOpenDocInSession);
	}
}

StringT getMainBookName(F_ObjHandleT docID)
{
	StringT pathName, newPathName;

	pathName = F_ApiGetString(FV_SessionId, docID, FP_Name);
	pathFilename(pathName);
	newPathName = F_Alloc(F_StrLen(pathName)+F_StrLen(defaultBookName)+5,NO_DSE);
	F_Sprintf(newPathName,"%s%s",pathName,defaultBookName);
	return newPathName;
}

BoolT setDefaultDirectory(StringT dirPath)
{
	F_ApiSetString(0,FV_SessionId,FP_OpenDir, dirPath);
	return TRUE;
}

BoolT getTempDirPath(StringT *path)
{
	StringT clPath, suffix;

	clPath = F_StrCopyString(F_ApiClientDir());
	suffix = F_StrCopyString("\\docline\\temp\\");
	*path = (StringT)F_Alloc((F_StrLen(clPath)+F_StrLen(suffix)+1)*sizeof(UCharT),NO_DSE);
	F_Sprintf((*path),"%s%s",F_StrCopyString(clPath),F_StrCopyString(suffix));
	F_ApiDeallocateString(&clPath);
	F_ApiDeallocateString(&suffix);
}

BoolT cleanTempDirectory()
{
	DirHandleT handle;
	IntT statusp, i;
	UCharT buf[100];
	StringT tmpDirPath;
	FilePathT *tmpDirFilepath, *file;

	if (!getTempDirPath(&tmpDirPath)) return FALSE;
	tmpDirFilepath = F_PathNameToFilePath(tmpDirPath,NULL,FDosPath);
	handle = F_FilePathOpenDir(tmpDirFilepath, &statusp);
	if (!handle)
	{
		F_Printf(NULL,"%s\n","CleanTempDirectory.DirHandle error.");
		writeToChannel("CleanTempDirectory.DirHandle error.\n");
		return FALSE;
	}
	while (file = F_FilePathGetNext(handle,&statusp))
	{
		F_DeleteFile(file);
	}

	return TRUE;
}
