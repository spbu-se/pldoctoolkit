#include "common.h"
#include "logging.h"

ChannelT logChannel;

BoolT getLogFileName(StringT *path)
{
	StringT fmPath;
	UCharT buf[100];
	IntT i;
	//*path = F_StrCopyString(F_ApiClientDir());
	fmPath = F_StrCopyString(F_ApiClientDir());
	//buf = F_Realloc(buf,F_StrLen(fmPath)+F_StrLen("\\docline\\docline.log")+5,NO_DSE);
	i = F_Sprintf(buf,"%s", fmPath);
	i = F_Sprintf(buf+i,"\\docline\\docline.log");
	*path = F_StrCopyString((StringT)buf);

	F_ApiDeallocateString(buf);
	return TRUE;
}

BoolT openLogChannel()
{
	StringT logPath;
	FilePathT *logFilePath;

	logPath = "";
	if (!getLogFileName(&logPath)) return FALSE;
	logFilePath = F_PathNameToFilePath(logPath,NULL,FDosPath);

	logChannel = F_ChannelOpen(logFilePath, "a");
	if (!logChannel)
	{
		F_Printf(NULL,"%s\n", "Log opening error");
		return FALSE;
	}

	F_FilePathFree(logFilePath);
	F_ApiDeallocateString(&logPath);

	return TRUE;
}

BoolT closeLogChannel()
{
	if (!F_ChannelClose(logChannel))
	{
		F_Printf(NULL,"%s\n", "Log closing error");
		return FALSE;
	}

	//F_ApiDeallocateString(&endString);

	return TRUE;
}
BoolT writeToChannel(StringT str)
{
	if (!F_ChannelWrite(str, sizeof(UCharT), F_StrLen(str), logChannel))
	{
		F_Printf(NULL,"%s\n","Log writing error");
		return FALSE;
	}
	F_ChannelFlush(logChannel);
	return TRUE;
}