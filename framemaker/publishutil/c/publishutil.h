#ifndef PUBLISHUTIL_H
#define PUBLISHUTIL_H

#define JVM_INIT_MEM_ERROR 11
#define MIN_MEM "32"
#define MAX_MEM "256"
#define ERROR_MESSAGE "Error occured while calling Java Virtual Machine, terminating...\n"
#define ERROR_MEM_MESSAGE "Error. Not enough memory to initialize Java Virtual Machine. Needed %s megabytes. Terminating...\n"

int mergeFilesTo(char **files, int len, char *out_file_name);
int splitFilesTo(char** files, int len, char *out_dir_path);
int removeTemporaryDRLs(char **files, int num);
int copyFilesFromTempDirectoryTo(char **files, int len, char *out_dir_path);

/* calls java impementation of export util 
   use empty string for default value */
int callJavaPublishUtil(char *jarPath, // path to a jar, containing java class
				 	    char *srcDir,  // path to a directory, containing all drl files
					    char *scrFile, // name of the file, containgn final inf product
					    char *srcId,   // id of final inf product in file
					    char *format,  // format of file to be exported to
					    char *dstFile);// destination file name

int callJavaImportUtil(char *jarPath,  // path to a jar, containing java class
					   char *srcDir);  // path to a directory, containing all drl files

int callJavaExportUtil(char *jarPath,  // path to a jar, containing java class
					   char *srcDir);  // path to a directory, containing all drl files

int correctXMLNamespaces(char *fileName, char *outFileName);

#endif /* PUBLISHUTIL_H */