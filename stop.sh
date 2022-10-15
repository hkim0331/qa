#!/bin/sh
export PORT=3024
kill `lsof -t -i:${PORT}`
