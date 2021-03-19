package net.kingdomscrusade.Kingdoms.error;

public class PlayerNoPermission extends Exception{
    public PlayerNoPermission(){
        super("You have no permission to run this command! ErrorCode: playerNoPermission");
    }
}
