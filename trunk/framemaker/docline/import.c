#include "common.h"
#include "import.h"
#include "logging.h"
#include "publishutil.h"

StringT mainBookPath;
StringT workDirPath;
StringT tempDirPath;
IntT fileNum;

BoolT copyFilesToTempDirectory(FilePathT *filePath)
{
	DirHandleT handle;
	IntT statusp, i;
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
	if (F_ApiChooseFile(&workDirPath, "Choose directory to save new docline project", "", "", FV_ChooseOpenDir, "")) return;
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
  if (mainBookPath) F_ApiDeallocateString(&mainBookPath);
  if (!closeLogChannel()) return FALSE;

	return TRUE;
}
VoidT importDocLineDoc()
{
	F_ObjHandleT docID;
	StringT tmpPath;
	FilePathT *filedirPath, *file, *filetmpPath;
	DirHandleT handle;
	IntT statusp, i;
	F_PropValsT params;
	UCharT buf[100];

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
	writeToChannel("\tCopying files to temporary directory... ");
	if (!copyFilesToTempDirectory(filedirPath))
  {
    F_FilePathFree(filedirPath);
    deinitializeConstants();
    return;
  }
	writeToChannel("Succesfull.\n");
	writeToChannel("\tCleaning import directory... ");
	if (!cleanImportDirectory())
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

	if (!validateFilename(path,DRL)) return 0;
	returnParams = NULL;
	writeToChannel("\n\tImporting ");
	writeToChannel(path);
	writeToChannel("... ");
	fileID = F_ApiOpen(path,&params,&returnParams);
	if (!fileID)
	{
		F_Printf(NULL,"Error in opening file %s",path);
		writeToChannel("Error. File not opened\n");
		return 0;
	}
	writeToChannel("\tSuccesful.\n");
	writeToChannel("\tRenaming file... ");
	renameFileToActualName(fileID);
	writeToChannel("\tSuccesful.\n");
	fileID = F_ApiSimpleSave(fileID,F_StrCopyString(F_ApiGetString(FV_SessionId,fileID,FP_Name)),False);
	F_ApiClose(fileID,FF_CLOSE_MODIFIED);

	F_ApiDeallocatePropVals(returnParams);

	return 1;
}
BoolT performImportXSLT()
{
	IntT retVal, statusp, i;
	UCharT jarPath[256];
	StringT tempPath, fileArr[65535], dirPath;
	FilePathT *filePath, *file;
	DirHandleT handle;
	F_StringsT strs;

	tempPath = F_ApiClientDir();
  dirPath = F_StrCopyString(tempDirPath);
	F_Sprintf(jarPath, "%s\\%s", tempPath, JAR_FILENAME);
	retVal = callJavaImportUtil(jarPath, dirPath);
	if (retVal > 0)
	{
		F_Printf(NULL,"JVM Initiliazation error\n");
		writeToChannel("Error. JVM Intialization error");
	}

	i=0;
	filePath = F_PathNameToFilePath(dirPath,NULL,FDosPath);
	handle = F_FilePathOpenDir(filePath, &statusp);
	if (!handle)
	{
		F_Printf(NULL,"%s\n","XSLT.Handle error");
		writeToChannel("Error. Handle error\n");
		return FALSE;
	}
	while(file = F_FilePathGetNext(handle, &statusp))
	{
		tempPath = F_FilePathToPathName(file, FDosPath);
		fileArr[i] = F_ApiCopyString(tempPath);
		i++;
		F_ApiDeallocateString(&tempPath);
	}
	strs.len = i;
	strs.val = fileArr;

	removeTemporaryDRLs(fileArr,i);

	return !retVal;
}

BoolT cleanImportDirectory()
{
	FilePathT *filedirPath, *file;
	DirHandleT handle;
	StringT tmpPath;
	IntT statusp;

	filedirPath = F_PathNameToFilePath (workDirPath, NULL, FDosPath);
	handle = F_FilePathOpenDir(filedirPath, &statusp);
	if (!handle)
	{
		F_FilePathFree(filedirPath);
		F_ApiAlert("Handle Error2", FF_ALERT_CONTINUE_NOTE);
		return 0;
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

	F_ApiDeallocateString(&tmpPath);
	F_FilePathFree(file);
	F_FilePathFree(filedirPath);

	return 1;
}

F_PropValsT generateBookUpdateParams()
{
	F_PropValsT params;
	IntT i;

	params = F_ApiGetUpdateBookDefaultParams();
	i = F_ApiGetPropIndex(&params,FS_AlertUserAboutFailure);
	params.val[i].propVal.u.ival = TRUE;
	i = F_ApiGetPropIndex(&params,FS_FontNotFoundInDoc);
	params.val[i].propVal.u.ival = FV_DoOK;

	return params;
}

BoolT setAttributeValue(F_ObjHandleT docID, F_ObjHandleT elemID, StringT attrName, StringT attrVal)
{
	F_AttributesT attrs;
	F_AttributeT *attr;
	IntT i;

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
			return TRUE;
		}
	}

	writeToChannel("\tError. setAttributeValue: no such attribute definition for this element\n");
	return FALSE;
}

BoolT setSecondLevelAttributes(F_ObjHandleT bookID)
{
	F_ObjHandleT topID, secondID, compID;
	F_AttributesT attrs, tmpAttrs;
	F_AttributeT attr;
	IntT i;

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
		tmpAttrs = F_ApiGetAttributes(bookID,compID);
		attrs = F_ApiCopyAttributes(&tmpAttrs);
		for (i=0; i<attrs.len; i++)
		{
			attr = F_ApiCopyAttribute(&(attrs.val[i]));
			if (attr.values.len != 0)
			{
				if (F_StrIEqual(attr.name,"ParentNameAttr"))
				{
					setAttributeValue(bookID,secondID,"Name",attr.values.val[0]);
					continue;
				}
				else if (F_StrIEqual(attr.name,"ProductId"))
				{
					if (F_StrIEqual(F_ApiGetString(bookID,F_ApiGetId(bookID,secondID,FP_ElementDef),FP_Name),(StringT)"ProductDocumentation"))
					{
						setAttributeValue(bookID,secondID,"ProductId",attr.values.val[0]);
						continue;
					}
				}
			}
			F_ApiDeallocateAttribute(&attr);
		}
		F_ApiDeallocateAttributes(&attrs);
		secondID = F_ApiGetId(bookID,secondID,FP_NextSiblingElement);
	}

	return TRUE;
}

BoolT openFilesInDirectory()
{
	StringT bookPath;
	F_ObjHandleT bookID;
	F_PropValsT params, *returnParams;

	if (!(bookID = openMainBook(F_StrCopyString(workDirPath)))) return;
	if (!addExistingDocs(F_StrCopyString(workDirPath),bookID)) return;
	bookPath = F_ApiGetString(FV_SessionId,bookID,FP_Name);
	returnParams = NULL;
	params = generateBookUpdateParams();
	F_ApiUpdateBook(bookID,&params,&returnParams);
	//F_ApiSimpleGenerate(bookID,False,True);
	if (!setSecondLevelAttributes(bookID)) return;
	F_ApiSimpleSave(bookID,bookPath,False); 

	F_ApiDeallocateString(&bookPath);
	//F_Free(&bookID);

  return TRUE;
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
		writeToChannel("Error. File not found.\n");
		return;
	}
	path = F_FilePathToPathName(F_PathNameToFilePath(F_ApiGetString(FV_SessionId,docID,FP_Name),NULL,FDefaultPath),FDosPath);
	if ((!validateFilename(path,FM))&&(!((validateFilename(path,FMBOOK))&&(F_StrISuffix(path,defaultBookName)))))
	{
		F_Printf(NULL,"Error. Not Framemaker file\n");
		writeToChannel("Error. Not Framemaker file\n");
		return;
	}
	pathFilename(path);
	openFilesInDirectory(path);
}
F_PropValsT generateOpenParams(BoolT interactive)
{
	F_PropValsT params;
	IntT i;

	params = F_ApiGetOpenDefaultParams();
	if (!params.len)
	{
		F_Printf(NULL,"%s\n","Default params error");
		writeToChannel("Error. Default params error\n");
	}
	if (interactive)
	{
		i = F_ApiGetPropIndex(&params, FS_ShowBrowser);
		params.val[i].propVal.u.ival = True;
	}
	i = F_ApiGetPropIndex(&params, FS_ForceOpenAsText);
	params.val[i].propVal.u.ival = True;

	//F_Free(&i);

	return params;
}
BoolT generateImportParams(F_PropValsT *params)
{
	IntT i;

	*params = F_ApiGetOpenDefaultParams();
	if (!(*params).len)
	{
		writeToChannel("Generating import params error.");
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
	UCharT newFilePathName[100];
	F_AttributesT attrs;
	F_AttributeT attr;
	IntT i;

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
		attr = attrs.val[i];
		if (!attr.values.len)
		{
			F_Printf(NULL,"Error in Id attribute");
			writeToChannel("Error. renameFileToActualName: Id attribute error\n");
			continue;
		}
		if (F_StrIEqual(attr.name,"Id"))
		{
			F_Sprintf(newFilePathName,"%s\\%s_%d.fm\0",workDirPath,attr.values.val[0],fileNum);
			fileNum++;
			F_ApiSimpleSave(fileID,newFilePathName,FALSE);

			//F_ApiDeallocateString(&newFilePathName);
			//F_ApiDeallocateAttribute(&attr);
			////F_Free(&i);
			//F_ApiDeallocateAttributes(&attrs);
			////F_Free (&highID);

			return TRUE;
		}
	}

	//F_ApiDeallocateString(&newFilePathName);
	//F_ApiDeallocateAttribute(&attr);
	////F_Free(&i);
	//F_ApiDeallocateAttributes(&attrs);
	////F_Free (&highID);

	return FALSE;
}

VoidT renameFilesToActualNames(F_ObjHandleT bookID)
{
	F_ObjHandleT compID;
	StringT compPath, newCompPath;
	F_AttributesT attrs;
	F_AttributeT attr;
	UIntT i, j;

	if (!bookID)
	{
		F_Printf(NULL,"renameFilesToActualNames error:\n\tInvalid Book\n");
		writeToChannel("Error. Invalid book.\n");
		return;
	}
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
					writeToChannel("Error. Id attribute error\n");
					continue;
				}

				newCompPath = F_Alloc((F_StrLen(attr.values.val[0])+F_StrLen(workDirPath)+10)*sizeof(UCharT),NO_DSE);
				F_Sprintf(newCompPath,"%s\\%s.fm",workDirPath,attr.values.val[0]);
				i = 0;
				while (F_RenameFile(F_PathNameToFilePath(compPath,NULL,FDosPath),F_PathNameToFilePath(newCompPath,NULL,FDosPath)) != FdeSuccess)
				{
					F_Sprintf(newCompPath,"%s\\%s%d.fm\0",workDirPath,attr.values.val[0],i);
					//F_Printf(NULL,"%s\n",newCompPath);
					i++;
				}
				F_ApiSetString(bookID,compID,FP_Name,newCompPath);
				F_ApiDeallocateString(&newCompPath);
			}
		}
		compID = F_ApiGetId(bookID,compID,FP_NextComponentInBook);
	}

	//F_Free(&compID);
	//F_ApiDeallocateAttribute(&attr);
	F_ApiDeallocateAttributes(&attrs);
	F_ApiDeallocateString(&compPath);
	//F_Free(&i);
	//F_Free(&j);
}

VoidT simpleOpenBook()
{
	StringT filePath;
	F_ObjHandleT bookID;

	bookID = F_ApiSimpleOpen(defaultPath,True);
	if (!bookID) return;
	filePath = F_ApiGetString(FV_SessionId,bookID,FP_Name);
	filePath = fileFileName(filePath);
	if (!F_StrIEqual(filePath,defaultBookName))
	{
		F_ApiClose(bookID,FF_CLOSE_MODIFIED);
	}
	F_ApiDeallocateString(&filePath);
	//F_Free(&bookID);
}
BoolT addExistingDocs(StringT path, F_ObjHandleT bookID)
{
	DirHandleT handle;
	IntT statusp;
	FilePathT *file, *filepath;
	StringT tmpPath;

	filepath = F_PathNameToFilePath (path, NULL, FDosPath);
	handle = F_FilePathOpenDir(filepath, &statusp);
	if (!handle)
	{
		F_Printf(NULL,"OpenBooks:\n\tDirectory path error: %s\n",path);
		writeToChannel("Error. Directory path error.\n");
		return 0;
	}
	while((file = F_FilePathGetNext(handle, &statusp)))
	{
		tmpPath = F_FilePathToPathName(file, FDosPath);
		if (validateFilename(tmpPath,FM)) 
		{
			if (!addExistingDoc(tmpPath,bookID)) return 0;
		}
		else if ((!F_StrIEqual(fileFileName(tmpPath),defaultBookName))&&
			(!validateFilename(tmpPath,FMBOOK))&&
			(!validateFilename(tmpPath,DRL)))
		{
			F_DeleteFile(file);
		}
	}

	F_FilePathCloseDir(handle);
	//F_Free(&statusp);
	F_ApiDeallocateString(&tmpPath);
	F_FilePathFree(file);
	F_FilePathFree(filepath);
	//F_Free(&handle);

	return 1;
}

BoolT addExistingDoc(StringT path, F_ObjHandleT bookID)
{
	F_ObjHandleT fileID, bookTopID, bookSecondID, fileTopID, defID;
	StringT parentString, place, placeAttrVal, elemName, bookPlaceAttrVal;
	F_PropValsT params, *returnParams;
	F_AttributesT attrs;
	F_AttributeT attr;
	IntT i;
	F_ElementLocT loc;

	if (!bookID)
	{
		writeToChannel((StringT)"Error. addExistingDoc: invalid book\n");
		return FALSE;
	}
	if (!generateOpenWithUnresolvedRefsParams(&params)) return FALSE;
	returnParams = NULL;
	fileID = F_ApiOpen(path,&params, &returnParams);
	if (!fileID)
	{
		writeToChannel((StringT)"Error. addExistingDoc: invalid file: ");
		writeToChannel(path);
		writeToChannel("\n");
		return FALSE;
	}
	//parentString = getHighestString(fileID);
	//place = getPlace(parentString);
	fileTopID = F_ApiGetId(fileID, F_ApiGetId(FV_SessionId,fileID,FP_MainFlowInDoc),FP_HighestLevelElement);
	if (!fileTopID)
	{
		writeToChannel((StringT)"Error. addExistingDoc: invalid file template: ");
		writeToChannel(path);
		writeToChannel("\n");
		return FALSE;
	}
	placeAttrVal = "";
	attrs  = F_ApiGetAttributes(fileID,fileTopID);
	for (i=0; i<attrs.len; i++)
	{
		attr = attrs.val[i];
		if (F_StrIEqual(attr.name,"FileName"))
		{
			if (!attr.values.len) continue;
			placeAttrVal = attr.values.val[0];
		}
		else if (F_StrIEqual(attr.name,"ParentType"))
		{
			//if (F_StrIEqual(attr.values.val[0],(StringT)"DocumentationCore")) 
			//{
			//	place = "DocumentationCore";
			//}
			//else if (F_StrIEqual(attr.values.val[0],(StringT)"product_documentation"))
			//{
			//	place = "ProductDocumentation";
			//}
			//else if (F_StrIEqual(attr.values.val[0],(StringT)"product_line"))
			//{
			//	place = "ProductLine";
			//}
			//else
			//{
			//	place = "";
			//}
			place = F_StrCopyString(attr.values.val[0]);
		}
	}
	bookTopID = F_ApiGetId(FV_SessionId,bookID,FP_HighestLevelElement);
	if (! bookTopID)
	{
		writeToChannel("Error. addExistingDoc: invalid book template\n");
		return FALSE;
	}
	bookSecondID = F_ApiGetId(bookID,bookTopID,FP_FirstChildElement);
	while (bookSecondID)
	{
		elemName = F_ApiGetString(bookID,F_ApiGetId(bookID,bookSecondID,FP_ElementDef),FP_Name);
		if (F_StrIEqual(elemName,place))
		{
			bookPlaceAttrVal = "";
			attrs = F_ApiGetAttributes(bookID,bookSecondID);
			for (i=0; i<attrs.len; i++)
			{
				attr = attrs.val[i];
				if (!attr.values.len) continue;
				if (F_StrIEqual(attr.name,"FileName"))
				{
					bookPlaceAttrVal = attr.values.val[0];
				}
			}
			if (F_StrIEqual(placeAttrVal, bookPlaceAttrVal))
			{
				loc.childId = 0;
				loc.offset = 0;
				loc.parentId = bookSecondID;

				F_ApiNewBookComponentInHierarchy(bookID,path,&loc);
				F_ApiSimpleSave(fileID,path,FALSE);
				F_ApiClose(fileID,FF_CLOSE_MODIFIED);

				return TRUE;
			}
		}
		bookSecondID = F_ApiGetId(bookID,bookSecondID,FP_NextSiblingElement);
	}
	if (F_StrIEqual(place,""))
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
			writeToChannel((StringT)"Error. addExistingDoc: invalid element: ");
			writeToChannel(place);
			writeToChannel((StringT)"\n");
			return FALSE;
		}
		bookSecondID = F_ApiNewElementInHierarchy(bookID,defID,&loc);
		attrs = F_ApiGetAttributes(bookID,bookSecondID);
		for (i=0; i<attrs.len; i++)
		{
			attr = attrs.val[i];
			if (F_StrIEqual(attr.name,"FileName"))
			{
				attr.values.len = 1;
				attr.values.val = (StringT *)F_Alloc(sizeof(StringT),DSE);
				attr.values.val[0] = F_StrCopyString(placeAttrVal);
				attrs.val[i] = attr;
			}
		}
		F_ApiSetAttributes(bookID,bookSecondID,&attrs);
		loc.childId = 0;
		loc.offset = 0;
		loc.parentId = bookSecondID;
	}

	F_ApiSimpleSave(fileID,path,FALSE);
	F_ApiClose(fileID,FF_CLOSE_MODIFIED);

	F_ApiNewBookComponentInHierarchy(bookID,path,&loc);

	return TRUE;
}

