#!/bin/sh
export QA_DEV=true
export DATABASE_URL='jdbc:postgresql://db/qa?user=postgres&password=password'
lein run
