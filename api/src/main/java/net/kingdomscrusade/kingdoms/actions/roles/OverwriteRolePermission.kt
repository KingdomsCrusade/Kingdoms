package net.kingdomscrusade.kingdoms.actions.roles

import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.actions.IAction
import net.kingdomscrusade.kingdoms.types.Permissions
import java.sql.Statement

class OverwriteRolePermission
    (
    private val roleName: String,
    private val roleKingdom: String,
    private val permissions: Set<Permissions>
    ): IAction, Commons()
{
    override fun execute(statement: Statement): String {
        val roleUUID = getRoleUUID(roleName, roleKingdom, statement).get()
        statement.executeUpdate(
            """
                UPDATE Roles SET role_permissions = '${permissionToString(permissions)}'
                WHERE role_uuid = '$roleUUID';
            """.trimIndent()
        )
        return roleUUID.toString()
    }
}