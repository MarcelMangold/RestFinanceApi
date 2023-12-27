#!/bin/bash

docker run --rm -v $(pwd):/liquibase/changelog liquibase/liquibase update --defaults-file=/liquibase/changelog/liquibase.properties --changelog-file=changelog/changelog/master.changelog.xml