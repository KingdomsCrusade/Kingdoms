package net.kingdomscrusade.kingdoms.api.actions.roles

import net.kingdomscrusade.kingdoms.api.actions.Commons
import net.kingdomscrusade.kingdoms.api.actions.IAction
import java.sql.Statement

class ChangeRoleName
    (
    private val oldRoleName: String,
    private val newRoleName: String,
    private val kingdomName: String
    ): IAction, Commons()
{
    override fun execute(statement: Statement): String {
        if (checkRoleDuplicate(newRoleName, kingdomName, statement))
            throw IllegalArgumentException("Duplicated name found under the same kingdom.")
        val roleUUID = getRoleUUID(oldRoleName, kingdomName, statement).get()
        statement.executeUpdate(
            """
                UPDATE Roles SET role_name = '$newRoleName'
                WHERE role_uuid = '$roleUUID';
            """.trimIndent()
        )
        return roleUUID.toString()
    }
}