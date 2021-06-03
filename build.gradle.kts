import com.google.protobuf.gradle.*

plugins {
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.protobuf") version "0.8.15"
    id("org.checkerframework") version "0.5.17"
    id("com.github.imflog.kafka-schema-registry-gradle-plugin") version "1.2.0"
    id("java-library")
    java
    idea
}

idea {
    module {
        sourceDirs = sourceDirs + file("generated/")
        generatedSourceDirs = generatedSourceDirs + file("generated/")
    }
}

configurations {
    compileOnly {
        extendsFrom
            annotationProcessor
    }
}

group = "com.pw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("http://packages.confluent.io/maven/")
}

buildscript {
    repositories {
        jcenter()
        maven("http://packages.confluent.io/maven/")
        maven("https://plugins.gradle.org/m2/")
        maven("https://jitpack.io")
    }
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src/main/java", "build/generated/source/openapi/src/main/java"))
        }
        resources {
            setSrcDirs(listOf("src/main/resources", "build/generated/source/openapi/src/main/resources"))
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.6.1"
    }
}

dependencies {
    // Spring
    implementation("org.springframework.boot", "spring-boot-starter-web")
    annotationProcessor("org.springframework.boot", "spring-boot-configuration-processor")

    // Security
    implementation("org.springframework.boot", "spring-boot-starter-security")
    implementation("org.springframework.boot", "spring-boot-starter-oauth2-resource-server")

    // Kafka
    implementation("org.springframework.kafka", "spring-kafka")

    // MongoDB
    implementation("org.springframework.boot", "spring-boot-starter-data-mongodb")

    // Lombok
    compileOnly("org.projectlombok", "lombok")
    annotationProcessor("org.projectlombok", "lombok")

    // Protobuf
    implementation("com.google.protobuf", "protobuf-java", "3.6.1")

    // Confluent
    implementation("io.confluent", "kafka-protobuf-serializer", "5.5.1")
    implementation("io.confluent", "kafka-schema-registry-client", "5.5.1")
    implementation("io.confluent", "monitoring-interceptors", "5.5.1")

    // Metrics
    implementation("org.springframework.boot", "spring-boot-starter-actuator", "2.4.0")
    implementation("io.micrometer", "micrometer-registry-prometheus", "latest.release")

    // Swagger
    implementation("io.swagger", "swagger-annotations", "1.6.2")
    implementation("org.openapitools", "jackson-databind-nullable", "0.2.1")

    // Tests
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    testImplementation("org.springframework.kafka", "spring-kafka-test")

    // Querydsl
    implementation("com.querydsl:querydsl-jpa")
    implementation("com.querydsl:querydsl-apt")
    compile("com.querydsl:querydsl-jpa:4.4.0")
    annotationProcessor("com.querydsl:querydsl-apt:4.4.0:jpa")
    testCompile("com.querydsl:querydsl-jpa:4.4.0")
    testAnnotationProcessor("com.querydsl:querydsl-apt:4.4.0:jpa")
}

tasks {

    bootJar {
        mainClass.set("com.pw.tms.TextMessageService")
        archiveFileName.set("text-message-service.jar")
        dependsOn("generateProto")
    }

    register<Exec>("dockerBuild") {
        group = "build"
        description = "Builds Docker Image"
        dependsOn("bootJar")
        commandLine("docker", "build", "-t", "text-message-service", "--build-arg", "PROFILE=dev", ".")
    }
}
