package net.kingdomscrusade.kingdoms.actions.roles

import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.actions.IAction
import java.security.Permissions
import java.sql.Statement

class RemoveRolePermission
    (
    private val roleName: String,
    private val roleKingdom: String,
    private val permission: Permissions
    ): IAction, Commons()
{
    override fun execute(statement: Statement): String {
        val roleUUID = getRoleUUID(roleName, roleKingdom, statement).get()
        val oldPermissionList = getPermissions(roleUUID, statement)
        val newPermissionList =
            if (oldPermissionList.isPresent)
                oldPermissionList.get()
                    .replace(",$permission", "")
                    .replace(permission.toString(), "")
            else
                ""
        statement.executeUpdate(
            """
                UPDATE Roles SET role_permissions = '$newPermissionList'
                WHERE role_uuid = '$roleUUID';
            """.trimIndent()
        )
        return roleUUID.toString()
    }
}