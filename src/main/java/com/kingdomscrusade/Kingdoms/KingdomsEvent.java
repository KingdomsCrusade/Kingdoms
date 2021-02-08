package com.kingdomscrusade.Kingdoms;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class KingdomsEvent implements Listener {

    KingdomsAPI api = new KingdomsAPI();

    @EventHandler
    public void playerLogin (PlayerJoinEvent event, Player player){
        api.setPlayerColor(
                player,
                api.getPlayerRole(api.getPlayerID(player.getName()))
        );
    }

}
