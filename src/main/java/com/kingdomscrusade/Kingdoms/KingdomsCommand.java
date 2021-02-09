package com.kingdomscrusade.Kingdoms;

import com.kingdomscrusade.Kingdoms.exceptions.empire.*;
import com.kingdomscrusade.Kingdoms.exceptions.members.*;
import com.kingdomscrusade.Kingdoms.exceptions.anonymous.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KingdomsCommand implements CommandExecutor {

    KingdomsAPI api = new KingdomsAPI();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
//        if (!(sender instanceof Player)){
//            sender.sendMessage(ChatColor.RED + "[Kingdoms]: Only players can execute this command!");
//            return true;
//        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("kingdoms")){
            if (args.length == 0) {
                sender.sendMessage(ChatColor.YELLOW + "/kingdoms ?"); //Help command planned to be added
                return true;
            } else switch (args[0]){

                case "new": // /kingdoms new <KINGDOM> <OWNER> <COLOR>
                    if (player.hasPermission("Kingdoms.administration")) {
                        if (args.length != 3) {
                            sender.sendMessage(ChatColor.YELLOW + "Usage: /kingdoms new <NAME> <OWNER>");
                            return true;
                        } else {
                            try {
                                api.newKingdom(args[1], args[2], args[3]);
                                sender.sendMessage(ChatColor.GREEN + ((args[1] + " Empire which is ruled by " + args[2] + " has been successfully registered!")));
                                return true;
                            } catch (empireInList e) {
                                sender.sendMessage(ChatColor.RED + e.getMessage());
                                return true;
                            } catch (playerNotExist p) {
                                sender.sendMessage(ChatColor.RED + p.getMessage());
                                return true;
                            } catch (playerHasKingdom p){
                                sender.sendMessage(ChatColor.RED + p.getMessage());
                                return true;
                            } catch (colorNotExist c){
                                sender.sendMessage(ChatColor.RED + c.getMessage());
                            }
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "You have no permission!");
                        return true;
                    }

                case "remove": // /kingdoms remove <KINGDOM>
                    if (player.hasPermission("Kingdoms.administration")) {
                        if (args.length == 2) {
                            try {
                                api.removeKingdom(args[1]);
                                sender.sendMessage(ChatColor.GREEN + "Empire " + args[1] + " has been successfully deleted!");
                                return true;
                            } catch (empireNotExist e) {
                                sender.sendMessage(ChatColor.RED + e.getMessage());
                                return true;
                            }
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "You have no permission!");
                        return true;
                    }

                case "list": // /kingdoms list
                    try {
                        sender.sendMessage(ChatColor.GREEN + "Empire List: " + api.getList());
                        return true;
                    } catch (noEmpire n){
                        sender.sendMessage(ChatColor.RED + n.getMessage());
                        return true;
                    }
                case "members":
                    if (args.length == 1){
                        sender.sendMessage(ChatColor.YELLOW + "Usage: /kingdoms members ?");
                        return true;
                    } else switch (args[1]){

                        case "add": // /kingdoms members add <MEMBER> <KINGDOM>
                            if (args.length == 4){
                                if (api.checkPermission(args[2], args[3])) {
                                    try {
                                        api.addMember(args[2], args[3]);
                                        sender.sendMessage(ChatColor.GREEN + args[2] + " has been successfully added to empire " + args[3] + "!");
                                        return true;
                                    } catch (empireNotExist e) {
                                        sender.sendMessage(ChatColor.RED + e.getMessage());
                                        return true;
                                    } catch (memberDuplicated m) {
                                        sender.sendMessage(ChatColor.RED + m.getMessage());
                                        return true;
                                    } catch (playerNotExist p) {
                                        sender.sendMessage(ChatColor.RED + p.getMessage());
                                        return true;
                                    } catch (playerInAnotherKingdom p){
                                        sender.sendMessage(ChatColor.RED + p.getMessage());
                                        return true;
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED + "You have no permission!");
                                    return true;
                                }
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "Usage: /kingdoms members add <MEMBER> <KINGDOM>");
                                return true;
                            }

                        case "remove": // /kingdoms members remove <MEMBER> <KINGDOM>
                            if (args.length == 4){
                                if (api.checkPermission(args[2], args[3])) {
                                    try {
                                        api.removeMember(args[2], args[3]);
                                        sender.sendMessage(ChatColor.GREEN + args[2] + " has been successfully removed from empire " + args[3] + "!");
                                        return true;
                                    } catch (memberNotFound | playerNotExist m) {
                                        sender.sendMessage(ChatColor.RED + m.getMessage());
                                        return true;
                                    } catch (listNotFound l) {
                                        sender.sendMessage(ChatColor.RED + l.getMessage());
                                        return true;
                                    } catch (empireNotExist e) {
                                        sender.sendMessage(ChatColor.RED + e.getMessage());
                                        return true;
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED + "You have no permission!");
                                    return true;
                                }
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "Usage: /kingdoms members remove <MEMBER> <KINGDOM>");
                                return true;
                            }


                        case "list": // /kingdoms members list <KINGDOM>
                            if (args.length == 3){
                                try {
                                    String memberList = api.listMember(args[2]);
                                    if (memberList != null) {
                                        sender.sendMessage(ChatColor.GREEN + args[2] + " members: " + memberList);
                                        return true;
                                    } else {
                                        sender.sendMessage(ChatColor.RED + "Kingdom " + args[2] + " does not exist / Kingdom " + args[2] + " does not have any members yet.");
                                        return true;
                                    }
                                } catch (listNotFound l){
                                    sender.sendMessage(ChatColor.RED + l.getMessage());
                                    return true;
                                } catch (empireNotExist e){
                                    sender.sendMessage(ChatColor.RED + e.getMessage());
                                    return true;
                                }
                            } else {
                                sender.sendMessage(ChatColor.YELLOW + "Usage: /kingdoms members list <KINGDOM>");
                                return true;
                            }

                        case "?":
                            sender.sendMessage(ChatColor.YELLOW + "add - add a new member to kingdom (owner / mayor)");
                            sender.sendMessage(ChatColor.WHITE + "      Usage: /kingdoms members add <MEMBER> <KINGDOM>");

                            sender.sendMessage(ChatColor.YELLOW + "remove - remove a member to kingdom (owner / mayor)");
                            sender.sendMessage(ChatColor.WHITE + "      Usage: /kingdoms members remove <MEMBER> <KINGDOM>");

                            sender.sendMessage(ChatColor.YELLOW + "list - List member of a kingdom (owner / mayor)");
                            sender.sendMessage(ChatColor.WHITE + "      Usage: /kingdoms members list <KINGDOM>");

                            return true;

                        default:
                            sender.sendMessage(ChatColor.YELLOW + "/kingdoms members ?");
                            return true;
                    }

                case "mayor": // /kingdoms mayor <args1>
                    if (args.length == 1){
                        sender.sendMessage(ChatColor.YELLOW + "/kingdoms mayor ?");
                        return true;
                    } else {
                        switch (args[1]){

                            case "get": // /kingdoms mayor get <KINGDOM>
                                if (args.length == 3){
                                    try {
                                        sender.sendMessage(ChatColor.GREEN + "Mayor of empire " + args[2] + ": " + api.getMayor(args[2]));
                                        return true;
                                    } catch (noMayor n){
                                        sender.sendMessage(ChatColor.RED + n.getMessage());
                                        sender.sendMessage(ChatColor.RED + "Please contact any admins online to fix this issue ASAP!");
                                        return true;
                                    } catch (empireNotExist e){
                                        sender.sendMessage(ChatColor.RED + e.getMessage());
                                        return true;
                                    }
                                }

                            case "set": // /kingdoms mayor set <MEMBER> <KINGDOM>
                                if (api.checkOwnerPermission(args[2], args[3])){
                                    try{
                                        api.setMayor(args[2], args[3]);
                                        sender.sendMessage(ChatColor.GREEN + args[2] + " is now the mayor of empire " + args[3] + "!");
                                        return true;
                                    } catch (memberNotInList m){
                                        sender.sendMessage(ChatColor.RED + m.getMessage());
                                        return true;
                                    } catch (playerNotExist p){
                                        sender.sendMessage(ChatColor.RED + p.getMessage());
                                        return true;
                                    } catch (empireNotExist e){
                                        sender.sendMessage(ChatColor.RED + e.getMessage());
                                        return true;
                                    }
                                } else {
                                    sender.sendMessage(ChatColor.RED + "You have no permission!");
                                    return true;
                                }

                            case "?":
                                sender.sendMessage(ChatColor.YELLOW + "get - Retrieve the name of the mayor of the empire");
                                sender.sendMessage(ChatColor.WHITE + "      Usage: /kingdom mayor get <KINGDOM>");

                                sender.sendMessage(ChatColor.YELLOW + "set - Set a new mayor of the empire (Owner)");
                                sender.sendMessage(ChatColor.WHITE + "      Usage: /kingdom mayor set <MEMBER> <KINGDOM>");

                                return true;

                            default:
                                sender.sendMessage(ChatColor.YELLOW + "/kingdoms mayor ?");
                                return true;

                        }
                    }


                case "?":
                    sender.sendMessage(ChatColor.YELLOW + "new - Add a new kingdom (admin)");
                    sender.sendMessage(ChatColor.WHITE + "      Usage: /kingdoms new <KINGDOM> <OWNER> <COLOR>");

                    sender.sendMessage(ChatColor.YELLOW + "remove - Remove a kingdom (admin)");
                    sender.sendMessage(ChatColor.WHITE + "      Usage: /kingdoms remove <KINGDOM>");

                    sender.sendMessage(ChatColor.YELLOW + "list - List existing kingdoms");
                    sender.sendMessage(ChatColor.WHITE + "      Usage: /kingdoms list");

                    sender.sendMessage(ChatColor.YELLOW + "members - /kingdoms members ?");

                    return true;

                default:
                    sender.sendMessage(ChatColor.YELLOW + "/kingdoms ?"); //Help command planned to be added
                    return true;
            }

        }
        return false;
    }

}
