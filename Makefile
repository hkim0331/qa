# security -v unlock-keychain ~/Library/Keychains/login.keychain-db

TAG=hkim0331/duct:0.2.1

all:
	@echo make build
	@echo make zip
	@echo make github

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


