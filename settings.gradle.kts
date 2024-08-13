// Set the name of the root project. This is typically the main directory or project name.
rootProject.name = "finalyze-core-bom"

pluginManagement {
    // Define the repositories where Gradle will look for plugins.
    // Maven Central is one of the most common repositories.
    repositories {
        mavenCentral()
    }

    // Define and load the plugin versions from settings properties.
    // These properties should be defined in the `settings.gradle.kts` file or passed in as
    // Gradle properties (e.g., via `gradle.properties` or command-line).

    //in this case all values come from gradle.properties. The variable name has to be same as the properties defined in gradle.properties.
    val kotlinVersion: String by settings // Kotlin plugin version, e.g., "1.9.24"
    println(kotlinVersion)
    val springbootVersion: String by settings // Spring Boot plugin version, e.g., "3.3.2"
    val springbootDependencyManagementVersion: String by settings // Dependency management plugin version, e.g., "1.1.6"

    // Configure the plugins that will be available for use in your project.
    // The `plugins` block allows you to specify the plugin IDs and their respective versions.
    plugins {
        // Apply the Spring Boot plugin using the version specified above.
        id("org.springframework.boot") version springbootVersion

        // Apply the Spring dependency management plugin using the specified version.
        id("io.spring.dependency-management") version springbootDependencyManagementVersion

        // Apply the Kotlin Spring plugin using the Kotlin version specified above.
        // Note: There's a typo in your original code: `plugin.version` should be `jvm`.
        kotlin("plugin.spring") version kotlinVersion
    }
}
