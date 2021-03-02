package net.kingdomscrusade.Kingdoms.exceptions;

public class channelNotFound extends Exception{
    public channelNotFound(){
        super("Current Discord channel set is not founded! ErrorCode: channelNotFound");
    }
}
