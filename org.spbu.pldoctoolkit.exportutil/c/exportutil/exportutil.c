#include "cj.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define ERROR_MESSAGE "Error occured while calling Java Virtual Machine, terminating...\n"

int callJavaExportUtil(char *jarPath, // path to a jar, containing java class
					   char *srcDir,  // path to a directory, containing all drl files
					   char *scrFile, // name of the file, containgn final inf product
					   char *srcId,   // id of final inf product in file
					   char *format,  // format of file to be exported to
					   char *dstFile) // destination file name
{	
	cjJVM_t jvm;
	cjClass_t proxyClass;
	cjObject_t proxy;
	int rc;
	char *args[3];
	char arg0[255] = "", arg1[] = "-Xms256m", arg2[] =	"-Xmx512m";
	char sout[4];

	char param[1000] = "";

	strcat(param, srcDir);
	strcat(param, "|");
	strcat(param, scrFile);
	strcat(param, "|");
	strcat(param, srcId);
	strcat(param, "|");
	strcat(param, format);
	strcat(param, "|");
	strcat(param, dstFile);

	strcat(arg0, "-Djava.class.path=");
	strcat(arg0, jarPath);
	
	args[0] = arg0;
	args[1] = arg1;
	args[2] = arg2;

	memset(&sout, 0, 4);
	memset(&jvm, 0, sizeof(cjJVM_t));
	memset(&proxyClass, 0, sizeof(cjClass_t));
	memset(&proxy, 0, sizeof(cjObject_t));

	jvm.argc = 3;
	jvm.argv = args;
	rc = cjJVMConnect(&jvm);
	if (rc != CJ_ERR_SUCCESS) {
		printf(ERROR_MESSAGE);
		return rc;
	}

	rc = cjProxyClassCreate(&proxyClass, "org/spbu/pldoctoolkit/exportutil/ExportUtil", &jvm);
	if (rc != CJ_ERR_SUCCESS) {
		printf(ERROR_MESSAGE);
		return rc;
	}

	proxy.clazz = &proxyClass;
	rc = cjProxyCreate(&proxy);
	if (rc != CJ_ERR_SUCCESS) {
		printf(ERROR_MESSAGE);
		return rc;
	}

	rc = cjProxyExecString(&proxy, param, sout);
	if (rc != CJ_ERR_SUCCESS) {
		printf(ERROR_MESSAGE);
		printf("cjProxyExecString %i", rc);
		return rc;
	}

	rc = cjFreeObject((proxy.clazz)->jvm, proxy.object);
	if (rc != CJ_ERR_SUCCESS) {
		printf(ERROR_MESSAGE);
		printf("cjFreeObject");
		return rc;
	}

	rc = cjClassDestroy(&proxyClass);
	if (rc != CJ_ERR_SUCCESS) {
		printf(ERROR_MESSAGE);
		printf("cjClassDestroy");
		return rc;
	}

	rc = cjJVMDisconnect(&jvm);
	if (rc != CJ_ERR_SUCCESS) {
		printf(ERROR_MESSAGE);
		printf("cjJVMDisconnect");
		return rc;
	}

	if (strcmp(sout, "0") == 0)
		return 0;
	else
		return 1;
}


