package net.kingdomscrusade.Kingdoms.exceptions;

public class kingdomNoDiscordChannel extends Exception{
    public kingdomNoDiscordChannel(){
        super("Your kingdom hasn't link any Discord channel! ErrorCode: kingdomNoDiscordChannel");
    }
}
