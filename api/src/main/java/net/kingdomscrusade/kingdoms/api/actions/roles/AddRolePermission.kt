package net.kingdomscrusade.kingdoms.api.actions.roles

import net.kingdomscrusade.kingdoms.api.actions.Commons
import net.kingdomscrusade.kingdoms.api.actions.IAction
import net.kingdomscrusade.kingdoms.api.types.Permissions
import java.sql.Statement

class AddRolePermission
    (
    private val roleName: String,
    private val roleKingdom: String,
    private val permission: Permissions
    ): IAction, Commons()
{

    override fun execute(statement: Statement): String {
        val addPermissionToString: (String, Permissions) -> String = {s, p ->
            val list = stringToPermissions(s).toMutableSet()
            list += p
            permissionToString(list)
        }
        val roleUUID = getRoleUUID(roleName, roleKingdom, statement).get()
        val oldPermissionList = getPermissions(roleUUID, statement)
        val newPermissionList =
            if (oldPermissionList.isPresent)
                addPermissionToString(oldPermissionList.get(), permission)
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