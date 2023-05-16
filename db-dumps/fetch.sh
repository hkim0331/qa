#!/bin/sh

SERV=app.melt
APP=qa
DUMP=${APP}/db-dumps
TODAY=`date +%F`

ssh ${SERV} "cd ${DUMP} && ./dump.sh"
scp ${SERV}:${DUMP}/${APP}-${TODAY}.sql .
