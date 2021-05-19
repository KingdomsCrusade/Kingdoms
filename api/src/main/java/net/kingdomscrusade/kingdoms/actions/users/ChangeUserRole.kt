package net.kingdomscrusade.kingdoms.actions.users

import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.actions.IAction
import java.sql.Statement
import java.util.*

class ChangeUserRole
    (
    private val userUUID: UUID,
    private val userKingdom: String,
    private val userRole: String
    ): IAction, Commons()
{
    override fun execute(statement: Statement): String {
        statement.executeUpdate(
            """
                UPDATE Users
                SET user_role = '${getRoleUUID(userRole, userKingdom, statement).get()}'
                WHERE (
                    user_uuid = '$userUUID' AND
                    user_kingdom = '${getKingdomUUID(userKingdom, statement).get()}'
                );
            """.trimIndent()
        )
        return userUUID.toString()
    }
}