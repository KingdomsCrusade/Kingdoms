package net.kingdomscrusade.kingdoms.actions

import net.kingdomscrusade.kingdoms.Main
import net.kingdomscrusade.kingdoms.mongo.pojo.Kingdoms
import net.kingdomscrusade.kingdoms.mongo.pojo.KingdomsCreation
import net.kingdomscrusade.kingdoms.mongo.pojo.KingdomsMembers
import net.kingdomscrusade.kingdoms.mongo.pojo.KingdomsRoles
import net.kingdomscrusade.kingdoms.objects.enums.RoleFlags
import org.bson.types.ObjectId
import org.bukkit.entity.Player
import java.util.*

class CreateKingdom {

    companion object{
        fun accept(sender: Player, name: String, player: Player):Boolean{
            val collection = Main.getKingdomsCollection()

            collection.insert(kingdomPOJO(sender, name, player))
            return true
        }

        private fun kingdomPOJO(sender: Player, name: String, player: Player): Kingdoms {

            val now = Date()

            /* Creating POJO */
            // Roles
            val ownerRole = KingdomsRoles(ObjectId(), "Owner", "Console", false)
            ownerRole.setFlags(arrayListOf(
                RoleFlags.ADMINISTRATOR
            ))

            val memberRole = KingdomsRoles(ObjectId(), "Member", "Console", false)
            memberRole.setFlags(arrayListOf(
                RoleFlags.MENTION,
                RoleFlags.USE_CHATROOM
            ))

            // Member
            val member = KingdomsMembers(player.uniqueId, player.name, now)
            member.setRole(arrayListOf(
                ownerRole.getRoleId()
            ))

            // Return
            return Kingdoms(
                name,
                KingdomsCreation(now, sender.name),
                arrayListOf(
                    ownerRole,
                    memberRole
                ),
                arrayListOf(member)
            )
        }
    }

}