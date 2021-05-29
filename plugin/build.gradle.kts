@file:Suppress("SpellCheckingInspection")

plugins {
    kotlin("jvm")
    id("net.minecrell.plugin-yml.bukkit")
}

// Package Coordinates //
group = "net.kingdomscrusade"
version = "0.2.0"

// Module Versions //
val paper       : String by project
val commandapi  : String by project
val kba         : String by project

repositories {
    /* Maven */             mavenCentral()
    /* Paper */             maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    /* CommandAPI */        maven { url = uri("https://jitpack.io") }
                            maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
    /* KotlinBukkitAPI */   maven { url = uri("http://nexus.devsrsouza.com.br/repository/maven-public/") }
}

dependencies {
    /* Paper */             compileOnly(group = "com.destroystokyo.paper", name = "paper-api", version = paper)
    /* CommandAPI */        compileOnly(group = "dev.jorel.CommandAPI", name = "commandapi-core", version = commandapi)
    /* KotlinBukkitAPI */   compileOnly(group = "br.com.devsrsouza.kotlinbukkitapi", name = "core", version = kba)
}

// plugin.yml
bukkit{
    main = "${project.group}.${project.name.toLowerCase()}.${project.name}Plugin"
    author = "LittleHillMYR"
    website = "kingdomscrusade.net"
    apiVersion = "1.16"
    depend = listOf("KotlinBukkitAPI")

    description = "Kingdom management plugin for Kingdoms Crusade"
}
