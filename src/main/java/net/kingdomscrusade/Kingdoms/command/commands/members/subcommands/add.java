package net.kingdomscrusade.Kingdoms.command.commands.members.subcommands;

import net.kingdomscrusade.Kingdoms.KingdomsCore;
import net.kingdomscrusade.Kingdoms.error.PlayerHasKingdom;
import net.kingdomscrusade.Kingdoms.error.PlayerNameNotExists;
import net.kingdomscrusade.Kingdoms.error.PlayerNoPermission;
import net.kingdomscrusade.Kingdoms.error.SqlError;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class add {
    KingdomsCore core = new KingdomsCore();
    public add(@NotNull CommandSender sender, @NotNull String[] args){
        if (args.length != 3){
            sender.sendMessage(
                    "Syntax error! Try out \"/kc members add ?\" to see how this command works!"
            );
            return;
        }
        if (args[2].equals("?")){
            sender.sendMessage(
                    "Executing this command let owners & mayors add members to their kingdom.\n" +
                            "Format: /kc members add <PlayerName>"
            );
            return;
        }

        Player player = (Player) sender;
        try{
            core.addMember(player, args[2]);
        } catch (SqlError | PlayerNoPermission | PlayerNameNotExists | PlayerHasKingdom e){
            sender.sendMessage(e.getMessage());
            return;
        }
        sender.sendMessage(
                args[2] + "has been successfully added to your kingdom!"
        );
    }
}
