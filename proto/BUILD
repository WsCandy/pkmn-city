load("@rules_proto_grpc//java:defs.bzl", "java_grpc_library")
load("@rules_proto_grpc//go:defs.bzl", "go_grpc_library")

package(default_visibility = ["//visibility:public"])

proto_library(
    name = "pokemon_proto",
    srcs = ["pokemon.proto"],
)

java_grpc_library(
    name = "java_pokemon_grpc",
    protos = [":pokemon_proto"],
)

go_grpc_library(
    name = "go_pokemon_grpc",
    importpath = "pkmn.city/proto/pokemon",
    protos = [":pokemon_proto"],
)
