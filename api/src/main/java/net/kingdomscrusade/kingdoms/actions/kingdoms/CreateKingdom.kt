package net.kingdomscrusade.kingdoms.actions.kingdoms

import net.kingdomscrusade.kingdoms.KingdomsAPI
import net.kingdomscrusade.kingdoms.actions.IAction
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
        statement.executeUpdate( "INSERT INTO Users    VALUE ('$userUUID', '$userName', '$kingdomUUID', '${KingdomsAPI.ownerUUID}');" )
        return kingdomUUID.toString()
    }
}