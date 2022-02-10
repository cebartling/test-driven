#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER racing_user WITH ENCRYPTED PASSWORD 'P4ssword';
    CREATE DATABASE racing_db;
    GRANT ALL PRIVILEGES ON DATABASE racing_db TO racing_user;
EOSQL
