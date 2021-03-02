package net.kingdomscrusade.Kingdoms.modules;

import net.kingdomscrusade.Kingdoms.KingdomsMain;
import org.bukkit.configuration.file.FileConfiguration;

public class  yamlManager {

    FileConfiguration yaml = KingdomsMain.getInstance().getConfig();

    public String ownerRoleID(){
        return yaml.getString("Discord.kingdomOwnerRoleID");
    }

    public void setOwnerRoleID(String roleID){
        yaml.set("Discord.kingdomOwnerRoleID", roleID);
    }

    public String prefix(){
        return yaml.getString("Discord.prefix");
    }

    public void setPrefix(String prefix){
        yaml.set("Discord.prefix", prefix);
    }

}
