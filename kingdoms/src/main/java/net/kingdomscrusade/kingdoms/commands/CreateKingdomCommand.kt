package net.kingdomscrusade.kingdoms.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.PlayerArgument
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.CommandExecutor
import net.kingdomscrusade.kingdoms.Main
import net.kingdomscrusade.kingdoms.mongo.pojo.Kingdoms
import net.kingdomscrusade.kingdoms.mongo.pojo.KingdomsCreation
import net.kingdomscrusade.kingdoms.mongo.pojo.KingdomsMembers
import net.kingdomscrusade.kingdoms.mongo.pojo.KingdomsRoles
import net.kingdomscrusade.kingdoms.objects.enums.RoleFlags
import net.kyori.adventure.text.Component
import org.bson.types.ObjectId
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
import kotlin.collections.ArrayList

class CreateKingdomCommand {

    val createKingdom: CommandAPICommand = CommandAPICommand("createkingdom")
        .withAliases("kingdomcreate")
        .withArguments(argument())
        .withPermission("Kingdoms.ManageKingdoms")
        .executes(
            CommandExecutor{commandSender: CommandSender?, args: Array<out Any>? ->
                run {
                    val collection = Main.getKingdomsCollection()
                    val sender = commandSender as Player
                    val senderName = sender.name
                    val name = args!![0] as String
                    val player = args[1] as Player
                    val playerName = player.name


                    /* Adding to Database */
                    collection.insert(kingdomPOJO(sender, name, player))
                    Bukkit.broadcast(
                        Component.text(
                            Main.getMessage().load("CreateKingdom", name, playerName, senderName)
                        ),
                        ""
                    )

                }
            }
        )

    private fun argument(): ArrayList<Argument>{
        val argument = ArrayList<Argument>()
        argument.add(StringArgument("name").setListed(false))
        argument.add(PlayerArgument("player"))
        return argument
    }

    private fun kingdomPOJO(sender:Player,name: String , player: Player): Kingdoms{

        val now = Date()

        /* Creating POJO */
        // Roles
        val ownerFlags = ArrayList<RoleFlags>()
        ownerFlags.add(RoleFlags.ADMINISTRATOR)

        val ownerRole = KingdomsRoles(ObjectId(), "Console")
        ownerRole.setRoleName("Owner")
        ownerRole.setFlags(ArrayList<RoleFlags>())
        ownerRole.setMutable(false)

        val memberFlags = ArrayList<RoleFlags>()
        memberFlags.add(RoleFlags.MENTION)
        memberFlags.add(RoleFlags.USE_CHATROOM)

        val memberRole = KingdomsRoles(ObjectId(), "Console")
        memberRole.setRoleName("Member")
        memberRole.setFlags(memberFlags)
        memberRole.setMutable(false)

        val roles = ArrayList<KingdomsRoles>()
        roles.add(ownerRole)
        roles.add(memberRole)

        // Members
        val memberRoleID = ArrayList<ObjectId>()
        memberRoleID.add(ownerRole.getRoleId())

        val member = KingdomsMembers(player.uniqueId, player.name, now)
        member.setRole(memberRoleID)

        val members = ArrayList<KingdomsMembers>()
        members.add(member)

        // Merging
        val document = Kingdoms(name, KingdomsCreation(now, sender.name))
        document.setRoles(roles)
        document.setMembers(members)

        return document
    }

}