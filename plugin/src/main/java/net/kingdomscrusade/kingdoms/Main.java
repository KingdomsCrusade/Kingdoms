package net.kingdomscrusade.kingdoms;

import lombok.Getter;
import net.kingdomscrusade.kingdoms.commands.KcCommand;
import net.kingdomscrusade.kingdoms.mongo.Mongo;
import net.kingdomscrusade.kingdoms.mongo.pojo.Kingdoms;
import net.kingdomscrusade.kingdoms.utils.MessageLoader;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.mongojack.JacksonMongoCollection;


public class Main extends JavaPlugin {

    private static @Getter Plugin instance;
    private static @Getter JacksonMongoCollection<Kingdoms> kingdomsCollection;
    private static @Getter MessageLoader message;

    @Override
    public void onEnable(){

        // Config file Initiation
        saveDefaultConfig();
        message = new MessageLoader(this);

        // Database Initiation
        kingdomsCollection = Mongo.init();

        // Plugin Initiation
        instance = this;

        // Command Initialization
        new KcCommand().kc();

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Kingdoms]: Plugin is now enabled!");

    }

    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Kingdoms]: Plugin is now disabled!");
    }

}
