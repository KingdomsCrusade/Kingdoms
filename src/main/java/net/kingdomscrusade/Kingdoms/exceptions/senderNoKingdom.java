package net.kingdomscrusade.Kingdoms.exceptions;

public class senderNoKingdom extends Exception{
    public senderNoKingdom(){
        super("You are not in any kingdom! ErrorCode: senderNoKingdom");
    }
}
