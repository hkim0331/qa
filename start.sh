#!/bin/sh
export PORT=3003
export DATABASE_URL='jdbc:postgresql://db/qa?user=postgres&password=password'
java -jar target/qa*-standalone.jar
