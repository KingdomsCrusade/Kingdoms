@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package net.kingdomscrusade.kingdoms.api.tables

import net.kingdomscrusade.kingdoms.api.types.InfoType
import org.jetbrains.exposed.sql.Table

object PluginInfo : Table() {
    private val types : () -> String = {
        val builder = StringBuilder()
        InfoType.values().forEachIndexed { index, permissions ->
            if (index != 0) builder.append(",")
            builder.append("\"$permissions\"")
        }
        builder.toString()
    }
    val key = customEnumeration(
        "_key",
        "ENUM($types)",
        { value -> InfoType.valueOf(value as String) },
        { it.name }
    ).uniqueIndex()
    val value = varchar("_value", 255)

    override val primaryKey = PrimaryKey(key)
}