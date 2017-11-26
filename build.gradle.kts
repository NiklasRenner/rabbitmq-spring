import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import kotlin.reflect.jvm.internal.impl.config.KotlinCompilerVersion

buildscript {
    val kotlinVersion: String by extra
    val springBootVersion: String by extra

    repositories {
        mavenCentral()
        maven(url = "https://repo.spring.io/milestone")
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
    }
}

group = "dk.renner.rpc"
version = "1.0.0-SNAPSHOT"

apply {
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("com.rabbitmq:amqp-client:2.7.1")

    testCompile("org.springframework.boot:spring-boot-starter-test")
}

// use repositories from buildscript block instead of duplicating configuration
repositories.addAll(project.buildscript.repositories)
// hack to get 'compile' etc. functions working in dependencies block, can be done with kotlin plugin, but then explicit version required, and gradle.properties not accessible here..
plugins { java }
