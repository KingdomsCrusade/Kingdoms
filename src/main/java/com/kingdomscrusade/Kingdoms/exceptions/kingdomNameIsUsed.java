package com.kingdomscrusade.Kingdoms.exceptions;

public class kingdomNameIsUsed extends Exception{
    public kingdomNameIsUsed(){
        super("Kingdom name given is used! ErrorCode: kingdomNameIsUsed");
    }
}
