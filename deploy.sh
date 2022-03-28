#!/bin/sh
if [ -z "$1" ]; then
	echo usage: $0 target/file.jar
	exit 1
fi
BN=`basename $1`
scp $1 app.melt:qa/ && \
ssh app.melt "(cd qa && ln -sf ${BN} qa.jar)" && \
ssh app.melt sudo systemctl restart qa && \
ssh app.melt systemctl status qa
