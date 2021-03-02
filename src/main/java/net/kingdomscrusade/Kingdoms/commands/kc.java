package net.kingdomscrusade.Kingdoms.commands;

import net.kingdomscrusade.Kingdoms.exceptions.channelNotFound;
import net.kingdomscrusade.Kingdoms.exceptions.kingdomNoDiscordChannel;
import net.kingdomscrusade.Kingdoms.exceptions.senderNoKingdom;
import net.kingdomscrusade.Kingdoms.exceptions.sqlError;
import net.kingdomscrusade.Kingdoms.modules.discord.DiscordActions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class kc implements CommandExecutor {

    DiscordActions discord = new DiscordActions();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("kc")){

            if (args.length == 0){
                sender.sendMessage(ChatColor.YELLOW +
                        "Type in your message after after \"/kc\" to send a message to your kingdom's chat!"
                );
                return true;
            }

            StringBuilder messageBuilder = new StringBuilder();
            int wordCount = 0;
            for (;wordCount != args.length - 1; wordCount++){
                messageBuilder.append(args[wordCount]).append(" ");
            }
            messageBuilder.append(args[wordCount]);

            try {
                discord.sendChatFromGame(player, messageBuilder.toString());
            } catch (sqlError | senderNoKingdom | kingdomNoDiscordChannel | channelNotFound e) {
                sender.sendMessage(ChatColor.RED + e.getMessage());
            }
            return true;

        }

        return false;

    }

}
