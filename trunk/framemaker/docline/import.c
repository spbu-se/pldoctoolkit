#include "common.h"
#include "import.h"
#include "logging.h"
#include "publishutil.h"

//StringT mainBookPath;
StringT workDirPath;
StringT tempDirPath;
IntT fileNum;

BoolT copyFilesToTempDirectory(FilePathT *filePath)
{
	DirHandleT handle;
	IntT statusp;
	FilePathT *file;
	StringT path;
	F_StringsT strs;
  BoolT first;

	handle = F_FilePathOpenDir(filePath, &statusp);
	if (!handle)
	{
		F_Printf(NULL,"%s\n","CopyFilesToTempDirectory.Handle error");
		writeToChannel("Error. Handle error\n");
		return FALSE;
	}
  first = TRUE;
	while(file = F_FilePathGetNext(handle, &statusp))
	{
    if (first)
    {
      strs.len = 1;
      strs.val = (StringT *)F_Alloc(sizeof(StringT),NO_DSE);
      first = FALSE;
    }
    else
    {
      strs.len ++;
      strs.val = (StringT *)F_Realloc(strs.val,strs.len*sizeof(StringT),NO_DSE);
    }
    path = F_FilePathToPathName(file, FDosPath);
    strs.val[strs.len-1] = F_StrCopyString(path);
    F_ApiDeallocateString(&path);
    F_FilePathFree(file);
	}
	F_FilePathCloseDir(handle);
  if (!splitFilesTo(strs.val,strs.len,tempDirPath))
  {
    F_ApiDeallocateStrings(&strs);
    return FALSE;
  }

  F_ApiDeallocateStrings(&strs);
	return TRUE;

}



BoolT generateOpenWithUnresolvedRefsParams(F_PropValsT *params)
{
	IntT i;

	*params = F_ApiGetOpenDefaultParams();
	if (!(*params).len)
	{
    writeToChannel("\tgenerateOpenWithUnresolvedRefsParams: get default params error");
    return FALSE;
	}
	i = F_ApiGetPropIndex(params,FS_RefFileNotFound);
	(*params).val[i].propVal.u.ival = FV_AllowAllRefFilesUnFindable;
	i = F_ApiGetPropIndex(params,FS_FontNotFoundInCatalog);
	(*params).val[i].propVal.u.ival = FV_DoOK;
	i = F_ApiGetPropIndex(params,FS_FontNotFoundInDoc);
	(*params).val[i].propVal.u.ival = FV_DoOK;

	return TRUE;
}

BoolT initializeConstants()
{
	if (F_ApiChooseFile(&workDirPath, "Choose directory to save new docline project", "", "", FV_ChooseOpenDir, "")) return FALSE;
	if (!setDefaultDirectory(workDirPath)) return FALSE;
	if (!getTempDirPath(&tempDirPath)) return FALSE;
  openLogChannel();
  fileNum = 0;

	return TRUE;
}
BoolT deinitializeConstants()
{ 
	if (tempDirPath) F_ApiDeallocateString(&tempDirPath);
	if (workDirPath) F_ApiDeallocateString(&workDirPath);
  //if (mainBookPath) F_ApiDeallocateString(&mainBookPath);
  if (!closeLogChannel()) return FALSE;

	return TRUE;
}
VoidT importDocLineDoc()
{
	StringT tmpPath;
	FilePathT *filedirPath, *file, *filetmpPath;
	DirHandleT handle;
	IntT statusp;
	F_PropValsT params;

	if (!initializeConstants())
  {
    deinitializeConstants();
    return;
  }
  writeToChannel("Import started\n");
	if (F_StrIEqual(workDirPath,(StringT)"")) //Check if directory selected
  {
    writeToChannel("\tNo output directory selected");
    deinitializeConstants();
    return; 
  }
	filedirPath = F_PathNameToFilePath(workDirPath, NULL, FDosPath);
  if (!filedirPath)
  {
    writeToChannel("\tInvalid directory selected");
    deinitializeConstants();
    return;
  }
	writeToChannel("\tCleaning temporary directory... ");
	if (!cleanTempDirectory())
  {
    F_FilePathFree(filedirPath);
    deinitializeConstants();
    return;
  }
	writeToChannel("Succesful.\n");
  writeToChannel("\tCleaning import directory... ");
	if (!cleanImportDirectory())
  {
    F_FilePathFree(filedirPath);
    deinitializeConstants();
    return;
  }
	writeToChannel("Succesfull.\n");
	writeToChannel("\tCopying files to temporary directory... ");
	if (!copyFilesToTempDirectory(filedirPath))
  {
    F_FilePathFree(filedirPath);
    deinitializeConstants();
    return;
  }
	writeToChannel("Succesfull.\n");
	filetmpPath = F_PathNameToFilePath (tempDirPath, NULL, FDosPath);
  if (!filetmpPath)
  {
    writeToChannel("\tNo temporary directory");
    F_FilePathFree(filedirPath);
    deinitializeConstants();
    return;
  }
	writeToChannel("\tPerforming XSL Transformation... ");
  if (!performImportXSLT())
  {
    F_FilePathFree(filetmpPath);
    F_FilePathFree(filedirPath);
    deinitializeConstants();
    return;
  }
	writeToChannel("Succesfull.\n");
	handle = F_FilePathOpenDir(filetmpPath,&statusp);
	if (!handle)
	{
		F_Printf(NULL,"import error:\n\tInvalid directory path: %s\n",workDirPath);
		writeToChannel("Error. Invalid directory path.\n");
    F_FilePathFree(filetmpPath);
    F_FilePathFree(filedirPath);
    deinitializeConstants();
    return;
	}
  F_FilePathFree(filetmpPath);
	if (!generateImportParams(&params))
  {
    F_FilePathFree(filedirPath);
    deinitializeConstants();
    return;
  }
	//Opening of all .drl files in directory with structured application "DocLine"
	writeToChannel("\tImporting all DRL files... ");
	while(file = F_FilePathGetNext(handle, &statusp))
	{
		tmpPath = F_FilePathToPathName(file,FDosPath);
		importBook(tmpPath,params);
		F_ApiDeallocateString(&tmpPath);
		F_FilePathFree(file);
	}
  F_ApiDeallocatePropVals(&params);
	F_FilePathCloseDir(handle);
	writeToChannel("Succesfull.\n");
	writeToChannel("\tCreating main project book... ");
	if (!openFilesInDirectory())
  {
    F_FilePathFree(filedirPath);
    deinitializeConstants();
    return;
  }
	writeToChannel("Succesfull.\n");
	writeToChannel("\tCleaning directory... ");
	if (!cleanDirectory(filedirPath))
  {
    F_FilePathFree(filedirPath);
    deinitializeConstants();
    return;
  }
  F_FilePathFree(filedirPath);
	writeToChannel("Succesfull.\n");
	writeToChannel("\nImport finished successully\n");
	deinitializeConstants();
}


BoolT importBook(StringT path, F_PropValsT params)
{
	F_ObjHandleT fileID;
	F_PropValsT *returnParams;

	if (!validateFilename(path,DRL))
	{
		writeToChannel("\n\tError. importBook: ");
		writeToChannel(path);
		writeToChannel(" is nor drl file.");
		return FALSE;
	}
	returnParams = NULL;
	writeToChannel("\n\tImporting ");
	writeToChannel(path);
	writeToChannel("... ");
	fileID = F_ApiOpen(path,&params,&returnParams);
	if (!fileID)
	{
		F_Printf(NULL,"Error in opening file %s",path);
		writeToChannel("Error. File not opened\n");
		if (returnParams != NULL) F_ApiDeallocatePropVals(returnParams);
		return FALSE;
	}
	writeToChannel("\tSuccesful.\n");
	writeToChannel("\tRenaming file... ");
	if (!renameFileToActualName(fileID))
	{
		if (returnParams != NULL) F_ApiDeallocatePropVals(returnParams);
	}
	writeToChannel("\tSuccesful.\n");
	F_ApiSimpleSave(fileID,F_StrCopyString(F_ApiGetString(FV_SessionId,fileID,FP_Name)),False);
	F_ApiClose(fileID,FF_CLOSE_MODIFIED);

	if (returnParams != NULL) F_ApiDeallocatePropVals(returnParams);

	return TRUE;
}
BoolT performImportXSLT()
{
	IntT statusp;
	StringT tempPath, dirPath, jarPath, doclineDir;
	FilePathT *filePath, *file;
	DirHandleT handle;
	F_StringsT strs;
	BoolT first;

	if (!getJarFileName(&jarPath)) return FALSE;
	dirPath = F_StrCopyString(tempDirPath);
	if (callJavaImportUtil(jarPath, dirPath) > 0)
	{
		writeToChannel("Error. JVM Intialization error");
		F_ApiDeallocateString(&jarPath);
		F_ApiDeallocateString(&dirPath);
		return FALSE;
	}
	F_ApiDeallocateString(&jarPath);

	first = TRUE;
	filePath = F_PathNameToFilePath(dirPath,NULL,FDosPath);
	handle = F_FilePathOpenDir(filePath, &statusp);
	F_FilePathFree(filePath);
	if (!handle)
	{
		writeToChannel("Error. Handle error\n");
		F_ApiDeallocateString(&jarPath);
		F_ApiDeallocateString(&dirPath);
		return FALSE;
	}
	while(file = F_FilePathGetNext(handle, &statusp))
	{
		if (first)
		{
			strs.len = 1;
			strs.val = (StringT *)F_Alloc(sizeof(StringT),NO_DSE);
			first = FALSE;
		}
		else
		{
			strs.len++;
			strs.val = (StringT *)F_Realloc(strs.val,strs.len*sizeof(StringT),NO_DSE);
		}
		tempPath = F_FilePathToPathName(file, FDosPath);
		strs.val[strs.len-1] = F_ApiCopyString(tempPath);
		F_ApiDeallocateString(&tempPath);
		F_FilePathFree(file);
	}
	removeTemporaryDRLs(strs.val,strs.len);

	F_ApiDeallocateStrings(&strs);
	F_ApiDeallocateString(&dirPath);

	return TRUE;
}

BoolT cleanImportDirectory()
{
	FilePathT *filedirPath, *file;
	DirHandleT handle;
	StringT tmpPath;
	IntT statusp;

	filedirPath = F_PathNameToFilePath (workDirPath, NULL, FDosPath);
	handle = F_FilePathOpenDir(filedirPath, &statusp);
	F_FilePathFree(filedirPath);
	if (!handle)
	{
		writeToChannel("\t\nError. cleanImportDirectory: invalid directory");
		return FALSE;
	}
	//Deleting non-docline files in directory
	while (file = F_FilePathGetNext(handle,&statusp))
	{
		tmpPath = F_FilePathToPathName(file,FDosPath);
		if (!validateFilename(tmpPath,DRL))
		{
			F_DeleteFile(file);
		}
		F_ApiDeallocateString(&tmpPath);
		F_FilePathFree(file);
	}
	F_FilePathCloseDir(handle);

	return TRUE;
}

BoolT generateBookUpdateParams(F_PropValsT *params)
{
	IntT i;

	*params = F_ApiGetUpdateBookDefaultParams();
	i = F_ApiGetPropIndex(params,FS_AlertUserAboutFailure);
	(*params).val[i].propVal.u.ival = TRUE;
	i = F_ApiGetPropIndex(params,FS_FontNotFoundInDoc);
	(*params).val[i].propVal.u.ival = FV_DoOK;

	return TRUE;
}

BoolT setAttributeValue(F_ObjHandleT docID, F_ObjHandleT elemID, StringT attrName, StringT attrVal)
{
	F_AttributesT attrs;
	F_AttributeT *attr;
	UIntT i;

	if (!elemID)
	{
		writeToChannel("\tError. setAttributeValue: invalid element\n");
		return FALSE;
	}
	attrs = F_ApiGetAttributes(docID,elemID);
	for (i=0; i<attrs.len; i++)
	{
		attr = &(attrs.val[i]);
		if (F_StrIEqual((*attr).name,attrName))
		{
			if ((*attr).values.len == 0)
			{
				(*attr).values.len = 1;
				(*attr).values.val = (StringT *)F_Alloc(sizeof(StringT),NO_DSE);
			}
			(*attr).values.val[0] = F_StrCopyString(attrVal);
			F_ApiSetAttributes(docID,elemID,&attrs);
      F_ApiDeallocateAttributes(&attrs);
			return TRUE;
		}
	}

	writeToChannel("\tError. setAttributeValue: no such attribute definition for this element\n");
  F_ApiDeallocateAttributes(&attrs);
	return FALSE;
}

BoolT setSecondLevelAttributes(F_ObjHandleT bookID)
{
	F_ObjHandleT topID, secondID, compID;
	F_AttributesT attrs, tmpAttrs;
	F_AttributeT *attr;
	UIntT i;

	topID = F_ApiGetId(FV_SessionId, bookID, FP_HighestLevelElement);
	if (!topID)
	{
		writeToChannel("\tError. setSecondLevelAttributes: No highest level element\n");
		return FALSE;
	}
	secondID = F_ApiGetId(bookID,topID,FP_FirstChildElement);
	while (secondID)
	{
		compID = F_ApiGetId(bookID,secondID,FP_FirstChildElement);
		if (!compID) continue;
		attrs = F_ApiGetAttributes(bookID,compID);
		//attrs = F_ApiCopyAttributes(&tmpAttrs);
		for (i=0; i<attrs.len; i++)
		{
			attr = &(attrs.val[i]);
			if ((*attr).values.len != 0)
			{
				if (F_StrIEqual((*attr).name,"ParentNameAttr"))
				{
					setAttributeValue(bookID,secondID,"Name",(*attr).values.val[0]);
					continue;
				}
				else if (F_StrIEqual((*attr).name,"ProductId"))
				{
					if (F_StrIEqual(F_ApiGetString(bookID,F_ApiGetId(bookID,secondID,FP_ElementDef),FP_Name),(StringT)"ProductDocumentation"))
					{
						setAttributeValue(bookID,secondID,"ProductId",(*attr).values.val[0]);
						continue;
					}
				}
			}
		}
		F_ApiDeallocateAttributes(&attrs);
		secondID = F_ApiGetId(bookID,secondID,FP_NextSiblingElement);
	}

	return TRUE;
}

BoolT openFilesInDirectory()
{
	StringT bookPath, workPath;
	F_ObjHandleT bookID;
	F_PropValsT params, *returnParams;

	workPath = F_StrCopyString(workDirPath);
	if (!openMainBook(workPath,&bookID,&bookPath))
	{
		writeToChannel("\n\tError. openFilesInDirectory: No book template found");
		F_ApiDeallocateString(&workPath);
		F_ApiDeallocateString(&bookPath);
		return FALSE;
	}
	if (!addExistingDocs(workPath,bookID))
	{
		writeToChannel("\n\tError. openFilesInDirectory: Invalid generated documents");
		F_ApiDeallocateString(&workPath);
		F_ApiDeallocateString(&bookPath);
		return FALSE;
	}
	F_ApiDeallocateString(&workPath);
	returnParams = NULL;
	if (!generateBookUpdateParams(&params))
	{
		writeToChannel("\n\tError. openFilesInDirectory: error in generation update params");
		F_ApiDeallocateString(&bookPath);
		F_ApiDeallocatePropVals(returnParams);
		F_ApiDeallocatePropVals(&params);
		return FALSE;
	}
	F_ApiUpdateBook(bookID,&params,&returnParams);
	F_ApiDeallocatePropVals(returnParams);
	F_ApiDeallocatePropVals(&params);
	if (!setSecondLevelAttributes(bookID))
	{
		writeToChannel("\n\tError. openFileInDirectory: error in setting attributes");
		F_ApiDeallocateString(&bookPath);
		return FALSE;
	}
	F_ApiSimpleSave(bookID,bookPath,False); 
	F_ApiDeallocateString(&bookPath);

	return TRUE;
}
BoolT generateImportParams(F_PropValsT *params)
{
	IntT i;

	*params = F_ApiGetOpenDefaultParams();
	if (!(*params).len)
	{
		writeToChannel("\n\tGenerating import params error.");
		return FALSE;
	}
	i = F_ApiGetPropIndex(params,FS_StructuredOpenApplication);
	(*params).val[i].propVal.u.sval = F_StrCopyString("DocLine");
	i = F_ApiGetPropIndex(params,FS_DontNotifyAPIClients);
	(*params).val[i].propVal.u.ival = False;
	i = F_ApiGetPropIndex(params,FS_RefFileNotFound);
	(*params).val[i].propVal.u.ival = FV_AllowAllRefFilesUnFindable;

	return TRUE;
}

BoolT renameFileToActualName(F_ObjHandleT fileID)
{
	F_ObjHandleT highID;
	StringT newFilePathName, value;
	F_AttributesT attrs;
	F_AttributeT *attr;
	UIntT i;

	if (!fileID)
	{
		writeToChannel("\tError. renameFileToActualName: Invalid file to rename.\n");
		F_Printf(NULL,"%s","Error. renamefileToActualName: Invalid file to Rename.\n");
		return FALSE;
	}
	highID = F_ApiGetId(fileID,F_ApiGetId(FV_SessionId,fileID,FP_MainFlowInDoc),FP_HighestLevelElement);
	if (!highID)
	{
		writeToChannel("\tError. renameFileToActualName: Invalid highest element.\n");
		F_Printf(NULL,"%s","Error. renameFileToActualName: Invalid highest element.\n");
		return FALSE;
	}
	attrs = F_ApiGetAttributes(fileID,highID);
	for (i=0; i<attrs.len; i++)
	{
		attr = &attrs.val[i];
		if (!(*attr).values.len)
		{
			F_Printf(NULL,"Error in Id attribute");
			writeToChannel("Error. renameFileToActualName: Id attribute error\n");
			continue;
		}
		if (F_StrIEqual((*attr).name,"Id"))
		{
      value = F_StrCopyString((*attr).values.val[0]);
      newFilePathName = (StringT)F_Alloc((F_StrLen(workDirPath)+F_StrLen(value)+10)*sizeof(UCharT),NO_DSE);
			F_Sprintf(newFilePathName,"%s\\%s_%d.fm\0",workDirPath,value,fileNum);
      F_ApiDeallocateString(&value);
			fileNum++;
			F_ApiSimpleSave(fileID,newFilePathName,FALSE);
      F_ApiDeallocateString(&newFilePathName);
      F_ApiDeallocateAttributes(&attrs);
			return TRUE;
		}
	}

  F_ApiDeallocateAttributes(&attrs);
	return FALSE;
}

VoidT simpleOpenBook()
{
	StringT filePath;
	F_ObjHandleT bookID;

  filePath = defaultPath;
	bookID = F_ApiSimpleOpen(defaultPath,True);
  F_ApiDeallocateString(&filePath);
	if (!bookID) return;
	filePath = F_ApiGetString(FV_SessionId,bookID,FP_Name);
	filePath = fileFileName(filePath);
	if (!F_StrIEqual(filePath,defaultBookName))
	{
		F_ApiClose(bookID,FF_CLOSE_MODIFIED);
	}
	F_ApiDeallocateString(&filePath);
}
BoolT addExistingDocs(StringT path, F_ObjHandleT bookID)
{
	DirHandleT handle;
	IntT statusp;
	FilePathT *file, *filepath;
	StringT tmpPath;

	filepath = F_PathNameToFilePath(path, NULL, FDosPath);
	handle = F_FilePathOpenDir(filepath, &statusp);
  F_FilePathFree(filepath);
	if (!handle)
	{
    writeToChannel("\n\tError. addExistingDocs: directory path error.\n");
		return FALSE;
	}
	while((file = F_FilePathGetNext(handle, &statusp)))
	{
		tmpPath = F_FilePathToPathName(file, FDosPath);
		if (validateFilename(tmpPath,FM)) 
		{
			if (!addExistingDoc(tmpPath,bookID))
      {
        writeToChannel("\n\tError. addExistingDocs");
        continue;
      }
		}
		else if ((!F_StrIEqual(fileFileName(tmpPath),defaultBookName))&&
			(!validateFilename(tmpPath,FMBOOK))&&
			(!validateFilename(tmpPath,DRL)))
		{
			F_DeleteFile(file);
		}
    F_ApiDeallocateString(&tmpPath);
    F_FilePathFree(file);
	}
	F_FilePathCloseDir(handle);

	return TRUE;
}

BoolT addExistingDoc(StringT path, F_ObjHandleT bookID)
{
	F_ObjHandleT fileID, bookTopID, bookSecondID, fileTopID, defID;
	StringT place, placeAttrVal, elemName, bookPlaceAttrVal;
	F_PropValsT params, *returnParams;
	F_AttributesT attrs;
	F_AttributeT *attr;
	UIntT i;
	F_ElementLocT loc;

	if (!bookID)
	{
		writeToChannel("\n\tError. addExistingDoc: invalid book");
		return FALSE;
	}
	if (!generateOpenWithUnresolvedRefsParams(&params))
  {
    writeToChannel("\n\tError. addExisingDoc: open params were not generated");
    return FALSE;
  }
	returnParams = NULL;
	fileID = F_ApiOpen(path,&params, &returnParams);
	if (!fileID)
	{
		writeToChannel("\n\tError. addExistingDoc: invalid file: ");
		writeToChannel(path);
    F_ApiDeallocatePropVals(&params);
    F_ApiDeallocatePropVals(returnParams);
		return FALSE;
	}
  F_ApiDeallocatePropVals(&params);
  F_ApiDeallocatePropVals(returnParams);
	fileTopID = F_ApiGetId(fileID, F_ApiGetId(FV_SessionId,fileID,FP_MainFlowInDoc),FP_HighestLevelElement);
	if (!fileTopID)
	{
		writeToChannel("\n\tError. addExistingDoc: invalid file template: ");
		writeToChannel(path);
		return FALSE;
	}
	placeAttrVal = F_StrCopyString("\0");
  place = F_StrCopyString("\0");
	attrs  = F_ApiGetAttributes(fileID,fileTopID);
	for (i=0; i<attrs.len; i++)
	{
		attr = &attrs.val[i];
		if (F_StrIEqual((*attr).name,(StringT)"FileName"))
		{
			if (!(*attr).values.len) continue;
      placeAttrVal = F_StrCopyString((*attr).values.val[0]);
		}
		else if (F_StrIEqual((*attr).name,(StringT)"ParentType"))
		{
      if (!(*attr).values.len) continue;
			place = F_StrCopyString((*attr).values.val[0]);
		}
	}
  F_ApiDeallocateAttributes(&attrs);
	bookTopID = F_ApiGetId(FV_SessionId,bookID,FP_HighestLevelElement);
	if (! bookTopID)
	{
		writeToChannel("Error. addExistingDoc: invalid book template\n");
    F_ApiDeallocateString(&place);
    F_ApiDeallocateString(&placeAttrVal);
		return FALSE;
	}
	bookSecondID = F_ApiGetId(bookID,bookTopID,FP_FirstChildElement);
	while (bookSecondID)
	{
		elemName = F_ApiGetString(bookID,F_ApiGetId(bookID,bookSecondID,FP_ElementDef),FP_Name);
		if (F_StrIEqual(elemName,place))
		{
			bookPlaceAttrVal = F_StrCopyString("\0");
			attrs = F_ApiGetAttributes(bookID,bookSecondID);
			for (i=0; i<attrs.len; i++)
			{
				attr = &attrs.val[i];
				if (!(*attr).values.len) continue;
				if (F_StrIEqual((*attr).name,(StringT)"FileName"))
				{
					bookPlaceAttrVal = F_StrCopyString((*attr).values.val[0]);
				}
			}
      F_ApiDeallocateAttributes(&attrs);
			if (F_StrIEqual(placeAttrVal, bookPlaceAttrVal))
			{
				loc.childId = 0;
				loc.offset = 0;
				loc.parentId = bookSecondID;

				F_ApiNewBookComponentInHierarchy(bookID,path,&loc);
				F_ApiSimpleSave(fileID,path,FALSE);
				F_ApiClose(fileID,FF_CLOSE_MODIFIED);

        F_ApiDeallocateString(&place);
        F_ApiDeallocateString(&placeAttrVal);
        F_ApiDeallocateString(&elemName);
        F_ApiDeallocateString(&bookPlaceAttrVal);

				return TRUE;
			}
      F_ApiDeallocateString(&bookPlaceAttrVal);
		}
    F_ApiDeallocateString(&elemName);
		bookSecondID = F_ApiGetId(bookID,bookSecondID,FP_NextSiblingElement);
	}
	if (F_StrIEqual(place,(StringT)"\0"))
	{
		loc.childId = 0;
		loc.offset = 0;
		loc.parentId = bookTopID;
	}
	else
	{
		loc.childId = 0;
		loc.offset = 0;
		loc.parentId = bookTopID;
		defID = F_ApiGetNamedObject(bookID,FO_ElementDef,place);
		if (!defID)
		{
			writeToChannel("\n\tError. addExistingDoc: invalid element: ");
			writeToChannel(place);
      F_ApiDeallocateString(&placeAttrVal);
      F_ApiDeallocateString(&place);
			return FALSE;
		}
		bookSecondID = F_ApiNewElementInHierarchy(bookID,defID,&loc);
    if (!bookSecondID)
    {
      writeToChannel("\n\tError. addExistingDoc: cannot create second level element");
      F_ApiDeallocateString(&placeAttrVal);
      F_ApiDeallocateString(&place);
      return FALSE;
    }
		attrs = F_ApiGetAttributes(bookID,bookSecondID);
		for (i=0; i<attrs.len; i++)
		{
			attr = &attrs.val[i];
			if (F_StrIEqual((*attr).name,(StringT)"FileName"))
			{
				(*attr).values.len = 1;
				(*attr).values.val = (StringT *)F_Alloc(sizeof(StringT),NO_DSE);
				(*attr).values.val[0] = F_StrCopyString(placeAttrVal);
			}
		}
		F_ApiSetAttributes(bookID,bookSecondID,&attrs);
    F_ApiDeallocateAttributes(&attrs);
		loc.childId = 0;
		loc.offset = 0;
		loc.parentId = bookSecondID;
	}
  F_ApiDeallocateString(&placeAttrVal);
  F_ApiDeallocateString(&place);

	F_ApiSimpleSave(fileID,path,FALSE);
	F_ApiClose(fileID,FF_CLOSE_MODIFIED);

	F_ApiNewBookComponentInHierarchy(bookID,path,&loc);

  writeToChannel("\n\t0");
	return TRUE;
}

