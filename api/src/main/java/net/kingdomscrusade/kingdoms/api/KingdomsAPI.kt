package net.kingdomscrusade.kingdoms.api

import net.kingdomscrusade.kingdoms.api.tables.PluginInfo
import net.kingdomscrusade.kingdoms.api.types.DatabaseType
import net.kingdomscrusade.kingdoms.api.types.InfoType
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.*

class KingdomsAPI(url: String, usr: String, pwd: String, databaseType: DatabaseType = DatabaseType.MARIADB) {

    companion object {
        // Database Info
        const val currentVersion = 1
        // UUIDs
        const val owner =   "66e00734-bde4-43c0-a426-46b79075cbb1"
        const val member =  "66e00734-bde4-43c0-a426-46b79075cbb2"
        const val visitor = "66e00734-bde4-43c0-a426-46b79075cbb3"
    }

    // Another constructor for databases without usr and pwd
    constructor(url: String, databaseType: DatabaseType = DatabaseType.MARIADB) : this(url, "", "", databaseType)

    init {
        val driver = when(databaseType) {
            DatabaseType.MARIADB -> "com.mysql.cj.jdbc.Driver"
            DatabaseType.MYSQL -> "org.mariadb.jdbc.Driver"
        }
        if (usr.isNotBlank() && pwd.isNotBlank())
            Database.connect(
                url = url,
                driver = driver,
                user = usr,
                password = pwd
            )
        else
            Database.connect(
                url = url,
                driver = driver
            )
    }

    /* Database Initialization */
    init {
        databaseInit()
    }

    fun databaseInit(){
        // Getting version number
        val version = transaction {
            PluginInfo
                .select { PluginInfo.key eq InfoType.VERSION_NUMBER }
                .map { it[PluginInfo.value] }
                .firstOrNull()
                ?.toInt()
                ?: 0
        }
        // Performing upgrade operation
        val module = DatabaseUpgrades()
        val list = module.list
        for (i in version..list.lastIndex)
            list[i](module)

        // Recording version
        if (statement.executeUpdate("UPDATE PluginInfo SET _value = '$currentVersion' WHERE _key = 'version_number'") == 0)
            statement.executeUpdate("INSERT INTO PluginInfo VALUE ('version_number', '$currentVersion')")
    }

}