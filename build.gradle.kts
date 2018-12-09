import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.nao4j.subtrol"
version = "1.0-SNAPSHOT"

plugins {
    application
    id("org.jetbrains.kotlin.jvm").version("1.3.11")
    id("org.jetbrains.kotlin.plugin.spring").version("1.3.11")
    id("org.springframework.boot").version("2.1.1.RELEASE")
}

repositories {
    mavenLocal()
    jcenter()
}

dependencies {
    compile(kotlin("stdlib"))
    compile(kotlin("reflect"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:1.3.11")
    implementation(platform("org.springframework.boot:spring-boot-dependencies:2.1.1.RELEASE"))
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    implementation("commons-io:commons-io:2.5")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("com.nhaarman:mockito-kotlin:1.6.0")
    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
}

application {
    mainClassName = "com.nao4j.subtrol.ApplicationKt"
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

tasks.getByName<KotlinCompile>("compileKotlin") {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
