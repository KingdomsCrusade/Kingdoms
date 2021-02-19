package com.kingdomscrusade.Kingdoms.exceptions;

public class playerNoPermission extends Exception{
    public playerNoPermission(){
        super("You have no permission to run this command! ErrorCode: playerNoPermission");
    }
}
