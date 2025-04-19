#!/bin/bash

docker stop finance_db

docker run --rm --name finance_db -e POSTGRES_DB=finance_db -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres:17.4

docker run --rm -v $(pwd)/liquibase:/liquibase/changelog liquibase/liquibase update --defaults-file=/liquibase/changelog/liquibase.properties --context-filter=master,workbench --changelog-file=changelog/changelog/master.changelog.xml