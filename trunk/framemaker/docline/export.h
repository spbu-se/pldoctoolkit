#ifndef EXPORT_H
#define EXPORT_H

#include "common.h"
#include "publishutil.h"

#define BUFFERSIZE (IntT)256

#define ERROR_LOG_FILENAME "error.log"

BoolT exportBook(StringT path, StringT dirPath, F_PropValsT params, UIntT* j);//Exporting of one docline book
BoolT performExportXSLT(StringT dirPath);//applying XSLT to exported documents
VoidT exportDocLineDoc(); //Exports docline project
VoidT generateBooks(F_ObjHandleT mainBookID); //Generates books for second-level elements
F_PropValsT generateExportParams();//Generates saving params for exporting
VoidT publishDocLineDoc(StringT format); //Publishes docline project

#endif //EXPORT_H
