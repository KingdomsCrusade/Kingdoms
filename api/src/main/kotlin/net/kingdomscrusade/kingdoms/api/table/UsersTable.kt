package net.kingdomscrusade.kingdoms.api.table

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object UsersTable : Table() {
    val uuid = uuid("user_uuid")

    val name = varchar(
        "user_name",
        16 /* The maximum length of in-game name is 16 */
    ).uniqueIndex()

    val kingdom = reference(
        "user_kingdom",
        KingdomsTable.id,
        ReferenceOption.CASCADE,
        ReferenceOption.CASCADE
    )

    val role = reference(
        "user_role",
        RolesTable.id,
        onDelete = ReferenceOption.SET_NULL,
        onUpdate = ReferenceOption.CASCADE
    ).nullable()

    override val primaryKey = PrimaryKey(uuid)
}