plugins {
    kotlin("jvm")
}

// Package Coordinates //
group = "net.kingdomscrusade"
version = "0.2.0"

// Module Versions //
val mysql   : String by project
val mariadb : String by project
val exposed : String by project
val slf4j   : String by project
val flyway  : String by project
val hikari  : String by project
val kotest  : String by project
val mockk   : String by project
val config  : String by project

repositories {
    mavenCentral()
}

dependencies {
    /* MySQL */     implementation      (group = "mysql",                   name = "mysql-connector-java",      version = mysql)
    /* MariaDB */   implementation      (group = "org.mariadb.jdbc",        name = "mariadb-java-client",       version = mariadb)
    /* Exposed */   implementation      (group = "org.jetbrains.exposed",   name = "exposed-core",              version = exposed)
                    implementation      (group = "org.jetbrains.exposed",   name = "exposed-dao",               version = exposed)
                    implementation      (group = "org.jetbrains.exposed",   name = "exposed-jdbc",              version = exposed)
    /* SLF4J */     implementation      (group = "org.slf4j",               name = "slf4j-simple",              version = slf4j)
    /* Flyway */    implementation      (group = "org.flywaydb",            name = "flyway-core",               version = flyway)
    /* Hikari */    implementation      (group = "com.zaxxer",              name = "HikariCP",                  version = hikari)

    /* Kotest */    testImplementation  (group = "io.kotest",               name = "kotest-runner-junit5",      version = kotest)
                    testImplementation  (group = "io.kotest",               name = "kotest-assertions-core",    version = kotest)
                    testImplementation  (group = "io.kotest",               name = "kotest-property",           version = kotest)
                    testImplementation  (group = "io.kotest",               name = "kotest-framework-datatest", version = kotest)
    /* Mockk */     testImplementation  (group = "io.mockk",                name = "mockk",                     version = mockk)
    /* Config */    testImplementation  (group = "com.typesafe",            name = "config",                    version = config)
}

tasks.test {
    useJUnitPlatform()
}