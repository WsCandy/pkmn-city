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
    main_class = "com.gocity.pokemon.PokemonApplication",
    runtime_deps = [
        ":deps",
    ],
)

java_library(
    name = "deps",
    srcs = glob(["src/main/java/**/*.java"]),
    resources = glob(["src/main/resources/**/*"]),
    runtime_deps = [
        "@io_grpc_grpc_java//netty",
    ],
    deps = [
        ":lombok",
        "@io_grpc_grpc_java//api",
        "@io_grpc_grpc_java//protobuf",
        "@io_grpc_grpc_java//stub",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:com_google_code_gson_gson",
        "@maven//:com_graphql_java_graphql_java",
        "@maven//:org_slf4j_slf4j_api",
        "@maven//:org_springframework_boot_spring_boot",
        "@maven//:org_springframework_boot_spring_boot_autoconfigure",
        "@maven//:org_springframework_boot_spring_boot_starter",
        "@maven//:org_springframework_boot_spring_boot_starter_data_mongodb",
        "@maven//:org_springframework_data_spring_data_commons",
        "@maven//:org_springframework_data_spring_data_mongodb",
        "@maven//:org_springframework_spring_beans",
        "@maven//:org_springframework_spring_context",
        "@maven//:org_springframework_spring_core",
        "@pkmn-city//proto:pokemon_java_grpc",
        "@pkmn-city//proto:pokemon_proto_java",
    ],
)
