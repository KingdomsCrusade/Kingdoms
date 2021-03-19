package net.kingdomscrusade.Kingdoms.error;

public class KingdomNameIsUsed extends Exception{
    public KingdomNameIsUsed(){
        super("Kingdom name given is used! ErrorCode: kingdomNameIsUsed");
    }
}
