import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.kingdomscrusade.Kingdoms"
version = "0.2.0-alpha"

/* Module Versions */
val paper_version = "1.16.5-R0.1-SNAPSHOT"
val jda_version = "4.2.0_168"
val commandapi_version = "5.9"
val kotlinbukkitapi_version = "0.2.0-SNAPSHOT"

plugins {

    /* Languages */
    java
    kotlin("jvm") version "1.4.30"

    /* Helper plugins */
    // Shading Plugins
    id ("com.github.johnrengelman.shadow") version "6.1.0"
    // plugin-yml
    id("net.minecrell.plugin-yml.bukkit") version "0.3.0"
    // Lombok
    id("io.freefair.lombok") version "5.3.0"

}

repositories {

    mavenLocal()
    jcenter()

    /* Paper */
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }

    /* Modules */
    // CommandAPI
    maven { url = uri("https://raw.githubusercontent.com/JorelAli/CommandAPI/mvn-repo/") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
    // SimpleCommands
    maven { url = uri("https://jitpack.io") }

    /* Test Modules */
    // MockBukkit
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/public/") }

}

dependencies {

    /* Language */
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.30")

    /* Paper */
    compileOnly("com.destroystokyo.paper:paper-api:$paper_version"){
        because("Project extends from Paper JavaPlugin class")
    }

    /* Modules */
    // JDA
    implementation("net.dv8tion:JDA:$jda_version"){
        because("Used for Discord bot implementation")
    }

    // MongoDB
    implementation("org.mongodb:mongodb-driver-sync:4.2.2"){
        because("MongoDB Driver, Database usage")
    }
    implementation("org.mongojack:mongojack:4.0.2"){
        because("Mapping Mongos to POJOs")
    }

    /* Helpers */
    // CommandAPI
    implementation("dev.jorel:commandapi-shade:$commandapi_version")

    // SimpleCommands
    implementation("com.github.Paul2708.simple-commands:simple-commands:0.4.1")

    // Commons-text
    implementation("org.apache.commons:commons-text:1.1"){
        because("Used for better String placeholders")
    }

    /* Test Modules */
    // Junit 5
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")

    // Mockito
    testImplementation("org.mockito:mockito-core:3.+")

    // MockBukkit
    testImplementation("com.github.seeseemelk:MockBukkit-v1.16:0.25.0")

    // Fongo
    testImplementation("com.github.fakemongo:fongo:2.1.0")
}

bukkit{

    main = "net.kingdomscrusade.Kingdoms.KingdomsMain"
    apiVersion = "1.16"

    author = "LittleHillMYR"
    website = "kingdomscrusade.net"
    depend = listOf("KotlinBukkitAPI")

    description = "Kingdom management plugin for Kingdoms Crusade"

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<ShadowJar> {
    baseName = "app"
    classifier = ""
    version = ""
}

tasks.withType<Test> {
    useJUnitPlatform()
}
