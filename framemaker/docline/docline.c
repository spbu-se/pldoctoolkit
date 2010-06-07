#include "docline.h"

#include "cutils.h"

#include "string.h"
#include "ctype.h"

#include "publishutil.h"
#include "import.h"
#include "export.h"
#include "structure.h"
#include "check.h"

#define STRUCTAPP_TEMPLATE_FILENAME "stuctapp_entry.fm"
#define STRUCTAPP_NAME "DocLine"
#define STRUCTURE_DIR "xml\\docline" //base filePath - framemaker structure dir

VoidT addStructApp()
{
  F_ObjHandleT structAppFm, templateID, topID, secondID, newChildID, thirdID, ttopID, tsecondID, elemDef;
  StringT structAppFmPath, fminitDir, pluginDirName, structAppFmName, elemName, docLineElemName, doclineDir;
  FilePathT *clientDirPath, *fmPath;
  IntT statusp;
  F_TextRangeT trange;
  F_TextItemsT tis;
  BoolT flag;
  F_ElementLocT loc;

  //open structapps.fm
  fminitDir = F_ApiClientDir();
  clientDirPath = F_PathNameToFilePath(fminitDir,NULL,FDosPath);
  F_ApiDeallocateString(&fminitDir);
  if (clientDirPath == NULL) return;
  fmPath = F_FilePathParent(clientDirPath,&statusp);
  F_FilePathFree(clientDirPath);
  fminitDir = F_FilePathToPathName(fmPath,FDosPath);
  F_FilePathFree(fmPath);
  pluginDirName = F_StrCopyString("Structure");
  structAppFmName = F_StrCopyString("structapps.fm");
  structAppFmPath = (StringT)F_Alloc((F_StrLen(fminitDir)+F_StrLen(pluginDirName)+F_StrLen(structAppFmName)+3)*sizeof(UCharT),NO_DSE);
  F_Sprintf(structAppFmPath, "%s\\%s\\%s", fminitDir,pluginDirName,structAppFmName);
  F_ApiDeallocateString(&structAppFmName);
  structAppFm = F_ApiSimpleOpen(structAppFmPath,FALSE);
  F_ApiDeallocateString(&structAppFmPath);

  topID = F_ApiGetId(structAppFm,F_ApiGetId(FV_SessionId,structAppFm,FP_MainFlowInDoc),FP_HighestLevelElement);
  if (!topID)
  {
    F_ApiAlert("Error. Empty structapps.fm", FF_ALERT_CONTINUE_WARN);
    return;
  }
  docLineElemName = F_StrCopyString(STRUCTAPP_NAME);
  flag = FALSE;
  secondID = F_ApiGetId(structAppFm,topID,FP_FirstChildElement);
  //check if structure  application "DocLine" exists
  //while (!flag && secondID)
  while (FALSE)
  {
    thirdID = F_ApiGetId(structAppFm,secondID,FP_FirstChildElement);
    if (!thirdID)
    {
      F_Printf(NULL,"\nEmpty entry in structapps.fm");
      secondID = F_ApiGetId(structAppFm,secondID,FP_NextSiblingElement);
      continue;
    }
    trange = F_ApiGetTextRange(structAppFm,thirdID,FP_TextRange);
	tis = F_ApiGetTextForRange(structAppFm,&trange,FTI_String);
	if (tis.len < 1)
	{
      secondID = F_ApiGetId(structAppFm,secondID,FP_NextSiblingElement);
      continue;
	}
	elemName = tis.val[1].u.sdata;
    if (F_StrIEqual(elemName, docLineElemName))
    {
      flag = TRUE;
    }
    F_ApiDeallocateString(&elemName);
    secondID = F_ApiGetId(structAppFm,secondID,FP_NextSiblingElement);
  }
  if (!flag)
  {
	  //F_ApiImport()
  }
  F_ApiDeallocateString(&pluginDirName);
  F_ApiDeallocateString(&fminitDir);
}

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

	  newMenuId = F_ApiDefineAndAddMenu(menuId,"NewMenuId", "New");
	  /* Define some commands and add them to the New-> menu. */
	  newProjectId = F_ApiDefineAndAddCommand(BOOK, newMenuId, "NewDocLineProject", "Docline project", "\\!BNP");
	  /* Add seperator after the New docline project command*/
	  separatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "NewSeparator");
	  F_ApiAddCommandToMenu(newMenuId, separatorId);	
	  /* Define and add DocumentationCore menu to the "New" menu. */
	  docCoreId = F_ApiDefineAndAddMenu(newMenuId, "NewDocCoreMenu", "DocumentationCore");
	  /* Define and add comand for adding new DocumentationCore section to the project */
	  newDocCoreSectionId = F_ApiDefineAndAddCommand(CORE, docCoreId, "NewDocCoreSection", "DocumentationCore section", "\\!NDC");
	  /* Add seperator after new DocumentationCore Section command*/
	  coreSeparatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "CoreSeparator");
	  F_ApiAddCommandToMenu(docCoreId, coreSeparatorId);	
	  /* Define "New *SpecificElement*" commands and add them to the DocumentationCore menu. */
	  newDictId = F_ApiDefineAndAddCommand(NEWDC, docCoreId, "NewDictionary", "Dictionary", "\\!BND");
	  newDirectId = F_ApiDefineAndAddCommand(NEWDI, docCoreId, "NewDirectory", "Directory", "\\!BDI");
	  newDirTempId = F_ApiDefineAndAddCommand(NEWDT, docCoreId, "NewDirTemplate", "DirTemplate", "\\!BDT");
	  newInfElemId = F_ApiDefineAndAddCommand(NEWIE, docCoreId, "NewInfElement", "InfElement", "\\!BNE");
	  newInfProdId = F_ApiDefineAndAddCommand(NEWIP, docCoreId, "NewInfProduct", "InfProduct", "\\!BNP");

	  /* Define and add ProductDocumentation menu to the "New" menu. */
	  prodDocId = F_ApiDefineAndAddMenu(newMenuId, "NewProdDocMenu", "ProductDocumentation");
	  /* Define and add comand for adding new ProductDocumentation section to the project */
	  newProdDocSectionId = F_ApiDefineAndAddCommand(PROD, prodDocId, "NewProdDocSection", "ProductDocumentation section", "\\!NPD");
	  /* Add seperator after new ProductDocumentation Section command*/
	  prodSeparatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "ProdSeparator");
	  F_ApiAddCommandToMenu(prodDocId, prodSeparatorId);	
	  /* Define "New *SpecificElement*" commands and add them to the ProductDocumentation menu. */
	  prodDictId = F_ApiDefineAndAddCommand(PNEWDC, prodDocId, "PNewDictionary", "Dictionary", "\\!PND");
	  prodDirectId = F_ApiDefineAndAddCommand(PNEWDI, prodDocId, "PNewDirectory", "Directory", "\\!PDI");
	  prodDirTempId = F_ApiDefineAndAddCommand(PNEWDT, prodDocId, "PNewDirTemplate", "DirTemplate", "\\!PDT");
	  finalInfProdId = F_ApiDefineAndAddCommand(PNEWIP, prodDocId, "PNewInfProduct", "FinalInfProduct", "\\!PIP");

	  /* Define and add ProductLine menu to the "New" menu. */
	  prodLineId = F_ApiDefineAndAddMenu(newMenuId, "NewProdLineMenu", "ProductLine");
	  /* Define and add comand for adding new ProductLine section to the project */
	  newProdLineSectionId = F_ApiDefineAndAddCommand(LINE, prodLineId, "NewProdLineSection", "ProductLine section", "\\!NPL");
	  /* Add seperator after new ProductLine Section command*/
	  lineSeparatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "LineSeparator");
	  F_ApiAddCommandToMenu(prodLineId, lineSeparatorId);	
	  /* Define "New *SpecificElement*" commands and add them to the ProductLine menu. */
	  productId = F_ApiDefineAndAddCommand(NEWPR, prodLineId, "NewProduct", "Product", "\\!BNP");

	  /* Define some commands and add them to the Open-> menu. */
	  sopenIFmD = F_ApiDefineAndAddCommand(SOPEN, menuId, "SimpleOpenDoclineProject","Open", "\\!IV");

	  /* Define command for importing DocLine project from existing DRL documentation. */
	  importDrlID = F_ApiDefineAndAddCommand(IMPORT, menuId, "Import", "Import","\\!BOO");

	  /* Define and add the Save As...-> menu to the DocLine menu. */
	  saveMenuId = F_ApiDefineAndAddMenu(menuId, "SaveDocLineMenu", "Publish");
	  /* Define some commands and add them to the Save As...-> menu. */
	  saveDoclineAsHtmlId = F_ApiDefineAndAddCommand(SAVEHT, saveMenuId, "SaveDoclineAsHtml", "HTML", "\\!BHT");
	  saveDoclineAsPdfId = F_ApiDefineAndAddCommand(SAVEPD, saveMenuId, "SaveDoclineAsPdf", "PDF", "\\!BPD");

	  /* Define command for exporting active project back to DRL */
	  exportProjectId = F_ApiDefineAndAddCommand(EXPORT, menuId, "ExportProject", "Export","\\!BEP");

	  /* Define command for checking correctness of active project */
	  checkCorrectId = F_ApiDefineAndAddCommand(CHECK, menuId, "CheckCorrect", "Check correctness","\\!CC");

	  /* Define command for closing active project and all files in it */
	  closeProjectId = F_ApiDefineAndAddCommand(CLOSE, menuId, "CloseProject", "Close Project","\\!BCP");

	  /* Get the ID of the FrameMaker book menu bar. */
	  bmenubarId = F_ApiGetNamedObject(FV_SessionId, FO_Menu, "!BookMainMenu");
	  /* Define and add the DocLine menu to the main menu. */
	  bmenuId = F_ApiDefineAndAddMenu(bmenubarId, "BDocLineMenu", "DocLine");

	  bnewMenuId = F_ApiDefineAndAddMenu(bmenuId,"BNewMenu","New");
	  /* Define some commands and add them to the New-> menu. */
	  bnewProjectId = F_ApiDefineAndAddCommand(BBOOK, bnewMenuId, "BNewDocLineProject", "Docline project", "\\!BNP");
	  /* Add seperator after the New docline project command*/
	  bseparatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "BNewSeparator");
	  F_ApiAddCommandToMenu(bnewMenuId, bseparatorId);	
	  /* Define and add DocumentationCore menu to the "New" menu. */
	  bdocCoreId = F_ApiDefineAndAddMenu(bnewMenuId, "BNewDocCoreMenu", "DocumentationCore");
	  /* Define and add comand for adding new DocumentationCore section to the project */
	  bnewDocCoreSectionId = F_ApiDefineAndAddCommand(BCORE, bdocCoreId, "BNewDocCoreSection", "DocumentationCore section", "\\!NDC");
	  /* Add seperator after new DocumentationCore Section command*/
	  bcoreSeparatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "CoreSeparator");
	  F_ApiAddCommandToMenu(bdocCoreId, bcoreSeparatorId);	
	  /* Define "New *SpecificElement*" commands and add them to the DocumentationCore menu. */
	  bnewDictId = F_ApiDefineAndAddCommand(BNEWDC, bdocCoreId, "BNewDictionary", "Dictionary", "\\!BND");
	  bnewDirectId = F_ApiDefineAndAddCommand(BNEWDI, bdocCoreId, "BNewDirectory", "Directory", "\\!BDI");
	  bnewDirTempId = F_ApiDefineAndAddCommand(BNEWDT, bdocCoreId, "BNewDirTemplate", "DirTemplate", "\\!BDT");
	  bnewInfElemId = F_ApiDefineAndAddCommand(BNEWIE, bdocCoreId, "BNewInfElement", "InfElement", "\\!BNE");
	  bnewInfProdId = F_ApiDefineAndAddCommand(BNEWIP, bdocCoreId, "BNewInfProduct", "InfProduct", "\\!BNP");

	  /* Define and add ProductDocumentation menu to the "New" menu. */
	  bprodDocId = F_ApiDefineAndAddMenu(bnewMenuId, "BNewProdDocMenu", "ProductDocumentation");
	  /* Define and add comand for adding new ProductDocumentation section to the project */
	  bnewProdDocSectionId = F_ApiDefineAndAddCommand(BPROD, bprodDocId, "BNewProdDocSection", "ProductDocumentation section", "\\!NPD");
	  /* Add seperator after new ProductDocumentation Section command*/
	  bprodSeparatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "BProdSeparator");
	  F_ApiAddCommandToMenu(bprodDocId, bprodSeparatorId);	
	  /* Define "New *SpecificElement*" commands and add them to the ProductDocumentation menu. */
	  bprodDictId = F_ApiDefineAndAddCommand(BPNEWDC, bprodDocId, "BPNewDictionary", "Dictionary", "\\!PND");
	  bprodDirectId = F_ApiDefineAndAddCommand(BPNEWDI, bprodDocId, "BPNewDirectory", "Directory", "\\!PDI");
	  bprodDirTempId = F_ApiDefineAndAddCommand(BPNEWDT, bprodDocId, "BPNewDirTemplate", "DirTemplate", "\\!PDT");
	  bfinalInfProdId = F_ApiDefineAndAddCommand(BPNEWIP, bprodDocId, "BPNewInfProduct", "FinalInfProduct", "\\!PIP");

	  /* Define and add ProductLine menu to the "New" menu. */
	  bprodLineId = F_ApiDefineAndAddMenu(bnewMenuId, "BNewProdLineMenu", "ProductLine");
	  /* Define and add comand for adding new ProductLine section to the project */
	  bnewProdLineSectionId = F_ApiDefineAndAddCommand(BLINE, prodLineId, "BNewProdLineSection", "ProductLine section", "\\!NPL");
	  /* Add seperator after new ProductLine Section command*/
	  blineSeparatorId = F_ApiNewNamedObject(FV_SessionId, FO_MenuItemSeparator, "BLineSeparator");
	  F_ApiAddCommandToMenu(bprodLineId, blineSeparatorId);	
	  /* Define "New *SpecificElement*" commands and add them to the ProductLine menu. */
	  bproductId = F_ApiDefineAndAddCommand(BNEWPR, bprodLineId, "BNewProduct", "Product", "\\!BNP");

	  /* Define some commands and add them to the Open-> menu. */
	  bsopenIFmD = F_ApiDefineAndAddCommand(BSOPEN, bmenuId, "BSimpleOpenDoclineProject","Open", "\\!IV");

	  /* Define command for importing DocLine project from existing DRL documentation. */
	  bimportDrlID = F_ApiDefineAndAddCommand(BIMPORT, bmenuId, "BImport", "Import","\\!BOO");

	  /* Define and add the Save As...-> menu to the DocLine menu. */
	  bsaveMenuId = F_ApiDefineAndAddMenu(bmenuId, "BSaveDocLineMenu", "Publish");
	  /* Define some commands and add them to the Save As...-> menu. */
	  bsaveDoclineAsHtmlId = F_ApiDefineAndAddCommand(BSAVEHT, bsaveMenuId, "BSaveDoclineAsHtml", "HTML", "\\!BHT");
	  bsaveDoclineAsPdfId = F_ApiDefineAndAddCommand(BSAVEPD, bsaveMenuId, "BSaveDoclineAsPdf", "PDF", "\\!BPD");

	  /* Define command for exporting active project back to DRL */
	  bexportProjectId = F_ApiDefineAndAddCommand(BEXPORT, bmenuId, "BExportProject", "Export","\\!BEP");

	  /* Define command for checking correctness of active project */
	  bcheckCorrectId = F_ApiDefineAndAddCommand(BCHECK, bmenuId, "BCheckCorrect", "Check correctness","\\!CCB");

	  /* Define command for closing active project and all files in it */
	  bcloseProjectId = F_ApiDefineAndAddCommand(BCLOSE, bmenuId, "BCloseProject", "Close Project","\\!BCP");
    //addStructApp();
	  
	  F_ApiNotification(FA_Note_PostActiveDocChange,TRUE);
	}
}

BoolT getDoclineFmDocType(IntT *type)
{
	F_ObjHandleT docID, highID, elemDef;
	StringT defName;
	BoolT isThird;
	IntT i;

	if (docID = F_ApiGetId(0, FV_SessionId, FP_ActiveBook)) //Opened document is book
	{
		highID = F_ApiGetId(FV_SessionId, docID, FP_HighestLevelElement);
	}
	else if (docID = F_ApiGetId(0, FV_SessionId, FP_ActiveDoc)) //Opened document is document
	{
		highID = F_ApiGetId(docID, F_ApiGetId(FV_SessionId, docID, FP_MainFlowInDoc), FP_HighestLevelElement);
	}
	else //No opened document
	{
		*type = 0;
		return TRUE;
	}
	if (!highID) //Unstructured document
	{
		*type = 0;
		return TRUE;
	}
	elemDef = F_ApiGetId(docID, highID, FP_ElementDef);
	if (!elemDef) return FALSE;
	defName = F_ApiGetString(docID, elemDef, FP_Name);
	if (F_StrIEqual(defName, HIGH_ELEM))
	{
		*type = FMBOOK;
		F_ApiDeallocateString(&elemDef);
		return TRUE;
	}
	else
	{
		StringT thirds[] = THIRD_ELEMENTS;
		for (i=0; i<THIRD_LENGTH; i++)
		{
			if (F_StrIEqual(thirds[i], defName))
			{
				*type = FM;
				F_ApiDeallocateString(&elemDef);
				return TRUE;
			}
		}
	}

	*type = 0; //Not DocLineFm document or book
	F_ApiDeallocateString(&elemDef);
	return TRUE;
}

VoidT DisableMenuItems(IntT type)
{
	F_ApiSetInt(FV_SessionId, checkCorrectId, FP_EnabledWhen, FV_ENABLE_ALWAYS_DISABLE);
	F_ApiSetInt(FV_SessionId, bcheckCorrectId, FP_EnabledWhen, FV_ENABLE_ALWAYS_DISABLE);
	switch (type)
	{
	case FMBOOK:
		F_ApiSetInt(bsaveMenuId, bsaveDoclineAsPdfId, FP_EnabledWhen, FV_ENABLE_ALWAYS_ENABLE);
		F_ApiSetInt(bsaveMenuId, bsaveDoclineAsHtmlId, FP_EnabledWhen, FV_ENABLE_ALWAYS_ENABLE);
		F_ApiSetInt(bmenuId, bcloseProjectId, FP_EnabledWhen, FV_ENABLE_ALWAYS_ENABLE);
		F_ApiSetInt(bmenuId, bexportProjectId, FP_EnabledWhen, FV_ENABLE_ALWAYS_ENABLE);
		break;
	case FM:
		F_ApiSetInt(saveMenuId, saveDoclineAsHtmlId, FP_EnabledWhen, FV_ENABLE_ALWAYS_ENABLE);
		F_ApiSetInt(saveMenuId, saveDoclineAsPdfId, FP_EnabledWhen, FV_ENABLE_ALWAYS_ENABLE);
		F_ApiSetInt(menuId, closeProjectId, FP_EnabledWhen, FV_ENABLE_ALWAYS_ENABLE);
		F_ApiSetInt(menuId, exportProjectId, FP_EnabledWhen, FV_ENABLE_ALWAYS_ENABLE);
		break;
	default:
		F_ApiSetInt(saveMenuId, saveDoclineAsHtmlId, FP_EnabledWhen, FV_ENABLE_ALWAYS_DISABLE);
		F_ApiSetInt(saveMenuId, saveDoclineAsPdfId, FP_EnabledWhen, FV_ENABLE_ALWAYS_DISABLE);
		F_ApiSetInt(bsaveMenuId, bsaveDoclineAsHtmlId, FP_EnabledWhen, FV_ENABLE_ALWAYS_DISABLE);
		F_ApiSetInt(bsaveMenuId, bsaveDoclineAsPdfId, FP_EnabledWhen, FV_ENABLE_ALWAYS_DISABLE);
		F_ApiSetInt(menuId, closeProjectId, FP_EnabledWhen, FV_ENABLE_ALWAYS_DISABLE);
		F_ApiSetInt(menuId, exportProjectId, FP_EnabledWhen, FV_ENABLE_ALWAYS_DISABLE);
		F_ApiSetInt(bmenuId, bsaveMenuId, FP_EnabledWhen, FV_ENABLE_ALWAYS_DISABLE);
		F_ApiSetInt(bmenuId, bcloseProjectId, FP_EnabledWhen, FV_ENABLE_ALWAYS_DISABLE);
		F_ApiSetInt(bmenuId, bexportProjectId, FP_EnabledWhen, FV_ENABLE_ALWAYS_DISABLE);
		break;
	}
	return TRUE;
}

VoidT F_ApiNotify(IntT notification, F_ObjHandleT docID, StringT sparm, IntT iparm)
{
	IntT type;
	switch (notification)
	{
	case FA_Note_PostActiveDocChange:
		type = NULL;
		if (!getDoclineFmDocType(&type)) return;
		DisableMenuItems(type);
		break;
	}
}

VoidT F_ApiCommand(IntT command)
{
	IntT response;
	/* Setting commands for handling menu items */
	switch(command) {
  case BOOK:
  case BBOOK:
	  closeProject();
	  createNewDocLineBook();
	  break;
  case CORE:
  case BCORE:
	  newSecondLevelSection(False, "DocumentationCore", NULL);
	  break;
  case NEWDC:
  case BNEWDC:
	  newDocCoreChild(DICTION);
	  break;
  case NEWDI:
  case BNEWDI:
	  newDocCoreChild(DIRECT);
	  break;
  case NEWDT:
  case BNEWDT:
	  newDocCoreChild(DIRTEMP);
	  break;
  case NEWIE:
  case BNEWIE:
	  newDocCoreChild(INFELEM);
	  break;
  case NEWIP:
  case BNEWIP:
	  newDocCoreChild(INFPROD);
	  break;
  case PROD:
  case BPROD:
	  newSecondLevelSection(False, "ProductDocumentation", NULL);
	  break;
  case BPNEWDC:
  case PNEWDC:
	  newProdDocChild(DICTION);
	  break;
  case BPNEWDI:
  case PNEWDI:
	  newProdDocChild(DIRECT);
	  break;
  case BPNEWDT:
  case PNEWDT:
	  newProdDocChild(DIRTEMP);
	  break;
  case BPNEWIP:
  case PNEWIP:
	  newProdDocChild(FINALINF);
	  break;
  case LINE:
  case BLINE:
	  newSecondLevelSection(False, "ProductLine", NULL);
	  break;
  case NEWPR:
  case BNEWPR:
	  newProdLineChild(PRODUCT);
	  break;
  case SOPEN:
  case BSOPEN:
	  closeProject();
	  simpleOpenBook();
	  break;
  case IMPORT:
  case BIMPORT:
	  closeProject();
	  importDocLineDoc();
	  break;
  case SAVEHT:
  case BSAVEHT:
	  publishDocLineDoc(HTML_FORMAT);
	  break;
  case SAVEPD:
  case BSAVEPD:
	  publishDocLineDoc(PDF_FORMAT);
	  break;
  case EXPORT:
  case BEXPORT:
	  exportDocLineDoc(FALSE);
	  break;
  case CHECK:
  case BCHECK:
	  checkDoclineProject();
	  break;
  case CLOSE:
  case BCLOSE:
	  /* Prompt user if he really wants to save all and close. */
	  response = F_ApiAlert("This will save all files and close them. Do you still wish to continue?",FF_ALERT_YES_DEFAULT);
	  if (!response)
		closeProject();
	  break;
	}
}
