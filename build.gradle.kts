import org.apache.tools.ant.taskdefs.ExecTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }

    dependencies {
        val springBootVersion = "2.0.0.M6"
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    }
}

group = "dk.renner.rpc"
version = "1.0.0-SNAPSHOT"

plugins {
    val kotlinVersion = "1.1.60"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
}

apply {
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict -Xcoroutines=enable")
        }
    }

    createTask("runRabbitmqContainer", Exec::class) {
        workingDir("rabbitmq-docker")
        executable(File(workingDir, "run.sh"))
    }
}

repositories.addAll(project.buildscript.repositories)

dependencies {
    compile(kotlin("stdlib-jre8"))
    compile(kotlin("reflect"))
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.19.3")

    compile("org.springframework.boot:spring-boot-starter-web")
    compile("com.rabbitmq:amqp-client:2.7.1")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}
