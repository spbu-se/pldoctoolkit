#ifndef LOGGING_H
#define LOGGING_H

BoolT getLogFileName(StringT *path);
BoolT openLogChannel();
BoolT closeLogChannel();
BoolT writeToChannel(StringT str);

#endif //LOGGING_H