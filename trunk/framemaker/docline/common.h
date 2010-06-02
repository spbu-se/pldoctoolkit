#ifndef COMMON_H
#define COMMON_H

#include "fapi.h"
#include "fchannel.h"
#include "fdetypes.h"
#include "futils.h"
#include "fstrings.h"
#include "fprogs.h"
#include "fmemory.h"

#define DRL 201
#define FM 200
#define FMBOOK 202
#define GENBOOK 203

#define PLUGIN_DIR_NAME "docline"
#define JAR_FILENAME "publishutil.jar"

#define HIGH_ELEM "DocLine"
#define THIRD_ELEMENTS {"InfElement", "InfProduct", "FinalInfProduct", "DirTemplate", "Directory", "Dictionary"}
#define THIRD_LENGTH 6

#define defaultPath  (F_StrCopyString("C:\\"))
#define defaultBookName "mainDRLFMBook.book"

BoolT getJarFileName(StringT *path);
BoolT getMainDirPath(StringT *path);
VoidT closeProject();
BoolT cleanTempDirectory();
BoolT cleanDirectory(FilePathT* dirPath); //Deletes temporary files in directory
BoolT validateFilename(StringT str,IntT type); //Validates type of file
BoolT openMainBook(StringT path, F_ObjHandleT *bookID, StringT *bookPath);//Creates and opens main book
StringT getPlace(StringT fileName);//Returns name of parent element for this
F_ObjHandleT componentExists(F_ObjHandleT bookID, F_ObjHandleT cID, StringT name);//Returns component's id if component with given name exists
VoidT pathFilename(UCharT *str); //Converts full path to path of directory
StringT fileFileName(UCharT *str); //Converts full path to file name
BoolT isDocLine(F_ObjHandleT docID); //Checks if document is docline document
StringT getHighestString(F_ObjHandleT docID); //Name of highest level element in document
IntT getActiveBookID();//Returns id of active book
VoidT closeAllDocs();//Closes all docs except main book
VoidT addStructuredElementToBook(F_ObjHandleT bookID, F_ObjHandleT newBookID, F_ObjHandleT currElemID, F_ObjHandleT elemID);//Adds structure of one book to another
StringT getMainBookName(F_ObjHandleT bookID);
BoolT getMainBookTemplate(StringT path);
BoolT getMainBookEDD(StringT *path);
BoolT getTempDirPath(StringT *path);
BoolT setDefaultDirectory(StringT dirPath);

#endif //COMMON_H