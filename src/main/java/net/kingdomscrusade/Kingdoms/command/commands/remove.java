package net.kingdomscrusade.Kingdoms.command.commands;

import net.kingdomscrusade.Kingdoms.KingdomsCore;
import net.kingdomscrusade.Kingdoms.error.KingdomNameNotExists;
import net.kingdomscrusade.Kingdoms.error.SqlError;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class remove {

    KingdomsCore core = new KingdomsCore();

    public remove(@NotNull CommandSender sender, @NotNull String[] args){

        if (args.length != 2){
            sender.sendMessage(
                    "Syntax error! Try out \"/kc remove ?\" to check out how this command works!"
            );
            return;
        }

        if (args[1].equals("?") /*|  /kc remove ?  |*/){
            sender.sendMessage(
                    "This command is used by staffs to remove kingdoms.\n" +
                            "Format: /kc remove <KingdomName>"
            );
            return;
        }

        try {
            core.removeKingdom(args[1]);
        } catch (SqlError | KingdomNameNotExists e){
            sender.sendMessage(
                    e.getMessage()
            );
            return;
        }
        sender.sendMessage(
                "Kingdom " + args[1] + " has been successfully deleted!"
        );

    }

}
