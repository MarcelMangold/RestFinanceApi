#!/bin/bash

docker run --rm -v "$(pwd):/liquibase/changelog" liquibase/liquibase update --context-filter=main --defaults-file=/liquibase/changelog/liquibase.properties --changelog-file=changelog/changelog/main.changelog.xml