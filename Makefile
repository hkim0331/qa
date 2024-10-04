
DEST="ubuntu@app.melt.kyutech.ac.jp"

all:
	@echo make build
	@echo make zip
	@echo make github
	@echo make uberjar
	@echo make run
	@echo make deploy

uberjar:
#	JAVA_HOME=/opt/homebrew/Cellar/openjdk@17/17.0.12/libexec/openjdk.jdk/Contents/Home lein uberjar
	lein uberjar

run: uberjar
	sh start.sh

deploy: uberjar
	scp target/qa-*-standalone.jar ${DEST}:qa/qa.jar && \
	ssh ${DEST} 'sudo systemctl restart qa' && \
	ssh ${DEST} 'systemctl status qa'

# ------------------
# docker

# security -v unlock-keychain ~/Library/Keychains/login.keychain-db

TAG=hkim0331/duct:0.3.0

build:
	docker build -t ${TAG} .

zip:
	zip -r duct.zip Dockerfile docker-compose.yml .devcontainer

clean:
	${RM} duct.zip *~

github: clean security manifest

security:
	security -v unlock-keychain ~/Library/Keychains/login.keychain-db

manifest: amd64 arm64
	docker manifest create --amend ${TAG} ${TAG}-amd64 ${TAG}-arm64
	docker manifest push ${TAG}

amd64:
	docker buildx build --platform linux/$@ --push -t ${TAG}-$@ .

arm64:
	docker buildx build --platform linux/$@ --push -t ${TAG}-$@ .
