package net.kingdomscrusade.kingdoms.api.table

import net.kingdomscrusade.kingdoms.api.varclass.PermissionType
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object PermissionsTable : Table() {
    val allPermissions : () -> String = {
        val builder = StringBuilder()
        PermissionType.values().forEachIndexed { index, permissions ->
            if (index != 0) builder.append(",")
            builder.append("\"$permissions\"")
        }
        builder.toString()
    }
    val ref = reference("permission_ref", RolesTable.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val value = customEnumeration(
        "permission_val",
        "ENUM(${allPermissions()})",
        { value -> PermissionType.valueOf(value as String) },
        { it.name }
    )
}