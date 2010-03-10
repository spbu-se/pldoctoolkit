#include "common.h"
#include "import.h"
#include "logging.h"
#include "publishutil.h"

StringT mainBookPath;
StringT workDirPath;
IntT fileNum;

BoolT mergeFile(F_StringsT in_files, StringT out_file)
{
	//F_PropValsT params, *returnParams;
	//F_ObjHandleT fileID, flowID;
	//StringT fileName, str;
	//FilePathT *outPath;
	//ChannelT outChannel;
	//F_TextItemsT tis;
	//F_TextItemT *ip;
	//UIntT i,j;
	//FontEncIdT enc;

	//outPath = F_PathNameToFilePath(out_file,NULL,FDosPath);
	//outChannel = F_ChannelOpen(outPath, "a");
	//if (!outChannel)
	//{
	//	writeToChannel("Open merge channel error");
	//	F_Printf(NULL,"Open merge channel error");
	//	return FALSE;
	//}

	//returnParams = NULL;
	//for (j=0; j<in_files.len; j++)
	//{
	//	fileName = in_files.val[j];
	//	params = generateOpenParams(FALSE);
	//	fileID = F_ApiOpen(fileName, &params, &returnParams);
	//	if (!fileID) continue;
	//	flowID = F_ApiGetId(FV_SessionId, fileID, FP_MainFlowInDoc);
	//	if (!flowID) continue;
	//	tis = F_ApiGetText(fileID, flowID, FTI_String | FTI_LineEnd);
	//	//if (!tis) continue;
	//	for (i=0; i<tis.len; i++)
	//	{
	//		ip=&tis.val[i];
	//		if (ip->dataType == FTI_String)
	//		{
	//			str = ip->u.sdata;
	//		}
	//		else
	//		{
	//			str="\n";
	//		}
	//		if (!F_ChannelWrite(str, sizeof(UCharT), F_StrLen(str), outChannel))
	//		{
	//			F_Printf(NULL,"%s\n","Merge file writing error");
	//			return FALSE;
	//		}
	//	}
	//	F_ApiClose(fileID,FF_CLOSE_MODIFIED);
	//	//*in_files++;
	//}
	//F_ChannelClose(outChannel);
	mergeFilesTo(in_files.val,in_files.len,out_file);
	return TRUE;
}

StringT chooseFile()
{
	F_ObjHandleT docID;
	F_PropValsT params, *returnParams;
	StringT dirPath;

	returnParams = NULL;
	params = generateOpenParams();
	docID = F_ApiOpen(defaultPath,&params,&returnParams);
	if (!docID)
	{
		F_Printf(NULL,"No such file: %s\n", defaultPath);
		writeToChannel("Error. No such file.\n");
		return "";
	}
	dirPath = F_ApiGetString(FV_SessionId,docID,FP_Name);
	F_ApiClose(docID,FF_CLOSE_MODIFIED);
	pathFilename(dirPath);//since then dirPath should be const
	//err = F_ApiChooseFile(&dirPath, "Choose directory with documentation", "", "", FV_ChooseOpenDir, "");
 //	if (err || !dirPath)
	//{
	//	F_Printf(NULL,"%s\n","Invalid folder");
	//	return;
	//}
	//dirPath = F_Realloc(dirPath,F_StrLen(dirPath)+1,NO_DSE);
	//if (!F_StrCat(dirPath, "\\"))
	//{
	//	F_Printf(NULL, "%s\n", "Invalid concatenation");
	//	return;
	//}

	return dirPath;
}
F_PropValsT generateSaveParams()
{
	F_PropValsT params;
	IntT i;

	params = F_ApiGetSaveDefaultParams();
	if (!params.len)
	{
		F_Printf(NULL,"%s\n","Default params error");
		writeToChannel("Error. Default params error.\n");
	}
	i = F_ApiGetPropIndex(&params, FS_FileType);
	params.val[i].propVal.u.ival = FV_SaveFmtText;

	F_Free(&i);

	return params;
}
BoolT copyFileToTempDirectory(StringT filePath)
{
	F_PropValsT params, *returnParams;
	F_ObjHandleT fileID;
	StringT newPath;
	UCharT buf[256];
	IntT i;

	params = generateOpenParams(FALSE);
	returnParams = NULL;
	fileID = F_ApiOpen(filePath,&params,&returnParams);
	params = generateSaveParams();
	newPath = F_ApiClientDir();
	i = 0;
	i = F_Sprintf(buf, "%s", newPath);
	i += F_Sprintf(buf+i, "%s", "\\docline\\temp\\");
	i += F_Sprintf(buf+i, "%s", F_StrCopyString(fileFileName(filePath)));
	//if (!F_StrCat(newPath,F_StrCopyString("\\docline\\temp\\")))
	//{
	//	F_Printf(NULL,"%s\n","Copying. Concatenation error");
	//	return FALSE;
	//}
	//if (!F_StrCat(newPath,F_StrCopyString(fileFileName(filePath))))
	//{
	//	F_Printf(NULL,"%s\n","Copying. Concatenation error");
	//	return FALSE;
	//}
	newPath = (StringT)buf;
	returnParams = NULL;
	F_ApiSave(fileID,newPath,&params,&returnParams);
	F_ApiClose(fileID,FF_CLOSE_MODIFIED);

	return TRUE;
}

BoolT copyFilesToTempDirectory(FilePathT *filePath)
{
	DirHandleT handle;
	IntT statusp, i;
	FilePathT *file;
	StringT path;
	StringT fileArr[65535];
	F_StringsT strs;

	handle = F_FilePathOpenDir(filePath, &statusp);
	if (!handle)
	{
		F_Printf(NULL,"%s\n","CopyFilesToTempDirectory.Handle error");
		writeToChannel("Error. Handle error\n");
		return FALSE;
	}
	i=0;
	while(file = F_FilePathGetNext(handle, &statusp))
	{
		//path = F_FilePathToPathName(file, FDosPath);
		i++;
		//copyFileToTempDirectory(path);
	}
	F_FilePathCloseDir(handle);	

	//fileArr = (StringT*)F_Alloc(i,NO_DSE);
	strs.len=i;
	//fileArr=(StringT*)F_Alloc(i*2,NO_DSE);
	i=0;
	handle = F_FilePathOpenDir(filePath, &statusp);
	if (!handle)
	{
		F_Printf(NULL,"%s\n","CopyFilesToTempDirectory.Handle error");
		writeToChannel("Error. Handle error\n");
		return FALSE;
	}
	while(file = F_FilePathGetNext(handle, &statusp))
	{
		path = F_FilePathToPathName(file, FDosPath);
		fileArr[i] = F_ApiCopyString(path);
		i++;
	}
	strs.val = fileArr;
	//mergeFile(strs,"C:\\Program Files\\Adobe\\FrameMaker8\\fminit\\docline\\temp\\temp.drl");
	if (!splitFilesTo(strs.val,strs.len,"C:\\Program Files\\Adobe\\FrameMaker8\\fminit\\docline\\temp\\")) return FALSE;
	return TRUE;

}

BoolT splitFiles(StringT dirPath)
{
	DirHandleT handle;
	IntT statusp, numb;
	FilePathT *filePath;
	StringT pathName;

	filePath = F_PathNameToFilePath(dirPath,NULL,FDosPath);
	handle = F_FilePathOpenDir(filePath, &statusp);
	if (!handle)
	{
		writeToChannel((StringT)"\tHandle error\n");
		return FALSE;
	}
	numb = 0;
	while (filePath = F_FilePathGetNext(handle,&statusp))
	{
		pathName = F_FilePathToPathName(filePath,FDosPath);
		if (validateFilename(pathName,FM))
		{
			if (!splitFile(pathName, &numb)) continue;
		}
	}
	F_FilePathCloseDir(handle);
	return TRUE;
}

BoolT splitFile(StringT filePath, IntT *numb)
{
	StringT highestString, idAttrVal, newBookPath, dirPath, newDocPath, elemDefName, name;
	F_ObjHandleT docID, topID, topDefID, elemID, newBookID, newFileID, newTopID, newDocID, newDocTopID, elemDef, childID;
	F_AttributesT attrs;
	F_AttributeT attr;
	UCharT buf[100];
	IntT i;
	F_ElementLocT loc;
	F_PropValsT params, *returnParams;

	params = generateOpenWithUnresolvedRefsParams();
	returnParams = NULL;
	docID = F_ApiOpen(filePath,&params,&returnParams);
	F_ApiDeallocatePropVals(&params);
	F_ApiDeallocatePropVals(returnParams);
	writeToChannel("\n\t");
	writeToChannel(filePath);
	writeToChannel(":\n");
	if (!docID)
	{
		i = FA_errno;
		return FALSE;
	}
	topID = F_ApiGetId(docID,F_ApiGetId(FV_SessionId,docID,FP_MainFlowInDoc),FP_HighestLevelElement);
	topDefID = F_ApiGetId(docID,topID,FP_ElementDef);
	//highestString = F_ApiGetString(docID,F_ApiGetId(docID,F_ApiGetId(FV_SessionId,docID,FP_MainFlowInDoc),FP_Name);
	highestString = getHighestString(docID);
	if (F_StrIEqual(highestString, (StringT)"DocumentationCore"))
	{
		newBookID = F_ApiSimpleOpen("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_documentationCore_book_template.book",FALSE);
	}
	else if (F_StrIEqual(highestString, (StringT)"ProductDocumentation"))
	{
		newBookID = F_ApiSimpleOpen("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_productDocumentation_book_template.book",FALSE);
	}
	else if (F_StrIEqual(highestString, (StringT)"ProductLine"))
	{
		newBookID = F_ApiSimpleOpen("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_productLine_book_template.book",FALSE);
	}
	else
	{
		return TRUE;//this may be already generated file
	}
	writeToChannel("\t\tTemplate opened");
	dirPath = F_ApiCopyString(filePath);
	pathFilename(dirPath);
	F_ApiDeallocateString(&buf);
	i = F_Sprintf(buf,"%s", dirPath);
	i = F_Sprintf(buf+i,"%s_%d.book","book",*numb);
	(*numb)++;
	newBookPath = F_StrCopyString((StringT)buf);
	writeToChannel("\t\tNew book file: ");
	writeToChannel(newBookPath);
	newTopID = F_ApiGetId(FV_SessionId,newBookID,FP_HighestLevelElement);
	attrs = F_ApiGetAttributes(docID,topID);
	F_ApiSetAttributes(newBookID,newTopID, &attrs);
	F_ApiSimpleSave(newBookID,newBookPath,FALSE);
	writeToChannel("\n\t\tBook Saved\n");
	elemID = F_ApiGetId(docID,topID,FP_FirstChildElement);
	while (elemID)
	{
		newDocID = F_ApiSimpleNewDoc("C:\\Program Files\\Adobe\\FrameMaker8\\Structure\\xml\\docline\\docline_doc_template.fm",FALSE);
		attrs = F_ApiGetAttributes(docID,elemID);
		for (i=0; i<attrs.len; i++)
		{
			if(F_StrIEqual(attrs.val[i].name, "id"))
			{
				idAttrVal = attrs.val[i].values.val[0];
			}
		}
		F_ApiDeallocateString(&buf);
		i = F_Sprintf(buf,"%s", dirPath);
		i = F_Sprintf(buf+i,"%s_%d.fm",idAttrVal,*numb);
		newDocPath = F_StrCopyString((StringT)buf);
		(*numb)++;
		writeToChannel("\t\tNew document: ");
		writeToChannel(newDocPath);
		writeToChannel("\n");
		F_ApiSimpleSave(newDocID,newDocPath,FALSE);
		elemDef = F_ApiGetId(docID,elemID,FP_ElementDef);
		elemDefName = F_ApiGetString(docID,elemDef,FP_Name);
		elemDef = F_ApiGetNamedObject(newDocID,FO_ElementDef,elemDefName);
		F_ApiWrapElement(newDocID,elemDef);
		writeToChannel("\t\tHighest element added\n");
		attrs = F_ApiGetAttributes(docID,elemID);
		newDocTopID = F_ApiGetId(newDocID,F_ApiGetId(FV_SessionId,newDocID,FP_MainFlowInDoc),FP_HighestLevelElement);
		F_ApiSetAttributes(newDocID,newDocTopID,&attrs);
		childID = F_ApiGetId(docID,elemID,FP_FirstChildElement);
		while (childID)
		{
			addStructuredElementToDoc(docID,newDocID,newDocTopID,childID);
			childID = F_ApiGetId(docID,childID,FP_NextSiblingElement);
		}
		writeToChannel("\t\tStructure added\n");
		F_ApiSimpleSave(newDocID,newDocPath,FALSE);
		F_ApiClose(newDocID,FF_CLOSE_MODIFIED);
		loc.childId = 0;
		loc.offset = 0;
		loc.parentId = newTopID;
		F_ApiNewBookComponentInHierarchy(newBookID,newDocPath, &loc);
		elemID = F_ApiGetId(docID,elemID,FP_NextSiblingElement);
	}
	F_ApiSimpleGenerate(newBookID,FALSE,TRUE);
	F_ApiSimpleSave(newBookID,newBookPath,FALSE);
	F_ApiClose(newBookID,FF_CLOSE_MODIFIED);
	F_ApiClose(docID,FF_CLOSE_MODIFIED);
	//F_DeleteFile(F_PathNameToFilePath(filePath,NULL,FDosPath));
	//F_ApiDeallocateString(buf);
	F_Sprintf(buf,"%s_%d",filePath,*numb);
	F_RenameFile(F_PathNameToFilePath(filePath,NULL,FDosPath),F_PathNameToFilePath(buf,NULL,FDosPath));
	F_ApiDeallocateString(&buf);
	writeToChannel("\t\tSplitting completed\n");

	return TRUE;
}

BoolT addStructuredElementToDoc(F_ObjHandleT fromDocID, F_ObjHandleT toDocID, F_ObjHandleT parentID, F_ObjHandleT elemToMove)
{
	F_ObjHandleT defID, childID, elemID;
	F_ElementLocT loc;
	F_AttributesT attrs;
	StringT defName, name;
 
	defID = F_ApiGetId(fromDocID,elemToMove,FP_ElementDef);
	if (!defID) return FALSE;
	defName = F_ApiGetString(fromDocID,defID,FP_Name);
	defID = F_ApiGetNamedObject(toDocID,FO_ElementDef,defName);
	loc.childId = 0;
	loc.offset = 0;
	loc.parentId = parentID;
	elemID = F_ApiNewElementInHierarchy(toDocID,defID,&loc);
	attrs = F_ApiGetAttributes(fromDocID,elemToMove);
	F_ApiSetAttributes(toDocID,elemID, &attrs);

	childID = F_ApiGetId(fromDocID,elemToMove,FP_FirstChildElement);
	while (childID)
	{
		addStructuredElementToDoc(fromDocID,toDocID,elemID,childID);
		childID = F_ApiGetId(fromDocID,childID,FP_NextSiblingElement);
	}
}

F_PropValsT generateOpenWithUnresolvedRefsParams()
{
	F_PropValsT params;
	IntT i;

	params = F_ApiGetOpenDefaultParams();
	if (!params.len)
	{
		F_ApiAlert("Default params error",FF_ALERT_CONTINUE_NOTE);
	}
	i = F_ApiGetPropIndex(&params,FS_RefFileNotFound);
	params.val[i].propVal.u.ival = FV_AllowAllRefFilesUnFindable;

	F_Free(&i);

	return params;
}

VoidT importDocLineDoc()
{
	F_ObjHandleT docID;
	StringT dirPath, tmpPath, bookPath, tmpDirPath;
	FilePathT *filedirPath, *file, *filetmpPath;
	DirHandleT handle;
	IntT statusp, i;
	F_PropValsT params;
	UCharT buf[100];

	openLogChannel();
	writeToChannel("Import started\n\n");

	dirPath = chooseFile();
	if (dirPath == "") return;
	filedirPath = F_PathNameToFilePath (dirPath, NULL, FDosPath);//since then filedirPath should be const
	writeToChannel("Cleaning temporary directory... ");
	if (!cleanTempDirectory()) return;
	writeToChannel("Succesful.\n");
	writeToChannel("Copying files to temporary directory... ");
	if (!copyFilesToTempDirectory(filedirPath)) return;
	writeToChannel("Succesfull.\n");
	if (!setDefaultDirectory(dirPath)) return;
	workDirPath = F_StrCopyString(dirPath);
	writeToChannel("Cleaning import directory... ");
	if (!cleanImportDirectory(dirPath)) return;
	writeToChannel("Succesfull.\n");
	tmpDirPath = F_StrCopyString(F_ApiClientDir());
	i = F_Sprintf(buf,"%s", tmpDirPath);
	i = F_Sprintf(buf+i,"\\docline\\temp\\");
	tmpDirPath = F_StrCopyString((StringT)buf);
	//if (!F_StrCat(tmpDirPath,F_StrCopyString("\\docline\\temp\\")))
	//{
	//	F_Printf(NULL,"%s\n","Copying. Concatenation error");
	//	writeToChannel("Concatenation error");
	//	return;
	//}
	filetmpPath = F_PathNameToFilePath (tmpDirPath, NULL, FDosPath);//since then filedirPath should be const
	writeToChannel("Performing XSL Transformation... ");
	if (!performImportXSLT(tmpDirPath)) return;
	writeToChannel("Succesfull.\n");
	bookPath = "";
	handle = F_FilePathOpenDir(filetmpPath,&statusp);
	if (!handle)
	{
		F_Printf(NULL,"import error:\n\tInvalid directory path: %s\n",dirPath);
		writeToChannel("Error. Invalid directory path.\n");
		return;
	}
	params = generateImportParams();
	//Opening of all .drl files in directory with structured application "DocLine"
	writeToChannel("Importing all DRL files... ");
	fileNum = 0;
	while((file = F_FilePathGetNext(handle, &statusp)) != NULL)
	{
		tmpPath = F_FilePathToPathName(file,FDosPath);
		importBook(tmpPath,params);
		F_ApiDeallocateString(&tmpPath);
		F_FilePathFree(file);

	}
	F_FilePathCloseDir(handle);
	writeToChannel("Succesfull.\n");
	//writeToChannel("Splitting... ");
	//if (!splitFiles(dirPath)) return;
	//writeToChannel("Succesfull.\n");
	writeToChannel("Creating main project book... ");
	openFilesInDirectory(dirPath);
	writeToChannel("Succesfull.\n");
	writeToChannel("Cleaning directory... ");
	filedirPath = F_PathNameToFilePath(dirPath,NULL,FDosPath);
	cleanDirectory(filedirPath);
	writeToChannel("Succesfull.\n");

	writeToChannel("\nImport finished successully\n\n");
	closeLogChannel();

	F_Free(&docID);
	F_Free(&handle);
	F_Free(&statusp);
	F_ApiDeallocatePropVals(&params);    
	F_FilePathFree(filedirPath);
	F_ApiDeallocateString(&dirPath);
	F_ApiDeallocateString(&bookPath);
}

BoolT editAttributes(F_ObjHandleT fileID, F_ObjHandleT elemID, StringT path)
{
	F_AttributesT attrs;
	F_AttributeT attr;
	IntT j;

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
				attr.values.val[0] = fileFileName(F_StrCopyString(path));
				attrs.val[j] = attr;
			}
		}
		F_ApiSetAttributes(fileID,elemID,&attrs);
	}
}

BoolT importBook(StringT path, F_PropValsT params)
{
	F_ObjHandleT fileID, elemID;
	F_AttributeT attr;
	F_AttributesT attrs;
	F_PropValsT *returnParams;
	UIntT j;
	StringT str;

	if (!validateFilename(path,DRL)) return 0;
	returnParams = NULL;
	writeToChannel("\n\tImporting ");
	writeToChannel(path);
	writeToChannel("... ");
	fileID = F_ApiOpen(path,&params,&returnParams);
	//F_ApiCallClient("FmDispatcher", "XmlInit");
	//F_ApiCallClient("FmDispatcher", "StructuredOutputDir D:\\Study\\MY_SP\\CurTest\\");
	//F_ApiCallClient("FmDispatcher", "StructuredLog C:\\Program Files\\Adobe\\FrameMaker8\\fminit\\docline\\docline.log");
	//F_ApiCallClient("FmDispatcher", "StructuredReadXml D:\Study\MY_SP\CurTest\User_manual.drl");
	if (!fileID)
	{
		F_Printf(NULL,"Error in opening file %s",path);
		writeToChannel("Error. File not opened\n");
		return 0;
	}
	writeToChannel("\tSuccesful.\n");
	//writeToChannel("\tInsserting attributes... ");
	//elemID = F_ApiGetId(FV_SessionId,fileID,FP_HighestLevelElement);
	////Inserting additional attribute, that indicates file name, in highest level element
	//editAttributes(fileID,elemID,path);
	//writeToChannel("\tSuccesful.\n");
	writeToChannel("\tRenaming file... ");
	renameFileToActualName(fileID);
	writeToChannel("\tSuccesful.\n");
	str = F_ApiGetString(FV_SessionId,fileID,FP_Name);
	fileID = F_ApiSimpleSave(fileID,F_ApiGetString(FV_SessionId,fileID,FP_Name),False);
	F_ApiClose(fileID,FF_CLOSE_MODIFIED);

	F_Free(&fileID);
	F_Free(&elemID);
	F_ApiDeallocatePropVals(returnParams);
	F_ApiDeallocateAttributes(&attrs);
	//F_ApiDeallocateAttribute(&attr);//was commented

	return 1;
}
BoolT performImportXSLT(StringT dirPath)
{
	IntT retVal;
	UCharT jarPath[256];
	StringT tempPath;

	tempPath = F_ApiClientDir();
	F_Sprintf(jarPath, "%s\\%s", tempPath, JAR_FILENAME);
	retVal = callJavaImportUtil(jarPath, dirPath);
	if (retVal > 0)
	{
		F_Printf(NULL,"JVM Initiliazation error\n");
		writeToChannel("Error. JVM Intialization error");
	}

	F_Free(&jarPath);
	F_ApiDeallocateString(&tempPath);

	return !retVal;
}

BoolT cleanImportDirectory(StringT dirPath)
{
	FilePathT *filedirPath, *file;
	DirHandleT handle;
	StringT tmpPath;
	IntT statusp;

	filedirPath = F_PathNameToFilePath (dirPath, NULL, FDosPath);
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

	F_Free(&handle);
	F_ApiDeallocateString(&tmpPath);
	F_Free(&statusp);
	F_FilePathFree(file);
	F_FilePathFree(filedirPath);

	return 1;
}
VoidT openFilesInDirectory(StringT path)
{
	StringT bookPath, dirPath;
	F_ObjHandleT bookID;

	dirPath = F_StrCopyString(path);
	pathFilename(dirPath);
	if (!(bookID = openMainBook(dirPath))) return;
	if (!addExistingDocs(dirPath,bookID)) return;
	bookPath = F_ApiGetString(FV_SessionId,bookID,FP_Name);
	F_ApiSimpleGenerate(bookID,False,True);
	F_ApiSimpleSave(bookID,bookPath,False); 

	F_ApiDeallocateString(&bookPath);
	F_ApiDeallocateString(&dirPath);
	F_Free(&bookID);
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

	F_Free(&i);

	return params;
}
F_PropValsT generateImportParams()
{
	F_PropValsT params;
	IntT i;

	params = F_ApiGetOpenDefaultParams();
	if (!params.len)
	{
		F_ApiAlert("Default params error",FF_ALERT_CONTINUE_NOTE);
	}
	i = F_ApiGetPropIndex(&params,FS_StructuredOpenApplication);
	params.val[i].propVal.u.sval = "DocLine";
	i = F_ApiGetPropIndex(&params,FS_DontNotifyAPIClients);
	params.val[i].propVal.u.ival = False;
	i = F_ApiGetPropIndex(&params,FS_RefFileNotFound);
	params.val[i].propVal.u.ival = FV_AllowAllRefFilesUnFindable;

	F_Free(&i);

	return params;
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
			F_Sprintf(newFilePathName,"%s%s_%d.fm",workDirPath,attr.values.val[0],fileNum);
			fileNum++;
			F_ApiSimpleSave(fileID,newFilePathName,FALSE);

			//F_ApiDeallocateString(&newFilePathName);
			//F_ApiDeallocateAttribute(&attr);
			//F_Free(&i);
			//F_ApiDeallocateAttributes(&attrs);
			//F_Free (&highID);

			return TRUE;
		}
	}

	//F_ApiDeallocateString(&newFilePathName);
	//F_ApiDeallocateAttribute(&attr);
	//F_Free(&i);
	//F_ApiDeallocateAttributes(&attrs);
	//F_Free (&highID);

	return FALSE;
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
		writeToChannel("Error. Invalid book.\n");
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
					writeToChannel("Error. Id attribute error\n");
					continue;
				}

				newCompPath = F_Alloc(F_StrLen(attr.values.val[0])+F_StrLen(dirPath)+10,NO_DSE);
				F_Sprintf(newCompPath,"%s%s.fm",dirPath,attr.values.val[0]);
				i = 0;
				while (F_RenameFile(F_PathNameToFilePath(compPath,NULL,FDosPath),F_PathNameToFilePath(newCompPath,NULL,FDosPath)) != FdeSuccess)
				{
					F_Sprintf(newCompPath,"%s%s%d.fm",dirPath,attr.values.val[0],i);
					//F_Printf(NULL,"%s\n",newCompPath);
					i++;
				}
				F_ApiSetString(bookID,compID,FP_Name,newCompPath);
				F_ApiDeallocateString(&newCompPath);
			}
		}
		compID = F_ApiGetId(bookID,compID,FP_NextComponentInBook);
	}

	F_Free(&compID);
	//F_ApiDeallocateAttribute(&attr);
	F_ApiDeallocateAttributes(&attrs);
	F_ApiDeallocateString(&compPath);
	F_ApiDeallocateString(&dirPath);
	F_Free(&i);
	F_Free(&j);
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
	F_Free(&bookID);
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
	F_Free(&statusp);
	F_ApiDeallocateString(&tmpPath);
	F_FilePathFree(file);
	F_FilePathFree(filepath);
	F_Free(&handle);

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
	params = generateOpenWithUnresolvedRefsParams();
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
			if (F_StrIEqual(attr.values.val[0],(StringT)"documentation_core")) 
			{
				place = "DocumentationCore";
			}
			else if (F_StrIEqual(attr.values.val[0],(StringT)"product_documentation"))
			{
				place = "ProductDocumentation";
			}
			else if (F_StrIEqual(attr.values.val[0],(StringT)"product_line"))
			{
				place = "ProductLine";
			}
			else
			{
				place = "";
			}
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
	F_ApiNewBookComponentInHierarchy(bookID,path,&loc);
	F_ApiSimpleSave(fileID,path,FALSE);
	F_ApiClose(fileID,FF_CLOSE_MODIFIED);

	return TRUE;
}

//BoolT addExistingDoc(StringT path, F_ObjHandleT bookID)
//{
//	F_ObjHandleT compID, docID, elemID;
//	BoolT compExists;
//	StringT tmpPath2, fileName, place;
//	F_ElementLocT elemLoc;
//
//	compID = F_ApiGetId(FV_SessionId,bookID,FP_FirstComponentInBook);
//	compExists = False;
//	while ((compID != 0)&&(!compExists))
//	{
//		tmpPath2 = F_ApiGetString(bookID,compID,FP_Name);
//		compExists =  F_StrIEqual(fileFileName(path),fileFileName(tmpPath2));
//		compID = F_ApiGetId(bookID,compID,FP_NextComponentInBook);
//	}
//	if (!compExists)
//	{
//		docID = F_ApiSimpleOpen(path,False);
//		fileName = getHighestString(docID);
//		F_ApiClose(docID,FF_CLOSE_MODIFIED);
//		place = getPlace(fileName);
//		if (F_StrIEqual(place,(StringT)""))
//		{
//			compID = F_ApiNewSeriesObject(bookID,FO_BookComponent,0);
//			F_ApiSetString(bookID,compID,FP_Name,path);
//		}
//		else
//		{
//			compID = F_ApiGetId(FV_SessionId,bookID,FP_HighestLevelElement);
//			//compID = F_ApiGetId(bookID,F_ApiGetId(FV_SessionId,bookID,FP_MainFlowInDoc),FP_HighestLevelElement);
//			if (!compID)
//			{
//				F_Printf(NULL,"OpenFiles:\n\tHighest element error\n");
//				writeToChannel("Error. Highest level element error.\n");
//				return 0;
//			}
//			else
//			{
//				compID = componentExists(bookID,compID,place);
//				if (!compID)
//				{
//					elemID = F_ApiGetNamedObject(bookID,FO_ElementDef,place);
//					elemLoc.parentId = F_ApiGetId(FV_SessionId,bookID,FP_HighestLevelElement);
//					elemLoc.childId = 0;
//					elemLoc.offset = 0;
//					compID = F_ApiNewElementInHierarchy(bookID,elemID,&elemLoc);
//				}
//				elemLoc.parentId = compID;
//				elemLoc.offset = 0;
//				elemLoc.childId = 0;
//				compID = F_ApiNewBookComponentInHierarchy(bookID,path,&elemLoc);
//			}
//		}
//	}
//
//	F_Free(&compID);
//	F_Free(&elemLoc);
//	F_Free(&elemID);
//	F_ApiDeallocateString(&place);
//	F_ApiDeallocateString(&fileName);
//	F_Free(&docID);
//	F_Free(&compExists);
//	F_ApiDeallocateString(&tmpPath2);//
//
//	return 1;
//}
//
//
