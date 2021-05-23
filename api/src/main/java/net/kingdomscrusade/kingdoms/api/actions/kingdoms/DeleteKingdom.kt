package net.kingdomscrusade.kingdoms.api.actions.kingdoms

import net.kingdomscrusade.kingdoms.api.actions.Commons
import net.kingdomscrusade.kingdoms.api.actions.IAction
import java.sql.Statement

class DeleteKingdom (private val kingdomName: String) : IAction, Commons() {
    override fun execute(statement: Statement): String {
        val kingdomUUID = getKingdomUUID(kingdomName, statement).get()
        statement.executeUpdate("DELETE FROM Kingdoms WHERE kingdom_uuid = '$kingdomUUID';")
        return kingdomUUID.toString()
    }
}