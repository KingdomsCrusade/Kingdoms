package net.kingdomscrusade.Kingdoms.exceptions;

public class playerNotMember extends Exception{
    public playerNotMember(){
        super("Player is not a member of your kingdom! ErrorCode: playerNotMember");
    }
}
