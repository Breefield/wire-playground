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
    sourcePath {
        srcDir("src/main/protos")
    }

    kotlin {}
}