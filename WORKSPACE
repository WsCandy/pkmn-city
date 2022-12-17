workspace(name = "pkmn-city")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

http_archive(
    name = "io_grpc_grpc_java",
    sha256 = "e258976ca0404fc6a6113a50add56857062828458f61285c1b43f0bfae305e9e",
    strip_prefix = "grpc-java-1.51.0",
    url = "https://github.com/grpc/grpc-java/archive/v1.51.0.zip",
)

http_archive(
    name = "rules_jvm_external",
    sha256 = "c21ce8b8c4ccac87c809c317def87644cdc3a9dd650c74f41698d761c95175f3",
    strip_prefix = "rules_jvm_external-1498ac6ccd3ea9cdb84afed65aa257c57abf3e0a",
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/1498ac6ccd3ea9cdb84afed65aa257c57abf3e0a.zip",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@io_grpc_grpc_java//:repositories.bzl", "IO_GRPC_GRPC_JAVA_ARTIFACTS")
load("@io_grpc_grpc_java//:repositories.bzl", "IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS")
load("@io_grpc_grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories()

load("@com_google_protobuf//:protobuf_deps.bzl", "PROTOBUF_MAVEN_ARTIFACTS")
load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

SPRING_BOOT_VERSION = "3.0.0"

GRPC_VERSION = "1.5.1"

SL4J_VERSION = "1.7.0"

# Java Dependencies
maven_install(
    artifacts = [
        "com.google.protobuf:protobuf-java-util:3.21.11",
        "commons-io:commons-io:2.11.0",
        "com.graphql-java:graphql-java:20.0",
        "org.projectlombok:lombok:1.18.24",
        "org.springframework.graphql:spring-graphql:1.1.0",
        "com.fasterxml.jackson.core:jackson-databind:2.14.1",
        "net.javacrumbs.future-converter:future-converter-java8-guava:1.2.0",
        "org.slf4j:slf4j-api:%s" % SL4J_VERSION,
        "org.springframework.boot:spring-boot:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-autoconfigure:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter-web:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter-graphql:%s" % SPRING_BOOT_VERSION,
    ] + IO_GRPC_GRPC_JAVA_ARTIFACTS + PROTOBUF_MAVEN_ARTIFACTS,
    generate_compat_repositories = True,
    override_targets = IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS,
    repositories = [
        "https://repo.maven.apache.org/maven2/",
    ],
)

load("@maven//:compat.bzl", "compat_repositories")

compat_repositories()
