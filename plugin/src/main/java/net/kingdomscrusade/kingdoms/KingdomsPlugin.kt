package net.kingdomscrusade.kingdoms

import br.com.devsrsouza.kotlinbukkitapi.architecture.KotlinPlugin

@Suppress("unused")
class KingdomsPlugin : KotlinPlugin() {
    override fun onPluginEnable() {
        println("Hello World!")
    }
    override fun onPluginDisable() {
        println("Goodbye!")
    }
}