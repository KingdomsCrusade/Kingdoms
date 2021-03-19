package net.kingdomscrusade.Kingdoms.command.commands;

import net.kingdomscrusade.Kingdoms.KingdomsCore;
import net.kingdomscrusade.Kingdoms.error.KingdomNameIsUsed;
import net.kingdomscrusade.Kingdoms.error.PlayerHasKingdom;
import net.kingdomscrusade.Kingdoms.error.PlayerNameNotExists;
import net.kingdomscrusade.Kingdoms.error.SqlError;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class create {

    KingdomsCore core = new KingdomsCore();

    public create(@NotNull CommandSender sender, @NotNull String[] args){

        if (args.length == 2 /*|  /kc create ?  |*/){
            if (args[1].equals("?")){
                sender.sendMessage(
                        "This command is used by staffs to create kingdoms.\n" +
                                "Format: /kc create <KingdomName> <OwnerName>"
                );
                return;
            }
        }

        if (args.length != 3){
            sender.sendMessage(
                    "Syntax error! Try out \"/kc create ?\" to check out how this command works!"
            );
            return;
        }

        try {
            core.createKingdom(
                    args[1],
                    args[2]
            );
        } catch (SqlError | KingdomNameIsUsed | PlayerNameNotExists | PlayerHasKingdom e){
            sender.sendMessage(
                    e.getMessage()
            );
            return;
        }
        sender.sendMessage(
                "Kingdom " + args[1] + " which is ruled by " + args[2] + " has been successfully added!"
        );

    }

}
