package net.kingdomscrusade.Kingdoms.command.commands;

import net.kingdomscrusade.Kingdoms.KingdomsCore;
import net.kingdomscrusade.Kingdoms.error.NoKingdomExists;
import net.kingdomscrusade.Kingdoms.error.SqlError;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class list {

    KingdomsCore core = new KingdomsCore();

    public list(@NotNull CommandSender sender, @NotNull String[] args){

        if (args.length != 1){

            if (args.length == 2){
                if (args[1].equals("?")){
                    sender.sendMessage(
                            "This command returns a list of registered kingdoms.\n" +
                                    "Format: /kc list"
                    );
                    return;
                }
            }

            sender.sendMessage(
                    "Syntax error! Try out \"/kc list ?\" to see how this command works!"
            );
            return;
        }

        List<String> kingdomList;
        try{
            kingdomList = core.getKingdomList();
        } catch (SqlError | NoKingdomExists e){
            sender.sendMessage(
                    e.getMessage()
            );
            return;
        }
        StringBuilder stringKingdomList = new StringBuilder();
        int kingdomCount = 0;

        while (kingdomCount != kingdomList.size() - 1){
            stringKingdomList.append(kingdomList.get(kingdomCount));
            stringKingdomList.append(", ");
            kingdomCount++;
        }

        stringKingdomList.append(kingdomList.get(kingdomCount));
        sender.sendMessage("Kingdoms registered: " + stringKingdomList.toString());

    }

}
