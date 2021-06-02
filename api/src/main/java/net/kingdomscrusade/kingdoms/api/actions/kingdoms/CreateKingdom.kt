package net.kingdomscrusade.kingdoms.api.actions.kingdoms

import net.kingdomscrusade.kingdoms.api.KingdomsAPI
import net.kingdomscrusade.kingdoms.api.actions.IAction
import java.sql.Statement
import java.util.*

class CreateKingdom
    (
    private val kingdomUUID: UUID = UUID.randomUUID(),
    private val kingdomName: String,
    private val userUUID: UUID,
    private val userName: String
    ) : IAction
{
    override fun execute(statement: Statement): String {
        statement.executeUpdate( "INSERT INTO Kingdoms VALUE ('$kingdomUUID', '$kingdomName');" )
        statement.executeUpdate( "INSERT INTO Users    VALUE ('$userUUID', '$userName', '$kingdomUUID', '${KingdomsAPI.owner}');" )
        return kingdomUUID.toString()
    }
}