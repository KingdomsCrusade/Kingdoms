package net.kingdomscrusade.kingdoms.utils

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.text.MessageFormat

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

    fun load(path: String, vararg input: String): String? {
        val strings = input.asList()
        when (strings.size){
            1 -> return MessageFormat.format(
                messageConfig.getString(path)!!,
                strings[0]
            )
            2 -> return MessageFormat.format(
                messageConfig.getString(path)!!,
                strings[0],
                strings[1]
            )
            3 -> return MessageFormat.format(
                messageConfig.getString(path)!!,
                strings[0],
                strings[1],
                strings[2]
            )
            4 -> return MessageFormat.format(
                messageConfig.getString(path)!!,
                strings[0],
                strings[1],
                strings[2],
                strings[3]
            )
            5 -> return MessageFormat.format(
                messageConfig.getString(path)!!,
                strings[0],
                strings[1],
                strings[2],
                strings[3],
                strings[4]
            )
        }
        return null
    }

}