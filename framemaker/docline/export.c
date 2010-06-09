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

	F_ElementLocT loc;
	F_ElementRangeT range;
	F_ObjHandleT defID;

	if (!isPublish && F_ApiAlert("This will export DocLineFM project and overwrite output directory. Do you still wish to continue?", FF_ALERT_YES_DEFAULT)) return;
	openLogChannel();
	writeToChannel("\nExport started...");
	if (!mainBookID) mainBookID = getActiveBookID();
	if (!isPublish && F_ApiChooseFile(&curDirPath, "Choose output directory", "", "", FV_ChooseOpenDir, "")) return;
	if (!cleanTempDirectory())
	{
		writeToChannel("\nCleaning directory error.");
		F_ApiDeallocateString(&curDirPath);
		return;
	}
	if (!getTempDirPath(&tempDirPath))
	{
		writeToChannel("\nInvalid temporary directory.");
		F_ApiDeallocateString(&curDirPath);
		return;
	}
	writeToChannel("\nGenerating export params... ");
	if (!generateExportParams(&params))
	{
		writeToChannel("\nError.");
		F_ApiDeallocateString(&curDirPath);
		return;
	}
	writeToChannel("Succesful.");
	writeToChannel("\nExporting books... ");
	name = F_StrCopyString(TEMP_BOOK_NAME);
	path = (StringT)F_Alloc((F_StrLen(tempDirPath)+F_StrLen(name)+1)*sizeof(UCharT),NO_DSE);
	F_Sprintf(path,"%s\\%s\0",tempDirPath,name);
	F_ApiDeallocateString(&name);
	returnParams = NULL;
	F_ApiSave(mainBookID,path,&params,&returnParams);
	F_ApiDeallocatePropVals(&params);
	F_ApiDeallocatePropVals(returnParams);
	writeToChannel("Succesful.");
	writeToChannel("\nPerforming XSL transformation... ");
	if (!performExportXSLT(tempDirPath))
	{
		writeToChannel("\nError.");
		F_ApiDeallocateString(&tempDirPath);
		F_ApiDeallocateString(&curDirPath);
		F_ApiDeallocateString(&path);
		return;
	}
	F_ApiDeallocateString(&tempDirPath);
	filePath = F_PathNameToFilePath(path,NULL,FDosPath);
	F_DeleteFile(filePath);
	F_FilePathFree(filePath);
	F_ApiDeallocateString(&path);
	writeToChannel("Succesful.\n");
	if (!correctFiles())
	{
		writeToChannel("\nError in correcting DRL files");
		F_ApiDeallocateString(&curDirPath);
		return;
	}
	writeToChannel("\nClosing all documents... ");
	closeAllDocs();
	writeToChannel("Succesful.");
	writeToChannel("\nCleaning directory... ");
	cleanDirectory(F_PathNameToFilePath(curDirPath,NULL,FDosPath));
	writeToChannel("Succesful.");
	writeToChannel("\nExport finished succesfully.");

	if (!isPublish)
	{
		closeLogChannel();
	}
	F_ApiDeallocateString(&curDirPath);
}

BoolT generateExportParams(F_PropValsT *params)
{
	IntT i;

	*params = F_ApiGetSaveDefaultParams();
	if (!(*params).len)
	{
		F_Printf(NULL,"Invalid default save params");
		writeToChannel("Error. Invalide default save params.\n");
		return FALSE;
	}
	i = F_ApiGetPropIndex(params,FS_FileType);
	(*params).val[i].propVal.u.ival = FV_SaveFmtXml;
	i = F_ApiGetPropIndex(params,FS_StructuredSaveApplication);
	(*params).val[i].propVal.u.sval = F_StrCopyString("DocLine");

	return TRUE;
}
VoidT publishDocLineDoc(StringT format)
{
	IntT retVal, response;

	UCharT statusMessage[256];

	StringT jarPath, tempPath, suggestName, errorFileName, errorMessage, errorPath, mainDrlFileName, sourceFileName, outFileName, sourceDirPath;
	F_ObjHandleT bookID;
	ChannelT chan;
	UCharT ptr[BUFFERSIZE];
	IntT numread;

	openLogChannel();
	writeToChannel("\nPublishing started...");
	// promt user if he is sure he wants to publish document
	response = F_ApiAlert("This will export all files to DRL and then publish them. Do you still wish to continue?", FF_ALERT_YES_DEFAULT);
	if (response != 0) // user clicked "no"
		return;

	//get path to jar file and check if jar exists
	if (!getJarFileName(&jarPath)) return FALSE;
	if((chan = F_ChannelOpen(F_PathNameToFilePath(jarPath, NULL, FDefaultPath),"r")) == NULL)
	{
		F_Sprintf(statusMessage, "Couldn't find %s. Reinstalling the application can solve this problem.", JAR_FILENAME);
		F_ApiAlert(statusMessage, FF_ALERT_CONTINUE_WARN);

		F_ApiDeallocateString(&jarPath);
		return;
	}
	F_ChannelClose(chan);
	mainBookID = getActiveBookID();
	if (!mainBookID || !F_StrISuffix(F_ApiGetString(FV_SessionId, mainBookID, FP_Name), defaultBookName))
	{
		writeToChannel("\nError. Invalid book");

		F_ApiDeallocateString(&jarPath);
		return;
	}
	//   promt for filename
	writeToChannel("Choosing Final Product file...");
	if (!getFinalInfProductNameByDialog(&mainDrlFileName))
	{
		F_ApiDeallocateString(&jarPath);
		return;
	}
	writeToChannel("Succesful.\n");
	if (!F_ApiAlert("Save exported files?", FF_ALERT_YES_DEFAULT))
	{
		if (F_ApiChooseFile(&sourceDirPath, "Choose output directory", "", "", FV_ChooseOpenDir, ""))
		{
			F_ApiDeallocateString(&mainDrlFileName);
			F_ApiDeallocateString(&jarPath);
			return;
		}
	}
	else
	{
		if (!getTempDirPath(&sourceDirPath))
		{
			F_ApiDeallocateString(&mainDrlFileName);
			F_ApiDeallocateString(&jarPath);
			return;
		}
	}	
	curDirPath = F_StrCopyString(sourceDirPath);
	//   save file name
	sourceFileName = (F_FilePathBaseName(F_PathNameToFilePath(mainDrlFileName, NULL, FDefaultPath)));
	F_ApiDeallocateString(&mainDrlFileName);

	// show dialog to choose published document name 
	//   form suggested save name
	suggestName = (StringT)F_Alloc((F_StrLen(sourceFileName)-F_StrLen(".drl") + F_StrLen(format) + 2)*sizeof(UCharT),NO_DSE);
	F_StrCpyN(suggestName, sourceFileName, F_StrLen(sourceFileName)-F_StrLen(".drl") + 1);
	F_StrCat(suggestName, ".");
	F_StrCat(suggestName, format);
	//   promt for save name
	response = F_ApiChooseFile(&outFileName, "Choose a name for a file", sourceDirPath, suggestName, FV_ChooseSave, "");
	F_ApiDeallocateString(&suggestName);
	if (response != 0)
	{
		F_ApiDeallocateString(&outFileName);
		F_ApiDeallocateString(&jarPath);
		return;
	}

	//export documentation
	exportDocLineDoc(TRUE);

	// invoke java util
	writeToChannel("Calling Java function...");
	retVal = callJavaPublishUtil(jarPath, sourceDirPath, sourceFileName, " ", format, outFileName);
	F_ApiDeallocateString(&sourceFileName);
	F_ApiDeallocateString(&sourceDirPath);
	F_ApiDeallocateString(&outFileName);
	F_ApiDeallocateString(&jarPath);
	if (retVal > 0)  // java util worked with error
	{
		switch (retVal)
		{
		case JVM_INIT_MEM_ERROR:
			writeToChannel("\n\t");
			errorMessage = (StringT)F_Alloc((F_StrLen(ERROR_MEM_MESSAGE)+F_StrLen(MAX_MEM))*sizeof(UCharT),NO_DSE);
			F_Sprintf(errorMessage,ERROR_MEM_MESSAGE,MAX_MEM);
			writeToChannel("\n");
			writeToChannel(errorMessage);
			F_ApiDeallocateString(&errorMessage);
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
		errorPath = F_ApiClientDir();
		errorFileName = F_Alloc(F_StrLen(errorPath) + PLUGIN_DIR_NAME + F_StrLen(ERROR_LOG_FILENAME) + 1, NO_DSE);
		F_Sprintf(errorFileName, "%s\\%s\\%s\0", errorPath, PLUGIN_DIR_NAME, ERROR_LOG_FILENAME);
		F_ApiDeallocateString(&errorPath);

		if((chan = F_ChannelOpen(F_PathNameToFilePath(errorFileName, NULL, FDefaultPath),"r")) == NULL)
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
					numread = F_ChannelRead(ptr, sizeof(UCharT), BUFFERSIZE-1, chan);
					ptr[numread] = '\0';
					writeToChannel("\n");
					writeToChannel((StringT)ptr);
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
					writeToChannel("\nPublication error.");
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
		F_ApiDeallocateString(&errorFileName);
	}
	writeToChannel("\nPublication complted");
	closeLogChannel();
}
