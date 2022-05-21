#!/usr/bin/env bash
docker-compose down

# Uncomment for a full clean restart:
docker system prune --force
docker container prune --force
docker volume prune --force
docker image prune --force

docker-compose build
docker-compose up -d
