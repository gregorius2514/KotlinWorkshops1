#!/bin/bash

./gradlew clean build -x test
cp build/libs/KotlinWorkshops-0.0.1.jar docker/app.jar
docker-compose -f docker/docker-compose.yml up

