package net.kingdomscrusade.kingdoms.api

import net.kingdomscrusade.kingdoms.api.types.DatabaseType

data class DatabaseInfo(
    val url: String = "jdbc:mysql://dev.kingdomscrusade.net:33062/test",
    val usr: String = "root",
    val pwd: String = "test",
    val driver: DatabaseType = DatabaseType.MYSQL
)
