#ifndef STRUCTURE_H
#define STRUCTURE_H

#include "common.h"

#define NEW_DLG 239
#define SECTION_DLG 240
#define OKDLG 5
#define CANCELDLG 6

#define DICTION 501
#define DIRECT 502
#define DIRTEMP 503
#define INFELEM 504
#define INFPROD 505
#define FINALINF 506
#define PRODUCT 507


VoidT createNewDocLineBook();
VoidT newDocCoreChild(IntT type);
VoidT newProdDocChild(IntT type);
VoidT newProdLineChild(IntT type);
BoolT newSecondLevelSection(BoolT isFirst, StringT type);
VoidT editHeader();
VoidT setAttributes(StringT idStr, StringT nameStr);

#endif //STRUCTURE_H
