package net.kingdomscrusade.Kingdoms.modules.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.kingdomscrusade.Kingdoms.KingdomsCore;
import net.kingdomscrusade.Kingdoms.KingdomsMain;
import net.kingdomscrusade.Kingdoms.error.ChannelNotFound;
import net.kingdomscrusade.Kingdoms.error.SenderNoKingdom;
import net.kingdomscrusade.Kingdoms.error.SqlError;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;

public class DiscordActions {

    Connection sql = KingdomsMain.getDatabaseConnection();
    JDA jda = KingdomsMain.getDiscordConnection();
    KingdomsCore core = new KingdomsCore();

    public void sendChatFromGame(Player sender, String message) throws SqlError, SenderNoKingdom, ChannelNotFound {

        try {

            UUID senderID = sender.getUniqueId();
            String senderKingdom = core.getPlayerKingdom(senderID);
            UUID receiverID;
            String channelID = null;

            if (core.playerHasKingdom(senderID)) {

                // Retrieving a list of message receivers
                PreparedStatement receiverList = sql.prepareStatement(
                        "SELECT PlayerData.PlayerUUID FROM PlayerData WHERE PlayerData.Kingdom = ?"
                );
                receiverList.setString(1, senderKingdom);
                ResultSet receivers = receiverList.executeQuery();

                // Retrieving the channel ID of the kingdom
                PreparedStatement receiveChannelToSend = sql.prepareStatement(
                        "SELECT KingdomData.DiscordChannelID FROM KingdomData WHERE KingdomData.Kingdom = ?"
                );
                receiveChannelToSend.setString(1, senderKingdom);
                ResultSet channelToSend = receiveChannelToSend.executeQuery();
                if (channelToSend.next()){
                    channelID = channelToSend.getString("DiscordChannel");
                }

                // Sending messages
                if (receivers.next()) {

                    // Sending to receivers, skip if user object is null (means not online)
                    do {
                        receiverID = UUID.fromString(receivers.getString("PlayerUUID"));
                        Player receiver = Bukkit.getPlayer(receiverID);
                        if (receiver == null) continue;

                        receiver.sendMessage(core.getPlayerName(senderID) + " >> " + ChatColor.GRAY + message);
                    } while (receivers.next());

                    // Sending to Discord if Discord Channel is set
                    if (channelID != null){
                        TextChannel channel = jda.getTextChannelById(channelID);
                        if (channel != null){
                            channel.sendMessage(core.getPlayerName(senderID) + " >> " + message).queue();
                        } else {
                            throw new ChannelNotFound();
                        }
                    }


                }

            } else {
                throw new SenderNoKingdom();
            }

        } catch (SQLException e){
            e.printStackTrace();
            throw new SqlError();
        }

    }

    public void sendChatFromDiscord(String kingdom, String senderName, String message) throws SQLException {

        UUID receiverID;

        PreparedStatement receiverList = sql.prepareStatement(
                "SELECT PlayerData.PlayerUUID FROM PlayerData WHERE PlayerData.Kingdom = ?"
        );
        receiverList.setString(1, kingdom);
        ResultSet receivers = receiverList.executeQuery();

        // Sending messages
        if (receivers.next()) {

            // Sending to receivers, skip if user object is null (means not online)
            do {
                receiverID = UUID.fromString(receivers.getString("PlayerUUID"));
                Player receiver = Bukkit.getPlayer(receiverID);
                if (receiver == null) continue;

                receiver.sendMessage(
                        ChatColor.WHITE + "[" + ChatColor.YELLOW + kingdom + ChatColor.WHITE + "]" +
                                ChatColor.WHITE + " " + senderName + " >> " +
                                ChatColor.GRAY + message);
            } while (receivers.next());

        }

    }

    public String[] createChannels(Guild guild, String name){

        Role Role = guild.createRole()
                .setName(name)
                .setPermissions(
                        Permission.VIEW_CHANNEL,
                        Permission.CREATE_INSTANT_INVITE,
                        Permission.NICKNAME_CHANGE,
                        Permission.MESSAGE_WRITE,
                        Permission.MESSAGE_EMBED_LINKS,
                        Permission.MESSAGE_ATTACH_FILES,
                        Permission.MESSAGE_ADD_REACTION,
                        Permission.MESSAGE_EXT_EMOJI,
                        Permission.MESSAGE_HISTORY,
                        Permission.VOICE_CONNECT,
                        Permission.VOICE_SPEAK,
                        Permission.VOICE_STREAM
                )
                .setHoisted(false)
                .setMentionable(false)
                .complete();

        String roleID = Role.getId();

        Category category = guild.createCategory(name)
                .setPosition(-1)
                .addPermissionOverride(
                        guild.getPublicRole(),
                        null,
                        EnumSet.of(
                                Permission.VIEW_CHANNEL
                        )
                )
                .addPermissionOverride(
                        Role,
                        EnumSet.of(
                                Permission.VIEW_CHANNEL
                        ),
                        null
                )
                .complete();

        category.createTextChannel("「" + name + "-announcements」")
                .addPermissionOverride(
                        Objects.requireNonNull(guild.getRoleById("814638310626558012")),
                        EnumSet.of(
                                Permission.MESSAGE_WRITE,
                                Permission.MESSAGE_MENTION_EVERYONE
                        ),
                        null
                )
                .addPermissionOverride(
                        Role,
                        null,
                        EnumSet.of(
                                Permission.MESSAGE_WRITE
                        )
                )
                .complete();
        String channelID = category.createTextChannel("「" + name + "-chat」")
                .complete()
                .getId();
        category.createVoiceChannel("「" + name + " VC」")
                .setBitrate(64)
                .complete();

        return new String[]{
                roleID,
                channelID
        };

    }

    public void removeChannels(Guild guild, String name){

        for (Category category:guild.getCategoriesByName(name, false)){
            for (GuildChannel channel:category.getChannels()){
                channel.delete().complete();
            }
            category.delete().complete();
        }

        for (Role role:guild.getRolesByName(name, false)){
            role.delete().complete();
        }

    }

}
