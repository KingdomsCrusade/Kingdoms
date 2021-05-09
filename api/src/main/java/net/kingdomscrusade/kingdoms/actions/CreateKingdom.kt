package net.kingdomscrusade.kingdoms.actions

import net.kingdomscrusade.kingdoms.KingdomsAPI
import java.sql.Connection
import java.util.*

class CreateKingdom
    (
    private val kingdomName: String,
    private val userUUID: UUID,
    private val userName: String
    ) :IAction
{
    private val kingdomUUID: UUID = UUID.randomUUID()
    override fun execute(database: Connection): String {
        database.createStatement().executeUpdate(
            """
                INSERT INTO Kingdoms VALUE ($kingdomUUID, $kingdomName);
                INSERT INTO Users VALUE ($userUUID, $userName, $kingdomUUID, ${KingdomsAPI.ownerUUID})
            """.trimIndent()
        )
        return kingdomUUID.toString()
    }
}