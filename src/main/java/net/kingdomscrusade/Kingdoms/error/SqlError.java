package net.kingdomscrusade.Kingdoms.error;

public class SqlError extends Exception{
    public SqlError(){
        super("An error has happened! ErrorCode: SQLException");
    }
}
