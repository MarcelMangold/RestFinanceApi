#!/bin/bash

docker run --rm --name finance_db -e POSTGRES_DB=finance_db -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres:17.4

