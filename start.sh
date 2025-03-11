#!/bin/sh
export PORT=8530
export DATABASE_URL='jdbc:postgresql://db/qa?user=postgres&password=password'
export QA_START=2025-03-01
java -jar qa.jar
