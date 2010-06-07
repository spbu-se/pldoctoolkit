#include "structure.h"

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
	F_Sprintf(bookPath,"%s\\%s\0", path, defaultBookName);
	F_ApiDeallocateString(&path);
	F_ApiSimpleSave(bookId, bookPath, False);
	F_ApiDeallocateString(&bookPath);
}
BoolT newSecondLevelSection(BoolT isFirst, StringT type, StringT *newFileName)
{
	F_ObjHandleT bookId, sectionDlgId, compId, elemId;
	StringT fileName;
	F_AttributesT attributes;
	F_ElementLocT elemLoc;

	/* Get Id of the book */
	//bookId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveBook);
	bookId = getActiveBookID();
	/* Open resource for the Create new DocumentationCore || ProductDocumentation || ProductLine section dialog */
	sectionDlgId = F_ApiOpenResource(FO_DialogResource, "DOCCORE");	
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
	*newFileName = F_StrCopyString(fileName);
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
	//bookId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveBook);
	bookId = getActiveBookID();
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
		doccores.val = (StringT*) F_Alloc(2*sizeof(StringT), NO_DSE);
		doccores.len = 2;
		doccores.val[0] = F_StrCopyString("...");
		doccores.val[1] = F_StrCopyString("New...");
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
			if(!newSecondLevelSection(True, "DocumentationCore", NULL))
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

	if (F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State) == 1)
	{
		if(!newSecondLevelSection(True, "DocumentationCore", &selectedDocCore))
		{
			F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
			return;
		}
	}
	else
	{
		/* get Id of selected DocumentationCore section */
		selectedDocCore = doccores.val[F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State)];
	}
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
	//bookId = F_ApiGetId(FV_SessionId, FV_SessionId, FP_ActiveBook);
	bookId = getActiveBookID();





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
		proddocs.val = (StringT*) F_Alloc(2*sizeof(StringT), NO_DSE);
		proddocs.len = 2;
		proddocs.val[0] = F_StrCopyString("...");
		proddocs.val[1] = F_StrCopyString("New...");
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
			if(!newSecondLevelSection(True, "ProductDocumentation", NULL))
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

	if (F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State) == 1)
	{
		if(!newSecondLevelSection(True, "ProductDocumentation", &selectedProdDoc))
		{
			F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
			return;
		}
	}
	else
	{
		/* get Id of selected ProductDocumentation section */
		selectedProdDoc = proddocs.val[F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State)];
	}
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
		prodlines.val = (StringT*) F_Alloc(2*sizeof(StringT), NO_DSE);
		prodlines.len = 2;
		prodlines.val[0] = F_StrCopyString("...");
		prodlines.val[1] = F_StrCopyString("New...");
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
			if(!newSecondLevelSection(True, "ProductLine", NULL))
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

	if (F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State) == 1)
	{
		if(!newSecondLevelSection(True, "ProductLine", &selectedProdLine))
		{
			F_ApiClose (dlgId, FF_CLOSE_MODIFIED);
			return;
		}
	}
	else
	{
		/* get Id of selected ProductLine section */
		selectedProdLine = prodlines.val[F_ApiGetInt(dlgId, F_ApiDialogItemId(dlgId, 7), FP_State)];
	}
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