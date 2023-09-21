DEST="ubuntu@app.melt.kyutech.ac.jp"
TAG=hkim0331/qa:0.1

build:
	docker build -t ${TAG} .

uberjar:
	lein uberjar

deploy: clean uberjar
	scp target/qa-*-standalone.jar ${DEST}:qa/qa.jar && \
	ssh ${DEST} 'sudo systemctl restart qa' && \
	ssh ${DEST} 'systemctl status qa'

clean:
	${RM} -r target
