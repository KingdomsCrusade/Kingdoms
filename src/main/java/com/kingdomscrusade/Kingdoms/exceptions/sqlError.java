package com.kingdomscrusade.Kingdoms.exceptions;

public class sqlError extends Exception{
    public sqlError(){
        super("An error has happened! ErrorCode: SQLException");
    }
}
