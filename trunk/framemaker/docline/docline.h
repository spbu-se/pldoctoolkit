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

#include "common.h"

#define BOOK 11
#define NEWDC 12
#define BNEWDC 121
#define NEWDI 13
#define BNEWDI 131
#define NEWDT 14
#define BNEWDT 141
#define NEWIE 15
#define BNEWIE 151
#define NEWIP 16
#define BNEWIP 161
#define BBOOK 21
#define BCORE 27
#define CORE 271
#define BNEWDC 22
#define NEWDC 221
#define BNEWDI 23
#define NEWDI 231
#define BNEWDT 24
#define NEWDT 241
#define BNEWIE 25
#define NEWIE 251
#define BNEWIP 26
#define NEWIP 261
#define BPROD 36
#define PROD 361
#define PNEWDC 32
#define BPNEWDC 321
#define PNEWDI 33
#define BPNEWDI 331
#define PNEWDT 34
#define BPNEWDT 341
#define PNEWIP 35
#define BPNEWIP 351
#define BLINE 47
#define LINE 471
#define BNEWPR 41
#define NEWPR 411
#define SAVEHT 101
#define BSAVEHT 1011
#define SAVEPD 102
#define BSAVEPD 1021
#define OPEN 103
#define BOPEN 113
#define IMPORT 104
#define BIMPORT 114
#define BSAVEHT 111
#define SAVEHT 1111
#define BSAVEPD 112
#define SAVEPD 1121
#define MAXSTRING 255

#define EXPORT 901
#define BEXPORT 911
#define CLOSE 902
#define BCLOSE 912
#define CHECK 904
#define BCHECK 905
#define SOPEN 913
#define BSOPEN 914
#define in ((MetricT) 65536*72)

// constants for publishing action
#define HTML_FORMAT "html"
#define PDF_FORMAT "pdf"

/* All the same for !MainMenu */
F_ObjHandleT menubarId, menuId; // !BookMainMenu and Docline menus
F_ObjHandleT newMenuId; // "New" submenu in the Docline menu
F_ObjHandleT docCoreId; // DocumentationCore submenu in the "New menu"
F_ObjHandleT prodDocId; // ProductDocumentation submenu in the "New menu"
F_ObjHandleT prodLineId; // ProductLine submenu in the "New menu"
F_ObjHandleT openMenuId; // "Open" submenu in the Docline menu
F_ObjHandleT sopenIFmD;//open existing fm docline project without checking
F_ObjHandleT saveMenuId; // "Publish" submenu in the Docline menu
F_ObjHandleT newProjectId, separatorId; // Some commands from the "New" submenu
F_ObjHandleT newDocCoreSectionId, coreSeparatorId, newDictId, newDirectId, newDirTempId, newInfElemId, newInfProdId; // Commands in "New DocumentationCore"
F_ObjHandleT newProdDocSectionId, prodSeparatorId, prodDictId, prodDirectId, prodDirTempId, finalInfProdId; // Commands in "New ProductDocumentation"
F_ObjHandleT newProdLineSectionId, lineSeparatorId, productId; // Commands in "New ProductLine"
F_ObjHandleT saveDoclineAsHtmlId, saveDoclineAsPdfId; // Commands from the "Save As..." submenu
F_ObjHandleT openFmID, importDrlID; // open exising fm docline project, import project from existing drl documentation
F_ObjHandleT closeProjectId; // menu item for closing active project and all files in it
F_ObjHandleT exportProjectId; // menu item for exporting active project back to DRL
F_ObjHandleT checkCorrectId; // menu item for checking project

/* All the same for !BookMainMenu */
F_ObjHandleT bmenubarId, bmenuId; // !BookMainMenu and Docline menus
F_ObjHandleT bnewMenuId; // "New" submenu in the Docline menu
F_ObjHandleT bdocCoreId; // DocumentationCore submenu in the "New menu"
F_ObjHandleT bprodDocId; // ProductDocumentation submenu in the "New menu"
F_ObjHandleT bprodLineId; // ProductLine submenu in the "New menu"
F_ObjHandleT bopenMenuId; // "Open" submenu in the Docline menu
F_ObjHandleT bsopenIFmD;//open existing fm docline project without checking
F_ObjHandleT bsaveMenuId; // "Publish" submenu in the Docline menu
F_ObjHandleT bnewProjectId, bseparatorId; // Some commands from the "New" submenu
F_ObjHandleT bnewDocCoreSectionId, bcoreSeparatorId, bnewDictId, bnewDirectId, bnewDirTempId, bnewInfElemId, bnewInfProdId; // Commands in "New DocumentationCore"
F_ObjHandleT bnewProdDocSectionId, bprodSeparatorId, bprodDictId, bprodDirectId, bprodDirTempId, bfinalInfProdId; // Commands in "New ProductDocumentation"
F_ObjHandleT bnewProdLineSectionId, blineSeparatorId, bproductId; // Commands in "New ProductLine"
F_ObjHandleT bsaveDoclineAsHtmlId, bsaveDoclineAsPdfId; // Commands from the "Save As..." submenu
F_ObjHandleT bopenFmID, bimportDrlID; // open exising fm docline project, import project from existing drl documentation
F_ObjHandleT bcloseProjectId; // menu item for closing active project and all files in it
F_ObjHandleT bexportProjectId; // menu item for exporting active project back to DRL
F_ObjHandleT bcheckCorrectId; // menu item for checking project
F_ObjHandleT bcheckCorrectId; //menu item for checking active project

F_ObjHandleT test;
