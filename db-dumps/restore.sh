#!/bin/sh
# 2022-05-07 --last option

DB=qa

if [ -z "$1" ]; then
    echo "usage: $0 yyyy-mm-dd.sql"
    echo "       $0 --last"
    echo restore postgresql database from ${DB}-yyyy-mm-dd.sql
    echo mind: this script drops database first.
    exit 1
elif [ "--last" = "$1" ]; then
    DUMP=`ls -t ${DB}* | head -1`
else
    DUMP=$1
fi

PSQL="psql -h db -U postgres"
${PSQL} -c "drop database ${DB}"
${PSQL} -c "create database ${DB} owner='postgres'"
${PSQL} ${DB} < ${DUMP}
