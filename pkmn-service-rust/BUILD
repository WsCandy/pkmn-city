load("@rules_proto_grpc//rust:rust_grpc_library.bzl", "GRPC_DEPS")
load("@rules_rust//rust:defs.bzl", "rust_binary")

rust_binary(
    name = "app",
    srcs = glob(["src/**/*.rs"]),
    edition = "2021",
    deps = [
        "//proto:rust_pokemon_grpc",
    ] + GRPC_DEPS,
)
