DEST="ubuntu@app.melt.kyutech.ac.jp"

#build:
#	docker build -t hkim0331/py99 .

uberjar:
	lein uberjar

deploy: clean uberjar
	scp target/qa-*-standalone.jar ${DEST}:qa/qa.jar && \
	ssh ${DEST} 'sudo systemctl restart qa' && \
	ssh ${DEST} 'systemctl status qa'

clean:
	${RM} -rf target
