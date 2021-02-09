package com.kingdomscrusade.Kingdoms;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class KingdomsEvent implements Listener {

    KingdomsAPI api = new KingdomsAPI();

    @EventHandler
    public void playerLogin (PlayerJoinEvent event){

        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();

        if (api.playerHasRole(playerID)) {

            api.setPlayerColor(
                    player,
                    api.getPlayerRole(playerID)
            );

        }
    }

}
