package net.kingdomscrusade.Kingdoms.error;

public class PlayerHasKingdom extends Exception{
    public PlayerHasKingdom(){
        super("This player is already in a kingdom! ErrorCode: playerHasKingdom");
    }
}
