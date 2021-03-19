package net.kingdomscrusade.Kingdoms.error;

public class PlayerNameNotExists extends Exception{
    public PlayerNameNotExists(){
        super("Player name given doesn't exists! ErrorCode: playerNameNotExists");
    }
}
