package net.kingdomscrusade.Kingdoms.command;

import net.kingdomscrusade.Kingdoms.command.commands.*;
import net.kingdomscrusade.Kingdoms.command.commands.members.MemberManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("kc")) switch (args[0]){

            case "create":
            case "new":
            case "add":
                new create(sender, args);
                break;

            case "delete":
            case "remove":
                new remove(sender,args);
                break;

            case "list":
                new list(sender, args);
                break;

            case "info":
            case "details":
                new info(sender,args);
                break;

            case "help":
                new help(sender, args);
                break;

            case "members":
                MemberManager.pass(sender, args);
                break;

            default:
                sender.sendMessage(
                        "Check out \"/kc help\"!"
                );
                break;
        }

        return true;
    }
}
