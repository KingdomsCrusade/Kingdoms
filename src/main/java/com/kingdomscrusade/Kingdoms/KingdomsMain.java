package com.kingdomscrusade.Kingdoms;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class KingdomsMain extends JavaPlugin {

    private static Plugin instance;
    private static Connection connection;
    String driver, url, username, password;

    @Override
    public void onEnable(){

        //Config file Initiation
        saveDefaultConfig();

        //Database Initiation
        DatabaseInit();

        //Plugin Initiation
        instance = this;
        getCommand("kingdoms").setExecutor(new KingdomsCommand());
        getServer().getPluginManager().registerEvents(new KingdomsEvent(), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Kingdoms]: Plugin is now enabled!");

    }

    private void DatabaseInit(){

        FileConfiguration configuration = getConfig();

        driver      = configuration.getString   ("Database.driver");
        url         = configuration.getString   ("Database.url");
        username    = configuration.getString   ("Database.username");
        password    = configuration.getString   ("Database.password");

        try{

            synchronized (this){
                if (getConnection() != null && !getConnection().isClosed()){
                    return;
                }

                //Getting Driver
                Class.forName(driver);

                //Connecting to the Database
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Attempting to connect to the database...");

                setConnection(DriverManager.getConnection
                        (url, username, password)
                );
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "DATABASE CONNECTED");
            }

        } catch (SQLException | ClassNotFoundException s){
            s.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public void setConnection(Connection connection){
        KingdomsMain.connection = connection;
    }

    public static Plugin getInstance(){
        return instance;
    }

    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Kingdoms]: Plugin is now disabled!");
    }

}
