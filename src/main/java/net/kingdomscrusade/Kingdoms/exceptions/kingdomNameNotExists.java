package net.kingdomscrusade.Kingdoms.exceptions;

public class kingdomNameNotExists extends Exception{
    public kingdomNameNotExists(){
        super("Kingdom name given doesn't exists! ErrorCode: kingdomNameNotExists");
    }
}
