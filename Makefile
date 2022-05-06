ORG := ga4gh
REPO := ga4gh-starter-kit-passport-broker
TAG := $(shell cat build.gradle | grep "^version" | cut -f 2 -d ' ' | sed "s/'//g")
IMG := ${ORG}/${REPO}:${TAG}
DEVDB := ga4gh-starter-kit.dev.db

Nothing:
	@echo "No target provided. Stop"

.PHONY: clean-sqlite
clean-sqlite:
	@rm -f ${DEVDB}

.PHONY: sqlite-db-build
sqlite-db-build: clean-sqlite
	@sqlite3 ${DEVDB} < database/sqlite/create-schema.migrations.sql

.PHONY: sqlite-db-populate-dev-dataset
sqlite-db-populate-dev-dataset:
	@sqlite3 ${DEVDB} < database/sqlite/populate-dev-dataset.migrations.sql

.PHONY: sqlite-db-refresh
sqlite-db-refresh: clean-sqlite sqlite-db-build sqlite-db-populate-dev-dataset

.PHONY: docker-build-test
docker-build-test:
	docker build -t ${ORG}/${REPO}:test --build-arg VERSION=${TAG} .

.PHONY: docker-build
docker-build:
	docker build -t ${IMG} --build-arg VERSION=${TAG} .

.PHONY: docker-publish
docker-publish:
	docker image push ${IMG}