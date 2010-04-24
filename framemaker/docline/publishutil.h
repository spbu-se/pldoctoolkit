#ifndef PUBLISHUTIL_H
#define PUBLISHUTIL_H

#define JVM_INIT_MEM_ERROR 11
#define MIN_MEM "32"
#define MAX_MEM "512"
#define ERROR_MESSAGE "Error occured while calling Java Virtual Machine, terminating...\n"
#define ERROR_MEM_MESSAGE "Error. Not enough memory to initialize Java Virtual Machine. Needed %s megabytes. Terminating...\n"

IntT mergeFilesTo(UCharT **files, IntT len, UCharT *out_file_name);

IntT splitFilesTo(UCharT **files, IntT len, UCharT *out_dir_path);

IntT removeTemporaryDRLs(UCharT **files, IntT num);

IntT copyFilesFromTempDirectoryTo(UCharT **files, IntT len, UCharT *out_dir_path);

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

IntT correctXMLNamespaces(StringT fileName, StringT outFileName);

#endif /* PUBLISHUTIL_H */