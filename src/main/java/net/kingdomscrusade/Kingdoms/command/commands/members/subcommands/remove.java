package net.kingdomscrusade.Kingdoms.command.commands.members.subcommands;

import net.kingdomscrusade.Kingdoms.KingdomsCore;
import net.kingdomscrusade.Kingdoms.error.PlayerNameNotExists;
import net.kingdomscrusade.Kingdoms.error.PlayerNoPermission;
import net.kingdomscrusade.Kingdoms.error.PlayerNotMember;
import net.kingdomscrusade.Kingdoms.error.SqlError;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class remove {
    KingdomsCore core = new KingdomsCore();
    public remove(@NotNull CommandSender sender, @NotNull String[] args){
        if (args.length != 3){
            sender.sendMessage(
                    "Syntax error! Try out \"/kc create ?\" to check out how this command works!"
            );
            return;
        }
        if (args[2].equals("?")){
            sender.sendMessage(
                    "Executing this command let owners & mayors remove members to their kingdom.\n" +
                            "Format: /kc members remove <PlayerName>"
            );
            return;
        }

        Player player = (Player) sender;
        try {
            core.removeMember(player, args[2]);
        } catch (SqlError | PlayerNameNotExists | PlayerNoPermission | PlayerNotMember e) {
            sender.sendMessage(e.getMessage());
            return;
        }
        sender.sendMessage(
                args[2] + " has been successfully removed from your kingdom!"
        );
    }
}
