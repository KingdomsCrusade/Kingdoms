package net.kingdomscrusade.Kingdoms.command.commands;

import net.kingdomscrusade.Kingdoms.KingdomsCore;
import net.kingdomscrusade.Kingdoms.error.KingdomNameNotExists;
import net.kingdomscrusade.Kingdoms.error.SqlError;
import org.apache.commons.text.StrSubstitutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class info {

    KingdomsCore core = new KingdomsCore();

    public info(@NotNull CommandSender sender, @NotNull String[] args){

        if (args.length != 2){
            sender.sendMessage(
                    "Syntax error! Try out \"/kc info ?\" to check out how this command works!"
            );
            return;
        }

        if (args[1].equals("?")){
            sender.sendMessage(
                    "This command returns info of kingdom queried.\n" +
                            "Format: /kc info <KingdomName>"
            );
            return;
        }

        Map<String, String> info;
        try{
            info = core.getKingdomDetails(args[1]);
        } catch (SqlError | KingdomNameNotExists e){
            sender.sendMessage(
                    e.getMessage()
            );
            return;
        }
        String message =
                "${Kingdom}\n" +
                        "Owner: ${Owner}\n" +
                        "Mayor: ${Mayor}";

        sender.sendMessage(
                StrSubstitutor.replace(message, info)
        );


    }

}
