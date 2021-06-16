@file:Suppress("SpellCheckingInspection")
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10" apply false
    id("net.minecrell.plugin-yml.bukkit") version "0.4.0"  apply false
}
subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    dependencies {
        val implementation by configurations
        implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect", version = "1.5.10")
    }
    tasks.withType<KotlinCompile>().all {
        kotlinOptions.jvmTarget = "16"
    }
}