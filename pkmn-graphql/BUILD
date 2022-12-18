load("@rules_jvm_external//:defs.bzl", "artifact")
load("@contrib_rules_jvm//java:defs.bzl", "JUNIT5_DEPS", "java_test_suite")

java_library(
    name = "lombok",
    exported_plugins = [":lombok_plugin"],
    exports = ["@maven//:org_projectlombok_lombok"],
)

java_plugin(
    name = "lombok_plugin",
    generates_api = True,
    processor_class = "lombok.launch.AnnotationProcessorHider$AnnotationProcessor",
    visibility = ["//visibility:public"],
    deps = ["@maven//:org_projectlombok_lombok"],
)

java_binary(
    name = "app",
    main_class = "com.gocity.graphql.GraphqlApplication",
    runtime_deps = [":deps"],
)

java_library(
    name = "deps",
    srcs = glob([
        "src/main/java/**/*.java",
    ]),
    resources = glob([
        "src/main/resources/**/*",
    ]),
    runtime_deps = [
        "@io_grpc_grpc_java//netty",
    ],
    deps = [
        ":lombok",
        "@io_grpc_grpc_java//api",
        "@io_grpc_grpc_java//protobuf",
        "@io_grpc_grpc_java//stub",
        "@maven//:com_google_code_gson_gson",
        "@maven//:com_graphql_java_graphql_java",
        "@maven//:net_javacrumbs_future_converter_future_converter_java8_guava",
        "@maven//:org_slf4j_slf4j_api",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_boot_spring_boot_autoconfigure",
        "@maven//:org_springframework_boot_spring_boot_starter_graphql",
        "@maven//:org_springframework_boot_spring_boot_starter_web",
        "@maven//:org_springframework_graphql_spring_graphql",
        "@maven//:org_springframework_spring_beans",
        "@maven//:org_springframework_spring_context",
        "@maven//:org_springframework_spring_core",
        "@pkmn-city//proto:pokemon_java_grpc",
        "@pkmn-city//proto:pokemon_proto_java",
    ],
)

java_library(
    name = "test-resources",
    resources = glob([
        "src/test/resources/**/*",
    ]),
)

java_test_suite(
    name = "tests",
    srcs = glob(["src/test/java/**/*.java"]),
    runner = "junit5",
    test_suffixes = ["Test.java"],
    runtime_deps = JUNIT5_DEPS,
    deps = [
        ":deps",
        ":test-resources",
        "@io_grpc_grpc_java//api",
        "@io_grpc_grpc_java//core:inprocess",
        "@io_grpc_grpc_java//core:util",
        "@io_grpc_grpc_java//stub",
        "@io_grpc_grpc_java//testing",
        "@maven//:com_google_protobuf_protobuf_java_util",
        "@maven//:org_springframework_boot_spring_boot_starter_test",
        "@maven//:org_springframework_boot_spring_boot_test_autoconfigure",
        "@maven//:org_springframework_graphql_spring_graphql_test",
        "@maven//:org_springframework_spring_beans",
        "@maven//:org_springframework_spring_context",
        "@maven//:org_springframework_spring_core",
        "@maven//:org_springframework_spring_test",
        "@pkmn-city//proto:pokemon_java_grpc",
        "@pkmn-city//proto:pokemon_proto_java",
        artifact("org.junit.jupiter:junit-jupiter-api"),
        artifact("org.junit.jupiter:junit-jupiter-params"),
    ],
)
