# .NET Core Test-Driven Development Example

## Introduction



- [User stories](./user-stories.md)


## Installation

1. Install .NET Core SDK 6.0. Go [here](https://dotnet.microsoft.com/en-us/download/dotnet/6.0) to download the SDK.
    - I did the initial development on macOS. 
1. Set your `PATH` to include the .NET tools: `export PATH="$PATH:~/.dotnet/tools"`
1. Install Entity Framework Core CLI tools: `dotnet tool install --global dotnet-ef`
    - More information on Entity Framework Core CLI tools [here](https://docs.microsoft.com/en-us/ef/core/cli/dotnet)
1. Verify the Entity Framework Core CLI tools installation: `dotnet ef` 


## PostgreSQL database

For this test-driven development demonstration, PostgreSQL 14 is used as the relational database system. 


### Starting the server

1. Using Docker Compose, start the database server: `docker compose up -d`

### Stopping the server

1. Using Docker Compose, stop the server by typing `docker compose down`

### Resetting the database

1. Using Docker Compose, stop the server and clean up the volumes: `docker compose down -v --remove-orphans --timeout 30`
1. Remove the data directory on your local filesystem: `rm -rf ./postgres-data`


## Migrating the database

- [Managing database schemas via migrations in Entity Framework Core](https://docs.microsoft.com/en-us/ef/core/managing-schemas/migrations/?tabs=dotnet-core-cli)

1. Ensure the PostgreSQL server is running within Docker Compose.
1. Change to the `tdd-example` project directory: `cd tdd-example`
1. Using the migrrations created in this project, update the database: `dotnet ef database update`
