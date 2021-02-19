package com.kingdomscrusade.Kingdoms.exceptions;

public class playerNameNotExists extends Exception{
    public playerNameNotExists(){
        super("Player name given doesn't exists! ErrorCode: playerNameNotExists");
    }
}
