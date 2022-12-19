load("@io_bazel_rules_go//go:def.bzl", "go_binary", "go_library")

go_binary(
    name = "app",
    srcs = glob(["**/*.go"]),
    deps = [
        "@com_github_gorilla_mux//:mux",
    ],
)
