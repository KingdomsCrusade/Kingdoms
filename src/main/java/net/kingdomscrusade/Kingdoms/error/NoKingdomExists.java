package net.kingdomscrusade.Kingdoms.error;

public class NoKingdomExists extends Exception{
    public NoKingdomExists(){
        super("No kingdom exists! ErrorCode: noKingdomExists");
    }
}
