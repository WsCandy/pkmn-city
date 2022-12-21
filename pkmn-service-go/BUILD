load("@io_bazel_rules_go//go:def.bzl", "go_binary", "go_library")

go_binary(
    name = "app",
    srcs = glob(["src/**/*.go"]),
    deps = [
        "//proto:go_pokemon_grpc",
        "@org_golang_google_grpc//:go_default_library",
    ],
)
