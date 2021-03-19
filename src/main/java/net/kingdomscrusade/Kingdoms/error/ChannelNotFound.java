package net.kingdomscrusade.Kingdoms.error;

public class ChannelNotFound extends Exception{
    public ChannelNotFound(){
        super("Current Discord channel set is not founded! ErrorCode: channelNotFound");
    }
}
