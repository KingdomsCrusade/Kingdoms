package com.kingdomscrusade.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public class KingdomsMain extends JavaPlugin {

    private static Plugin instance;

    @Override
    public void onEnable(){
        instance = this;
        init();
        getCommand("kingdoms").setExecutor(new KingdomsCommand());
        getServer().getPluginManager().registerEvents(new KingdomsEvent(), this);

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Kingdoms]: Plugin is now enabled!");

    }

    private void init() {

        if(!this.getDataFolder().exists()){
            this.getDataFolder().mkdir();
        }

        File kingdomFile = new File(this.getDataFolder()+"/kingdoms.yml");
        FileConfiguration kingdomConfig = YamlConfiguration.loadConfiguration(kingdomFile);
        if (!kingdomFile.exists()){
            try {
                kingdomFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File playerFile = new File(this.getDataFolder()+"/player.yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
        if (!playerFile.exists()){
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static Plugin getInstance(){
        return instance;
    }

    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Kingdoms]: Plugin is now disabled!");
    }

}
