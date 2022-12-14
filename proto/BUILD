load("@io_grpc_grpc_java//:java_grpc_library.bzl", "java_grpc_library")

package(default_visibility = ["//visibility:public"])

proto_library(
    name = "pokemon_proto",
    srcs = ["pokemon.proto"],
)

java_proto_library(
    name = "pokemon_proto_java",
    deps = [":pokemon_proto"],
)

java_grpc_library(
    name = "pokemon_java_grpc",
    srcs = [":pokemon_proto"],
    deps = [":pokemon_proto_java"],
)
