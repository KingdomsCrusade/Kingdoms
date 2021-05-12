package net.kingdomscrusade.kingdoms.actions.roles

import net.kingdomscrusade.kingdoms.actions.Commons
import net.kingdomscrusade.kingdoms.actions.IAction
import java.security.Permissions
import java.sql.Statement

class AddRolePermission
    (
    private val roleName: String,
    private val roleKingdom: String,
    private val permission: Permissions
    ): IAction, Commons()
{

    override fun execute(statement: Statement): String {
        val roleUUID = getRoleUUID(roleName, roleKingdom, statement).get()
        val permissionList = getPermissions(roleUUID, statement)
        val newPermissionList =
            if (permissionList.isPresent)
                "${permissionList.get()},$permission"
            else
                permission.toString()
        statement.executeUpdate(
            """
                UPDATE Roles SET role_permissions = '$newPermissionList'
                WHERE role_uuid = '$roleUUID';
            """.trimIndent()
        )
        return roleUUID.toString()
    }

}