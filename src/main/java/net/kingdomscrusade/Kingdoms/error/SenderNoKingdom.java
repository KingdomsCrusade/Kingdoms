package net.kingdomscrusade.Kingdoms.error;

public class SenderNoKingdom extends Exception{
    public SenderNoKingdom(){
        super("You are not in any kingdom! ErrorCode: senderNoKingdom");
    }
}
