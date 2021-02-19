package com.kingdomscrusade.Kingdoms;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KingdomsEvent implements Listener {

    Connection sql = KingdomsMain.getConnection();

    @EventHandler
    public void playerLogin (PlayerJoinEvent event){

        try {

            String ID = event.getPlayer().getUniqueId().toString();
            String Name = event.getPlayer().getName();

            PreparedStatement newRecord = sql.prepareStatement(
                    "INSERT INTO PlayerData (PlayerUUID, PlayerName)\n" +
                            "SELECT * FROM (SELECT ?, ?) AS tmp\n" +
                            "WHERE NOT EXISTS(\n" +
                            "    SELECT PlayerUUID FROM PlayerData WHERE PlayerUUID = ?\n" +
                            "    ) LIMIT 1;"
            );

            newRecord.setString(1, ID);
            newRecord.setString(2, Name);
            newRecord.setString(3, ID);
            newRecord.executeUpdate();

            event.getPlayer().sendMessage(ChatColor.GOLD + "Kingdoms Plugin v0.1.1-alpha is active!");

        } catch (SQLException e) {
            e.printStackTrace();
            event.getPlayer().sendMessage(ChatColor.RED + "FAILED TO RECORD YOUR DATA IN KINGDOM PLUGIN DATABASE");
        }

    }

}
