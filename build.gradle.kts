plugins {
    id("java-platform")
    id("maven-publish")
    id("version-catalog")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
    /**
     * NOTE: THE ABOVE WORKS because I have PLUGIN Management in settings.gradle.kts
     * This means that the versions for the plugins are managed centrally in the `settings.gradle.kts` file.
     *
     * Otherwise, you would need to include the version for each plugin directly here.
     * Example:
     *
     * id("io.spring.dependency-management") version "1.1.6"
     * kotlin("plugin.spring") version "1.9.25"
     *
     * This ensures that the correct versions of the plugins are applied.
     */
}

group = "com.symon.bank.core"
version = if (project.hasProperty("artefact.version") && project.hasProperty("build.version")) {
    // Dynamically sets the project version based on properties if they exist.
    // This allows you to specify custom artifact and build versions during the build process.
    val artefactVersion = project.property("artefact.version")
    val buildVersion = project.property("build.version")
    "$artefactVersion.$buildVersion"
} else {
    "0.1-SNAPSHOT" // Default version if no properties are specified.
}

// Load version properties from gradle.properties or command-line properties.
val kotlinVersion = project.property("kotlinVersion").toString()
val springBootVersion = project.property("springbootVersion").toString()
val springDependencyManagementVersion = project.property("springbootDependencyManagementVersion").toString()
val springCloudVersion = project.property("springCloudVersion").toString()

repositories {
    // Define where to fetch dependencies from. Maven Central is the primary repository used.
    mavenCentral()
}

catalog {
    versionCatalog {
        // Define a custom version catalog within this project. This allows you to alias dependencies and plugins.
        plugin("kotlinJvm", "org.jetbrains.kotlin.jvm").version(kotlinVersion)
        plugin("kotlinPluginSpring", "org.jetbrains.kotlin.plugin.spring").version(kotlinVersion)
        plugin("springBoot", "org.springframework.boot").version(springBootVersion)
        plugin("springDependencyManagement", "io.spring.dependency-management").version(springDependencyManagementVersion)
    }
}

javaPlatform {
    // Allow dependencies to be declared in this BOM project. This is necessary for BOM (Bill of Materials) files.
    allowDependencies()
}

dependencies {
    // Declare dependencies that should be available to other projects that import this BOM.
    val kotlinLoggingVersion = "3.0.5"

    // `api` dependencies are accessible to projects that use this BOM.
    api("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.boot:spring-boot-starter-actuator")
}

dependencyManagement {
    // Manage dependency versions centrally within this block, allowing consistent versions across projects.

    val mockkVersion = "1.13.9"
    val springMockkVersion = "4.0.2"

    dependencies {
        // Import BOMs (Bill of Materials) from other projects.
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:$springBootVersion")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springCloudVersion")
        }

        // Declare specific dependencies and their versions that are managed by this BOM.
        dependency("io.mockk:mockk:$mockkVersion")
        dependency("com.ninja-squad:springmockk:$springMockkVersion")
    }
}

tasks.withType<Test> {
    // Configure testing to use JUnit Platform, which is required for running JUnit 5 tests.
    useJUnitPlatform()
}

publishing {
    // Define the publications that you want to publish
    publications {
        // Create a Maven publication named "mavenJava"
        create<MavenPublication>("mavenJava") {
            // Specify that the publication should include the Java Platform component
            from(components["javaPlatform"])

            // Configure the POM (Project Object Model) file for this publication
            pom {
                // Set the name of the artifact in the POM
                name.set("Bank Core BOM")

                // Set a description for the artifact in the POM
                description.set("A BOM for managing dependencies")

                // Set the URL for the artifact's homepage or repository
                url.set("https://example.com")
            }
        }
    }

    // Define where the publications should be published
    repositories {
        // Publish the artifact to the local Maven repository
        mavenLocal()  // This specifies the local Maven repository at ~/.m2/repository
    }
}
