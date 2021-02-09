package com.kingdomscrusade.Kingdoms;

import com.kingdomscrusade.Kingdoms.exceptions.anonymous.colorNotExist;
import com.kingdomscrusade.Kingdoms.exceptions.empire.empireInList;
import com.kingdomscrusade.Kingdoms.exceptions.empire.empireNotExist;
import com.kingdomscrusade.Kingdoms.exceptions.empire.noEmpire;
import com.kingdomscrusade.Kingdoms.exceptions.empire.noMayor;
import com.kingdomscrusade.Kingdoms.exceptions.members.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.inventivetalent.nicknamer.api.SimpleNickManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class KingdomsAPI {

    // Variables
    Plugin Main = KingdomsMain.getInstance();
    SimpleNickManager nick = new SimpleNickManager(Main);

    File kingdomFile = new File(Main.getDataFolder(), "/kingdoms.yml");
    FileConfiguration kingdomConfig = YamlConfiguration.loadConfiguration(kingdomFile);

    File playerFile = new File(Main.getDataFolder()+"/player.yml");
    FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

    String [] ColorList = {
            "DARK_RED",
            "RED",
            "GOLD",
            "YELLOW",
            "DARK_GREEN",
            "GREEN",
            "AQUA",
            "DARK_AQUA",
            "DARK_BLUE",
            "BLUE",
            "LIGHT_PURPLE",
            "DARK_PURPLE",
            "WHITE",
            "GRAY",
            "DARK_GRAY",
            "BLACK"
    };


    //Kingdoms
    public void newKingdom(String kingdomName, String owner, String color) throws empireInList, playerNotExist, playerHasKingdom, colorNotExist { //Planned to change to UUID format

        if (!(kingdomConfig.contains("Kingdoms." + kingdomName))) {

            UUID ownerID = getPlayerID(owner);
            if (ownerID != null) {

                String uppercaseColor = color.toUpperCase();
                if (Arrays.stream(ColorList).anyMatch(x -> x == uppercaseColor)) {

                    if (playerHasRole(ownerID)) {

                        String playerRole = getPlayerRole(ownerID);

                        if (playerIsOwner(ownerID, playerRole) || playerIsMayor(ownerID, playerRole)) {
                            throw new playerHasKingdom("This player is already managing another kingdom!");

                        } else {
                            //remove user from another kingdom's list
                            List<String> anotherMemberList = kingdomConfig.getStringList("Kingdoms." + getPlayerRole(ownerID) + ".members");
                            anotherMemberList.remove(String.valueOf(ownerID));
                            kingdomConfig.set("Kingdoms." + kingdomName + ".members", anotherMemberList);
                        }

                    }

                    //give role
                    addPlayerRole(ownerID, kingdomName);

                    List<String> list = kingdomConfig.getStringList("KingdomsList");
                    list.add(kingdomName);
                    kingdomConfig.set("KingdomsList", list);

                    kingdomConfig.set("Kingdoms." + kingdomName + ".owner", String.valueOf(ownerID));
                    kingdomConfig.set("Kingdoms." + kingdomName + ".mayor", String.valueOf(ownerID));

                    setColor(uppercaseColor, kingdomName);
                    setPlayerColor(Bukkit.getPlayer(owner), uppercaseColor);

                    saveKingdom();

                } else {
                    throw new colorNotExist("Wrong color name!");
                }
            } else{
                throw new playerNotExist("This player does not exist!");
            }
        } else {
            throw new empireInList("An empire with same name is in list!");
        }
    }

    public void removeKingdom(String kingdomName) throws empireNotExist {

        if (kingdomConfig.contains("Kingdoms." + kingdomName)){

            //remove all members and owner mayor's role
            UUID owner = (UUID) kingdomConfig.get("Kingdoms." + kingdomName + ".owner");
            UUID mayor = (UUID) kingdomConfig.get("Kingdoms." + kingdomName + ".mayor");
            List<UUID> members = (List<UUID>) kingdomConfig.get("Kingdoms." + kingdomName + ".members");

            removePlayerRole(owner);
            clearPlayerColor(Bukkit.getPlayer(owner));

            removePlayerRole(mayor);
            clearPlayerColor(Bukkit.getPlayer(mayor));

            Integer i = 0;
            while (i != members.size() - 1){
                removePlayerRole(members.get(i));
                clearPlayerColor(Bukkit.getPlayer(members.get(i)));
                i++;
            }

            kingdomConfig.set("Kingdoms." + kingdomName, null);

            List<String> list = kingdomConfig.getStringList("KingdomsList");
            list.remove(kingdomName);
            kingdomConfig.set("KingdomsList", list);

            saveKingdom();
        } else {
            throw new empireNotExist("Empire name given does not exist!");
        }

    }

    public String getList() throws noEmpire {
        if (kingdomConfig.contains("KingdomsList")) {
            List<?> list = kingdomConfig.getStringList("KingdomsList");
            StringBuilder sb = new StringBuilder();

            int i = 0;
            while (i < list.size() - 1) {
                sb.append(list.get(i));
                sb.append(", ");
                i++;
            }
            sb.append(list.get(i));

            String result = sb.toString();
            return result;
        } else {
            throw new noEmpire("None empire exist!");
        }
    }

    // Owner & Mayor
    public String getMayor(String kingdomName) throws empireNotExist, noMayor {

        if(kingdomConfig.contains("Kingdoms." + kingdomName)) {
            if (kingdomConfig.contains("Kingdoms." + kingdomName + ".mayor")) {
                return getPlayerName((UUID) kingdomConfig.get("Kingdoms." + kingdomName + ".mayor"));
            } else {
                throw new noMayor("This empire has no mayor!");
            }
        } else {
            throw new empireNotExist("Empire name given does not exist!");
        }

    }

    public void setMayor(String member, String kingdomName) throws playerNotExist, empireNotExist, memberNotInList {

        if (kingdomConfig.contains("Kingdoms." + kingdomName)){

            UUID memberID = getPlayerID(member);
            if (memberID != null){

                List<String> list= kingdomConfig.getStringList("Kingdoms." + kingdomName + ".members");
                if (list.contains(String.valueOf(memberID))){

                    kingdomConfig.set("Kingdoms." + kingdomName + ".mayor", String.valueOf(memberID));
                    list.remove(String.valueOf(memberID));
                    kingdomConfig.set("Kingdoms." + kingdomName + ".members", list);

                    saveKingdom();

                } else {
                    throw new memberNotInList("Player is not a member of the empire!");
                }
            } else {
                throw new playerNotExist("This player does not exist!");
            }
        } else {
            throw new empireNotExist("Empire name given does not exist!");
        }
    }


    //Members
    public void addMember(String newMember, String kingdomName) throws empireNotExist, memberDuplicated, playerNotExist, playerInAnotherKingdom { //Plan to change to UUID format

        if (kingdomConfig.contains("Kingdoms." + kingdomName)){

            UUID memberID = getPlayerID(newMember);
            if (memberID != null) {

                if (!(playerHasOtherRole(memberID, kingdomName))) {

                    List<String> list = kingdomConfig.getStringList("Kingdoms." + kingdomName + ".members");
                    if (!(list.contains(String.valueOf(memberID)))) {

                        addPlayerRole(memberID, kingdomName);

                        list.add(String.valueOf(memberID));
                        kingdomConfig.set("Kingdoms." + kingdomName + ".members", list);
                        addPlayerRole(memberID, kingdomName);
                        setPlayerColor(Bukkit.getPlayer(newMember), kingdomName);

                        saveKingdom();

                    } else {
                        throw new memberDuplicated("Same player has been found in this empire's member list!");
                    }

                } else {
                    throw new playerInAnotherKingdom("Player is a member of another kingdom!");
                }

            } else {
                throw new playerNotExist("This player does not exist!");
            }

        } else{
            throw new empireNotExist("Empire name given does not exist!");
        }
    }

    public void removeMember(String member, String kingdomName) throws memberNotFound, listNotFound, empireNotExist, playerNotExist {

        if (kingdomConfig.contains("Kingdoms." + kingdomName)){

            if (kingdomConfig.contains("Kingdoms." + kingdomName + ".members")){

                UUID memberID = getPlayerID(member);
                if (memberID != null) {

                    List<String> list = kingdomConfig.getStringList("Kingdoms." + kingdomName + ".members");
                    if (list.contains(String.valueOf(memberID))) {

                        removePlayerRole(memberID);

                        list.remove(String.valueOf(memberID));
                        kingdomConfig.set("Kingdoms." + kingdomName + ".members", list);

                        clearPlayerColor(Bukkit.getPlayer(member));

                        saveKingdom();

                    } else {
                        throw new memberNotFound("Member not found in this empire's member list!");
                    }

                } else {
                    throw new playerNotExist("This player does not exist!");
                }

            } else {
                throw new listNotFound("This empire currently has no members!");
            }

        } else {
            throw new empireNotExist("Empire name given does not exist!");
        }

    }

    public String listMember(String kingdomName) throws listNotFound, empireNotExist {

        if (kingdomConfig.contains("Kingdoms." + kingdomName)){

            if (kingdomConfig.contains("Kingdoms." + kingdomName + ".members")){

                List<?> list = kingdomConfig.getStringList("Kingdoms." + kingdomName + ".members");
                StringBuilder stringName = new StringBuilder();
                int i = 0;
                while (i < list.size() - 1){
                    stringName.append(getPlayerName((UUID) list.get(i)));
                    stringName.append(", ");
                    i++;
                }
                stringName.append(getPlayerName((UUID) list.get(i)));

                String result = stringName.toString();
                return result;

            } else{
                throw new listNotFound("This empire currently has no members!");
            }

        } else {
            throw new empireNotExist("Empire name given does not exist!");
        }

    }


    //YAML file
    private void saveKingdom() {
        try {
            kingdomConfig.save(kingdomFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePlayer(){
        try{
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Convenience
    private void addPlayerRole(UUID playerID, String kingdomName){
       playerConfig.set(String.valueOf(playerID) + "kingdom", kingdomName);
       savePlayer();
    }

    private void removePlayerRole(UUID playerID){
        playerConfig.set(String.valueOf(playerID) + "kingdom", null);
        savePlayer();
    }

    private boolean playerHasSameRole(UUID playerID, String kingdomName){
        if (playerConfig.getString(String.valueOf(playerID) + "kingdom") == kingdomName){
            return false;
        } else { return true; }
    }

    private boolean playerHasOtherRole(UUID playerID, String kingdomName){
        if (playerConfig.getString(String.valueOf(playerID) + "kingdom") != kingdomName){
            return false;
        } else { return true; }
    }

    public boolean playerHasRole(UUID playerID){
        return (playerConfig.contains(String.valueOf(playerID) + "kingdom"));
    }

    public String getPlayerRole(UUID playerID){
        return (playerConfig.getString(String.valueOf(playerID) + "kingdom"));
    }

    public UUID getPlayerID(String name){
        return Bukkit.getPlayerUniqueId(name);
    }

    private String getPlayerName(UUID id){
        return Bukkit.getPlayer(id).getName();
    }

    private boolean playerIsOwner(UUID id, String kingdomName){
        return (kingdomConfig.getString("Kingdoms." + kingdomName + ".owner") == String.valueOf(id));
    }

    private boolean playerIsMayor(UUID id, String kingdomName){
        return (kingdomConfig.getString("Kingdoms." + kingdomName + ".mayor") == String.valueOf(id));
    }

    public boolean checkOwnerPermission(String name, String kingdom){
        String player = String.valueOf(getPlayerID(name));
        return kingdomConfig.getString("Kingdoms." + kingdom + ".owner") == player;
    }


    public boolean checkPermission(String name, String kingdom){
        String player = String.valueOf(getPlayerID(name));
        return kingdomConfig.getString("Kingdoms." + kingdom + ".owner") == player || kingdomConfig.getString("Kingdoms." + kingdom + ".mayor") == player;
    }

    public void setColor(String color, String kingdom){
        kingdomConfig.set("Kingdoms." + kingdom + ".color", color);
    }

    public String getColor(String kingdom){
        return kingdomConfig.getString("Kingdoms." + kingdom + ".color");
    }

    public void setPlayerColor(Player player, String kingdom){
        switch (getColor(kingdom)){

            case "DARK_RED":
                nick.setNick(player.getUniqueId(), ChatColor.DARK_RED + player.getName());
                break;

            case "RED":
                nick.setNick(player.getUniqueId(), ChatColor.RED + player.getName());
                break;

            case "GOLD":
                nick.setNick(player.getUniqueId(), ChatColor.GOLD + player.getName());
                break;

            case "YELLOW":
                nick.setNick(player.getUniqueId(), ChatColor.YELLOW + player.getName());
                break;

            case "DARK_GREEN":
                nick.setNick(player.getUniqueId(), ChatColor.DARK_GREEN + player.getName());
                break;

            case "GREEN":
                nick.setNick(player.getUniqueId(), ChatColor.GREEN + player.getName());
                break;

            case "AQUA":
                nick.setNick(player.getUniqueId(), ChatColor.AQUA + player.getName());
                break;

            case "DARK_AQUA":
                nick.setNick(player.getUniqueId(), ChatColor.DARK_AQUA + player.getName());
                break;

            case "DARK_BLUE":
                nick.setNick(player.getUniqueId(), ChatColor.DARK_BLUE + player.getName());
                break;

            case "BLUE":
                nick.setNick(player.getUniqueId(), ChatColor.BLUE + player.getName());
                break;

            case "LIGHT_PURPLE":
                nick.setNick(player.getUniqueId(), ChatColor.LIGHT_PURPLE + player.getName());
                break;

            case "DARK_PURPLE":
                nick.setNick(player.getUniqueId(), ChatColor.DARK_PURPLE + player.getName());
                break;

            case "WHITE":
                nick.setNick(player.getUniqueId(), ChatColor.WHITE + player.getName());
                break;

            case "GRAY":
                nick.setNick(player.getUniqueId(), ChatColor.GRAY + player.getName());
                break;

            case "DARK_GRAY":
                nick.setNick(player.getUniqueId(), ChatColor.DARK_GRAY + player.getName());
                break;

            case "BLACK":
                nick.setNick(player.getUniqueId(), ChatColor.BLACK + player.getName());
                break;

        }
    }

    private void clearPlayerColor(Player player){
        nick.removeNick(player.getUniqueId());
    }
}
