#ifndef IMPORT_H
#define IMPORT_H

#include "common.h"

VoidT importDocLineDoc(); //Imports .drl files in directory
BoolT importBook(StringT path, F_PropValsT params);//Importing of one docline book
BoolT performImportXSLT(StringT dirPath);//appying XSLT to document for impporting
BoolT cleanImportDirectory(StringT dirPath); //Deletes all non-DRL files in directory
VoidT openFilesInDirectory(StringT path); //Common part of Open and Import
VoidT openBook(); //Opens existing docline project with checking its structure
F_PropValsT generateOpenParams();//Generates opening params for importing
F_PropValsT generateImportParams();//Generates import params for importing
F_PropValsT generateOpenWithUnresolvedRefsParams();//Generates opening params for .fm files with unresolved XRefs
VoidT renameFilesToActualNames(F_ObjHandleT bookID);//Renames book to it's actual name
VoidT simpleOpenBook();//Opens existing docline project without checking its structure
BoolT addExistingDocs(StringT path, F_ObjHandleT bookID);//Adds docs in directory to book
BoolT addExistingDoc(StringT path, F_ObjHandleT bookID);//Adds doc in directory to book

#endif //IMPORT_H