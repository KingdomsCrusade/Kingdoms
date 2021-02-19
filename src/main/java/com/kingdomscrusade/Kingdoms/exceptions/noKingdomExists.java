package com.kingdomscrusade.Kingdoms.exceptions;

public class noKingdomExists extends Exception{
    public noKingdomExists(){
        super("No kingdom exists! ErrorCode: noKingdomExists");
    }
}
