package net.kingdomscrusade.Kingdoms.command.commands.members;

import net.kingdomscrusade.Kingdoms.command.commands.members.subcommands.add;
import net.kingdomscrusade.Kingdoms.command.commands.members.subcommands.index;
import net.kingdomscrusade.Kingdoms.command.commands.members.subcommands.list;
import net.kingdomscrusade.Kingdoms.command.commands.members.subcommands.remove;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MemberManager {

    public static void pass(@NotNull CommandSender sender, @NotNull String[] args) {

        switch(args[1]){
            case "add":
                new add(sender,args);
                break;
            case "remove":
            case "delete":
                new remove(sender,args);
                break;
            case "list":
                new list(sender,args);
                break;
            default:
                new index(sender,args);
                break;
        }

    }

}
