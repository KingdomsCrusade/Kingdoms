package net.kingdomscrusade.Kingdoms.command.commands.members.subcommands;

import net.kingdomscrusade.Kingdoms.KingdomsCore;
import net.kingdomscrusade.Kingdoms.error.KingdomNameNotExists;
import net.kingdomscrusade.Kingdoms.error.NoMemberExists;
import net.kingdomscrusade.Kingdoms.error.SqlError;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class list {
    KingdomsCore core = new KingdomsCore();
    public list(@NotNull CommandSender sender, @NotNull String[] args){
        if (args.length != 3){
            sender.sendMessage(
                    "Syntax error! Try out \"/kc members list ?\" to check out how this command works!"
            );
            return;
        }
        if (args[2].equals("?")){
            sender.sendMessage(
                    "This command returns a list of members of a kingdom.\n" +
                            "Format: /kc members list <KingdomName>"
            );
            return;
        }

        List<String> memberList;
        try {
            memberList = core.listMember(args[2]);
        } catch (KingdomNameNotExists | SqlError | NoMemberExists e) {
            sender.sendMessage(e.getMessage());
            return;
        }
        StringBuilder stringMemberList = new StringBuilder();
        int memberCount = 0;

        while (memberCount != memberList.size() - 1) {
            stringMemberList.append(memberList.get(memberCount));
            stringMemberList.append(", ");
            memberCount++;
        }

        stringMemberList.append(memberList.get(memberCount));
        sender.sendMessage("Members: " + stringMemberList.toString());

    }
}
