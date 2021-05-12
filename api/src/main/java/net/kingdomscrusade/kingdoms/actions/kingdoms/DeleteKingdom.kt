package net.kingdomscrusade.kingdoms.actions.kingdoms

import net.kingdomscrusade.kingdoms.actions.IAction
import java.sql.Statement
import java.util.*

class DeleteKingdom (private val kingdomUUID: UUID) : IAction {
    override fun execute(statement: Statement): String {
        statement.executeUpdate("DELETE FROM Kingdoms WHERE kingdom_uuid = '$kingdomUUID';")
        return kingdomUUID.toString()
    }
}