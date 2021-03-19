package net.kingdomscrusade.Kingdoms.error;

public class PlayerNotMember extends Exception{
    public PlayerNotMember(){
        super("Player is not a member of your kingdom! ErrorCode: playerNotMember");
    }
}
