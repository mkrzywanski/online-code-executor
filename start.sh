#!/bin/bash

cd ./online-code-executor-backend
./gradlew executor-app:jibDockerBuild

cd ..

docker-compose up --build --force-recreate
