plugins {
    java
    idea
    kotlin("jvm") version "1.6.10" apply true
    id("com.squareup.wire") version "5.0.0-alpha04"
}

repositories {
    mavenCentral()
}

wire {
    custom {
        // Be sure that `server-generator` is on the classpath for Gradle to resolve
        // `GrpcServerSchemaHandler`.
        schemaHandlerFactory = com.squareup.wire.kotlin.grpcserver.GrpcServerSchemaHandler.Factory()
        options = mapOf(
            // Defaults to `true` if absent. Any other value than `true` is considered false.
            "singleMethodServices" to "false",
            // Defaults to `suspending` if absent. Any other value than `suspending` is considered
            // non-suspending.
            "rpcCallStyle" to "blocking",
        )
        // We set the custom block exclusivity to false so that the next `kotlin {}` block can also
        // generate the protobuf Messages.
        exclusive = false
    }

    sourcePath {
        srcDir("src/main/protos")
    }

    kotlin {
        exclusive = false
        rpcRole = "client"
    }

    kotlin {
        exclusive = false
        rpcCallStyle = "blocking"
        rpcRole = "server"
    }
}

dependencies {
    api("com.squareup.wire:wire-runtime:5.0.0-alpha04")
    implementation("com.squareup.wire:wire-grpc-client:5.0.0-alpha04")

    // sever stuff
    implementation("com.squareup.wiregrpcserver:server:1.0.0-alpha04")
    implementation("io.grpc:grpc-stub:1.60.0")
    implementation("io.grpc:grpc-protobuf:1.60.0")
    implementation("io.grpc:grpc-netty:1.60.0")
    implementation("io.grpc:grpc-services:1.60.0")
}

buildscript {
    dependencies {
        classpath("com.squareup.wiregrpcserver:server-generator:1.0.0-alpha04")
    }
}