#include "cj.h"
#include "publishutil.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <direct.h>

int fileNum;

char *fileFileName(char *str)
{
	while (*str)
	{
		*str++;
	}
	str--;
	while ((*str)&&(*str != '\\'))
	{
		*str--;
	}
	*str++;
	return str;
}

char *replace_str(char *str, char *sub, char *name, char *type)
{
	char buffer[10000];
	char *p;

	if(! (p = strstr(str, sub))) // Is 'sub' even in 'str'?
	return str;
	strncpy(buffer, str, p - str); // Copy characters before occurence
	buffer[p - str] = '\0';
	sprintf(buffer + (p - str), "%s filename=\"%s\" parenttype=\"%s\" %s %s",sub, name, type, "xmlns:d=\"http://math.spbu.ru/drl\" xmlns=\"http://docbook.org/ns/docbook\"", p + strlen(sub));
	return buffer;
}

int replace_all_str(char *str, char **out_str, char *sub, char *new_sub)
{
	char *buffer;
	char *p;

	if(! (p = strstr(str, sub))) // Is 'sub' even in 'str'?
	return str;

  buffer = (char *)malloc((strlen(str)-strlen(sub)+strlen(new_sub)+1)*sizeof(char));
	strncpy(buffer, str, p - str); // Copy characters before occurence
  sprintf(buffer + (p - str), "%s%s\0", new_sub, p + strlen(sub));
  //free(p);
  *out_str = (char *)malloc((strlen(buffer)+1)*sizeof(char));
  strncpy((*out_str),buffer,strlen(buffer));
  (*out_str)[strlen(buffer)] = '\0';
  free(buffer);

	return 1;
}


int splitFilesTo(char** files, int len, char *out_dir_path)
{
	int i;
	fileNum =0;
	for (i=0; i<len; i++)
	{
		splitFileTo(files[i],out_dir_path);
	}
	return 1;
}

int writeHeaderToNewFile(FILE **outFile, char *out_dir_path)
{
	char newFileName[100];

	if (*outFile) return 0;
	sprintf(newFileName,"%s%d.drl",out_dir_path,fileNum);
	fileNum++;
	*outFile = fopen(newFileName,"w");
	if (!(*outFile)) return 0;
	fprintf(*outFile,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

	return 1;
}

int splitFileTo(char *filePath, char *out_dir_path)
{
	FILE *file, *outFile;
	char *newFileName, ch, *str;

	file = fopen(filePath,"r");
	if (!file) return 0;
	str = fileFileName(filePath);
	newFileName = (char *)malloc((strlen(out_dir_path)+strlen(str)+1)*sizeof(char));
	sprintf(newFileName,"%s%s",out_dir_path,str);
	outFile = fopen(newFileName,"w");
	while ((ch = fgetc(file))!= EOF)
	{
		fputc(ch,outFile);
	}
	fclose(outFile);
	fclose(file);
	file = NULL;
	return 1;
}

int copyFilesFromTempDirectoryTo(char **files, int len, char *out_dir_path)
{
	int i;
	for (i=0; i<len; i++)
	{
		if (!splitFileTo(files[i],out_dir_path)) continue;
	}
	return 1;
}



int mergeFilesTo(char **files, int len, char *out_file_name)
{
	FILE *in_file, *out_file;
	char ch, *new_str;
	char str[10000];
	int i;
	int first;
	
	out_file = fopen(out_file_name,"w");
	if (!out_file) return 0;
	//str = "";
	first = 1;
	for (i=0; i<len; i++)
	{
		in_file = fopen(files[i],"r");
		if (!in_file) continue;
		while (fgets(str,10000,in_file))
		{
			if (strstr(str,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"))
			{
				if (first)
				{
					fprintf(out_file,"%s\n%s\n","<?xml version=\"1.0\" encoding=\"UTF-8\"?>","<d:DocLine xmlns:d=\"http://math.spbu.ru/drl\" xmlns=\"http://docbook.org/ns/docbook\">");
					first = 0; 
				}
				continue;
			}
			else if (strstr(str,"<d:DocumentationCore"))
			{
				fprintf(out_file,"%s",replace_str(str,"<d:DocumentationCore",fileFileName(files[i]),""));
				continue;
			}
			else if (strstr(str,"<d:ProductDocumentation"))
			{
				fprintf(out_file,"%s",replace_str(str,"<d:ProductDocumentation",fileFileName(files[i]),""));
				continue;
			}
			else if (strstr(str,"<d:ProductLine"))
			{
				fprintf(out_file,"%s",replace_str(str,"<d:ProductLine",fileFileName(files[i]),""));
				continue;
			}
			fprintf(out_file,"%s",str);
			//fgets(str,100,in_file);
			//if (ch == '\n')
			//{
			//	if (str == "<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
			//	{
			//		if (first)
			//		{
			//			first = 0; 
			//		}
			//		else
			//		{
			//			str = "";
			//		}
			//	}
			//	fprintf(out_file,"%s",str);
			//	str = "";
			//	continue;
			//}
			//new_str = str;
			//
			//str = (char *)realloc(NULL, strlen(str)+1);
			//str = strcat(new_str,`);
			//new_str = new_str + strlen(str);
			//*new_str = ch;
			//str = new_str;
			//fputc(ch, out_file);
		}
		fputc('\n',out_file);
		fclose(in_file);
	}
	fprintf(out_file,"%s","</d:DocLine>");
	fclose(out_file);
	return 1;
}

int callJavaPublishUtil(char *jarPath, // path to a jar, containing java class
					    char *srcDir,  // path to a directory, containing all drl files
					    char *scrFile, // name of the file, containgn final inf product
					    char *srcId,   // id of final inf product in file
					    char *format,  // format of file to be exported to
					    char *dstFile) // destination file name
{	
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

	return invokeJava(jarPath, "org/spbu/publishutil/PublishUtil", param);
}

int callJavaImportUtil(char *jarPath, // path to a jar, containing java class
					   char *srcDir)  // path to a directory, containing all drl files
{	
	return invokeJava(jarPath, "org/spbu/publishutil/ImportUtil", srcDir);
}

int callJavaExportUtil(char *jarPath, // path to a jar, containing java class
					   char *srcDir)  // path to a directory, containing all drl files
{	
	return invokeJava(jarPath, "org/spbu/publishutil/ExportUtil", srcDir);
}

int invokeJava(char *jarPath,    // path to a jar, containing java class
			   char *className,  // name of the class, that implements CJProxy interface
			   char *param)       // args to pass to execute() method
{	
	cjJVM_t jvm;
	cjClass_t proxyClass;
	cjObject_t proxy;
	int rc;
	char *args[3], *minMemParam, *maxMemParam, *clpParam, sout[4];

	args[1] = (char *)malloc((strlen(MIN_MEM)+6)*sizeof(char));
	sprintf(args[1],"-Xms%sm\0",MIN_MEM);
	args[2] = (char *)malloc((strlen(MAX_MEM)+6)*sizeof(char));
	sprintf(args[2],"-Xmx%sm\0",MAX_MEM);
	//char arg0[256] = "", arg1[] = "-Xms256m", arg2[] =	"-Xmx512m";
	//char arg0[256] = "", arg1[] = "-Xms32m", arg2[] =	"-Xmx256m";
	//char arg0[256] = "", arg1[] = minMemParam, arg2[] =	maxMemParam;
	//char arg0[256] = "", arg1[] = "-Xms32m", arg2[] =	"-Xmx100m";
	clpParam = "-Djava.class.path=";
	args[0] = (char *)malloc((strlen(clpParam)+strlen(jarPath)+1)*sizeof(char));
	sprintf(args[0],"%s%s\0",clpParam,jarPath);

	memset(&sout, 0, 4);
	memset(&jvm, 0, sizeof(cjJVM_t));
	memset(&proxyClass, 0, sizeof(cjClass_t));
	memset(&proxy, 0, sizeof(cjObject_t));

	jvm.argc = 3;
	jvm.argv = args;
	rc = cjJVMConnect(&jvm);
	if (rc != CJ_ERR_SUCCESS) {
		switch (rc)
		{
		case CJ_ERR_JVM_CONNECT_NOT_ENOUGH_MEM:
			printf(ERROR_MEM_MESSAGE,"maxMem");
			return JVM_INIT_MEM_ERROR;
		default:
			printf(ERROR_MESSAGE);
			printf("cjJVMConnect %i", rc);
			return rc;
		}
	}

	rc = cjProxyClassCreate(&proxyClass, className, &jvm);
	if (rc != CJ_ERR_SUCCESS) {
		printf(ERROR_MESSAGE);
		printf("cjProxyClassCreate %i", rc);
		return rc;
	}

	proxy.clazz = &proxyClass;
	rc = cjProxyCreate(&proxy);
	if (rc != CJ_ERR_SUCCESS) {
		printf(ERROR_MESSAGE);
		printf("cjProxyCreate %i", rc);
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
		return -1;
}

int removeTemporaryDRLs(char **files, int num)
{
	FILE *file;
	int i, flag;
	char str[10000];

	for (i=0; i<num; i++)
	{
		file = fopen(files[i],"r");
		flag = 0;
		while (fgets(str,10000,file))
		{
			if ((strstr(str,"<DocumentationCore"))
				||(strstr(str,"<ProductDocumentation"))
				||(strstr(str,"<ProductLine")))
			{
				flag = 1;
				break;
			}
		}
		fclose(file);
		if (flag)
		{
			remove(files[i]);
		}
	}
}

int correctXMLNamespaces(char *fileName, char *outFileName)
{
	FILE *file, *outFile;
	char str[10000], *new_str, **strArr;
	int i, j;

	strArr = (char **)malloc(10000*sizeof(char *));
	i = 0;
	file = fopen(fileName,"r");
	if (!file) return 0;
	while (fgets(str,10000,file))
	{
		if (strstr(str, "xmlns=\"http://docbook.org/ns/docbook\""))
		{
			if (!replace_all_str(str,&new_str,"xmlns=\"http://docbook.org/ns/docbook\"","")) continue;
			strArr[i] = (char *)malloc((strlen(new_str)+1)*sizeof(char));
			strcpy(strArr[i],new_str);
			i++;
			free(new_str);
		}
		else if (strstr(str,"<d:ProductDocumentation"))
		{
			if (!replace_all_str(str,&new_str,"<d:ProductDocumentation","<d:ProductDocumentation xmlns=\"http://docbook.org/ns/docbook\"")) continue;
			strArr[i] = (char *)malloc((strlen(new_str)+1)*sizeof(char));
			strcpy(strArr[i],new_str);
			i++;
			free(new_str);
		}
		else if (strstr(str,"<d:DocumentationCore"))
		{
			if (!replace_all_str(str,&new_str,"<d:DocumentationCore","<d:DocumentationCore xmlns=\"http://docbook.org/ns/docbook\"")) continue;
			strArr[i] = (char *)malloc((strlen(new_str)+1)*sizeof(char));
			strcpy(strArr[i],new_str);
			i++;
			free(new_str);
		}
		else if (strstr(str,"<d:ProductLine"))
		{
			if (!replace_all_str(str,&new_str,"<d:ProductLine","<d:ProductLine xmlns=\"http://docbook.org/ns/docbook\"")) continue;
			strArr[i] = (char *)malloc((strlen(new_str)+1)*sizeof(char));
			strcpy(strArr[i],new_str);
			i++;
			free(new_str);
		}
		else
		{
			strArr[i] = (char *)malloc((strlen(str)+1)*sizeof(char));
			strcpy(strArr[i],str);
			i++;
		}
	}
	fclose(file);
	outFile = fopen(outFileName,"w");
	for (j=0; j<i; j++)
	{
		fputs(strArr[j],outFile);
		free(strArr[j]);
	}
	fclose(outFile);
	free(strArr);

	return 1;
}
