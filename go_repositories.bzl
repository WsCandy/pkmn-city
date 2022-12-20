load("@bazel_gazelle//:deps.bzl", "go_repository")

def go_repositories():
    go_repository(
        name = "org_golang_google_grpc",
        sum = "h1:E1eGv1FTqoLIdnBCZufiSHgKjlqG6fKFf6pPWtMTh8U=",
        importpath = "google.golang.org/grpc",
        version = "v1.51.0",
    )
