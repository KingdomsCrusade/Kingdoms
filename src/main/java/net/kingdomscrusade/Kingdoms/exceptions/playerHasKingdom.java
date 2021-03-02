package net.kingdomscrusade.Kingdoms.exceptions;

public class playerHasKingdom extends Exception{
    public playerHasKingdom(){
        super("This player is already in a kingdom! ErrorCode: playerHasKingdom");
    }
}
