#include "fm_sgml.h"
#include "fstrings.h"
#include "fstrlist.h"
#include "fmemory.h"
#include "futils.h"
#include "fcharmap.h"

/*
 * function prototypes
 */

IntT bookNum;

VoidT pathFilename(UCharT *str)
{
	while (*str)
	{
		*str++;
	}
	str--;
	while ((*str)&&(*str != '\\'))
	{
		*str = 0;
		*str--;
	}
}

SrwErrorT Sr_EventHandler(SrEventT *eventp, SrConvObjT srObj)
{
	StringT bookPathName;
	FilePathT *bookPath;
	IntT i;
	UCharT buf[500];

	switch(eventp->evtype)
	{
	case SR_EVT_BEGIN_READER:
	case SR_EVT_BEGIN_BOOK:
		////case SR_EVT_
		//bookPath = Sr_GetBookFilePath(srObj);
		//if (bookPath == NULL) break;
		//bookPathName = F_FilePathToPathName(bookPath, FDefaultPath);
		//pathFilename(bookPathName);
		//i = F_Sprintf(buf,"%s", bookPathName);
		//i = F_Sprintf(buf+i,"\\book%d.book", bookNum);
		//*bookPathName = (StringT)buf;
		//F_ApiAlert(bookPathName,FF_ALERT_CONTINUE_NOTE);
		//bookPath = F_PathNameToFilePath(bookPathName,NULL,FDefaultPath);
		//Sr_SetBookFilePath(srObj,bookPath);

		//F_FilePathFree(bookPath);
		//F_ApiDeallocateString(&bookPathName);
		//F_ApiDeallocateString(&buf);
		break;
	case SR_EVT_BEGIN_DOC:
	case SR_EVT_BEGIN_BOOK_COMP:
	case SR_EVT_BEGIN_ELEM:
	case SR_EVT_END_ELEM:
	case SR_EVT_CDATA:
	case SR_EVT_RE:
		break;
	default:
		break;
	}
	return Sr_Convert(eventp, srObj);
}

SrwErrorT Sw_EventHandler(SwEventT *eventp, SwConvObjT swObj)
{
	switch (eventp->evtype)
	{
	case SW_EVT_BEGIN_BOOK:
		{
			F_ApiAlert("Gotcha!!!",FF_ALERT_CONTINUE_NOTE);
			break;
		}
	default:
		{
			break;
		}
	}
	Sw_Convert(eventp,swObj);
}