package net.kingdomscrusade.kingdoms.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException

class MessageLoader (main: Plugin){

    private var messageFile: File
    private var messageConfig: FileConfiguration

    init {
        if (!main.dataFolder.exists()){
            main.dataFolder.mkdir()
        }

        messageFile = File(main.dataFolder, "/message.yml")
        messageConfig = YamlConfiguration.loadConfiguration(messageFile)
        if (!messageFile.exists()){
            try{
                messageFile.createNewFile()
            } catch (e: IOException){
                e.printStackTrace()
            }
        }
    }

    fun load(path: String, vararg input: String): String {
        val strings = input.asList()
        return String.format(messageConfig.getString(path)!!, strings)
    }

}