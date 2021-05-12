package net.kingdomscrusade.kingdoms.actions.users

import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.actions.IAction
import java.sql.Statement
import java.util.*

class DeleteUser
    (
    private val userUUID: UUID,
    private val userKingdom: String
    ): IAction, Commons()
{
    override fun execute(statement: Statement): String {
        statement.executeUpdate(
            """
                DELETE FROM Users WHERE (
                    user_uuid = '$userUUID', 
                    user_kingdom = '${getKingdomUUID(userKingdom, statement).get()}'
                );
            """.trimIndent()
        )
        return userUUID.toString()
    }
}