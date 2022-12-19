# pkmn-city (WIP)
This repository is designed to be a proof of concept for a production ready mono-repo structured in micro services that can handle multiple cross-language builds and communication.

It currently features Spring Boot 3.0, MongoDB 6.0, Spring GraphQL and gRPC for inter-service communication.

## Setup
The build tool this repo uses is Bazel: https://bazel.build. To get started you'll need to install `bazelisk` on your machine. You can install Bazelisk with Homebrew you can do that with the following command: 
```
$ brew install bazelisk
```
Once you have `bazelisk` installed you can start building and running targets.

### Building
There are currently two projects in this repo `pkmn-graphql` and `pkmn-service`. You can build these by running the following commands:
```
$ bazel build //pkmn-graphql:app
$ bazel build //pkmn-service:app
```

### Running
To run the projects simple do the following:
```
$ bazel run //pkmn-graphql:app -- --spring.profiles.active=local
$ bazel run //pkmn-service:app -- --spring.profiles.active=local
```
Once running `pkmn-graphql` will be available on port `8080` and `pkmn-service` will be available on port `8081`.

- http://localhost:8080/graphiql?path=/graphql

### Testing
To run the tests simply do the following:
```
$ bazel test //pkmn-grapql:tests
```
There are only tests in the `pkmn-graphql` project currently, more will be added later.

## Databases
The `pkmn-service` service talks to MongoDB in order to retrieve data, to get this an instance of MongoDB running simple run:

```
$ docker-compose up --detach
```

When you run the database for the the first time there won't be any data in it so you'll need to seed it and create a user that the application can use to read data. 

### Seeding
To seed the database connect to it using your tool of choice and create a database called `pkmn` with a collection called `pokemon`.

The data you want to import into the `pokemon` collection is located in the `pkmn-service/data/mongo` folder.
