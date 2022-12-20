load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@io_grpc_grpc_java//:repositories.bzl", "IO_GRPC_GRPC_JAVA_ARTIFACTS", "IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS", "grpc_java_repositories")

SPRING_BOOT_VERSION = "3.0.0"
GRPC_VERSION = "1.5.1"
SL4J_VERSION = "1.7.0"
JUNIT_JUPITER_VERSION = "5.9.1"
JUNIT_PLATFORM_VERSION = "1.9.1"

def java_packages():
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
        ] + IO_GRPC_GRPC_JAVA_ARTIFACTS,
        generate_compat_repositories = True,
        override_targets = IO_GRPC_GRPC_JAVA_OVERRIDE_TARGETS,
        repositories = [
            "https://repo.maven.apache.org/maven2/",
        ],
    )
