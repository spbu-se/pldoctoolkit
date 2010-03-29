/*
 * COPYRIGHT (c) 1986-1997 Adobe Systems Incorporated.
 *
 * This source code contained herein is proprietary and
 * confidential information of Adobe Systems Incorporated
 * and is covered by the U.S. and other copyright and trade
 * secret protection.  Unauthorized adaptation, distribution,
 * use or display is prohibited and may be subject to civil
 * and criminal penalties.  Disclosure to others is prohibited.
 * For the terms and conditions of source code use refer to
 * your Adobe Systems Incorporated Source Code License Agreement.
 */


#include "fapi.h"
#include "fdetypes.h"

extern VoidT Structured_ApiEmergency FARGS((VoidT));
extern VoidT Structured_ApiInitialize FARGS((IntT init));
extern VoidT Structured_ApiCommand FARGS((IntT command));
extern VoidT Structured_ApiNotify FARGS((IntT notification, F_ObjHandleT docId, StringT sparm, IntT iparm));
extern VoidT Structured_ApiDialogEvent FARGS((IntT dlgNum, IntT itemNum, IntT mods));

VoidT F_ApiEmergency()
{
	Structured_ApiEmergency();
}

VoidT F_ApiInitialize(init)
IntT init;
{
    F_FdeInit();/*This is part of FDE so every time the dll is loaded this must be called.*/
    F_ApiEnableUnicode(True);
	F_FdeInitFontEncs("UTF-8");
	Structured_ApiInitialize(init);
}

VoidT F_ApiCommand(command)
IntT command;
{
    F_FdeInit();/*This is part of FDE so every time the dll is loaded this must be called.*/
	Structured_ApiCommand(command);
}

VoidT F_ApiNotify(notification, docId, sparm, iparm)
IntT notification;
F_ObjHandleT docId;
StringT sparm;
IntT iparm;
{
    F_FdeInit();/*This is part of FDE so every time the dll is loaded this must be called.*/
	Structured_ApiNotify(notification, docId, sparm, iparm);
}

VoidT F_ApiDialogEvent(dlgNum, itemNum, mods)
IntT dlgNum;
IntT itemNum; 
IntT mods;
{
    F_FdeInit();/*This is part of FDE so every time the dll is loaded this must be called.*/
	Structured_ApiDialogEvent(dlgNum, itemNum, mods);
}