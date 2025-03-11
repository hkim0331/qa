#!/bin/sh
PID=`/usr/bin/lsof -i:8530 -t`
kill ${PID}
echo killed PID ${PID}
