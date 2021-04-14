package net.kingdomscrusade.kingdoms.actions

import net.kingdomscrusade.kingdoms.KingdomsMain
import net.kingdomscrusade.kingdoms.mongo.pojo.Kingdoms.Kingdoms
import net.kingdomscrusade.kingdoms.mongo.pojo.Kingdoms.KingdomsCreation
import net.kingdomscrusade.kingdoms.mongo.pojo.Kingdoms.KingdomsRoles
import net.kingdomscrusade.kingdoms.mongo.pojo.Players.Players
import net.kingdomscrusade.kingdoms.mongo.pojo.Players.PlayersKingdom
import net.kingdomscrusade.kingdoms.objects.enums.RoleFlags
import org.bson.types.ObjectId
import org.bukkit.entity.Player
import java.util.*

class CreateKingdom {

    companion object{
        fun accept(sender: Player, name: String, player: Player):Boolean{
            val kingdomsCollection = KingdomsMain.getKingdomsCollection()
            val playersCollection = KingdomsMain.getPlayersCollection()
            val kingdomPOJO = kingdomPOJO(sender, name)

            kingdomsCollection.insert(kingdomPOJO)
            playersCollection.insert(
                Players(
                    player.uniqueId,
                    player.name,
                    PlayersKingdom(
                        name,
                        kingdomPOJO
                            .getRole("Owner")
                            .getRoleId(),
                        Date()
                    )
                )
            )
            return true
        }

        private fun kingdomPOJO(sender: Player, name: String): Kingdoms {

            val now = Date()

            /* Creating POJO */
            // Roles
            val ownerRole = KingdomsRoles(
                ObjectId(),
                "Owner",
                "Console",
                false
            )
            ownerRole.setFlags(arrayListOf(
                RoleFlags.ADMINISTRATOR
            ))

            val memberRole = KingdomsRoles(
                ObjectId(),
                "Member",
                "Console",
                false
            )
            memberRole.setFlags(arrayListOf(
                RoleFlags.MENTION,
                RoleFlags.USE_CHATROOM
            ))

//            // Member
//            val member = KingdomsMembers(player.uniqueId, player.name, now)
//            member.setRole(arrayListOf(
//                ownerRole.getRoleId()
//            ))

            // Return
            return Kingdoms(
                name,
                KingdomsCreation(now, sender.name),
                arrayListOf(
                    ownerRole,
                    memberRole
                )
            )
        }
    }

}