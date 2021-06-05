@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package net.kingdomscrusade.kingdoms.api.repository

import net.kingdomscrusade.kingdoms.api.model.PermissionType
import org.jetbrains.exposed.sql.CustomFunction
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import java.util.*

object Roles : Table() {
    val id = uuid("role_id").clientDefault { UUID.randomUUID() }
    val name = varchar("role_name", 255)
    val kingdom = reference(
        "role_kingdom",
        Kingdoms.id,
        ReferenceOption.CASCADE,
        ReferenceOption.CASCADE
    ).nullable()

    override val primaryKey = PrimaryKey(id)
}

object Permissions : Table() {
    val allPermissions : () -> String = {
        val builder = StringBuilder()
        PermissionType.values().forEachIndexed { index, permissions ->
            if (index != 0) builder.append(",")
            builder.append("\"$permissions\"")
        }
        builder.toString()
    }
    val ref = reference("permission_ref", Roles.id, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val value = customEnumeration(
        "permission_val",
        "ENUM(${allPermissions()})",
        { value -> PermissionType.valueOf(value as String) },
        { it.name }
    )
}

class RolesRepository {
    // TODO: 3/6/2021
}
