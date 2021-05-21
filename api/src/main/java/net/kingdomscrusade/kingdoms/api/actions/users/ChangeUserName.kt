package net.kingdomscrusade.kingdoms.api.actions.users

import net.kingdomscrusade.kingdoms.api.actions.IAction
import java.sql.Statement
import java.util.*

class ChangeUserName
    (
    private val userUUID: UUID,
    private val newName: String
    ): IAction
{
    override fun execute(statement: Statement): String {
        statement.executeUpdate(
            """
                UPDATE Users
                SET user_name = '$newName'
                WHERE user_uuid = '$userUUID';
            """.trimIndent()
        )
        return userUUID.toString()
    }
}