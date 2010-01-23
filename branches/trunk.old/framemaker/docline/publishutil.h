#ifndef PUBLISHUTIL_H
#define PUBLISHUTIL_H

/* calls java impementation of export util 
   use empty string for default value */
IntT callJavaPublishUtil(StringT jarPath, // path to a jar, containing java class
				 	     StringT srcDir,  // path to a directory, containing all drl files
					     StringT scrFile, // name of the file, containgn final inf product
					     StringT srcId,   // id of final inf product in file
					     StringT format,  // format of file to be exported to
					     StringT dstFile);// destination file name

IntT callJavaImportUtil(StringT jarPath,  // path to a jar, containing java class
					    StringT srcDir);  // path to a directory, containing all drl files

IntT callJavaExportUtil(StringT jarPath,  // path to a jar, containing java class
					    StringT srcDir);  // path to a directory, containing all drl files

#endif /* PUBLISHUTIL_H */