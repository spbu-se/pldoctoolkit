#include "export.h"

BoolT exportBook(StringT path, StringT dirPath, F_PropValsT params, UIntT* j)
{
	IntT docID, elemID;
	UIntT k;
	F_AttributesT attrs;
	F_AttributeT attr;
	F_PropValsT *returnParams;
	StringT curFilePath;

	docID = F_ApiSimpleOpen(path,False);
	elemID = F_ApiGetId(FV_SessionId,docID,FP_HighestLevelElement);
	if (!elemID)
	{
		F_Printf(NULL,"No highest level elment in %s",path);
		return 0;
	}
	attrs = F_ApiGetAttributes(docID,elemID);
	for (k=0; k<attrs.len; k++)
	{
		attr = attrs.val[k];
		if (F_StrIEqual(attr.name,(StringT)"FileName"))
		{
			if (!attr.values.len || !attr.values.val[0])
			{
				curFilePath = F_Alloc(F_StrLen(path)+F_StrLen(dirPath)+10,NO_DSE);
				F_Sprintf(curFilePath,"%sfile%d.drl",dirPath,*j);
				(*j)++;
			}
			else
			{
				curFilePath = F_Alloc(F_StrLen(path)+F_StrLen(dirPath)+F_StrLen(attr.values.val[0])+5,NO_DSE);
				F_Sprintf(curFilePath,"%s%s",dirPath,attr.values.val[0]);
			}
		}
		F_ApiDeallocateString(&curFilePath);
	}
	returnParams = NULL;
	F_ApiSave(docID,path,&params,&returnParams);
	F_ApiClose(docID,FF_CLOSE_MODIFIED);

	F_ApiDeallocatePropVals(returnParams);
	//F_ApiDeallocateAttribute(&attr);
	F_Free(&k);
	F_ApiDeallocateAttributes(&attrs);
	F_Free(&elemID);
	F_Free(&docID);

	return 1;
}
BoolT performExportXSLT(StringT dirPath)
{
	StringT tempPath;
	UCharT jarPath[256];
	IntT retVal;

	tempPath = F_ApiClientDir();
	F_Sprintf(jarPath, "%s\\%s", tempPath, JAR_FILENAME);
	retVal = callJavaExportUtil(jarPath,dirPath);
	if (retVal)
	{
		F_Printf(NULL,"JVM Initiliazation error\n");
	}

	F_Free(&jarPath);
	F_ApiDeallocateString(&tempPath);

	return !retVal;
}
VoidT exportDocLineDoc()
{
	F_ObjHandleT bookID;
	F_PropValsT params;
	IntT j, statusp;
	DirHandleT handle;
	FilePathT *filePath;
	StringT path, dirPath;


	bookID = getActiveBookID();
	if (!bookID)
	{
		F_Printf(NULL,"Invalid document\n");
		return;
	}
	dirPath = F_ApiGetString(FV_SessionId,bookID,FP_Name);
	pathFilename(dirPath); //Since this point dirPath and bookID should be constants

	params = generateExportParams();
	generateBooks(bookID);

	filePath = F_PathNameToFilePath(dirPath,NULL,FDefaultPath);
	handle = F_FilePathOpenDir(filePath,&statusp);
	j = 0;
	//iteration through generated books in current directory
	while (filePath = F_FilePathGetNext(handle,&statusp))
	{
		path = F_FilePathToPathName(filePath,FDosPath);
		if (validateFilename(path,GENBOOK))
		{
			exportBook(path,dirPath,params, &j);
		}
	}
	performExportXSLT(dirPath);
	closeAllDocs();
	cleanDirectory(F_PathNameToFilePath(dirPath,NULL,FDosPath));

	F_ApiDeallocateString(&path);
	F_Free(&j);
	F_Free(&statusp);
	F_FilePathCloseDir(handle);
	F_Free(&handle);
	F_Free(filePath);
	F_ApiDeallocatePropVals(&params);
	F_ApiDeallocateString(&dirPath);
	F_Free(&bookID);
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

F_PropValsT generateExportParams()
{
	F_PropValsT params;
	IntT i;

	params = F_ApiGetSaveDefaultParams();
	if (!params.len)
	{
		F_Printf(NULL,"Invalid default save params");
		return params;
	}
	i = F_ApiGetPropIndex(&params,FS_FileType);
	params.val[i].propVal.u.ival = FV_SaveFmtXml;
	i = F_ApiGetPropIndex(&params,FS_StructuredSaveApplication);
	params.val[i].propVal.u.sval = "DocLine";

	F_Free(&i);

	return params;
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
	//My code
	//compID = F_ApiGetId(FV_SessionId, bookID, FP_HighestLevelElement);
	//compID = F_ApiGetId(bookID, compID, FP_FirstChildElement);
	//while (compID){
	//	attrs = F_ApiGetAttributes(bookID,compID);
	//	for (i=0; i<attrs.len; i++)
	//	{
	//		attr = attrs.val[i];
	//		if (F_StrIEqual(attr.name, "FileName") && attr.values.len)
	//		{
	//			filePath = F_PathNameToFilePath(attr.values.val[0],NULL,FDosPath);
	//			if (!F_FilePathProperty(filePath,FF_FilePathExist))
	//			{
	//				exportBook(
	//			}
	//		}
	//	}
	//}
	// My code end
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
