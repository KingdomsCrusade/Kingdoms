package net.kingdomscrusade.kingdoms.actions.roles

import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.actions.IAction
import java.sql.Statement

class ChangeRoleName
    (
    private val oldRoleName: String,
    private val newRoleName: String,
    private val kingdomName: String
    ): IAction, Commons()
{
    override fun execute(statement: Statement): String {
        if (checkRoleDuplicate(oldRoleName, kingdomName, statement))
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