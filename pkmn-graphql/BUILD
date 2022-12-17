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
    runtime_deps = [
        ":deps",
    ],
)

java_library(
    name = "deps",
    srcs = glob(["src/main/java/**/*.java"]),
    resources = glob(["src/main/resources/**/*"]),
    deps = [
        ":lombok",
        "@io_grpc_grpc_java//api",
        "@io_grpc_grpc_java//protobuf",
        "@io_grpc_grpc_java//stub",
        "@maven//:com_google_code_gson_gson",
        "@maven//:com_graphql_java_graphql_java",
        "@maven//:commons_io_commons_io",
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
