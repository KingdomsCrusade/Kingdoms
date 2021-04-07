package net.kingdomscrusade.kingdoms.mongo;

import com.mongodb.client.MongoClients;
import lombok.NonNull;
import lombok.val;
import net.kingdomscrusade.kingdoms.Main;
import net.kingdomscrusade.kingdoms.mongo.pojo.Kingdoms.Kingdoms;
import net.kingdomscrusade.kingdoms.mongo.pojo.Players.Players;
import org.bson.UuidRepresentation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.mongojack.JacksonMongoCollection;

import java.util.*;

public class Mongo {

    public static Map<String, JacksonMongoCollection<?>> init(){

        /* Variables */
        val main = Main.getInstance();
        val config = main.getConfig();

        @NonNull val connectionString = Objects.requireNonNull(config.getString("Database.connection"));
        @NonNull val databaseName = Objects.requireNonNull(config.getString("Database.database_name"));

        /* Execution */
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.YELLOW + "Establishing connection to database..."
        );

        val client = MongoClients.create(connectionString);
        val database = client.getDatabase(databaseName);

        // Collecting the collections name into a list
        List<String> collections = new ArrayList<>();
        database.listCollectionNames().forEach(collections::add);


        // Creating collections if not exists
        if (
                collections.stream()
                        .noneMatch(
                                name -> name.equals("kingdoms")
                        )
        )
            database.createCollection("kingdoms");

        if (
                collections.stream()
                        .noneMatch(
                                name -> name.equals("players")
                        )
        )
            database.createCollection("players");


        // Putting all of it into a map
        Map<String, JacksonMongoCollection<?>> map = new HashMap<>();
        map.put(
                "kingdoms",
                JacksonMongoCollection.builder().build(
                        database,
                        "kingdoms",
                        Kingdoms.class,
                        UuidRepresentation.JAVA_LEGACY
                )
        );
        map.put(
                "players",
                JacksonMongoCollection.builder().build(
                        database,
                        "players",
                        Players.class,
                        UuidRepresentation.JAVA_LEGACY
                )
        );

        return map;

    }
}
