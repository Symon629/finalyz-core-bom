# Getting Started


The version-catalog is an important feature in Gradle that allows you to manage dependencies and plugin versions in a centralized and consistent way across multiple projects. It essentially acts as a custom catalog where you define and group your dependencies and plugins.

Why is catalog Important?
Centralized Version Management:

The catalog allows you to define versions of dependencies and plugins in one place. This centralization makes it easy to update versions across all projects that reference the catalog, reducing the risk of version conflicts and making maintenance easier.
Consistency Across Projects:

By defining dependencies and plugins in a version-catalog, you ensure that all projects within a multi-module or multi-repository setup use the same versions. This consistency is crucial in large projects where different modules or teams might otherwise drift in dependency versions, leading to integration issues.
Reusability:

The catalog can be shared across multiple projects or modules, making it a reusable asset. If you have a set of dependencies or plugins commonly used across many projects, you can define them in a catalog and import this catalog wherever needed.
Simplification of Dependency Management:

With the version-catalog, you can define aliases for your dependencies and plugins, making it easier to refer to them. This makes build scripts cleaner and more understandable, especially for large projects with numerous dependencies.
Separation of Concerns:

The version-catalog helps to separate the concerns of dependency management from the rest of the build logic. This modularization improves the maintainability of your build scripts and reduces the cognitive load on developers.
Use Cases for catalog
Multi-Module Projects:

In a large project with multiple modules (e.g., a microservices architecture), you can use a version-catalog to manage dependencies and plugins across all modules. This ensures that every module is using the same versions, reducing the risk of compatibility issues.
Company-Wide Standards:

If your organization has certain libraries, tools, or frameworks that must be used across all projects, you can define these in a version-catalog. This enforces company-wide standards and simplifies the onboarding process for new projects.
Open-Source Projects:

In an open-source project where contributors might add new modules or dependencies, a version-catalog helps maintain consistency in dependency versions, ensuring that the project remains stable and easier to maintain.
Plugin Version Management:

Besides managing library dependencies, the version-catalog is useful for managing plugin versions. This is particularly valuable when working with tools like Spring Boot, where specific plugin versions might be tightly coupled with the libraries used.
Dependency Aliasing:

If a project uses different versions of the same dependency in different contexts (e.g., different environments), you can use aliases in the version-catalog to manage these variations cleanly and consistently.
Example of version-catalog Usage
In your build script, you could define a catalog like this

````dtd
catalog {
    versionCatalog {
        version("kotlin", "1.9.24")
        version("springBoot", "3.3.2")
        
        library("kotlin-stdlib", "org.jetbrains.kotlin:kotlin-stdlib").versionRef("kotlin")
        library("spring-web", "org.springframework.boot:spring-boot-starter-web").versionRef("springBoot")
        
        plugin("kotlinJvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
        plugin("springBoot", "org.springframework.boot").versionRef("springBoot")
    }
}

````

In another project or module, you can then refer to these libraries and plugins like this:

```dtd

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.spring.web)
}

plugins {
    id(libs.plugins.kotlinJvm)
    id(libs.plugins.springBoot)
}

```
This usage makes the build script more readable and maintainable, while ensuring that any changes to versions are propagated consistently across all referencing projects.



## In order to use this file you will need to publish to a maven repository
 We will push this to maven local or a maven repopsitory or a nexus repoistory. 
 For now we will be using:
 _**./gradlew publishToMavenLocal_**

and in the other project you 

// Import the BOM file from the bank-core-bom project
implementation(platform("com.symon.bank.core:bank-core-bom:0.1-SNAPSHOT"))

3. Configure Repositories
   Ensure that your project is configured to look for dependencies in the correct repositories. If you published the BOM to your local Maven repository, your repositories block should include mavenLocal():

```
repositories {
    mavenCentral()
    mavenLocal() // Add this if you're using a locally published BOM
}

```
4. Sync and Build

5. After adding the BOM file, sync your Gradle project (in IntelliJ, click "Reload All Gradle Projects" from the Gradle tool window) and rebuild your project to ensure everything is working correctly.