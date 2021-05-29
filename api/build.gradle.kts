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
val junit   : String by project

repositories {
    mavenCentral()
}

dependencies {
    /* MySQL */     implementation      (group = "mysql",                   name = "mysql-connector-java",  version = mysql)
    /* MariaDB */   implementation      (group = "org.mariadb.jdbc",        name = "mariadb-java-client",   version = mariadb)
    /* Exposed */   implementation      (group = "org.jetbrains.exposed",   name = "exposed-core",          version = exposed)
                    implementation      (group = "org.jetbrains.exposed",   name = "exposed-dao",           version = exposed)
                    implementation      (group = "org.jetbrains.exposed",   name = "exposed-jdbc",          version = exposed)
    /* Junit 5 */   testImplementation  (group = "org.junit.jupiter",       name = "junit-jupiter",         version = junit)
    /* KT-Test */   testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}