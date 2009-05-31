#include <xalanc/Include/PlatformDefinitions.hpp>
#if defined(XALAN_CLASSIC_IOSTREAMS)
#include <iostream.h>
#else
#include <iostream>
#endif
#include <xercesc/util/PlatformUtils.hpp>
#include <xalanc/XalanTransformer/XalanTransformer.hpp>
#include "fapi.h"
#include "fdetypes.h"
#include "futils.h"
#include "fstrings.h"
#include "fprogs.h"
#include "fmemory.h"
#include "cutils.h"

#include "string.h"
#include "ctype.h"

VoidT createNewDocLineBook();
VoidT newDocCoreChild(IntT type);
VoidT openBook(); //Opens existing docline project with checking its structure
VoidT importDocLineDoc(); //Imports .drl files in directory
VoidT exportDocLineDoc(); //Exports docline project
VoidT closeProject();
VoidT editHeader();
VoidT setAttributes(StringT idStr, StringT nameStr);
VoidT cleanDirectory(FilePathT* dirPath); //Deletes temporary files in directory
BoolT validateFilename(UCharT *str,IntT type); //Validates type of file
VoidT generateBooks(F_ObjHandleT mainBookID); //Generates books for second-level elements
VoidT renameFilesToActualNames(F_ObjHandleT bookID);//Renames files, corresponding components of book to value of their id attribute
VoidT openFilesInDirectory(StringT path); //Common part of Open and Import
VoidT pathFilename(UCharT *str); //Converts full path to path of directory
StringT fileFileName(UCharT *str); //Converts full path to file name
BoolT isDocLine(F_ObjHandleT docID); //Checks if document is docline document
StringT getHighestString(F_ObjHandleT docID); //Name of highest level element in document
IntT test;
