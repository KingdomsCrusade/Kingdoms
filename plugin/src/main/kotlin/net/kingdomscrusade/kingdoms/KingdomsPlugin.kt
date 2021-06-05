package net.kingdomscrusade.kingdoms

import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class KingdomsPlugin : JavaPlugin() {
    override fun onEnable() {
        println("Hello World!")
    }
    override fun onDisable() {
        println("Goodbye!")
    }
}