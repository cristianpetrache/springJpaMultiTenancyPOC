#!/bin/bash

docker run --name tenant1Postgres -e POSTGRES_PASSWORD=zaq12wsx -p 15432:5432 -d postgres:12
docker run --name tenant2Postgres -e POSTGRES_PASSWORD=zaq12wsx -p 25432:5432 -d postgres:12
