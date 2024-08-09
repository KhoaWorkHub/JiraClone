#!/usr/bin/env bash
# start.sh

# Pull the latest images
docker-compose pull

# Build the services
docker-compose build

# Bring up the services
docker-compose up -d

# Check the status of the containers
docker ps

# Remove unused Docker images
docker image prune -f

# Backup the database
#docker exec <database_container_name> /usr/bin/mysqldump -u <username> --password=<password> <database_name> > backup.sql

# To restore the database from backup, uncomment the following line:
# docker exec -i <database_container_name> /usr/bin/mysql -u <username> --password=<password> <database_name> < backup.sql