package net.kingdomscrusade.kingdoms

import java.sql.Connection
import java.sql.DriverManager

/**
 * The main class of kingdoms API. A database will be created on class construction
 */
class KingdomsAPI(databaseUrl: String, username: String, password: String): IDatabase {

    override lateinit var database: Connection
    val actions: IActions = Actions(this)

    init {
        Class.forName("com.mysql.jdbc.Driver")
        database = DriverManager.getConnection(databaseUrl, username, password)
    }

}