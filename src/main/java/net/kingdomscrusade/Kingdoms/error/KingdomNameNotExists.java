package net.kingdomscrusade.Kingdoms.error;

public class KingdomNameNotExists extends Exception{
    public KingdomNameNotExists(){
        super("Kingdom name given doesn't exists! ErrorCode: kingdomNameNotExists");
    }
}
