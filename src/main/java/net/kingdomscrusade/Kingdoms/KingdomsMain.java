package net.kingdomscrusade.Kingdoms;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.kingdomscrusade.Kingdoms.command.CommandManager;
import net.kingdomscrusade.Kingdoms.modules.discord.DiscordListener;
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

    // SQL
    private static Connection connection;
    String driver, url, username, password;

    // Discord
    private static JDA jda;
    String token;

    @Override
    public void onEnable(){

        //Config file Initiation
        saveDefaultConfig();

        //Database Initiation
        DatabaseInit();

        // Discord System Initialization
        DiscordInit();

        //Plugin Initiation
        instance = this;
        getCommand("kc").setExecutor(new CommandManager());
        //TODO Tab completer

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
                if (getDatabaseConnection() != null && !getDatabaseConnection().isClosed()){
                    return;
                }

                //Getting Driver
                Class.forName(driver);

                //Connecting to the Database
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Attempting to connect to the database...");

                setDatabaseConnection(DriverManager.getConnection
                        (url, username, password)
                );
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "DATABASE CONNECTED");
            }

        } catch (SQLException | ClassNotFoundException s){
            s.printStackTrace();
        }
    }

    private void DiscordInit(){

        try {

            // Fetching information from config file
            FileConfiguration configuration = getConfig();
            token = configuration.getString("Discord.token");

            // Connect
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Attempting to connect to the Discord...");
            jda = JDABuilder.createDefault(token)
                    .addEventListeners(new DiscordListener())
                    .setActivity(Activity.listening("your kingdom chats"))
                    .build();
            jda.awaitReady();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "DISCORD CONNECTED");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Plugin getInstance(){
        return instance;
    }

    public static Connection getDatabaseConnection(){
        return connection;
    }
    public void setDatabaseConnection(Connection connection){
        KingdomsMain.connection = connection;
    }

    public static JDA getDiscordConnection(){
        return jda;
    }

    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Kingdoms]: Plugin is now disabled!");
    }

}
