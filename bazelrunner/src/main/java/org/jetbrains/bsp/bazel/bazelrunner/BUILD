load("@io_bazel_rules_kotlin//kotlin:jvm.bzl", "kt_jvm_library")

kt_jvm_library(
    name = "bazelrunner",
    srcs = glob(["*.kt"]),
    resources = ["//log4j_config"],
    visibility = ["//bazelrunner:__subpackages__"],
    deps = [
        "//bazelrunner/src/main/java/org/jetbrains/bsp/bazel/bazelrunner/outputs",
        "//bazelrunner/src/main/java/org/jetbrains/bsp/bazel/bazelrunner/params",
        "//bazelrunner/src/main/java/org/jetbrains/bsp/bazel/bazelrunner/utils",
        "//commons",
        "//executioncontext/workspacecontext",
        "//logger",
        "//server/src/main/java/org/jetbrains/bsp/bazel/server/bsp/info",
        "@maven//:ch_epfl_scala_bsp4j",
        "@maven//:com_fasterxml_jackson_core_jackson_annotations",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        "@maven//:com_fasterxml_jackson_module_jackson_module_kotlin",
        "@maven//:org_apache_logging_log4j_log4j_api",
        "@maven//:org_apache_logging_log4j_log4j_core",
        "@maven//:org_eclipse_lsp4j_org_eclipse_lsp4j_jsonrpc",
    ],
)
