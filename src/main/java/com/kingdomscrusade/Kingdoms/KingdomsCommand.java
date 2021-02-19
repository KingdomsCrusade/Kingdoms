package com.kingdomscrusade.Kingdoms;

import com.kingdomscrusade.Kingdoms.exceptions.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class KingdomsCommand implements CommandExecutor {

    KingdomsAPI api = new KingdomsAPI();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("kingdoms")){
            if (args.length == 0){

                sender.sendMessage(ChatColor.YELLOW +
                        "Execute command \"/kingdoms ?\" to check out how to use this plugin!"
                );

                return true;
            } else switch (args[0]){

                case "new":
                case "add":
                case "create": // /kingdoms create <kingdomName> <playerName>

                    // FUTURE Add permission check.

                    if (args.length == 2) {

                        if (args[1].equals("?")) {

                            sender.sendMessage(ChatColor.YELLOW +
                                    "This command is used by staffs to create kingdoms."
                            );

                            sender.sendMessage(ChatColor.YELLOW +
                                    "Format: /kingdoms create <kingdomName> <playerName>"
                            );

                            return true;

                        }

                    } else if (args.length != 3){

                        sender.sendMessage(ChatColor.YELLOW +
                                "Try out \"/kingdoms create ?\" to check out how this command works!"
                        );
                        return true;

                    } else {

                        try {
                            api.createKingdom(args[1], args[2]);
                            sender.sendMessage(ChatColor.GREEN +
                                    "Kingdom " + args[1] + " which is ruled by " + args[2] + " has been successfully added!"
                            );
                            return true;
                        } catch (sqlError | kingdomNameIsUsed | playerNameNotExists | playerHasKingdom e){
                            sender.sendMessage(ChatColor.RED + e.getMessage());
                            return true;
                        }

                    }

                case "delete":
                case "remove": // /kingdoms remove <kingdomName>

                    if (args.length != 2){

                        sender.sendMessage(ChatColor.YELLOW +
                                "Try out \"/kingdoms remove ?\" to check out how this command works!"
                        );
                        return true;

                    } else {

                        if (args[1].equals("?")) {

                            sender.sendMessage(ChatColor.YELLOW +
                                    "This command is used by staffs to remove kingdoms." + ChatColor.RED +
                                    "WARNING: DELETED KINGDOMS CANNOT BE RESTORED!"
                            );

                            sender.sendMessage(ChatColor.YELLOW +
                                    "Format: /kingdoms remove <kingdomName>"
                            );

                            return true;
                        } else {

                            try {
                                api.removeKingdom(args[1]);
                                sender.sendMessage(ChatColor.GREEN +
                                        "Kingdom " + args[1] + " has been successfully deleted!"
                                );
                                return true;
                            } catch (sqlError | kingdomNameNotExists e){
                                sender.sendMessage(ChatColor.RED + e.getMessage());
                                return true;
                            }

                        }

                    }

                case "list": // /kingdoms list

                    if (args.length == 2) {

                        if (args[1].equals("?")) {

                            sender.sendMessage(ChatColor.YELLOW +
                                    "This command returns a list of kingdoms registered."
                            );

                            sender.sendMessage(ChatColor.YELLOW +
                                    "Format: /kingdoms list"
                            );

                        } else {

                            sender.sendMessage(ChatColor.YELLOW +
                                    "Try out \"/kingdoms list ?\" to check out how this command works!"
                            );

                        }
                        return true;

                    } else {

                        try{

                            List<String> kingdomList = api.getKingdomList();
                            StringBuilder stringKingdomList = new StringBuilder();
                            int kingdomCount = 0;

                            while (kingdomCount != kingdomList.size() - 1){
                                stringKingdomList.append(kingdomList.get(kingdomCount));
                                stringKingdomList.append(", ");
                                kingdomCount++;
                            }

                            stringKingdomList.append(kingdomList.get(kingdomCount));
                            sender.sendMessage(ChatColor.GREEN + "Kingdoms registered: " + ChatColor.WHITE + stringKingdomList.toString());
                            return true;

                        } catch (noKingdomExists | sqlError e){
                            sender.sendMessage(ChatColor.RED + e.getMessage());
                            return true;
                        }

                    }

                case "info":
                case "details": // /kingdoms details <kingdomName>

                    if (args.length != 2){

                        sender.sendMessage(ChatColor.YELLOW +
                                "Try out \"/kingdoms list ?\" to check out how this command works!"
                        );
                        return true;

                    } else {

                        if (args[1].equals("?")) {

                            sender.sendMessage(ChatColor.YELLOW +
                                    "This command returns details of kingdom given."
                            );

                            sender.sendMessage(ChatColor.YELLOW +
                                    "Format: /kingdoms details <kingdomName>"
                            );

                            return true;

                        } else {

                            try {
                                List<String> details = api.getKingdomDetails(args[1]);

                                sender.sendMessage(ChatColor.GOLD + "Details of Kingdom " + details.get(0));
                                sender.sendMessage(ChatColor.WHITE + "OWNER: " + ChatColor.GRAY + details.get(1));
                                sender.sendMessage(ChatColor.WHITE + "MAYOR: " + ChatColor.GRAY + details.get(2));

                                return true;

                            } catch (sqlError | kingdomNameNotExists e) {
                                sender.sendMessage(ChatColor.RED + e.getMessage());
                                return true;
                            }

                        }
                    }

                case "members": // /kingdoms members <arg1>

                    if (args.length == 1){

                        sender.sendMessage(ChatColor.YELLOW +
                                "Check out \"/kingdoms members ?\" to check out its subcommands!"
                        );

                        return true;

                    } else switch (args[1]) {

                        case "add": // /kingdoms members add <playerName>

                            if (args.length != 3) {

                                sender.sendMessage(ChatColor.YELLOW +
                                        "Try out \"/kingdoms members add ?\" to check out how this command works!"
                                );

                                return true;

                            } else {

                                if (args[2].equals("?")) {

                                    sender.sendMessage(ChatColor.YELLOW +
                                            "Executing this command let owners & mayors add members to their kingdom."
                                    );

                                    sender.sendMessage(ChatColor.YELLOW +
                                            "Format: /kingdoms members add <playerName>"
                                    );

                                    return true;

                                } else {

                                    try {
                                        Player player = (Player) sender;
                                        api.addMember(player, args[2]);
                                        sender.sendMessage(ChatColor.GREEN +
                                                args[2] + " has been successfully added to your kingdom!"
                                        );
                                        return true;
                                    } catch (sqlError | playerNameNotExists | playerNoPermission | playerHasKingdom e) {
                                        sender.sendMessage(ChatColor.RED + e.getMessage());
                                        return true;
                                    }

                                }

                            }

                        case "delete":
                        case "remove": // /kingdoms members remove <playerName>

                            if (args.length != 3) {

                                sender.sendMessage(ChatColor.YELLOW +
                                        "Try out \"/kingdoms members remove ?\" to check out how this command works!"
                                );

                                return true;

                            } else {

                                if (args[2].equals("?")) {

                                    sender.sendMessage(ChatColor.YELLOW +
                                            "Executing this command let owners & mayors remove members to their kingdom."
                                    );

                                    sender.sendMessage(ChatColor.YELLOW +
                                            "Format: /kingdoms members remove <playerName>"
                                    );

                                    return true;

                                } else {

                                    try {
                                        Player player = (Player) sender;
                                        api.removeMember(player, args[2]);
                                        sender.sendMessage(ChatColor.GREEN +
                                                args[2] + " has been successfully removed from your kingdom!"
                                        );
                                        return true;
                                    } catch (sqlError | playerNameNotExists | playerNoPermission | playerNotMember e) {
                                        sender.sendMessage(ChatColor.RED + e.getMessage());
                                        return true;
                                    }

                                }

                            }

                        case "list": // /kingdoms members list <kingdomName>

                            if (args.length != 3) {

                                sender.sendMessage(ChatColor.YELLOW +
                                        "Try out \"/kingdoms members list ?\" to check out how this command works!"
                                );

                                return true;

                            } else {

                                if (args[2].equals("?")) {

                                    sender.sendMessage(ChatColor.YELLOW +
                                            "This command returns a list of members of a kingdom."
                                    );

                                    sender.sendMessage(ChatColor.YELLOW +
                                            "Format: /kingdoms members list <kingdomName>"
                                    );

                                    return true;

                                } else {

                                    try {

                                        List<String> memberList = api.listMember(args[2]);
                                        StringBuilder stringMemberList = new StringBuilder();
                                        int memberCount = 0;

                                        while (memberCount != memberList.size() - 1) {
                                            stringMemberList.append(memberList.get(memberCount));
                                            stringMemberList.append(", ");
                                            memberCount++;
                                        }

                                        stringMemberList.append(memberList.get(memberCount));
                                        sender.sendMessage(ChatColor.GREEN + "Members: " + ChatColor.WHITE + stringMemberList.toString());
                                        return true;

                                    } catch (kingdomNameNotExists | sqlError | noMemberExists e) {
                                        sender.sendMessage(ChatColor.RED + e.getMessage());
                                        return true;
                                    }

                                }

                            }


                        case "?":

                            sender.sendMessage(ChatColor.YELLOW + "This command let players manage kingdom members! ");
                            sender.sendMessage(ChatColor.YELLOW + "Format: /kingdoms members <arg1>");
                            sender.sendMessage(ChatColor.YELLOW + "Arguments available: add, remove/delete, list");

                            return true;

                        default:

                            sender.sendMessage(ChatColor.YELLOW +
                                    "Check out \"/kingdoms members ?\" to check out its subcommands!"
                            );

                            return true;

                    }

                    // FUTURE New arg[0] : "properties"

                case "?":

                    sender.sendMessage(ChatColor.YELLOW + "Kingdoms plugin is a plugin dedicated to help players manage kingdoms and provide benefits to kingdoms registered!");
                    sender.sendMessage(ChatColor.YELLOW + "Format: /kingdoms <arg1>");
                    sender.sendMessage(ChatColor.YELLOW + "Arguments available: add/create, remove/delete, list, details/info, members");

                    return true;

                default:

                    sender.sendMessage(ChatColor.YELLOW +
                            "Execute command \"/kingdoms ?\" to check out how to use this plugin!"
                    );

                    return true;

            }
        }

        return false;
    }

}
