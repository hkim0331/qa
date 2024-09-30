#!/bin/sh
export QA_DEV=true
export PORT=3003
export DATABASE_URL='jdbc:postgresql://db/qa?user=postgres&password=password'
lein run
