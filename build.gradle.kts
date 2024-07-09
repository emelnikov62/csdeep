import Build_gradle.DataSourceDefaultConfig.PG_DB_PWD
import Build_gradle.DataSourceDefaultConfig.PG_DB_URL
import Build_gradle.DataSourceDefaultConfig.PG_DB_USER
import Build_gradle.DataSourceConfigProperty.PG_DB_PWD_PROP
import Build_gradle.DataSourceConfigProperty.PG_DB_URL_PROP
import Build_gradle.DataSourceConfigProperty.PG_DB_USER_PROP
import Build_gradle.TaskConstant.RUN_SCRIPT_PARAM
import Build_gradle.TaskConstant.RUN_SCRIPT_TASK
import Build_gradle.TaskConstant.BUILD_SCRIPT

println("********************************************************************************************************")
println("Running JVM version: ${System.getProperty("java.vm.name")} - ${System.getProperty("java.runtime.version")}")
println("Running gradle version: ${gradle.gradleVersion}")
println("********************************************************************************************************")

/*----------------------------PLUGIN DEPENDENCIES------------------------------------------------------*/
plugins {
    kotlin("jvm")
    kotlin("plugin.spring")

    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("io.gitlab.arturbosch.detekt")
    id("com.github.spotbugs") // htps://plugins.gradle.org/plugin/com.github.spotbugs
    id("nebula.release")
    id("org.liquibase.gradle")
    id("com.github.node-gradle.node")
    id("org.zeroturnaround.gradle.jrebel")
    id("io.github.ximtech.jasypt-encrypt-plugin")

    checkstyle
}

/*-----------------------------------DEPENDENCIES------------------------------------------------------*/
configurations {
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
        exclude("org.springframework.boot", "spring-boot-starter-tomcat")
        exclude("org.apache.tomcat", "tomcat-jdbc")
    }
    implementation {
        exclude("junit", "junit")
    }
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${project.properties["detektVersion"]}")
    spotbugs("com.github.spotbugs:spotbugs:${project.properties["spotBugsPluginVersion"]}")
    compileOnly("com.github.spotbugs:spotbugs-annotations:${project.properties["spotBugsPluginVersion"]}")
    liquibaseRuntime("com.microsoft.sqlserver:mssql-jdbc:${project.properties["sqlserverJdbcVersion"]}")
    liquibaseRuntime("org.liquibase:liquibase-core:${project.properties["liquibaseVersion"]}")

    /*-----------------------------------PRODUCTION DEPENDENCIES------------------------------------------------------*/
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${project.properties["kotlinVersion"]}")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${project.properties["kotlinVersion"]}")
    implementation("org.springframework.boot:spring-boot:${project.properties["springbootVersion"]}")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:${project.properties["springbootVersion"]}") {
        exclude("org.apache.tomcat", "tomcat-jdbc")
    }
    implementation("org.springframework.boot:spring-boot-autoconfigure:${project.properties["springbootVersion"]}")
    implementation("org.springframework.boot:spring-boot-starter-jetty:${project.properties["springbootVersion"]}")
    implementation("io.jsonwebtoken:jjwt:${project.properties["jjwtVersion"]}")
    implementation("org.apache.cxf:cxf-spring-boot-starter-jaxrs:${project.properties["cxfVersion"]}")
    implementation("org.springframework.boot:spring-boot-starter-security:${project.properties["springbootVersion"]}")
    implementation("org.springframework.security:spring-security-ldap:${project.properties["springSecurityVersion"]}")
    implementation("org.springframework.boot:spring-boot-starter-log4j2:${project.properties["springbootVersion"]}")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:${project.properties["mybatisStarterVersion"]}")
    implementation("org.springframework.boot:spring-boot-starter-actuator:${project.properties["springbootVersion"]}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${project.properties["jacksonVersion"]}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${project.properties["jacksonVersion"]}")
    implementation("com.fasterxml.jackson.core:jackson-core:${project.properties["jacksonVersion"]}")
    implementation("com.fasterxml.jackson.core:jackson-annotations:${project.properties["jacksonVersion"]}")
    implementation("com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider:${project.properties["jacksonVersion"]}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${project.properties["jacksonVersion"]}")
    implementation("org.liquibase:liquibase-core:${project.properties["liquibaseVersion"]}")
    implementation("com.microsoft.sqlserver:mssql-jdbc:${project.properties["sqlserverJdbcVersion"]}")
    implementation("com.zaxxer:HikariCP:${project.properties["hikariCPVersion"]}")
    implementation("org.apache.poi:poi:5.2.3")
    implementation("org.apache.poi:poi-ooxml:5.2.3")
    implementation("org.apache.commons:commons-lang3:${project.properties["apacheCommonsLang3Version"]}")
    implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:${project.properties["jasyptStarterVersion"]}")

    /*-----------------------------------TEST DEPENDENCIES------------------------------------------------------------*/
    testImplementation("org.junit.jupiter:junit-jupiter-api:${project.properties["junitVersion"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-params:${project.properties["junitVersion"]}")
    testImplementation("org.assertj:assertj-core:${project.properties["assertjVersion"]}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${project.properties["springbootVersion"]}")
    testImplementation("org.springframework.security:spring-security-test:${project.properties["springSecurityVersion"]}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${project.properties["junitVersion"]}")
    testRuntimeOnly("org.junit.platform:junit-platform-engine:${project.properties["junitPlatformVersion"]}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:${project.properties["junitPlatformVersion"]}")
    testRuntimeOnly("org.junit.platform:junit-platform-commons:${project.properties["junitPlatformVersion"]}")

    implementation ("org.postgresql:postgresql:42.5.4")
}

/*-----------------------------------PROJECT CONFIGURATION------------------------------------------------------*/
defaultTasks("clean", "check", "assemble")

val jvmVersion = JavaVersion.VERSION_17.majorVersion
val projectCurrentDate = (ext.properties["currentDate"] as groovy.lang.Closure<*>)() as String

group = project.properties["projectGroup"]!!

/*-----------------------------------TASKS CONFIGURATION------------------------------------------------------*/
@Suppress("UndocumentedPublicClass")
object TaskConstant {
    const val RUN_SCRIPT_TASK = "npmRunScript"
    const val RUN_SCRIPT_PARAM = "scriptName"

    const val BUILD_SCRIPT = "build"
}

@Suppress("UndocumentedPublicClass")
object DataSourceDefaultConfig {
    const val PG_DB_URL = "jdbc:postgresql://localhost:5432/postgres?currentSchema=csdeep"
    const val PG_DB_USER = "postgres"
    const val PG_DB_PWD = "postgres"
}

@Suppress("UndocumentedPublicClass")
object DataSourceConfigProperty {
    const val PG_DB_URL_PROP = "csdeep.datasource.jdbc-url"
    const val PG_DB_USER_PROP = "csdeep.datasource.username"
    const val PG_DB_PWD_PROP = "csdeep.datasource.password"
}

val extendSystemProperties = fun (systemProperties: MutableMap<String, Any>) {
    systemProperties[PG_DB_URL_PROP] = systemProperties[PG_DB_URL_PROP] ?: PG_DB_URL
    systemProperties[PG_DB_USER_PROP] = systemProperties[PG_DB_USER_PROP] ?: PG_DB_USER
    systemProperties[PG_DB_PWD_PROP] = systemProperties[PG_DB_PWD_PROP] ?: PG_DB_PWD
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = jvmVersion
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    systemProperties = System.getProperties() as MutableMap<String, Any>
    extendSystemProperties(systemProperties)
    useJUnitPlatform()
}

tasks.withType<org.springframework.boot.gradle.tasks.run.BootRun> {
    dependsOn(RUN_SCRIPT_TASK, tasks.withType<org.zeroturnaround.jrebel.gradle.IncrementalRebelGenerateTask>())
    systemProperties = System.getProperties() as MutableMap<String, Any>
    extendSystemProperties(systemProperties)
}

tasks.withType<ProcessResources> {
    project.ext.set(RUN_SCRIPT_PARAM, BUILD_SCRIPT)
    dependsOn(RUN_SCRIPT_TASK)
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = jvmVersion
}

tasks.withType<Jar> {
    manifest {
        attributes(
            mapOf(
                "Build-Time" to projectCurrentDate,
                "Implementation-Vendor" to "Master-Domino",
                "Implementation-Vendor-Id" to project.group,
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version
            )
        )
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.register<com.github.gradle.node.npm.task.NpmTask>(RUN_SCRIPT_TASK) {
    args.set(listOf("run", project.properties[RUN_SCRIPT_PARAM] as String))
    dependsOn(
        tasks.withType<com.github.gradle.node.npm.task.NpmSetupTask>(),
        tasks.withType<com.github.gradle.node.npm.task.NpmInstallTask>()
    )
}

/*-----------------------------------PLUGIN CONFIGURATION------------------------------------------------------*/
detekt {
    debug = true
    toolVersion = project.properties["detektVersion"] as String
    parallel = true
    disableDefaultRuleSets = false
    config = files(rootProject.projectDir.path + "/detekt-config.yml")
}
tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = jvmTarget
    reports {
        html.required.set(true)
        xml.required.set(true)
    }
}

checkstyle {
    configFile = File(rootDir, "md_checkstyle.xml")
    configDirectory.set(file(rootProject.projectDir))
}

tasks.checkstyleMain {
    mustRunAfter("processTestResources")
    mustRunAfter("compileTestKotlin")
    dependsOn("detekt")
    dependsOn("spotbugsMain")
    dependsOn("spotbugsTest")
}

tasks.check {
    dependsOn("detekt")
    dependsOn("checkstyleMain")
}

// https://github.com/spotbugs/spotbugs-gradle-plugin#configure-spotbugs-plugin
spotbugs {
    toolVersion.set(project.properties["spotBugsPluginVersion"] as String)
    excludeFilter.set(file(rootProject.projectDir.path + "/spotbugs-exclude.xml"))
}

// https://github.com/spotbugs/spotbugs-gradle-plugin#configure-the-spotbugstask
tasks.spotbugsMain {
    reports.create("html") {
        required.set(true)
    }
}

publishing {
    publications {
        create<MavenPublication>("csdeep")
    }
}

node {
    download.set(true)
    version.set(project.properties["nodeJsVersion"].toString())
    nodeProjectDir.set(file(project.projectDir.path + "/src/webapp"))
    npmVersion.set(project.properties["npmVersion"].toString())
}

liquibase {
    val sharedArguments = mapOf(
        "url" to System.getProperty(PG_DB_URL_PROP, PG_DB_URL),
        "username" to System.getProperty(PG_DB_USER_PROP, PG_DB_USER),
        "password" to System.getProperty(PG_DB_PWD_PROP, PG_DB_PWD)
    )

    activities.register("main") {
        arguments = HashMap<String, String>().apply {
            put("changeLogFile", "src/main/resources/db/csdeep-changelog.xml")
            putAll(sharedArguments)
        }
    }

    activities.register("test") {
        arguments = HashMap<String, String>().apply {
            put("changeLogFile", "src/test/resources/db/csdeep-test-changelog.xml")
            putAll(sharedArguments)
        }
    }
    runList = project.properties["liquibase.plugin.runList"]
}