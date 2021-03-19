package net.kingdomscrusade.Kingdoms.command.commands.members.subcommands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class index {
    public index(@NotNull CommandSender sender, @NotNull String[] args){
        sender.sendMessage(
                    "This command let players manage kingdom members! \n" +
                            "Format: /kingdoms members <arg1>\n" +
                            "Arguments available: add, remove/delete, list"
        );
    }
}
