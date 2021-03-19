package net.kingdomscrusade.Kingdoms.error;

public class KingdomNoDiscordChannel extends Exception{
    public KingdomNoDiscordChannel(){
        super("Your kingdom hasn't link any Discord channel! ErrorCode: kingdomNoDiscordChannel");
    }
}
