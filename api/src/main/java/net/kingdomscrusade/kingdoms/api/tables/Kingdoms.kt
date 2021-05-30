@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package net.kingdomscrusade.kingdoms.api.tables

import org.jetbrains.exposed.sql.Table
import java.util.*

object Kingdoms : Table() {
    val uuid = uuid("kingdom_uuid").default(UUID.randomUUID())
    val name = varchar("kingdom_name", 12).uniqueIndex()

    override val primaryKey = PrimaryKey(uuid)
}