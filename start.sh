#!/bin/sh
export PORT=3024
export DATABASE_URL='jdbc:postgresql://db/qa?user=postgres'
java -jar target/qa-1.9.0-standalone.jar


