package net.kingdomscrusade.Kingdoms.modules.discord;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kingdomscrusade.Kingdoms.KingdomsCore;
import net.kingdomscrusade.Kingdoms.KingdomsMain;
import net.kingdomscrusade.Kingdoms.exceptions.kingdomNameNotExists;
import net.kingdomscrusade.Kingdoms.exceptions.sqlError;
import net.kingdomscrusade.Kingdoms.modules.yamlManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DiscordListener extends ListenerAdapter {

    Connection sql = KingdomsMain.getDatabaseConnection();
    DiscordActions actions = new DiscordActions();
    KingdomsCore core = new KingdomsCore();
    yamlManager yaml = new yamlManager();


    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        // Returns if the message is not from a server, for example Direct Messages.
        if (!event.isFromGuild())
            return;

        String prefix = yaml.prefix();
        String message = event.getMessage().getContentDisplay();
        // If its a command, execute them
        if (message.startsWith(prefix)){

            message = message.replaceFirst(prefix, "");
            String[] args = message.split("\\s+");
            Member member = event.getMember();
            if (member == null){
                event.getChannel().sendMessage(
                        "An error has occurred: memberIsNull"
                ).queue();
                return;
            }
            switch (args[0]){

                case "link":
                    if (!member.hasPermission(Permission.ADMINISTRATOR)) {
                        event.getChannel().sendMessage(
                                "You have no permission sadly :)"
                        ).queue();
                        return;
                    }
                    if (args.length != 2){
                        event.getChannel().sendMessage(
                                prefix + "link [kingdomName]"
                        ).queue();
                        return;
                    }
                    try {
                        if (!core.checkKingdomExistence(args[1])){
                            event.getChannel().sendMessage(
                                    "Ya know what? This kingdom doesnt even exist LMFAO"
                            ).queue();
                            return;
                        }
                        if (core.linkedDiscord(args[1])) {
                            event.getChannel().sendMessage(
                                    "This kingdom has a channel LINKED, check again?"
                            ).queue();
                            return;
                        }
                        String[] discordInfo = actions.createChannels(event.getGuild(), args[1]);
                        core.setDiscord(
                                args[1],
                                discordInfo[0],
                                discordInfo[1]
                        );
                        event.getChannel().sendMessage(
                                "Channels created and linked!"
                        ).queue();
                        return;
                    } catch (sqlError e){
                        event.getChannel().sendMessage(
                                "Database error, huh? Call Raza or Lil to check"
                        ).queue();
                        return;
                    } catch (kingdomNameNotExists e){
                        event.getChannel().sendMessage(
                                "Ya know what? This kingdom doesnt even exist LMFAO"
                        ).queue();
                        return;
                    } catch (SQLException e){
                        e.printStackTrace();
                        event.getChannel().sendMessage(
                                "Database error, huh? Call Raza or Lil to check"
                        ).queue();
                        return;
                    }

                case "unlink":
                    if (!member.hasPermission(Permission.ADMINISTRATOR)) {
                        event.getChannel().sendMessage(
                                "You have no permission sadly :)"
                        ).queue();
                        return;
                    }
                    if (args.length != 2){
                        event.getChannel().sendMessage(
                                prefix + "unlink [kingdomName]"
                        ).queue();
                        return;
                    }
                    try {
                        if (!core.checkKingdomExistence(args[1])){
                            event.getChannel().sendMessage(
                                    "Ya know what? This kingdom doesnt even exist LMFAO"
                            ).queue();
                            return;
                        }
                        if (!core.linkedDiscord(args[1])) {
                            event.getChannel().sendMessage(
                                    "This kingdom doesnt even has a channel linked bruh -_-"
                            ).queue();
                            return;
                        }
                        actions.removeChannels(event.getGuild(), args[1]);
                        core.setDiscord(
                                args[1],
                                null,
                                null
                        );
                        event.getChannel().sendMessage(
                                "Channels deleted and unlinked."
                        ).queue();
                        return;
                    } catch (sqlError e){
                        event.getChannel().sendMessage(
                                "Database error, huh? Call Raza or Lil to check"
                        ).queue();
                        return;
                    } catch (kingdomNameNotExists e){
                        event.getChannel().sendMessage(
                                "Ya know what? This kingdom doesnt even exist LMFAO"
                        ).queue();
                        return;
                    } catch (SQLException e){
                        e.printStackTrace();
                        event.getChannel().sendMessage(
                                "Database error, huh? Call Raza or Lil to check"
                        ).queue();
                        return;
                    }

                case "newmember":
                case "addmember":
                    // If the sender doesnt have "Kingdom Owner" role, break
                    if (!event.getMember().getRoles().contains(event.getGuild().getRoleById(yaml.ownerRoleID()))){
                        event.getChannel().sendMessage(
                                "You have no permission sadly :)"
                        ).queue();
                        return;
                    }
                    try {
                        String categoryName = Objects.requireNonNull(event.getMessage().getCategory()).getName();
                        if (!core.checkKingdomExistence(categoryName)) {
                            event.getChannel().sendMessage(
                                    "Wrong channel babyyyyyyy"
                            ).queue();
                            return;
                        }
                        String targetID = args[1]
                                .replace("<@!", "")
                                .replace(">", "");
                        event.getGuild().addRoleToMember(
                                targetID,
                                Objects.requireNonNull(event.getGuild().getRoleById(
                                        core.getKingdomRoleID(categoryName)
                                ))
                        ).complete();
                        return;
                    } catch (SQLException e){
                        e.printStackTrace();
                        event.getChannel().sendMessage(
                                "Database error, huh? Call Raza or Lil to check"
                        ).queue();
                        return;
                    } catch (sqlError e){
                        event.getChannel().sendMessage(
                                "Database error, huh? Call Raza or Lil to check"
                        ).queue();
                        return;
                    } catch (kingdomNameNotExists e){
                        event.getChannel().sendMessage(
                                "Looks like the script went wrong... Call Lil to check"
                        ).queue();
                        return;
                    }

                case "deletemember":
                case "removemember":
                    // If the sender doesnt have "Kingdom Owner" role, break
                    if (!event.getMember().getRoles().contains(event.getGuild().getRoleById(yaml.ownerRoleID()))){
                        event.getChannel().sendMessage(
                                "You have no permission sadly :)"
                        ).queue();
                        return;
                    }
                    try {
                        String categoryName = Objects.requireNonNull(event.getMessage().getCategory()).getName();
                        if (!core.checkKingdomExistence(categoryName)) {
                            event.getChannel().sendMessage(
                                    "Wrong channel babyyyyyyy"
                            ).queue();
                            return;
                        }
                        String targetID = args[1]
                                .replace("<@!", "")
                                .replace(">", "");
                        Role role = Objects.requireNonNull(event.getGuild().getRoleById(
                                core.getKingdomRoleID(categoryName)
                        ));
                        if (!Objects.requireNonNull(event.getGuild().getMemberById(targetID))
                                .getRoles()
                                .contains(role)){

                            event.getChannel().sendMessage(
                                    "This human doesnt even have your kingdom role bruh"
                            ).queue();
                            return;

                        }
                        event.getGuild().removeRoleFromMember(
                                targetID,
                                role
                        ).complete();
                        return;
                    } catch (SQLException e){
                        e.printStackTrace();
                        event.getChannel().sendMessage(
                                "Database error, huh? Call Raza or Lil to check"
                        ).queue();
                        return;
                    } catch (sqlError e){
                        event.getChannel().sendMessage(
                                "Database error, huh? Call Raza or Lil to check"
                        ).queue();
                        return;
                    } catch (kingdomNameNotExists e){
                        event.getChannel().sendMessage(
                                "Looks like the script went wrong... Call Lil to check"
                        ).queue();
                        return;
                    }

                case "setowner":
                    if (!member.hasPermission(Permission.ADMINISTRATOR)) {
                        event.getChannel().sendMessage(
                                "You have no permission sadly :)"
                        ).queue();
                        return;
                    }
                    String roleID = args[1]
                            .replace("<@&", "")
                            .replace(">", "");
                    yaml.setPrefix(roleID);
                    event.getChannel().sendMessage(
                            "Prefix has been successfully set to " + args[1] + "."
                    ).queue();
                    return;

                case "prefix":
                    if (!member.hasPermission(Permission.ADMINISTRATOR)) {
                        event.getChannel().sendMessage(
                                "You have no permission sadly :)"
                        ).queue();
                        return;
                    }
                    yaml.setPrefix(args[1]);
                    event.getChannel().sendMessage(
                            "Prefix has been successfully set to `" + args[1] + "`."
                    ).queue();
                    return;

            }

        }

        try {

            // Sends message to Game
            PreparedStatement KingdomChannelQuery = sql.prepareStatement(
                    "SELECT Kingdom FROM KingdomData WHERE DiscordChannelID = ?"
            );
            KingdomChannelQuery.setString(1, event.getChannel().getId());
            ResultSet KingdomChannelResult = KingdomChannelQuery.executeQuery();

            if (KingdomChannelResult.next()) {

                String receiverKingdom = KingdomChannelResult.getString("Kingdom");
                String sender = Objects.requireNonNull(event.getMember()).getNickname();
                if (sender == null) sender = "Unknown";
                actions.sendChatFromDiscord(receiverKingdom, sender, message);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}
