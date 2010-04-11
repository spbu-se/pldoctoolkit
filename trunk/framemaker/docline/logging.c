#include "common.h"
#include "logging.h"

ChannelT logChannel;

BoolT getLogFileName(StringT *path)
{
	StringT fmPath, suffix;

	//if (!getMainDirPath(&fmPath)) return FALSE;
	fmPath = F_StrCopyString(F_ApiClientDir());
	//fmPath = F_StrCopyString("c:\\Program Files\\Adobe\\FrameMaker8\\fminit");
	suffix = F_StrCopyString("\\docline\\docline.log");
	(*path) = F_Alloc(F_StrLen(fmPath)+F_StrLen(suffix)+1,NO_DSE);
	F_Sprintf((*path),"%s%s\0", fmPath, suffix);

	F_ApiDeallocateString(&fmPath);
	F_ApiDeallocateString(&suffix);

	return TRUE;
}

BoolT openLogChannel()
{
	StringT logPath;
	FilePathT *logFilePath;

	if (logChannel) return TRUE;

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