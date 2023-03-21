#!/bin/sh
PID=`/usr/bin/lsof -i:3003 -t`
kill ${PID}
echo killed PID ${PID} 
