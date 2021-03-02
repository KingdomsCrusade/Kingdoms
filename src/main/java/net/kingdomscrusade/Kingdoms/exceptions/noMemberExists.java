package net.kingdomscrusade.Kingdoms.exceptions;

public class noMemberExists extends Exception{
    public noMemberExists(){
        super("There is no member in this kingdom! ErrorCode: noMemberExists");
    }
}
