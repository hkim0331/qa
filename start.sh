#!/bin/sh
export PORT=3003
export DATABASE_URL='jdbc:postgresql://db/qa?user=postgres&password=password'
# export QA_DEV=true

# java -jar target/qa*-standalone.jar
lein run
