rootProject.name = "csdeep"

pluginManagement {
    val kotlinVersion: String by settings
    val detektVersion: String by settings
    val springbootVersion: String by settings
    val dependencyManagementVersion: String by settings
    val nebulaReleaseVersion: String by settings
    val gradleNodeVersion: String by settings
    val liquibasePluginVersion: String by settings
    val jrebelPluginVersion: String by settings
    val spotBugsPluginVersion: String by settings
    val checkstyleVersion: String by settings
    val jasyptPluginVersion: String by settings

    plugins {
        kotlin("jvm").version(kotlinVersion)
        kotlin("plugin.spring").version(kotlinVersion)

        id("io.gitlab.arturbosch.detekt").version(detektVersion)
        id("com.github.spotbugs").version(spotBugsPluginVersion)
        id("checkstyle").version(checkstyleVersion)
        id("org.springframework.boot").version(springbootVersion)
        id("io.spring.dependency-management").version(dependencyManagementVersion)
        id("nebula.release").version(nebulaReleaseVersion)
        id("com.github.node-gradle.node").version(gradleNodeVersion)
        id("org.liquibase.gradle").version(liquibasePluginVersion)
        id("org.zeroturnaround.gradle.jrebel").version(jrebelPluginVersion)
        id("io.github.ximtech.jasypt-encrypt-plugin").version(jasyptPluginVersion)
    }
}