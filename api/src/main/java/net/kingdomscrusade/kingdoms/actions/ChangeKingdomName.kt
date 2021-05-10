package net.kingdomscrusade.kingdoms.actions

import java.sql.Statement
import java.util.*

class ChangeKingdomName(
    private val kingdomUUID: UUID,
    private val newKingdomName: String
    ): IAction
{
    override fun execute(statement: Statement): String {
        statement.executeUpdate(
            """
            UPDATE Kingdoms SET kingdom_name = '$newKingdomName' 
                WHERE kingdom_uuid = '$kingdomUUID';
            """.trimIndent())
        return kingdomUUID.toString()
    }
}