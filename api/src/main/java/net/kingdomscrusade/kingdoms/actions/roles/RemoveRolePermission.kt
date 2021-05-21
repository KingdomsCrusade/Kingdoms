package net.kingdomscrusade.kingdoms.actions.roles

import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.actions.IAction
import net.kingdomscrusade.kingdoms.types.Permissions
import java.sql.Statement

class RemoveRolePermission
    (
    private val roleName: String,
    private val roleKingdom: String,
    private val permission: Permissions
    ): IAction, Commons()
{
    override fun execute(statement: Statement): String {
        val removePermissionFromString: (String, Permissions) -> String = {s, p ->
            val list = stringToPermissions(s).toMutableSet()
            list -= p
            permissionToString(list)
        }
        val roleUUID = getRoleUUID(roleName, roleKingdom, statement).get()
        val oldPermissionList = getPermissions(roleUUID, statement)
        val newPermissionList = removePermissionFromString(oldPermissionList.get(), permission)
        statement.executeUpdate(
            """
                UPDATE Roles SET role_permissions = '$newPermissionList'
                WHERE role_uuid = '$roleUUID';
            """.trimIndent()
        )
        return roleUUID.toString()
    }
}