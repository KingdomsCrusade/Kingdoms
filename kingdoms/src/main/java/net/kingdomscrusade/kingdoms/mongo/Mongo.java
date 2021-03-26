package net.kingdomscrusade.kingdoms.mongo;

import com.mongodb.client.MongoClients;
import lombok.NonNull;
import lombok.val;
import lombok.var;
import net.kingdomscrusade.kingdoms.Main;
import net.kingdomscrusade.kingdoms.mongo.pojo.Kingdoms;
import org.bson.UuidRepresentation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.mongojack.JacksonMongoCollection;

public class Mongo {

    public static JacksonMongoCollection<Kingdoms> init(){

        /* Variables */
        val main = Main.getInstance();
        val config = main.getConfig();

        @NonNull val connectionString = config.getString("Database.connection");
        @NonNull val databaseName = config.getString("Database.database_name");

        /* Execution */
        Bukkit.getConsoleSender().sendMessage(
                ChatColor.YELLOW + "Establishing connection to database..."
        );

        val client = MongoClients.create(connectionString);
        val database = client.getDatabase(databaseName);

        var contain = false;
        for (String name: database.listCollectionNames()){
            if (name.equals("kingdoms")) {
                contain = true;
                break;
            }
        }
        if (!contain) database.createCollection("kingdoms");

        return JacksonMongoCollection.builder().build(
                database,
                "kingdoms",
                Kingdoms.class,
                UuidRepresentation.JAVA_LEGACY
        );

    }
}
