#ifndef IMPORT_H
#define IMPORT_H

#include "common.h"

BoolT addExistingDocs(StringT path, F_ObjHandleT bookID); //Adds docs in directory to book
BoolT addExistingDoc(StringT path, F_ObjHandleT bookID); //Adds doc in directory to book
BoolT cleanImportDirectory(); //Deletes all non-DRL files in directory
BoolT deinitializeConstants(); //Deinitialization of global variables
BoolT generateImportParams(F_PropValsT *params); //Generates import params for importing
F_PropValsT generateOpenParams(BoolT interactive); //Generates opening params for importing
BoolT generateOpenWithUnresolvedRefsParams(F_PropValsT * params); //Generates opening params for .fm files with unresolved XRefs
VoidT importDocLineDoc(); //Imports .drl files in directory
BoolT importBook(StringT path, F_PropValsT params); //Importing of one docline book
BoolT initializeConstants(); //Initialization of global variables
BoolT openFilesInDirectory(); //Common part of Open and Import
VoidT openBook(); //Opens existing docline project with checking its structure
BoolT performImportXSLT(); //appying XSLT to document for impporting
VoidT renameFilesToActualNames(F_ObjHandleT bookID); //Renames book to it's actual name
VoidT simpleOpenBook(); //Opens existing docline project without checking its structure

#endif //IMPORT_H