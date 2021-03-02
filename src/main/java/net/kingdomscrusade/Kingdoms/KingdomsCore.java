package net.kingdomscrusade.Kingdoms;

import net.kingdomscrusade.Kingdoms.exceptions.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class KingdomsCore {

    // Variables
//    Plugin plugin = KingdomsMain.getInstance();
    Connection sql = KingdomsMain.getDatabaseConnection();

    String[] ColorList = {
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
    public void createKingdom(String kingdomName, String playerName) throws sqlError, kingdomNameIsUsed, playerNameNotExists, playerHasKingdom {

        try {

            UUID playerUUID = Bukkit.getPlayerUniqueId(playerName);
            if (playerUUID == null) throw new playerNameNotExists();
            if (checkPlayerExistence(playerUUID)) {

                if (!(checkKingdomExistence(kingdomName))) {

                    if (!(playerHasKingdom(playerUUID))) {

                        //Insert new kingdom into KingdomData
                        PreparedStatement insertNewKingdom = sql.prepareStatement(
                                "INSERT INTO KingdomData (KingdomData.Kingdom, KingdomData.Owner) VALUES (?, ?)"
                        );
                        insertNewKingdom.setString(1, kingdomName);
                        insertNewKingdom.setString(2, playerUUID.toString());
                        insertNewKingdom.executeUpdate();

                        //Update player's kingdom to new kingdom
                        PreparedStatement updatePlayerKingdom = sql.prepareStatement(
                                "UPDATE PlayerData SET PlayerData.Kingdom = ?, PlayerData.Pos = 'Owner' WHERE PlayerData.PlayerUUID = ?"
                        );
                        updatePlayerKingdom.setString(1, kingdomName);
                        updatePlayerKingdom.setString(2, playerUUID.toString());
                        updatePlayerKingdom.executeUpdate();

                    } else {
                        throw new playerHasKingdom();
                    }

                } else {
                    throw new kingdomNameIsUsed();
                }

            } else {
                throw new playerNameNotExists();
            }
        } catch (SQLException s) {
            s.printStackTrace();
            throw new sqlError();
        }

    }

    public void removeKingdom(String kingdomName) throws kingdomNameNotExists, sqlError {

        try {

            if (checkKingdomExistence(kingdomName)) {

                // Delete the kingdom from KingdomData Table
                PreparedStatement deleteKingdom = sql.prepareStatement(
                        "DELETE FROM KingdomData WHERE KingdomData.Kingdom = ?"
                );
                deleteKingdom.setString(1, kingdomName);
                deleteKingdom.executeUpdate();

                //Update all members kingdom
                PreparedStatement updateRole = sql.prepareStatement(
                        "UPDATE PlayerData SET PlayerData.Kingdom = null, PlayerData.Pos = null WHERE PlayerData.Kingdom = ?"
                );
                updateRole.setString(1, kingdomName);
                updateRole.executeUpdate();

            } else {
                throw new kingdomNameNotExists();
            }

        } catch (SQLException s) {
            s.printStackTrace();
            throw new sqlError();
        }

    }


    public List<String> getKingdomList() throws sqlError, noKingdomExists {

        try {

            PreparedStatement retrieveList = sql.prepareStatement(
                    "SELECT KingdomData.Kingdom FROM KingdomData"
            );
            ResultSet KingdomList = retrieveList.executeQuery();
            List<String> resultList = new ArrayList<>();

            if (KingdomList.next()) {

                do {
                    resultList.add(KingdomList.getString("Kingdom"));
                } while (KingdomList.next());

            } else {

                throw new noKingdomExists();

            }

            return resultList;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new sqlError();
        }
    }

    public List<String> getKingdomDetails(String kingdomName) throws kingdomNameNotExists, sqlError {

        String Kingdom = null, OwnerName = null, MayorName = null;

        try {

            if (checkKingdomExistence(kingdomName)) {

                PreparedStatement retrieveKingdomDetail = sql.prepareStatement(
                        "SELECT * FROM KingdomData WHERE KingdomData.Kingdom = ?"
                );
                retrieveKingdomDetail.setString(1, kingdomName);
                ResultSet KingdomDetail = retrieveKingdomDetail.executeQuery();
                List<String> detailReturn = new ArrayList<>();

                // Assigning all variables

                if (KingdomDetail.next()) {

                    Kingdom = KingdomDetail.getString("Kingdom");

                    String OwnerString = KingdomDetail.getString("Owner");
                    if (OwnerString != null) {
                        OwnerName = getPlayerName(OwnerString);
                    } else {
                        OwnerName = "null";
                    }

                    String MayorString = KingdomDetail.getString("Mayor");
                    if (MayorString != null) {
                        MayorName = getPlayerName(MayorString);
                    } else {
                        MayorName = "null";
                    }

                }

                // Adding them to list
                detailReturn.add(Kingdom);
                detailReturn.add(OwnerName);
                detailReturn.add(MayorName);

                return detailReturn;

            } else {
                throw new kingdomNameNotExists();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new sqlError();
        }

    }

    // Properties

    //Members
    public void addMember(Player sender, String playerName) throws sqlError, playerNoPermission, playerNameNotExists, playerHasKingdom {

        try {

            if (checkPlayerPos(sender.getUniqueId())) {

                UUID playerUUID = Bukkit.getPlayerUniqueId(playerName);
                if (playerUUID == null) throw new playerNameNotExists();
                if (checkPlayerExistence(playerUUID)) {

                    if (!(playerHasKingdom(playerUUID))) {

                        PreparedStatement addPlayer = sql.prepareStatement(
                                "UPDATE PlayerData SET PlayerData.Kingdom = ?, PlayerData.Pos = 'Member' WHERE PlayerData.PlayerUUID = ?"
                        );
                        addPlayer.setString(1, getPlayerKingdom(sender.getUniqueId()));
                        addPlayer.setString(2, playerUUID.toString());
                        addPlayer.executeUpdate();

                    } else {
                        throw new playerHasKingdom();
                    }

                } else {
                    throw new playerNameNotExists();
                }

            } else {
                throw new playerNoPermission();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new sqlError();
        }

    }

    public void removeMember(Player sender, String playerName) throws playerNameNotExists, playerNotMember, playerNoPermission, sqlError {

        try {

            if (checkPlayerPos(sender.getUniqueId())) {

                UUID playerUUID = Bukkit.getPlayerUniqueId(playerName);
                if (playerUUID == null) throw new playerNameNotExists();
                if (checkPlayerExistence(playerUUID)) {

                    if (getPlayerKingdom(sender.getUniqueId())   .equals   (getPlayerKingdom(playerUUID))){

                        PreparedStatement addPlayer = sql.prepareStatement(
                                "UPDATE PlayerData SET PlayerData.Kingdom = null, PlayerData.Pos = null WHERE PlayerData.PlayerUUID = ?"
                        );
                        addPlayer.setString(1, playerUUID.toString());
                        addPlayer.executeUpdate();

                    } else {
                        throw new playerNotMember();
                    }

                } else {
                    throw new playerNameNotExists();
                }

            } else {
                throw new playerNoPermission();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new sqlError();
        }

    }

    public List<String> listMember(String kingdomName) throws noMemberExists, sqlError, kingdomNameNotExists {

        try {

            if (checkKingdomExistence(kingdomName)) {

                PreparedStatement retrieveList = sql.prepareStatement(
                        "SELECT PlayerData.PlayerName FROM PlayerData WHERE PlayerData.Kingdom = ?"
                );
                retrieveList.setString(1, kingdomName);
                ResultSet memberList = retrieveList.executeQuery();
                List<String> resultList = new ArrayList<>();

                if (memberList.next()) {

                    do {
                        resultList.add(memberList.getString("PlayerName"));
                    } while (memberList.next());

                } else {

                    throw new noMemberExists();

                }

                return resultList;

            } else {
                throw new kingdomNameNotExists();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new sqlError();
        }

    }

    public String getDiscordKingdom(String channelID) throws sqlError {

        try {

            PreparedStatement getName = sql.prepareStatement(
                    "SELECT KingdomData.Kingdom FROM KingdomData WHERE KingdomData.DiscordChannelID = ?"
            );
            getName.setString(1, channelID);
            ResultSet getNameResult = getName.executeQuery();
            if (getNameResult.next()) {
                return getNameResult.getString("Kingdom");
            }
            return null;

        } catch (SQLException e){
            e.printStackTrace();
            throw new sqlError();
        }

    }

    public void setDiscord(String kingdomName,  String roleID, String channelID) throws kingdomNameNotExists, sqlError {

        try {

            if (checkKingdomExistence(kingdomName)) {

                PreparedStatement setDiscord = sql.prepareStatement(
                        "UPDATE KingdomData SET KingdomData.DiscordChannelID = ?, KingdomData.DiscordRoleID = ? WHERE KingdomData.Kingdom = ?"
                );
                setDiscord.setString(1, channelID);
                setDiscord.setString(2, roleID);
                setDiscord.setString(3, kingdomName);
                setDiscord.executeUpdate();

            } else {
                throw new kingdomNameNotExists();
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new sqlError();
        }

    }

    // Convenience
    public boolean checkKingdomExistence(String kingdomName) throws SQLException {

        PreparedStatement checkExistence = sql.prepareStatement(
                "SELECT KingdomData.Kingdom FROM KingdomData WHERE KingdomData.Kingdom = ?"
        );
        checkExistence.setString(1, kingdomName);
        return checkExistence.executeQuery().next();

    }

    public boolean checkPlayerExistence(UUID playerID) throws SQLException {

        PreparedStatement checkExistence = sql.prepareStatement(
                "SELECT PlayerData.PlayerUUID FROM PlayerData WHERE PlayerData.PlayerUUID = ?"
        );
        checkExistence.setString(1, playerID.toString());
        return checkExistence.executeQuery().next();

    }

    public String getPlayerName(UUID playerID) throws SQLException {

        PreparedStatement getName = sql.prepareStatement(
                "SELECT PlayerData.PlayerName FROM PlayerData WHERE PlayerData.PlayerUUID = ?"
        );
        getName.setString(1, playerID.toString());
        ResultSet player = getName.executeQuery();

        if (player.next()){
            return player.getString("PlayerName");
        }

        return null;

    }

    public String getPlayerName(String playerID) throws SQLException {

        PreparedStatement getName = sql.prepareStatement(
                "SELECT PlayerData.PlayerName FROM PlayerData WHERE PlayerData.PlayerUUID = ?"
        );
        getName.setString(1, playerID);
        ResultSet player = getName.executeQuery();

        if (player.next()){
            return player.getString("PlayerName");
        }

        return null;

    }

    public boolean playerHasKingdom(UUID playerID) throws SQLException {

        String playerKingdom = getPlayerKingdom(playerID);
        return playerKingdom != null;

    }

    public String getPlayerKingdom(UUID playerID) throws SQLException {

        String playerKingdom = null;

        PreparedStatement getKingdom = sql.prepareStatement(
                "SELECT PlayerData.Kingdom FROM PlayerData WHERE PlayerData.PlayerUUID = ?"
        );
        getKingdom.setString(1, playerID.toString());
        ResultSet player = getKingdom.executeQuery();

        if (player.next()){
            playerKingdom = player.getString("Kingdom");
        }

        return playerKingdom;

    }

    public boolean checkPlayerPos(UUID playerID) throws SQLException {

        String playerPos = null;

        PreparedStatement checkPos = sql.prepareStatement(
                "SELECT PlayerData.Pos FROM PlayerData WHERE PlayerData.PlayerUUID = ?"
        );
        checkPos.setString(1, playerID.toString());
        ResultSet player = checkPos.executeQuery();

        if (player.next()){
            playerPos = player.getString("Pos");
        }
        if (playerPos == null) return false;

        return playerPos.equals("Owner") || playerPos.equals("Mayor");

    }

    public String getKingdomRoleID(String kingdomName) throws sqlError, kingdomNameNotExists {

        try{

            if (checkKingdomExistence(kingdomName)) {

                PreparedStatement KingdomRoleIDQuery = sql.prepareStatement
                                ("SELECT KingdomData.DiscordRoleID " +
                                        "FROM KingdomData " +
                                        "WHERE KingdomData.Kingdom = ?");
                KingdomRoleIDQuery.setString(1, kingdomName);
                ResultSet KingdomRoleIDResult = KingdomRoleIDQuery.executeQuery();


                if (KingdomRoleIDResult.next()){
                    return KingdomRoleIDResult.getString("DiscordRoleID");
                }

            } else {
                throw new kingdomNameNotExists();
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new sqlError();
        }
        return null;
    }

    public boolean linkedDiscord(String kingdomName) throws kingdomNameNotExists, sqlError {

        try {

            if (checkKingdomExistence(kingdomName)){

                PreparedStatement checkDiscord = sql.prepareStatement(
                        "SELECT KingdomData.DiscordChannelID FROM KingdomData WHERE KingdomData.Kingdom = ?"
                );
                checkDiscord.setString(1, kingdomName);
                ResultSet checkDiscordResult = checkDiscord.executeQuery();
                if (checkDiscordResult.next()){
                    return checkDiscordResult.getString("DiscordChannelID") != null;
                }

            } else {
                throw new kingdomNameNotExists();
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new sqlError();
        }

        return false;
    }

}