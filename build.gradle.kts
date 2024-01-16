plugins {
    java
    jacoco
    id("org.springframework.boot") version "3.1.3"
}

apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

val picocliVersion = "4.7.5"

dependencies {
    implementation("org.fusesource.jansi:jansi:2.4.0")
    implementation("info.picocli:picocli-spring-boot-starter:$picocliVersion")
    implementation("info.picocli:picocli-shell-jline3:$picocliVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("com.h2database:h2")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.flywaydb:flyway-core")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

group = "lab28.group4.asm1"
version = "1.0.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
}

tasks.register<JavaExec>("shell") {
    mainClass.set("lab28.group4.asm1.Application")
    args = listOf("shell")
    classpath = sourceSets["main"].runtimeClasspath
    standardInput = System.`in`
}

tasks.bootJar {
    doLast {
        val jarPath = archiveFile.get().asFile.absolutePath
        println(jarPath)
    }
}