package net.kingdomscrusade.kingdoms.api

import net.kingdomscrusade.kingdoms.api.actions.IAction
import java.sql.*

class KingdomsAPI(url: String, usr: String, pwd: String) {

    private val database: Connection = DriverManager.getConnection(url, usr, pwd)
    val statement: Statement = database.createStatement()
    private val currentVersion = 1

    companion object DefaultRoleUUIDs {
        const val ownerUUID =   "66e00734-bde4-43c0-a426-46b79075cbb1"
        const val memberUUID =  "66e00734-bde4-43c0-a426-46b79075cbb2"
        const val visitorUUID = "66e00734-bde4-43c0-a426-46b79075cbb3"
    }

    fun execute(action: IAction): String = action.execute(statement)

    // Database Initialization
    init {
        databaseInit()
    }

    fun databaseInit(){
        // Getting version number
        val query: ResultSet
        var version = 0
        try {
            query = statement.executeQuery("SELECT _value FROM PluginInfo WHERE _key = 'version_number';")
            version =
                if (query.next())
                    query.getInt("_value")
                else 0
        } catch (e: SQLSyntaxErrorException) { }

        // Performing upgrade operation
        val module = DatabaseUpgrades()
        val list = module.list
        for (i in version..list.lastIndex)
            list[i](module, statement)

        // Recording version
        if (statement.executeUpdate("UPDATE PluginInfo SET _value = '$currentVersion' WHERE _key = 'version_number'") == 0)
            statement.executeUpdate("INSERT INTO PluginInfo VALUE ('version_number', '$currentVersion')")
    }

}