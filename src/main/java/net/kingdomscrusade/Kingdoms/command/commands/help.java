package net.kingdomscrusade.Kingdoms.command.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class help {
    public help(@NotNull CommandSender sender, @NotNull String[] args){

        sender.sendMessage(
                "KCDIGP Commands\n" +
                        "/kc create - Register a kingdom (STAFF ONLY)\n" +
                        "/kc remove - Remove a kingdom (STAFF ONLY)\n" +
                        "/kc list - List currently registered kingdoms\n" +
                        "/kc info - Retrieve info of a kingdom\n" +
                        "/kc members - Manage kingdom members"
        );

    }
}
