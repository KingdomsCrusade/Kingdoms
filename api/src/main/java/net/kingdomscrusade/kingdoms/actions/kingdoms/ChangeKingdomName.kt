package net.kingdomscrusade.kingdoms.actions.kingdoms

import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.actions.IAction
import java.sql.Statement

class ChangeKingdomName(
    private val oldKingdomName: String,
    private val newKingdomName: String
    ): IAction, Commons()
{
    override fun execute(statement: Statement): String {
        val kingdomUUID = getKingdomUUID(oldKingdomName, statement).get()
        statement.executeUpdate(
            """
            UPDATE Kingdoms SET kingdom_name = '$newKingdomName' 
            WHERE kingdom_uuid = '$kingdomUUID';
            """.trimIndent())
        return kingdomUUID.toString()
    }
}