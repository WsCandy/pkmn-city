workspace(name = "pkmn-city")

SPRING_BOOT_VERSION = "3.0.0"

GRPC_VERSION = "1.5.1"

SL4J_VERSION = "1.7.0"

JUNIT_JUPITER_VERSION = "5.9.1"

JUNIT_PLATFORM_VERSION = "1.9.1"

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

http_archive(
    name = "contrib_rules_jvm",
    sha256 = "548f0583192ff79c317789b03b882a7be9b1325eb5d3da5d7fdcc4b7ca69d543",
    strip_prefix = "rules_jvm-0.9.0",
    url = "https://github.com/bazel-contrib/rules_jvm/archive/refs/tags/v0.9.0.tar.gz",
)

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@io_grpc_grpc_java//:repositories.bzl", "IO_GRPC_GRPC_JAVA_ARTIFACTS")
load("@io_grpc_grpc_java//:repositories.bzl", "IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS")
load("@io_grpc_grpc_java//:repositories.bzl", "grpc_java_repositories")

grpc_java_repositories()

load("@com_google_protobuf//:protobuf_deps.bzl", "PROTOBUF_MAVEN_ARTIFACTS")
load("@com_google_protobuf//:protobuf_deps.bzl", "protobuf_deps")

protobuf_deps()

# Java Dependencies
maven_install(
    artifacts = [
        "org.projectlombok:lombok:1.18.24",
        "net.javacrumbs.future-converter:future-converter-java8-guava:1.2.0",
        "org.springframework.boot:spring-boot:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter-web:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter-graphql:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter-data-mongodb:%s" % SPRING_BOOT_VERSION,
        "org.springframework.boot:spring-boot-starter-test:%s" % SPRING_BOOT_VERSION,
        "org.springframework.graphql:spring-graphql-test:1.1.0",
        "org.hamcrest:hamcrest:2.2",
        "com.google.protobuf:protobuf-java-util:3.21.12",
        "org.junit.platform:junit-platform-launcher:%s" % JUNIT_PLATFORM_VERSION,
        "org.junit.platform:junit-platform-reporting:%s" % JUNIT_PLATFORM_VERSION,
        "org.junit.jupiter:junit-jupiter-api:%s" % JUNIT_JUPITER_VERSION,
        "org.junit.jupiter:junit-jupiter-params:%s" % JUNIT_JUPITER_VERSION,
        "org.junit.jupiter:junit-jupiter-engine:%s" % JUNIT_JUPITER_VERSION,
    ] + IO_GRPC_GRPC_JAVA_ARTIFACTS + PROTOBUF_MAVEN_ARTIFACTS,
    generate_compat_repositories = True,
    override_targets = IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS,
    repositories = [
        "https://repo.maven.apache.org/maven2/",
    ],
)

load("@maven//:compat.bzl", "compat_repositories")

compat_repositories()

load("@contrib_rules_jvm//:repositories.bzl", "contrib_rules_jvm_deps")

contrib_rules_jvm_deps()

load("@contrib_rules_jvm//:setup.bzl", "contrib_rules_jvm_setup")

contrib_rules_jvm_setup()
