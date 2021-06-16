@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package net.kingdomscrusade.kingdoms.api.repository

import org.jetbrains.exposed.sql.Table

object Kingdoms : Table() {
    val id = uuid("kingdom_id")
    /* It takes 1 byte to store length for varchar that length is <= 255*/
    val name = varchar("kingdom_name", 255).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}

class KingdomsRepository {
    // TODO: 3/6/2021  
}