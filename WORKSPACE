workspace(name = "pkmn-city")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "rules_proto_grpc",
    sha256 = "fb7fc7a3c19a92b2f15ed7c4ffb2983e956625c1436f57a3430b897ba9864059",
    strip_prefix = "rules_proto_grpc-4.3.0",
    urls = ["https://github.com/rules-proto-grpc/rules_proto_grpc/archive/4.3.0.tar.gz"],
)

# Java
http_archive(
    name = "rules_jvm_external",
    sha256 = "c21ce8b8c4ccac87c809c317def87644cdc3a9dd650c74f41698d761c95175f3",
    strip_prefix = "rules_jvm_external-1498ac6ccd3ea9cdb84afed65aa257c57abf3e0a",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/1498ac6ccd3ea9cdb84afed65aa257c57abf3e0a.zip",
)

http_archive(
    name = "contrib_rules_jvm",
    sha256 = "548f0583192ff79c317789b03b882a7be9b1325eb5d3da5d7fdcc4b7ca69d543",
    strip_prefix = "rules_jvm-0.9.0",
    url = "https://github.com/bazel-contrib/rules_jvm/archive/refs/tags/v0.9.0.tar.gz",
)

load("@rules_proto_grpc//:repositories.bzl", "rules_proto_grpc_toolchains")

rules_proto_grpc_toolchains()

load("@rules_proto_grpc//java:repositories.bzl", rules_proto_grpc_java_repos = "java_repos")

rules_proto_grpc_java_repos()

load("@io_grpc_grpc_java//:repositories.bzl", "grpc_java_repositories")
load("//:java_packages.bzl", "java_packages")

java_packages()

load("@maven//:compat.bzl", "compat_repositories")

compat_repositories()

grpc_java_repositories()

load("@contrib_rules_jvm//:repositories.bzl", "contrib_rules_jvm_deps")

contrib_rules_jvm_deps()

load("@contrib_rules_jvm//:setup.bzl", "contrib_rules_jvm_setup")

contrib_rules_jvm_setup()

# Golang
load("@rules_proto_grpc//:repositories.bzl", "bazel_gazelle", "io_bazel_rules_go")

io_bazel_rules_go()

bazel_gazelle()

load("@rules_proto_grpc//go:repositories.bzl", rules_proto_grpc_go_repos = "go_repos")

rules_proto_grpc_go_repos()

load("@io_bazel_rules_go//go:deps.bzl", "go_register_toolchains", "go_rules_dependencies")

go_rules_dependencies()

go_register_toolchains(version = "1.19.4")

load("@bazel_gazelle//:deps.bzl", "gazelle_dependencies")

gazelle_dependencies()

load("//:go_repositories.bzl", "go_repositories")

go_repositories()

# Rust
load("@rules_proto_grpc//rust:repositories.bzl", rules_proto_grpc_rust_repos = "rust_repos")

rules_proto_grpc_rust_repos()

load("@com_github_grpc_grpc//bazel:grpc_deps.bzl", "grpc_deps")

grpc_deps()

load("@rules_rust//rust:repositories.bzl", "rules_rust_dependencies", "rust_register_toolchains")

rules_rust_dependencies()

rust_register_toolchains(edition = "2021")
