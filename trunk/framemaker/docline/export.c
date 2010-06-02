#include "export.h"
#include "logging.h"

#define TEMP_BOOK_NAME "tmp_main_book.drl"

StringT curDirPath;
F_ObjHandleT mainBookID;

BoolT getFinalInfProductNameByDialog(StringT *fileName)
{
	F_ObjHandleT dlgID, highID, elemID, childID, *finals;
	F_StringsT finalInfProds;
	StringT elemName, childName, name;
	F_AttributesT attrs;
	F_AttributeT *attr;
	BoolT first;
	IntT i;

	dlgID = F_ApiOpenResource(FO_DialogResource, "PUBLISH");

	if (!mainBookID)
	{
		writeToChannel("\tNo main book\n");
		return FALSE;
	}
	highID = F_ApiGetId(FV_SessionId, mainBookID, FP_HighestLevelElement);
	if (!highID)
	{
		writeToChannel("\tNo highest element\n");
		return TRUE;
	}
	first = TRUE;
	elemID = F_ApiGetId(mainBookID,highID,FP_FirstChildElement);
	while (elemID)
	{
		elemName = F_ApiGetString(mainBookID,F_ApiGetId(mainBookID,elemID,FP_ElementDef),FP_Name);
		if (F_StrIEqual(elemName,"ProductDocumentation"))
		{
			childID = F_ApiGetId(mainBookID,elemID,FP_FirstChildElement);
			while (childID)
			{
				childName = F_ApiGetString(mainBookID,F_ApiGetId(mainBookID,childID,FP_ElementDef),FP_Name);
				if (F_StrIEqual(childName,(StringT)"FinalInfProduct"))
				{
					attrs = F_ApiGetAttributes(mainBookID,childID);
					for (i=0; i<attrs.len; i++)
					{
						attr = &(attrs.val[i]);
						if ((F_StrIEqual((*attr).name,"Id")) && ((*attr).values.len != 0))
						{
							if (first)
							{
								finalInfProds.len = 1;
								finalInfProds.val = (StringT *)F_Alloc(sizeof(StringT),NO_DSE);
								finals = (F_ObjHandleT *)F_Alloc(sizeof(F_ObjHandleT),NO_DSE);
								first = FALSE;
							}
							else
							{
								finalInfProds.len++;
								finalInfProds.val = (StringT *)F_Realloc(finalInfProds.val,finalInfProds.len*sizeof(StringT),NO_DSE);
								finals = (F_ObjHandleT *)F_Realloc(finals,finalInfProds.len*sizeof(F_ObjHandleT),NO_DSE);
							}
							finalInfProds.val[finalInfProds.len-1] = F_StrCopyString((*attr).values.val[0]);
							finals[finalInfProds.len-1] = elemID;
						}
					}
					F_ApiDeallocateAttributes(&attrs);
				}
				F_ApiDeallocateString(&childName);
				childID = F_ApiGetId(mainBookID,childID,FP_NextSiblingElement);
			}
		}
		F_ApiDeallocateString(&elemName);
		elemID = F_ApiGetId(mainBookID,elemID,FP_NextSiblingElement);
	}

	F_ApiSetStrings(dlgID, F_ApiDialogItemId(dlgID, FINALLIST), FP_Labels, &finalInfProds);
	F_ApiDeallocateStrings(&finalInfProds);
	/* Make the first item the default. */
	F_ApiSetInt(dlgID, F_ApiDialogItemId(dlgID, FINALLIST), FP_State, 0);
	F_ApiModalDialog(DLG_NUM, dlgID);

	//not OK button pressed
	if (F_ApiGetInt(dlgID, F_ApiDialogItemId(dlgID, OKBUTTON), FP_State) != True) return FALSE;
	elemID = finals[F_ApiGetInt(dlgID, F_ApiDialogItemId(dlgID, FINALLIST), FP_State)];
	if (!elemID) return FALSE;
	attrs = F_ApiGetAttributes(mainBookID,elemID);
	for (i=0; i<attrs.len; i++)
	{
		attr = &(attrs.val[i]);
		if (F_StrIEqual((*attr).name,"FileName") && ((*attr).values.len != 0))
		{
			(*fileName) = F_StrCopyString((*attr).values.val[0]);
			//name = F_StrCopyString((*attr).values.val[0]);
			//(*fileName) = F_Alloc((F_StrLen(curDirPath)+F_StrLen(name)+2)*sizeof(UCharT),NO_DSE);
			//F_Sprintf((*fileName),"%s\\%s\0",curDirPath,name);
			//F_ApiDeallocateString(&name);
			F_ApiDeallocateAttributes(&attrs);
			F_Free(finals);
			return TRUE;
		}
	}
	F_ApiDeallocateAttributes(&attrs);
	F_Free(finals);

	return FALSE;
}

BoolT performExportXSLT(StringT dirPath)
{
	StringT jarPath;
	IntT retVal;

	if (!getJarFileName(&jarPath)) return FALSE;
	retVal = callJavaExportUtil(jarPath,dirPath);
	if (retVal > 0)
	{
		switch (retVal)
		{
		case JVM_INIT_MEM_ERROR:
			writeToChannel("\n\t");
			F_ApiDeallocateString(&jarPath);
			jarPath = (StringT)F_Alloc((F_StrLen(ERROR_MEM_MESSAGE)+F_StrLen(MAX_MEM))*sizeof(UCharT),NO_DSE);
			F_Sprintf(jarPath,ERROR_MEM_MESSAGE,MAX_MEM);
			writeToChannel(jarPath);
			break;
		default:
			writeToChannel("Error. JVM Intialization error");
			break;
		}
		F_ApiDeallocateString(&jarPath);
		return FALSE;
	}
	F_ApiDeallocateString(&jarPath);
	if (retVal)
	{
		F_Printf(NULL,"JVM Initiliazation error\n");
		writeToChannel("Error. JVM Initialization error");
	}
	return !retVal;
}

BoolT correctFiles()
{
	DirHandleT handle;
	FilePathT *dirpath, *file;
	IntT statusp;
	StringT path, tmpDirPath, name, newPath;

	if (!getTempDirPath(&tmpDirPath)) return FALSE;
	dirpath = F_PathNameToFilePath(tmpDirPath,NULL,FDosPath);
	F_ApiDeallocateString(&tmpDirPath);
	handle = F_FilePathOpenDir(dirpath,&statusp);
	if (!handle)
	{
		F_FilePathFree(dirpath);
		return FALSE;
	}
	while(file = F_FilePathGetNext(handle,&statusp))
	{
		path = F_FilePathToPathName(file,FDosPath);
		if (validateFilename(path,DRL))
		{
			name = F_StrCopyString(fileFileName(F_StrCopyString(path)));
			newPath = (StringT)F_Alloc((F_StrLen(curDirPath)+F_StrLen(name)+3)*sizeof(UCharT),NO_DSE);
			F_Sprintf(newPath,"%s\\%s",F_StrCopyString(curDirPath),F_StrCopyString(name));
			if (!correctXMLNamespaces(path,newPath)) continue;
			F_ApiDeallocateString(&newPath);
			F_ApiDeallocateString(&name);
		}
		F_ApiDeallocateString(&path);
		F_FilePathFree(file);
	}
	F_FilePathFree(dirpath);

	return TRUE;
}

VoidT exportDocLineDoc(BoolT isPublish)
{
	F_ObjHandleT bookID;
	F_PropValsT params, *returnParams;
	IntT j, statusp;
	DirHandleT handle;
	FilePathT *filePath;
	StringT path, name, tempDirPath;

	if (!isPublish && F_ApiAlert("This will export DocLineFM project and overwrite output directory. Do you still wish to continue?", FF_ALERT_YES_DEFAULT)) return;
	openLogChannel();
	writeToChannel("\nExport started...\n");
	if (!mainBookID) mainBookID = getActiveBookID();
	if (!isPublish && F_ApiChooseFile(&curDirPath, "Choose output directory", "", "", FV_ChooseOpenDir, "")) return;
	if (!cleanTempDirectory())
	{
		F_ApiDeallocateString(&curDirPath);
		return;
	}
	if (!getTempDirPath(&tempDirPath))
	{
		F_ApiDeallocateString(&curDirPath);
		return;
	}
	writeToChannel("Generating export params... ");
	params = generateExportParams();
	writeToChannel("Succesful.\n");
	writeToChannel("Exporting books... ");
	name = F_StrCopyString(TEMP_BOOK_NAME);
	path = (StringT)F_Alloc((F_StrLen(tempDirPath)+F_StrLen(name)+1)*sizeof(UCharT),NO_DSE);
	F_Sprintf(path,"%s\\%s",F_StrCopyString(tempDirPath),F_StrCopyString(name));
	F_ApiDeallocateString(&name);
	returnParams = NULL;
	F_ApiSave(mainBookID,path,&params,&returnParams);
	writeToChannel("Succesful.\n");
	writeToChannel("Performing XSL transformation... ");
	if (!performExportXSLT(tempDirPath)) return;
	filePath = F_PathNameToFilePath(path,NULL,FDosPath);
	F_DeleteFile(filePath);
	F_FilePathFree(filePath);
	writeToChannel("Succesful.\n");
	//writeToChannel("Copying files from temporary...");
	//if (!copyFilesFromTempDirectory()) return;
	//writeToChannel("Succesful.\n");
	if (!correctFiles()) return;
	writeToChannel("Closing all documents... ");
	closeAllDocs();
	writeToChannel("Succesful.\n");
	writeToChannel("Cleaning directory... ");
	cleanDirectory(F_PathNameToFilePath(curDirPath,NULL,FDosPath));
	writeToChannel("Succesful.\n");
	writeToChannel("Export finished succesfully.\n");

	if (!isPublish)
	{
		F_ApiDeallocateString(&curDirPath);
		closeLogChannel();
	}

	F_ApiDeallocateString(&path);
	F_ApiDeallocatePropVals(&params);
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
			writeToChannel("Error. Convert error.\n");
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

F_PropValsT generateExportParams()
{
	F_PropValsT params;
	IntT i;

	params = F_ApiGetSaveDefaultParams();
	if (!params.len)
	{
		F_Printf(NULL,"Invalid default save params");
		writeToChannel("Error. Invalide default save params.\n");
		return params;
	}
	i = F_ApiGetPropIndex(&params,FS_FileType);
	params.val[i].propVal.u.ival = FV_SaveFmtXml;
	i = F_ApiGetPropIndex(&params,FS_StructuredSaveApplication);
	params.val[i].propVal.u.sval = F_StrCopyString("DocLine");

	//F_Free(&i);

	return params;
}
VoidT publishDocLineDoc(StringT format)
{
	IntT retVal, response;
	StringT jarPath; // = "C:\\Program Files (x86)\\Adobe\\FDK8\\samples\\docline\\debug\\exportutil.jar";
	UCharT sourceDirPath[256];
	UCharT sourceFileName[256];
	UCharT destinationFileName[256];

	UCharT statusMessage[256];

	StringT tempPath, tempName, tempResult;
	F_ObjHandleT bookID;
	ChannelT chan;
	UCharT ptr[BUFFERSIZE];
	IntT numread;

	openLogChannel();
	writeToChannel("\nPublishing started... \n");
	// promt user if he is sure he wants to publish document
	response = F_ApiAlert("This will export all files to DRL and then publish them. Do you still wish to continue?", FF_ALERT_YES_DEFAULT);
	if (response != 0) // user clicked "no"
		return;

	//get path to jar file and check if jar exists
	tempPath = F_ApiClientDir();
	if (!getJarFileName(&jarPath)) return FALSE;
	if((chan = F_ChannelOpen(F_PathNameToFilePath(jarPath, NULL, FDefaultPath),"r")) == NULL)
	{
		F_Sprintf(statusMessage, "Couldn't find %s. Reinstalling the application can solve this problem.", JAR_FILENAME);
		F_ApiAlert(statusMessage, FF_ALERT_CONTINUE_WARN);
		return;
	}
	F_ChannelClose(chan);
	mainBookID = getActiveBookID();
	if (!mainBookID || !F_StrISuffix(F_ApiGetString(FV_SessionId, mainBookID, FP_Name), defaultBookName))
	{
		F_ApiAlert("Invalid book", FF_ALERT_CONTINUE_NOTE);
		return;
	}
	//   promt for filename
	writeToChannel("Choosing Final Product file...");
	curDirPath = F_ApiGetString(FV_SessionId,mainBookID,FP_Name);
	pathFilename(curDirPath);
	if (!getFinalInfProductNameByDialog(&tempResult)) return;
	writeToChannel("Succesful.\n");
	if (!F_ApiAlert("Save exported files?", FF_ALERT_YES_DEFAULT))
	{
		if (F_ApiChooseFile(&tempPath, "Choose output directory", "", "", FV_ChooseOpenDir, "")) return;
	}
	else
	{
		if (!getTempDirPath(&tempPath)) return FALSE;
	}	
	curDirPath = F_StrCopyString(tempPath);
	//   save file name
	F_Sprintf(sourceFileName, "%s", F_FilePathBaseName(F_PathNameToFilePath(tempResult, NULL, FDefaultPath)));
	//   save dir name
	F_Sprintf(sourceDirPath, "%s", tempPath);
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
	//F_Free(tempPath);
	//F_Free(tempName);

	//export documentation
	exportDocLineDoc(TRUE);

	// invoke java util
	writeToChannel("Calling Java function...");
	retVal = callJavaPublishUtil(jarPath, sourceDirPath, sourceFileName, " ", format, destinationFileName);
	if (retVal > 0)
	{
		switch (retVal)
		{
		case JVM_INIT_MEM_ERROR:
			writeToChannel("\n\t");
			F_ApiDeallocateString(&jarPath);
			jarPath = (StringT)F_Alloc((F_StrLen(ERROR_MEM_MESSAGE)+F_StrLen(MAX_MEM))*sizeof(UCharT),NO_DSE);
			F_Sprintf(jarPath,ERROR_MEM_MESSAGE,MAX_MEM);
			writeToChannel(jarPath);
			break;
		default:
			writeToChannel("Error. JVM Intialization error");
			break;
		}
		return FALSE;
	}
	else  // java util worked, so there will be an error log file
	{
		writeToChannel("Succesful.\n");
		tempPath = F_ApiClientDir();
		tempResult = F_Alloc(F_StrLen(tempPath) + F_StrLen(ERROR_LOG_FILENAME) + 1, NO_DSE);
		F_Sprintf(tempResult, "%s\\%s", tempPath, ERROR_LOG_FILENAME);
		//F_Free(tempPath);

		if((chan = F_ChannelOpen(F_PathNameToFilePath(tempResult, NULL, FDefaultPath),"r")) == NULL)
		{
			F_Printf(NULL, "Couldn't find error log file.");
			writeToChannel("Error. Log file error.\n");
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
	closeLogChannel();
}
