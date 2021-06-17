package net.kingdomscrusade.kingdoms.api.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object RolesTable : Table() {
    val id = uuid("role_id")
    val name = varchar("role_name", 255)
    val kingdom = reference(
        "role_kingdom",
        KingdomsTable.id,
        ReferenceOption.CASCADE,
        ReferenceOption.CASCADE
    ).nullable()

    override val primaryKey = PrimaryKey(id)
}