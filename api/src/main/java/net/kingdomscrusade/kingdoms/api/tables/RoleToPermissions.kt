@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package net.kingdomscrusade.kingdoms.api.tables

import net.kingdomscrusade.kingdoms.api.types.Permissions
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object RoleToPermissions : Table() {
    private val permissions : () -> String = {
        val builder = StringBuilder()
        Permissions.values().forEachIndexed { index, permissions ->
            if (index != 0) builder.append(",")
            builder.append("\"$permissions\"")
        }
        builder.toString()
    }
    val ref = reference("ref", Roles.uuid, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val value = customEnumeration(
        "value",
        "ENUM($permissions)",
        { value -> Permissions.valueOf(value as String) },
        { it.name }
    )
}