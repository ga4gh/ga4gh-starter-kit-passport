ORG := $(shell cat build.gradle | grep "^group" | cut -f 2 -d ' ' | sed "s/'//g")
REPO := $(shell cat settings.gradle | grep "rootProject.name" | cut -f 3 -d ' ' | sed "s/'//g")
TAG := $(shell cat build.gradle | grep "^version" | cut -f 2 -d ' ' | sed "s/'//g")
IMG := ${ORG}/${REPO}:${TAG}
DEVDB := ga4gh-starter-kit.dev.db
JAR := ga4gh-starter-kit-passport-admin.jar

Nothing:
	@echo "No target provided. Stop"

# ##############################
# PASSPORT USER MANAGEMENT
# ##############################

.PHONY: passport-user-management-ory-quickstart
passport-user-management-ory-quickstart:
	@docker-compose -p passport-user-management-ory -f apps/passport-user-management/quickstart/quickstart-ory.yml up --build --force-recreate

.PHONY: passport-user-management-ga4gh-quickstart
passport-user-management-ga4gh-quickstart:
	@docker-compose -p passport-user-management-ga4gh -f apps/passport-user-management/quickstart/quickstart-ga4gh.yml up --build --force-recreate

.PHONY: passport-user-management-ui-docker-build
passport-user-management-ui-docker-build:
	@VERSION=`cat apps/passport-user-management/subapps/passport-user-management-ui/package.json | grep version | xargs | cut -f 2 -d ' ' | sed -r 's:,::'` && \
		docker build -t ga4gh/passport-user-management-ui:$$VERSION apps/passport-user-management/subapps/passport-user-management-ui

.PHONY: passport-user-management-ui-docker-push
passport-user-management-ui-docker-push:
	@VERSION=`cat apps/passport-user-management/subapps/passport-user-management-ui/package.json | grep version | xargs | cut -f 2 -d ' ' | sed -r 's:,::'` && \
		docker push ga4gh/passport-user-management-ui:$$VERSION

### OTHER APPS


.PHONY: clean-sqlite
clean-sqlite:
	@rm -f ${DEVDB}

.PHONY: clean-jar
clean-jar:
	@rm -f ${JAR}

.PHONY: clean-all
clean-all: clean-sqlite clean-jar

.PHONY: sqlite-db-build
sqlite-db-build: clean-sqlite
	@sqlite3 ${DEVDB} < database/sqlite/create-schema.migrations.sql

.PHONY: sqlite-db-populate-dev-dataset
sqlite-db-populate-dev-dataset:
	@sqlite3 ${DEVDB} < database/sqlite/populate-dev-dataset.migrations.sql

.PHONY: sqlite-db-refresh
sqlite-db-refresh: clean-sqlite sqlite-db-build sqlite-db-populate-dev-dataset
