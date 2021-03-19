package net.kingdomscrusade.Kingdoms.error;

public class NoMemberExists extends Exception{
    public NoMemberExists(){
        super("There is no member in this kingdom! ErrorCode: noMemberExists");
    }
}
