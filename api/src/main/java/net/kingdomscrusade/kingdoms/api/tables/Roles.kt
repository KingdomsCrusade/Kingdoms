@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package net.kingdomscrusade.kingdoms.api.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import java.util.*

object Roles : Table() {
    val uuid = uuid("role_uuid").default(UUID.randomUUID())
    val name = varchar("role_name", 12)
    val kingdom = reference(
        "role_kingdom",
        Kingdoms.uuid,
        ReferenceOption.CASCADE,
        ReferenceOption.CASCADE
    ).nullable()

    override val primaryKey = PrimaryKey(uuid)
}