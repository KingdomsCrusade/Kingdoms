@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package net.kingdomscrusade.kingdoms.api.repository

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val uuid = uuid("user_uuid")

    val name = varchar(
        "user_name",
        16 /* The maximum length of in-game name is 16 */
    ).uniqueIndex()

    val kingdom = reference(
        "user_kingdom",
        Kingdoms.id,
        ReferenceOption.CASCADE,
        ReferenceOption.CASCADE
    )

    val role = reference(
        "user_role",
        Roles.id,
        onDelete = ReferenceOption.SET_NULL,
        onUpdate = ReferenceOption.CASCADE
    ).nullable()

    override val primaryKey = PrimaryKey(uuid)
}

class UsersRepository {
    // TODO: 3/6/2021  
}
